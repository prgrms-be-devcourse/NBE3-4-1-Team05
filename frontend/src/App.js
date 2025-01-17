import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainMenuScreen from './screens/MainMenuScreen';
import './App.css';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainMenuScreen />} />
            </Routes>
        </Router>
    );
}

export default App;