import React, { useState, useEffect } from "react";
import AvailableCourseCard from "../components/AvailableCourseCard";
import Button from "../components/Button";
import colors from "../styles/colors";

function EnrollCoursesPage() {
    const [semesters, setSemesters] = useState([]);
    const [selectedSemester, setSelectedSemester] = useState("");
    const [courses, setCourses] = useState([]);
    const [selectedCourses, setSelectedCourses] = useState([]);
    const [message, setMessage] = useState("");
    const [popup, setPopup] = useState(null);
    const [successPopup, setSuccessPopup] = useState(null);
    const [search, setSearch] = useState("");
    const [searchInput, setSearchInput] = useState("");

    const [coursePage, setCoursePage] = useState(0);
    const coursesPerPage = 5;
    const [enrolledCourses, setEnrolledCourses] = useState([]);
    const [completedCourses, setCompletedCourses] = useState([]);
    const [courseToUnenroll, setCourseToUnenroll] = useState(null);

    const token = localStorage.getItem("token");

    useEffect(() => {
        fetchSemesters();
        fetchEnrolledCourses();
        fetchCompletedCourses();
    }, []);

    const showPopup = (text) => {
        setPopup(text);

        setTimeout(() => {
            setPopup(null);
        }, 3000);
    };

    const showSuccessPopup = (text) => {
        setSuccessPopup(text);

        setTimeout(() => {
            setSuccessPopup(null);
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

    const fetchCompletedCourses = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/enrollment/completed-courses", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (!response.ok) {
                setMessage(`Error loading completed courses: ${response.status}`);
                return;
            }

            const data = await response.json();
            setCompletedCourses(data);
        } catch (error) {
            console.error(error);
            setMessage("Failed to load completed courses");
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

        const alreadyCompleted = completedCourses.some(
            (course) => course.courseId === courseId
        );

        if (alreadyCompleted) {
            showPopup("Course Already Completed");
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

            const data = await response.json();

            if (data.errors && data.errors.length > 0) {
                showPopup(data.errors[0]);
            }

            if (data.enrolledCourseIds && data.enrolledCourseIds.length > 0) {
                setSelectedCourses([]);
                setMessage("");
                fetchEnrolledCourses();
            }

        } catch (error) {
            console.error(error);
            setMessage("Enrollment failed");
        }
    };

    const handleUnenroll = async (courseId) => {
        try {
            const response = await fetch(
                `http://localhost:8080/api/enrollment/${courseId}`,
                {
                    method: "DELETE",
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            if (!response.ok) {
                showPopup("Unable to unenroll from course");
                return;
            }

            showSuccessPopup("Successfully Unenrolled");

            fetchEnrolledCourses();

        } catch (error) {
            console.error(error);
            showPopup("Unenrollment failed");
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

    const successPopupStyle = {
        position: "fixed",
        top: "90px",
        left: "50%",
        transform: "translateX(-50%)",
        backgroundColor: "#fff",
        color: "#2e7d32",
        border: "2px solid #2e7d32",
        borderRadius: "10px",
        padding: "14px 24px",
        display: "flex",
        alignItems: "center",
        gap: "12px",
        boxShadow: "0 4px 12px rgba(0, 0, 0, 0.2)",
        zIndex: 1000
    };

    const successIconStyle = {
        width: "26px",
        height: "26px",
        borderRadius: "50%",
        backgroundColor: "#2e7d32",
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

    const overlayStyle = {
        position: "fixed",
        top: 0,
        left: 0,
        width: "100%",
        height: "100%",
        backgroundColor: "rgba(0,0,0,0.45)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        zIndex: 2000
    };

    const modalStyle = {
        backgroundColor: "white",
        padding: "28px",
        borderRadius: "12px",
        width: "400px",
        textAlign: "center",
        boxShadow: "0 8px 24px rgba(0,0,0,0.25)"
    };

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

            {successPopup && (
                <div style={successPopupStyle}>
                    <div style={successIconStyle}>✓</div>
                    <strong>{successPopup}</strong>
                </div>
            )}

            {courseToUnenroll && (
                <div style={overlayStyle}>
                    <div style={modalStyle}>
                        <h2 style={{ marginTop: 0 }}>
                            Confirm Unenrollment
                        </h2>

                        <p>
                            Are you sure you want to unenroll from{" "}
                            <strong>{courseToUnenroll.courseCode}</strong>?
                        </p>

                        <div
                            style={{
                                display: "flex",
                                justifyContent: "center",
                                gap: "16px",
                                marginTop: "20px"
                            }}
                        >
                            <Button
                                onClick={() => setCourseToUnenroll(null)}
                                variant="modalCancel"
                                size="modal"
                            >
                                Cancel
                            </Button>

                            <Button
                                onClick={() => {
                                    handleUnenroll(courseToUnenroll.courseId);
                                    setCourseToUnenroll(null);
                                }}
                                variant="modalDanger"
                                size="modal"
                            >
                                Unenroll
                            </Button>
                        </div>
                    </div>
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

                    <Button onClick={handleSearch} variant="primary">
                        Search
                    </Button>
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
                                <Button
                                    onClick={() => setCoursePage((prev) => Math.max(prev - 1, 0))}
                                    disabled={isFirstCoursePage}
                                    variant="pagination"
                                >
                                    ‹
                                </Button>

                                <span style={{ fontSize: "20px" }}>
                                    {startIndex + 1}-{endIndex} of {totalCourses}
                                </span>

                                <Button
                                    onClick={() => setCoursePage((prev) => prev + 1)}
                                    disabled={isLastCoursePage}
                                    variant="pagination"
                                >
                                    ›
                                </Button>
                            </div>
                        )}

                        <div style={{ textAlign: "center", marginTop: "24px" }}>
                            <Button
                                onClick={handleEnroll}
                                disabled={selectedCourses.length === 0}
                                variant="primary"
                            >
                                Enroll
                            </Button>
                        </div>

                        {filteredEnrolledCourses.length > 0 ? (
                            <div style={{ marginTop: "32px" }}>
                                <h2 style={{ textAlign: "center", color: colors.navyBlue }}>
                                    Enrolled Courses
                                </h2>

                                <div
                                    style={{
                                        display: "grid",
                                        gridTemplateColumns: "repeat(auto-fit, minmax(420px, 1fr))",
                                        gap: "20px",
                                        marginTop: "20px"
                                    }}
                                >
                                    {filteredEnrolledCourses.map((course) => (
                                        <div
                                            key={course.courseId}
                                            style={{
                                                display: "flex",
                                                flexDirection: "column",
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
                                                    width: "100%",
                                                    backgroundColor: "#f0f0f0",
                                                    display: "flex",
                                                    flexDirection: "column",
                                                    alignItems: "center",
                                                    justifyContent: "center",
                                                    padding: "20px",
                                                    borderBottom: `1px solid ${colors.borderGray}`
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

                                                <Button
                                                    onClick={() => setCourseToUnenroll(course)}
                                                    variant="danger"
                                                    style={{ marginTop: "12px" }}
                                                >
                                                    Unenroll
                                                </Button>
                                            </div>
                                        </div>
                                    ))}
                                </div>
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