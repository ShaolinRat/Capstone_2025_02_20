import { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { blogService } from "../../services/api";
import { useAuth } from "../../context/AuthContext";
import "./BlogPost.css";

function BlogPost() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [blog, setBlog] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [editedBlog, setEditedBlog] = useState(null);

  useEffect(() => {
    const fetchBlog = async () => {
      try {
        const data = await blogService.getBlogById(id);
        console.log("Fetched blog:", data); // Debug log
        setBlog(data);
        setEditedBlog(data);
      } catch (err) {
        console.error("Error fetching blog:", err);
        setError("Failed to load blog post");
      } finally {
        setLoading(false);
      }
    };

    fetchBlog();
  }, [id]);

  const handleEdit = () => {
    setIsEditing(true);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditedBlog((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSave = async () => {
    try {
      const updatedBlog = await blogService.updateBlog(id, {
        title: editedBlog.title,
        content: editedBlog.content,
      });
      setBlog(updatedBlog);
      setIsEditing(false);
    } catch (err) {
      console.error("Error updating blog:", err);
      setError("Failed to update blog post");
    }
  };

  const handleDelete = async () => {
    if (window.confirm("Are you sure you want to delete this blog post?")) {
      try {
        await blogService.deleteBlog(id);
        navigate("/blogs");
      } catch (err) {
        console.error("Error deleting blog:", err);
        setError("Failed to delete blog post");
      }
    }
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">{error}</div>;
  if (!blog) return <div className="error">Blog not found</div>;

  const canEditDelete =
    user && blog.author && user.username === blog.author.username;

  return (
    <div className="blog-post-container">
      <div className="blog-post-header">
        <Link to="/blogs" className="back-button">
          ← Back to Blogs
        </Link>
      </div>

      {isEditing ? (
        <div className="blog-post-edit">
          <input
            type="text"
            name="title"
            value={editedBlog.title}
            onChange={handleChange}
            className="edit-title"
          />
          <textarea
            name="content"
            value={editedBlog.content}
            onChange={handleChange}
            className="edit-content"
            rows="10"
          />
          <div className="edit-actions">
            <button
              onClick={() => setIsEditing(false)}
              className="cancel-button"
            >
              Cancel
            </button>
            <button onClick={handleSave} className="save-button">
              Save Changes
            </button>
          </div>
        </div>
      ) : (
        <article className="blog-post">
          <h1>{blog.title}</h1>
          <div className="blog-meta">
            <span>By {blog.author?.username || "Anonymous"}</span>
            {blog.createdAt && (
              <span>{new Date(blog.createdAt).toLocaleDateString()}</span>
            )}
          </div>
          <div className="blog-content">{blog.content}</div>
          {canEditDelete && (
            <div className="blog-actions">
              <button onClick={handleEdit} className="edit-button">
                Edit
              </button>
              <button onClick={handleDelete} className="delete-button">
                Delete
              </button>
            </div>
          )}
        </article>
      )}
    </div>
  );
}

export default BlogPost;
