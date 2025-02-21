// WARNING 안내 무시
/* eslint-disable */

import { Link } from 'react-router-dom';

import '../styles/login.css';
import kakaoTalkPic from '../assets/kakao_login_medium_wide.png'

const K_CLIENT_ID = process.env.REACT_APP_K_CLIENT_ID;
const K_REDIRECT_URI = process.env.REACT_APP_K_REDIRECT_URL;
const kakaoURL = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${K_CLIENT_ID}&redirect_uri=${K_REDIRECT_URI}`

function Login() {

    return (
        <>
            <div className='login-wrapper'>
                <h2>Log in</h2>
                <input type='email' name='userEmail' placeholder='email'></input>
                <input type='password' name='userPassword' placeholder='password'></input>
                <input type='submit' value='login'></input>
                <div className='api-login'>
                    <div className='container'>
                        <a href={kakaoURL}>
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