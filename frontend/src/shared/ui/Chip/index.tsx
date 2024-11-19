/** @jsxImportSource @emotion/react */
import { ChipProps } from './Chip.types';
import { base } from './Chip.styles';

export const Chip = ({
  children = 'Chip',
  color = 'primary',
  border = 1,
  fontSize = 1,
  fontWeight = 700,
  ...props
}: ChipProps) => {
  return (
    <span css={base(color, border, fontSize, fontWeight)} {...props}>
      {children}
    </span>
  );
};
