/** @jsxImportSource @emotion/react */
import React, { useState } from 'react';
import { Button } from '@/shared/ui/Button';
import { TextField } from '@/shared/ui/TextField';
import { Typography } from '@/shared/ui/Typography';
import { textFieldCss } from './styles';
import { handleDateChange } from '@/Utils/birthUtils';
import { useNavigate } from 'react-router-dom';
import { UserRole } from '../..';
import { useMutation } from '@tanstack/react-query';
import { INickname, checkNickname } from '@/pages/profile/ui/nickname-edit/api';
import { Palette } from '@/shared/model/globalStylesTyes';
import { AxiosResponse } from 'axios';
import { signUp } from '../../api/api';
import { useUserStore } from '@/entities/user/model';
import { useShallow } from 'zustand/shallow';
import { SignupResponse } from '@/shared/api/user/user.type';

export const DataForm = ({ role }: { role: UserRole }) => {
  const [gender, setGender] = useState<'male' | 'female' | undefined>(
    undefined
  );
  const [birthYear, setBirthYear] = useState<string>('');
  const [birthMonth, setBirthMonth] = useState<string>('');
  const [birthDay, setBirthDay] = useState<string>('');
  const [nickname, setNickname] = useState<string>('');
  const [name, setName] = useState<string>('');
  const [color, setColor] = useState<Palette>('primary');
  const [msg, setMsg] = useState<string>('');
  const nav = useNavigate();
  // const location = useLocation();
  // const idToken = location.state?.idToken;
  const { updateUserInfo, getUserInfo } = useUserStore(
    useShallow((state) => ({
      updateUserInfo: state.updateUserInfo,
      getUserInfo: state.getUserInfo,
    }))
  );

  const handleDateChangeWrapper =
    (type: 'year' | 'month' | 'day') =>
    (e: React.ChangeEvent<HTMLInputElement>) => {
      handleDateChange(
        e.target.value,
        type,
        type === 'year'
          ? setBirthYear
          : type === 'month'
            ? setBirthMonth
            : setBirthDay
      );
    };

  const getBirthString = () =>
    `${birthYear}-${birthMonth.padStart(2, '0')}-${birthDay.padStart(2, '0')}`;

  const handleNicknameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newNickname = e.target.value.slice(0, 10); // 10자 제한
    setNickname(newNickname);
  };

  const signupHandler = async () => {
    signUpMutation.mutate();
  };

  const signUpMutation = useMutation({
    mutationFn: async () => {
      if (
        !nickname ||
        !birthYear ||
        !birthMonth ||
        !birthDay ||
        !name ||
        !gender ||
        !role
      ) {
        alert('모든 필드를 입력해주세요.');
        throw new Error('모든 필드를 입력해주세요.');
      }

      const birth = getBirthString();

      try {
        const idToken = getUserInfo().userIdToken;
        if (!idToken) throw new Error('no idToken');

        return await signUp(idToken, role, birth, name, nickname, gender);
      } catch (err) {
        console.log(JSON.stringify(err));
        throw new Error('SignUp err');
      }
    },
    onSuccess: async (data: AxiosResponse<SignupResponse>) => {
      //TODO: capa 토스트 고려해보기
      alert('회원가입이 완료되었습니다.');
      // preference에 user 정보 저장
      
      // TODO: Access Token 하드코딩 수정
      // const userAccessToken = data.data.data.accessToken;
      const user = data.data.data.userInfo;
      await updateUserInfo({ user });
      if (user.role === 'child') nav('/main');
      if (user.role === 'protector') nav('/protector-main');
    },
    onError: (error) => {
      console.error('회원가입 실패:', error);
      alert('회원가입에 실패했습니다. 다시 시도해주세요.');
    },
  });

  const accessToken = getUserInfo().userAccessToken ?? '';
  const { mutate } = useMutation<
    AxiosResponse<INickname>,
    Error,
    { accessToken: string; nickname: string }
  >({
    mutationFn: async ({ nickname }): Promise<AxiosResponse<INickname>> => {
      return await checkNickname(nickname);
    },
    onSuccess: () => {
      setMsg('사용 가능한 닉네임입니다.');
      setColor('primary');
    },
    onError: () => {
      setMsg(
        nickname.length === 0
          ? '닉네임을 작성해주세요.'
          : '이미 사용 중인 닉네임입니다.'
      );
      setColor('danger');
    },
  });

  return (
    <div style={{ margin: '2rem' }}>
      <Typography color="dark" size="1.25" weight={600}>
        회원가입을 위해
        <br />
        정보를 입력해주세요!
      </Typography>
      <div css={textFieldCss}>
        <div style={{ display: 'flex', gap: '1rem', marginTop: '1rem' }}>
          <TextField
            color="primary"
            label="닉네임"
            variant="outlined"
            value={nickname}
            onChange={handleNicknameChange}
            style={{ flexGrow: 1 }}
          />
          <Button
            color="primary"
            fontSize="1"
            variant="contained"
            handler={() => mutate({ accessToken, nickname })}
          >
            확인
          </Button>
        </div>
        {msg && (
          <Typography color={color} size="1" weight={500}>
            {msg}
          </Typography>
        )}
        <div style={{ margin: '1rem 0' }}>
          <TextField
            color="primary"
            label="이름"
            variant="outlined"
            onChange={(e) => setName(e.target.value)}
          />
        </div>
        <Typography color="dark" size="1" weight={500}>
          생년월일
        </Typography>
        <div style={{ display: 'flex', gap: '1rem', margin: '1rem 0' }}>
          <TextField
            color="primary"
            label="YYYY"
            variant="outlined"
            value={birthYear}
            onChange={handleDateChangeWrapper('year')}
          />
          <TextField
            color="primary"
            label="MM"
            variant="outlined"
            value={birthMonth}
            onChange={handleDateChangeWrapper('month')}
          />
          <TextField
            color="primary"
            label="DD"
            variant="outlined"
            value={birthDay}
            onChange={handleDateChangeWrapper('day')}
          />
        </div>
        <div
          style={{
            display: 'flex',
            justifyContent: 'center',
            gap: '1rem',
            marginTop: '1.3rem',
          }}
        >
          <Button
            color="light"
            fontSize="1"
            fullwidth
            variant={gender === 'male' ? 'contained' : 'outlined'}
            handler={() => setGender('male')}
          >
            남자
          </Button>
          <Button
            color="light"
            fontSize="1"
            fullwidth
            variant={gender === 'female' ? 'contained' : 'outlined'}
            handler={() => setGender('female')}
          >
            여자
          </Button>
        </div>
      </div>
      <div
        style={{
          display: 'flex',
          justifyContent: 'flex-end',
          marginTop: '2rem',
        }}
      >
        <Button
          color="primary"
          fontSize="1"
          variant="contained"
          handler={signupHandler}
          fullwidth
        >
          가입하기
        </Button>
      </div>
    </div>
  );
};
