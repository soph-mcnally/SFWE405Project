import { Navigate } from "react-router-dom";
import ProtectedLayout from "./ProtectedLayout";

// Reusable wrapper for protected pages
function ProtectedRoute({ children }) {
  const token = localStorage.getItem("token");

  if (!token) {
    return <Navigate to="/login" />;
  }

  return (
    <ProtectedLayout>
      {children}
    </ProtectedLayout>
  );
}

export default ProtectedRoute;