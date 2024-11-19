import { ColorScale, Palette, Size } from '@/shared/model/globalStylesTyes';

export interface SpinnerProps extends React.ComponentProps<'div'> {
  size?: Size;
  color?: Palette;
  scale?: ColorScale;
}
