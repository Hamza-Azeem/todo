import axios from "../../api/axios";
import { useEffect, useState } from "react";

export default function Forbidden() {
    const [userToken, setUserToken] = useState(null);
    const [message, setMessage] = useState("");
    async function fetchUserToken() {
        try {
            const resp = await axios.get("/tokens/user-token");
            setUserToken(resp.data)
        } catch (error) {
            console.log(error.response?.data || error.message);
        }
    }

    useEffect(() => {
        fetchUserToken();
    }, []);

    async function handleSendRequest(userId) {
        try {
            const resp = await axios.post(`/admin/requests/${userId}`);
            setUserToken(resp.data)
            setMessage("Request submitted successfully.")
        } catch (error) {
            console.log(error.response?.data || error.message);
        }
    }

    return (
        <div className="container mt-5 text-center">
            {message && <div className="alert alert-success" role="alert">
                {message}
            </div>}
            <h1 className="text-danger">403 - Forbidden</h1>
            <p>You don't have permission to access this resource.</p>
            <button className="btn btn-primary" onClick={() => { handleSendRequest(userToken.userId) }}>Request Permission</button>
        </div>
    );
}
