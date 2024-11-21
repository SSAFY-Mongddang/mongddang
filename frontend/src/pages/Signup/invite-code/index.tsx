/** @jsxImportSource @emotion/react */
import { Button } from '@/shared/ui/Button';
import { TextField } from '@/shared/ui/TextField';
import { Typography } from '@/shared/ui/Typography';
import { containerCss } from '../ui/invite-code.styles';
import { useMutation } from '@tanstack/react-query';
import { invitation } from '../api/api';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ChangeEvent } from 'react';
import { UserInfo, useUserStore } from '@/entities/user/model';
import { useShallow } from 'zustand/shallow';
import { UserService } from '@/shared/api/user/user.service';
import { TopBar } from '@/shared/ui/TopBar';

export const InviteCode = () => {
  const nav = useNavigate();
  const [code, setCode] = useState('');
  const { updateUserInfo, getUserInfo } = useUserStore(
    useShallow((state) => ({
      updateUserInfo: state.updateUserInfo,
      getUserInfo: state.getUserInfo,
    }))
  );

  const inviteMutation = useMutation({
    mutationFn: async () => {
      await invitation(code);
    },
    onSuccess: async () => {
      // alert('연결이 되었습니다.');
      const userInfo = getUserInfo();
      const newUser = (await UserService.userQuery()).data.data;
      try {
        const newUserInfo = userInfo.user && {
          ...userInfo,
          user: newUser,
        };
        await updateUserInfo(newUserInfo as Partial<UserInfo>);
      } catch (err) {
        console.log(err);
        console.log(JSON.stringify(err));
      }
      nav(-1);
    },
    onError: (error) => {
      console.error('환아 등록 실패:', JSON.stringify(error));
      const userInfo = getUserInfo();
      console.log(userInfo.user?.role);
      console.log(userInfo.userAccessToken);

      alert('연결이 실패하였습니다. 다시 시도해주세요');
    },
  });

  const handleCodeChange = (e: ChangeEvent<HTMLInputElement>) => {
    setCode(e.target.value);
  };

  const handleSubmit = () => {
    if (!code.trim()) {
      alert('초대 코드를 입력해주세요.');
      return;
    }
    inviteMutation.mutate();
  };

  return (
    <>
      <TopBar type='iconpage' iconHandler={() => nav('/protector-main')}></TopBar>
      <div css={containerCss}>
        <Typography color="dark" size="1.5" weight={700}>
          아이의 초대코드를
          <br />
          입력해주세요.
        </Typography>
        <TextField
          label=""
          defaultValue=""
          maxRows={10}
          type="text"
          variant="standard"
          value={code}
          onChange={handleCodeChange}
        />
        <Button
          style={{ width: '8rem' }}
          handler={handleSubmit}
          fullwidth
          color="primary"
          fontSize="1"
          variant="contained"
        >
          확인
        </Button>
      </div>
    </>
  );
};
