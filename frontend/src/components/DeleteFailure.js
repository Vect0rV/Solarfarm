import { useLocation } from "react-router-dom";

function DeleteSuccess() {
    const { state } = useLocation();
    const msg = state?.msg;
    console.log("msg: ", msg);

    return <p>Panel delete failed ❌ -- {msg} </p>;
}

export default DeleteSuccess;