import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.tsx';
import './index.css';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { MantineProvider, createTheme } from '@mantine/core';
import { DatesProvider } from '@mantine/dates';
import '@mantine/core/styles.css';
import 'dayjs/locale/ko';
import '@mantine/charts/styles.css';
import '@mantine/dates/styles.css';

const queryClient = new QueryClient();

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
          <App />
        </DatesProvider>
      </MantineProvider>
    </QueryClientProvider>
  </StrictMode>
);
