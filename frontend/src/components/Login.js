import { useState } from "react"
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { useContext } from "react";
import AuthContext from "../context/AuthContext";
import { useNavigate } from "react-router-dom";


import Error from "./Error";



const Login = () => {

    const auth = useContext(AuthContext)

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState([]);

    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();

        const response = await fetch("http://localhost:8080/api/authenticate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                username,
                password,
            }),
        });

        if (response.status === 200) {
            const { jwt_token } = await response.json();
            console.log(jwt_token);
            auth.login(jwt_token);
            navigate("/");
        } else if (response.status === 403) {
            setErrors(["Login failed."]);
        } else {
            setErrors(["Unknown error."]);
        }
    }





    return(
        <div>
            <h2>Login</h2>
            {errors.map((error, i) => (
                <Error key={i}  msg={error}/>
            ))}
            <Form>
                <h3 className="mb-3 mx-auto" style={{ maxWidth: '300px', alignContent: "center" }}>{"Please enter your user name and password"}</h3>
                <Form.Group className="mb-3 mx-auto" controlId="userName" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <Form.Label>Username</Form.Label>
                    <input type="text" className="form-control" id="username" name="username" value={username} onChange={(e) => setUsername(e.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3 mx-auto" controlId="password" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <Form.Label>Password</Form.Label>
                    <input type="text" className="form-control" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3 mx-auto text-center" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <Button variant="primary" type="submit" onClick={handleSubmit}>Submit</Button>
                </Form.Group>    
            </Form>
        </div>
    )
}



export default Login;