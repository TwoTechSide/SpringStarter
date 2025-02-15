// WARNING 안내 무시
/* eslint-disable */

import React from 'react';
import { Link } from 'react-router-dom';

import '../styles/login.css';

function Login() {
    return (
        <>
            <div className='login-wrapper'>
                <h2>Log in</h2>
                <input type='email' name='userEmail' placeholder='email'></input>
                <input type='password' name='userPassword' placeholder='password'></input>
                <input type='submit' value='login'></input>
                <Link to="/signup">회원 가입</Link>
            </div>
        </>
    )
}

export default Login;