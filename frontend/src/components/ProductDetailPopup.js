import React, { useState } from 'react';
import { ImageOff } from 'lucide-react';

const ProductDetailPopup = ({product, onClose}) => {
    const [imageError, setImageError] = useState(false);

    const handleImageError = () => {
        setImageError(true);
    };

    const API_BASE_URL = 'http://localhost:8080'; // 백엔드 API 주소

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white rounded-lg w-full max-w-4xl mx-4 relative">
                <button
                    className="absolute right-4 top-4 text-gray-500 hover:text-gray-700 text-2xl font-bold"
                    onClick={onClose}
                >
                    ×
                </button>
                <div className="flex flex-col md:flex-row p-6">
                    <div className="w-full md:w-1/2">
                        {(!product.image || imageError) ? (
                            <div className="h-[400px] bg-gray-100 rounded-lg flex items-center justify-center">
                                <ImageOff
                                    size={48}
                                    className="text-gray-400"
                                />
                            </div>
                        ) : (
                            <img
                                src={`${API_BASE_URL}/images/${product.image}`}
                                alt={product.title}
                                className="h-[400px] w-full object-contain rounded-lg bg-gray-50"
                                onError={handleImageError}
                            />
                        )}
                    </div>
                    <div className="w-full md:w-1/2 md:pl-6 mt-4 md:mt-0">
                        <h2 className="text-2xl font-bold text-gray-800 mb-4">
                            {product.title}
                        </h2>
                        <p className="text-xl font-bold text-gray-600 mb-4">
                            {product.price.toLocaleString()}원
                        </p>
                        <p className="text-gray-600 leading-relaxed">
                            {product.description}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductDetailPopup;