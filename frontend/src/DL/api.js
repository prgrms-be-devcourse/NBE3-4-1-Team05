import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // 백엔드 API 주소

const api = axios.create({
    baseURL: API_BASE_URL, // baseURL을 사용하여 기본 API 주소 설정
    headers: {
        'Content-Type': 'application/json ',
    },
    withCredentials: true, // 세션 기반 인증에 필요한 설정 (쿠키 포함)
});

export const uploadImage = async (image) => {
    if (!image) {
        alert("이미지를 선택하세요.");
        return;
    }

    const formData = new FormData();
    formData.append("image", image);
  
    try {
      const response = await axios.post(`${API_BASE_URL}/upload`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      return response.data; // 서버에서 반환된 이미지 URL
    } catch (err) {
      console.error("이미지 업로드 실패:", err);
      throw new Error("이미지 업로드에 실패했습니다.");
    }
  };

  export const addMenu = async (menuData) => {
    try {
      const response = await axios.post(`${API_BASE_URL}/menus`, menuData, {
        headers: {
          "Content-Type": "application/json",
        },
      });
      return response.data; // 서버 응답 데이터
    } catch (err) {
      console.error("메뉴 추가 실패:", err);
      throw new Error("메뉴 추가에 실패했습니다.");
    }
  };