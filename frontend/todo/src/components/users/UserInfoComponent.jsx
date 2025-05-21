import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "../../api/axios";
import ChangePermission from "../admin/ChangePerimissionComponent";


export default function UserInfo() {
    const [user, setUser] = useState(null);
    const { userId } = useParams();

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

    // async function changeUserPermission(userId, type) {
    //     try {
    //         const resp = await axios("/admin/change-type", {
    //             params: {
    //                 id: userId,
    //                 type: type
    //             }
    //         });
    //         console.log("TYPE_CHANGED", resp.data);
    //     } catch (error) {
    //         console.log(error.response?.data || error.message);
    //     }
    // }

    return (
        <div className="container mt-5">
            <div className="d-flex m-2 justify-content-between">
                <ChangePermission userId={userId} type={2}/>
                <ChangePermission userId={userId} type={1}/>
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










