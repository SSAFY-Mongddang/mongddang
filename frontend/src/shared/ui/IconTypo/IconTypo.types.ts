import { ReactNode } from "react";
import { Tsize } from "../Typography/Typography.types";

export interface IconTypoProps {
  icon: string; 
  menu: ReactNode;
  selected?: boolean;
  size?: number;
  fontSize?: Tsize;
  disabled?: boolean;
}