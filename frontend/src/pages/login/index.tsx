/** @jsxImportSource @emotion/react */
import { GoogleLogin } from '@react-oauth/google';
import axios, { AxiosResponse } from 'axios';
import { useNavigate } from 'react-router-dom';
import { base, contentCss, googleCss } from './ui/styles';
import { Icon } from '@/shared/ui/Icon';
import { Typography } from '@/shared/ui/Typography';
import { useUserStore } from '@/entities/user/model';
import { UserService } from '@/shared/api/user/user.service';
import { LoginResponse } from './api/api';

interface IcredentialResponse {
  credential?: string;
  clientId?: string;
  select_by?: string;
}

const Login = () => {
  const nav = useNavigate();

  const updateUser = useUserStore((state) => state.updateUser);

  const handleLoginSuccess = (credentialResponse: IcredentialResponse) => {
    const idToken = credentialResponse.credential;
    console.log('ID Token:', idToken);

    // ID Token을 백엔드로 전송
    axios
      .post(
        `${import.meta.env.VITE_API_BASE_URL}/api/auth/login`,
        { idToken },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${idToken}`,
          },
        }
      )
      .then(async (response: AxiosResponse<LoginResponse>) => {
        // const accessToken = response.data.body.accessToken;
        // console.log('토큰 저장 성공:', accessToken);
        const userToken = response.data.data.accessToken;

        // accessToken을 로컬 스토리지에 저장
        // localStorage.setItem('accessToken', accessToken);

        // accessToken 스토어 및 perference에 저장
        await updateUser({ userToken });
        // user 정보를 스토어 및 perference에 저장
        const user = (await UserService.userQuery()).data.data;
        await updateUser({ user });

        // accessToken을 axios 전역 헤더에 설정
        axios.defaults.headers.common['Authorization'] = `Bearer ${userToken}`;

        // 회원 여부에 따른 페이지 이동
        if (response.data.data.isRegistered) {
          nav('/');
        } else {
          nav('/signup', { state: { idToken } });
        }
      })
      .catch((error) => {
        console.error('토큰 저장 실패:', error);
        console.log(idToken);
      });

    } catch (error) {
      console.error('로그인 실패:', error);
    }
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
          <GoogleLogin
            onSuccess={handleLoginSuccess}
            onError={handleLoginError}
          />
        </div>
      </div>
    </div>
  );
};

export default Login;