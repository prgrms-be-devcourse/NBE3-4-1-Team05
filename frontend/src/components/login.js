import React, { useState } from "react";
import { FaRegUserCircle } from "react-icons/fa";
import { RiLockPasswordLine } from "react-icons/ri";
import axios from "axios";
import "../index.css";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "/perform_login",
        new URLSearchParams({
          username,
          password,
        }),
        {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          }
        }
      );

      console.log("Login successful:", response.data);
      window.location.href = "/admin/order";
    } catch (err) {
      console.error("Login failed:", err);
      setError("Invalid username or password");
    }
  };

  return (
    <div className="flex items-center justify-center w-screen h-screen bg-gray-300">
      <div className="relative bg-white w-[400px] p-8 rounded-lg shadow-lg">
        {/* Profile Icon */}
        <div className="absolute -top-10 left-1/2 transform -translate-x-1/2 bg-gray-300 rounded-full w-20 h-20 flex items-center justify-center shadow-md">
          <FaRegUserCircle className="text-4xl text-gray-600" />
        </div>

        {/* Login Form */}
        <div className="mt-12 text-center">
          <h2 className="text-lg font-semibold text-gray-700">
            Login to admin account
          </h2>
        </div>
        <form onSubmit={handleSubmit} className="mt-6">
          {/* Username Field */}
          <div className="flex items-center border-b border-gray-300 py-2">
            <FaRegUserCircle className="text-gray-500 mr-2" />
            <input
              type="text"
              placeholder="Admin"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="w-full focus:outline-none text-gray-700"
            />
          </div>

          {/* Password Field */}
          <div className="flex items-center border-b border-gray-300 py-2 mt-4">
            <RiLockPasswordLine className="text-gray-500 mr-2" />
            <input
              type="password"
              placeholder="********"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full focus:outline-none text-gray-700"
            />
          </div>

          {/* Remember Me and Forgot Password */}
          <div className="flex items-center justify-between mt-4">
            <label className="flex items-center text-sm text-gray-600">
              <input type="checkbox" className="mr-2" />
              Remember me
            </label>
            <button
              type="button"
              onClick={() => alert("Feature not implemented")}
              className="text-sm text-gray-600 hover:underline"
            >
              Forgot password?
            </button>
          </div>

          {/* Login Button */}
          <button
            type="submit"
            className="w-full bg-gray-700 text-white py-2 mt-6 rounded-lg hover:bg-gray-800 transition"
          >
            LOGIN
          </button>
        </form>
        {error && (
          <p className="mt-4 text-sm text-red-600 bg-red-100 p-2 rounded">
            {error}
          </p>
        )}
      </div>
    </div>
  );
};

export default Login;
