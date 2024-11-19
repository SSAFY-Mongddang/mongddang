
import { FontWeight, Palette } from "@/shared/model/globalStylesTyes";
import React, { ReactNode } from "react";
 

export interface MenuTabProps extends React.ComponentProps<'div'> {
  children: ReactNode[];
  color?: Palette;
  fontSize? : number;
  fontWeight? :FontWeight;
  onChangeMenu: (menu: number) => void;
}
