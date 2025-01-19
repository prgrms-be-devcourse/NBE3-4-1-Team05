import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // 백엔드 API 주소

const api = axios.create({
  baseURL: API_BASE_URL, // baseURL을 사용하여 기본 API 주소 설정
  headers:{
    "Content-Type" : "application/json",
  },
  withCredentials: true, // 세션 기반 인증에 필요한 설정 (쿠키 포함)
});

export const uploadImage = async (image) => {
  if (!image) {
    alert("이미지를 선택하세요.");
    return;
  }

  try {

    const formData = new FormData();
    formData.append("image", image);

    const response = await api.post(`${API_BASE_URL}/admin/menus`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    alert("이미지 업로드 성공.");
    return response.data; // 서버에서 반환된 이미지 URL
  } catch (err) {
    console.error("이미지 업로드 실패:", err);
    throw new Error("이미지 업로드에 실패했습니다.");
  }
};

export const addMenu = async (menuData,image) => {
  try {
    const formData = new FormData();
    formData.append("menu", JSON.stringify(menuData)); // JSON으로 변환 후 추가
    formData.append("image", image); 

    const response = await api.post(`${API_BASE_URL}/admin/menus`, formData, {
      headers: {
        "Content-Type": "application/form-data",
      },
    });
    console.log("메뉴 생성 성공:", response.data);
    return response.data; // 서버 응답 데이터
  } catch (err) {
    console.error("메뉴 추가 실패:", err);
    throw new Error("메뉴 추가에 실패했습니다.");
  }
};

// 주문 조회
export const AdminOrderList = () => api.get(`${API_BASE_URL}/admin/order`);

// 주문 상세 조회
export const AdminOrderDetail = (id) => api.get(`${API_BASE_URL}/admin/order/${id}`);

// 주문 취소
export const AdminCancelOrder = (id) => api.delete(`${API_BASE_URL}/admin/order/${id}`);import axios from 'axios';


// 주문 생성
export const createOrder = async (orderData) => {
    try {
      const response = await api.post('/order', orderData);
      return response.data; // 성공적으로 받은 데이터 반환
    } catch (error) {
      // 오류 처리
      console.error('주문 생성 실패:', error.response?.data || error.message);
      throw error; // 오류를 호출한 쪽으로 다시 던짐
    }
  };

// 주문 조회
export const orderList = (email) => api.get(`/order/${email}`);

// 주문 상세 조회
export const orderDetail = (email, id) => api.get(`/order/${email}/${id}`);

// 주문 수정
export const modifyOrder = (email, id, data) => api.put(`/order/${email}/${id}`, data);

// 주문 취소
export const cancelOrder = (email, id) => api.delete(`/order/${email}/${id}`);