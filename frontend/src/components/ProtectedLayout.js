import { Link, useNavigate } from "react-router-dom";
import colors from "../styles/colors";
import { logout } from "../services/authService";

function ProtectedLayout({ children }) {
    const navigate = useNavigate();
    const role = localStorage.getItem("role")?.toUpperCase();

    const userEmail =
        localStorage.getItem("email") ||
        localStorage.getItem("userEmail") ||
        "Logged in user";

    async function handleLogout() {
        const token = localStorage.getItem("token");

        try {
            if (token) {
                await logout(token);
            }
        } catch (error) {
            console.error("Logout error:", error);
        } finally {
            localStorage.removeItem("token");
            localStorage.removeItem("email");
            localStorage.removeItem("userEmail");
            localStorage.removeItem("expiresAt");
            localStorage.removeItem("theme");
            localStorage.removeItem("role");

            navigate("/login");
        }
    }

    return (
        <div>
            <header style={headerStyle}>
                <nav style={navStyle}>
                    <Link
                        to="/home"
                        style={navButtonStyle}
                        onMouseEnter={handleMouseEnter}
                        onMouseLeave={handleMouseLeave}
                    >
                        Home
                    </Link>

                    <Link
                        to="/academic"
                        style={navButtonStyle}
                        onMouseEnter={handleMouseEnter}
                        onMouseLeave={handleMouseLeave}
                    >
                        Academic Record
                    </Link>

                    <Link
                        to="/enroll"
                        style={navButtonStyle}
                        onMouseEnter={handleMouseEnter}
                        onMouseLeave={handleMouseLeave}
                    >
                        Enroll Courses
                    </Link>

                    <Link
                        to="/manage-account"
                        style={navButtonStyle}
                        onMouseEnter={handleMouseEnter}
                        onMouseLeave={handleMouseLeave}
                    >
                        Manage Account
                    </Link>

                    <Link
                        to="/homework"
                        style={navButtonStyle}
                        onMouseEnter={handleMouseEnter}
                        onMouseLeave={handleMouseLeave}
                    >
                        Homework
                    </Link>

                    {role === "ADMIN" && (
                        <Link
                            to="/manage-courses"
                            style={navButtonStyle}
                            onMouseEnter={handleMouseEnter}
                            onMouseLeave={handleMouseLeave}
                        >
                            Manage Courses
                        </Link>
                    )}
                </nav>

                <div style={userSectionStyle}>
                    <span>{userEmail}</span>
                    <button onClick={handleLogout} style={logoutButtonStyle}>
                        Logout
                    </button>
                </div>
            </header>

            <main>{children}</main>
        </div>
    );
}

function handleMouseEnter(e) {
    e.target.style.backgroundColor = colors.cardinalRed;
    e.target.style.color = colors.white;
}

function handleMouseLeave(e) {
    e.target.style.backgroundColor = colors.white;
    e.target.style.color = colors.navyBlue;
}

const headerStyle = {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "16px 24px",
    backgroundColor: colors.navyBlue,
    color: colors.white
};

const navStyle = {
    display: "flex",
    gap: "12px",
    flexWrap: "wrap"
};

const userSectionStyle = {
    display: "flex",
    alignItems: "center",
    gap: "12px"
};

const navButtonStyle = {
    backgroundColor: colors.white,
    color: colors.navyBlue,
    padding: "8px 16px",
    borderRadius: "6px",
    textDecoration: "none",
    fontWeight: "bold",
    border: `1px solid ${colors.borderGray}`
};

const logoutButtonStyle = {
    backgroundColor: colors.cardinalRed,
    color: colors.white,
    padding: "8px 14px",
    borderRadius: "6px",
    border: "none",
    cursor: "pointer",
    fontWeight: "bold"
};

export default ProtectedLayout;