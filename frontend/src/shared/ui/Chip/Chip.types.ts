import { FontWeight, Palette } from '@/shared/model/globalStylesTyes';
import { ReactNode } from 'react';

export interface ChipProps extends React.ComponentProps<'span'> {
  children?: ReactNode;
  color?: Palette;
  border?: number;
  fontSize?:number;
  fontWeight?: FontWeight;
}
