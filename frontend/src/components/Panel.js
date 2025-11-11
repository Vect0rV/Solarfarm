import Button from 'react-bootstrap/Button';
import { Link } from "react-router-dom";

function Panel({ panel, id, section, row, column, 
    yearInstalled, material, tracking, onDelete, onEdit }) {

        function deleteMe() {
            console.log("Delete panel id:", panel.id)
            onDelete(panel.id);
        }

        function editMe() {
            onEdit(panel)
        }

        return (
            <div className="p-3 border bg-light">
                <h3>Section: { section }</h3>
                <p>Row: { row } Column: { column}</p>
                <p>Year Installed: {yearInstalled}</p>
                <p>Material Type: {material}</p>
                <p>Sun Tracking: {tracking}</p>
                <div>
                <Link to={`/edit/${panel.id}`}>Edit Panel</Link>
                <Button variant="danger" onClick={deleteMe}>Delete Panel</Button>
                </div>
            </div>
        )

}

export default Panel;