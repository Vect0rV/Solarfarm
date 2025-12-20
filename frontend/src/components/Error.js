import {  useLocation } from "react-router-dom";
import "../App.css";

function Error( {i, msg} ) {
    const location = useLocation();
    console.log("msg: ", msg)

    return <p className="error-msg">* {msg}</p>;
}

export default Error;