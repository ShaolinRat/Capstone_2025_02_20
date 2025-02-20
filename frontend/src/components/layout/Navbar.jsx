import { Link } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import "./Navbar.css";

function Navbar() {
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-brand">
          News Blog
        </Link>
        <ul className="nav-links">
          <li>
            <Link to="/blogs">Blogs</Link>
          </li>
          {user ? (
            <>
              <li>
                <Link to="/create-blog">Create Blog</Link>
              </li>
              <li>
                <button onClick={handleLogout} className="logout-button">
                  Logout
                </button>
              </li>
            </>
          ) : (
            <>
              <li>
                <Link to="/login" className="auth-link">
                  Login
                </Link>
              </li>
              <li>
                <Link to="/register" className="auth-link">
                  Register
                </Link>
              </li>
            </>
          )}
        </ul>
      </div>
    </nav>
  );
}

export default Navbar;
