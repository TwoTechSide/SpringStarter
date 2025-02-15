// WARNING 안내 무시
/* eslint-disable */

import '../styles/signup.css'

function Signup() {
    return (
        <div className="signup-wrapper">
            <h2>회원 가입</h2>
            <div className='inputRow'>
                <div className='inputDescription'>이메일</div>
                <input type="email" className='rightInput' style={{width: '40%'}} placeholder="email"></input>
            </div>
            <div className='inputRow'>
                <div className='inputDescription'>이름</div>
                <input type="name" className='rightInput' placeholder="name"></input>
            </div>
            <div className='inputRow'>
                <div className='halfRow'>
                    <div className='inputDescription'>비밀번호</div>
                    <input type="password" className='rightInput' placeholder="password"></input>
                </div>
                <div className='halfRow'>
                    <div className='inputDescription'>비밀번호 확인</div>
                    <input type="password" className='rightInput' placeholder="Password"></input>
                </div>
            </div>
            <div className='inputRow'>
                <div className='halfRow'>
                    <div className='inputDescription'>나이</div>
                    <input type="text" className='rightInput' placeholder="age"></input>
                </div>
                <div className='halfRow'>
                    <div className='inputDescription'>성별</div>
                    <select name="gender" className='rightSelect'>
                        <option value="Male"> Male </option>
                        <option value="Female"> Female </option>
                    </select>
                </div>
            </div>
            <div className='inputRow' style={{height: '60px'}}>
                <input type='submit' value='Sign up' style={{width: '70%', height: '100%', margin: 'auto'}}></input>
            </div>
        </div>
    )
}

export default Signup;