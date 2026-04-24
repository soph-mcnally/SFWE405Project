import colors from "../styles/colors";

function AcademicRecordCard({ record }) {
  return (
    <div style={{
      border: `1px solid ${colors.borderGray}`,
      borderRadius: "8px",
      padding: "16px",
      marginBottom: "12px",
      backgroundColor: colors.white
    }}>
      <h2 style={{ color: colors.cardinalRed }}>
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