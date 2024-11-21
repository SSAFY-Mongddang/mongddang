/** @jsxImportSource @emotion/react */
import { TopBar } from '@/shared/ui/TopBar';
import { Icon } from '@/shared/ui/Icon';
import { base, containerCss, toastCss } from './ui/styles';
import { Chip } from '@/shared/ui/Chip';
import { imgCss } from '../Encyclopedia/ui/styles';
import space from '../../assets/img/space.png';
import { Typography } from '@/shared/ui/Typography';
import { Button } from '@/shared/ui/Button';
// import { useQuery } from '@tanstack/react-query';
// import { getUserInfo } from './api/mypage-api';
import { useNavigate } from 'react-router-dom';
import { Toast } from '@/shared/ui/Toast';

import { useState } from 'react';
import { useUserStore } from '@/entities/user/model';
import { Clipboard } from '@capacitor/clipboard';

export const Profile = () => {
  const nav = useNavigate();
  const [showToast, setShowToast] = useState(false);
  const getUserInfo = useUserStore((state) => state.getUserInfo);

  // const ProfileQuery = useQuery({
  //   queryKey: ['profile'],
  //   queryFn: async () => {
  //     const accessToken = localStorage.getItem('accessToken') || ''; // 로컬 스토리지에서 accessToken 가져오기
  //     return await getUserInfo(accessToken);
  //   },
  // });
  // const inviteCode = ProfileQuery.data?.data?.data?.invitationCode; // 초대 코드 가져오기
  const user = getUserInfo().user;
  const inviteCode = user?.invitationCode;

  const copyToClipboard = async () => {
    if (inviteCode) {
      await Clipboard.write({ string: inviteCode });
      setShowToast(true); // Toast 표시
      setTimeout(() => {
        setShowToast(false); // 3초 후 Toast 숨김
      }, 3000);
      // navigator.clipboard
      //   .writeText(inviteCode)
      //   .then(() => {
      //     setShowToast(true); // Toast 표시
      //     setTimeout(() => {
      //       setShowToast(false); // 3초 후 Toast 숨김
      //     }, 3000);
      //   })
      //   .catch((err) => {
      //     console.error('복사 실패:', err);
      //   });
    } else {
      alert('초대 코드가 없습니다.');
    }
  };
  // console.log(ProfileQuery);

  const navHandler = () => {
    if (user?.role === 'child') nav('/menu');
    if (user?.role === 'protector') nav('/protector-main');
  };

  return (
    <div>
      <div css={toastCss(showToast)}>
        <Toast color="success" fontSize={0.5} isIcon variant="contained">
          초대 코드가 복사되었습니다.
        </Toast>
      </div>
      <TopBar type="iconpage" iconHandler={navHandler}>
        프로필
      </TopBar>
      <div css={base}>
        <Icon size={5}>
          <img alt="icon-1" src="/img/%EB%A7%90%EB%9E%912.png" />
        </Icon>
        <Chip border={1} color="primary" fontSize={0.8} fontWeight={600}>
          {user?.role === 'child' ? '어린이' : '보호자'}
        </Chip>
      </div>
      <img css={imgCss} src={space} alt="배경 이미지" />
      <div css={containerCss}>
        <Chip border={1} color="primary" fontSize={0.8} fontWeight={600}>
          프로필
        </Chip>
        <div
          style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}
        >
          <Typography color="dark" size="1" weight={700}>
            이름: {user?.name}
          </Typography>
          <Typography color="dark" size="1" weight={700}>
            닉네임: {user?.nickname}
          </Typography>
          <Typography color="dark" size="1" weight={700}>
            성별:
            {user?.gender === 'male' ? '남자' : '여자'}
          </Typography>
          <Typography color="dark" size="1" weight={700}>
            생년월일: {user?.birth}
          </Typography>
        </div>
        <div
          style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}
        >
          {user?.role === 'child' && (
            <Chip border={1} color="primary" fontSize={0.8} fontWeight={600}>
              연결된 보호자
            </Chip>
          )}
          {user?.connected?.map((guardian, index) => (
            <Typography key={index} color="dark" size="1" weight={700}>
              {guardian.name} ({guardian.nickname})
            </Typography>
          ))}
        </div>
      </div>
      <div style={{ display: 'flex', margin: '0 1rem', gap: '1rem' }}>
        {user?.role === 'child' && (
          <Button
            handler={copyToClipboard}
            color="secondary"
            fontSize="1.25"
            variant="contained"
            fullwidth
          >
            초대코드 복사
          </Button>
        )}
        <Button
          handler={() =>
            nav('/nickname/edit', {
              state: { nickname: user?.nickname },
            })
          }
          color="primary"
          fontSize="1.25"
          variant="contained"
          fullwidth
        >
          닉네임 수정
        </Button>
      </div>
    </div>
  );
};
