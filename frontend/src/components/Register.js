import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Error from "./Error";
import { handleResponse } from "../api/handleResponse";



export default function Register () {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState([]);

    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        console.log("Submitted");
        event.preventDefault();

        const response = await fetch("http://localhost:8080/api/create_account", {
            method: "POST",
            headers: {
                "Content-Type": "application/json", 
            },
            body: JSON.stringify({
                username,
                password,
            }),
        })
        .then(handleResponse)
        .then(result => {
            if (result.error) {
                setErrors(result.messages);
                console.log("Result: ", result);
                console.log("Error messages: ", result.messages);
                return;
            } else {
                setErrors(["Unknown error."]);
            }
            navigate("/login");
        })


        console.log("Username: ", username);
        console.log("Password: ", password);
    };

    return (
        <div>
            <h2>Register</h2>
            {errors.map((error, i) => (
                <Error key={i} msg={error} />
            ))}
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="username">Username:</label>
                    <input 
                        type="text"
                        onChange={(event) => setUsername(event.target.value)}
                        id="username"
                    />
                </div>
                <div>
                    <label html="password">Password:</label>
                    <input
                        type="text"
                        onChange={(event) => setPassword(event.target.value)}
                        id="password"
                    />
                </div>
                <div>
                    <button type="submit">Register</button>
                </div>
            </form>
        </div>
    )

}
