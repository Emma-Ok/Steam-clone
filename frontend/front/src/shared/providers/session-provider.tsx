"use client";

import React from 'react';
import { AuthProvider } from '@/src/shared/providers/auth-context';

export const SessionProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  return <AuthProvider>{children}</AuthProvider>;
};
