import { useEffect, useState } from "react";
import axios from "../../api/axios";
import TaskRow from "../tasks/TaskRowComponent.jsx"; // new component

export default function UsersTasks() {
    const [usersTasks, setUsersTasks] = useState();

    async function fetchUsersTasks() {
        try {
            const resp = await axios.get("/tasks/users");
            setUsersTasks(resp.data.data);
        } catch (error) {
            console.log(error.response?.data || error.message);
        }
    }

    useEffect(() => {
        fetchUsersTasks();
    }, []);

    return (
        <div className="container mt-5">
            {usersTasks && Object.entries(usersTasks).map(([email, tasks], index) => (
                <div className="container mb-4" key={index}>
                    <h4 className="m-2">Email: {email}</h4>
                    <div className="card shadow-sm border-0 rounded-4 bg-white">
                        <div className="card-header bg-primary text-white text-center rounded-top-4">
                            <h4 className="mb-0">📝 Tasks</h4>
                        </div>
                        <div className="card-body p-0">
                            <table className="table mb-0">
                                <thead className="table-light">
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Status</th>
                                        <th colSpan={2}>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {tasks && tasks.length > 0 ? (
                                        tasks.map((task) => (
                                            <TaskRow key={task.id} 
                                            fetchUsers={fetchUsersTasks}
                                            task={task} />
                                        ))
                                    ) : (
                                        <tr>
                                            <td colSpan="4" className="text-center py-4">
                                                No tasks found for this user
                                            </td>
                                        </tr>
                                    )}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
}