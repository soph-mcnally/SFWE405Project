// =========================
// @Author: Karri Fox
// @Description: Page for users to manage their account info (name, email, password, etc.)
// =========================
import React, { useEffect, useState } from "react";
import { Link, redirect } from "react-router-dom";
import colors from "../styles/colors";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";
import {
    fullPageStyle,
    dashboardCardStyle,
    fullWidthInputStyle
} from "../styles/sharedStyles";

function ManageUserAccountPage() {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");
    const credentialsId = localStorage.getItem("credentialsId");
    const personId = localStorage.getItem("personId");
    const navigate = useNavigate();

    const [account, setAccount] = useState({
        userName: "",
        email: "",
        password: "", // keep empty, but treat as optional
        accountStatus: "ACTIVE"
    });

    const [person, setPerson] = useState({
        firstName: "",
        lastName: "",
        personType: "",
        degreeLevel: ""
    });

    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState("");

    // *************************
    // FETCH DATA
    // *************************
    useEffect(() => {
        fetchAccount();
        fetchPerson();
    }, [credentialsId, personId]);

    const fetchAccount = async () => {
        try {
            const res = await fetch(`/api/manageUserAccounts/${credentialsId}`, {
                headers: { Authorization: `Bearer ${token}` },
                cache: "no-store"
            });

            if (res.status === 304) {
                console.warn("Using cached account data");
                return;
            }

            if (!res.ok) {
                const errorText = await res.text();
                console.error("Server error:", errorText);
                throw new Error("Failed to fetch account");
            }

            const data = await res.json();
            setAccount(data);

        } catch (err) {
            console.error("Error fetching user account:", err);
            setMessage("Error fetching user account.");
        }
    };

    const fetchPerson = async () => {
        try {
            const res = await fetch(`/api/manageUserAccounts/people/${personId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });

            if (res.status === 304) {
                console.warn("Using cached person data");
                return;
            }

            if (!res.ok) {
                const errorText = await res.text();
                throw new Error(errorText);
            }

            const data = await res.json();
            setPerson(data);

        } catch (err) {
            console.error("Error fetching person:", err);
            setMessage("Error fetching person.");
        } finally {
            setLoading(false);
        }
    };

    // *************************
    // HANDLE INPUT CHANGES
    // *************************
    const handleAccountChange = (e) => {
        setAccount({ ...account, [e.target.name]: e.target.value });
    };

    const handlePersonChange = (e) => {
        setPerson({ ...person, [e.target.name]: e.target.value });
    };

    // *************************
    // SUBMIT BOTH
    // *************************
    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            firstName: person.firstName,
            lastName: person.lastName,
            email: account.email,
            userName: account.userName,
            accountStatus: account.accountStatus
        };

        if(account.password && account.password.trim() !== "") {
            payload.password = account.password;
        }

        const res = await fetch(`/api/manageUserAccounts/profile/${personId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify(payload)
        });

        const data = await res.json();
        console.log("Updated profile:", data);

        if (res.ok) {
            setMessage("Profile updated successfully!");

            //update localStorage email if it was changed
            localStorage.setItem("email", account.email);

            // Clear password field after successful update
            setAccount(prev => ({ ...prev, password: "" }));

            //navigate("/home");
        } else {
            setMessage("Error updating profile: " + (data.message || res.statusText));
        }
    };

    if (loading) return <p>Loading...</p>;

    return (
        <div style={fullPageStyle}>
            <section style={dashboardStyle}>
                <h1 style={dashboardTitleStyle}>Manage User Account</h1>

                <form onSubmit={handleSubmit} style={{ textAlign: "left" }}>

                    <h3>Personal Info</h3>
                    <input
                        name="firstName"
                        value={person.firstName}
                        onChange={handlePersonChange}
                        placeholder="First Name"
                        style={inputStyle}
                    />

                    <input
                        name="lastName"
                        value={person.lastName}
                        onChange={handlePersonChange}
                        placeholder="Last Name"
                        style={inputStyle}
                    />

                    <h3>Account Info</h3>

                    <input
                        name="userName"
                        value={account.userName}
                        onChange={handleAccountChange}
                        placeholder="Username"
                        style={inputStyle}
                    />

                    <input
                        type="password"
                        name="password"
                        value={account.password}
                        onChange={handleAccountChange}
                        placeholder="New Password"
                        autoComplete="new-password"
                        style={inputStyle}
                    />

                    <input
                        name="email"
                        value={account.email}
                        onChange={handleAccountChange}
                        placeholder="Email"
                        style={inputStyle}
                    />

                    <select
                        name="accountStatus"
                        value={account.accountStatus}
                        onChange={handleAccountChange}
                        style={inputStyle}
                    >
                        <option value="ACTIVE">ACTIVE</option>
                        <option value="INACTIVE">INACTIVE</option>
                        <option value="SUSPENDED">SUSPENDED</option>
                    </select>

                    {/* Role-based logic example */}
                    {role === "FACULTY" && (
                        <div>
                            <h4>Faculty Controls</h4>
                            {/* Example placeholder */}
                            <input
                                placeholder="Grade (future feature)"
                                style={inputStyle}
                            />
                        </div>
                    )}

                    <Button
                        type="submit"
                        variant="cardinal"
                        fullWidth
                        style={{ padding: "12px", borderRadius: "8px" }}
                    >
                        Save Changes to User Account
                    </Button>

                    {message && <p>{message}</p>}
                </form>
            </section>
        </div>
    );
}

// *************************
// STYLES
// *************************
const dashboardStyle = {
    ...dashboardCardStyle,
    maxWidth: "700px",
    margin: "0 auto"
};

const dashboardTitleStyle = {
    marginBottom: "20px",
    color: colors.navyBlue
};

const inputStyle = {
    ...fullWidthInputStyle,
    padding: "10px",
    marginBottom: "12px"
};

export default ManageUserAccountPage;