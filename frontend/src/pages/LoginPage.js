import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { login } from "../services/authService";

function LoginPage() {
    const [usernameOrEmail, setUsernameOrEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const navigate = useNavigate();
    const location = useLocation();
    const sessionMessage = location.state?.message;

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const data = await login(usernameOrEmail, password);

            // store auth data
            localStorage.setItem("token", data.token);
            localStorage.setItem("email", data.email);
            localStorage.setItem("expiresAt", data.expiresAt);
            localStorage.setItem("personId", data.personId);
            localStorage.setItem("credentialsId", data.credentialsId);
            localStorage.setItem("role", data.role);

            setMessage("Login successful!");

            navigate("/home");
        } catch (error) {
            console.error("Login error:", error);
            setMessage("Login failed. Check credentials.");
        }
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>Login</h2>

            {}
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    placeholder="Username or Email"
                    value={usernameOrEmail}
                    onChange={(e) => setUsernameOrEmail(e.target.value)}
                    style={{ display: "block", marginBottom: "10px", padding: "8px", width: "250px" }}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={{ display: "block", marginBottom: "10px", padding: "8px", width: "250px" }}
                />
                {}
                <button type="submit" style={{ padding: "8px 12px" }}>
                    Login
                </button>
            </form>

            {sessionMessage && (
                <p style={{ color: "red" }}>
                    <strong>{sessionMessage}</strong>
                </p>
            )}

            {message && (
                <p>
                    <strong>{message}</strong>
                </p>
            )}
        </div>
    );
}

export default LoginPage;