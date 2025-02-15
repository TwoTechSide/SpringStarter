// WARNING 안내 무시
/* eslint-disable */

import './App.css';

function App() {
    return (
        <>
            <div className='login-wrapper'>
                <h2>Log in</h2>
                <input type='email' name='userEmail' placeholder='email'></input>
                <input type='password' name='userPassword' placeholder='password'></input>
                <input type='submit' value='login'></input>
                <a className='signup' href=''>회원 가입</a>
            </div>
        </>
    )
}

export default App;