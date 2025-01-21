import React, { useState, useEffect } from "react";
import { createOrder } from "../DL/api";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";

const OrderPage = () => {
    const [menus, setMenus] = useState([]); // 장바구니 상태
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const navigate = useNavigate(); // useNavigate 훅 추가

    useEffect(() => {
        const storedCart = localStorage.getItem("cartItems");
        if (storedCart) {
            try {
                const parsedCart = JSON.parse(storedCart);
                setMenus(parsedCart);
            } catch (error) {
                setMenus([]);
            }
        }
    }, []);

    useEffect(() => {
        // 페이지 진입 시 로컬 스토리지에서 데이터 로드
        const storedCart = localStorage.getItem("cartItems");
        if (storedCart) {
            setMenus(JSON.parse(storedCart));
        }
    }, []);
    
    const updateQuantity = (menuId, change) => {
        setMenus((prevMenus) => {
            const updatedMenus = prevMenus.map((menu) =>
                menu.menuId === menuId
                    ? { ...menu, quantity: Math.max(menu.quantity + change, 1) }
                    : menu
            );
            localStorage.setItem("cartItems", JSON.stringify(updatedMenus)); // 로컬 스토리지 갱신
            return updatedMenus;
        });
    };

   const removeFromCart = (menuId) => {
        setMenus((prevMenus) => {
            const updatedMenus = prevMenus.filter((menu) => menu.menuId !== menuId);
            localStorage.setItem("cartItems", JSON.stringify(updatedMenus)); // 로컬 스토리지 갱신
            return updatedMenus;
        });
    };

    const calculateTotalPrice = () => {
        return menus.reduce((total, menu) => total + menu.quantity * menu.price, 0);
    };

    const resetOrderPage = () => {
        setMenus([]);
        setEmail("");
        setAddress("");
        localStorage.removeItem("cartItems"); // 로컬 스토리지 초기화
    };

    const placeOrder = async () => {
        if (!email || !address) {
            alert("이메일과 주소를 입력해주세요.");
            return;
        }

        if (menus.length === 0) {
            alert("장바구니가 비어 있습니다.");
            return;
        }

        try {
            const orderData = { email, address, menus };
            console.log("Order Data:", orderData);
            const response = await createOrder(orderData);
            alert(`주문이 완료되었습니다! 총 결제 금액: ${calculateTotalPrice()}원`);
            resetOrderPage();
            navigate("/");
            return response.data;
        } catch (error) {
            console.error("주문 실패 이유:", error.message);
            alert("주문에 실패했습니다. 다시 시도해주세요.");
        }
    };

    return (
        
        <div className="relative bg-gray-100 min-h-screen flex items-center justify-center py-8">
            <Navbar />
            <div className="relative bg-gray-200 w-full max-w-[540px] rounded-md shadow-lg p-6">
                <h1 className="text-[28px] font-semibold text-gray-800 mb-6 text-center">
                    주문 페이지
                </h1>

                {/* 장바구니 섹션 */}
                <div className="bg-white rounded-md shadow-md p-4 mb-6">
                    <h2 className="text-[24px] font-semibold mb-4">장바구니 🛒</h2>
                    {menus.length > 0 ? (
                        menus.map((menu, index) => (
                            <div
    key={index}
    className="flex justify-between items-center border-b border-gray-300 py-2"
>
    <div>
        <p>
            {menu.menuName}, 수량: {menu.quantity}, 가격:{" "}
            {menu.quantity * menu.price}원
        </p>
    </div>
    <div className="flex items-center space-x-2">
        {/* 수량 감소 버튼 */}
        <button
            onClick={() => updateQuantity(menu.menuId, -1)}
            className="w-8 h-8 flex items-center justify-center bg-gray-200 text-gray-600 rounded hover:bg-gray-300"
        >
            -
        </button>
        <span className="w-8 text-center">{menu.quantity}</span>
        {/* 수량 증가 버튼 */}
        <button
            onClick={() => updateQuantity(menu.menuId, 1)}
            className="w-8 h-8 flex items-center justify-center bg-gray-200 text-gray-600 rounded hover:bg-gray-300"
        >
            +
        </button>
        {/* 삭제 버튼 */}
        <button
            onClick={() => removeFromCart(menu.menuId)}
            className="w-8 h-8 flex items-center justify-center text-gray-500 hover:text-red-500"
        >
            <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
                className="w-5 h-5"
            >
                <line x1="18" y1="6" x2="6" y2="18" />
                <line x1="6" y1="6" x2="18" y2="18" />
            </svg>
            </button>
            </div>
            </div>

                        ))
                    ) : (
                        <p className="text-gray-700">장바구니에 추가된 상품이 없습니다.</p>
                    )}
                </div>

                {/* 총 결제 금액 */}
                <p className="text-[18px] font-semibold text-right mb-6">
                    총 결제 금액: {calculateTotalPrice()}원
                </p>

                {/* 주문 정보 */}
                <div className="grid grid-cols-1 gap-4">
                    <div className="bg-white rounded-md shadow-md p-4">
                        <h2 className="text-[20px] font-semibold mb-4 text-gray-700">주문 정보</h2>

                        <label htmlFor="email" className="block text-gray-600 mb-2 font-medium">
                            이메일
                        </label>
                        <input
                            id="email"
                            type="email"
                            placeholder="example@email.com"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="w-full px-4 py-2 mb-4 border border-gray-300 rounded-md shadow-sm"
                        />

                        <label htmlFor="address" className="block text-gray-600 mb-2 font-medium">
                            주소
                        </label>
                        <input
                            id="address"
                            type="text"
                            placeholder="주소를 입력하세요"
                            value={address}
                            onChange={(e) => setAddress(e.target.value)}
                            className="w-full px-4 py-2 mb-4 border border-gray-300 rounded-md shadow-sm"
                        />
                        <div className="flex justify-center space-x-4 mt-4">
                            <button
                            onClick={placeOrder}
                            className="px-4 py-2 text-white rounded-md hover:opacity-90"
                            style={{ backgroundColor: "#9EBA99" }}
                            >
                                주문하기
                            </button>
                            <button
                            onClick={() => navigate("/")} // 메인 화면으로 이동
                            className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
                            >
                                돌아가기
                            </button>
                        </div>
                    </div>
                    <div className="bg-white rounded-md shadow-md p-4">
                        <h2 className="text-[20px] font-semibold mb-4 text-gray-700">안내 사항</h2>
                        <ul className="list-disc list-inside text-gray-600">
                            <li>당일 오후 2시 이후 주문은 익일 배송됩니다.</li>
                            <li>발송 전 주문 건에 한하여 주소 수정 및 취소가 가능합니다.</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default OrderPage;
