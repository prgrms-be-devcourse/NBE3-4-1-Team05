import React, { useEffect, useState } from "react";
import { useLocation, useParams, useNavigate } from "react-router-dom";
import { orderDetail, cancelOrder } from "../DL/api"; // 주문 상세 API 호출 함수

const AdminOrderDetail = () => {
  const { id } = useParams(); // URL에서 주문 번호(id) 가져오기
  const location = useLocation(); // 상태로 전달된 email 가져오기
  const navigate = useNavigate();
  const email = location.state?.email;
  const [order, setOrder] = useState(null);
  const [error, setError] = useState(null);
  const formatDateTime = (dateTime) => {
    const date = new Date(dateTime);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 +1 필요
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    return `${year}-${month}-${day} ${hours}:${minutes}`;
  };

  useEffect(() => {
    const fetchOrderDetail = async () => {
      try {
        const response = await orderDetail(email, id);
        setOrder(response.data.data);
      } catch (err) {
        setError("주문 데이터를 불러오는 데 실패했습니다.");
      }
    };

    if (email && id) {
      fetchOrderDetail();
    }
  }, [email, id]);

  const handleCancel = async () => {
    try {
      await cancelOrder(email, id); // 주문 취소 API 호출
      alert("주문이 성공적으로 취소되었습니다.");
      navigate("/admin/order/list", { state: { email } }); // 취소 후 주문 목록으로 이동
    } catch (err) {
      setError("주문 취소에 실패했습니다.");
      console.error(err);
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
      className="absolute bg-gray-100 rounded-lg p-8 shadow-lg"
      style={{
        width: "905px",
        height: "950px",
        left: "50px",
        top: "55px",
      }}
    >
      {/* 주문내역서 제목 */}
      <h1 className="text-2xl font-bold absolute" style={{ left: "40px", top: "20px" }}>
        주문내역서
      </h1>

      {/* 주문 상태와 시간 */}
      <div className="bg-gray-200 p-4 rounded mt-20">
        <p className="font-semibold">배송 상태: {order.deliveryStatus ? "배송중" : "배송 준비중"}</p>
        <p>주문 일시: {formatDateTime(order.order_time)}</p>
      </div>

      {/* 상품 내역 */}
      <div className="mt-8 bg-gray-50 p-4 rounded shadow">
        <h2 className="font-semibold mb-4">상품 내역</h2>
        <ul className="space-y-2">
          {order.omlist.map((item) => (
            <li key={item.name} className="flex justify-between">
              <span>{item.name}</span>
              <span>{item.quantity}개</span>
            </li>
          ))}
        </ul>
        <p className="font-bold text-right mt-4">총 결제 금액: {order.totalPrice}원</p>
      </div>

      {/* 이메일, 주소 폼 */}
      <div className="mt-8 grid grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">이메일</label>
          <input
            type="text"
            value={email}
            readOnly
            className="w-full border-gray-300 rounded p-2"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">주소</label>
          <input
            type="text"
            value={order.address}
            readOnly
            className="w-full border-gray-300 rounded p-2"
          />
        </div>
      </div>

      {/* 안내 사항 */}
      <p className="text-sm text-gray-500 mt-4">
        * 당일 오후 2시 이후 주문은 익일 배송됩니다.
      </p>

      {/* 주문 수정 및 취소 버튼 */}
      <div className="flex justify-end mt-6 space-x-4">
      <button
    className="bg-gray-500 hover:bg-gray-600 text-white font-semibold px-4 py-2 rounded"
    onClick={() => navigate("/order/list", { state: { email } })}
  >
    돌아가기
  </button>

        <button
          className="bg-red-500 hover:bg-red-600 text-white font-semibold px-4 py-2 rounded"
          onClick={handleCancel}
        >
          주문 취소
        </button>
      </div>
    </div>
  );
};

export default AdminOrderDetail;
