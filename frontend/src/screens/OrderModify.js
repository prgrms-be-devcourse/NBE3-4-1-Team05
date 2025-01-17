import React, { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import { modifyOrder, orderDetail } from "../DL/api"; // 수정 API와 상세 조회 API

const OrderModify = () => {
  const { id } = useParams(); // URL에서 주문 ID 가져오기
  const location = useLocation(); // 이전 화면에서 전달된 email
  const navigate = useNavigate();

  const email = location.state?.email;
  const [order, setOrder] = useState(null);
  const [updatedEmail, setUpdatedEmail] = useState(email || "");
  const [updatedAddress, setUpdatedAddress] = useState("");
  const [omlist, setOmlist] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchOrderDetail = async () => {
      try {
        const response = await orderDetail(email, id);
        console.log(response.data); // API 응답 확인
        const orderData = response.data.data;
    
        setOrder(orderData);
        setUpdatedEmail(orderData.email);
        setUpdatedAddress(orderData.address);
        setOmlist(orderData.omlist);
      } catch (err) {
        setError("주문 데이터를 불러오는 데 실패했습니다.");
      }
    };

    if (email && id) {
      fetchOrderDetail();
    }
  }, [email, id]);

  const handleQuantityChange = (menuId, change) => {
    setOmlist((prev) =>
      prev.map((item) =>
        item.menuId === menuId
          ? { ...item, quantity: Math.max(item.quantity + change, 1) }
          : item
      )
    );
  };
  

  const handleSubmit = async () => {
    const updatedData = {
      email: updatedEmail,
      address: updatedAddress,
      omlist: omlist.map((item) => ({
        menuId: item.menuId,
        quantity: item.quantity,
      })),
    };
    console.log("Submit email:", email); // 디버깅용
    console.log("Submit id:", id); // 디버깅용
    console.log("Submit data:", updatedData); // 디버깅용

    try {
      await modifyOrder(email, id, updatedData); // API 호출
      alert("주문이 성공적으로 수정되었습니다.");
      navigate("/order/list", { state: { email: updatedEmail } }); // 수정 후 주문 리스트로 이동
    } catch (err) {
        console.error("Error while submitting:", err.response?.data || err.message);
      setError("주문 수정에 실패했습니다.");
    }
  };

  if (error) {
    return <p>{error}</p>;
  }

  if (!order) {
    return <p>로딩 중...</p>;
  }

  return (
    <div>
      <h1>주문 수정</h1>
      <form onSubmit={(e) => e.preventDefault()}>
        <label>
          이메일:
          <input
            type="text"
            value={updatedEmail}
            onChange={(e) => setUpdatedEmail(e.target.value)}
          />
        </label>
        <label>
          주소:
          <input
            type="text"
            value={updatedAddress}
            onChange={(e) => setUpdatedAddress(e.target.value)}
          />
        </label>
        <h2>주문한 상품</h2>
        <ul>
          {omlist.map((item) => (
            <li key={item.menuId}>
              <span>{item.name}</span>
              <button
                type="button"
                onClick={() => handleQuantityChange(item.menuId, -1)}
              >
                -
              </button>
              <span>{item.quantity}</span>
              <button
                type="button"
                onClick={() => handleQuantityChange(item.menuId, 1)}
              >
                +
              </button>
            </li>
          ))}
        </ul>
        <button type="button" onClick={handleSubmit}>
          주문 수정
        </button>
      </form>
    </div>
  );
};

export default OrderModify;
