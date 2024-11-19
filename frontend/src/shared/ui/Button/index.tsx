/** @jsxImportSource @emotion/react */
import { ButtonProps } from './Button.types';
import { base, variantCss } from './Button.styles';
import { useAudioStore } from '@/shared/model/useAudioStore';
export const Button = ({
  children,
  variant = 'contained',
  color = 'primary',
  scale,
  disabled = false,
  fullwidth = false,
  isShadow = false,
  fontSize = '1',
  handler,
  ...props
}: ButtonProps) => {
  const audio = useAudioStore();
  return (
    <button
      css={[
        base(color, fullwidth, isShadow, fontSize), // isShadow 전달
        variantCss(variant, color, scale),
      ]}
      disabled={disabled}
      onClick={() => {
        handler();
        audio.bubble.play();
      }}
      {...props}
    >
      {children}
    </button>
  );
};
