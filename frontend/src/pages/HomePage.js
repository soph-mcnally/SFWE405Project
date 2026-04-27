import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import colors from "../styles/colors";

function HomePage() {
    const userRole = localStorage.getItem("role") || "student";
    const token = localStorage.getItem("token");

    const currentSemester = getCurrentSemester();

    const [enrolledCourses, setEnrolledCourses] = useState([]);

    useEffect(() => {
        fetchEnrolledCourses();
    }, []);

    const fetchEnrolledCourses = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/enrollment/my-courses", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (!response.ok) {
                console.error(`Error loading enrolled courses: ${response.status}`);
                return;
            }

            const data = await response.json();
            setEnrolledCourses(data);
        } catch (error) {
            console.error("Failed to load enrolled courses", error);
        }
    };

    const currentSemesterCourses = enrolledCourses.filter(course => {
        return course.semester?.toLowerCase() === currentSemester.toLowerCase();
    });

    const totalCurrentUnits = currentSemesterCourses.reduce((total, course) => {
        return total + (course.unitsAmount || 0);
    }, 0);

    return (
        <div style={pageStyle}>
            <section style={dashboardStyle}>
                <h1 style={dashboardTitleStyle}>Dashboard</h1>

                <p style={semesterStyle}>
                    Current Semester: <strong>{currentSemester}</strong>
                </p>
            </section>

            {userRole.toLowerCase() === "student" && (
                <section style={contentGridStyle}>
                    <div style={cardStyle}>
                        <h2 style={cardTitleStyle}>Enrolled Courses</h2>

                        <p style={{ ...subTextStyle, fontSize: "14px" }}>
                            Showing courses for <strong>{currentSemester}</strong>
                        </p>

                        <div style={unitsBadgeStyle}>
                            Total Units: {totalCurrentUnits}
                        </div>

                        {currentSemesterCourses.length > 0 ? (
                            currentSemesterCourses.map(course => (
                                <div key={course.courseId} style={courseItemStyle}>
                                    <strong style={courseCodeStyle}>
                                        {course.courseCode}
                                    </strong>

                                    <p style={courseNameStyle}>
                                        {course.courseName}
                                    </p>

                                    <p style={courseUnitsStyle}>
                                        {course.unitsAmount} units
                                    </p>
                                </div>
                            ))
                        ) : (
                            <p>No enrolled courses for this semester.</p>
                        )}
                    </div>

                    <div style={cardStyle}>
                        <h2 style={cardTitleStyle}>Upcoming Homework</h2>

                        <p style={subTextStyle}>
                            Homework assignments will display here once implemented.
                        </p>

                        <div style={placeholderStyle}>
                            Upcoming homework layout placeholder
                        </div>
                    </div>
                </section>
            )}
        </div>
    );
}

function getCurrentSemester() {
    const today = new Date();
    const month = today.getMonth() + 1;
    const year = today.getFullYear();

    if (month >= 1 && month <= 5) {
        return `Spring ${year}`;
    }

    if (month >= 8 && month <= 12) {
        return `Fall ${year}`;
    }

    return `No active semester`;
}

const pageStyle = {
    minHeight: "100vh",
    backgroundColor: colors.lightGray,
    padding: "32px"
};

const dashboardStyle = {
    maxWidth: "700px",
    margin: "0 auto",
    textAlign: "center",
    backgroundColor: colors.white,
    padding: "32px",
    borderRadius: "12px",
    borderTop: `6px solid ${colors.cardinalRed}`,
    borderLeft: `1px solid ${colors.borderGray}`,
    borderRight: `1px solid ${colors.borderGray}`,
    borderBottom: `1px solid ${colors.borderGray}`,
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
};

const dashboardTitleStyle = {
    marginBottom: "10px",
    color: colors.navyBlue
};

const semesterStyle = {
    fontSize: "18px",
    marginBottom: "0",
    color: colors.black
};

const contentGridStyle = {
    maxWidth: "1100px",
    margin: "32px auto 0",
    display: "grid",
    gridTemplateColumns: "1fr 1fr",
    gap: "24px"
};

const cardStyle = {
    backgroundColor: colors.white,
    padding: "24px",
    borderRadius: "12px",
    border: `1px solid ${colors.borderGray}`,
    boxShadow: "0 2px 8px rgba(0,0,0,0.08)"
};

const cardTitleStyle = {
    color: colors.navyBlue,
    marginTop: 0,
    marginBottom: "8px"
};

const subTextStyle = {
    color: "#666",
    marginTop: "-4px"
};

const unitsBadgeStyle = {
    display: "inline-block",
    backgroundColor: colors.cardinalRed,
    color: colors.white,
    padding: "8px 12px",
    borderRadius: "20px",
    fontWeight: "bold",
    marginTop: "8px",
    marginBottom: "12px"
};

const courseItemStyle = {
    padding: "12px",
    marginTop: "12px",
    borderRadius: "8px",
    backgroundColor: colors.lightGray,
    borderLeft: `6px solid ${colors.cardinalRed}`,
    borderTop: `1px solid ${colors.borderGray}`,
    borderRight: `1px solid ${colors.borderGray}`,
    borderBottom: `1px solid ${colors.borderGray}`
};

const courseCodeStyle = {
    color: colors.cardinalRed,
    fontSize: "16px"
};

const courseNameStyle = {
    margin: "4px 0 0",
    color: colors.navyBlue,
    fontWeight: "bold"
};

const courseUnitsStyle = {
    margin: "4px 0 0",
    color: "#666"
};

const placeholderStyle = {
    marginTop: "16px",
    padding: "32px",
    borderRadius: "8px",
    border: `2px dashed ${colors.cardinalRed}`,
    textAlign: "center",
    color: colors.navyBlue,
    backgroundColor: colors.lightGray
};

export default HomePage;