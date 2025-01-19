import React from 'react';
import Navbar from '../components/Navbar';
import Sidebar from '../components/Sidebar';
import ProductList from "../components/ProductList";

const OrderListScreen = () => {
    return (
        <div>
            <Navbar />
            <div className="content-container">
                <Sidebar />
            </div>
        </div>
    );
};

export default OrderListScreen;