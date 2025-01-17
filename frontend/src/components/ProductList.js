import React, { useState } from 'react';
import ProductCard from './ProductCard';
import ProductDetailPopup from './ProductDetailPopup';
import '../styles/ProductList.css';

const ProductList = () => {
    const [selectedProduct, setSelectedProduct] = useState(null);

    const handleProductClick = (product) => {
        setSelectedProduct(product);
    };

    const handleClosePopup = () => {
        setSelectedProduct(null);
    };

    const products = [
        {
            id: 1,
            image: require('../assets/images/menus/menu1.jpg'),
            title: 'Pena Espresso',
            price: 4500,
            description: '메뉴1에 대한 상세 설명...'
        },
        {
            id: 2,
            image: require('../assets/images/menus/menu2.jpg'),
            title: 'Transparent Coffee',
            price: 5000,
            description: '메뉴2에 대한 상세 설명...'
        },
        {
            id: 3,
            image: require('../assets/images/menus/menu3.jpg'),
            title: 'Nicaragua Espresso',
            price: 4000,
            description: '메뉴3에 대한 상세 설명...'
        },
        {
            id: 4,
            image: require('../assets/images/menus/menu4.jpg'),
            title: 'Coldcut Coffee',
            price: 5500,
            description: '메뉴4에 대한 상세 설명...'
        }
    ];

    return (
        <div className="product-list">
            {products.map(product => (
                <div key={product.id} onClick={() => handleProductClick(product)}>
                    <ProductCard
                        image={product.image}
                        title={product.title}
                        price={product.price}
                    />
                </div>
            ))}

            {selectedProduct && (
                <ProductDetailPopup
                    product={selectedProduct}
                    onClose={handleClosePopup}
                />
            )}
        </div>
    );
};

export default ProductList;
