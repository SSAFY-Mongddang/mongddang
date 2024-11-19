import { ReactNode } from "react";

export interface TabMenuProps {
  contents: ReactNode[];
  activeTab?: string;
  onTabChange?: (tabId: string) => void;
}
