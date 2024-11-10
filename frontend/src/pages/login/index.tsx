/** @jsxImportSource @emotion/react */
import { Browser } from '@capacitor/browser';
import { base, contentCss, googleCss } from './ui/styles';
import { Icon } from '@/shared/ui/Icon';
import { Typography } from '@/shared/ui/Typography';

const Login = () => {
  const clientId = import.meta.env.VITE_GOOGLE_CLIENT_ID;

  const handleGoogleLogin = async () => {
    try {
      // Google OAuth URL 생성
      const googleAuthUrl = 
        'https://accounts.google.com/o/oauth2/v2/auth?' +
        'client_id=' + clientId +
        '&redirect_uri=' + encodeURIComponent(window.location.origin) +
        '&response_type=code' +
        '&access_type=offline' +
        '&scope=' + encodeURIComponent('email profile openid') +
        '&prompt=consent';

      console.log('Auth URL:', googleAuthUrl); // URL 확인용

      // Browser 플러그인으로 OAuth 창 열기
      await Browser.open({
        url: googleAuthUrl,
        presentationStyle: 'fullscreen',
      });

      // 브라우저 닫힘 이벤트 처리
      Browser.addListener('browserFinished', () => {
        console.log('Browser closed');
        Browser.close();
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
          <button 
            onClick={handleGoogleLogin}
            style={{
              padding: '10px 20px',
              backgroundColor: '#fff',
              border: '1px solid #ddd',
              borderRadius: '4px',
              display: 'flex',
              alignItems: 'center',
              gap: '10px',
              cursor: 'pointer'
            }}
          >
            <img 
              src="/img/google-logo.svg" 
              alt="Google logo" 
              style={{ width: '20px', height: '20px' }}
            />
            Google로 로그인
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;