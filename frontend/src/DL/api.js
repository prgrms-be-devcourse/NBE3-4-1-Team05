import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // 백엔드 API 주소

const api = axios.create({
  baseURL: API_BASE_URL, // baseURL을 사용하여 기본 API 주소 설정
  headers: {
    'Content-Type': 'application/json ', 
  },
  withCredentials: true, // 세션 기반 인증에 필요한 설정 (쿠키 포함)
});


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