import { useState } from "react";
import AdminUpdateTask from "../admin/AdminUpdateTaskComponent";
import AdminDeleteTask from "../admin/AdminDeleteTaskComponent";

export default function TaskRow({ task, fetchUsers }) {
    const [taskUpdated, setTaskUpdated] = useState({ ...task });
    return (
        <tr>
            <td><input type="text" className="form-control" disabled value={taskUpdated.id} /></td>
            <td>
                <input
                    type="text"
                    className="form-control"
                    value={taskUpdated.name}
                    onChange={(e) => setTaskUpdated({ ...taskUpdated, name: e.target.value })}
                />
            </td>
            <td>
                <input
                    type="number"
                    className="form-control"
                    value={taskUpdated.status}
                    onChange={(e) => setTaskUpdated({ ...taskUpdated, status: Number(e.target.value) })}
                    min="1"
                    max="3"
                />
            </td>
            <td>
                <AdminUpdateTask taskUpdate={taskUpdated} onChange={fetchUsers}/>
            </td>
            <td>                <AdminDeleteTask taskId={task.id} onChange= {fetchUsers} />
            </td>
        </tr>
    );
}
