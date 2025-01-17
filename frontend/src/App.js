import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./login";

function App() {
  return (
    <Router>
      <Routes>
        {/* /login 경로에 LoginPage 컴포넌트를 렌더링 */}
        <Route path="/login" element={<LoginPage />} />
        {/* 기본 경로 설정 */}
        <Route path="/" element={<h1>Welcome to the Home Page</h1>} />
      </Routes>
    </Router>
  );
}

export default App;
