import axios from "../../api/axios";
import { useState } from 'react';
import { useNavigate  } from 'react-router-dom';

export default function Register() {
    const navigate = useNavigate();
    const [registerationRequest, setRegisterationRequest] = useState(
        {
            firstName: "",
            lastName: "",
            email: "",
            password: ""
        }
    );
    async function handleRegister(event) {
        event.preventDefault();
        try {
            const res = await axios.post('/auth/register', registerationRequest);
            console.log("Registration success:", res.data);
            navigate('/auth/login')
        } catch (error) {
            console.error("Registration failed:", error.response?.data || error.message);
        }
    }
    return (
        <div className="cotainer w-100">
            <form

                onSubmit={handleRegister}
                className="p-4 border rounded-3 bg-white shadow w-100"

            >
                <h2 className="mb-4 text-center text-primary">Register</h2>

                <div className="mb-3 text-start ">
                    <label htmlFor="fistName" className="form-label fw-semibold ">
                        First Name
                    </label>
                    <input
                        type="text"
                        id="fistName"
                        className="form-control form-control-lg"
                        value={registerationRequest.firstName}
                        onChange={e =>
                            setRegisterationRequest({
                                ...registerationRequest,
                                firstName: e.target.value
                            })
                        }
                        placeholder="Enter your first name"
                        required
                    />
                </div>

                <div className="mb-3 text-start">
                    <label htmlFor="lastName" className="form-label fw-semibold">
                        Last Name
                    </label>
                    <input
                        type="lastName"
                        id="lastName"
                        className="form-control form-control-lg"
                        value={registerationRequest.lastName}
                        onChange={e =>
                            setRegisterationRequest({
                                ...registerationRequest,
                                lastName: e.target.value
                            })
                        }
                        placeholder="Enter your last name"
                        required
                    />
                </div>

                <div className="mb-3 text-start">
                    <label htmlFor="email" className="form-label fw-semibold">
                        Email Address
                    </label>
                    <input
                        type="email"
                        id="email"
                        className="form-control form-control-lg"
                        value={registerationRequest.email}
                        onChange={e =>
                            setRegisterationRequest({
                                ...registerationRequest,
                                email: e.target.value
                            })
                        }
                        placeholder="Enter your email"
                        required
                    />
                </div>

                <div className="mb-4 text-start">
                    <label htmlFor="password" className="form-label fw-semibold">
                        Password
                    </label>
                    <input
                        type="password"
                        id="password"
                        className="form-control form-control-lg"
                        value={registerationRequest.password}
                        onChange={e =>
                            setRegisterationRequest({
                                ...registerationRequest,
                                password: e.target.value
                            })
                        }
                        placeholder="Enter your password"
                        required
                    />
                </div>

                <button type="submit" className="btn btn-primary btn-lg w-100">
                    Register
                </button>
            </form>
        </div>

    );

}