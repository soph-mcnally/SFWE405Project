import React, { useEffect, useState } from "react";
import { fetchAcademicRecord } from "../services/academicRecord";
import AcademicRecordCard from "../components/AcademicRecordCard";

function AcademicRecordPage() {
  const [records, setRecords] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadAcademicRecord() {
      try {
        const token = localStorage.getItem("token");
        const data = await fetchAcademicRecord(token);
        setRecords(data);
      } catch (err) {
        setError("Unable to load academic record.");
      } finally {
        setLoading(false);
      }
    }

    loadAcademicRecord();
  }, []);

  if (loading) {
    return <p>Loading academic record...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  return (
    <div
      style={{
        minHeight: "calc(100vh - 64px)",
        backgroundColor: "#f5f5f5",
        padding: "40px 20px"
      }}
    >
      <div
        style={{
          maxWidth: "900px",
          margin: "0 auto"
        }}
      >
        <h1 style={{ textAlign: "center", marginBottom: "24px" }}>
          Academic Record
        </h1>

        {records.length === 0 ? (
          <p style={{ textAlign: "center" }}>No academic records found.</p>
        ) : (
          records.map((record) => (
            <AcademicRecordCard
              key={record.enrollmentId}
              record={record}
            />
          ))
        )}
      </div>
    </div>
  );
}

export default AcademicRecordPage;