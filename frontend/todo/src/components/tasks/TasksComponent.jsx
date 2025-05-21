import { useState, useEffect } from "react";
import axios from "../../api/axios";
import { useParams } from "react-router-dom";
import UpdateTask from "./UpdateComponent";
import DeleteTask from "./DeleteComponent";
import AddTask from "./AddComponent";
import { useNavigate } from "react-router-dom";

export default function Tasks() {

    const { userId } = useParams();
    const [tasks, setTasks] = useState([]);
    const [loading,setLoading] = useState(false);
    const navigate = useNavigate();

    const taskStatusMapper = {
        1: "Pending",
        2: "Started",
        3: "Completed"
    }

    useEffect(() => {
        async function fetchTasks() {
            try {
                setLoading(true);
                const resp = await axios.get(`/tasks/${userId}`);
                setLoading(false);
                setTasks(resp.data);
                console.log(resp.data);
                console.log("TASKS_FETCHED");
            } catch (error) {
                setLoading(false);
                if (error.response?.status === 403) {
                    navigate("/auth/forbidden");
                }
                console.log(error.response?.data || error.message);
            }
        }
        fetchTasks();
    }, [userId, tasks.length]);

    async function handleTaskComplete(index) {
        try {
            const updatedTasks = [...tasks];
            updatedTasks[index] = { ...updatedTasks[index], status: 3 };
            setLoading(true);
            const resp = await axios.put(`/tasks/${userId}`, updatedTasks[index]);
            setTasks(updatedTasks);
            setLoading(false);
            console.log("TASK_COMPLETED", resp.data);
        } catch (error) {
            setLoading(false);
            console.log(error.response?.data || error.message);
        }
    }

    function handleTaskUpdate(updatedTask, idx) {
        let tempTasks = [...tasks];
        tempTasks[idx] = updatedTask;
        setTasks(tempTasks);
    }

    function handleTaskDelete(idx) {
        let tempTasks = [...tasks];
        tempTasks.splice(idx, 1);
        setTasks(tempTasks);
    }
    function handleTaskCreation(newTask) {
        let prevTasks = [...tasks];
        prevTasks.push(newTask);
        setTasks(prevTasks);
    }



    return (
        <div className="container mt-4">
            <h3 className="mb-3 pt-4">User Tasks</h3>
            {tasks && tasks.length > 0 &&
                <table className="table table-dark">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Status</th>
                            <th>Complete</th>
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>

                        {
                            tasks.map((task, index) => {
                                return <tr key={index}>
                                    <td>{index + 1}</td>
                                    <td>{task.status === 3 ? <s>{task.name}</s> : task.name}</td>
                                    <td>{taskStatusMapper[task.status]}</td>
                                    <td><button className="btn btn-success" onClick={() => handleTaskComplete(index)} disabled={task.status === 3 || loading}> Complete</button></td>
                                    <td><UpdateTask task={task} userId={userId} index={index} onTaskUpdate={handleTaskUpdate} /></td>
                                    <td><DeleteTask userId={userId} index={index} taskId={task.id} onTaskDelete={handleTaskDelete} /></td>
                                </tr>
                            })

                        }

                    </tbody>
                </table>
            }
            {
                (!tasks || tasks.length === 0) &&
                <p>Tasks empty :( ... start adding some</p>
            }
            <AddTask onTaskCreation={handleTaskCreation} />

        </div>
    );
}