/** @jsxImportSource @emotion/react */
import { useState } from 'react';
import { Typography } from '@/shared/ui/Typography';
import { Button } from '@/shared/ui/Button';
import {
  containerCss,
  nextbtnCss,
  buttonGroupCss,
  mainContentCss,
  btnCss,
} from './styles';
import { IconTypo } from '@/shared/ui/IconTypo';
import { UserRole } from '../..';
import child from '@/assets/img/page/child.png'
import protector from '@/assets/img/page/protector.png'

interface UserDataFormProps {
  onSubmit: (role: UserRole) => void;
}

export const RoleSelector = ({ onSubmit }: UserDataFormProps) => {
  const [role, setRole] = useState<UserRole>(undefined);

  return (
    <div css={mainContentCss}>
      <div css={containerCss}>
        <Typography color="dark" size="1.25" weight={700}>
          사용자를 선택해주세요!
        </Typography>
        <div css={buttonGroupCss}>
          <div css={btnCss(role, 'child')} onClick={() => setRole('child')}>
            <IconTypo
              fontSize="1.75"
              icon={child}
              menu="어린이"
              size={6}
            />
          </div>
          <div
            css={btnCss(role, 'protector')}
            onClick={() => setRole('protector')}
          >
            <IconTypo
              fontSize="1.75"
              icon={protector}
              menu="보호자"
              size={6}
            />
          </div>
        </div>
        <div>
          <Button
            css={nextbtnCss}
            disabled={!role}
            color={'light'}
            fontSize="1"
            variant={'contained'}
            handler={() => onSubmit(role)}
          >
            다음
          </Button>
        </div>
      </div>
    </div>
  );
};
