import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // 백엔드 API 주소

const api = axios.create({
  baseURL: API_BASE_URL, // baseURL을 사용하여 기본 API 주소 설정
  headers: {
    "Content-Type": "application/json",
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

// 응답 인터셉터 추가
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    console.error('API 에러:', error.response);
    if (error.response?.status === 302) {
      // 로그인 페이지로 리다이렉트
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const addMenu = async (menuData, image) => {
  try {
    const formData = new FormData();

    const menuRequestDto = {
      productName: menuData.productName,
      description: menuData.description,
      price: Number(menuData.price),
      stock: Number(menuData.stock)
    };

    formData.append("menu", new Blob([JSON.stringify(menuRequestDto)], {
      type: 'application/json'
    }));
    formData.append("image", image);


    formData.forEach((value, key) => {
      console.log(`${key}:`, value);
    });

    const response = await api.post(`/admin/menus`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
      withCredentials: true,
    });

    console.log("메뉴 생성 성공:", response.data);
    return response.data;
  } catch (err) {
    if (err.response?.status === 302) {
      window.location.href = '/login';
      throw new Error('로그인이 필요합니다.');
    }
    throw new Error(err.response?.data?.message || "메뉴 추가에 실패했습니다.");
  }
};

export const adminApi = {
  getAllOrders: async () => {
    try {
      // API 경로 수정
      const response = await api.get('/admin/order');  // 또는 실제 백엔드 경로에 맞게 수정
      return response;
    } catch (error) {
      console.error('getAllOrders Error:', error);
      throw error;
    }
  },
  getOrderDetail: async (orderId) => {
    try {
      // API 경로 수정
      const response = await api.get(`/admin/order/${orderId}`);  // 또는 실제 백엔드 경로에 맞게 수정
      return response;
    } catch (error) {
      console.error('getOrderDetail Error:', error);
      throw error;
    }
  },
  modifyMenu: async (menuId, menuData, image) => {
    try {
      const formData = new FormData();

      const menuBlob = new Blob([JSON.stringify(menuData)], {
        type: 'application/json'
      });
      formData.append('menuRequestDto', menuBlob);

      // 이미지가 있는 경우에만 추가
      /*if (image) {
        formData.append('image', image);
      }*/

      const response = await axios.post(`${API_BASE_URL}/admin/menus/${menuId}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
      return response.data;
    } catch (error) {
      console.error('modifyMenu Error:', error);
      throw error;
    }
  },
  cancelOrder: (orderId) => api.delete(`/admin/order/${orderId}`),
  cancelMenu: (menuId) => api.delete(`/admin/menus/${menuId}`)
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
// export const getAllMenu = () => api.get(`/menus`);
export const getAllMenu = (page = 0) => {
  return api.get(`/menus?page=${page}`);
};

// 특정 메뉴 조회
export const getMenu = (id) => api.get(`/menus/${id}`);