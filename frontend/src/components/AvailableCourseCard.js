import colors from "../styles/colors";

function AvailableCourseCard({ course, selected, onSelect }) {
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
                display: "flex",
                alignItems: "flex-start",
                gap: "12px"
            }}
        >
            <input
                type="checkbox"
                checked={selected}
                onChange={onSelect}
                style={{
                    width: "22px",
                    height: "22px",
                    cursor: "pointer",
                    accentColor: colors.cardinalRed,
                    flexShrink: 0
                }}
            />

            <div>
                <h3 style={{ color: colors.navyBlue, margin: 0 }}>
                    {course.courseCode}: {course.courseName}
                </h3>
                <p style={{ margin: "4px 0" }}>
                    <strong>Units:</strong> {course.unitsAmount}
                </p>
            </div>
        </div>
    );
}

export default AvailableCourseCard;