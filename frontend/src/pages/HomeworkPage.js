/*
 * HomeworkPage.js
 *Last Update: 2026-04-28
 * 
 * Primary Author: @TravisPotter
 * Secondary Author: @N/A
 * 
 * 
 */

import React, { useState, useEffect, useCallback } from "react";
import colors from "../styles/colors";
import {
    getHomeworkForCourse,
    getFacultyHomeworkForCourse,
    createAssignment,
    deleteAssignment
} from "../services/homeworkService";

// ── helpers ──────────────────────────────────────────────────────────────────

function formatDate(dateString) {
    if (!dateString) return "No date";
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", { month: "long", day: "numeric", year: "numeric" });
}

// ── styles (matching existing pages) ─────────────────────────────────────────

const pageStyle = {
    minHeight: "calc(100vh - 64px)",
    backgroundColor: colors.lightGray,
    padding: "40px 20px"
};

const containerStyle = {
    maxWidth: "860px",
    margin: "0 auto"
};

const headerCardStyle = {
    backgroundColor: colors.white,
    padding: "28px 32px",
    borderRadius: "12px",
    borderTop: `6px solid ${colors.cardinalRed}`,
    border: `1px solid ${colors.borderGray}`,
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
    marginBottom: "28px",
    textAlign: "center"
};

const cardStyle = {
    backgroundColor: colors.white,
    padding: "24px",
    borderRadius: "12px",
    border: `1px solid ${colors.borderGray}`,
    boxShadow: "0 2px 8px rgba(0,0,0,0.08)",
    marginBottom: "24px"
};

const sectionTitleStyle = {
    color: colors.navyBlue,
    marginTop: 0,
    marginBottom: "16px",
    fontSize: "18px"
};

const selectStyle = {
    padding: "8px 12px",
    border: `1px solid ${colors.borderGray}`,
    borderRadius: "6px",
    fontSize: "14px",
    width: "100%",
    marginTop: "6px"
};

const inputStyle = {
    padding: "8px 12px",
    border: `1px solid ${colors.borderGray}`,
    borderRadius: "6px",
    fontSize: "14px",
    width: "100%",
    marginTop: "6px",
    boxSizing: "border-box"
};

const labelStyle = {
    display: "block",
    fontWeight: "bold",
    color: colors.navyBlue,
    marginBottom: "12px"
};

const btnPrimary = {
    backgroundColor: colors.cardinalRed,
    color: colors.white,
    border: "none",
    padding: "9px 20px",
    borderRadius: "6px",
    fontWeight: "bold",
    cursor: "pointer",
    fontSize: "14px"
};

const btnDanger = {
    backgroundColor: "transparent",
    color: colors.cardinalRed,
    border: `1px solid ${colors.cardinalRed}`,
    padding: "5px 12px",
    borderRadius: "6px",
    fontWeight: "bold",
    cursor: "pointer",
    fontSize: "13px"
};

const assignmentCardStyle = {
    padding: "14px 16px",
    marginTop: "12px",
    borderRadius: "8px",
    backgroundColor: colors.lightGray,
    borderLeft: `6px solid ${colors.navyBlue}`,
    border: `1px solid ${colors.borderGray}`,
    borderLeftWidth: "6px",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "flex-start"
};

const placeholderStyle = {
    marginTop: "16px",
    padding: "28px",
    borderRadius: "8px",
    border: `2px dashed ${colors.cardinalRed}`,
    textAlign: "center",
    color: colors.navyBlue,
    backgroundColor: colors.lightGray
};

const toastBase = {
    position: "fixed",
    top: "90px",
    left: "50%",
    transform: "translateX(-50%)",
    padding: "12px 24px",
    borderRadius: "10px",
    fontWeight: "bold",
    boxShadow: "0 4px 12px rgba(0,0,0,0.2)",
    zIndex: 1000,
    display: "flex",
    alignItems: "center",
    gap: "10px"
};

// ── sub-components ────────────────────────────────────────────────────────────

function Toast({ message, type }) {
    if (!message) return null;
    const isError = type === "error";
    return (
        <div style={{
            ...toastBase,
            backgroundColor: colors.white,
            color: isError ? colors.cardinalRed : "#2e7d32",
            border: `2px solid ${isError ? colors.cardinalRed : "#2e7d32"}`
        }}>
            <span>{isError ? "✕" : "✓"}</span>
            {message}
        </div>
    );
}

