import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // 백엔드 API 주소

const api = axios.create({
  baseURL: API_BASE_URL, // baseURL을 사용하여 기본 API 주소 설정
  headers:{
    "Content-Type" : "application/json",
  },
  withCredentials: true, // 세션 기반 인증에 필요한 설정 (쿠키 포함)
});

// 요청 인터셉터 - 토큰 추가
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export const uploadImage = async (image) => {
  if (!image) {
    alert("이미지를 선택하세요.");
    return;
  }

  try {

    const formData = new FormData();
    formData.append("image", image);

    const response = await api.post(`/admin/menus`, formData, {
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

    const response = await api.post(`/admin/menus`, formData, {
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

export const adminApi = {
  getAllOrders: () => api.get(`${API_BASE_URL}/admin/order`),
  getOrderById: (id) => api.get(`${API_BASE_URL}/admin/order/${id}`),
  cancelOrder: (id) => api.delete(`${API_BASE_URL}/admin/order/${id}`)
};

// 주문 생성
export const createOrder = async (orderData) => {
    try {
      const response = await api.post(`/order`, orderData);
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



// 메뉴 전체 조회
// DL/api.js
export const getAllMenu = (page = 0, sort = 'recent') => {
    let sortParam;
    switch(sort) {
        case 'viewsDesc':
            sortParam = 'VIEWS_DESC';
            break;
        case 'recent':
            sortParam = 'RECENT';
            break;
        case 'oldest':
            sortParam = 'OLDEST';
            break;
        case 'priceDesc':
            sortParam = 'PRICE_DESC';
            break;
        case 'priceAsc':
            sortParam = 'PRICE_ASC';
            break;
        default:
            sortParam = 'RECENT';
    }
    return api.get(`/menus?page=${page}&sortType=${sortParam}`);
};

// 특정 메뉴 조회
export const getMenu = (id) => api.get(`/menus/${id}`);