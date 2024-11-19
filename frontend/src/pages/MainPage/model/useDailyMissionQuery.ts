import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { getDailyMission, getReward } from '../api/infoApi';

export const useDailyMissionQuery = () => {
  return useQuery({
    queryKey: ['dailyMissionList'],
    queryFn: getDailyMission,
    staleTime: 0,
  });
};

export const useMissionRewardMutation = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (missionId: number) => {
      await getReward(missionId);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['dailyMissionList'],
      });
    },
  });
};
