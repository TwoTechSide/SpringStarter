import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Login from './pages/login';
import Signup from './pages/signup';
import KakaoApiLogin from './pages/KakaoApiLogin';
import NaverApiLogin from './pages/NaverApiLogin';

const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Login />} />
                <Route path='/sign-up' element={<Signup />}/>
                <Route path='/login/oauth2/kakao' element={<KakaoApiLogin />}/>
                <Route path='/login/oauth2/naver' element={<NaverApiLogin />}/>
            </Routes>
        </BrowserRouter>
    )
}

export default Router;