import React from 'react';
import Navbar from '../../components/Navbar';
import Sidebar from '../../components/Sidebar';
import AdminProductList from "../../components/admin/AdminProductList";

const AdminMenuScreen = () => {
    return (
        <div>
            <Navbar />
            <div className="content-container">
                <Sidebar />
                <main style={{
                    marginLeft: '240px', // sidebar 너비만큼
                    flex: 1,
                    paddingTop: '20px'
                }}>
                    <AdminProductList />
                </main>
            </div>
        </div>
    );
};

export default AdminMenuScreen;