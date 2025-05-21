import axios from "../../api/axios";


export default function AdminDelete({ taskId, onChange }) {
    async function handleAdminDelete() {
        try {
            console.log(taskId);
            const resp = await axios.delete(`/admin/task/${taskId}`);
            onChange();
            console.log(resp.data);
        } catch (error) {
            console.log(error.response?.data || error.message);
        }
    }
    return (
        <button className="btn btn-danger"
            onClick={()=>handleAdminDelete()}
        >
            Delete Task
        </button>
    );

}