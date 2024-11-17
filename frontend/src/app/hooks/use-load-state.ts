import { useUserStore } from '@/entities/user/model/store';
import { useEffect } from 'react';
import { useShallow } from 'zustand/shallow';

export const useLoadState = () => {
  const { fetchUserInfo } = useUserStore(
    useShallow((state) => ({
      fetchUserInfo: state.fetchUserInfo,
    }))
  );

  useEffect(() => {
    (async () => {
      await fetchUserInfo();
    })();
  }, [fetchUserInfo]);
};
