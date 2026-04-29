const BASE_URL = "http://localhost:8080/api/semester";

export async function getSemesters(token) {
    const response = await fetch(BASE_URL, {
        headers:{
            Authorization: `Bearer ${token}`
        }
    });
    if (!response.ok) throw new Error("Failed to fetch semesters");
    return response.json();
}

export async function getCoursesBySemester(token, semesterId) {
    const response = await fetch(`${BASE_URL}/${semesterId}/courses`, {
        headers:{
            Authorization: `Bearer ${token}`
        }
    });
    if (!response.ok) throw new Error("Failed to fetch courses");
    return response.json();
}

export async function addCourse(token, semesterId, course) {
    const response = await fetch(`${BASE_URL}/${semesterId}/courses`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(course)
    });
    if (!response.ok) throw new Error("Failed to add course");
    return response.json();
}

export async function updateCourse(token, semesterId, courseId, course) {
    const response = await fetch(`${BASE_URL}/${semesterId}/courses/${courseId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(course)
    });
    if (!response.ok) throw new Error("Failed to update course");
    return response.json();
}

export async function deleteCourse(token, semesterId, courseId){
    const response = await fetch(`${BASE_URL}/${semesterId}/courses/${courseId}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    if (!response.ok) throw new Error("Failed to delete course");
}

export async function getFacultyForCourse(token, semesterId, courseId) {
    const response = await fetch(`${BASE_URL}/${semesterId}/courses/${courseId}/faculty`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    if (!response.ok) throw new Error("Failed to fetch faculty");
    return response.json();
}

export async function getAllFaculty(token) {
    const response = await fetch(`${BASE_URL}/faculty`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    if (!response.ok) throw new Error("Failed to fetch faculty list");
    return response.json();
}

export async function assignFaculty(token, semesterId, courseId, personId) {
    const response = await fetch(`${BASE_URL}/${semesterId}/courses/${courseId}/faculty/${personId}`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    if (!response.ok) throw new Error("Failed to assign faculty");
}

export async function removeFaculty(token, semesterId, courseId, personId) {
    const response = await fetch(`${BASE_URL}/${semesterId}/courses/${courseId}/faculty/${personId}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    if (!response.ok) throw new Error("Failed to remove faculty");
}