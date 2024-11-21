import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { KidsMainPage } from './pages/MainPage';
import { NicknameTitle } from './pages/nickname-title';
import { Btn } from './pages/btn';
import Menu from './pages/Menu/Menu';
import Login from './pages/login';
import { NicknameEdit } from './pages/profile/ui/nickname-edit';
import { GoogleOAuthProvider } from '@react-oauth/google';
import { Profile } from './pages/profile';
import { Report } from './pages/report';
import { RecordPage } from './pages/record';
import { Encyclopedia } from './pages/Encyclopedia';
import { SignUp } from './pages/Signup';
import ProtectorMain from './pages/protectormain/ProtectorMain';
import { useLoadState } from './app/hooks/use-load-state';
import { InviteCode } from './pages/Signup/invite-code';
import { GmiDetail } from './pages/report/ui/detail/gmi';
import { Variability } from './pages/report/ui/detail/variability';
import { Mean } from './pages/report/ui/detail/mean';
import { Tir } from './pages/report/ui/detail/tir/indesx';
import { DayRecordPage } from './pages/day-record';
import SettingPage from './pages/settingPage/SettingPage';
import { useEffect } from 'react';
import { SocialLogin } from '@capgo/capacitor-social-login';
import { PushNotification } from './shared/ui/PushNotification/PushNotification';
import { SamsungSetting } from './pages/samsung-setting/index';
import { ForegoundServiceSetting } from './pages/foreground-setting/index';
import Medication from './pages/medication/Medication';
import MedicationAdd from './pages/medicationAdd/MedicationAdd';

function App() {
  useLoadState();

  useEffect(() => {
    SocialLogin.initialize({
      google: {
        webClientId: import.meta.env.VITE_GOOGLE_WEB_CLIENT_ID,
      },
    });
  }, []);

  return (
    <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_AUTH_CLIENT_ID}>
      <PushNotification />
      <Router>
        <Routes>
          <Route path="/main" element={<KidsMainPage />} />
          <Route path="/btn" element={<Btn />} />
          <Route path="/" element={<Login />} />
          <Route path="/invitecode" element={<InviteCode />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/encyclopedia" element={<Encyclopedia />} />
          <Route path="/nickname/title" element={<NicknameTitle />} />
          <Route path="/menu" element={<Menu />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/nickname/edit" element={<NicknameEdit />} />
          <Route path="/report" element={<Report />} />
          <Route path="/report/detail/gmi" element={<GmiDetail />} />
          <Route path="/report/detail/gv" element={<Variability />} />
          <Route path="/report/detail/mean" element={<Mean />} />
          <Route path="/report/detail/tir" element={<Tir />} />
          <Route path="/record" element={<RecordPage />} />
          <Route path="/record/:date" element={<DayRecordPage />} />
          <Route path="/protector-main" element={<ProtectorMain />} />
          <Route path="/setting" element={<SettingPage />} />
          <Route path="/samsungsetting" element={<SamsungSetting />} />
          <Route
            path="/foregroundsetting"
            element={<ForegoundServiceSetting />}
          />
          <Route path="/medication" element={<Medication />} />
          <Route path="/medication/add" element={<MedicationAdd />} />
        </Routes>
      </Router>
    </GoogleOAuthProvider>
  );
}

export default App;
