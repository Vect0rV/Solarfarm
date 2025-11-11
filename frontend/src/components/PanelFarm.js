import React, { useEffect, useState, useContext } from "react"
import Button from 'react-bootstrap/Button';
import { Container, Row, Col, Form } from 'react-bootstrap';
import { Link, useNavigate, useParams } from "react-router-dom";
import AuthContext from "../context/AuthContext";
import Panel from "./Panel";

const BLANK_PANEL = {
    section: "", row: 0, column: 0,
        yearInstalled: "", material: "", tracking: false
}

const INITIAL_PANEL = {
    id: 17, section: "The Ridge", row: 1, column: 1,
        yearInstalled: 2020, material: "POLY_SI", tracking: true
}

function PanelFarm({ panelOne = INITIAL_PANEL, blankPanel = BLANK_PANEL }) {

    const [panels, setPanels] = useState([]);

    const [isEditing, setIsEditing] = useState(false);

    const { id } = useParams();

    const navigate = useNavigate();

    const [reload, setReload] = useState(false);

    const auth = useContext(AuthContext);

    const [panel, setPanel] = useState(null);

    const [newPanel, setNewPanel] = useState(
        {section: "", row: 0, column: 0,
        yearInstalled: "", material: "", tracking: false}
    );

    useEffect(
        () => {
            if (id) {
                fetch(`http://localhost:8080/api/solarpanel/${id}`)
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
                    .catch(console.log);
            }
        }, 
    
        [id]
    );

    const handleChange = (evt) => {
        const {name, type, checked, value} = evt.target;

        if (evt.target.name === "tracking") {
            newPanel.tracking = evt.target.checked;
        }

        setNewPanel((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value
        }));
    }

    const handleSubmit = (event) => {
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
            

            fetch(`http://localhost:8080/api/solarpanel/${id}`, init)
            .then(response => {
                console.log("Edit status", response.status)
                if (response.status === 404) {
                    return Promise.reject("Response is 404");
                } else if (response.status === 204) {
                    console.log("Panel updated!");
                    // const updatedPanels = panels.map(p => 
                    // p.id === panel.id ? panel : p);
                    setReload(prev => !prev);
                    console.log("panel after update", panel);
                    return null;
                } else {
                    let message = `Panel id ${id} update failed with status ${response.status}.`
                    console.log(message);
                    return Promise.reject(message);
                }
            })
            // .then(json => setPanels([...panels, json]))
            .then(data => {
                navigate("/confirmation", { msg: "^v^"});
            })
            .catch(error => {
                navigate("/error", { msg: "💥" });
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

            fetch("http://localhost:8080/api/solarpanel", init)
            .then(response => {
                if (response.status !== 201) {
                    return Promise.reject("response is not 200 ok");
                }
                return response.json();
            })
            .then(json => setPanels([...panels, json]))
            .then(data => {
                navigate("/confirmation", { msg: "^v^"});
            })
            .catch(error => {
                navigate("/error", { msg: "💥" });
            });
        }
    }

    return (
        <div>     
            <Form>
                <h3 className="mb-3 mx-auto" style={{ maxWidth: '300px', alignContent: "center" }}>{isEditing ? `Edit Panel ${newPanel.id}` : "Add Panel"}</h3>
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
                    <Form.Label htmlFor="yearInstalled">Installation Year</Form.Label>
                    <input type="text" id="yearInstalled" name="yearInstalled" value={newPanel.yearInstalled} onChange={handleChange}/>
                </Form.Group>    
                <Form.Group className="mb-3 mx-auto" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <Form.Label htmlFor="material">Material Type</Form.Label>
                    <Form.Control id="material" as="select" name="material" value={newPanel.material} onChange={handleChange}>
                        <option value="">[Select a Material Type]</option>
                        <option value="CIGS">CIGS</option>
                        <option value="A_SI">A_SI</option>
                        <option value="POLY_SI">POLY_SI</option>
                        <option value="CD_TE">CD_TE</option>
                        <option value="MONO_SI">MONO_SI</option>
                    </Form.Control>
                </Form.Group>
                <Form.Group className="mb-3 mx-auto" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <input type="checkbox" value="tracking" id="chkIsTracking" name="tracking"
                        checked={newPanel.tracking} onChange={handleChange}/>
                    <label htmlFor="chkIsTracking">
                        <p>{newPanel.tracking ? "Tracking" : "Not Tracking"}</p></label>
                </Form.Group>   
                <Form.Group className="mb-3 mx-auto text-center" style={{ maxWidth: '300px', alignContent: "center" }}>
                    <Button variant="primary" type="submit" onClick={handleSubmit}>Submit</Button>
                </Form.Group>    
            </Form>
        </div>
    )
}

export default PanelFarm;