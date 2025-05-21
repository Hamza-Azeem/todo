import axios from '../../api/axios';
import { useState } from 'react';
import { Button, Modal, Form } from 'react-bootstrap';

export default function UpdateTask({ task, userId, onTaskUpdate, index }) {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const [loading, setLoading] = useState(false);
    const [taskUpdated, setTaskUpdated] = useState(task);
    async function handleUpdate(e) {
        handleClose();
        try {
            setLoading(true);
            // await new Promise((resolve) => setTimeout(resolve, 3000));
            const resp = await axios.put(`/tasks/${userId}`, taskUpdated);
            setLoading(false);
            console.log("TASK_UPDATED", resp.data);
            onTaskUpdate(taskUpdated, index);
        } catch (error) {
            setLoading(false);
            console.log(error.response?.data || error.message);
        }
    }

    return (
        <>
            <Button variant="warning" onClick={handleShow} disabled={loading}>
                Update
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Update Task</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <input className="form-control mb-3" placeholder="Title" defaultValue={task.name}
                        onChange={(e) => setTaskUpdated({ ...taskUpdated, name: e.target.value })} />
                    <Form.Select
                        aria-label="Task status select"
                        value={taskUpdated.status}
                        onChange={(e) =>
                            setTaskUpdated({ ...taskUpdated, status:Number(e.target.value) })
                        }
                    >
                        <option value={1}>Pending</option>
                        <option value={2}>Started</option>
                        <option value={3}>Completed</option>
                    </Form.Select>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={handleUpdate}>
                        Save
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}
