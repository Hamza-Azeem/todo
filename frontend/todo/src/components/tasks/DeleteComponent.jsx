import axios from "../../api/axios";
import { useEffect, useState } from "react";
import { Button, Modal } from 'react-bootstrap';

export default function DeleteTask({ onTaskDelete, taskId, userId, index }) {
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);
    const [loading, setLoading] = useState(false);
    const handleRemoveTask = async () => {
        try {
            setLoading(true);
            const resp = await axios.delete(`/tasks/${userId}/${taskId}`);
            setLoading(false);
            console.log(resp.data);
            onTaskDelete(index);
            handleClose();
        } catch (error) {
            setLoading(false);
            console.log(error.response?.data || error.message);
        }
    };


    return (
        <>
            <Button variant="danger" onClick={handleShow} disabled={loading}>
                Delete
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title className="">Delete Task</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <h6>Are you sure you want to delete this task? </h6>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button variant="danger" onClick={() => { handleRemoveTask(); handleClose(); }}>
                        Delete
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}