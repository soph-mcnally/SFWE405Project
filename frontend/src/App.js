import React, { useState } from "react";

function App() {
  const [usernameOrEmail, setUsernameOrEmail] = useState("");
  const [password, setPassword] = useState("");
  const [token, setToken] = useState(localStorage.getItem("token") || "");
  const [records, setRecords] = useState([]);
  const [message, setMessage] = useState("");

  const handleLogin = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
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

      const data = await response.json();
      setToken(data.token);
      localStorage.setItem("token", data.token);
      setMessage("Login successful. Token stored in app state.");
    } catch (error) {
      console.error("Login error:", error);
      setMessage("Login failed. Check credentials.");
    }
  };

  const fetchAcademicRecord = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/academic-record", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error("Failed to fetch academic record");
      }

      const data = await response.json();
      setRecords(data);
      setMessage("Academic record loaded successfully.");
    } catch (error) {
      console.error("Academic record error:", error);
      setMessage("Could not load academic record.");
    }
  };

  const handleLogout = () => {
    setToken("");
    setRecords([]);
    localStorage.removeItem("token");
    setMessage("Logged out successfully.");
  };

  return (
    <div style={{ padding: "20px", fontFamily: "Arial, sans-serif" }}>
      <h1>SFWE 405 Course Management System</h1>
      <p>Frontend connected to Spring Boot backend</p>

      <h2>Login</h2>
      <div style={{ marginBottom: "20px" }}>
        <input
          type="text"
          placeholder="Username or Email"
          value={usernameOrEmail}
          onChange={(e) => setUsernameOrEmail(e.target.value)}
          style={{ display: "block", marginBottom: "10px", padding: "8px", width: "250px" }}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={{ display: "block", marginBottom: "10px", padding: "8px", width: "250px" }}
        />
        <button onClick={handleLogin} style={{ marginRight: "10px", padding: "8px 12px" }}>
          Login
        </button>
        <button onClick={fetchAcademicRecord} disabled={!token} style={{ marginRight: "10px", padding: "8px 12px" }}>
          Load Academic Record
        </button>
        <button onClick={handleLogout} disabled={!token} style={{ padding: "8px 12px" }}>
          Logout
        </button>
      </div>

      {message && <p><strong>{message}</strong></p>}

      <h2>Academic Records</h2>
      {records.length === 0 ? (
        <p>No records loaded</p>
      ) : (
        <div>
          {records.map((record, index) => (
            <div
              key={index}
              style={{
                border: "1px solid #ccc",
                borderRadius: "8px",
                padding: "12px",
                marginBottom: "12px",
                maxWidth: "500px",
                backgroundColor: "#f9f9f9"
              }}
            >
              <h3 style={{ margin: "0 0 8px 0" }}>{record.courseCode}</h3>
              <p><strong>Course Name:</strong> {record.courseName}</p>
              <p><strong>Course Type:</strong> {record.courseType}</p>
              <p><strong>Semester:</strong> {record.semester}</p>
              <p><strong>Grade:</strong> {record.grade}</p>
              <p><strong>Status:</strong> {record.status}</p>
              <p><strong>Units:</strong> {record.unitsAmount}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default App;