export interface BottomBarProps {
  children?: React.ReactNode;
  icons?: string[]; 
  menus?: string[];
  onHandleChange: (menu: number|undefined) => void;
}