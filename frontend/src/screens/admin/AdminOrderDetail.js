import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { adminApi } from '../../DL/api';

const AdminOrderDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [order, setOrder] = useState({
        deliveryStatus: false,
        order_time: '',
        omlist: [],
        totalPrice: 0,
        email: '',
        address: ''
    });

    useEffect(() => {
        const fetchOrderDetail = async () => {
            try {
                const response = await adminApi.getOrderDetail(id);
                if (!response || !response.data) {
                    throw new Error('데이터가 없습니다');
                }
                console.log('Order Detail:', response.data.data);
                setOrder(response.data.data);
            } catch (err) {
                console.error('Error fetching order detail:', err);
            }
        };
        if (id) {
            fetchOrderDetail();
        }
    }, [id]);

    const formatDateTime = (dateTime) => {
        if (!dateTime) return '-';
        try {
            const date = new Date(dateTime);
            return date.toLocaleString('ko-KR', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit'
            });
        } catch (error) {
            console.error('날짜 포맷팅 에러:', error);
            return '-';
        }
    };
        
    const handleCancel = async () => {
        if (!window.confirm('정말로 이 주문을 취소하시겠습니까?')) {
            return;
        }

        if (order.deliveryStatus) {
            alert('배송이 시작된 주문은 취소할 수 없습니다.');
            return;
        }

        try {
            await adminApi.cancelOrder(id);
            alert('주문이 성공적으로 취소되었습니다.');
            navigate('/admin/order');
        } catch (error) {
            console.error('주문 취소 실패:', error);
            alert('주문 취소에 실패했습니다.');
        } finally {
        }
    };

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
                        value={order.email}
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

            {/* 취소 및 돌아가기 버튼 */}
            <div className="flex justify-end mt-6 space-x-4">
                <button
                    className="bg-red-500 hover:bg-red-600 text-white font-semibold px-4 py-2 rounded"
                    onClick={handleCancel}
                >
                    주문 취소
                </button>
                <button
                    className="bg-gray-500 hover:bg-gray-600 text-white font-semibold px-4 py-2 rounded"
                    onClick={() => navigate("/admin/order")}
                >
                    돌아가기
                </button>
            </div>
        </div>
    );
};

export default AdminOrderDetail;