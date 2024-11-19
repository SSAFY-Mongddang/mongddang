import { ReactElement, ReactNode } from 'react';

export interface IconProps extends React.ComponentProps<'span'> {
  children: ReactElement<'img'> | ReactNode;
  size?: number;
}
