"use client";
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';

export default function OAuthCallbackPage() {
  const router = useRouter();

  useEffect(() => {
    const params = new URLSearchParams(globalThis.location.search);
    const token = params.get('token');
    if (token) {
      localStorage.setItem('authToken', token);
      router.push('/games');
    } else {
      router.push('/login');
    }
  }, [router]);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <span className="text-lg">Procesando autenticación...</span>
    </div>
  );
}
