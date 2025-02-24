// WARNING 안내 무시
/* eslint-disable */

import { Link } from 'react-router-dom';

import '../styles/login.css';
import kakaoTalkPic from '../assets/kakao_login_medium_wide.png'
import naverPic from '../assets/naver_login_btnG.png'

const K_CLIENT_ID = process.env.REACT_APP_K_CLIENT_ID;
const K_REDIRECT_URI = process.env.REACT_APP_K_REDIRECT_URI;

const N_CLIENT_ID = process.env.REACT_APP_N_CLIENT_ID;
const N_CALLBACK_URL = process.env.REACT_APP_N_CALLBACK_URL;

const kakaoURL = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${K_CLIENT_ID}&redirect_uri=${K_REDIRECT_URI}`;
const naverURL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${N_CLIENT_ID}&redirect_uri=${N_CALLBACK_URL}&state=STATE_STRING`;

function Login() {

    return (
        <>
            <div className='login-wrapper'>
                <h2>Log in</h2>
                <input type='text' name='userId' placeholder='Id'></input>
                <input type='password' name='password' placeholder='Password'></input>
                <input type='submit' value='login'></input>
                <div className='api-login'>
                    <div className='container'>
                        <a href={kakaoURL}>
                            <img src={kakaoTalkPic} />
                        </a>
                        <a href={naverURL}>
                            <img src={naverPic} />
                        </a>
                    </div>
                </div>
                <Link to="/sign-up">회원 가입</Link>
            </div>
        </>
    )
}

export default Login;