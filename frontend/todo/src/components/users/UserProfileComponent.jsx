import axios from "../../api/axios";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import RequestComponent from "./RequestsComponent.jsx"

export default function UserProfile() {
    const [user, setUser] = useState();
    const [requests, setRequests] = useState(null);
    const navigate = useNavigate();
    async function fetchUser() {
        try {
            const resp = await axios.get("/tokens/user");
            // console.log(resp.data);
            setUser(resp.data);
        } catch (error) {
            if (error.response?.status === 403) {
                navigate("/auth/forbidden");
            }
            console.log(error.response?.data || error.data);
        }
    }
    async function fetchRequests() {
        if (user.userDto.userType === "ADMIN") {
            try {

                const resp = await axios.get("/admin/requests", {
                    skipAuthRedirect: true
                });
                setRequests(resp.data);
            } catch (error) {
                if (error.response?.status === 403) {

                    console.log("Requests ignored");
                } else {
                    console.log(error.response?.data || error.message);
                }
            }
        } else {
            setRequests(null);
        }
    }

    useEffect(() => {
        fetchUser();
    }, []);

    useEffect(() => {
        if (user) {
            fetchRequests();
        }
    }, [user]);




    return (
        <div className="container mt-5">
            {requests && <div className="d-flex m-2 justify-content-end"><RequestComponent requests={requests} /></div>}

            <div className="card shadow-lg border-0 rounded-4 bg-light">
                <div className="card-header bg-success text-white text-center rounded-top-4">
                    <h2 className="mb-0">👤 User Information</h2>
                </div>
                {user && (() => {
                    const { id, firstName, lastName, email } = user.userDto;
                    return (
                        <div className="card-body">
                            <ul className="list-group list-group-flush">
                                <li className="list-group-item d-flex">
                                    <span className="fw-bold text-secondary">ID:</span>
                                    <span className="text-dark ps-2 fw-bold">{id}</span>
                                </li>
                                <li className="list-group-item d-flex">
                                    <span className="fw-bold text-secondary">First Name:</span>
                                    <span className="text-dark ps-2 fw-bold">{firstName}</span>
                                </li>
                                <li className="list-group-item d-flex">
                                    <span className="fw-bold text-secondary">Last Name:</span>
                                    <span className="text-dark ps-2 fw-bold">{lastName}</span>
                                </li>
                                <li className="list-group-item d-flex">
                                    <span className="fw-bold text-secondary">Email:</span>
                                    <span className="text-dark ps-2 fw-bold">{email}</span>
                                </li>
                            </ul>
                        </div>
                    );
                })()}
                <div className="d-flex justify-content-center">
                    <button className="btn btn-primary m-2 fw-bold"
                        onClick={() => { navigate(`/tasks/${user.userDto.id}`) }}>Show Tasks</button>
                    {user && user.userDto.userType === 'ADMIN' && <button className="btn btn-primary m-2 fw-bold"
                    onClick={()=>{navigate('/users/tasks')}}
                    >Users Tasks</button>}
                </div>


            </div>
        </div>
    );

}
