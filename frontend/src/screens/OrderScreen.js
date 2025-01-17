// import React, { useState } from "react";
// import { createOrder } from "../DL/api";

// const OrderPage = () => {
//   // 상태 관리
//   const [menus, setMenus] = useState([]); // 장바구니에 담긴 메뉴 리스트
//   const [email, setEmail] = useState("");
//   const [address, setAddress] = useState("");
//   const [orderMessage, setOrderMessage] = useState("");

//   const addToCart = (menuId, quantity) => {
//     setMenus((prevMenus) => {
//       // 기존 메뉴 목록에서 동일한 menuId가 있는 항목 찾기
//       const existingMenu = prevMenus.find((menu) => menu.menuId === menuId);
  
//       if (existingMenu) {
//         // 동일한 menuId가 있으면 수량을 업데이트
//         return prevMenus.map((menu) =>
//           menu.menuId === menuId
//             ? { ...menu, quantity: menu.quantity + quantity }
//             : menu
//         );
//       } else {
//         // 동일한 menuId가 없으면 새 항목 추가
//         return [...prevMenus, { menuId, quantity }];
//       }
//     });
//   };
//   const removeFromCart = (menuId) => {
//     setMenus((prevMenus) =>
//       prevMenus
//         .map((menu) =>
//           menu.menuId === menuId
//             ? { ...menu, quantity: menu.quantity - 1 } // 수량 감소
//             : menu
//         )
//         .filter((menu) => menu.quantity > 0) // 수량이 0 이하인 항목 제거
//     );
//   };
//   // 주문 데이터 전송

//   const updateQuantity = (menuId, change) => {
//     setMenus((prevMenus) =>
//       prevMenus
//         .map((menu) =>
//           menu.menuId === menuId
//             ? { ...menu, quantity: Math.max(menu.quantity + change, 0) }
//             : menu
//         )
//         .filter((menu) => menu.quantity > 0) // 수량이 0이면 항목 제거
//     );
//   };
 
// const placeOrder = async () => {
//     try {
//       const orderData = { email, address, menus };
//       const response = await createOrder(orderData); // createOrder 호출
//       setOrderMessage(response.message); // 서버에서 반환된 메시지
//     } catch (error) {
//       console.error("주문 실패:", error.response?.data || error.message);
//       setOrderMessage("주문에 실패했습니다.");
//     }
//   };

//   return (
//     <div>
//       <h1>주문 페이지</h1>

//       {/* 상품 리스트 (예제용 하드코딩) */}
//       <div>
//         <h2>상품 리스트</h2>
//         <div>
//           <p>상품 1</p>
//           <button
//         type="button"
//         onClick={() => updateQuantity(1 -1)} // 수량 감소
//         disabled={menu.quantity <= 1} // 수량이 1일 때 비활성화
//       >
//         -
//       </button>
//       <span>{menu.quantity}</span>
//       <button
//         type="button"
//         onClick={() => updateQuantity(1, 1)} // 수량 증가
//       >
//         +
//       </button>
//         </div>
//         <div>
//           <p>상품 2</p>
//           <button
//         type="button"
//         onClick={() => updateQuantity(2, -1)} // 수량 감소
//         disabled={menu.quantity <= 1} // 수량이 1일 때 비활성화
//       >
//         -
//       </button>
//       <span>{menu.quantity}</span>
//       <button
//         type="button"
//         onClick={() => updateQuantity(2, 1)} // 수량 증가
//       >
//         +
//       </button>
//         </div>
//       </div>

//       {/* 장바구니 */}
//       <div>
//         <h2>장바구니</h2>
//         {menus.length > 0 ? (
//           menus.map((menu, index) => (
//             <div key={index}>
//               <p>
//                 메뉴 ID: {menu.menuId}, 수량: {menu.quantity}
//               </p>
//               <button onClick={() => removeFromCart(menu.menuId)}>삭제</button>
//             </div>
//           ))
//         ) : (
//           <p>장바구니에 추가된 상품이 없습니다.</p>
//         )}
//       </div>

//       {/* 주문 정보 입력 */}
//       <div>
//         <h2>주문 정보</h2>
//         <input
//           type="email"
//           placeholder="이메일"
//           value={email}
//           onChange={(e) => setEmail(e.target.value)}
//         />
//         <input
//           type="text"
//           placeholder="주소"
//           value={address}
//           onChange={(e) => setAddress(e.target.value)}
//         />
//         <button onClick={placeOrder}>주문하기</button>
//       </div>

//       {/* 결과 메시지 */}
//       {orderMessage && <p>{orderMessage}</p>}
//     </div>
//   );
// };

// export default OrderPage;
