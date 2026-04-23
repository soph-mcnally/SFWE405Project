import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import EnrollCoursesPage from "./pages/EnrollCoursesPage";

function App() {
  const token = localStorage.getItem("token");

  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path="/login" element={<LoginPage />} />
          <Route
              path="/enroll"
              element={token ? <EnrollCoursesPage /> : <Navigate to="/login" />}
          />
        </Routes>
      </BrowserRouter>
  );
}

export default App;