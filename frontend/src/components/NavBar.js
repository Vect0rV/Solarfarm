import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";
import { useEffect, useState, useContext } from "react";
import AuthContext from "../context/AuthContext";

function NavBar({}) {

    // const [user, setUser] = useState(null);

    const auth = useContext(AuthContext)

    function handleLogout() {
        auth.logout();
    }

    // useEffect(() => {
    //     if (auth.user != null) {
    //         setUser(auth.user);
    //     }
    // }, [auth.user]);

    return (

    <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <a className="navbar-brand" href="#">Navbar</a>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav mr-auto">
                <li className="nav-item active">
                    <Link className="nav-link" to="/">Home</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/solarpanels">Solar Panels</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/add">Add</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/about">About</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/contact">Contact</Link>
                </li>
                {!auth.user ? (
                <ul>
                    <li className="nav-item">
                        <Link className="nav-link" to="/login">Login</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/register">Register</Link>
                    </li>
                </ul>) : (<li className="nav-item">
                    Hi {auth.user.username}
                    <Button className="nav-link" onClick={handleLogout}>Logout</Button>
                </li>)}
            
            </ul>
            <form className="form-inline my-2 my-lg-0">
            <input className="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" />
            <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>
    );

    {/* 
            <ul>
             <li>
                <Link to="/">Home</Link>
            </li>
            <li>
                <Link to="/add">Add</Link>
            </li>
            <li>
                <Link to="/about">About</Link>
            </li>
            <li>
                <Link to="/contact">Contact</Link>
            </li>
             <li>
                <Link to="/login">Login</Link>
            </li>
            </ul>
        </nav> */}
}

export default NavBar;