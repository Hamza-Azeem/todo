import axios from "axios";

const instance = axios.create(
    {
        baseURL: "http://localhost:8080"
    }
);

instance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

instance.interceptors.response.use(
    response => response,
    error => {
        const skipRedirect = error.config?.skipAuthRedirect;
        if (error.response && error.response.status === 403 && !skipRedirect) {
            window.location.href = "/auth/forbidden"; 
        }
        return Promise.reject(error);
    }
)

export default instance;