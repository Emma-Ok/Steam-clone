'use client';

import React from 'react';
import { useRouter } from 'next/navigation';
import { Button } from '@/src/design-system/atoms/button';
import { Input } from '@/src/design-system/atoms/input';
import { useAuthActions } from '@/src/shared/hooks/use-auth-actions';
import { Notification } from '../../../components/Notification';
import { useNotification } from '../../../hooks/useNotification';

interface AuthPageProps {
  mode: 'login' | 'register';
}

export const AuthPage: React.FC<AuthPageProps> = ({ mode }) => {
  const router = useRouter();
  const { login, register } = useAuthActions();
  const [email, setEmail] = React.useState('');
  const [password, setPassword] = React.useState('');
  const [username, setUsername] = React.useState('');
  const notification = useNotification();
  const [loading, setLoading] = React.useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    notification.hideNotification();
    setLoading(true);

    try {
      if (mode === 'login') {
        const result = await login(email, password);
        if (result.success) {
          router.push('/games');
          router.refresh();
        } else {
          let msg = result.error || 'Error al iniciar sesión';
          if (msg === 'Request failed' || msg === 'Login failed') {
            msg = 'No se pudo iniciar sesión. Verifica tu email y contraseña o intenta más tarde.';
          }
          if (msg.toLowerCase().includes('invalid credentials')) {
            msg = 'Email o contraseña incorrectos.';
          }
          notification.showNotification(msg, 'error');
        }
      } else {
        const result = await register(email, username, password);
        if (result.success) {
          router.push('/games');
          router.refresh();
        } else {
          let msg = result.error || 'Error al registrarse';
          if (msg.toLowerCase().includes('password')) {
            msg = 'La contraseña es demasiado corta. Debe tener al menos 6 caracteres.';
          }
          if (msg.toLowerCase().includes('already exists') || msg.toLowerCase().includes('duplicate') || msg.toLowerCase().includes('correo')) {
            msg = 'Ya existe una cuenta con ese correo electrónico.';
          }
          if (msg === 'Request failed' || msg === 'Registration failed') {
            msg = 'No se pudo registrar. Verifica tus datos o intenta más tarde.';
          }
          notification.showNotification(msg, 'error');
        }
      }
    } finally {
      setLoading(false);
    }
  };

  const isLogin = mode === 'login';

  return (
    <div className="min-h-screen flex items-center justify-center bg-background px-4">
      <div className="w-full max-w-md">
        {/* Hero Section */}
        <div className="mb-12 text-center">
          <div className="w-16 h-16 bg-primary rounded-lg flex items-center justify-center mx-auto mb-6">
            <span className="text-primary-foreground font-bold text-2xl">S</span>
          </div>
          <h1 className="text-3xl font-bold mb-2">SteamClone</h1>
          <p className="text-muted-foreground">
            {isLogin ? 'Welcome back' : 'Join the community'}
          </p>
        </div>

        {/* Auth Form */}
        <div className="bg-card border border-border rounded-lg p-8 space-y-6">
          <div>
            <h2 className="text-2xl font-bold mb-6">
              {isLogin ? 'Login' : 'Create Account'}
            </h2>

            {notification.isVisible && (
              <Notification
                message={notification.message || ''}
                type={notification.type}
                onClose={notification.hideNotification}
              />
            )}
          </div>

          <form onSubmit={handleSubmit} className="space-y-5">
            <Input
              label="Email"
              type="email"
              placeholder="your@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />

            {!isLogin && (
              <Input
                label="Username"
                type="text"
                placeholder="Choose a username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            )}

            <Input
              label="Password"
              type="password"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />

            <Button type="submit" isLoading={loading} className="w-full">
              {isLogin ? 'Login' : 'Create Account'}
            </Button>
          </form>

          {/* OAuth Buttons */}
          <div className="space-y-3">
            <div className="relative">
              <div className="absolute inset-0 flex items-center">
                <div className="w-full border-t border-border" />
              </div>
              <div className="relative flex justify-center text-sm">
                <span className="px-2 bg-card text-muted-foreground">Or continue with</span>
              </div>
            </div>

            <div className="grid grid-cols-2 gap-3">
              <Button
                variant="secondary"
                className="w-full"
                disabled={loading}
                onClick={() => {
                  // OAuth redirect to Google
                  globalThis.location.href = `${process.env.NEXT_PUBLIC_API_URL}/oauth2/authorization/google`;
                }}
              >
                Google
              </Button>
              <Button
                variant="secondary"
                className="w-full"
                disabled={loading}
                onClick={() => {
                  // OAuth redirect to GitHub
                  globalThis.location.href = `${process.env.NEXT_PUBLIC_API_URL}/oauth2/authorization/github`;
                }}
              >
                GitHub
              </Button>
            </div>
          </div>

          {/* Toggle Link */}
          <div className="text-center text-sm">
            {isLogin ? (
              <>
                <span className="text-muted-foreground">Don't have an account? </span>
                <a href="/register" className="text-primary hover:underline font-semibold">
                  Sign up
                </a>
              </>
            ) : (
              <>
                <span className="text-muted-foreground">Already have an account? </span>
                <a href="/login" className="text-primary hover:underline font-semibold">
                  Login
                </a>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
