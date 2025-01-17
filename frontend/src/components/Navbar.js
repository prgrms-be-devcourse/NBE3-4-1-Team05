import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../styles/Navbar.css';

const Navbar = () => {
    const [showDropdown, setShowDropdown] = useState(false);

    return (
        <nav className="navbar">
            <div className="navbar-left">
                <a href="/" className="logo">Grids & Circles</a>
                {/*<Link to="/" className="logo">Grids & Circles</Link>*/}
            </div>
            <div className="navbar-right">
                <ul className="nav-links">
                    <li><a href="/menu">주문 조회</a></li>
                    <li><a href="/jangbaguni">장바구니</a></li>
                    <li><a href="/gongji">공지사항</a></li>
                    <li
                        className="dropdown"
                        onMouseEnter={() => setShowDropdown(true)}
                        onMouseLeave={() => setShowDropdown(false)}
                    >
                        <Link to="/gwanrija">관리자 페이지</Link>
                        {showDropdown && (
                            <ul className="dropdown-menu">
                                <li><Link to="/gwanrija/orders">주문 조회</Link></li>
                                <li><Link to="/gwanrija/products">상품 조회</Link></li>
                            </ul>
                        )}
                    </li>
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;
