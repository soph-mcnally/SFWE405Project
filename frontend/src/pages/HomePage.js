import { Link } from "react-router-dom";

function HomePage() {
    return (
        <div style={{ padding: "20px" }}>
            <h1>Dashboard</h1>

            <div style={{ marginTop: "20px" }}>
                <Link to="/enroll">
                    <button style={{ marginRight: "10px" }}>
                        Enroll Courses
                    </button>
                </Link>

                <Link to="/academic">
                    <button>
                        Academic Record
                    </button>
                </Link>

                <Link to="/requirements">
                    <button style={{ marginRight: "10px" }}>
                        University Requirements
                    </button>
                </Link>

            </div>
        </div>
    );
}

export default HomePage;