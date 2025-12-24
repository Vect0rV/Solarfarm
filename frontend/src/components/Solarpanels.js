import { useContext, useEffect, useState } from "react";
import { Container, Row, Col, Form } from 'react-bootstrap';
import Panel from "./Panel";
import { useParams, useNavigate } from "react-router-dom";
import AuthContext from "../context/AuthContext";

function Solarpanels() {
    const [panels, setPanels] = useState([]);

    const [reload, setReload] = useState(false);

    const { id } = useParams();

    const navigate = useNavigate();

    const auth = useContext(AuthContext);

    const [errors, setErrors] = useState([]);

    useEffect(() => {
        console.log("User: ", auth.user)

        fetch("process.env.REACT_API_URL}/api/panels", {
            headers: {
                "Authorization": `Bearer ${auth.user.token}`,
                "Content-Type": "application/json" 
            }
        })
            .then(response => {
                if (response.status !== 200) {
                    return Promise.reject("panels fetch failed");
                }
                return response.json();
            })
            .then(json => setPanels(json))
            .catch(console.log);
    }, [reload]);

    const deleteById = (panelId) => {
        console.log("Delete Panel Id: ", panelId)
        fetch(`process.env.REACT_API_URL}/api/panels/${panelId}`, { 
            method: "DELETE",
            headers:{
                        "Authorization": `Bearer ${auth.user.token}`,
                        "Content-Type": "application/json" 
                    }})
            .then(response => {
                if (response.status === 204) {
                    setReload(prev => !prev);
                    return fetch("process.env.REACT_API_URL}/api/panels", {
                        headers:{
                            "Authorization": `Bearer ${auth.user.token}`,
                            "Content-Type": "application/json" 
                        }
                    });
                }
                if (response.status === 403) {
                    console.log("403 Hit");
                    const msg = "Admin permission required";
                    console.log("403 error msg:", msg);
                    navigate("/deleteFailure", {state: { msg }});
                    throw new Error("DELETE_FAILURE_HANDLED");
                }
            })
            .then(res => res.json())
            .then(data => {
                setPanels(data);
            })
            .then(data => {
                console.log("Delete Success Id:", panelId);
                navigate("/deleteSuccess", { state: {panelId}});
            })
            .catch(err => {
                if (err.message === "DELETE_FAILURE_HANDLED") {
                    console.log("Do nothing");
                    return; // do nothing
                }
                navigate("/error", { msg: "💥" });
            });
    }


    return <Container>
            <Row>
                {panels.map((panel) => (
                    <Col key={panel.id} md={3} className="mb-4">
                            <Panel 
                            panel={panel}
                            section={panel.section}
                            row={panel.row}
                            column={panel.column}
                            installationYear={panel.installationYear}
                            materialType={panel.materialType}
                            isTracking={panel.isTracking ? "Tracking" : "Not Tracking"}
                            onDelete={deleteById}
                            />
                    </Col>
                ))}
            </Row>
        </Container>

}

export default Solarpanels;