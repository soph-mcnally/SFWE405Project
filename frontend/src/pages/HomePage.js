import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Button from "../components/Button";
import colors from "../styles/colors";

function HomePage() {
    const userRole = localStorage.getItem("role") || "student";
    const token = localStorage.getItem("token");

    const currentSemester = getCurrentSemester();

    const [enrolledCourses, setEnrolledCourses] = useState([]);
    const [homeworkAssignments, setHomeworkAssignments] = useState([]);

    const [teachingCourses, setTeachingCourses] = useState([]);

    useEffect(() => {
        if (userRole.toLowerCase() === "student") {
            fetchEnrolledCourses();
            fetchHomeworkAssignments();
        }

        if (userRole.toLowerCase() === "faculty") {
            fetchTeachingCourses();
        }
    }, []);

    const fetchEnrolledCourses = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/enrollment/my-courses", {
                headers: { Authorization: `Bearer ${token}` }
            });

            if (!response.ok) return;

            const data = await response.json();
            setEnrolledCourses(data);
        } catch (error) {
            console.error("Failed to load enrolled courses", error);
        }
    };

    const fetchHomeworkAssignments = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/homework-assignment/my-assignments", {
                headers: { Authorization: `Bearer ${token}` }
            });

            if (response.status === 204) {
                setHomeworkAssignments([]);
                return;
            }

            if (!response.ok) return;

            const data = await response.json();
            setHomeworkAssignments(data);
        } catch (error) {
            console.error("Failed to load homework assignments", error);
        }
    };

    const fetchTeachingCourses = async () => {
        try {
            const response = await fetch(
                "http://localhost:8080/api/enrollment/my-assigned-courses",
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            if (!response.ok) {
                console.error(`Error loading teaching courses: ${response.status}`);
                return;
            }

            const data = await response.json();

            console.log("Teaching courses:", data);

            setTeachingCourses(data);

        } catch (error) {
            console.error("Failed to load teaching courses", error);
        }
    };


    const currentSemesterCourses = enrolledCourses.filter(course =>
        course.semester?.toLowerCase() === currentSemester.toLowerCase()
    );

    const totalCurrentUnits = currentSemesterCourses.reduce((total, course) => {
        return total + (course.unitsAmount || 0);
    }, 0);

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const sevenDaysFromNow = new Date(today);
    sevenDaysFromNow.setDate(today.getDate() + 7);

    const sortedUpcomingHomeworkAssignments = homeworkAssignments
        .filter(assignment => {
            const dueDate = new Date(assignment.dueDate);
            dueDate.setHours(0, 0, 0, 0);
            return dueDate >= today && dueDate <= sevenDaysFromNow;
        })
        .sort((a, b) => new Date(a.dueDate) - new Date(b.dueDate));

    return (
        <div style={pageStyle}>
            <section style={dashboardStyle}>
                <h1 style={dashboardTitleStyle}>Welcome back!</h1>

                <p style={dashboardSubtitleStyle}>
                    Here is your {formatRole(userRole)} dashboard.
                </p>

                <p style={semesterStyle}>
                    Current Semester: <strong>{currentSemester}</strong>
                </p>
            </section>

            {userRole.toLowerCase() === "admin" && (
                <section style={{ maxWidth: "700px", margin: "32px auto 0", textAlign: "center" }}>
                    <h2 style={{ color: colors.navyBlue, marginBottom: "20px" }}>Admin Controls</h2>
                    <Link to="/manage-courses">
                        <Button variant="cardinal" size="large">
                            Manage Courses
                        </Button>
                    </Link>
                </section>
            )}

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
                                <CourseCard key={course.courseId} course={course} />
                            ))
                        ) : (
                            <p>No enrolled courses for this semester.</p>
                        )}
                    </div>

                    <div style={cardStyle}>
                        <h2 style={cardTitleStyle}>Upcoming Homework</h2>

                        <p style={{ ...subTextStyle, fontSize: "14px" }}>
                            {sortedUpcomingHomeworkAssignments.length} assignment
                            {sortedUpcomingHomeworkAssignments.length !== 1 ? "s" : ""} due within the next 7 days
                        </p>

                        {sortedUpcomingHomeworkAssignments.length > 0 ? (
                            sortedUpcomingHomeworkAssignments.map(assignment => (
                                <AssignmentCard
                                    key={assignment.homeworkAssignmentsID}
                                    assignment={assignment}
                                />
                            ))
                        ) : (
                            <div style={placeholderStyle}>
                                No assignments due within the next 7 days.
                            </div>
                        )}
                    </div>
                </section>
            )}

            {userRole.toLowerCase() === "faculty" && (
                <section style={singleCardGridStyle}>
                    <div style={cardStyle}>
                        <h2 style={cardTitleStyle}>Courses I’m Teaching</h2>

                        <p style={{ ...subTextStyle, fontSize: "14px" }}>
                            Teaching {teachingCourses.length} course
                            {teachingCourses.length !== 1 ? "s" : ""} this semester
                        </p>

                        {teachingCourses.length > 0 ? (
                            teachingCourses.map(course => (
                                <CourseCard key={course.courseId} course={course} />
                            ))
                        ) : (
                            <div style={placeholderStyle}>
                                No teaching courses found.
                            </div>
                        )}
                    </div>
                </section>
            )}
        </div>
    );
}

function CourseCard({ course }) {
    return (
        <div style={courseItemStyle}>
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
    );
}

function AssignmentCard({ assignment }) {
    return (
        <div style={homeworkItemStyle}>
            <strong style={homeworkTitleStyle}>
                {assignment.assignmentName}
            </strong>

            <p style={homeworkCourseStyle}>
                {assignment.course?.courseCode} - {assignment.course?.courseName}
            </p>

            <p style={homeworkDueDateStyle}>
                Due: {formatDate(assignment.dueDate)}
            </p>
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

function formatDate(dateString) {
    const date = new Date(dateString);

    return date.toLocaleDateString("en-US", {
        month: "long",
        day: "numeric",
        year: "numeric"
    });
}
function formatRole(role) {
    if (!role) {
        return "user";
    }

    return role.charAt(0).toUpperCase() + role.slice(1).toLowerCase();
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
    gridTemplateColumns: "repeat(auto-fit, minmax(320px, 1fr))",
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

const homeworkItemStyle = {
    padding: "12px",
    marginTop: "12px",
    borderRadius: "8px",
    backgroundColor: colors.lightGray,
    borderLeft: `6px solid ${colors.navyBlue}`,
    borderTop: `1px solid ${colors.borderGray}`,
    borderRight: `1px solid ${colors.borderGray}`,
    borderBottom: `1px solid ${colors.borderGray}`
};

const homeworkTitleStyle = {
    color: colors.cardinalRed,
    fontSize: "16px"
};

const homeworkCourseStyle = {
    margin: "4px 0 0",
    color: colors.navyBlue,
    fontWeight: "bold"
};

const homeworkDueDateStyle = {
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

const dashboardSubtitleStyle = {
    fontSize: "16px",
    color: "#666",
    marginTop: "-4px",
    marginBottom: "12px"
};

const singleCardGridStyle = {
    maxWidth: "700px",
    margin: "32px auto 0"
};

export default HomePage;