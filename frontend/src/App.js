import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainMenuScreen from './screens/MainMenuScreen';
import OrderListScreen from "./screens/OrderListScreen";

import './App.css';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainMenuScreen />} />
                <Route path="/order_list" element={<OrderListScreen />} />
            </Routes>
        </Router>
    );
}

export default App;