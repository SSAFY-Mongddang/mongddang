/** @jsxImportSource @emotion/react */
import { ProgressProps } from './Progress.types';
import { base, fillCss, sizeCss, variantCss } from './Progress.styles';

export const Progress = ({
  color = 'primary',
  variant = 'rounded',
  transparentBackground = false,
  size = 'md',
  value = 1,
  max = 3,
  ...props
}: ProgressProps) => {


  return (
    <div
      css={[base(transparentBackground), sizeCss[size], variantCss[variant]]}
      {...props}
    >
      <div
        css={fillCss(value, max, color)}
      ></div>
    </div>
  );
};
