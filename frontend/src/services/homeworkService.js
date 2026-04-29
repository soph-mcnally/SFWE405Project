const BASE_URL = "http://localhost:8080/api/homework-assignment";

function getHeaders(token) {
    return {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
    };
}

// Student: get HW for a specific course
export async function getHomeworkForCourse(token, courseId) {
    const res = await fetch(`${BASE_URL}/studentViewHWByCourse/${courseId}`, {
        headers: getHeaders(token)
    });
    if (res.status === 204) return [];
    if (!res.ok) throw new Error(`Error ${res.status}`);
    return res.json();
}

// Faculty: get HW for a specific course
export async function getFacultyHomeworkForCourse(token, courseId) {
    const res = await fetch(`${BASE_URL}/facultyViewAssignmentByCourse/${courseId}`, {
        headers: getHeaders(token)
    });
    if (res.status === 204) return [];
    if (!res.ok) throw new Error(`Error ${res.status}`);
    return res.json();
}

// Faculty: create assignment
export async function createAssignment(token, courseId, assignment) {
    const res = await fetch(`${BASE_URL}/facultyCreateAssignmentByCourse/${courseId}`, {
        method: "POST",
        headers: getHeaders(token),
        body: JSON.stringify(assignment)
    });
    if (!res.ok) throw new Error(`Error ${res.status}`);
    return res.json();
}

// Faculty: delete assignment
export async function deleteAssignment(token, courseId, asgnId) {
    const res = await fetch(`${BASE_URL}/facultyDeleteAssignment/${courseId}/${asgnId}`, {
        method: "DELETE",
        headers: getHeaders(token)
    });
    if (!res.ok) throw new Error(`Error ${res.status}`);
    return res.text();
}