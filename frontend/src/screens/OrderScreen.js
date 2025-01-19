import React, { useState, useEffect } from "react";
import { createOrder, getMenu } from "../DL/api";

const OrderPage = () => {
    const [menus, setMenus] = useState([]);
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [orderMessage, setOrderMessage] = useState("");
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const loadProducts = async () => {
            try {
                const data = await getMenu();
                setProducts(data); // API로 받은 데이터를 상태에 저장
            } catch (error) {
                console.error("상품 리스트를 불러오는 중 오류 발생:", error);
            }
        };

        loadProducts();
    }, []);

    const addToCart = (menuId, menuName, quantity, price) => {
        setMenus((prevMenus) => {
            const existingMenu = prevMenus.find((menu) => menu.menuId === menuId);
            if (existingMenu) {
                return prevMenus.map((menu) =>
                    menu.menuId === menuId
                        ? { ...menu, quantity: menu.quantity + quantity }
                        : menu
                );
            } else {
                return [...prevMenus, { menuId, menuName, quantity, price }];
            }
        });
    };

    const updateQuantity = (menuId, change) => {
        setMenus((prevMenus) =>
            prevMenus.map((menu) =>
                menu.menuId === menuId
                    ? { ...menu, quantity: Math.max(menu.quantity + change, 1) }
                    : menu
            )
        );
    };

    const removeFromCart = (menuId) => {
        setMenus((prevMenus) => prevMenus.filter((menu) => menu.menuId !== menuId));
    };

    const calculateTotalPrice = () => {
        return menus.reduce((total, menu) => total + menu.quantity * menu.price, 0);
    };

    const resetOrderPage = () => {
        setMenus([]);
        setEmail("");
        setAddress("");
        setOrderMessage("");
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
            console.log("Order Data:", orderData); // 전달되는 데이터 확인
            const response = await createOrder(orderData);
            alert(`주문이 완료되었습니다! 총 결제 금액: ${calculateTotalPrice()}원`);
            resetOrderPage();
            return response.data;
        } catch (error) {
            console.error("주문 실패 이유:", error.message);
            alert("주문에 실패했습니다. 다시 시도해주세요.");
        }
    };


    return (
        <div className="relative bg-gray-100 min-h-screen flex items-center justify-center py-8">
            <div className="relative bg-gray-200 w-full max-w-[540px] rounded-md shadow-lg p-6">
                <h1 className="text-[28px] font-semibold text-gray-800 mb-6 text-center">
                    주문 페이지
                </h1>

                {/* 상품 리스트 */}
                <div className="bg-white rounded-md shadow-md p-4 mb-6">
                    <h2 className="text-[24px] font-semibold mb-4">상품 리스트</h2>
                    {[
                        { id: 1, name: "케냐 에스프레소", price: 5000 },
                        { id: 2, name: "콜롬비아 드립백", price: 3000 },
                        { id: 3, name: "에티오피아 원두", price: 7000 },
                    ].map((product) => (
                        <div
                            key={product.id}
                            className="flex justify-between items-center border-b border-gray-300 py-2"
                        >
                            <p>
                                {product.name} - {product.price}원
                            </p>
                            <button
                                onClick={() =>
                                    addToCart(product.id, product.name, 1, product.price)
                                }
                                className="px-4 py-2 text-white rounded-md hover:opacity-90"
                                style={{ backgroundColor: "#9EBA99" }}
                            >
                                장바구니에 추가
                            </button>
                        </div>
                    ))}
                </div>

                {/* 장바구니 섹션 */}
                <div className="bg-white rounded-md shadow-md p-4 mb-6">
                    <h2 className="text-[24px] font-semibold mb-4">장바구니 🛒</h2>
                    {menus.length > 0 ? (
                        menus.map((menu, index) => (
                            <div
                                key={index}
                                className="flex justify-between items-center border-b border-gray-300 py-2"
                            >
                                <p>
                                    {menu.menuName}, 수량: {menu.quantity}, 가격:{" "}
                                    {menu.quantity * menu.price}원
                                </p>
                                <button
                                    onClick={() => removeFromCart(menu.menuId)}
                                    className="flex items-center justify-center w-8 h-8 text-gray-500 hover:text-red-500"
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

                        {/* 이메일 입력 */}
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

                        {/* 주소 입력 */}
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

                        <button
                            onClick={placeOrder}
                            className="px-4 py-2 text-white rounded-md hover:opacity-90"
                            style={{ backgroundColor: "#9EBA99" }}
                        >
                            주문하기
                        </button>
                    </div>

                    {/* 안내 사항 */}
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
