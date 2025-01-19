import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // 백엔드 API 주소

const api = axios.create({
    baseURL: API_BASE_URL, // baseURL을 사용하여 기본 API 주소 설정
    headers: {
        'Content-Type': 'application/json ',
    },
    withCredentials: true, // 세션 기반 인증에 필요한 설정 (쿠키 포함)
});

// 메뉴 전체 조회
export const getAllMenu = () => api.get(`/menus`);

// 특정 메뉴 조회
export const getMenu = (id) => api.get(`/menus/${id}`);

// // 메뉴 전체 목록 조회
// export const getMenuList = async () => {
//     try {
//         const response = await api.get('/menus');
//         return response.data;
//     } catch (error) {
//         console.error('메뉴 목록 조회 실패:', error.response?.data || error.message);
//         throw error;
//     }
// };
//
// // 특정 메뉴 상세 조회
// export const getMenuDetail = async (id) => {
//     try {
//         const response = await api.get(`/menus/${id}`);
//         return response.data;
//     } catch (error) {
//         console.error('메뉴 상세 조회 실패:', error.response?.data || error.message);
//         throw error;
//     }
// };