import React, { useState, useEffect } from "react";
import { orderList } from "../DL/api";
import { useLocation, useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";

const OrderList = () => {
  const location = useLocation(); // useLocation 훅으로 state 받기
  const navigate = useNavigate();
  const [orders, setOrders] = useState([]);
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

  const formatOrderNumber = (date, id) => {
    // 날짜를 "yyyyMMdd" 형식으로 변환
    const formattedDate = new Date(date).toISOString().split("T")[0].replace(/-/g, "");

    // 주문 ID를 4자리로 변환
    const formattedOrderId = String(id).padStart(4, "0");

    // 최종 문자열 반환
    return `${formattedDate}-${formattedOrderId}`;
  };

  const email = location.state?.email; // 전달된 email 값 확인

  useEffect(() => {
    if (email) {
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
      <div className="min-h-screen bg-gray-100">
        <Navbar />
        <div className="w-full p-8 pt-20">
          <h1 className="text-3xl font-bold mb-6 border-b-2 pb-4 text-gray-800">
            주문 조회
          </h1>
          {orders.length > 0 ? (
              <table className="table-auto w-full border-collapse border border-gray-300 bg-white rounded-lg shadow-md">
                <thead className="bg-gray-200">
                <tr>
                  <th className="border border-gray-300 px-6 py-3 text-left text-sm font-medium text-gray-700">
                    주문 번호
                  </th>
                  <th className="border border-gray-300 px-6 py-3 text-left text-sm font-medium text-gray-700">
                    상품 내역
                  </th>
                  <th className="border border-gray-300 px-6 py-3 text-left text-sm font-medium text-gray-700">
                    주문일자
                  </th>
                  <th className="border border-gray-300 px-6 py-3 text-left text-sm font-medium text-gray-700">
                    배송상태
                  </th>
                </tr>
                </thead>
                <tbody>
                {orders.map((order) => (
                    <tr
                        key={order.id}
                        className="hover:bg-gray-100 transition-colors duration-200"
                    >
                      <td className="border border-gray-300 px-6 py-4">
                        <button
                            className="cursor-pointer text-blue-500 underline hover:text-blue-700 bg-transparent border-none p-0"
                            onClick={() =>
                                navigate(`/order/detail/${order.id}`, { state: { email } })
                            }
                        >
                          {formatOrderNumber(order.order_time, order.id)}
                        </button>
                      </td>
                      <td className="border border-gray-300 px-6 py-4">
                        {order.omlist.map(
                            (item, index) =>
                                `${item.name} ${item.quantity}개${
                                    index < order.omlist.length - 1 ? ", " : ""
                                }`
                        )}
                      </td>
                      <td className="border border-gray-300 px-6 py-4">
                        {formatDateTime(order.order_time)}
                      </td>
                      <td className="border border-gray-300 px-6 py-4">
                                    <span
                                        className={`px-3 py-1 rounded-lg font-semibold ${
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
              <p className="text-gray-500 text-center mt-8">
                주문 내역이 없습니다.
              </p>
          )}
        </div>
      </div>
  );
};

export default OrderList;
