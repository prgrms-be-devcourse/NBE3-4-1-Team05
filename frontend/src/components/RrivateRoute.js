import { Navigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth'; // 인증 훅 사용 가정

const PrivateRoute = ({ children, roles }) => {
    const { user, isLoading } = useAuth();

    if (isLoading) {
        return (
            <div className="flex justify-center items-center min-h-screen">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-gray-900"></div>
            </div>
        );
    }

    if (!user) {
        return <Navigate to="/login" />;
    }

    if (roles && !roles.includes(user.role)) {
        return <Navigate to="/" />;
    }

    return children;
};

export default PrivateRoute;