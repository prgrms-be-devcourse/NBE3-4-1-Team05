import React, {useState} from 'react';
import {format} from 'date-fns';
import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";

const NoticeScreen = () => {
    const [notices] = useState([
        {
            id: 1,
            title: '2024 설날 연휴 매장 운영 안내',
            content: '안녕하세요. Grids & Circles입니다. 설날 연휴 매장 운영 시간을 안내드립니다...',
            date: '2024-01-15',
            important: true
        },
        {
            id: 2,
            title: '개인정보 처리방침 개정 안내',
            content: '개인정보 처리방침이 다음과 같이 변경될 예정입니다...',
            date: '2024-01-10',
            important: false
        },
        {
            id: 3,
            title: '신메뉴 출시 안내',
            content: '새로운 시그니처 메뉴가 출시되었습니다...',
            date: '2024-01-05',
            important: true
        }
    ]);

    return (
        <div>
            <Navbar/>
            <div className="content-container relative flex">
                <Sidebar/>
                <div className="min-h-screen bg-gray-50 pt-20 relative flex-1 ml-[250px]">
                    <div className="max-w-4xl mx-auto p-6">
                        <h1 className="text-3xl font-bold mb-8">공지사항</h1>
                        <div className="bg-white rounded-lg shadow">
                            {notices.map((notice) => (
                                <div
                                    key={notice.id}
                                    className="border-b last:border-b-0 p-6 hover:bg-gray-50 transition-colors"
                                >
                                    <div className="flex items-center justify-between mb-2">
                                        <h2 className="text-xl font-semibold flex items-center">
                                            {notice.important && (
                                                <span className="bg-red-500 text-white text-xs px-2 py-1 rounded mr-2">
                                            중요
                                        </span>
                                            )}
                                            {notice.title}
                                        </h2>
                                        <span className="text-gray-500 text-sm">
                                    {format(new Date(notice.date), 'yyyy.MM.dd')}
                                </span>
                                    </div>
                                    <p className="text-gray-600 mt-2">{notice.content}</p>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default NoticeScreen;