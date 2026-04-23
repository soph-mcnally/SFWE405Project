import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function EnrollCoursesPage() {
    const [semesters, setSemesters] = useState([]);
    const [selectedSemester, setSelectedSemester] = useState("");
    const [courses, setCourses] = useState([]);
    const [selectedCourses, setSelectedCourses] = useState([]);
    const [message, setMessage] = useState("");

    const navigate = useNavigate();
    const token = localStorage.getItem("token");

    console.log("TOKEN:", token);

    useEffect(() => {
        fetchSemesters();
    }, []);

    const fetchSemesters = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/enrollment/semesters", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            console.log("Status:", response.status);

            if (!response.ok) {
                const errorText = await response.text();
                console.log("Backend error:", errorText);
                setMessage(`Error: ${response.status}`);
                return;
            }

            const data = await response.json();
            console.log("Semesters:", data);
            setSemesters(data);
        } catch (error) {
            console.error("Fetch error:", error);
            setMessage("Failed to load semesters");
        }
    };

    const fetchCourses = async (semesterId) => {
        try {
            const response = await fetch(
                `http://localhost:8080/api/enrollment/semesters/${semesterId}/courses`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            if (!response.ok) {
                const errorText = await response.text();
                console.log("Backend error:", errorText);
                setMessage(`Error loading courses: ${response.status}`);
                return;
            }

            const data = await response.json();
            setCourses(data);
        } catch (error) {
            console.error(error);
            setMessage("Failed to load courses");
        }
    };

    const handleSemesterChange = (e) => {
        const semesterId = e.target.value;
        setSelectedSemester(semesterId);
        setSelectedCourses([]);
        fetchCourses(semesterId);
    };

    const handleCourseSelect = (courseId) => {
        if (selectedCourses.includes(courseId)) {
            setSelectedCourses(selectedCourses.filter((id) => id !== courseId));
        } else {
            setSelectedCourses([...selectedCourses, courseId]);
        }
    };

    const handleEnroll = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/enrollment", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify({
                    courseIds: selectedCourses
                })
            });

            if (!response.ok) {
                const errorText = await response.text();
                console.log("Backend error:", errorText);
                setMessage(`Enrollment failed: ${response.status}`);
                return;
            }

            const data = await response.json();
            console.log("Enrollment response:", data);
            setMessage("Enrollment successful!");
        } catch (error) {
            console.error(error);
            setMessage("Enrollment failed");
        }
    };

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };

    return (
        <div style={{ padding: "20px" }}>
            <h1>Enroll in Courses</h1>

            <button onClick={handleLogout} style={{ marginBottom: "20px", padding: "8px 12px" }}>
                Logout
            </button>

            <h3>Select Semester</h3>
            <select value={selectedSemester} onChange={handleSemesterChange}>
                <option value="">-- Select Semester --</option>
                {semesters.map((semester) => (
                    <option key={semester.id} value={semester.id}>
                        {semester.season} {semester.semesterYear}
                    </option>
                ))}
            </select>

            <h3>Available Courses</h3>
            {courses.length === 0 ? (
                <p>No courses loaded</p>
            ) : (
                courses.map((course) => (
                    <div key={course.courseId}>
                        <input
                            type="checkbox"
                            checked={selectedCourses.includes(course.courseId)}
                            onChange={() => handleCourseSelect(course.courseId)}
                        />
                        {course.courseCode} - {course.courseName}
                    </div>
                ))
            )}

            <button
                onClick={handleEnroll}
                style={{ marginTop: "20px", padding: "8px 12px" }}
                disabled={selectedCourses.length === 0}
            >
                Enroll
            </button>

            {message && <p><strong>{message}</strong></p>}
        </div>
    );
}

export default EnrollCoursesPage;