import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.mongddang.app',
  appName: 'mongddang',
  webDir: 'dist',
  server: {
    url: 'https://frontmal.ngrok.app',
    cleartext: true,
  },
  plugins: {
    CapacitorHttp: {
      enabled: true,  
    },
  },
};

export default config;
