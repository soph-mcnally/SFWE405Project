/*
 * @author Sophie McNally
 *
 * Admin page for managing courses by semester
 * Allow admin to view, search, add, edit, delete courses within a selected semester
 * Allows admin to assign and remove faculty from courses
*/
import React, { useState, useEffect } from "react";
import { getSemesters, getCoursesBySemester, addCourse, updateCourse, deleteCourse,getFacultyForCourse, getAllFaculty,
                                                assignFaculty, removeFaculty} from "../services/semesterService";
import Button from "../components/Button";
import colors from "../styles/colors";

function ManageCoursesPage() {
    const [semesters, setSemesters] = useState([]);
    const [selectedSemester, setSelectedSemester] = useState("");
    const [courses, setCourses] = useState([]);
    const [message, setMessage] = useState("");
    const [editingCourse, setEditingCourse] = useState(null);
    const [showAddForm, setShowAddForm] = useState(false);
    const [newCourse, setNewCourse] = useState({
        courseCode: "",
        courseName: "",
        courseType: "LECTURE",
        unitsAmount: 3,
        upperDivision: false,
        university: { universityId: 1 }
    });
    const [searchInput, setSearchInput] = useState("");
    const [search, setSearch] = useState("");
    const [allFaculty, setAllFaculty] = useState([]);
    const [courseFaculty, setCourseFaculty] = useState({});
    const [selectedFaculty, setSelectedFaculty] = useState({});
    const [coursePage, setCoursePage] = useState(0);
    const coursesPerPage = 5;

    const token = localStorage.getItem("token");

    useEffect(() => {
        loadSemesters();
    }, []);

    const loadSemesters = async () => {
        try {
            const data = await getSemesters(token);
            setSemesters(data);
        } catch (error) {
            setMessage("Failed to load semesters");
        }
    };

    const loadCourses = async (semesterId) => {
        try {
            const data = await getCoursesBySemester(token, semesterId);
            setCourses(data);
            await loadFacultyData(data, semesterId);
        } catch (error) {
            setMessage("Failed to load courses");
        }
    };

    const loadFacultyData = async (courses, semesterId) => {
        try {
            const faculty = await getAllFaculty(token);
            setAllFaculty(faculty);

            const facultyMap = {};
            for (const course of courses) {
                const assigned = await getFacultyForCourse(token, semesterId, course.courseId);
                facultyMap[course.courseId] = assigned;
            }
            setCourseFaculty(facultyMap);
        } catch (error) {
            console.error("Failed to load faculty data", error);
        }
    };

    const handleSemesterChange = (e) => {
        const semesterId = e.target.value;
        setSelectedSemester(semesterId);
        setCourses([]);
        setMessage("");
        setShowAddForm(false);
        setEditingCourse(null);
        setCoursePage(0);
        if (semesterId) loadCourses(semesterId);
    };

    const handleAddCourse = async () => {
        try {
            await addCourse(token, selectedSemester, newCourse);
            setMessage("Course added successfully!");
            setShowAddForm(false);
            setNewCourse({
                courseCode: "",
                courseName: "",
                courseType: "LECTURE",
                unitsAmount: 3,
                upperDivision: false,
                university: { universityId: 1 }
            });
            loadCourses(selectedSemester);
        } catch (error) {
            setMessage("Failed to add course");
        }
    };

    const handleEditCourse = (course) => {
        setEditingCourse({ ...course });
        setShowAddForm(false);
        setMessage("");
    };

    const handleUpdateCourse = async () => {
        try {
            await updateCourse(token, selectedSemester, editingCourse.courseId, editingCourse);
            setMessage("Course updated successfully!");
            setEditingCourse(null);
            loadCourses(selectedSemester);
        } catch (error) {
            setMessage("Failed to update course");
        }
    };

    const handleDeleteCourse = async (courseId) => {
        if (!window.confirm("Are you sure you want to delete this course?")) return;
        try {
            await deleteCourse(token, selectedSemester, courseId);
            setMessage("Course deleted successfully!");
            loadCourses(selectedSemester);
        } catch (error) {
            setMessage("Failed to delete course");
        }
    };

    const handleAssignFaculty = async (courseId) => {
        const personId = selectedFaculty[courseId];
        if (!personId) return;
        try {
            await assignFaculty(token, selectedSemester, courseId, personId);
            setMessage("Faculty assigned successfully!");
            await loadFacultyData(courses, selectedSemester);
        } catch (error) {
            setMessage("Failed to assign faculty");
        }
    };

    const handleRemoveFaculty = async (courseId, personId) => {
        try {
            await removeFaculty(token, selectedSemester, courseId, personId);
            setMessage("Faculty removed successfully!");
            await loadFacultyData(courses, selectedSemester);
        } catch (error) {
            setMessage("Failed to remove faculty");
        }
    };

    const filteredCourses = courses.filter(course =>
        course.courseCode.toLowerCase().includes(search.toLowerCase()) ||
        course.courseName.toLowerCase().includes(search.toLowerCase())
    );
    const totalCourses = filteredCourses.length;
    const startIndex = coursePage * coursesPerPage;
    const endIndex = Math.min(startIndex + coursesPerPage, totalCourses);
    const visibleCourses = filteredCourses.slice(startIndex, endIndex);

    const isFirstPage = coursePage === 0;
    const isLastPage = endIndex >= totalCourses;


    return (
        <div style={{ minHeight: "calc(100vh - 64px)", backgroundColor: colors.lightGray, padding: "40px 20px" }}>
            <div style={{ maxWidth: "900px", margin: "0 auto" }}>
                <h1 style={{ textAlign: "center", marginBottom: "24px", color: colors.navyBlue }}>
                    Manage Courses by Semester
                </h1>

                {/* semester selector */}
                <div style={{ textAlign: "center", marginBottom: "24px" }}>
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

                {/* search */}
                {selectedSemester && (
                    <div style={{ textAlign: "center", marginBottom: "20px" }}>
                        <input
                            type="text"
                            placeholder="Search by course code or name..."
                            value={searchInput}
                            onChange={(e) => setSearchInput(e.target.value)}
                            onKeyDown={(e) => { if (e.key === "Enter") setSearch(searchInput); }}
                            style={{ padding: "8px", width: "250px", marginRight: "10px" }}
                        />
                        <Button
                            onClick={() => { setSearch(searchInput); setCoursePage(0);}}
                            variant="primary"
                        >
                            Search
                        </Button>
                        {search && (
                            <Button
                                onClick={() => { setSearch(""); setSearchInput(""); setCoursePage(0); }}
                                variant="secondary"
                                style={{ marginLeft: "8px" }}
                            >
                                Clear
                            </Button>
                        )}
                    </div>
                )}

                {/* add course button */}
                {selectedSemester && !showAddForm && !editingCourse && (
                    <div style={{ textAlign: "center", marginBottom: "20px" }}>
                        <Button
                            onClick={() => setShowAddForm(true)}
                            variant="primary"
                            size="medium"
                        >
                            + Add Course
                        </Button>
                    </div>
                )}

                {/* add course form */}
                {showAddForm && (
                    <div style={{ backgroundColor: colors.white, border: `1px solid ${colors.borderGray}`, borderRadius: "8px", padding: "20px", marginBottom: "24px" }}>
                        <h3 style={{ color: colors.navyBlue }}>Add New Course</h3>
                        <input
                            placeholder="Course Code (e.g. CSE310)"
                            value={newCourse.courseCode}
                            onChange={(e) => setNewCourse({ ...newCourse, courseCode: e.target.value })}
                            style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                        />
                        <input
                            placeholder="Course Name"
                            value={newCourse.courseName}
                            onChange={(e) => setNewCourse({ ...newCourse, courseName: e.target.value })}
                            style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                        />
                        <select
                            value={newCourse.courseType}
                            onChange={(e) => setNewCourse({ ...newCourse, courseType: e.target.value })}
                            style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                        >
                            <option value="LECTURE">LECTURE</option>
                            <option value="LAB">LAB</option>
                            <option value="DISCUSSION">DISCUSSION</option>
                        </select>
                        <input
                            type="number"
                            placeholder="Units"
                            value={newCourse.unitsAmount}
                            onChange={(e) => setNewCourse({ ...newCourse, unitsAmount: Number(e.target.value) })}
                            style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                        />
                        <label style={{ display: "block", marginBottom: "10px" }}>
                            <input
                                type="checkbox"
                                checked={newCourse.upperDivision}
                                onChange={(e) => setNewCourse({ ...newCourse, upperDivision: e.target.checked })}
                                style={{ marginRight: "8px" }}
                            />
                            Upper Division
                        </label>
                        <Button
                            onClick={handleAddCourse}
                            variant="cardinal"
                            size="medium"
                            style={{ marginRight: "10px" }}
                        >
                            Save
                        </Button>
                        <Button
                            onClick={() => setShowAddForm(false)}
                            variant="secondary"
                            size="medium"
                        >
                            Cancel
                        </Button>
                    </div>
                )}

                {/* Edit Course Form */}
                {editingCourse && (
                    <div style={{ backgroundColor: colors.white, border: `1px solid ${colors.borderGray}`, borderRadius: "8px", padding: "20px", marginBottom: "24px" }}>
                        <h3 style={{ color: colors.navyBlue }}>Edit Course</h3>
                        <input
                            placeholder="Course Code"
                            value={editingCourse.courseCode}
                            onChange={(e) => setEditingCourse({ ...editingCourse, courseCode: e.target.value })}
                            style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                        />
                        <input
                            placeholder="Course Name"
                            value={editingCourse.courseName}
                            onChange={(e) => setEditingCourse({ ...editingCourse, courseName: e.target.value })}
                            style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                        />
                        <select
                            value={editingCourse.courseType}
                            onChange={(e) => setEditingCourse({ ...editingCourse, courseType: e.target.value })}
                            style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                        >
                            <option value="LECTURE">LECTURE</option>
                            <option value="LAB">LAB</option>
                            <option value="DISCUSSION">DISCUSSION</option>
                        </select>
                        <input
                            type="number"
                            placeholder="Units"
                            value={editingCourse.unitsAmount}
                            onChange={(e) => setEditingCourse({ ...editingCourse, unitsAmount: Number(e.target.value) })}
                            style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                        />
                        <label style={{ display: "block", marginBottom: "10px" }}>
                            <input
                                type="checkbox"
                                checked={editingCourse.upperDivision}
                                onChange={(e) => setEditingCourse({ ...editingCourse, upperDivision: e.target.checked })}
                                style={{ marginRight: "8px" }}
                            />
                            Upper Division
                        </label>
                        <Button
                            onClick={handleUpdateCourse}
                            variant="cardinal"
                            size="medium"
                            style={{ marginRight: "10px" }}
                        >
                            Save
                        </Button>
                        <Button
                            onClick={() => setEditingCourse(null)}
                            variant="secondary"
                            size="medium"
                        >
                            Cancel
                        </Button>
                    </div>
                )}

                {/* course List */}
                {filteredCourses.length === 0 && selectedSemester && (
                    <p style={{ textAlign: "center" }}>No courses found for this semester.</p>
                )}

                {visibleCourses.map((course) => (
                    <div
                        key={course.courseId}
                        style={{ backgroundColor: colors.white, border: `1px solid ${colors.borderGray}`, borderLeft: `6px solid ${colors.cardinalRed}`, borderRadius: "8px", padding: "16px", marginBottom: "12px", boxShadow: "0 2px 6px rgba(0,0,0,0.08)" }}
                    >
                        <h3 style={{ color: colors.navyBlue, marginTop: 0 }}>
                            {course.courseCode}: {course.courseName}
                        </h3>
                        <p style={{ margin: "4px 0" }}><strong>Type:</strong> {course.courseType}</p>
                        <p style={{ margin: "4px 0" }}><strong>Units:</strong> {course.unitsAmount}</p>
                        <p style={{ margin: "4px 0" }}><strong>Upper Division:</strong> {course.upperDivision ? "Yes" : "No"}</p>
                        <div style={{ marginTop: "12px" }}>
                            <Button
                                onClick={() => handleEditCourse(course)}
                                variant="primary"
                                size="small"
                                style={{ marginRight: "10px" }}
                            >
                                Edit
                            </Button>
                            <Button
                                onClick={() => handleDeleteCourse(course.courseId)}
                                variant="danger"
                                size="small"
                            >
                                Delete
                            </Button>
                        </div>
                        <div style={{ marginTop: "12px", borderTop: `1px solid ${colors.borderGray}`, paddingTop: "12px" }}>
                            <strong>Assigned Faculty:</strong>
                            {(courseFaculty[course.courseId] || []).length === 0 ? (
                                <p style={{ margin: "4px 0", color: "#666" }}>No faculty assigned</p>
                            ) : (
                                (courseFaculty[course.courseId] || []).map(f => (
                                    <div key={f.personID} style={{ display: "flex", alignItems: "center", gap: "8px", margin: "4px 0" }}>
                                        <span>{f.firstName} {f.lastName}</span>
                                        <Button
                                            onClick={() => handleRemoveFaculty(course.courseId, f.personID)}
                                            variant="danger"
                                            size="xs"
                                        >
                                            Remove
                                        </Button>
                                    </div>
                                ))
                            )}
                            <div style={{ marginTop: "8px", display: "flex", gap: "8px" }}>
                                <select
                                    value={selectedFaculty[course.courseId] || ""}
                                    onChange={(e) => setSelectedFaculty({ ...selectedFaculty, [course.courseId]: e.target.value })}
                                    style={{ padding: "4px", flex: 1 }}
                                >
                                    <option value="">-- Select Faculty --</option>
                                    {allFaculty.map(f => (
                                        <option key={f.personID} value={f.personID}>
                                            {f.firstName} {f.lastName}
                                        </option>
                                    ))}
                                </select>
                                <Button
                                    onClick={() => handleAssignFaculty(course.courseId)}
                                    variant="primary"
                                    size="xs"
                                >
                                    Assign
                                </Button>
                            </div>
                        </div>
                    </div>
                ))}

                {totalCourses > 0 && (
                    <div style={{
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        gap: "24px",
                        marginTop: "20px"
                    }}>
                        <Button
                            onClick={() => setCoursePage((prev) => Math.max(prev - 1, 0))}
                            disabled={isFirstPage}
                            variant="pagination"
                        >
                            ‹
                        </Button>

                        <span style={{ fontSize: "20px" }}>
                            {startIndex + 1}-{endIndex} of {totalCourses}
                        </span>

                        <Button
                            onClick={() => setCoursePage((prev) => prev + 1)}
                            disabled={isLastPage}
                            variant="pagination"
                        >
                            ›
                        </Button>
                    </div>
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

export default ManageCoursesPage;