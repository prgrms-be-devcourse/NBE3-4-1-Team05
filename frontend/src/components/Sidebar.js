// Sidebar.js
import React from 'react';
import { Link } from 'react-router-dom';

const Sidebar = () => {
    return (
        <div className="w-[250px] min-h-screen bg-white fixed left-0 top-[64px] border-r border-[#e0e0e0]">
            {/* Sidebar Header */}
            <div className="pl-5 pt-8">
                <h2 className="text-base font-normal text-gray-800 mb-6 pb-2 border-b border-gray-200">List</h2>
            </div>

            {/* Sidebar Navigation */}
            <nav>
                <ul className="list-none p-0 m-0">
                    <li>
                        <Link
                            to="/products/all"
                            className="block pl-5 py-2 text-sm text-gray-600 hover:text-gray-800"
                        >
                            All Products
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/products/coffee-bean"
                            className="block pl-5 py-2 text-sm text-gray-600 hover:text-gray-800"
                        >
                            Coffee Bean package
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/products/capsule"
                            className="block pl-5 py-2 text-sm text-gray-600 hover:text-gray-800"
                        >
                            Capsule
                        </Link>
                    </li>
                </ul>
            </nav>
        </div>
    );
};

export default Sidebar;