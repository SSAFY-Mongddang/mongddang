/** @jsxImportSource @emotion/react */
import { Button } from '@/shared/ui/Button';
import { Modal } from '@/shared/ui/Modal';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { AxiosResponse } from 'axios';
import { base, modalContentStyle, modalCss, modalOverlay, storyTypographyCss, xiconCss } from './styles';
import { useState } from 'react';
import { getMainInfo } from '@/pages/Encyclopedia/api/api';


interface SamsungModalProps {
  isOpen: boolean;
  onClose: () => void;
  children: React.ReactNode;
}

export const SamsungModal: React.FC<SamsungModalProps>= ({ isOpen, onClose, children }) => {
  // const queryClient = useQueryClient();
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