"use client";

import React from 'react';
import { WebSocketProvider } from './WebSocketProvider';
import ClientButton from './ClientButton';

const ClientWrapper: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  return (
    <WebSocketProvider>
      {children}
      <ClientButton onClick={() => console.log('Button clicked!')}>Click Me</ClientButton>
    </WebSocketProvider>
  );
};

export default ClientWrapper;