import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Sidebar.css';

const Sidebar = () => {
    return (
        <div className="sidebar">
            <div className="sidebar-header">
                <h2>List</h2>
            </div>
            <nav className="sidebar-nav">
                <ul>
                    <li><Link to="/products/all">All Products</Link></li>
                    <li><Link to="/products/coffee-bean">Coffee Bean package</Link></li>
                    <li><Link to="/products/capsule">Capsule</Link></li>
                </ul>
            </nav>
        </div>
    );
};

export default Sidebar;
