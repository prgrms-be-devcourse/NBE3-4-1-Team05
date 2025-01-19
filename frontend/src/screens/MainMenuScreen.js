import React from 'react';
import Navbar from '../components/Navbar';
import Sidebar from '../components/Sidebar';
import ProductList from "../components/ProductList";

const MainMenuScreen = () => {
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
                    <ProductList />
                </main>
            </div>
        </div>
    );
};

export default MainMenuScreen;