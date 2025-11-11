import { useState, useEffect } from 'react';
import Button from 'react-bootstrap/Button';
import { Form } from 'react-bootstrap';
import Table from 'react-bootstrap/Table';
import Sighting from './Sighting';

const getBlankPanel = () => ({
    bugType: "", order: "", description: "", date: ""
})

function BugSafariFetch() {

    const [sightings, setSightings] = useState([]);

    const [newSighting, setNewSighting] = useState(
        {bugType: "", order: "", description: "", date: ""}
    );

    const [isEditing, setIsEditing] = useState(false);


    useEffect(() => {

        // fetch initial sightings
        fetch("http://localhost:8080/sighting")
            .then(response => {
                if (response.status !== 200) {
                    return Promise.reject("sightings fetch failed")
                }
                return response.json();
            })
            .then(json => setSightings(json))
            .catch(console.log);

    }, []); // only run once

    const add = () => {


        const init = {
            method: "Post",
            headers: {
                "Content-Type": "application/json",
                "Accepts": "application/json"
            },
            body: JSON.stringify(newSighting)
        };

        fetch("http://localhost:8080/sighting", init)
            .then(response => {
                if (response.status !== 201) {
                    return Promise.reject("response is not 200 ok");
                }
                return response.json();
            })
            .then(json => setSightings([...sightings, json]))
            .catch(error => {
                console.log(error);
                renderErrors(error);
            });
    }

    const edit = (sighting) => {
        const init = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Accepts": "application/json"
            },
            body: JSON.stringify(sighting)
        };

        fetch(`http://localhost:8080/sighting/${sighting.sightingId}`, init)
            .then(response => {
                if (response.status === 404) {
                    return Promise.reject("Response is 404");
                } else if (response.status === 204) {
                    console.log("Sighitng updated!");
                    const updatedSightings = sightings.map(s => 
                       s.sightingId === sighting.sightingId ? sighting : s
                    );
                    return updatedSightings;
                } else {
                    let message = `Sighting id ${sighting.sightingId} update failed with status ${response.status}.`
                    console.log(message);
                    return Promise.reject(message);
                }
            })
            .then(updatedSightings => {
                setSightings(updatedSightings);
                console.log("Sighting updated locally.")
            })
            .catch(error => {
                console.error("Edit error:", error);
                renderErrors(error)
            })
    }

    const deleteById = (sightingId) => {
        console.log("sightingId", sightingId);
        fetch(`http://localhost:8080/sighting/${sightingId}`, { method: "DELETE" })
            .then(response => {
                if (response.status === 204) {
                    // filter new state
                    setSightings(sightings.filter(s => s.sightingId !== sightingId));
                } else if (response.status === 404) {
                    return Promise.reject("sighting not found");
                } else {
                    return Promise.reject(`Delete failed with status: ${response.status}`);
                }
            })
            .catch(error => {
                console.log(error);
                renderErrors(error.message);
            });
    }

    const handleChange = (evt) => {
        const {name, value} = evt.target;

        setNewSighting((prev) => ({
            ...prev,
            [name] : value
        }));
    }

    const handleEdit = function(sighting) {
        setNewSighting(sighting);
        console.log("Edit Sighting:", sighting);
        setIsEditing(true);
    }

    function handleSubmit(evt) {
        evt.preventDefault();
        if (isEditing === true) {
            edit(newSighting);
        } else {
            add();
        }
        setNewSighting(getBlankPanel);
        setIsEditing(false);
    }

    function renderErrors(message) {
        const errDiv = document.getElementById("api-errors");
        errDiv.innerHTML = message;
        errDiv.style.display = "block";
    }

    return (
    <div style={{ background:"silver", minHeight: "100vh" }}>
        <div style={{ background:"silver" }}>
        <h1 style={{ textAlign:"center", background:"silver" }}>Bug Safari</h1>
        </div>
        <Table striped border hover variant='dark'>
            <thead>
                <tr>
                    <th style={{ textAlign: "center" }}>Bug Type</th>
                    <th style={{ textAlign: "center" }}>Order</th>
                    <th style={{ textAlign: "center" }}>Description</th>
                    <th style={{ textAlign: "center" }}>Date</th>
                    <th colSpan="2" style={{ textAlign: "center" }}>Actions</th>
                </tr>
            </thead>
            <tbody>
                {sightings.map((sighting) => (
                    
                    <Sighting 
                        key={sighting.sightingId}
                        sighting={sighting}
                        id={sighting.sightingId}
                        bugType={sighting.bugType}
                        order={sighting.order}
                        description={sighting.description}
                        date={sighting.date}
                        onEdit={handleEdit}
                        onDelete={deleteById}
                    />
                ))} 
            </tbody>
        </Table>
        <h3 id="api-errors"></h3>
        <Form>
            <h3 style={{ textAlign:"center" }}>Add a Sighting</h3>

            <Form.Group className="mb-3 mx-auto" controlId="bugType" style={{ maxWidth: '300px', alignContent: "center"}}>
                <Form.Label>Bug Type</Form.Label>
                <Form.Control type="text" className="form-control bg-light border-gold text-dark" name="bugType" value={newSighting.bugType} onChange={handleChange}></Form.Control>
            </Form.Group>
            <Form.Group className="mb-3 mx-auto" controlId="order" style={{ maxWidth: '300px', alignContent: "center"}}>
                <Form.Label>Order:</Form.Label>
                <Form.Control type="text" className="form-control" name="order" value={newSighting.order} onChange={handleChange}></Form.Control>
            </Form.Group>
            <Form.Group className="mb-3 mx-auto" controlId="description" style={{ maxWidth: '300px', alignContent: "center"}}>
                <Form.Label>Description:</Form.Label>
                <Form.Control type="text" className="form-control" name="description" value={newSighting.description} onChange={handleChange}></Form.Control>
            </Form.Group>
            <Form.Group className="mb-3 mx-auto" controlId="date" style={{ maxWidth: '300px', alignContent: "center"}}>
                <Form.Label>Date:</Form.Label>
                <Form.Control type="date" className="form-control" name="date" value={newSighting.date} onChange={handleChange}></Form.Control>
            </Form.Group>
            <div className="text-center">
                <Button variant="warning" type="submit" onClick={handleSubmit}>Submit</Button>
            </div>
        </Form>
    </div>
    );
}

export default BugSafariFetch;

