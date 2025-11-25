import React from 'react';

export type NotificationType = 'success' | 'error' | 'warning' | 'info';

interface NotificationProps {
  message: string;
  type?: NotificationType;
  onClose?: () => void;
}

const typeStyles: Record<NotificationType, string> = {
  success: 'bg-green-100 text-green-800 border-green-400',
  error: 'bg-red-100 text-red-800 border-red-400',
  warning: 'bg-yellow-100 text-yellow-800 border-yellow-400',
  info: 'bg-blue-100 text-blue-800 border-blue-400',
};

export const Notification: React.FC<NotificationProps> = ({ message, type = 'info', onClose }) => (
  <div className={`border px-4 py-2 rounded shadow-sm mb-2 flex items-center justify-between ${typeStyles[type]}`}>
    <span>{message}</span>
    {onClose && (
      <button className="ml-4 text-sm text-gray-500 hover:text-gray-700" onClick={onClose}>
        ×
      </button>
    )}
  </div>
);
