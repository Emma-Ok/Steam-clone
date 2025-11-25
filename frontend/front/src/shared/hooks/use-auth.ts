'use client';

import { useState, useCallback, useEffect } from 'react';
import { User, AuthResponse } from '@/types';
import { API_ENDPOINTS } from '@/src/shared/constants/api';
import { get, post } from '@/src/shared/utils/api-client';

interface UseAuthState {
  user: User | null;
  isAuthenticated: boolean;
  loading: boolean;
  error: string | null;
}

export function useAuth() {
  const [state, setState] = useState<UseAuthState>({
    user: null,
    isAuthenticated: false,
    loading: true,
    error: null,
  });

  // Check auth status on mount
  const loadUser = useCallback(async () => {
    setState((prev) => ({ ...prev, loading: true }));
    const response = await get<User>(API_ENDPOINTS.ME);

    if (response.data) {
      const user = response.data;
      setState({
        user,
        isAuthenticated: true,
        loading: false,
        error: null,
      });
      return { success: true, user } as const;
    }

    localStorage.removeItem('authToken');
    const error = response.error?.message || 'No pudimos recuperar tu perfil.';
    setState({
      user: null,
      isAuthenticated: false,
      loading: false,
      error,
    });
    return { success: false, error } as const;
  }, []);

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    if (token) {
      loadUser();
    } else {
      setState((prev) => ({ ...prev, loading: false }));
    }
  }, [loadUser]);

  const login = useCallback(async (email: string, password: string) => {
    setState((prev) => ({ ...prev, loading: true, error: null }));
    const response = await post<AuthResponse>(API_ENDPOINTS.LOGIN, { email, password });

    if (response.data) {
      localStorage.setItem('authToken', response.data.accessToken);
      const result = await loadUser();
      return result.success ? { success: true } : { success: false, error: result.error };
    }

    const error = response.error?.message || 'Login failed';
    setState((prev) => ({ ...prev, loading: false, error }));
    return { success: false, error };
  }, [loadUser]);

  const logout = useCallback(async () => {
    localStorage.removeItem('authToken');
    setState({
      user: null,
      isAuthenticated: false,
      loading: false,
      error: null,
    });
  }, []);

  return { ...state, login, logout };
}
