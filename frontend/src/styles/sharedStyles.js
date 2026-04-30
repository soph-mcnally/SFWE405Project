import colors from "./colors";

export const pageStyle = {
    minHeight: "calc(100vh - 64px)",
    backgroundColor: colors.lightGray,
    padding: "40px 20px"
};

export const fullPageStyle = {
    minHeight: "100vh",
    backgroundColor: colors.lightGray,
    padding: "32px"
};

export const containerStyle = {
    maxWidth: "900px",
    margin: "0 auto"
};

export const narrowContainerStyle = {
    maxWidth: "700px",
    margin: "0 auto"
};

export const cardStyle = {
    backgroundColor: colors.white,
    padding: "24px",
    borderRadius: "12px",
    border: `1px solid ${colors.borderGray}`,
    boxShadow: "0 2px 8px rgba(0,0,0,0.08)"
};

export const dashboardCardStyle = {
    backgroundColor: colors.white,
    padding: "32px",
    borderRadius: "12px",
    borderTop: `6px solid ${colors.cardinalRed}`,
    borderLeft: `1px solid ${colors.borderGray}`,
    borderRight: `1px solid ${colors.borderGray}`,
    borderBottom: `1px solid ${colors.borderGray}`,
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
};

export const formCardStyle = {
    backgroundColor: colors.white,
    border: `1px solid ${colors.borderGray}`,
    borderRadius: "8px",
    padding: "20px"
};

export const pageTitleStyle = {
    textAlign: "center",
    marginBottom: "24px",
    color: colors.navyBlue
};

export const sectionTitleStyle = {
    color: colors.navyBlue,
    marginTop: 0,
    marginBottom: "16px"
};

export const inputStyle = {
    padding: "8px 12px",
    border: `1px solid ${colors.borderGray}`,
    borderRadius: "6px",
    fontSize: "14px",
    boxSizing: "border-box"
};

export const fullWidthInputStyle = {
    ...inputStyle,
    width: "100%"
};

export const selectStyle = {
    ...inputStyle,
    width: "260px"
};

export const labelStyle = {
    display: "block",
    fontWeight: "bold",
    color: colors.navyBlue,
    marginBottom: "12px"
};

export const placeholderStyle = {
    marginTop: "16px",
    padding: "32px",
    borderRadius: "8px",
    border: `2px dashed ${colors.cardinalRed}`,
    textAlign: "center",
    color: colors.navyBlue,
    backgroundColor: colors.lightGray
};

export const errorCardStyle = {
    backgroundColor: colors.white,
    padding: "24px",
    borderRadius: "8px",
    border: `1px solid ${colors.borderGray}`,
    boxShadow: "0 2px 8px rgba(0, 0, 0, 0.1)",
    textAlign: "center"
};