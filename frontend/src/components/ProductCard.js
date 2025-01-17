// ProductCard.js
import React, { useState } from 'react';

const ProductCard = ({ image, title, price, onClick }) => {
    const [quantity, setQuantity] = useState(0);

    const handleIncrement = (e) => {
        e.stopPropagation();
        setQuantity(prev => prev + 1);
    };

    const handleDecrement = (e) => {
        e.stopPropagation();
        if (quantity > 0) {
            setQuantity(prev => prev - 1);
        }
    };

    const handleAddClick = (e) => {
        e.stopPropagation();
        // 추가 버튼 로직
    };

    return (
        <div
            className="bg-white rounded shadow-sm overflow-hidden cursor-pointer"
            onClick={onClick}
        >
            <div className="relative pb-[60%] overflow-hidden">
                <img
                    src={image}
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
                    <div className="flex items-center space-x-2">
                        <button
                            onClick={handleDecrement}
                            className="w-7 h-7 border border-gray-300 rounded flex items-center justify-center hover:bg-gray-50"
                        >
                            -
                        </button>
                        <span className="w-8 text-center">{quantity}</span>
                        <button
                            onClick={handleIncrement}
                            className="w-7 h-7 border border-gray-300 rounded flex items-center justify-center hover:bg-gray-50"
                        >
                            +
                        </button>
                    </div>
                    <button
                        className="px-4 py-1.5 bg-gray-800 text-white text-sm rounded hover:bg-gray-700"
                        onClick={handleAddClick}
                    >
                        추가
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ProductCard;