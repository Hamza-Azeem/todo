import { Outlet } from "react-router-dom";
import Logout from "./LogoutComponent";

export default function ProtectedLayout() {
    return (
        <>
            <nav className="navbar navbar-dark bg-dark px-4 d-flex justify-content-between">
                <h4 className="text-white pt-2">Task Manager</h4>
                <Logout />
            </nav>
            <div className="container mt-4">
                <Outlet /> 
            </div>
        </>
    );
}
