import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import ProtectedRoute from "./components/ProtectedRoute"; //added to reduce redundancy of reused code
                                                          // This is a reusable wrapper for protected pages
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import EnrollCoursesPage from "./pages/EnrollCoursesPage";
import AcademicRecordPage from "./pages/AcademicRecordPage";
import UniversityRequirementsPage from "./pages/UniversityRequirementsPage";

function App() {
  const token = localStorage.getItem("token");

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={<Navigate to={token ? "/home" : "/login"} />}
        />

        <Route path="/login" element={<LoginPage />} />

        //cleaned up route paths -> now uses protected route
        <Route
          path="/home"
          element={
            <ProtectedRoute>
              <HomePage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/enroll"
          element={
            <ProtectedRoute>
              <EnrollCoursesPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/academic"
          element={
            <ProtectedRoute>
              <AcademicRecordPage />
            </ProtectedRoute>
          }
        />

          <Route
              path="/requirements"
              element={
              <ProtectedRoute adminOnly={true}>
              <UniversityRequirementsPage />
              </ProtectedRoute>
          }
          />
      </Routes>
    </BrowserRouter>
  );
}

export default App;