const API_BASE_URL = "http://localhost:8080";

export async function fetchAcademicRecord(token) {
  const response = await fetch(`${API_BASE_URL}/api/academic-record`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error("Failed to fetch academic record");
  }

  return response.json();
}