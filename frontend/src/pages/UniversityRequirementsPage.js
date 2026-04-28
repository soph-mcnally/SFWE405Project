import React, { useState, useEffect } from "react";

function UniversityRequirementsPage() {
    const [requirements, setRequirements] = useState([]);
    const [universities, setUniversities] = useState([]);
    const [newReq, setNewReq] = useState({ requirementDescription: "", universityId: "", category: "" });

    // Retrieve the token and role saved during login
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    useEffect(() => {
        // Only attempt to fetch data if the user has the correct role
        if (role === "ADMIN" || role === "FACULTY") {
            fetchRequirements();
            fetchUniversities();
        }
    }, [role, token]);

    const fetchRequirements = async () => {
        try {
            const res = await fetch("http://localhost:8080/api/university-requirements", {
                headers: { Authorization: `Bearer ${token}` }
            });
            const data = await res.json();
            setRequirements(data);
        } catch (err) {
            console.error("Failed to fetch requirements", err);
        }
    };

    const fetchUniversities = async () => {
        try {
            const res = await fetch("http://localhost:8080/universities", {
                headers: { Authorization: `Bearer ${token}` }
            });
            const data = await res.json();
            setUniversities(data);
        } catch (err) {
            console.error("Failed to fetch universities", err);
        }
    };

    const handleAddRequirement = async (e) => {
        e.preventDefault();
        const payload = {
            requirementDescription: newReq.requirementDescription,
            category: parseInt(newReq.category),
            university: { universityId: parseInt(newReq.universityId) }
        };

        await fetch("http://localhost:8080/api/university-requirements", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify(payload)
        });
        setNewReq({ requirementDescription: "", universityId: "", category: "" });
        fetchRequirements();
    };


    if (role !== "ADMIN" && role !== "FACULTY") {
        return (
            <div style={{ padding: "50px", textAlign: "center", border: "2px solid red", margin: "20px" }}>
                <h1 style={{ color: "red" }}>403 - Forbidden</h1>
                <p>Access Denied: You do not have permission to manage university requirements.</p>
                <p>Current Role: <strong>{role || "None"}</strong></p>
                <button onClick={() => window.history.back()}>Go Back</button>
            </div>
        );
    }

    return (
        <div style={{ padding: "20px" }}>
            <h2>University Requirements Management</h2>
            <p>Logged in as: <strong>{role}</strong></p>

            <form onSubmit={handleAddRequirement} style={{ marginBottom: "30px", border: "1px solid #ccc", padding: "15px", borderRadius: "8px" }}>
                <h3>Add New Requirement</h3>
                <input
                    placeholder="Requirement Description"
                    value={newReq.requirementDescription}
                    onChange={(e) => setNewReq({...newReq, requirementDescription: e.target.value})}
                    style={{ display: "block", marginBottom: "10px", width: "98%", padding: "8px" }}
                    required
                />
                <select
                    value={newReq.universityId}
                    onChange={(e) => setNewReq({...newReq, universityId: e.target.value})}
                    style={{ display: "block", marginBottom: "10px", padding: "8px", width: "99%" }}
                    required
                >
                    <option value="">Select University</option>
                    {universities.map(u => (
                        <option key={u.universityId} value={u.universityId}>{u.name}</option>
                    ))}
                </select>
                <input
                    placeholder="Category ID (Number)"
                    type="number"
                    value={newReq.category}
                    onChange={(e) => setNewReq({...newReq, category: e.target.value})}
                    style={{ display: "block", marginBottom: "10px", padding: "8px", width: "98%" }}
                    required
                />
                <button type="submit" style={{ padding: "10px 20px", cursor: "pointer" }}>Save Requirement</button>
            </form>

            <h3>Existing Requirements</h3>
            <table style={{ width: "100%", borderCollapse: "collapse" }}>
                <thead>
                <tr style={{ borderBottom: "2px solid #333" }}>
                    <th style={{ textAlign: "left" }}>University</th>
                    <th style={{ textAlign: "left" }}>Description</th>
                    <th style={{ textAlign: "left" }}>Category</th>
                </tr>
                </thead>
                <tbody>
                {requirements.map((req) => (
                    <li key={req.requirementID} style={{ marginBottom: "10px" }}>
                        <strong>{req.university?.name || "Unknown University"}:</strong> {req.requirementDescription} (Category: {req.category})
                    </li>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default UniversityRequirementsPage;