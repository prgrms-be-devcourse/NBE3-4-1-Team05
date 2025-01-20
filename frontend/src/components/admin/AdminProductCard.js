// ProductCard.js
import React, { useState } from 'react';
import { adminApi } from '../../DL/api';

const ProductCard = ({ image, title, price, onClick }) => {
    
    const handleCancel = async () => {
        await adminApi.cancelMenu(menuId);
    };

    const handleModify = async () => {
        await adminApi.modifyMenu(menuId, menuData);
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
                <div className="flex items-center justify-between">
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

export default ProductCard;