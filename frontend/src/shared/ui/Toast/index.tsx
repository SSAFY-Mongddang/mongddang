/** @jsxImportSource @emotion/react */
import { ReactNode } from 'react';
import { ToastProps } from './Toast.types';
import { base, variants } from './Toast.styles';
import { HiOutlineCheckCircle, HiOutlineExclamation } from 'react-icons/hi';
import { Palette } from '@/shared/model/globalStylesTyes';
import { Icon } from '../Icon';
import { Typography } from '../Typography';

const colorToIcon: Record<Palette, ReactNode> = {
  primary: <HiOutlineCheckCircle />,
  success: <HiOutlineCheckCircle />,
  danger: <HiOutlineExclamation />,
  indigo: <HiOutlineExclamation />,
  blue: <HiOutlineExclamation />,
  dark: <HiOutlineCheckCircle />,
  light: <HiOutlineCheckCircle />,
  secondary: <HiOutlineCheckCircle />,
};

export const Toast = ({
  children,
  variant = 'contained',
  color = 'primary',
  fontSize = 1,
  isIcon = false,
  ...props
}: ToastProps) => {
  return (
    <div css={[base(fontSize), variants[variant](color, fontSize)]} {...props}>
      {isIcon ? (
        <Icon color={variant === 'filled' ? 'light' : 'dark'}>
          {colorToIcon[color]}
        </Icon>
      ) : undefined}
      <Typography size={'1'} weight={500} color="dark">
        {children}
      </Typography>
    </div>
  );
};
