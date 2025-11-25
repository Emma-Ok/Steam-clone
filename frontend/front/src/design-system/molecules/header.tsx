'use client';

import React from 'react';
import Link from 'next/link';
import { useSession } from '@/src/shared/hooks/use-session';
import { useAuthActions } from '@/src/shared/hooks/use-auth-actions';
import { Button } from '@/src/design-system/atoms/button';

export const Header: React.FC = () => {
  const { isAuthenticated, user, isLoading } = useSession();
  const { logout } = useAuthActions();

  const handleLogout = async () => {
    await logout();
  };

  return (
    <header className="bg-card border-b border-border">
      <div className="max-w-7xl mx-auto px-4 py-4 flex items-center justify-between">
        <Link href="/" className="flex items-center gap-2">
          <div className="w-8 h-8 bg-primary rounded-lg flex items-center justify-center">
            <span className="text-primary-foreground font-bold">S</span>
          </div>
          <span className="font-bold text-xl">SteamClone</span>
        </Link>

        <nav className="flex items-center gap-6">
          <Link href="/games" className="text-foreground hover:text-accent transition-colors">
            Games
          </Link>
          {isAuthenticated && (
            <>
              <Link href="/library" className="text-foreground hover:text-accent transition-colors">
                Library
              </Link>
              <Link href={`/profile/${user?.id}`} className="text-foreground hover:text-accent transition-colors">
                Profile
              </Link>
            </>
          )}
        </nav>

        <div className="flex items-center gap-3">
          {isLoading ? (
            <span className="text-muted-foreground">Loading...</span>
          ) : isAuthenticated ? (
            <>
              <span className="text-muted-foreground">{user?.username}</span>
              <Button variant="ghost" size="sm" onClick={handleLogout}>
                Logout
              </Button>
            </>
          ) : (
            <>
              <Link href="/login">
                <Button variant="ghost" size="sm">
                  Login
                </Button>
              </Link>
              <Link href="/register">
                <Button size="sm">
                  Register
                </Button>
              </Link>
            </>
          )}
        </div>
      </div>
    </header>
  );
};
