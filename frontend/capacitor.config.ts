import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.mongddang.app',
  appName: 'mongddang',
  webDir: 'dist',
  server: {
    url: 'https://62d5-14-46-142-178.ngrok-free.app',
    cleartext: true,
  },
};

export default config;
