import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "../../api/axios";



export default function UserInfo() {
    const [user, setUser] = useState(null);
    const { userId } = useParams();
    const navigate = useNavigate();

    async function fetchUserById(userId) {
        try {
            const resp = await axios.get(`/users/${userId}`);
            console.log(resp.data);
            setUser(resp.data);
        } catch (error) {
            console.log(error.response?.data || error.message);
        }
    }
    useEffect(() => {
        fetchUserById(userId);
    }, []);

    async function changeUserPermission(userId, type) {
        try {
            const resp = await axios("/admin/change-type", {
                params: {
                    id: userId,
                    type: type
                }
            });
            console.log("TYPE_CHANGED", resp.data);
        } catch (error) {
            console.log(error.response?.data || error.message);
        }
    }
    async function deleteUserRequest(userId) {
        try {
            const resp = await axios.delete(`/admin/requests/${userId}`);
            console.log(resp.data);
        } catch (error) {
            console.log(error.response?.data || error.message);
        }
    }

    return (
        <div className="container mt-5">
            <div className="d-flex m-2 justify-content-between">
                <button className="btn btn-danger" onClick={() => { changeUserPermission(userId, 2), deleteUserRequest(userId), navigate("/tokens/user") }}>Suspend</button>
                <button className="btn btn-success" onClick={() => { changeUserPermission(userId, 1), deleteUserRequest(userId), navigate("/tokens/user") }}>Approve</button>
            </div>
            <div className="card shadow-lg border-0 rounded-4 bg-light">
                <div className="card-header bg-success text-white text-center rounded-top-4">
                    <h2 className="mb-0">👤 User Information</h2>
                </div>
                {user && (() => {

                    return (
                        <div className="card-body">
                            <ul className="list-group list-group-flush">
                                <li className="list-group-item d-flex">
                                    <span className="fw-bold text-secondary">ID:</span>
                                    <span className="text-dark ps-2 fw-bold">{user.id}</span>
                                </li>
                                <li className="list-group-item d-flex">
                                    <span className="fw-bold text-secondary">First Name:</span>
                                    <span className="text-dark ps-2 fw-bold">{user.firstName}</span>
                                </li>
                                <li className="list-group-item d-flex">
                                    <span className="fw-bold text-secondary">Last Name:</span>
                                    <span className="text-dark ps-2 fw-bold">{user.lastName}</span>
                                </li>
                                <li className="list-group-item d-flex">
                                    <span className="fw-bold text-secondary">Email:</span>
                                    <span className="text-dark ps-2 fw-bold">{user.email}</span>
                                </li>
                            </ul>
                        </div>
                    );
                })()}
                {/* <button className="btn btn-primary mt-2 d-flex fw-bold"
                    onClick={() => { navigate(`/tasks/${user.userDto.id}`) }}>Show Tasks</button> */}
            </div>
        </div>
    );
}










