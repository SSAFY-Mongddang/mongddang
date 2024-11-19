/** @jsxImportSource @emotion/react */
import { SpinnerProps } from './Spinner.types';
import { base } from './Spinner.styles';

export const Spinner = ({
  size = 'sm',
  color = 'primary',
  scale,
  ...props
}: SpinnerProps) => {
  return (
    <div css={base(size, color, scale)} {...props}/>
  );
};
