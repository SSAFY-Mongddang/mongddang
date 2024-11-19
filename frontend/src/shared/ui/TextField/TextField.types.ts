import { Palette } from "@/shared/model/globalStylesTyes";


export type TextFieldVariant = 'outlined' | 'standard';

export interface TextFieldProps
  extends Omit<React.ComponentProps<'input'>, 'size'> {
  color?: Palette;
  defaultValue?: string;
  label?: string;
  maxRows?: number;
  multiLine?: boolean;
  placeholder?: string;
  readOnly?: boolean;
  type?: string;
  variant?: TextFieldVariant;
}
