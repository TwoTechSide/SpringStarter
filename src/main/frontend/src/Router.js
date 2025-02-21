import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Login from './pages/login';
import Signup from './pages/signup';
import ApiLoginHandler from './pages/ApiLoginHandler';

const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Login />} />
                <Route path='/signup' element={<Signup />}/>
                <Route path='/login/oauth2/kakao' element={<ApiLoginHandler />}/>
            </Routes>
        </BrowserRouter>
    )
}

export default Router;