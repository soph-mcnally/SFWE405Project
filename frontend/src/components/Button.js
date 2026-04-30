import colors from "../styles/colors";

function Button({
  children,
  onClick,
  type = "button",
  disabled = false,
  variant = "primary",
  size = "default",
  fullWidth = false,
  style = {}
}) {
  const baseStyle = {
    border: "none",
    borderRadius: "6px",
    cursor: disabled ? "not-allowed" : "pointer",
    opacity: disabled ? 0.6 : 1,
    fontWeight: "bold",
    boxSizing: "border-box"
  };

  const variants = {
    primary: {
      backgroundColor: colors.navyBlue,
      color: colors.white
    },

    cardinal: {
      backgroundColor: colors.cardinalRed,
      color: colors.white
    },

    danger: {
      backgroundColor: colors.cardinalRed,
      color: colors.white
    },

    outlineDanger: {
      backgroundColor: "transparent",
      color: colors.cardinalRed,
      border: `1px solid ${colors.cardinalRed}`
    },

    secondary: {
      backgroundColor: colors.white,
      color: colors.black,
      border: `1px solid ${colors.borderGray}`
    },

    pagination: {
      background: "none",
      color: colors.cardinalRed,
      fontSize: "32px",
      padding: "0 8px"
    },

    modalCancel: {
      backgroundColor: "#d3d3d3",
      color: colors.black
    },

    modalDanger: {
      backgroundColor: "#b00020",
      color: colors.white
    }
  };

  const sizes = {
    xs: {
      padding: "2px 8px",
      fontSize: "12px",
      borderRadius: "4px"
    },

    small: {
      padding: "6px 14px",
      fontSize: "13px"
    },

    default: {
      padding: "8px 12px",
      fontSize: "14px"
    },

    medium: {
      padding: "8px 16px",
      fontSize: "14px"
    },

    large: {
      padding: "12px 24px",
      fontSize: "16px"
    },

    modal: {
      padding: "10px 18px",
      fontSize: "14px"
    }
  };

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      style={{
        ...baseStyle,
        ...sizes[size],
        ...variants[variant],
        width: fullWidth ? "100%" : undefined,
        ...style
      }}
    >
      {children}
    </button>
  );
}

export default Button;