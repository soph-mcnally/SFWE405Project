import { Link, useNavigate } from "react-router-dom";
import colors from "../styles/colors";

function ProtectedLayout({ children }) {
  const navigate = useNavigate();

  const userEmail =
    localStorage.getItem("email") ||
    localStorage.getItem("userEmail") ||
    "Logged in user";

  function handleLogout() {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    localStorage.removeItem("userEmail");
    navigate("/login");
  }

  return (
    <div>
      <header
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          padding: "16px 24px",
          backgroundColor: colors.navyBlue,
          color: colors.white
        }}
      >
        <nav
          style={{
            display: "flex",
            gap: "12px"
          }}
        >
          <Link
            to="/home"
            style={navButtonStyle}
            onMouseEnter={e => e.target.style.backgroundColor = "#8C1D40"}
            onMouseLeave={e => e.target.style.backgroundColor = "#FFFFFF"}
          >
            Home
          </Link>

          <Link
            to="/academic"
            style={navButtonStyle}
            onMouseEnter={e => e.target.style.backgroundColor = "#8C1D40"}
            onMouseLeave={e => e.target.style.backgroundColor = "#FFFFFF"}
          >
            Academic Record
          </Link>

          <Link
            to="/enroll"
            style={navButtonStyle}
            onMouseEnter={e => e.target.style.backgroundColor = "#8C1D40"}
            onMouseLeave={e => e.target.style.backgroundColor = "#FFFFFF"}
          >
            Enroll Courses
          </Link>
        </nav>

        <div style={{ display: "flex", alignItems: "center", gap: "12px" }}>
          <span>{userEmail}</span>
          <button onClick={handleLogout}>Logout</button>
        </div>
      </header>

      <main>
        {children}
      </main>
    </div>
  );
}

const navLinkStyle = {
  color: colors.white,
  textDecoration: "none",
  fontWeight: "bold"
};

const navButtonStyle = {
  backgroundColor: "#FFFFFF",
  color: "#001C48",
  padding: "8px 16px",
  borderRadius: "6px",
  textDecoration: "none",
  fontWeight: "bold",
  border: "1px solid #ccc"
};

export default ProtectedLayout;