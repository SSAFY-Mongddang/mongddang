/** @jsxImportSource @emotion/react */
import { Button } from '@/shared/ui/Button';
// import { Modal } from '@/shared/ui/Modal';
// import { useMutation, useQueryClient } from '@tanstack/react-query';
// import { AxiosResponse } from 'axios';
import {modalContentStyle, modalOverlay} from './styles';
// import PermissionHandler from '../../plugin/PermissionHandler';


interface SamsungModalProps {  
  isOpen: boolean;
  onClose: () => void;
  children: React.ReactNode;
}

export const SamsungModal: React.FC<SamsungModalProps>= ({ isOpen, onClose, children }) => {
  // const queryClient = useQueryClient();
  if (!isOpen) return null;
  // async function checkForegroundServicePermission() {
  //   const result = await PermissionHandler.checkPermission('android.permission.FOREGROUND_SERVICE');
  //   console.log(`Permission granted: ${result.isGranted}`);
  // }
  
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