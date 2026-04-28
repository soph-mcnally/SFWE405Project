const API_BASE_URL = "http://localhost:8080";

export async function fetchAcademicRecord(token, page = 0, size = 5, search = "") {
  const params = new URLSearchParams({
    page,
    size,
    search
  });

  const response = await fetch(`${API_BASE_URL}/api/academic-record?${params}`, {
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