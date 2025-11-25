"use client";

import React, { createContext, useContext } from 'react';

const WebSocketContext = createContext<WebSocket | null>(null);

export const WebSocketProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  return <WebSocketContext.Provider value={null}>{children}</WebSocketContext.Provider>;
};

export const useWebSocket = () => {
  return useContext(WebSocketContext);
};