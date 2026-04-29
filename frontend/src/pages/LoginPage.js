import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function LoginPage() {
    const [usernameOrEmail, setUsernameOrEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    usernameOrEmail,
                    password
                })
            });

            if (!response.ok) {
                throw new Error("Login failed");
            }

            const data = await response.json();

            localStorage.setItem("token", data.token);
            localStorage.setItem("role", data.role)
            localStorage.setItem("expiresAt", data.expiresAt);
            localStorage.setItem("personId", data.personId);
            localStorage.setItem("credentialsId", data.credentialsId);
            localStorage.setItem("role", data.role);
            localStorage.setItem("userEmail", data.email)

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

            {message && <p><strong>{message}</strong></p>}
        </div>
    );
}

export default LoginPage;