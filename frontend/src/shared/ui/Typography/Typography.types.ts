import { ColorScale, FontWeight, Palette } from "@/shared/model/globalStylesTyes";
import { ReactNode } from "react";

export type Tsize = '0.75' | '1' | '1.25' | '1.5' | '1.75';

export interface TypographyProps extends React.ComponentProps<'div'> {
  children: ReactNode;
  color?: Palette;
  scale?: ColorScale;
  size?: Tsize;
  weight?: FontWeight;
}
