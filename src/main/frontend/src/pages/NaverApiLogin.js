import axios from "axios";
import { useEffect } from "react";

const NaverApiLogin = (props) => {

    const code = new URL(window.location.href).searchParams.get("code");

    useEffect(() => {
        const naverLogin = async () => {
            await axios({
                method: "GET",
                url: `/login/oauth2/naver/handler?code=${code}`,
                headers: {
                    "Content-Type": "application/json;charset=utf-8"
                }
            }).then((res) => {
                console.log(res);
            }).catch((err) => {
                console.log(err);
            });
        }
        naverLogin();
    }, [props.history]);

    return (
        <div>
            <p>네이버 로그인 진행중...</p>
        </div>
    )
}

export default NaverApiLogin;