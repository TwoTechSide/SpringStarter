// WARNING 안내 무시
/* eslint-disable */

import { useState } from 'react';
import '../styles/signup.css'
import axios from 'axios';

const Signup = () => {

    const [userInfo, setUserInfo] = useState({
        email: '',
        name: '',
        password: '',
        age: 0,
        gender: '',
    });

    const onChangeEmail = (e) => {
        setUserInfo({
            ...userInfo,
            email: e.target.value,
        });
    };

    const onChangeName = (e) => {
        setUserInfo({
            ...userInfo,
            name: e.target.value,
        });
    };

    const onChangePassword = (e) => {
        setUserInfo({
            ...userInfo,
            password: e.target.value,
        });
    };

    const onChangeAge = (e) => {
        setUserInfo({
            ...userInfo,
            age: e.target.value,
        });
    };

    const onChangeGender = (e) => {
        setUserInfo({
            ...userInfo,
            gender: e.target.value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(userInfo);

        axios.post('/user/sign-up', JSON.stringify(userInfo), {
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then((res) => {
            console.log(res);
        }).catch((err) => {
            console.log(err);
        });
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="signup-wrapper">
                <h2>회원 가입</h2>
                <div className='inputRow'>
                    <div className='inputDescription'>이메일</div>
                    <input type="email" className='rightInput' style={{width: '40%'}} placeholder="email" name='email' onChange={onChangeEmail}></input>
                </div>
                <div className='inputRow'>
                    <div className='inputDescription'>이름</div>
                    <input type="name" className='rightInput' placeholder="name" name='name' onChange={onChangeName}></input>
                </div>
                <div className='inputRow'>
                    <div className='halfRow'>
                        <div className='inputDescription'>비밀번호</div>
                        <input type="password" className='rightInput' placeholder="password" name='password' onChange={onChangePassword}></input>
                    </div>
                    <div className='halfRow'>
                        <div className='inputDescription'>비밀번호 확인</div>
                        <input type="password" className='rightInput' placeholder="Password" name='password2'></input>
                    </div>
                </div>
                <div className='inputRow'>
                    <div className='halfRow'>
                        <div className='inputDescription'>나이</div>
                        <input type="text" className='rightInput' placeholder="age" name='age' onChange={onChangeAge}></input>
                    </div>
                    <div className='halfRow'>
                        <div className='inputDescription'>성별</div>
                        <select name="gender" className='rightSelect' onChange={onChangeGender}>
                            <option value="Male"> Male </option>
                            <option value="Female">Female </option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                </div>
                <div className='inputRow' style={{height: '60px'}}>
                    <input type='submit' value='Sign up' style={{width: '70%', height: '100%', margin: 'auto'}}></input>
                </div>
            </div>
        </form>
    )
}

export default Signup;