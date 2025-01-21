import React from 'react';
import {format} from 'date-fns';
import Navbar from '../components/Navbar';
import Sidebar from '../components/Sidebar';

const EventsScreen = () => {
    const events = [
        {
            id: 1,
            title: '신년맞이 커피 패키지 20% 할인',
            period: '2025.01.20 - 2025.02.20',
            description: '새해를 맞이하여 모든 커피 패키지 20% 할인 이벤트를 진행합니다.',
            image: 'http://localhost:8080/images/event1.jpg',
            status: 'ongoing'
        },
        {
            id: 2,
            title: '발렌타인데이 특별 세트',
            period: '2025.02.01 - 2025.02.14',
            description: '소중한 사람에게 전하는 발렌타인데이 특별 세트를 준비했습니다.',
            image: 'http://localhost:8080/images/event2.jpg',
            status: 'upcoming'
        },
        {
            id: 3,
            title: '아메리카노 리필 이벤트',
            period: '2025.01.15 - 2025.01.31',
            description: '매장에서 드시는 아메리카노 1회 리필 서비스',
            image: 'http://localhost:8080/images/event3.jpg',
            status: 'ongoing'
        }
    ];

    const getStatusBadge = (status) => {
        switch (status) {
            case 'ongoing':
                return (
                    <span className="bg-green-500 text-white px-3 py-1 rounded-full text-sm">
                        진행중
                    </span>
                );
            case 'upcoming':
                return (
                    <span className="bg-blue-500 text-white px-3 py-1 rounded-full text-sm">
                        예정
                    </span>
                );
            default:
                return (
                    <span className="bg-gray-500 text-white px-3 py-1 rounded-full text-sm">
                        종료
                    </span>
                );
        }
    };

    return (
        <div>
            <Navbar/>
            <div className="content-container">
                <Sidebar/>
                <main style={{marginLeft: '250px', flex: 1, paddingTop: '20px'}}>
                    <div className="min-h-screen bg-gray-50 pt-20">
                        <div className="min-w-[396px] mx-auto p-6">
                            <h1 className="text-3xl font-bold mb-8">이벤트/세일</h1>
                            <div className="grid grid-cols-1 gap-6 auto-rows-auto"
                                 style={{
                                     gridTemplateColumns: 'repeat(auto-fit, minmax(396px, 1fr))'
                                 }}>
                                {events.map((event) => (
                                    <div
                                        key={event.id}
                                        className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow"
                                    >
                                        <img
                                            src={event.image}
                                            alt={event.title}
                                            className="w-full h-48 object-cover"
                                        />
                                        <div className="p-6">
                                            <div className="flex justify-between items-start mb-4">
                                                <h2 className="text-xl font-semibold">{event.title}</h2>
                                                {getStatusBadge(event.status)}
                                            </div>
                                            <p className="text-gray-600 mb-4">{event.description}</p>
                                            <div className="text-sm text-gray-500">
                                                <i className="far fa-calendar-alt mr-2"></i>
                                                {event.period}
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    );
}
export default EventsScreen;