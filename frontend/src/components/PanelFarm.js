import React, { useEffect, useState, useContext } from "react"
import Button from 'react-bootstrap/Button';
import { Container, Row, Col, Form } from 'react-bootstrap';
import { Link, useNavigate, useParams } from "react-router-dom";
import AuthContext from "../context/AuthContext";
import Panel from "./Panel";
import Error from "./Error";
import { handleResponse } from "../api/handleResponse";

const BLANK_PANEL = {
    section: "", row: 0, column: 0,
        installationYear: "", materialType: "", isTracking: false
}

const INITIAL_PANEL = {
    id: 17, section: "The Ridge", row: 1, column: 1,
        installationYear: 2020, materialType: "POLY_SI", isTracking: true
}

function PanelFarm({ panelOne = INITIAL_PANEL, blankPanel = BLANK_PANEL }) {

    const [panels, setPanels] = useState([]);

    const [isEditing, setIsEditing] = useState(false);

    const { id } = useParams();

    const navigate = useNavigate();

    const [reload, setReload] = useState(false);

    const auth = useContext(AuthContext);

    const [panel, setPanel] = useState(null);

    const [errors, setErrors] = useState([]);

    const [newPanel, setNewPanel] = useState(
        {section: "", row: 0, column: 0,
        installationYear: "", materialType: null, isTracking: false}
    );


    useEffect(
        () => {
            console.log("Trying to fetch panel: ", id);
            if (id) {
            // URL to be set as env variables
                fetch(`${process.env.REACT_APP_API_URL}/api/panels/${id}`, {
                    headers: {
                        "Authorization": `Bearer ${auth.user.token}`,
                        "Content-Type": "application/json" 
                    }
                })
                    .then(response => {
                        if (response.status !== 200) {
                            return Promise.reject("panels fetch failed")
                        }
                        return response.json();
                    })
                    .then(json => { 
                        setPanel(json);
                        setNewPanel(json);
                    })
                    .catch(err => {
                        console.error("Edit fetch failed", err);
                    });
            }
        }, 
    
        [id]
    );

    const handleChange = (evt) => {
        const {name, type, checked, value} = evt.target;

        if (evt.target.name === "isTracking") {
            newPanel.isTracking = evt.target.checked;
        }

        setNewPanel((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value
        }));
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        const updatedPanel = {...newPanel};
    
        if (id) {
            const init = {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Accepts": "application/json",
                    Authorization: `Bearer ${auth.user.token}`
                },
                body: JSON.stringify(updatedPanel)
            };

            console.log("Fetch Update Panel", updatedPanel);
            console.log("Fetch Updated New Panel", newPanel);
            

            fetch(`${process.env.REACT_APP_API_URL}/api/panels/${id}`, init)
                .then(handleResponse)
                .then(result => {
                    if (result.error) {
                        setErrors(result.messages);
                        console.log("Result: ", result);
                        console.log("Error messages: ", result.messages);
                        return;
                    }
                    setPanels([...panels, result.data])
                    navigate("/solarpanels", { msg: "Update successful"});
                }).catch(error => {
                    console.error("Unhandled fetch error:", error);
                });
        } else {
            const init = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accepts": "application/json",
                    Authorization: `Bearer ${auth.user.token}`
                },
                body: JSON.stringify(newPanel)
            };

            fetch(`${process.env.REACT_APP_API_URL}/api/panels`, init)
            .then(handleResponse)
            .then(result => {
                if (result.error) {
                    setErrors(result.messages);
                    return;
                }
                setPanels([...panels, result.data])
                navigate("/solarpanels", { msg: "^v^"});
            })
            .catch(() => {
                setErrors(["Network error"]);
            });
        }
    }

    return (
        <div>
            {errors.map((error, i) => (
                <Error key={i}  msg={error}/>
            ))}     
            <Form>
                <h3 className="mb-3 mx-auto" style={{ maxWidth: '300px', alignContent: "center" }}>{id ? `Edit Panel ${newPanel.panelId}` : "Add Panel"}</h3>
                <Form.Group className="mb-3 mx-auto" controlId="section" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <Form.Label>Section</Form.Label>
                    <input type="text" className="form-control" id="section" name="section" value={newPanel.section} onChange={handleChange}/>
                </Form.Group>
                <Form.Group className="mb-3 mx-auto" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <Form.Label>Row</Form.Label>
                    <Form.Control type="number" id="row" name="row" value={newPanel.row} onChange={handleChange}/>
                    <Form.Label>Column</Form.Label>
                    <Form.Control type="number" id="column" name="column" value={newPanel.column} onChange={handleChange}/>
                </Form.Group>
                <Form.Group className="mb-3 mx-auto" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <Form.Label htmlFor="installationYear">Installation Year</Form.Label>
                    <input type="text" id="installationYear" name="installationYear" value={newPanel.installationYear} onChange={handleChange}/>
                </Form.Group>    
                <Form.Group className="mb-3 mx-auto" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <Form.Label htmlFor="materialType">Material Type</Form.Label>
                    <Form.Control id="materialType" as="select" name="materialType" value={newPanel.materialType} onChange={handleChange}>
                        <option value="">[Select a Material Type]</option>
                        <option value="CIGS">CIGS</option>
                        <option value="A_SI">A_SI</option>
                        <option value="POLY_SI">POLY_SI</option>
                        <option value="CD_TE">CD_TE</option>
                        <option value="MONO_SI">MONO_SI</option>
                    </Form.Control>
                </Form.Group>
                <Form.Group className="mb-3 mx-auto" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <input type="checkbox" value="tracking" id="chkIsTracking" name="isTracking"
                        checked={newPanel.isTracking} onChange={handleChange}/>
                    <label htmlFor="chkIsTracking">
                        <p>{newPanel.isTracking ? "Tracking" : "Not Tracking"}</p></label>
                </Form.Group>   
                <Form.Group className="mb-3 mx-auto text-center" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <Button variant="primary" type="submit" onClick={handleSubmit}>Submit</Button>
                </Form.Group>    
            </Form>
        </div>
    )
}

export default PanelFarm;