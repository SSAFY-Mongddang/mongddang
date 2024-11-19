import { ReactNode } from 'react';

export type TTopBar = 'page' | 'iconpage' | 'modal';

export interface TopBarProps extends React.ComponentProps<'div'> {
  children?: ReactNode;
  type?: TTopBar;
  iconHandler?: () => void;
}
