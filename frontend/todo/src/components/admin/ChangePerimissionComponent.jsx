import { useNavigate } from "react-router-dom";
import axios from "../../api/axios";

export default function ChangePermission({ userId, type }) {
    const navigate = useNavigate();
    async function changeUserPermission() {
        try {
            console.log(userId);
            console.log(type);
            const resp = await axios("/admin/change-type", {
                params: {
                    id: userId,
                    type: type
                }
            });
            deleteUserRequest(userId);
            console.log("TYPE_CHANGED", resp.data);
        } catch (error) {
            console.log(error.response?.data || error.message);
        }
    }

    async function deleteUserRequest() {
        try {
            const resp = await axios.delete(`/admin/requests/${userId}`);
            console.log(resp.data);
        } catch (error) {
            console.log(error.response?.data || error.message);
        }
    }

    return (
        <button className={type === 1 ? "btn btn-success" : "btn btn-danger"}
            onClick={() => { changeUserPermission(), navigate("/tokens/user")}}
        >{type === 1 ? "Approve" : "Suspend"}</button>
    );
}