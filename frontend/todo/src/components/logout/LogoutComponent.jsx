import { useNavigate } from "react-router-dom";


export default function Logout() {
    const navigate = useNavigate();
    function handleLogout() {
        const token = localStorage.getItem("token");

        if (token) {
            localStorage.removeItem("token");
        }
        navigate("/auth/login");
    }

    return (
        <button className="btn btn-danger" onClick={handleLogout}> Logout </button>
    );
}