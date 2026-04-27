export function clearSession() {
  localStorage.removeItem("token");
  localStorage.removeItem("email");
  localStorage.removeItem("userEmail");
  localStorage.removeItem("expiresAt");
}