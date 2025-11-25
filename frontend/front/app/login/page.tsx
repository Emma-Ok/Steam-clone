"use client";

import { MainLayout } from '@/src/design-system/templates/main-layout';
import { AuthPage } from '@/src/design-system/pages/auth-page';
import React from 'react';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL?.replace(/\/$/, '') || 'http://localhost:8080';

const LoginPage = () => {
  return (
    <MainLayout>
      <AuthPage mode="login" />
    </MainLayout>
  );
};

export default LoginPage;
