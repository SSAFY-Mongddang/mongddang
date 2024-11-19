import { QueryClient } from '@tanstack/react-query';

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 2,
      staleTime: 0.3 * 60 * 1000,
      gcTime: 0.5 * 60 * 1000,
      refetchOnWindowFocus: false, 
      refetchOnReconnect: true,
      refetchOnMount: true, 
    },
  },
});
