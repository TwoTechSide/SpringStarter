import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const ApiLoginHandler = (props) => {

    const navigate = useNavigate();

    const code = new URL(window.location.href).searchParams.get("code");


    useEffect(() => {
        const kakaoLogin = async () => {
            await axios({
                method: "GET",
                url: `/login/oauth2/kakao/handler?code=${code}`,
                headers: {
                    "Content-Type": "application/json;charset=utf-8"
                }
            }).then((res) => {
                console.log(res.data);
                if (res.data == '') {
                    // navigate("/sign-up");
                }
            });
        }
        kakaoLogin();
    }, [props.history]);

    return (
        <div>
            <p>카카오 로그인 진행중...</p>
        </div>
    )
}

export default ApiLoginHandler;