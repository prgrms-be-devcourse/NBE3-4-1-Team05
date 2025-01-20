// ProductCard.js
import React from 'react';
import { adminApi } from '../../DL/api';
import { useNavigate } from 'react-router-dom';

const AdminProductCard = ({ image, title, price, id, description, stock, onClick}) => {
    const API_BASE_URL = 'http://localhost:8080';
    const navigate = useNavigate();

    const handleCancel = async () => {
        try {
            if (window.confirm('정말로 삭제하시겠습니까?')) {
                await adminApi.cancelMenu(id);  // id 전달
                window.location.reload();  // 페이지 새로고침
            }
        } catch (error) {
            console.error('삭제 실패:', error);
            alert('삭제에 실패했습니다.');
        }
    };

    const handleModify = async (e) => {
        e.stopPropagation()
        navigate(`/admin/modify/${id}`, { 
            state: { 
                id,
                title,
                price,
                image,
                description,
                stock
            } 
        });
    };

    return (
        <div
            className="bg-white rounded shadow-sm overflow-hidden cursor-pointer"
            onClick={onClick}
        >
            <div className="relative pb-[60%] overflow-hidden">
                <img
                    src={`${API_BASE_URL}/images/${image}`}
                    alt={title}
                    className="absolute top-0 left-0 w-full h-full object-contain bg-gray-50"
                />
            </div>
            <div className="p-4">
                <h3 className="text-base font-normal text-[#333] mb-1">{title}</h3>
                <p className="text-base text-[#666] font-normal mb-4">
                    {price.toLocaleString()}원
                </p>
                <div className="flex items-center justify-end space-x-2">
                <button
                        className="px-4 py-1.5 bg-gray-800 text-white text-sm rounded hover:bg-gray-700"
                        onClick={handleCancel}
                    >
                        삭제
                    </button>
                    <button
                        className="px-4 py-1.5 bg-gray-800 text-white text-sm rounded hover:bg-gray-700"
                        onClick={handleModify}
                    >
                        수정
                    </button>
                </div>
            </div>
        </div>
    );
};

export default AdminProductCard;