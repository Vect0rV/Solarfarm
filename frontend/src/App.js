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
import DeleteSuccess from "./components/DeleteSuccess";
import DeleteFailure  from "./components/DeleteFailure";
import Error from "./components/Error";

const LOCAL_STORAGE_TOKEN_KEY = "solarFarmToken"

function App() {

  const [user, setUser] = useState(null);
  const [isAuthRestored, setIsAuthRestored] = useState(false);

  const login = (token) => {
    try {
      localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

      const decoded = jwtDecode(token);

      // Decode the token
      const { sub: username, authorities } = jwtDecode(token);

      // Split the authorities string into an array of roles
      // const roles = authorities.map(a => a.replace("ROLE_", ""));

      const roles = (typeof authorities === "string" ? authorities.split(",") : authorities);

      // Create the "user" object
      const user = {
        username,
        roles,
        token,
        hasRole(role) {
          return this.roles.includes(role);
        },
      };

      // Log user and token for debugging
      console.log("User: ", user);
      console.log(jwtDecode(token));
      
      // Update the user state
      setUser(user);

      // Return the user to the caller
      return user;
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

  if (!isAuthRestored) {
    return null;
  }

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
            <Route path="/deleteSuccess" element={<DeleteSuccess />} />
            <Route path="/deleteFailure" element={<DeleteFailure />} />
            <Route path="/error" element={<Error />} />
          </Routes>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
