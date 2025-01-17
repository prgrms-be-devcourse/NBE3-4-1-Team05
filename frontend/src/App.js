import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import Navbar from './components/Navbar';
import Sidebar from './components/Sidebar';
import ProductList from "./components/ProductList";
import './App.css';

function App() {
    return (
        <BrowserRouter>
            <div className="App">
                <Navbar />
                <div className="main-container">
                    <Sidebar />
                    <div className="content">
                        <ProductList />
                    </div>
                </div>
            </div>
        </BrowserRouter>
    );
}

export default App;