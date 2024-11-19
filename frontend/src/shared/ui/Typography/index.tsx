/** @jsxImportSource @emotion/react */
import { TypographyProps } from "./Typography.types";
import { base } from "./Typography.styles";

export const Typography = ({
  children = 'Typography',
  color = 'primary',
  scale,
  size = '1',
  weight = 100,
  ...props
}: TypographyProps) => {
  
  return(
      <div css={base(color, size, weight, scale)} {...props}>
        {children}
      </div>
  )
}