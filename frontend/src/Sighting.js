

function Sighting({ sighting, id, bugType, order, description, date, onEdit, onDelete }) {

    function handleDelete() {
        console.log("sightingId2", id);
        onDelete(id);
    }

    function editMe() {
        onEdit(sighting);
    }

    return(
        <tr>
            <td>{bugType}</td>
            <td>{order}</td>
            <td>{description}</td>
            <td>{date}</td>
            <td><button onClick={editMe}>Edit</button></td>
            <td><button onClick={handleDelete}>Delete</button></td>
        </tr>
    )
}

export default Sighting;