import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainMenuScreen from './screens/MainMenuScreen';
import OrderListScreen from "./screens/OrderListScreen";
import NoticeScreen from './screens/NoticeScreen';
import EventsScreen from './screens/EventsScreen';

import './App.css';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainMenuScreen />} />
                <Route path="/order_list" element={<OrderListScreen />} />
                <Route path="/notice" element={<NoticeScreen />} />
                <Route path="/events" element={<EventsScreen />} />
            </Routes>
        </Router>
    );
}

export default App;