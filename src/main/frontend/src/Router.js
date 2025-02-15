import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import App from './pages/login';
import Signup from './pages/signup';

const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<App />} />
                <Route path='/signup' element={<Signup />}/>
            </Routes>
        </BrowserRouter>
    )
}

export default Router;