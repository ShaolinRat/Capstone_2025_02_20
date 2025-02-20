import axios from "axios";

const BASE_URL = "http://localhost:8080/api";

const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

// Add auth token interceptor
api.interceptors.request.use(
  (config) => {
    const user = JSON.parse(localStorage.getItem("user"));
    if (user && user.token) {
      config.headers.Authorization = `Bearer ${user.token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export const blogService = {
  // Get all blogs
  getAllBlogs: async () => {
    try {
      const response = await api.get("/blogs");
      console.log("Blog response:", response.data);
      return response.data;
    } catch (error) {
      console.error("Error fetching blogs:", error.response || error);
      throw new Error(error.response?.data?.message || "Failed to fetch blogs");
    }
  },

  // Get single blog
  getBlogById: async (id) => {
    try {
      const response = await api.get(`/blogs/${id}`);
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || "Failed to fetch blog");
    }
  },

  // Create new blog
  createBlog: async (blogData) => {
    try {
      const response = await api.post("/blogs", blogData);
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || "Failed to create blog");
    }
  },

  // Update blog
  updateBlog: async (id, blogData) => {
    try {
      const response = await api.put(`/blogs/${id}`, blogData);
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || "Failed to update blog");
    }
  },

  // Delete blog
  deleteBlog: async (id) => {
    try {
      await api.delete(`/blogs/${id}`);
    } catch (error) {
      throw new Error(error.response?.data?.message || "Failed to delete blog");
    }
  },
};

export default api;
