import { Navigate } from "react-router-dom";
import ProtectedLayout from "./ProtectedLayout";
import { clearSession } from "../services/sessionService";

// Reusable wrapper for protected pages
function ProtectedRoute({ children }) {
  const token = localStorage.getItem("token");
  const expiresAt = localStorage.getItem("expiresAt");

  if (!token || !expiresAt) {
    clearSession();
    return <Navigate to="/login" state={{ message: "Session expired. Please log in again." }} replace />;
    // "replace" protects against the user pressing the back button and re-accessing the protected endpoint
  }

  const expirationTime = new Date(expiresAt).getTime();
  const currentTime = new Date().getTime();

  if (currentTime >= expirationTime) {
    clearSession();
    return <Navigate to="/login" state={{ message: "Session expired. Please log in again." }} replace />;
  }

  return (
    <ProtectedLayout>
      {children}
    </ProtectedLayout>
  );
}

export default ProtectedRoute;