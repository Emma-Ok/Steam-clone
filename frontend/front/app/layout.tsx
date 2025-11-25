import { Analytics } from '@vercel/analytics/next'
import { SessionProvider } from '@/src/shared/providers/session-provider'
import './globals.css'
import { Geist, Geist_Mono } from 'next/font/google'
import ClientWrapper from '../components/ClientWrapper';

const _geist = Geist({ subsets: ["latin"] });
const _geistMono = Geist_Mono({ subsets: ["latin"] });

export const metadata = {
  title: 'SteamClone - Game Library & Community',
  description: 'Discover and manage your favorite games with SteamClone',
  generator: 'v0.app',
};

export default function Layout({ children }: { readonly children: React.ReactNode }) {
  return (
    <html lang="en">
      <body className={`font-sans antialiased`}>
        <SessionProvider>
          <ClientWrapper>
            {children}
          </ClientWrapper>
        </SessionProvider>
        <Analytics />
      </body>
    </html>
  );
}
