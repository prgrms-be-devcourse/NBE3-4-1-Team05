import React, { useState, useEffect } from 'react';
import ProductCard from './ProductCard';
import ProductDetailPopup from './ProductDetailPopup';
import { getAllMenu } from '../DL/api';

const ProductList = () => {
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = async () => {
        try {
            const response = await getAllMenu();
            setProducts(response.data.data.content); // content 배열로 수정
            setLoading(false);
        } catch (error) {
            setError('메뉴를 불러오는데 실패했습니다.');
            setLoading(false);
        }
    };

    const handleProductClick = (product) => {
        setSelectedProduct(product);
    };

    const handleClosePopup = () => {
        setSelectedProduct(null);
    };

    if (loading) return <div className="flex justify-center items-center min-h-screen">로딩중...</div>;
    if (error) return <div className="flex justify-center items-center min-h-screen">{error}</div>;

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
                                    title={product.productName} // productName으로 수정
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
