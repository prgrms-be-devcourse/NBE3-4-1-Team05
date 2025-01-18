import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./components/login";
import AddMenuPage from "./screens/addMenu";
import AdminOrderDetail from "./screens/AdminOrderDetail";
import AdminOrderList from './screens/AdminOrderList';

function App() {
  return (
    <Router>
      <Routes>
        {/* /login 경로에 LoginPage 컴포넌트를 렌더링 */}
        <Route path="/login" element={<LoginPage />} />
        {/* 기본 경로 설정 */}
        <Route path="/" element={<h1>Welcome to the Home Page</h1>} />
        <Route path="/admin/addMenu" element={<AddMenuPage/>}/>
        <Route path="/admin/order/id" element={<AdminOrderDetail />} />
        <Route path="/admin/order" element={<AdminOrderList />} />
      </Routes>
    </Router>
  );
}

export default App;
