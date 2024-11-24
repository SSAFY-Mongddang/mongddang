import { QueryClient } from '@tanstack/react-query';

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 1,
      staleTime: 0,
      gcTime: 0.5 * 60 * 1000,
      refetchOnWindowFocus: false, 
      refetchOnReconnect: true,
      refetchOnMount: true, 
    },
  },
});
