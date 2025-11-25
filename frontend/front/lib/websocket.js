'use client';
import { useEffect, useRef } from 'react';

export const useWebSocket = (url, onMessage) => {
  const socket = useRef(null);

  useEffect(() => {
    socket.current = new WebSocket(url);

    socket.current.onopen = () => {
      console.log('WebSocket connected');
    };

    socket.current.onmessage = (event) => {
      if (onMessage) {
        onMessage(JSON.parse(event.data));
      }
    };

    socket.current.onclose = () => {
      console.log('WebSocket disconnected');
    };

    return () => {
      if (socket.current) {
        socket.current.close();
      }
    };
  }, [url, onMessage]);

  return socket.current;
};