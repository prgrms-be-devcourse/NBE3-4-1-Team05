import React from 'react';
import Navbar from './Navbar';
import Sidebar from './Sidebar';

const Layout = ({ children, showSidebar = true }) => {
    return (
        <div className="min-h-screen bg-gray-50">
            {/* Navbar */}
            <Navbar />
            
            {/* Main Content Area */}
            <div> {/* Navbar 높이만큼 상단 여백 */}
                {showSidebar && <Sidebar />}
                
                {/* Main Content */}
                <div> {/* Sidebar 너비만큼 왼쪽 여백 */}
                    {children}
                </div>
            </div>
        </div>
    );
};

export default Layout;