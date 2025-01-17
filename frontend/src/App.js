import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import OrderPage from './screens/OrderScreen';
import EmailInput from './screens/EmailInput';
import OrderList from './screens/OrderList';
import OrderDetail from './screens/OrderDetail';
import OrderModify from './screens/OrderModify';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/order" element={<OrderPage />} />
        <Route path="/order/email" element={<EmailInput />} />
        <Route path="/order/list" element={<OrderList />} />
        <Route path="/order/detail/:id" element={<OrderDetail />} />
        <Route path="/order/modify/:id" element={<OrderModify />} />
      </Routes>
    </Router>
  );
}

export default App;
