import { useState, useCallback } from 'react';
import { NotificationType } from '../components/Notification';

export function useNotification() {
  const [message, setMessage] = useState<string | null>(null);
  const [type, setType] = useState<NotificationType>('info');

  const showNotification = useCallback((msg: string, notifType: NotificationType = 'info') => {
    setMessage(msg);
    setType(notifType);
  }, []);

  const hideNotification = useCallback(() => {
    setMessage(null);
  }, []);

  return {
    message,
    type,
    showNotification,
    hideNotification,
    isVisible: !!message,
  };
}
