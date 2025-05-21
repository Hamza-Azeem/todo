import axios from "../../api/axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";


export default function Login() {
    const [loading, setLoading] = useState(false);
    const [loginRequest, setLoginRequest] = useState({
        email: "",
        password: ""
    });
    const navigate = useNavigate();
    const [failed, setFailed] = useState(false);

    async function handleLogin(e) {
        e.preventDefault();
        try {
            setLoading(true);
            const resp = await axios.post("/auth/login", loginRequest);
            setLoading(false);
            console.log("Login success");
            localStorage.setItem("token", resp.data);
            setFailed(false);
            navigate("/tokens/user")
        } catch (error) {
            setFailed(true);
            setLoading(false);
            console.log("Login failed", error.response?.data || error.message);
        }
    }

    function handleRegister() {
        navigate("/auth/register");
    }

    return (
        <div className="container w-100 p-4 border rounded-3 bg-white shadow w-100">
            <h2 className="mb-4 text-center text-success">Login</h2>
            {failed && <div className="alert alert-danger" role="alert">
                Invalid email or password.
            </div>}
            <form onSubmit={handleLogin}>
                <div className="form-group text-start fw-semibold">
                    <label htmlFor="email">Email</label>
                    <input className="form-control" type="email" id="email" value={loginRequest.email}
                        onChange={(e) => setLoginRequest({ ...loginRequest, email: e.target.value })} />
                </div>
                <div className="form-group text-start fw-semibold">
                    <label htmlFor="password">Password</label>
                    <input className="form-control" type="password" id="password" value={loginRequest.password}
                        onChange={(e) => setLoginRequest({ ...loginRequest, password: e.target.value })} />
                </div>
                <div className="form-group mt-2 ">
                    <button className="btn btn-success form-control" disabled={loading}>
                        {loading ? <span className="spinner-border spinner-border-sm" /> : "Login"}
                        </button>
                </div>
            </form>
            <p className="mt-3">Don't have an account? <a onClick={handleRegister} className="link-secondary fw-bold">Signup</a></p>
        </div>
    );
}