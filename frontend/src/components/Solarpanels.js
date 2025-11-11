import { useEffect, useState } from "react";
import { Container, Row, Col, Form } from 'react-bootstrap'
import Panel from "./Panel";
import { useParams, useNavigate } from "react-router-dom"

function Solarpanels() {
    const [panels, setPanels] = useState([]);

    const [reload, setReload] = useState(false);

    const navigate = useNavigate();

    useEffect(() => {
        
        fetch("http://localhost:8080/api/solarpanel")
            .then(response => {
                if (response.status !== 200) {
                    return Promise.reject("panels fetch failed")
                }
                return response.json();
            })
            .then(json => setPanels(json))
            .catch(console.log);
    }, [reload]);

    const deleteById = (panelId) => {
        fetch(`http://localhost:8080/api/solarpanel/${panelId}`, { method: "DELETE" })
            .then(response => {
                if (response.status === 204) {
                    setReload(prev => !prev);
                    return fetch("http://localhost:8080/api/solarpanel");
                }})
            .then(res => res.json())
            .then(data => {
                setPanels(data);
            })
            .then(data => {
                navigate("/confirmation", { msg: "All Good"});
            })
            .catch(() => {
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
                            yearInstalled={panel.yearInstalled}
                            material={panel.material}
                            tracking={panel.tracking ? "Tracking" : "Not Tracking"}
                            onDelete={deleteById}
                            />
                    </Col>
                ))}
            </Row>
        </Container>

}

export default Solarpanels;