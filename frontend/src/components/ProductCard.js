import React, { useState } from 'react';
import '../styles/ProductCard.css';

const ProductCard = ({ image, title, price }) => {
    const [quantity, setQuantity] = useState(0);

    const handleIncrement = () => {
        setQuantity(prev => prev + 1);
    };

    const handleDecrement = () => {
        if (quantity > 0) {
            setQuantity(prev => prev - 1);
        }
    };

    return (
        <div className="product-card">
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
                    <button className="add-button">
                        추가
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ProductCard;