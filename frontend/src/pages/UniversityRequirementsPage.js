/*

Created By: Gavin Hernandez

Frontend page for University Requirements

 */

import React, { useState, useEffect } from "react";
import colors from "../styles/colors";
import Button from "../components/Button";
import {
    fullPageStyle,
    dashboardCardStyle,
    formCardStyle,
    inputStyle
} from "../styles/sharedStyles";

function UniversityRequirementsPage() {
    const [requirements, setRequirements] = useState([]);
    const [universities, setUniversities] = useState([]);
    const [newReq, setNewReq] = useState({ requirementDescription: "", universityId: "", category: "" });

    const [searchTerm, setSearchTerm] = useState("");

    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;

    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    useEffect(() => {
        if (role === "ADMIN" || role === "FACULTY") {
            fetchRequirements();
            fetchUniversities();
        }
    }, [role, token]);

    useEffect(() => {
        setCurrentPage(1);
    }, [searchTerm]);

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

        try {
            const response = await fetch("http://localhost:8080/api/university-requirements", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                const errorData = await response.json();
                console.error("Server Error Detail:", errorData);
                return;
            }

            setNewReq({ requirementDescription: "", universityId: "", category: "" });
            fetchRequirements();

        } catch (err) {
            console.error("Network error:", err);
        }
    };

    const handleDelete = async (requirementID) => {
        if (!window.confirm("Are you sure you want to delete this requirement?")) return;

        try {
            const response = await fetch(`http://localhost:8080/api/university-requirements/${requirementID}`, {
                method: "DELETE",
                headers: { Authorization: `Bearer ${token}` }
            });

            if (response.ok) {
                fetchRequirements();
            } else {
                alert("Failed to delete the requirement.");
            }
        } catch (err) {
            console.error("Delete error:", err);
        }
    };

    const filteredRequirements = requirements.filter(req => {
        const desc = req.requirementDescription?.toLowerCase() || "";
        const uniName = req.university?.name?.toLowerCase() || "";
        const cat = req.category?.toString() || "";
        const search = searchTerm.toLowerCase();

        return desc.includes(search) || uniName.includes(search) || cat.includes(search);
    });

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = filteredRequirements.slice(indexOfFirstItem, indexOfLastItem);
    const totalPages = Math.ceil(filteredRequirements.length / itemsPerPage);

    const nextPage = () => { if (currentPage < totalPages) setCurrentPage(currentPage + 1); };
    const prevPage = () => { if (currentPage > 1) setCurrentPage(currentPage - 1); };

    //Forbidden State
    if (role !== "ADMIN" && role !== "FACULTY") {
        return (
            <div style={fullPageStyle}>
                <div style={{ ...containerStyle, textAlign: "center" }}>
                    <h1 style={{ color: colors.cardinalRed }}>403 - Forbidden</h1>
                    <p>Access Denied: You do not have permission to manage university requirements.</p>
                    <Button
                        onClick={() => window.history.back()}
                        variant="primary"
                    >
                        Go Back
                    </Button>
                </div>
            </div>
        );
    }

    return (
        <div style={fullPageStyle}>
            <section style={containerStyle}>
                <h2 style={{ color: colors.navyBlue, marginTop: 0 }}>University Requirements Management</h2>
                <p style={{ color: "#666" }}>Logged in as: <strong>{role}</strong></p>

                <form onSubmit={handleAddRequirement} style={formStyle}>
                    <h3 style={{ color: colors.navyBlue }}>Add New Requirement</h3>
                    <input
                        placeholder="Requirement Description"
                        value={newReq.requirementDescription}
                        onChange={(e) => setNewReq({...newReq, requirementDescription: e.target.value})}
                        style={formInputStyle}
                        required
                    />
                    <select
                        value={newReq.universityId}
                        onChange={(e) => setNewReq({...newReq, universityId: e.target.value})}
                        style={formInputStyle}
                        required
                    >
                        <option value="">Select University</option>
                        {universities.map(u => (<option key={u.universityId} value={u.universityId}>{u.name}</option>))}
                    </select>
                    <input
                        placeholder="Category ID (Number)"
                        type="number"
                        value={newReq.category}
                        onChange={(e) => setNewReq({...newReq, category: e.target.value})}
                        style={formInputStyle}
                        required
                    />
                    <Button type="submit" variant="primary">
                        Save Requirement
                    </Button>
                </form>

                <h3 style={{ color: colors.navyBlue }}>Existing Requirements</h3>
                <input
                    type="text"
                    placeholder="Search by description, university, or category..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    style={{ ...formInputStyle, marginBottom: "20px", borderColor: colors.navyBlue }}
                />

                <div style={{ marginTop: "20px" }}>
                    {currentItems.length > 0 ? (
                        currentItems.map((req) => (
                            <div key={req.requirementID} style={listItemStyle}>
                                <div>
                                    <strong style={{ color: colors.cardinalRed }}>
                                        {req.university?.name || "Unknown University"}:
                                    </strong>
                                    <span style={{ marginLeft: "8px" }}>
                        {req.requirementDescription} (Category: {req.category})
                    </span>
                                </div>

                                <Button
                                    onClick={() => handleDelete(req.requirementID)}
                                    variant="danger"
                                    size="small"
                                    style={{ marginLeft: "10px" }}
                                >
                                    Delete
                                </Button>
                            </div>
                        ))
                    ) : (
                        <p style={{ textAlign: "center", color: "#666", fontStyle: "italic" }}>
                            No requirements match your search.
                        </p>
                    )}
                </div>

                {totalPages > 1 && (
                    <div style={{ display: "flex", justifyContent: "center", alignItems: "center", marginTop: "20px" }}>
                        <Button
                            onClick={prevPage}
                            disabled={currentPage === 1}
                            variant="pagination"
                        >
                            ‹
                        </Button>

                        <span style={{ margin: "0 15px", fontWeight: "bold", fontSize: "20px" }}>
                        Page {currentPage} of {totalPages}
                    </span>

                        <Button
                            onClick={nextPage}
                            disabled={currentPage === totalPages}
                            variant="pagination"
                        >
                            ›
                        </Button>
                    </div>
                )}
            </section>
        </div>
    );
}

const containerStyle = {
    ...dashboardCardStyle,
    maxWidth: "900px",
    margin: "0 auto"
};

const formStyle = {
    ...formCardStyle,
    marginBottom: "30px",
    backgroundColor: "#f9f9f9"
};

const formInputStyle = {
    ...inputStyle,
    display: "block",
    marginBottom: "12px",
    width: "98%",
    padding: "10px",
    borderRadius: "4px"
};

const listItemStyle = {
    padding: "12px",
    marginBottom: "8px",
    backgroundColor: colors.lightGray,
    borderRadius: "6px",
    borderLeft: `4px solid ${colors.navyBlue}`,
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center"
};

export default UniversityRequirementsPage;