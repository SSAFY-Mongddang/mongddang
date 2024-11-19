import { ColorScale, Palette } from '@/shared/model/globalStylesTyes';
import { ReactNode } from 'react';
import { Tsize } from '../Typography/Typography.types';

export type ButtonVariant = 'contained' | 'outlined';

export interface ButtonProps extends React.ComponentProps<'button'> {
  children: ReactNode;
  variant?: ButtonVariant;
  fontSize?:Tsize;
  color?: Palette;
  scale?: ColorScale;
  disabled?: boolean;
  isShadow?:boolean;
  fullwidth?: boolean;
  handler: () => void;
}