import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";
const EmailInput = () => {
  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    setEmail(e.target.value);
  };

  const handleOrderClick = (e) => {
    e.preventDefault();
    navigate("/order/list", { state: { email } }); // 원하는 경로로 이동
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-200">
      <Navbar />
      <div className="bg-white w-96 rounded-md shadow-md p-8 relative text-center">
        {/* User Icon */}
        <div className="absolute -top-10 left-1/2 transform -translate-x-1/2 bg-gray-300 rounded-full p-5">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth="1.5"
            stroke="currentColor"
            className="w-12 h-12 text-gray-600"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M15.75 10.5c0 2.485-2.015 4.5-4.5 4.5s-4.5-2.015-4.5-4.5S9.765 6 12.25 6s4.5 2.015 4.5 4.5z"
            />
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M21 21c-1.406-3.052-4.514-5.25-8-5.25S6.406 17.948 5 21h16z"
            />
          </svg>
        </div>

        <h1 className="mt-8 text-2xl font-semibold text-gray-800">
          Order List For Email
        </h1>
        <form onSubmit={handleOrderClick} className="mt-6">
          <div className="relative">
            <input
              type="email"
              value={email}
              onChange={handleInputChange}
              required
              placeholder="Email"
              className="w-full px-4 py-2 text-gray-700 bg-gray-200 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-gray-400"
              style={{
                paddingLeft: "2.2rem", // 입력 텍스트와 placeholder의 시작 위치를 동일하게 설정
                paddingTop: "0.50rem", // 입력 텍스트가 placeholder와 수직 정렬되도록 설정
              }}
            />
            <span className="absolute inset-y-0 left-0 flex items-center pl-3">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth="1.5"
                stroke="currentColor"
                className="w-5 h-5 text-gray-400"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M21.75 8.25v7.5M18 12l-3 3m3-3l3-3M4.5 3.75h15M19.5 21.75h-15M19.5 3.75a2.25 2.25 0 012.25 2.25v12a2.25 2.25 0 01-2.25 2.25m-15 0A2.25 2.25 0 012.25 18V6a2.25 2.25 0 012.25-2.25"
                />
              </svg>
            </span>
          </div>
          <button
            type="submit"
            className="w-full mt-4 bg-gray-600 text-white py-2 px-4 rounded-md hover:bg-gray-700 transition duration-200"
          >
            Search
          </button>
        </form>
      </div>
    </div>
  );
};

export default EmailInput;
