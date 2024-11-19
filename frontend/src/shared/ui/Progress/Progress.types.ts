import { Palette, Size } from "@/shared/model/globalStylesTyes";

export type ProgressVariant = 'rounded' | 'rectangle';

export interface ProgressProps extends React.ComponentProps<'div'> {
  color?: Palette;
  value: number;
  max: number;
  variant?: ProgressVariant;
  transparentBackground?: boolean;
  size: Size;
}
