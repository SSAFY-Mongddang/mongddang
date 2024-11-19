import { Colors } from './globalStyles';

// 디자인 스타일 (hover, actice, disabled 등..)

const ColorStyle = {
  primary: {
    button: Colors.primary['A200'],
    main: Colors.primary['A200'],
    contrastText: '#fff',
    hover: Colors.primary['A400'],
    active: Colors.primary['A100'],
    disabled: Colors.primary['100'],
  },
  secondary: {
    button: Colors.secondary['200'],
    main: Colors.secondary['200'],
    contrastText: '#fff',
    hover: Colors.secondary['500'],
    active: Colors.secondary['A100'],
    disabled: Colors.secondary['100'],
  },
  danger: {
    button: Colors.danger['500'],
    main: Colors.danger['500'],
    contrastText: '#fff',
    hover: Colors.danger['A200'],
    active: Colors.danger['A100'],
    disabled: Colors.danger['200'],
  },
  indigo: {
    button: Colors.indigo['500'],
    main: Colors.indigo['500'],
    contrastText: '#fff',
    hover: Colors.indigo['A200'],
    active: Colors.indigo['300'],
    disabled: Colors.indigo['300'],
  },
  blue: {
    button: Colors.blue['500'],
    main: Colors.blue['500'],
    contrastText: '#fff',
    hover: Colors.blue['A200'],
    active: Colors.blue['300'],
    disabled: Colors.blue['300'],
  },
  success: {
    button: Colors.success['500'],
    main: Colors.success['A400'],
    contrastText: '#fff',
    hover: Colors.success['A200'],
    active: Colors.success['300'],
    disabled: Colors.success['300'],
  },
  dark: {
    button: '#000',
    main: '#000',
    contrastText: '#fff',
    hover: Colors.dark['700'],
    active: Colors.dark['500'],
    disabled: Colors.dark['300'],
  },
  light: {
    button: Colors.primary['A200'],
    main: '#fff',
    contrastText: '#000',
    hover: '#f2f2f2',
    active: '#8FDCFF',
    disabled: '#eee',
  },
  text: {
    primary: Colors.primary['900'],
    secondary: Colors.secondary['400'],
    danger: Colors.danger['A200'],
    indigo: Colors.indigo['900'],
    blue: Colors.blue['900'],
    success: Colors.success['900'],
    dark: Colors.dark['900'],
    light: Colors.primary['900'],
    disabled: Colors.dark.A100,
  },
  background: {
    primary: Colors.primary['200'],
    secondary: Colors.secondary['50'],
    danger: Colors.danger['50'],
    indigo: Colors.indigo['400'],
    blue: Colors.blue['400'],
    success: Colors.success['400'],
    dark: Colors.dark['700'],
    light: Colors.light['700'],
  },
};

export default ColorStyle;