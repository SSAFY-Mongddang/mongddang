import { ColorScale, Palette } from '@/shared/model/globalStylesTyes';

export interface ToggleProps extends React.ComponentProps<'div'> {
  color?: Palette;
  scale?: ColorScale;
  size?: number;
  isOn?: boolean;
  onToggle?: (isOn: boolean) => void; 
}