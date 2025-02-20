import axios from "axios";

const BASE_URL = "http://localhost:8080/api/auth";

export const authService = {
  login: async (username, password) => {
    try {
      const response = await axios.post(
        `${BASE_URL}/login`,
        new URLSearchParams({
          username: username,
          password: password,
        }),
        {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          withCredentials: true, // Important for handling cookies
        }
      );

      // The response will now contain username instead of token
      if (response.data.success) {
        localStorage.setItem(
          "user",
          JSON.stringify({
            username: response.data.username,
          })
        );
      }
      return response.data;
    } catch (error) {
      console.error("Login error:", error.response?.data || error.message);
      throw error.response?.data || error;
    }
  },

  register: async (username, password) => {
    try {
      const response = await axios.post(`${BASE_URL}/register`, {
        username,
        password,
      });
      return response.data;
    } catch (error) {
      console.error(
        "Registration error:",
        error.response?.data || error.message
      );
      throw new Error(error.response?.data?.message || "Registration failed");
    }
  },

  logout: () => {
    localStorage.removeItem("user");
  },

  getCurrentUser: () => {
    return JSON.parse(localStorage.getItem("user"));
  },

  // Added helper method to check authentication status
  isAuthenticated: () => {
    const user = localStorage.getItem("user");
    return !!user && !!JSON.parse(user).token;
  },
};

// Modify the axios interceptor since we're not using JWT tokens anymore
axios.interceptors.request.use(
  (config) => {
    config.withCredentials = true; // Important for sending cookies
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default authService;
