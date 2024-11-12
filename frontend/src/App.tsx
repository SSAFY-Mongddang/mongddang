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
import { RecordPage, TodayRecordPage } from './pages/record';
import { Encyclopedia } from './pages/Encyclopedia';
import { SignUp } from './pages/Signup';
import ProtectorMain from './pages/protectormain/ProtectorMain';
import { useLoadState } from './app/hooks/use-load-state';
import { InviteCode } from './pages/Signup/invite-code';


function App() {
  useLoadState();
  return (
    <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_AUTH_CLIENT_ID}>
      <Router>
        <Routes>
          <Route path="/" element={<KidsMainPage />} />
          <Route path="/btn" element={<Btn />} />
          <Route path="/login" element={<Login />} />
          <Route path="/invitecode" element={<InviteCode />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/encyclopedia" element={<Encyclopedia />} />
          <Route path="/nickname/title" element={<NicknameTitle />} />
          <Route path="/menu" element={<Menu />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/nickname/edit" element={<NicknameEdit />} />
          <Route path="/report" element={<Report />} />
          <Route path="/record" element={<RecordPage />} />
          <Route path="/record/:date" element={<TodayRecordPage />} />
          <Route path="/protector-main" element={<ProtectorMain />} />
        </Routes>
      </Router>
    </GoogleOAuthProvider>
  );
}

export default App;
