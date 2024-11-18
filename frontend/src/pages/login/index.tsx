/** @jsxImportSource @emotion/react */
// import { GoogleLogin } from '@react-oauth/google';
// import axios, { AxiosResponse } from 'axios';
import { useNavigate } from 'react-router-dom';
import { base, contentCss, googleCss } from './ui/styles';
import { Icon } from '@/shared/ui/Icon';
import { Typography } from '@/shared/ui/Typography';
import { useUserStore } from '@/entities/user/model';
// import { UserService } from '@/shared/api/user/user.service';
// import { LoginResponse } from './api/api';
import { SocialLogin } from '@capgo/capacitor-social-login';
import { api } from './api/api';
import { AxiosResponse } from 'axios';
import { LoginResponse } from '@/shared/api/user/user.type';
import { useShallow } from 'zustand/shallow';
import { AccessTokenPlugin, TokenPayload } from '../check-samsung-data/plugin/AccessTokenPlugin';

// interface IcredentialResponse {
//   credential?: string;
//   clientId?: string;
//   select_by?: string;
// }

const Login = () => {
  const nav = useNavigate();
  const { updateUserInfo, getUserInfo } = useUserStore(
    useShallow((state) => ({
      updateUserInfo: state.updateUserInfo,
      getUserInfo: state.getUserInfo,
    }))
  );
  const tokenPayload: TokenPayload = {"token": getUserInfo().userAccessToken ?? ""}
  AccessTokenPlugin.getAccessTokenPlugin(tokenPayload)
  .then((response) => {
    console.log('Response from native:', response.message);
  })
  .catch((error) => {
    console.error('Error:', error);
  });
  // 유저 정보가 존재하면 로그인 안 하고 바로 각각의 메인 페이지로 이동
  // if (getUserInfo().user?.role === 'child') nav('/main');
  // if (getUserInfo().user?.role === 'protector') nav('/protector-main');

  // const handleLoginSuccess = (credentialResponse: IcredentialResponse) => {
  //   const idToken = credentialResponse.credential;
  //   console.log('ID Token:', idToken);

  //   // ID Token을 백엔드로 전송
  //   axios
  //     .post(
  //       `${import.meta.env.VITE_API_BASE_URL}/api/auth/login`,
  //       { idToken },
  //       {
  //         headers: {
  //           'Content-Type': 'application/json',
  //           Authorization: `Bearer ${idToken}`,
  //         },
  //       }
  //     )
  //     .then(async (response: AxiosResponse<LoginResponse>) => {
  //       // const accessToken = response.data.body.accessToken;
  //       // console.log('토큰 저장 성공:', accessToken);
  //       const userToken = response.data.data.accessToken;

  //       // accessToken을 로컬 스토리지에 저장
  //       // localStorage.setItem('accessToken', accessToken);

  //       // accessToken 스토어 및 perference에 저장
  //       await updateUser({ userToken });
  //       // user 정보를 스토어 및 perference에 저장
  //       const user = (await UserService.userQuery()).data.data;
  //       await updateUser({ user });

  //       // accessToken을 axios 전역 헤더에 설정
  //       axios.defaults.headers.common['Authorization'] = `Bearer ${userToken}`;

  //       // 회원 여부에 따른 페이지 이동
  //       if (response.data.data.isRegistered) {
  //         nav('/');
  //       } else {
  //         nav('/signup', { state: { idToken } });
  //       }
  //     })
  //     .catch((error) => {
  //       console.error('토큰 저장 실패:', error);
  //       console.log(idToken);
  //     });
  // };

  // const handleLoginError = () => {
  //   console.log('로그인 실패');
  // };

  const googleLogin = async () => {
    await SocialLogin.login({
      provider: 'google',
      options: {
        scopes: ['email', 'profile'],
      },
    }).then(async (res) => {
      const idToken = res.result.idToken;
      // const userAccessToken = res.result.accessToken?.token;
      const userIdToken = res.result.idToken;

      await updateUserInfo({ userIdToken });
      
      await api
        .post('/api/auth/login', { idToken })
        .then(async (res: AxiosResponse<LoginResponse>) => {

          if (res.data.data.isRegistered) {
            // TODO: Access Token 하드코딩 수정
            // const userAccessToken = res.data.data.accessToken;
            const userInfo = res.data.data.userInfo;
            await updateUserInfo({ user: userInfo });

            const user = getUserInfo();
            console.log('****user rola****');
            console.log('****user rola****');
            console.log(user.user?.role);
            console.log('****user rola****');
            console.log('****user rola****');
            if(user.userAccessToken != null){
              handleTokenSubmit(user.userAccessToken)
            }  
            if (user.user?.role === 'protector') {
              nav('/protector-main');
            }
            if (user.user?.role === 'child') {
              console.log(user.user.role);

              nav('/menu');
            }
            // nav('/protector-main');
          } else {
            nav('/signup');
          }
        })
        .catch((err) => {
          console.log('*****로그인 에러*****');
          console.log('*****로그인 에러*****');
          console.log(JSON.stringify(err));
          console.log(err);
          console.log('*****로그인 에러*****');
          console.log('*****로그인 에러*****');
        });
    });
  };

  return (
    <div css={base}>
      <div css={contentCss}>
        <div>
          <Icon size={14}>
            <img alt="icon-0" src="/img/logo.png" />
          </Icon>
          <Typography
            style={{ textAlign: 'center' }}
            color="dark"
            size="1.25"
            weight={500}
          >
            몽땅과 함께하는
            <br />
            당뇨 관리!
          </Typography>
        </div>
        <div css={googleCss}>
          {/* <GoogleLogin
            onSuccess={handleLoginSuccess}
            onError={handleLoginError}
          /> */}
          <button onClick={googleLogin}>Google Login Btn</button>
        </div>
      </div>
    </div>
  );
};

export default Login;
