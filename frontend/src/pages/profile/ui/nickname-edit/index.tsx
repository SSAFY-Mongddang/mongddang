/** @jsxImportSource @emotion/react */
import { Button } from '@/shared/ui/Button';
import { TextField } from '@/shared/ui/TextField';
import { TopBar } from '@/shared/ui/TopBar';
import React, { useState } from 'react';
import { containerCss, editCss } from './styles';
import { Typography } from '@/shared/ui/Typography';
import { Palette } from '@/shared/model/globalStylesTyes';
import { useMutation } from '@tanstack/react-query';
import { INickname, checkNickname, updateNickname } from './api';
import { AxiosResponse } from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';

export const NicknameEdit = () => {
  const [nickname, setNickname] = useState<string>('');
  const [color, setColor] = useState<Palette>('primary');
  const [msg, setMsg] = useState<string>('');
  const nav = useNavigate();
  const location = useLocation();
  const prenickname = location.state?.nickname;

  const nicknameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };

  const { mutate: checkNicknameMutation } = useMutation<
    AxiosResponse<void>,
    Error,
    string
  >({
    mutationFn: async (nickname: string) => await checkNickname(nickname),
    onSuccess: () => {
      console.log('조회 성공');
      setMsg('사용 가능한 닉네임입니다.');
      setColor('primary');
    },
    onError: () => {
      console.log('닉네임 조회 실패');
      if (nickname.length === 0) {
        setMsg('닉네임을 작성해주세요.');
        setColor('danger');
      } else {
        setMsg('이미 사용 중인 닉네임입니다.');
        setColor('danger');
      }
    },
  });

  const accessToken = localStorage.getItem('accessToken') || '';
  const { mutate: updateNicknameMutation } = useMutation<
    AxiosResponse<INickname>,
    Error,
    string
  >({
    mutationFn: async (nickname: string): Promise<AxiosResponse<INickname>> => {
      return await updateNickname(accessToken, nickname);
    },
    onSuccess: () => {
      console.log('닉네임 업데이트 성공');
      setMsg('닉네임이 변경되었습니다.');
      setColor('primary');
      nav('/profile');
    },
    onError: (error) => {
      console.log('닉네임 업데이트 실패', error);
      if (nickname.length === 0) {
        setMsg('닉네임을 작성해주세요.');
        setColor('danger');
      } else {
        setMsg('오류가 발생했습니다.');
        setColor('danger');
      }
    },
  });

  return (
    <div>
      <TopBar type="iconpage" iconHandler={()=>nav('/profile')}>닉네임 수정</TopBar>
      <div css={containerCss}>
        <Typography color="dark" size="1" weight={700}>
          현재 닉네임 : {prenickname}
        </Typography>
        <div css={editCss}>
          <TextField
            style={{ flex: '1' }}
            color={color}
            label="닉네임"
            type="text"
            variant="outlined"
            value={nickname}
            onChange={nicknameChange}
          />
          <Button
            handler={() => checkNicknameMutation(nickname)} // 확인 버튼에서 확인
            color="primary"
            fontSize="1"
            variant="contained"
          >
            확인
          </Button>
        </div>

        {msg && (
          <Typography color={color} size="1" weight={500}>
            {msg}
          </Typography>
        )}

        <Button
          style={{ margin: '1rem 0' }}
          handler={() => updateNicknameMutation(nickname)} // 변경하기 버튼에서 변경
          color="primary"
          fontSize="1"
          fullwidth
          variant="contained"
        >
          변경하기
        </Button>
      </div>
    </div>
  );
};
