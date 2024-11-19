export type MenuCategory =
  | 'meal'
  | 'profile'
  | 'record'
  | 'report'
  | 'setting'
  | 'medication'
  | '';

export type MenuBtnItem = {
  icon: string;
  category: MenuCategory;
  menu: {
    child: string;
    protector: string;
  };
  urlTo: {
    child: string;
    protector: string;
  };
};
