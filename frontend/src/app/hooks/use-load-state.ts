import { useUserStore } from '@/entities/user/model/store';
import { useEffect } from 'react';
import { useShallow } from 'zustand/shallow';

export const useLoadState = () => {
  const { fetchUser } = useUserStore(
    useShallow((state) => ({ fetchUser: state.fetchUser }))
  );

  useEffect(() => {
    (async () => {
      await fetchUser();
    })();
  }, [fetchUser]);
};
