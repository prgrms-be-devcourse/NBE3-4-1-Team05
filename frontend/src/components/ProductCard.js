import React, { useState } from 'react';
import '../styles/ProductCard.css';

const ProductCard = ({ image, title, price, onClick }) => {
    const [quantity, setQuantity] = useState(0);

    const handleIncrement = (e) => {
        e.stopPropagation(); // 이벤트 전파 중지
        setQuantity(prev => prev + 1);
    };

    const handleDecrement = (e) => {
        e.stopPropagation(); // 이벤트 전파 중지
        if (quantity > 0) {
            setQuantity(prev => prev - 1);
        }
    };

    const handleAddClick = (e) => {
        e.stopPropagation(); // 이벤트 전파 중지
        // 추가 버튼 로직
    };

    return (
        <div className="product-card" onClick={onClick}>
            <div className="product-image">
                <img src={image} alt={title} />
            </div>
            <div className="product-info">
                <h3>{title}</h3>
                <p className="price">{price.toLocaleString()}원</p>
                <div className="quantity-control">
                    <div className="quantity-buttons">
                        <button onClick={handleDecrement}>-</button>
                        <span>{quantity}</span>
                        <button onClick={handleIncrement}>+</button>
                    </div>
                    <button className="add-button" onClick={handleAddClick}>
                        추가
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ProductCard;