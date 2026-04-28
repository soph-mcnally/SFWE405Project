import React, { useState, useEffect } from "react";
import AvailableCourseCard from "../components/AvailableCourseCard";
import colors from "../styles/colors";

function EnrollCoursesPage() {
    const [semesters, setSemesters] = useState([]);
    const [selectedSemester, setSelectedSemester] = useState("");
    const [courses, setCourses] = useState([]);
    const [selectedCourses, setSelectedCourses] = useState([]);
    const [message, setMessage] = useState("");
    const [popup, setPopup] = useState(null);
    const [search, setSearch] = useState("");
    const [searchInput, setSearchInput] = useState("");

    const [coursePage, setCoursePage] = useState(0);
    const coursesPerPage = 5;
    const [enrolledCourses, setEnrolledCourses] = useState([]);

    const token = localStorage.getItem("token");

    useEffect(() => {
        fetchSemesters();
        fetchEnrolledCourses();
    }, []);

    const showPopup = (text) => {
        setPopup(text);

        setTimeout(() => {
            setPopup(null);
        }, 3000);
    };

    const fetchSemesters = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/enrollment/semesters", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (!response.ok) {
                setMessage(`Error: ${response.status}`);
                return;
            }

            const data = await response.json();
            setSemesters(data);
        } catch (error) {
            console.error(error);
            setMessage("Failed to load semesters");
        }
    };

    const fetchCourses = async (semesterId, searchTerm = "") => {
        try {
            const response = await fetch(
                `http://localhost:8080/api/enrollment/semesters/${semesterId}/courses?search=${searchTerm}`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            if (!response.ok) {
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

    const fetchEnrolledCourses = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/enrollment/my-courses", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (!response.ok) {
                setMessage(`Error loading enrolled courses: ${response.status}`);
                return;
            }

            const data = await response.json();
            setEnrolledCourses(data);
        } catch (error) {
            console.error(error);
            setMessage("Failed to load enrolled courses");
        }
    };

    const handleSemesterChange = (e) => {
        const semesterId = e.target.value;
        setSelectedSemester(semesterId);
        setSelectedCourses([]);
        setCoursePage(0);

        if (semesterId === "") {
            setCourses([]);
            return;
        }

        fetchCourses(semesterId, search);
    };

    const handleSearch = () => {
        setSearch(searchInput);
        setCoursePage(0);
        fetchCourses(selectedSemester, searchInput);
    };

    const handleCourseSelect = (courseId) => {
        const alreadyEnrolled = enrolledCourses.some(
            (course) => course.courseId === courseId
        );

        if (alreadyEnrolled) {
            showPopup("Already in Course");
            return;
        }

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
                setMessage(`Enrollment failed: ${response.status}`);
                return;
            }

            setSelectedCourses([]);
            setMessage("");
            fetchEnrolledCourses();
        } catch (error) {
            console.error(error);
            setMessage("Enrollment failed");
        }
    };

    const sortedCourses = [...courses].sort((a, b) => {
        return a.courseCode.localeCompare(b.courseCode);
    });

    const totalCourses = sortedCourses.length;
    const startIndex = coursePage * coursesPerPage;
    const endIndex = Math.min(startIndex + coursesPerPage, totalCourses);
    const visibleCourses = sortedCourses.slice(startIndex, endIndex);

    const isFirstCoursePage = coursePage === 0;
    const isLastCoursePage = endIndex >= totalCourses;

    const popupStyle = {
        position: "fixed",
        top: "90px",
        left: "50%",
        transform: "translateX(-50%)",
        backgroundColor: "#fff",
        color: "#b00020",
        border: "2px solid #b00020",
        borderRadius: "10px",
        padding: "14px 24px",
        display: "flex",
        alignItems: "center",
        gap: "12px",
        boxShadow: "0 4px 12px rgba(0, 0, 0, 0.2)",
        zIndex: 1000,
        animation: "shake 0.35s"
    };

    const popupIconStyle = {
        width: "26px",
        height: "26px",
        borderRadius: "50%",
        backgroundColor: "#b00020",
        color: "white",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        fontWeight: "bold"
    };

    const selectedSemesterObject = semesters.find(
        (semester) => String(semester.id) === String(selectedSemester)
    );

    const filteredEnrolledCourses = selectedSemesterObject
        ? enrolledCourses.filter(
            (course) =>
                course.semester ===
                `${selectedSemesterObject.season} ${selectedSemesterObject.semesterYear}`
        )
        : [];

    return (
        <div
            style={{
                minHeight: "calc(100vh - 64px)",
                backgroundColor: colors.lightGray,
                padding: "40px 20px"
            }}
        >
            {popup && (
                <div style={popupStyle}>
                    <div style={popupIconStyle}>✕</div>
                    <strong>{popup}</strong>
                </div>
            )}

            <div style={{ maxWidth: "900px", margin: "0 auto" }}>
                <h1 style={{ textAlign: "center", marginBottom: "24px" }}>
                    Enroll in Courses
                </h1>

                <div style={{ marginBottom: "24px", textAlign: "center" }}>
                    <h3>Select Semester</h3>

                    <select
                        value={selectedSemester}
                        onChange={handleSemesterChange}
                        style={{ padding: "8px", width: "260px" }}
                    >
                        <option value="">-- Select Semester --</option>
                        {semesters.map((semester) => (
                            <option key={semester.id} value={semester.id}>
                                {semester.season} {semester.semesterYear}
                            </option>
                        ))}
                    </select>
                </div>

                <div style={{ marginBottom: "20px", textAlign: "center" }}>
                    <input
                        type="text"
                        placeholder="Search by course code or name..."
                        value={searchInput}
                        onChange={(e) => setSearchInput(e.target.value)}
                        onKeyDown={(e) => {
                            if (e.key === "Enter") {
                                handleSearch();
                            }
                        }}
                        style={{
                            padding: "8px",
                            width: "250px",
                            marginRight: "10px"
                        }}
                    />

                    <button onClick={handleSearch}>
                        Search
                    </button>
                </div>

                {selectedSemester ? (
                    <>
                        <h3 style={{ textAlign: "center", marginBottom: "16px" }}>
                            Available Courses
                        </h3>

                        {courses.length === 0 ? (
                            <p style={{ textAlign: "center" }}>No courses loaded.</p>
                        ) : (
                            visibleCourses.map((course) => (
                                <AvailableCourseCard
                                    key={course.courseId}
                                    course={course}
                                    selected={selectedCourses.includes(course.courseId)}
                                    onSelect={() => handleCourseSelect(course.courseId)}
                                />
                            ))
                        )}

                        {totalCourses > 0 && (
                            <div
                                style={{
                                    display: "flex",
                                    justifyContent: "center",
                                    alignItems: "center",
                                    gap: "24px",
                                    marginTop: "20px"
                                }}
                            >
                                <button
                                    onClick={() => setCoursePage((prev) => Math.max(prev - 1, 0))}
                                    disabled={isFirstCoursePage}
                                    style={{
                                        border: "none",
                                        background: "none",
                                        fontSize: "32px",
                                        cursor: isFirstCoursePage ? "not-allowed" : "pointer",
                                        color: colors.cardinalRed
                                    }}
                                >
                                    ‹
                                </button>

                                <span style={{ fontSize: "20px" }}>
                    {startIndex + 1}-{endIndex} of {totalCourses}
                </span>

                                <button
                                    onClick={() => setCoursePage((prev) => prev + 1)}
                                    disabled={isLastCoursePage}
                                    style={{
                                        border: "none",
                                        background: "none",
                                        fontSize: "32px",
                                        cursor: isLastCoursePage ? "not-allowed" : "pointer",
                                        color: colors.cardinalRed
                                    }}
                                >
                                    ›
                                </button>
                            </div>
                        )}

                        <div style={{ textAlign: "center", marginTop: "24px" }}>
                            <button
                                onClick={handleEnroll}
                                disabled={selectedCourses.length === 0}
                                style={{ padding: "8px 12px" }}
                            >
                                Enroll
                            </button>
                        </div>

                        {filteredEnrolledCourses.length > 0 ? (
                            <div style={{ marginTop: "32px" }}>
                                <h2 style={{ textAlign: "center", color: colors.navyBlue }}>
                                    Enrolled Courses
                                </h2>

                                {filteredEnrolledCourses.map((course) => (
                                    <div
                                        key={course.courseId}
                                        style={{
                                            display: "flex",
                                            backgroundColor: colors.white,
                                            border: `1px solid ${colors.borderGray}`,
                                            borderRadius: "8px",
                                            marginBottom: "14px",
                                            overflow: "hidden",
                                            boxShadow: "0 2px 6px rgba(0, 0, 0, 0.08)"
                                        }}
                                    >
                                        <div
                                            style={{
                                                width: "150px",
                                                backgroundColor: "#f0f0f0",
                                                display: "flex",
                                                flexDirection: "column",
                                                alignItems: "center",
                                                justifyContent: "center",
                                                padding: "20px"
                                            }}
                                        >
                                            <div style={{ fontSize: "42px", color: "#5faf5f" }}>
                                                ✓
                                            </div>
                                            <strong>Enrolled</strong>
                                        </div>

                                        <div style={{ padding: "20px", flex: 1 }}>
                                            <h2
                                                style={{
                                                    color: colors.navyBlue,
                                                    marginTop: 0,
                                                    marginBottom: "12px"
                                                }}
                                            >
                                                {course.courseName} <br />
                                                ({course.courseType})
                                            </h2>

                                            <p><strong>Class:</strong> {course.courseCode}</p>
                                            <p><strong>Semester:</strong> {course.semester}</p>
                                            <p><strong>Units:</strong> {course.unitsAmount}</p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <p style={{ textAlign: "center", marginTop: "20px" }}>
                                No enrolled courses for this semester.
                            </p>
                        )}
                    </>
                ) : (
                    <p style={{ textAlign: "center", marginTop: "20px" }}>
                        Select a semester to view available and enrolled courses.
                    </p>
                )}

                {message && (
                    <p style={{ textAlign: "center", marginTop: "20px" }}>
                        <strong>{message}</strong>
                    </p>
                )}
            </div>
        </div>
    );
}

export default EnrollCoursesPage;