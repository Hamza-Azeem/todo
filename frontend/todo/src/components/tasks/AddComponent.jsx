import axios from "../../api/axios";
import { useState } from "react";
import { Button, Modal, Form } from 'react-bootstrap';



export default function AddTask({ onTaskCreation }) {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const [loading, setLoading] = useState(false);
    const [newTask, setNewTask] = useState({
        name: "",
        status: 1
    });

    const hadnleAddTask = async () => {
        try {
            setLoading(true);
            const resp = await axios.post("/tasks", newTask);
            setLoading(false);
            console.log("TASK_CREATED", resp.data);
            onTaskCreation(resp.data);
        } catch (error) {
            setLoading(false);
            console.log(error.response?.data || error.message);
        }
    }

    return (
        <>
            <Button className="fw-bold w-100" variant="success" disabled={loading} onClick={handleShow}>
                Add Task
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title className="">Add Task</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <input className="form-control mb-3" placeholder="Task title..."
                        onChange={(e) => setNewTask({ ...newTask, name: e.target.value })} />
                    <Form.Select
                        aria-label="Task status select"
                        defaultValue={1}
                        onChange={(e) =>
                            setNewTask({ ...newTask, status: Number(e.target.value) })
                        }
                    >
                        <option value={1}>Pending</option>
                        <option value={2}>Started</option>
                        <option value={3}>Completed</option>
                    </Form.Select>

                </Modal.Body>
                <Modal.Footer className="d-flex flex-row justify-content-start">
                    <Button variant="secondary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button variant="success" onClick={() => { hadnleAddTask(); handleClose(); }}>
                        Add
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}