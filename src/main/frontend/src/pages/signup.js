// WARNING 안내 무시
/* eslint-disable */

import { useState } from 'react';
import '../styles/signup.css'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Signup = () => {

    const navigate = useNavigate();

    const [userInfo, setUserInfo] = useState({
        email: '',
        name: '',
        password: '',
        age: '',
        gender: '',
    });

    const [confirmPassword, setConfirmPassword] = useState("");

    const [isValidEmail, setIsValidEmail] = useState(false);
    const [isValidPassword, setIsValidPassword] = useState(false);

    const onChangeEmail = (e) => { setUserInfo({...userInfo, email: e.target.value}); };
    const onChangeName = (e) => { setUserInfo({...userInfo, name: e.target.value}); };
    const onChangePassword = (e) => { setUserInfo({...userInfo, password: e.target.value}); };
    const onChangeAge = (e) => { setUserInfo({...userInfo, age: e.target.value}); };
    const onChangeGender = (e) => { setUserInfo({...userInfo, gender: e.target.value}); };

    // 비밀번호 확인
    const onChangeConfirmPassword = (e) => {
        let value = e.target.value;
        setConfirmPassword(value);

        if (value != userInfo.password) return setIsValidPassword(false);
        else return setIsValidPassword(true);
    }

    // Form 데이터로 post 요청
    const handleSubmit = (e) => {
        e.preventDefault();

        // 공란 확인
        if (userInfo.email == "") return alert("이메일을 입력해주세요.");
        if (userInfo.name == "") return alert("이름을 입력해주세요.");
        if (userInfo.age == "") return alert("나이를 입력해주세요.");

        // 회원가입 유효성 검사
        if (isValidEmail == false) return alert("이메일 중복 확인을 해주세요.");
        if (userInfo.password != confirmPassword) return alert("비밀번호가 일치하지 않습니다.");
        if (userInfo.gender == "None" || userInfo.gender == "") return alert("성별을 선택해주세요.");

        axios.post('/user/sign-up', JSON.stringify(userInfo), {
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then((res) => {
            if (res.data == true) {
                alert("회원 가입에 성공하였습니다.");
                navigate("/");
            } else {
                alert("회원 가입에 실패하였습니다.");   
                console.log(res);
            }
        })
        .catch((err) => {
            console.log(err);
        });
    }

    // Get 요청 -> Email 중복 확인 -> isValidEmail = true/false
    const checkDuplicateEmail = (e) => {
        e.preventDefault();

        if (userInfo.email == "")
            return alert("이메일을 입력해주세요.");

        axios.get("/user/checkEmail", {
            params: {
                email: userInfo.email
            }
        })
        .then((res) => {
            if (res.data == true) alert("사용 가능한 이메일입니다.");
            else alert("사용할 수 없는 이메일입니다.");
            setIsValidEmail(res.data);
        })
        .catch((err) => {
            console.log(err);
        });
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="signup-wrapper">
                <h2>회원 가입</h2>
                <div className='inputRow'>
                    <div className='inputDescription'>이메일</div>
                    <input type="email" className={`rightInput ${isValidEmail && "validInput"}`} style={{width: '40%', marginRight: '30px'}} placeholder="email" name='email' onChange={onChangeEmail}></input>
                    <button className='checkConfirm' onClick={checkDuplicateEmail}>중복 확인</button>
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
                        <input type="password" className={`rightInput ${!isValidPassword && confirmPassword != "" && "invalidInput"}`} placeholder="Password" name='password2' onChange={onChangeConfirmPassword}></input>
                        { (!isValidPassword && confirmPassword != "") && <div className='invalidDescription'>※비밀번호가 일치하지 않습니다.</div> }
                    </div>
                </div>
                <div className='inputRow'>
                    <div className='halfRow'>
                        <div className='inputDescription'>나이</div>
                        <input type="text" className={"rightInput"} placeholder="age" name='age' onChange={onChangeAge}></input>
                    </div>
                    <div className='halfRow'>
                        <div className='inputDescription'>성별</div>
                        <select name="gender" className='rightSelect' onChange={onChangeGender}>
                            <option value="None">=== 선택 ===</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
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