/** @jsxImportSource @emotion/react */ 
import { BackdropProps } from './Backdrop.types'; 
import { base } from './Backdrop.styles'; 

export const Backdrop = ({ 
  children, 
  opacity = 40, 
  blur = 20, 
  ...props 
}: BackdropProps) => { 
  return ( 
    <div css={base(opacity, blur)} {...props}> 
      {children} 
    </div> 
  ); 
};