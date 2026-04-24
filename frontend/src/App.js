import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import EnrollCoursesPage from "./pages/EnrollCoursesPage";
import AcademicRecordPage from "./pages/AcademicRecordPage";

function App() {
  const token = localStorage.getItem("token");

  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Navigate to="/login" />} />
              <Route path="/login" element={<LoginPage />} />

              <Route
                  path="/home"
                  element={token ? <HomePage /> : <Navigate to="/login" />}
              />

              <Route
                  path="/enroll"
                  element={token ? <EnrollCoursesPage /> : <Navigate to="/login" />}
              />

              <Route
                  path="/academic"
                  element={token ? <AcademicRecordPage /> : <Navigate to="/login" />}
              />
          </Routes>
      </BrowserRouter>
  );
}

export default App;