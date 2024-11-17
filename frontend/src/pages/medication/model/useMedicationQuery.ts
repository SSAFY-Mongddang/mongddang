import { useQuery } from '@tanstack/react-query';
import { getMedicationList } from '../api/medicactionApi';

export const useMedicationQuery = (nickname: string | undefined) => {
  const fetchMedication = async ({ queryKey }: { queryKey: string[] }) => {
    const [, userNickname] = queryKey;
    return getMedicationList(userNickname);
  };

  return useQuery({
    queryKey: nickname
      ? ['medicationList', nickname ?? '']
      : ['medicationList', ''],
    queryFn: fetchMedication,
    staleTime: 0,
    enabled: !!nickname,
  });
};
