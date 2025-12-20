import { useLocation } from "react-router-dom";

function DeleteSuccess() {
    const { state } = useLocation();
    const panelId = state?.panelId;

    return <p>Panel {panelId} Deleted Successfully ✅ </p>;
}

export default DeleteSuccess;