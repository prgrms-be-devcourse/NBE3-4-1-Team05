import React from 'react';
import { Link } from 'react-router-dom';

const Sidebar = () => {
    return (
        <div className="w-[250px] min-h-screen bg-white fixed left-0 top-[64px] border-r-2 border-[#e0e0e0]">
            {/* Sidebar Header */}
            <div className="pl-5 pt-40">
                <h2 className="text-xl font-bold text-gray-800 mb-6 pb-2 border-b border-gray-200 text-right pr-5">List</h2>
            </div>


            {/* Sidebar Navigation */}
            <nav>
                <ul className="list-none p-0 m-0">
                    <li>
                        <Link
                            to="/"
                            className="block font-bold pr-5 py-2 text-sm text-gray-600 hover:text-gray-800 text-right"
                        >
                            All Products
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/notice"
                            className="block font-bold pr-5 py-2 text-sm text-gray-600 hover:text-gray-800 text-right group relative"
                        >
                            공지사항
                            <span
                                className="absolute right-full top-1/2 -translate-y-1/2 mr-2 w-2 h-2 bg-red-500 rounded-full hidden group-hover:block"></span>
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/events"
                            className="block font-bold pr-5 py-2 text-sm text-gray-600 hover:text-gray-800 text-right group relative"
                        >
                            이벤트/세일
                            <span className="bg-yellow-400 text-xs px-1 ml-1 rounded-full text-white">New</span>
                        </Link>
                    </li>
                </ul>
            </nav>
        </div>
    );
};

export default Sidebar;
