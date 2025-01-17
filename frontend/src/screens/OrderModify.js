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
    <div
      className="absolute bg-gray-200 rounded-md shadow-lg p-8"
      style={{
        width: "695px",
        height: "850px",
        left: "140px",
        top: "115px",
      }}
    >
      <h1
        className="font-semibold text-black mb-6"
        style={{
          width: "148px",
          height: "39px",
          fontFamily: "'Inter', sans-serif",
          fontStyle: "normal",
          fontWeight: 600,
          fontSize: "32px",
          lineHeight: "39px",
        }}
      >
        주문 수정
      </h1>
      <form className="space-y-6" onSubmit={(e) => e.preventDefault()}>
        <div>
          <label className="block text-gray-700 font-medium mb-2">이메일:</label>
          <input
            type="text"
            value={updatedEmail}
            onChange={(e) => setUpdatedEmail(e.target.value)}
            className="w-full border rounded p-2 text-gray-700"
          />
        </div>
        <div>
          <label className="block text-gray-700 font-medium mb-2">주소:</label>
          <input
            type="text"
            value={updatedAddress}
            onChange={(e) => setUpdatedAddress(e.target.value)}
            className="w-full border rounded p-2 text-gray-700"
          />
        </div>
        <h2 className="font-semibold text-lg">주문한 상품</h2>
        <ul className="space-y-4">
          {omlist.map((item) => (
            <li key={item.menuId} className="flex justify-between items-center">
              <span>{item.name}</span>
              <div className="flex items-center space-x-2">
              <button
              type="button"
              className="bg-red-500 px-3 py-1 rounded text-white hover:bg-red-600"
              onClick={() => handleQuantityChange(item.menuId, -1)}
              >
                  -
              </button>
                      <span>{item.quantity}</span>
                <button
          type="button"
          className="bg-blue-500 px-3 py-1 rounded text-white hover:bg-blue-600"
          onClick={() => handleQuantityChange(item.menuId, 1)}
        >
                  +
                </button>
              </div>
            </li>
          ))}
        </ul>
        <div className="flex justify-start gap-2 mt-6">
        <button
          type="button"
          onClick={handleSubmit}
          className="bg-blue-500 text-white px-4 py-2 rounded shadow hover:bg-blue-600 mr-1"
        >
          주문 수정
        </button>
        <button
        type="button"
        onClick={() => navigate(`/order/detail/${id}`, { state: { email } })}
        className="bg-red-500 text-white px-4 py-2 rounded shadow hover:bg-gray-600"
      >
        돌아가기
      </button>
      </div>
      </form>
    </div>
  );
};

export default OrderModify;