function AssignmentItem({ assignment, isFaculty, onDelete, courseId }) {
    return (
        <div style={assignmentCardStyle}>
            <div>
                <strong style={{ color: colors.cardinalRed, fontSize: "15px" }}>
                    {assignment.assignmentName}
                </strong>
                {assignment.course && (
                    <p style={{ margin: "4px 0 0", color: colors.navyBlue, fontWeight: "bold", fontSize: "13px" }}>
                        {assignment.course.courseCode} – {assignment.course.courseName}
                    </p>
                )}
                {assignment.description && (
                    <p style={{ margin: "4px 0 0", color: "#555", fontSize: "13px" }}>
                        {assignment.description}
                    </p>
                )}
                <p style={{ margin: "4px 0 0", color: "#666", fontSize: "13px" }}>
                    Due: {formatDate(assignment.dueDate)}
                </p>
            </div>
            {isFaculty && (
                <button
                    style={btnDanger}
                    onClick={() => onDelete(courseId, assignment.homeworkAssignmentsID)}
                >
                    Delete
                </button>
            )}
        </div>
    );
}

// ── STUDENT VIEW ──────────────────────────────────────────────────────────────

function StudentView({ token }) {
    const [courses, setCourses] = useState([]);
    const [selectedCourse, setSelectedCourse] = useState("");
    const [assignments, setAssignments] = useState([]);
    const [loading, setLoading] = useState(false);
    const [toast, setToast] = useState(null);

    const showToast = (message, type = "error") => {
        setToast({ message, type });
        setTimeout(() => setToast(null), 3000);
    };

    // Load enrolled courses so student can pick one
    useEffect(() => {
        fetch("http://localhost:8080/api/enrollment/my-courses", {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(r => r.ok ? r.json() : Promise.reject(r.status))
            .then(setCourses)
            .catch(() => showToast("Failed to load your enrolled courses"));
    }, [token]);

    const handleCourseChange = useCallback(async (e) => {
        const courseId = e.target.value;
        setSelectedCourse(courseId);
        setAssignments([]);
        if (!courseId) return;

        setLoading(true);
        try {
            const data = await getHomeworkForCourse(token, courseId);
            setAssignments(data);
        } catch {
            showToast("Failed to load assignments for this course");
        } finally {
            setLoading(false);
        }
    }, [token]);

    const sorted = [...assignments].sort((a, b) => new Date(a.dueDate) - new Date(b.dueDate));

    return (
        <>
            {toast && <Toast message={toast.message} type={toast.type} />}

            <div style={cardStyle}>
                <h2 style={sectionTitleStyle}>View Homework by Course</h2>
                <label style={labelStyle}>
                    Select a Course
                    <select value={selectedCourse} onChange={handleCourseChange} style={selectStyle}>
                        <option value="">-- Choose a course --</option>
                        {courses.map(c => (
                            <option key={c.courseId} value={c.courseId}>
                                {c.courseCode} – {c.courseName}
                            </option>
                        ))}
                    </select>
                </label>

                {loading && <p style={{ color: "#666" }}>Loading assignments…</p>}

                {!loading && selectedCourse && (
                    sorted.length > 0 ? (
                        sorted.map(a => (
                            <AssignmentItem
                                key={a.homeworkAssignmentsID}
                                assignment={a}
                                isFaculty={false}
                            />
                        ))
                    ) : (
                        <div style={placeholderStyle}>No assignments found for this course.</div>
                    )
                )}

                {!selectedCourse && (
                    <div style={placeholderStyle}>Select a course to see its assignments.</div>
                )}
            </div>
        </>
    );
}

// ── FACULTY VIEW ──────────────────────────────────────────────────────────────

function FacultyView({ token }) {
    const [courses, setCourses] = useState([]);
    const [selectedCourse, setSelectedCourse] = useState("");
    const [assignments, setAssignments] = useState([]);
    const [loading, setLoading] = useState(false);
    const [toast, setToast] = useState(null);

    // Create-form state
    const [form, setForm] = useState({ assignmentName: "", description: "", dueDate: "" });
    const [creating, setCreating] = useState(false);
    const [showForm, setShowForm] = useState(false);

    const showToast = (message, type = "error") => {
        setToast({ message, type });
        setTimeout(() => setToast(null), 3500);
    };

    // Load faculty's associated courses
    useEffect(() => {
        fetch("http://localhost:8080/api/enrollment/my-assigned-courses", {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(r => r.ok ? r.json() : Promise.reject(r.status))
            .then(setCourses)
            .catch(() => showToast("Failed to load your courses"));
    }, [token]);

    const loadAssignments = useCallback(async (courseId) => {
        if (!courseId) return;
        setLoading(true);
        try {
            const data = await getFacultyHomeworkForCourse(token, courseId);
            setAssignments(data);
        } catch {
            showToast("Failed to load assignments");
        } finally {
            setLoading(false);
        }
    }, [token]);

    const handleCourseChange = (e) => {
        const courseId = e.target.value;
        setSelectedCourse(courseId);
        setAssignments([]);
        setShowForm(false);
        loadAssignments(courseId);
    };

    const handleCreate = async () => {
        if (!form.assignmentName.trim()) {
            showToast("Assignment name is required");
            return;
        }
        if (!form.dueDate) {
            showToast("Due date is required");
            return;
        }
        setCreating(true);
        try {
            const created = await createAssignment(token, selectedCourse, form);
            setAssignments(prev => [...prev, created]);
            setForm({ assignmentName: "", description: "", dueDate: "" });
            setShowForm(false);
            showToast("Assignment created!", "success");
        } catch {
            showToast("Failed to create assignment");
        } finally {
            setCreating(false);
        }
    };

    const handleDelete = async (courseId, asgnId) => {
        if (!window.confirm("Delete this assignment? This cannot be undone.")) return;
        try {
            await deleteAssignment(token, courseId, asgnId);
            setAssignments(prev => prev.filter(a => a.homeworkAssignmentsID !== asgnId));
            showToast("Assignment deleted.", "success");
        } catch {
            showToast("Failed to delete assignment");
        }
    };

    return (
        <>
            {toast && <Toast message={toast.message} type={toast.type} />}

            {/* Course selector */}
            <div style={cardStyle}>
                <h2 style={sectionTitleStyle}>Manage Homework Assignments</h2>
                <label style={labelStyle}>
                    Select a Course
                    <select value={selectedCourse} onChange={handleCourseChange} style={selectStyle}>
                        <option value="">-- Choose a course --</option>
                        {courses.map(c => (
                            <option key={c.courseId} value={c.courseId}>
                                {c.courseCode} – {c.courseName}
                            </option>
                        ))}
                    </select>
                </label>
            </div>

            {/* Assignment list + create button */}
            {selectedCourse && (
                <div style={cardStyle}>
                    <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "8px" }}>
                        <h2 style={{ ...sectionTitleStyle, marginBottom: 0 }}>Assignments</h2>
                        <button style={btnPrimary} onClick={() => setShowForm(f => !f)}>
                            {showForm ? "Cancel" : "+ New Assignment"}
                        </button>
                    </div>

                    {/* Create form */}
                    {showForm && (
                        <div style={{
                            backgroundColor: "#f9f0f2",
                            border: `1px solid ${colors.borderGray}`,
                            borderRadius: "8px",
                            padding: "20px",
                            marginTop: "16px",
                            marginBottom: "8px"
                        }}>
                            <h3 style={{ color: colors.navyBlue, marginTop: 0 }}>Create New Assignment</h3>

                            <label style={labelStyle}>
                                Assignment Name *
                                <input
                                    type="text"
                                    style={inputStyle}
                                    value={form.assignmentName}
                                    onChange={e => setForm(f => ({ ...f, assignmentName: e.target.value }))}
                                    placeholder="e.g. Midterm Essay"
                                />
                            </label>

                            <label style={labelStyle}>
                                Description
                                <textarea
                                    rows={3}
                                    style={{ ...inputStyle, resize: "vertical" }}
                                    value={form.description}
                                    onChange={e => setForm(f => ({ ...f, description: e.target.value }))}
                                    placeholder="Optional instructions or details"
                                />
                            </label>

                            <label style={labelStyle}>
                                Due Date *
                                <input
                                    type="date"
                                    style={inputStyle}
                                    value={form.dueDate}
                                    onChange={e => setForm(f => ({ ...f, dueDate: e.target.value }))}
                                />
                            </label>

                            <button
                                style={{ ...btnPrimary, marginTop: "8px" }}
                                onClick={handleCreate}
                                disabled={creating}
                            >
                                {creating ? "Creating…" : "Create Assignment"}
                            </button>
                        </div>
                    )}

                    {/* Assignment list */}
                    {loading && <p style={{ color: "#666" }}>Loading…</p>}

                    {!loading && assignments.length > 0 ? (
                        assignments.map(a => (
                            <AssignmentItem
                                key={a.homeworkAssignmentsID}
                                assignment={a}
                                isFaculty={true}
                                onDelete={handleDelete}
                                courseId={selectedCourse}
                            />
                        ))
                    ) : (
                        !loading && (
                            <div style={placeholderStyle}>
                                No assignments yet. Click "+ New Assignment" to add one.
                            </div>
                        )
                    )}
                </div>
            )}
        </>
    );
}

// ── MAIN PAGE ─────────────────────────────────────────────────────────────────

function HomeworkPage() {
    const token = localStorage.getItem("token");
    const role = (localStorage.getItem("role") || "student").toLowerCase();
    const isFaculty = role === "faculty";

    return (
        <div style={pageStyle}>
            <div style={containerStyle}>
                <div style={headerCardStyle}>
                    <h1 style={{ color: colors.navyBlue, margin: 0 }}>
                        {isFaculty ? "Manage Homework" : "My Homework"}
                    </h1>
                    <p style={{ color: "#666", marginTop: "8px", marginBottom: 0 }}>
                        {isFaculty
                            ? "Create and delete homework assignments for your courses."
                            : "View homework assignments for your enrolled courses."}
                    </p>
                </div>

                {isFaculty
                    ? <FacultyView token={token} />
                    : <StudentView token={token} />
                }
            </div>
        </div>
    );
}

export default HomeworkPage;