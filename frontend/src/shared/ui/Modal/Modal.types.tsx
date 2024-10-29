import { ReactNode } from 'react';

export interface ModalProps extends React.ComponentProps<'div'> {
  children: ReactNode;
  xHandler?: () => void;
}
