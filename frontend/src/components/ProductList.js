import React, { useState, useEffect, useRef, useCallback } from 'react';
import ProductCard from './ProductCard';
import ProductDetailPopup from './ProductDetailPopup';
import { getAllMenu } from '../DL/api';


const ProductList = () => {
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const observer = useRef();
    const [sortOption, setSortOption] = useState('recent');

    // 정렬 옵션 변경 핸들러
    const handleSortChange = (e) => {
        setSortOption(e.target.value);
        setPage(0);
        setProducts([]);
        setLoading(true); // 로딩 상태 추가
    };

    const fetchProducts = async () => {
        try {
            const response = await getAllMenu(page, sortOption); // API 호출 시 정렬 옵션 전달
            const newProducts = response.data.data.content;

            setProducts(prev => page === 0 ? newProducts : [...prev, ...newProducts]);
            setHasMore(!response.data.data.last);
            setLoading(false);
        } catch (error) {
            setError('메뉴를 불러오는데 실패했습니다.');
            setLoading(false);
        }
    };

    const lastProductCallback = useCallback(node => {
        if (loading) return;

        if (observer.current) {
            observer.current.disconnect();
        }

        observer.current = new IntersectionObserver(entries => {
            if (entries[0].isIntersecting && hasMore) {
                setPage(prevPage => prevPage + 1);
            }
        });

        if (node) {
            observer.current.observe(node);
        }
    }, [loading, hasMore]);

    const isFirstRender = useRef(true);

    useEffect(() => {
        if (isFirstRender.current) {
            isFirstRender.current = false;
        }
        fetchProducts();
    }, [page, sortOption]);

    const handleProductClick = (product) => {
        setSelectedProduct(product);
    };

    const handleClosePopup = () => {
        setSelectedProduct(null);
    };

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
                {/* 정렬 드롭다운 */}
                <div className="flex justify-end mb-6">
                    <select
                        value={sortOption}
                        onChange={handleSortChange}
                        className="px-4 py-2 border border-gray-300 rounded-md shadow-sm bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                    >
                        <option value="viewsDesc">조회순</option>
                        <option value="recent">최근등록순</option>
                        <option value="oldest">나중등록순</option>
                        <option value="priceDesc">가격높은순</option>
                        <option value="priceAsc">가격낮은순</option>
                    </select>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6 auto-rows-fr">
                    {products.map((product, index) => (
                        <div
                            key={product.id}
                            ref={index === products.length - 1 ? lastProductCallback : null}
                            onClick={() => handleProductClick(product)}
                            className="aspect-[3/4] w-full min-w-[250px]"
                        >
                            <div className="h-full">
                                <ProductCard
                                    image={product.image}
                                    title={product.productName}
                                    price={product.price}
                                />
                            </div>
                        </div>
                    ))}
                </div>
                {loading && <div className="flex justify-center items-center py-4">로딩중...</div>}
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
