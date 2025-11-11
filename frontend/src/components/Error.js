import {  useLocation } from "react-router-dom";

function Error( {i, msg} ) {
    const location = useLocation();
    console.log("msg: ", msg)

    return <p>  {msg}</p>;
}

export default Error;