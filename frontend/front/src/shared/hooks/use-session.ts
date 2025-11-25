'use client';

import { useAuthContext } from '@/src/shared/providers/auth-context';

export const useSession = () => {
  const { user, isAuthenticated, isLoading } = useAuthContext();
  return { user, isAuthenticated, isLoading };
};
