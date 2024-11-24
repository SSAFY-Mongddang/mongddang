import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.tsx';
import './index.css';
import { QueryClientProvider } from '@tanstack/react-query';
import { MantineProvider, createTheme } from '@mantine/core';
import { DatesProvider } from '@mantine/dates';
import 'dayjs/locale/ko';
import '@mantine/core/styles.layer.css';
import '@mantine/dates/styles.layer.css';
import '@mantine/charts/styles.css';
import AudioCollection from './shared/ui/AudioCollection/AudioCollection.tsx';
import { queryClient } from './shared/lib/queryClient.ts';
import TouchEffect from './shared/ui/TouchEffect/TouchEffect.tsx';

const theme = createTheme({
  primaryColor: 'cyan',
});

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <MantineProvider theme={theme}>
        <DatesProvider
          settings={{ locale: 'ko', firstDayOfWeek: 0, weekendDays: [0] }}
        >
          <TouchEffect>
            <App />
          </TouchEffect>
          <AudioCollection />
        </DatesProvider>
      </MantineProvider>
    </QueryClientProvider>
  </StrictMode>
);
