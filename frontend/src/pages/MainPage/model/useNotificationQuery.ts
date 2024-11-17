import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { getNotification, readNotification } from '../api/notificationApi';

export const useNotificationQuery = () => {
  return useQuery({
    queryKey: ['notificationList'],
    queryFn: getNotification,
    staleTime: 0,
  });
};

export const useNotificationReadMutation = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (notificationId: number) => {
      await readNotification(notificationId);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['notificationList'],
      });
    },
  });
};
