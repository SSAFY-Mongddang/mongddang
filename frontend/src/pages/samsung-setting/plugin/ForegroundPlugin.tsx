import { registerPlugin } from '@capacitor/core';

export interface ForegroundPlugin {
  startForeground(): Promise<{ message: string }>;
  stopForeground(): Promise<{ message: string}>;
  getCurrentMonitoringStauts(): Promise<boolean>
}

export const Foreground = registerPlugin<ForegroundPlugin>('Foreground')