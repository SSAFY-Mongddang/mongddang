import { useQuery, useQueryClient } from '@tanstack/react-query';
import { getMainInfo } from '../api/infoApi';

type MainInfoType = {
  nickname: string;
  mainTitleName: string;
  mainMongddangId: number;
  coin: number;
  unreadNotification: boolean;
  unclaimedMissionReward: boolean;
  unclaimedAchivementReward: boolean;
};

export const useMainInfoQuery = () => {
  return useQuery<MainInfoType>({
    queryKey: ['mainInfo'],
    queryFn: getMainInfo,
    staleTime: 0,
  });
};

export const useRefreshMainInfo = () => {
  const queryClient = useQueryClient();
  return () => {
    queryClient.invalidateQueries({
      queryKey: ['mainInfo'],
    });
  };
};
