import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navbar = () => {
    const [showDropdown, setShowDropdown] = useState(false);
    const navigate = useNavigate();
    
    const handleAdminMenuClick = async (path) => {
        try {
            // state에 리다이렉트 경로 저장
            navigate('/login', {
                state: { redirectTo: path },
                replace: true  // 히스토리 스택에 쌓이지 않도록 설정
            });
        } catch (error) {
            console.error('인증 확인 실패:', error);
            navigate('/login', {
                state: { redirectTo: path },
                replace: true
            });
        }
    };

    return (
        <nav className="flex justify-between items-center px-8 py-4 bg-[#333] text-white fixed top-0 left-0 right-0 z-[1000]">
            <div className="navbar-left">
                <Link to="/" className="text-white no-underline text-2xl font-bold">
                    Grids & Circles
                </Link>
            </div>
            <div>
                <ul className="flex list-none m-0 p-0">
                    <li className="ml-8 relative">
                        <Link to="/order/email" className="text-white no-underline">
                            주문 조회
                        </Link>
                    </li>
                    <li className="ml-8 relative">
                        <Link to="/order" className="text-white no-underline">
                            장바구니
                        </Link>
                    </li>
                    <li
                        className="ml-8 relative"
                        onMouseEnter={() => setShowDropdown(true)}
                        onMouseLeave={() => setShowDropdown(false)}
                    >
                        <span className="text-white cursor-pointer">
                           관리자 페이지
                        </span>
                        {showDropdown && (
                            <ul className="absolute top-full right-0 bg-[#333] py-2 min-w-[150px] rounded shadow-md list-none">
                                <li className="m-0 p-2 hover:bg-[#444]">
                                    <span onClick={()=>handleAdminMenuClick('/admin/order')} className="text-white no-underline block">
                                        주문 조회
                                    </span>
                                </li>
                                <li className="m-0 p-2 hover:bg-[#444]">
                                    <span onClick={()=>handleAdminMenuClick('/admin/menus')} className="text-white no-underline block">
                                        상품 조회
                                    </span>
                                </li>
                            </ul>
                        )}
                    </li>
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;