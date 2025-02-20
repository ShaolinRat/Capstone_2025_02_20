import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { blogService } from "../../services/api";
import "./BlogList.css";

function BlogList() {
  const [blogs, setBlogs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBlogs = async () => {
      try {
        const data = await blogService.getAllBlogs();
        console.log("Received blogs:", data); // Debug log
        setBlogs(Array.isArray(data) ? data : []); // Ensure blogs is always an array
        setError(null);
      } catch (err) {
        console.error("Error details:", err);
        setError("Failed to load blogs. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchBlogs();
  }, []);

  if (loading) {
    return (
      <div className="blog-list-container">
        <div className="loading">Loading blogs...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="blog-list-container">
        <div className="error">{error}</div>
      </div>
    );
  }

  return (
    <div className="blog-list-container">
      <div className="blog-list-header">
        <h1>Blog Timeline</h1>
        <Link to="/create-blog" className="create-blog-button">
          Create New Blog
        </Link>
      </div>

      {blogs.length === 0 ? (
        <div className="no-blogs">
          No blogs found. Be the first to create one!
        </div>
      ) : (
        <div className="timeline">
          {blogs.map((blog) => (
            <div key={blog.id} className="timeline-item">
              <div className="timeline-content">
                <h2>{blog.title || "Untitled"}</h2>
                <p className="blog-preview">{blog.content || "No content"}</p>
                <div className="blog-meta">
                  <span>By {blog.author?.username || "Anonymous"}</span>
                  {blog.createdAt && (
                    <span>{new Date(blog.createdAt).toLocaleDateString()}</span>
                  )}
                </div>
                <div className="blog-actions">
                  <Link to={`/blogs/${blog.id}`} className="read-more">
                    Read More
                  </Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default BlogList;
