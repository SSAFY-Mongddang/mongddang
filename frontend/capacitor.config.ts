import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.mongddang.app',
  appName: 'mongddang',
  webDir: 'dist',
  server: {
    url: 'http://192.168.1.5:8100',
    cleartext: true,
  },
  plugins: {
    CapacitorHttp: {
      enabled: true,
    },
  },
};

export default config;
