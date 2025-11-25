'use client';

import React, { createContext, useContext, useEffect, useMemo, useState, useCallback } from 'react';
import { User } from '@/types';
import { API_ENDPOINTS } from '@/src/shared/constants/api';
import { get } from '@/src/shared/utils/api-client';

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  setUser: (user: User | null) => void;
  setAuthToken: (token: string) => void;
  clearAuth: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const initializeAuth = async () => {
      const token = localStorage.getItem('authToken');
      if (token) {
        try {
          const response = await get<User>(API_ENDPOINTS.ME);
          if (response.data) {
            setUser(response.data);
          } else {
            localStorage.removeItem('authToken');
          }
        } catch (error) {
          console.error('Failed to fetch user:', error);
          localStorage.removeItem('authToken');
        }
      }
      setIsLoading(false);
    };

    initializeAuth();
  }, []);

  const setAuthToken = useCallback((token: string) => {
    localStorage.setItem('authToken', token);
  }, []);

  const clearAuth = useCallback(() => {
    localStorage.removeItem('authToken');
    setUser(null);
  }, []);

  const value = useMemo(
    () => ({
      user,
      isAuthenticated: !!user,
      isLoading,
      setUser,
      setAuthToken,
      clearAuth,
    }),
    [user, isLoading, setAuthToken, clearAuth]
  );

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuthContext = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuthContext must be used within AuthProvider');
  }
  return context;
};
