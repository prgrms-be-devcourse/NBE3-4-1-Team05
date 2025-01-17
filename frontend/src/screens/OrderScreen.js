import React, { useState } from "react";
import { createOrder } from "../DL/api";

const OrderPage = () => {
  // 상태 관리
  const [menus, setMenus] = useState([]); // 장바구니에 담긴 메뉴 리스트
  const [email, setEmail] = useState("");
  const [address, setAddress] = useState("");
  const [orderMessage, setOrderMessage] = useState("");

  // 장바구니에 메뉴 추가
  const addToCart = (menuId, quantity) => {
    setMenus((prevMenus) => [
      ...prevMenus,
      { menuId, quantity }
    ]);
  };

  // 장바구니에서 메뉴 삭제
  const removeFromCart = (menuId) => {
    setMenus((prevMenus) => prevMenus.filter((menu) => menu.menuId !== menuId));
  };

  // 주문 데이터 전송
 
const placeOrder = async () => {
    try {
      const orderData = { email, address, menus };
      const response = await createOrder(orderData); // createOrder 호출
      setOrderMessage(response.message); // 서버에서 반환된 메시지
    } catch (error) {
      console.error("주문 실패:", error.response?.data || error.message);
      setOrderMessage("주문에 실패했습니다.");
    }
  };

  return (
    <div>
      <h1>주문 페이지</h1>

      {/* 상품 리스트 (예제용 하드코딩) */}
      <div>
        <h2>상품 리스트</h2>
        <div>
          <p>상품 1</p>
          <button onClick={() => addToCart(1, 1)}>추가</button>
        </div>
        <div>
          <p>상품 2</p>
          <button onClick={() => addToCart(2, 1)}>추가</button>
        </div>
      </div>

      {/* 장바구니 */}
      <div>
        <h2>장바구니</h2>
        {menus.length > 0 ? (
          menus.map((menu, index) => (
            <div key={index}>
              <p>
                메뉴 ID: {menu.menuId}, 수량: {menu.quantity}
              </p>
              <button onClick={() => removeFromCart(menu.menuId)}>삭제</button>
            </div>
          ))
        ) : (
          <p>장바구니에 추가된 상품이 없습니다.</p>
        )}
      </div>

      {/* 주문 정보 입력 */}
      <div>
        <h2>주문 정보</h2>
        <input
          type="email"
          placeholder="이메일"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="text"
          placeholder="주소"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
        />
        <button onClick={placeOrder}>주문하기</button>
      </div>

      {/* 결과 메시지 */}
      {orderMessage && <p>{orderMessage}</p>}
    </div>
  );
};

export default OrderPage;
