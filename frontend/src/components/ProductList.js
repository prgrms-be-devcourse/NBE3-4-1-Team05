// ProductList.js
import React, { useState } from 'react';
import ProductCard from './ProductCard';
import ProductDetailPopup from './ProductDetailPopup';

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
        <div className="flex min-h-[calc(100vh-60px)] mt-[60px]">
            {/* Sidebar */}
            <div className="w-[250px] fixed left-0 top-[60px] h-[calc(100vh-60px)] bg-white border-r border-[#e0e0e0] p-5">
                <ul className="list-none p-0 m-0">
                    <li className="py-2.5 cursor-pointer text-base text-[#333] hover:text-[#666]">All Products</li>
                    <li className="py-2.5 cursor-pointer text-base text-[#333] hover:text-[#666]">Coffee Bean package</li>
                    <li className="py-2.5 cursor-pointer text-base text-[#333] hover:text-[#666]">Capsule</li>
                </ul>
            </div>

            {/* Main Content */}
            <div className="flex-1 p-8">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6 auto-rows-fr">
                    {products.map(product => (
                        <div
                            key={product.id}
                            onClick={() => handleProductClick(product)}
                            className="aspect-[3/4] w-full min-w-[250px]"
                        >
                            <div className="h-full">
                                <ProductCard
                                    image={product.image}
                                    title={product.title}
                                    price={product.price}
                                />
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            {/* Popup */}
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