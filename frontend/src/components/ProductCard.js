import React, { useState } from 'react';
import { ImageOff } from 'lucide-react';

const ProductCard = ({ image, title, price, onClick, product }) => {
    const [quantity, setQuantity] = useState(0);
    const [imageError, setImageError] = useState(false);

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

    // OrderPage의 addToCart 로직을 직접 사용
    const addToCart = (menuId, menuName, quantity, price) => {
        const cartItems = JSON.parse(localStorage.getItem('cartItems') || '[]');

        const existingItem = cartItems.find(item => item.menuId === menuId);
        if (existingItem) {
            existingItem.quantity += quantity;
            localStorage.setItem('cartItems', JSON.stringify(cartItems));
        } else {
            cartItems.push({ menuId, menuName, quantity, price });
            localStorage.setItem('cartItems', JSON.stringify(cartItems));
        }
    };

    const handleAddClick = (e) => {
        e.stopPropagation();
        if (quantity === 0) {
            alert('수량을 선택해주세요.');
            return;
        }

        // OrderPage의 addToCart 메서드 형식에 맞춰서 데이터 전달
        addToCart(
            product.id,          // menuId
            product.productName, // menuName
            quantity,           // quantity
            product.price       // price
        );

        alert('장바구니에 추가되었습니다.');
        setQuantity(0); // 수량 초기화
    };

    const handleImageError = () => {
        setImageError(true);
    };

    return (
        <div
            className="bg-white rounded shadow-sm overflow-hidden cursor-pointer"
            onClick={onClick}
        >
            <div className="relative pb-[60%] overflow-hidden">
                {(!image || imageError) ? (
                    <div className="absolute top-0 left-0 w-full h-full flex items-center justify-center bg-gray-100">
                        <ImageOff
                            size={48}
                            className="text-gray-400"
                        />
                    </div>
                ) : (
                    <img
                        src={image}
                        alt={title}
                        className="absolute top-0 left-0 w-full h-full object-contain bg-gray-50"
                        onError={handleImageError}
                    />
                )}
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