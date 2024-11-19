import { ReactNode } from 'react';

export interface BackdropProps extends React.ComponentProps<'div'> {
  children?: ReactNode;
  opacity?: number;
  blur?: number;
}
