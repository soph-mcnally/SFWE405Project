import colors from "../styles/colors";

function AcademicRecordCard({ record }) {
  return (
    <div
      style={{
        border: `1px solid ${colors.borderGray}`,
        borderLeft: `6px solid ${colors.cardinalRed}`,
        borderRadius: "8px",
        padding: "16px",
        marginBottom: "12px",
        backgroundColor: colors.white,
        boxShadow: "0 2px 6px rgba(0, 0, 0, 0.08)",

      }}
    >
      <h2 style={{ color: colors.navyBlue, marginTop: 0, textAlign: "center"}}>
        {record.courseCode}: {record.courseName}
      </h2>

      <p style={{ margin: "6px 0" }}><strong>Course Type:</strong> {record.courseType}</p>
      <p style={{ margin: "6px 0" }}><strong>Semester:</strong> {record.semester}</p>
      <p style={{ margin: "6px 0" }}><strong>Status:</strong> {record.status}</p>
      <p style={{ margin: "6px 0" }}><strong>Grade:</strong> {!record.grade ? "Pending" : record.grade}</p>
      <p style={{ margin: "6px 0" }}><strong>Units:</strong> {record.unitsAmount}</p>
    </div>
  );
}

export default AcademicRecordCard;