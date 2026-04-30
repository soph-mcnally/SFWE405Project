import React, { useEffect, useState } from "react";
import { fetchAcademicRecord } from "../services/academicRecord";
import AcademicRecordCard from "../components/AcademicRecordCard";
import Button from "../components/Button";
import colors from "../styles/colors";

function AcademicRecordPage() {
  const [records, setRecords] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  const [totalPages, setTotalPages] = useState(0);
  const [isFirstPage, setIsFirstPage] = useState(true);
  const [isLastPage, setIsLastPage] = useState(true);
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState("");
  const [searchInput, setSearchInput] = useState("");
  const pageSize = 5;

  useEffect(() => {
    async function loadAcademicRecord() {
      try {
        setLoading(true);
        setError("");

        const token = localStorage.getItem("token");
        const data = await fetchAcademicRecord(token, page, pageSize, search);

        setRecords(data.content);
        setTotalPages(data.totalPages);
        setIsFirstPage(data.first);
        setIsLastPage(data.last);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    loadAcademicRecord();
  }, [page, search]);

  if (loading) {
    return <p>Loading academic record...</p>;
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
        <h1
          style={{
            textAlign: "center",
            marginBottom: "24px",
            color: colors.navyBlue
          }}
        >
          Academic Record
        </h1>

        {!error && (
          <div style={{ marginBottom: "20px", textAlign: "center" }}>
            <input
              type="text"
              placeholder="Search by course code or name..."
              value={searchInput}
              onChange={(e) => setSearchInput(e.target.value)}
              style={{
                padding: "8px",
                width: "250px",
                marginRight: "10px"
              }}
            />

            <Button
              onClick={() => {
                setPage(0);
                setSearch(searchInput);
              }}
              variant="primary"
            >
              Search
            </Button>

            <Button
              onClick={async () => {
                const token = localStorage.getItem("token");

                const response = await fetch(
                  "http://localhost:8080/api/academic-record/export",
                  {
                    headers: {
                      Authorization: `Bearer ${token}`
                    }
                  }
                );

                const blob = await response.blob();
                const url = window.URL.createObjectURL(blob);

                const a = document.createElement("a");
                a.href = url;
                a.download = "academic_record.csv";
                a.click();
              }}
              variant="primary"
              style={{ marginLeft: "10px" }}
            >
              Export CSV
            </Button>
          </div>
        )}

        {error ? (
          <div
            style={{
              backgroundColor: "white",
              padding: "24px",
              borderRadius: "8px",
              border: `1px solid ${colors.borderGray}`,
              boxShadow: "0 2px 8px rgba(0, 0, 0, 0.1)",
              textAlign: "center"
            }}
          >
            <h2 style={{ marginBottom: "12px", color: colors.cardinalRed }}>
              Academic Record Unavailable
            </h2>

            <p>{error}</p>
          </div>
        ) : records.length === 0 ? (
          <p style={{ textAlign: "center" }}>No academic records found.</p>
        ) : (
          records.map((record) => (
            <AcademicRecordCard
              key={record.enrollmentId}
              record={record}
            />
          ))
        )}

        {!error && records.length > 0 && (
          <div style={{ textAlign: "center", marginTop: "20px" }}>
            <Button
              onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
              disabled={isFirstPage}
              variant="pagination"
              style={{ marginRight: "10px" }}
            >
              ‹
            </Button>

            <span style={{ fontSize: "20px" }}>
              Page {page + 1} of {totalPages}
            </span>

            <Button
              onClick={() => setPage((prev) => prev + 1)}
              disabled={isLastPage}
              variant="pagination"
              style={{ marginLeft: "10px" }}
            >
              ›
            </Button>
          </div>
        )}
      </div>
    </div>
  );
}

export default AcademicRecordPage;