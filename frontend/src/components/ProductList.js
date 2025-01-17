import React from 'react';
import ProductCard from './ProductCard';
import '../styles/ProductList.css';

const ProductList = () => {
    const products = [
        {
            id: 1,
            image: require('../assets/images/menus/menu1.jpg'),
            title: 'Pena Espresso',
            price: 4500
        },
        {
            id: 2,
            image: require('../assets/images/menus/menu2.jpg'),
            title: 'Transparent Coffee',
            price: 5000
        },
        {
            id: 3,
            image: require('../assets/images/menus/menu3.jpg'),
            title: 'Nicaragua Espresso',
            price: 4000
        },
        {
            id: 4,
            image: require('../assets/images/menus/menu4.jpg'),
            title: 'Coldcut Coffee',
            price: 5500
        }
    ];

    return (
        <div className="product-list">
            {products.map(product => (
                <ProductCard
                    key={product.id}
                    image={product.image}
                    title={product.title}
                    price={product.price}
                />
            ))}
        </div>
    );
};

export default ProductList;
