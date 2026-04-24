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
    <div style={{ padding: "20px" }}>
      <h1>Academic Record</h1>

      {records.length === 0 ? (
        <p>No academic records found.</p>
      ) : (
        records.map((record, index) => (
          <AcademicRecordCard
            key={record.enrollmentId}
            record={record}
          />
        ))
      )}
    </div>
  );
}

export default AcademicRecordPage;