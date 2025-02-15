// import React, {useEffect, useState} from 'react';
// import axios from 'axios';

// WARNING 안내 무시
/* eslint-disable */

import { useState } from 'react';
import './App.css';

function App() {
//   const [hello, setHello] = useState('')

//    useEffect(() => {
//        axios.get('/api/api/hello')
//        .then(response => setHello(response.data))
//        .catch(error => console.log(error))
//    }, []);

    useEffect(() => {
        axios.get('/api/api/hello')
        .then(response => setHello(response.data))
        .catch(error => console.log(error))
    }, []);

    return (
        <div className='App'>
            <div className='black-nav'>
                <h4>블로그</h4>
            </div>

            <button onClick={ ()=>{
                let copiedList = [...title];
                copiedList.sort();
                setTitle(copiedList);
            }}>제목 정렬</button>

            <div className='list'>
                <h4>{ title[0] } <span onClick={ ()=>{changeTitle()} }>👍</span> { like } </h4>
                <p>1월 발행</p>
            </div>
            <div className='list'>
                <h4>{ title[1] }</h4>
                <p>2월 발행</p>
            </div>
            <div className='list'>
                <h4>{ title[2] }</h4>
                <p>3월 발행</p>
            </div>
        </div>
    );
}

export default App;