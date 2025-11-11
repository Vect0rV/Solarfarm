import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import Home from "./components/Home";
import Solarpanels from "./components/Solarpanels";
import NavBar from "./components/NavBar";
import PanelFarm from "./components/PanelFarm";
import Confirmation from "./components/Confirmation";
import Error from "./components/Error";
import About from "./components/About";
import Contact from "./components/Contact";
import Login from "./components/Login";
import Register from "./components/Register";
import { useState, useEffect } from "react";
import AuthContext from "./context/AuthContext";
import { jwtDecode } from "jwt-decode";

const LOCAL_STORAGE_TOKEN_KEY = "solarFarmToken"

function App() {

  const [user, setUser] = useState(null);

  const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
    if (token) {
      login(token);
    }
    setRestoreLoginAttemptCompleted(true);
  }, []);

  const login = (token) => {
    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

    const { sub: username, roles } = jwtDecode(token);

    // const roles = authorititesString.split(',');

    const user = {
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    }
    console.log("user: ", user);

    setUser(user);

    return user
  };

   const auth = { 
    user: user ? { ...user } : null,
     login, 
     logout 
    }

  function logout() {
    // setUser(prev => {
    //   console.log("user after logout: ", prev);
    //   return null;
    //   });
    setUser(null);
    localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
    
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
            <Route path="/Register" element={<Register />} />
            <Route path="/confirmation" element={<Confirmation />} />
            <Route path="/error" element={<Error />} />
          </Routes>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
