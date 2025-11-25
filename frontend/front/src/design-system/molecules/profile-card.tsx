'use client';

import React from 'react';
import { User } from '@/types';
import { Card } from '@/src/design-system/atoms/card';
import { Badge } from '@/src/design-system/atoms/badge';

interface ProfileCardProps {
  user: User;
}

export const ProfileCard: React.FC<ProfileCardProps> = ({ user }) => {
  return (
    <Card className="space-y-6">
      <div className="space-y-4">
        <div className="w-24 h-24 bg-primary rounded-lg flex items-center justify-center mx-auto">
          <span className="text-primary-foreground font-bold text-4xl">
            {user.username.charAt(0).toUpperCase()}
          </span>
        </div>

        <div className="text-center">
          <h2 className="text-2xl font-bold">{user.username}</h2>
          <p className="text-muted-foreground">{user.email}</p>
        </div>

        <div className="flex flex-wrap gap-2 justify-center">
          <Badge variant="primary">Member since {new Date(user.createdAt).getFullYear()}</Badge>
        </div>
      </div>

      <div className="pt-6 border-t border-border space-y-3">
        <div className="flex justify-between">
          <span className="text-muted-foreground">Account Status</span>
          <Badge variant="accent">Active</Badge>
        </div>
      </div>
    </Card>
  );
};
