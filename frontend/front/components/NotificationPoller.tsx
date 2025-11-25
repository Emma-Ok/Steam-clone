"use client";

import React, { useEffect, useState } from 'react';

const NotificationPoller: React.FC = () => {
  const [notifications, setNotifications] = useState<string[]>([]);

  useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const response = await fetch('/api/notifications');
        if (response.ok) {
          const data = await response.json();
          setNotifications(data);
        } else {
          console.error('Failed to fetch notifications');
        }
      } catch (error) {
        console.error('Error fetching notifications:', error);
      }
    };

    const interval = setInterval(fetchNotifications, 5000); // Poll every 5 seconds
    return () => clearInterval(interval);
  }, []);

  return (
    <div>
      <h2>Notifications</h2>
      <ul>
        {notifications.map((notification) => (
          <li key={notification}>{notification}</li>
        ))}
      </ul>
    </div>
  );
};

export default NotificationPoller;