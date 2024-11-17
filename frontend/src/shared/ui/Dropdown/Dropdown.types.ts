import { ConnectedUser } from '@/entities/user/model';

export interface DropdownProps {
  options: ConnectedUser[];
  onSelect: (value: ConnectedUser) => void;
  isOpen: boolean;
  onOpen: () => void;
  onClose: () => void;
  buttonLabel?: string;
  selectedValue?: string;
}
