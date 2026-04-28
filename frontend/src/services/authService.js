const API_BASE_URL = "http://localhost:8080";

export async function login(usernameOrEmail, password) {
  const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      usernameOrEmail,
      password
    })
  });

  if (!response.ok) {
    throw new Error("Login failed");
  }

  return response.json();
}

export async function logout(token) {
  const response = await fetch(`${API_BASE_URL}/api/auth/logout`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`
    }
  });

  if (!response.ok) {
    throw new Error("Logout failed");
  }
}