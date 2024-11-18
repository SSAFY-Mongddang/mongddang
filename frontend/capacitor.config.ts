import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.mongddang.app',
  appName: 'mongddang',
  webDir: 'dist',
  server: {
    url: 'https://1d36-14-46-142-181.ngrok-free.app',
    cleartext: true,
  },
  plugins: {
    CapacitorHttp: {
      enabled: true,
    },
  },
};

export default config;
