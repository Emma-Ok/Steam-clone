import React from 'react';
import { Header } from '@/src/design-system/molecules/header';

interface MainLayoutProps {
  children: React.ReactNode;
}

export const MainLayout: React.FC<MainLayoutProps> = ({ children }) => {
  return (
    <div className="min-h-screen flex flex-col bg-background">
      <Header />
      <main className="flex-1 max-w-7xl w-full mx-auto px-4 py-8">
        {children}
      </main>
      <footer className="bg-card border-t border-border mt-12">
        <div className="max-w-7xl mx-auto px-4 py-6 text-center text-muted-foreground text-sm">
          <p>© 2025 SteamClone. Proyecto para la Universidad de Antioquia.</p>
        </div>
      </footer>
    </div>
  );
};
