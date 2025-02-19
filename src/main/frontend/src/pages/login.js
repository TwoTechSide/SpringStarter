// WARNING 안내 무시
/* eslint-disable */

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import '../styles/login.css';
import kakaoTalkPic from '../assets/kakao_login_medium_wide.png'
import axios from 'axios';

function Login() {

    let [location, setLocation] = useState("");

    useEffect(() => {
        axios.get('/login/kakao-api-key')
        .then((res) => {setLocation(res.data);})
        .catch((err) => {console.log(err);});
    }, []);

    return (
        <>
            <div className='login-wrapper'>
                <h2>Log in</h2>
                <input type='email' name='userEmail' placeholder='email'></input>
                <input type='password' name='userPassword' placeholder='password'></input>
                <input type='submit' value='login'></input>
                <div className='api-login'>
                    <div className='container'>
                        <a href={location}>
                            <img src={kakaoTalkPic} />
                        </a>
                    </div>
                </div>
                <Link to="/signup">회원 가입</Link>
            </div>
        </>
    )
}

export default Login;