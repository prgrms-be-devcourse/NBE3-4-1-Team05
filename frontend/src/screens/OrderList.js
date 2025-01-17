import React, { useState, useEffect } from "react";
import { orderList } from "../DL/api";
import { useLocation, useNavigate } from "react-router-dom";

const OrderList = () => {
  const location = useLocation(); // useLocation 훅으로 state 받기
  const navigate = useNavigate();
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState(null);

  const email = location.state?.email; // 전달된 email 값 확인

  useEffect(() => {
    if(email){
      const fetchOrders = async () => {
        try {
          const response = await orderList(email);
          setOrders(response.data.data);
        } catch (err) {
          setError("주문 데이터를 불러오는 데 실패했습니다.");
          console.error(err);
        }
      };

      fetchOrders();
    }
  }, [email]);

  if (error) {
    return <p className="text-red-500">{error}</p>;
  }

  return (
    <div className="p-4 bg-gray-100">
      <h1 className="text-2xl font-bold mb-4 border-b-2 pb-2">주문 조회</h1>
      {orders.length > 0 ? (
        <table className="table-auto w-full border-collapse border border-gray-300">
          <thead className="bg-gray-200">
            <tr>
              <th className="border border-gray-300 px-4 py-2">주문 번호</th>
              <th className="border border-gray-300 px-4 py-2">상품 내역</th>
              <th className="border border-gray-300 px-4 py-2">주문일자</th>
              <th className="border border-gray-300 px-4 py-2">배송상태</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((order) => (
              <tr key={order.id} className="hover:bg-gray-100">
           <td>
            <button
            className="cursor-pointer text-blue-500 underline hover:text-blue-700 bg-transparent border-none p-0"
            onClick={() => navigate(`/order/detail/${order.id}`, { state: { email } })}>
              {order.id}
              </button>
                </td>
                <td className="border border-gray-300 px-4 py-2">
                  {order.omlist.map(
                    (item, index) =>
                      `${item.name} ${item.quantity}개${
                        index < order.omlist.length - 1 ? ", " : ""
                      }`
                  )}
                </td>
                <td className="border border-gray-300 px-4 py-2">
                  {order.order_time}
                </td>
                <td className="border border-gray-300 px-4 py-2">
                  <span
                    className={`px-2 py-1 rounded ${
                      order.deliveryStatus
                        ? "bg-green-200 text-green-700"
                        : "bg-yellow-200 text-yellow-700"
                    }`}
                  >
                    {order.deliveryStatus ? "배송중" : "배송 준비중"}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p className="text-gray-500">주문 내역이 없습니다.</p>
      )}
    </div>
  );
};

export default OrderList;
