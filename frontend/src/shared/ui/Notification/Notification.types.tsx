import { ReactNode } from 'react';

export type Tnotification = 'confirm' | 'primary' | 'alert';

export interface NotificationProps extends React.ComponentProps<'div'> {
  ment: ReactNode;
  type: Tnotification;
  twoBtn?: boolean;
  width?: number;
  height?: number;
  bluehandler: () => void;
  redHandler?: () => void;
  children?: ReactNode[];
}
