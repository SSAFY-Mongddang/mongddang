/** @jsxImportSource @emotion/react */
import { Button } from '@/shared/ui/Button';
import {modalContentStyle, modalOverlay} from '../style';


interface SamsungModalProps {  
  isOpen: boolean;
  onClose: () => void;
  children: React.ReactNode;
}

export const SamsungModal: React.FC<SamsungModalProps>= ({ isOpen, onClose, children }) => {
  if (!isOpen) return null;
  
  return (
    <div css={modalOverlay} onClick={onClose}>
      <div css={modalContentStyle} onClick={(e) => e.stopPropagation()}>
        {children}
        <Button handler={onClose}  
            color="primary" 
            fontSize="1.25"
            variant="contained"
            fullwidth>닫기</Button>
      </div>
    </div>
  )
}
