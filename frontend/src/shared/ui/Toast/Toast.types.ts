import { Palette } from '@/shared/model/globalStylesTyes';
import { ReactNode } from 'react';

export type ToastVariant = 'contained' | 'filled' | 'outlined';

export interface ToastProps extends React.ComponentProps<'div'> {
  children: ReactNode;
  variant?: ToastVariant;
  color?: Palette;
  fontSize?: number;
  isIcon?: boolean;
}
