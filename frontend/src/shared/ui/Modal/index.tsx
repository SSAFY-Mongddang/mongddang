/** @jsxImportSource @emotion/react */
import { ModalProps } from './Modal.types';
import { Backdrop } from '../Backdrop';
import { backdropStyle, base } from './Modal.styles';

export const Modal = ({
  children,
  height = 60,
  width = 30,
  ...props
}: ModalProps) => {
  return (
    <Backdrop css={backdropStyle}>
      <div css={base(height, width)} {...props}>
        {children}
      </div>
    </Backdrop>
  );
};
