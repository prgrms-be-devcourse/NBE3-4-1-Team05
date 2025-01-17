import React, { useEffect, useState } from "react";
import { useLocation, useParams, useNavigate } from "react-router-dom";
import { orderDetail, cancelOrder } from "../DL/api"; // 주문 상세 API 호출 함수

const OrderDetail = () => {
  const { id } = useParams(); // URL에서 주문 번호(id) 가져오기
  const location = useLocation(); // 상태로 전달된 email 가져오기
  const navigate = useNavigate();
  const email = location.state?.email;
  const [order, setOrder] = useState(null);
  const [error, setError] = useState(null);

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

  const handleModify = () => {
    navigate(`/order/modify/${id}`, { state: { email } }); // 수정 화면으로 이동
  };

  const handleCancel = async () => {
    try {
      await cancelOrder(email, id); // 주문 취소 API 호출
      alert("주문이 성공적으로 취소되었습니다.");
      navigate("/order/list", { state: { email } }); // 취소 후 주문 목록으로 이동
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
    <div className="order-detail-container">
      <h1 className="text-xl font-bold">주문내역서</h1>
      <div className="order-detail-header">
        <p>주문 상태: {order.deliveryStatus ? "배송중" : "배송 준비중"}</p>
        <p>주문 일시: {order.order_time}</p>
      </div>
      <div className="order-detail-products">
        <h2>상품 내역</h2>
        <ul>
          {order.omlist.map((item) => (
            <li key={item.name}>
              {item.name} - {item.quantity}개
            </li>
          ))}
        </ul>
        <p className="font-bold">총 결제 금액: {order.totalPrice}원</p>
      </div>
      <div className="order-detail-footer">
        <form>
          <label>
            이메일:
            <input type="text" defaultValue={email} readOnly />
          </label>
          <label>
            주소:
            <input type="text" defaultValue={order.address} readOnly />
          </label>
        </form>
        <p className="text-sm text-gray-500">
          * 당일 오후 2시 이후 주문은 익일 배송됩니다.
        </p>
      </div>
      <div className="order-detail-actions">
      <button
          className="bg-blue-500 text-white p-2 rounded mr-2"
          onClick={handleModify}
        >
          주문 수정
        </button>
        <button
          className="bg-red-500 text-white p-2 rounded"
          onClick={handleCancel}
        >
          주문 취소
        </button>
      </div>
    </div>
  );
};

export default OrderDetail;
