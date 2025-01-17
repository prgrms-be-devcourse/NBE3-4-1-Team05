import React, { useState } from "react";
import OrderList from "./OrderList";
import { useNavigate } from "react-router-dom";

const EmailInput = () => {
  const [email, setEmail] = useState("");
  const [showOrders, setShowOrders] = useState(false);
  const navigate = useNavigate()

  const handleInputChange = (e) => {
    setEmail(e.target.value);
  };

  const handleOrderClick = () => {
    navigate("/order/list", { state: { email }}); // 원하는 경로로 이동
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setShowOrders(true); // 주문 목록 조회 컴포넌트 표시
  };

  return (
    <div>
      {!showOrders ? (
        <form onSubmit={handleSubmit}>
          <label>
            이메일 입력:
            <input
              type="email"
              value={email}
              onChange={handleInputChange}
              required
            />
          </label>
          <button type="submit"  onClick={handleOrderClick}>주문 조회</button>
        </form>
      ) : (
        <OrderList email={email} /> // 이메일을 OrderList 컴포넌트로 전달
      )}
    </div>
  );
};

export default EmailInput;
