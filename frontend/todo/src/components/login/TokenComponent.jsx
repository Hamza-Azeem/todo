import { useEffect, useState } from "react";
import { Navigate, Outlet } from "react-router-dom";
import axios from "../../api/axios";

export default function IsTokenValid() {
  const [isValid, setIsValid] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");

    async function validateToken() {
      if (!token) {
        setIsValid(false);
        return;
      }

      try {
        const resp = await axios.get("/tokens");
        console.log("VALID_TOKEN", resp.data);
        setIsValid(true);
      } catch (error) {
        console.log(error.response?.data || error.message);
        localStorage.removeItem("token");
        setIsValid(false);
      }
    }

    validateToken();
  }, []);

  if (isValid === null) return <div>Checking authentication...</div>;
  if (!isValid) return <Navigate to="/auth/login" />;
  return <Outlet />;
}
