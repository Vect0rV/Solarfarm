import Button from 'react-bootstrap/Button';
import { Link } from "react-router-dom";

function Panel({ panel, id, section, row, column, 
    installationYear, materialType, isTracking, onDelete, onEdit }) {

        function deleteMe() {
            console.log("Delete panel id:", panel.id)
            onDelete(panel.panelId);
        }

        function editMe() {
            onEdit(panel)
        }

        return (
            <div className="p-3 border bg-light">
                <h3>Section: { section }</h3>
                <p>Row: { row } Column: { column}</p>
                <p>Year Installed: {installationYear}</p>
                <p>Material Type: {materialType}</p>
                <p>Sun Tracking: {isTracking}</p>
                <div>
                <Link to={`/edit/${panel.panelId}`} className="btn btn-primary">Edit Panel</Link>
                <Button variant="danger" onClick={deleteMe}>Delete Panel</Button>
                </div>
            </div>
        )

}

export default Panel;