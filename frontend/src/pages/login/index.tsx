/** @jsxImportSource @emotion/react */
import { useNavigate } from 'react-router-dom';
import { base, btn, btnImg, contentCss, pocket } from './ui/styles';
import { useUserStore } from '@/entities/user/model';
import { SocialLogin } from '@capgo/capacitor-social-login';
import { api } from './api/api';
import { AxiosResponse } from 'axios';
import { LoginResponse } from '@/shared/api/user/user.type';
import { useShallow } from 'zustand/shallow';
import loginBtn from '@/assets/img/page/login/login_button.png';
import { Button } from '@/shared/ui/Button';
import { usePushNotificationStore } from '@/shared/model/usePushNotificationStore';
import { InitializePushListener } from '@/shared/api/push-notification/initPushListener';
import { useMedicationAddStore } from '../medicationAdd/model/useMedicationAddStore';

const Login = () => {
  const nav = useNavigate();
  const { updateUserInfo, getUserInfo } = useUserStore(
    useShallow((state) => ({
      updateUserInfo: state.updateUserInfo,
      getUserInfo: state.getUserInfo,
    }))
  );
  const setPushNotification = usePushNotificationStore(
    (state) => state.setPushNotification
  );
  const { setUserInfo } = useMedicationAddStore();

  const googleLogin = async () => {
    await SocialLogin.login({
      provider: 'google',
      options: {
        scopes: ['email', 'profile'],
      },
    }).then(async (res) => {
      const idToken = res.result.idToken;
      const userIdToken = res.result.idToken;

      await updateUserInfo({ userIdToken });

      await api
        .post('/api/auth/login', { idToken })
        .then(async (res: AxiosResponse<LoginResponse>) => {
          if (res.data.data.isRegistered) {
            const userAccessToken = res.data.data.accessToken;
            const userInfo = res.data.data.userInfo;

            await updateUserInfo({ userAccessToken, user: userInfo });
            await InitializePushListener(setPushNotification);
            // FIXME: useMedicationAddStore로 nickname 값을 활용하기 위한 초기화
            setUserInfo(userInfo.nickname);

            const user = getUserInfo();
            if (user.user?.role === 'protector') {
              nav('/protector-main');
            }

            if (user.user?.role === 'child') {
              console.log(user.user.role);
              nav('/main');
            }
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
          <a css={pocket} href="/main"></a>
        </div>
        <Button css={btn} handler={googleLogin}>
          <img css={btnImg} src={loginBtn} alt="" />
        </Button>
      </div>
    </div>
  );
};

export default Login;
