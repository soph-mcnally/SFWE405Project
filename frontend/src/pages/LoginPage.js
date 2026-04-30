import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import Button from "../components/Button";
import colors from "../styles/colors";

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
            localStorage.setItem("role", data.role);
            localStorage.setItem("expiresAt", data.expiresAt);
            localStorage.setItem("personId", data.personId);
            localStorage.setItem("credentialsId", data.credentialsId);
            localStorage.setItem("userEmail", data.email);

            setMessage("Login successful!");

            navigate("/home");
        } catch (error) {
            console.error("Login error:", error);
            setMessage("Login failed. Check credentials.");
        }
    };

    return (
        <div style={pageStyle}>
            <section style={loginCardStyle}>
                <h1 style={titleStyle}>Course Management System</h1>

                <p style={subtitleStyle}>
                    Log in to access your dashboard.
                </p>

                {sessionMessage && (
                    <div style={errorMessageStyle}>
                        <strong>{sessionMessage}</strong>
                    </div>
                )}

                <form onSubmit={handleLogin} style={formStyle}>
                    <label style={labelStyle}>
                        Username or Email
                        <input
                            type="text"
                            placeholder="Enter username or email"
                            value={usernameOrEmail}
                            onChange={(e) => setUsernameOrEmail(e.target.value)}
                            style={inputStyle}
                        />
                    </label>

                    <label style={labelStyle}>
                        Password
                        <input
                            type="password"
                            placeholder="Enter password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            style={inputStyle}
                        />
                    </label>

                    <Button
                        type="submit"
                        variant="cardinal"
                        fullWidth
                        style={{ marginTop: "8px", borderRadius: "8px" }}
                    >
                        Login
                    </Button>
                </form>

                {message && (
                    <p
                        style={{
                            ...messageStyle,
                            color: message.includes("successful")
                                ? "#2e7d32"
                                : colors.cardinalRed
                        }}
                    >
                        <strong>{message}</strong>
                    </p>
                )}
            </section>
        </div>
    );
}

const pageStyle = {
    minHeight: "calc(100vh - 64px)",
    backgroundColor: colors.lightGray,
    padding: "40px 20px",
    display: "flex",
    justifyContent: "center",
    alignItems: "flex-start"
};

const loginCardStyle = {
    width: "100%",
    maxWidth: "420px",
    backgroundColor: colors.white,
    padding: "32px",
    borderRadius: "12px",
    borderTop: `6px solid ${colors.cardinalRed}`,
    borderLeft: `1px solid ${colors.borderGray}`,
    borderRight: `1px solid ${colors.borderGray}`,
    borderBottom: `1px solid ${colors.borderGray}`,
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
};

const titleStyle = {
    color: colors.navyBlue,
    textAlign: "center",
    marginTop: 0,
    marginBottom: "8px"
};

const subtitleStyle = {
    color: "#666",
    textAlign: "center",
    marginTop: 0,
    marginBottom: "24px"
};

const formStyle = {
    display: "flex",
    flexDirection: "column",
    gap: "14px"
};

const labelStyle = {
    display: "block",
    fontWeight: "bold",
    color: colors.navyBlue
};

const inputStyle = {
    width: "100%",
    padding: "10px",
    marginTop: "6px",
    borderRadius: "6px",
    border: `1px solid ${colors.borderGray}`,
    boxSizing: "border-box",
    fontSize: "14px"
};

const errorMessageStyle = {
    backgroundColor: "#f9f0f2",
    color: colors.cardinalRed,
    border: `1px solid ${colors.cardinalRed}`,
    borderRadius: "8px",
    padding: "12px",
    textAlign: "center",
    marginBottom: "18px"
};

const messageStyle = {
    textAlign: "center",
    marginTop: "18px",
    marginBottom: 0
};

export default LoginPage;