export type TColor = Record<ColorScale, string>;
export type Palette =
  | 'primary'
  | 'secondary'
  | 'success'
  | 'danger'
  | 'indigo'
  | 'blue'
  | 'dark'
  | 'light';
export type ColorScale =
  | '50'
  | '100'
  | '200'
  | '300'
  | '400'
  | '500'
  | '600'
  | '700'
  | '800'
  | '900'
  | 'A100'
  | 'A200'
  | 'A400'
  | 'A700';
export type FontWeight = 100 | 200 | 300 | 400 | 500 | 600 | 700 | 800 | 900;

export type Size = 'sm' | 'md' | 'lg' | 'xl';
