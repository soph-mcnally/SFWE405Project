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
        boxShadow: "0 2px 6px rgba(0, 0, 0, 0.08)"
      }}
    >
      <h2 style={{ color: colors.navyBlue, marginTop: 0 }}>
        {record.courseCode}: {record.courseName}
      </h2>

      <p><strong>Course Type:</strong> {record.courseType}</p>
      <p><strong>Semester:</strong> {record.semester}</p>
      <p><strong>Status:</strong> {record.status}</p>
      <p><strong>Grade:</strong> {record.grade}</p>
      <p><strong>Units:</strong> {record.unitsAmount}</p>
    </div>
  );
}

export default AcademicRecordCard;