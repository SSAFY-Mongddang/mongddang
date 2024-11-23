import { registerPlugin } from '@capacitor/core';

export interface ForegroundPlugin {
    startForeground(): Promise<{ message: string }>;
    stopForeground(): Promise<{ message: string}>;
    isCheckStatusMonitoring():Promise<{isActive:boolean}>
  }
  
export const Foreground = registerPlugin<ForegroundPlugin>('Foreground')
