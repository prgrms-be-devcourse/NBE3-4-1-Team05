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
