'use client';

import { useCallback } from 'react';
import { API_ENDPOINTS } from '@/src/shared/constants/api';
import { useAuthContext } from '@/src/shared/providers/auth-context';
import { get, post } from '@/src/shared/utils/api-client';
import { AuthResponse, User } from '@/types';

async function fetchProfile() {
  const response = await get<User>(API_ENDPOINTS.ME);
  if (response.data) {
    return response.data;
  }
  throw new Error(response.error?.message || 'No pudimos obtener tu perfil.');
}

export function useAuthActions() {
  const { setUser, setAuthToken, clearAuth } = useAuthContext();

  const login = useCallback(
    async (email: string, password: string) => {
      const response = await post<AuthResponse>(API_ENDPOINTS.LOGIN, { email, password });

      if (!response.data) {
        let errorMsg = response.error?.message || 'Login failed';
        if (response.error?.details) {
          errorMsg += '\n' + Object.values(response.error.details).join(' ');
        }
        return { success: false, error: errorMsg } as const;
      }

      try {
        setAuthToken(response.data.accessToken);
        const profile = await fetchProfile();
        setUser(profile);
        return { success: true, user: profile } as const;
      } catch (error) {
        clearAuth();
        const message = error instanceof Error ? error.message : 'No pudimos obtener tu perfil.';
        return { success: false, error: message } as const;
      }
    },
    [setAuthToken, setUser, clearAuth]
  );

  const register = useCallback(
    async (email: string, username: string, password: string) => {
      const response = await post<AuthResponse>(API_ENDPOINTS.REGISTER, {
        email,
        username,
        password,
      });

      if (!response.data) {
        let errorMsg = response.error?.message || 'Registration failed';
        if (response.error?.details) {
          errorMsg += '\n' + Object.values(response.error.details).join(' ');
        }
        return {
          success: false,
          error: errorMsg,
        } as const;
      }

      try {
        setAuthToken(response.data.accessToken);
        const profile = await fetchProfile();
        setUser(profile);
        return { success: true, user: profile } as const;
      } catch (error) {
        clearAuth();
        const message = error instanceof Error ? error.message : 'No pudimos obtener tu perfil.';
        return { success: false, error: message } as const;
      }
    },
    [setAuthToken, setUser, clearAuth]
  );

  const logout = useCallback(async () => {
    try {
      await post(API_ENDPOINTS.LOGOUT);
    } catch (error) {
      console.error('Logout error:', error);
    } finally {
      clearAuth();
    }
  }, [clearAuth]);

  return { login, register, logout };
}
