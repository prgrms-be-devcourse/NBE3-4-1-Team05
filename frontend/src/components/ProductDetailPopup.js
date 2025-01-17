import React from 'react';
import '../styles/ProductDetailPopup.css';

const ProductDetailPopup = ({product, onClose}) => {
    return (
        <div className="popup-overlay">
            <div className="popup-content">
                <button className="close-button" onClick={onClose}>×</button>
                <div className="product-detail">
                    <img src={product.image} alt={product.title}/>
                    <div className="product-info">
                        <h2>{product.title}</h2>
                        <p className="price">{product.price.toLocaleString()}원</p>
                        <p className="description">{product.description}</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductDetailPopup;
