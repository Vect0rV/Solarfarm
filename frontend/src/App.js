// React & Router
import { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";

// Context & Utilities
import AuthContext from "./context/AuthContext";
import { jwtDecode } from "jwt-decode";

// Components
import NavBar from "./components/NavBar";
import Home from "./components/Home";
import About from "./components/About";
import Contact from "./components/Contact";
import Solarpanels from "./components/Solarpanels";
import PanelFarm from "./components/PanelFarm";
import Login from "./components/Login";
import Register from "./components/Register";
import Confirmation from "./components/Confirmation";
import Error from "./components/Error";

const LOCAL_STORAGE_TOKEN_KEY = "solarFarmToken"

function App() {

  const [user, setUser] = useState(null);
  const [isAuthRestored, setIsAuthRestored] = useState(false);

  const login = (token) => {
    try {
      localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

      const { sub: username, roles } = jwtDecode(token);

      const user = {
        username,
        roles,
        token,
        hasRole(role) {
          return this.roles.includes(role);
        },
      };

      setUser(user);
      return user
    } catch (error) {
     console.error("Failed to decode token: ", error);
    }
   };

 const logout = () => {
   setUser(null);
   localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
 }

  useEffect(() => {
    const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
    if (token) {
      login(token);
    }
    setIsAuthRestored(true);
  }, []);

  const auth = {
     user: user ? { ...user } : null,
     login,
     logout
  };

  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <NavBar auth={auth} />

          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="solarpanels" element={auth.user ? ( <Solarpanels/>) : (<Navigate to="/login" />)} />
            <Route path="/about" element={<About />} />
            <Route path="/contact" element={<Contact />} />
            <Route path="/edit/:id" element={auth.user ? <PanelFarm /> : <Navigate to="/login" />} />
            <Route path="/add" element={auth.user ? <PanelFarm /> : <Navigate to="/login" />} />
            <Route path="/login" element={<Login auth={auth}/>} />
            <Route path="/register" element={<Register />} />
            <Route path="/confirmation" element={<Confirmation />} />
            <Route path="/error" element={<Error />} />
          </Routes>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
