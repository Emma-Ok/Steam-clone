import React from 'react';
import { cn } from '@/lib/utils';

interface CardProps extends React.HTMLAttributes<HTMLDivElement> {
  hoverable?: boolean;
}

export const Card = React.forwardRef<HTMLDivElement, CardProps>(
  ({ className, hoverable = false, ...props }, ref) => {
    return (
      <div
        className={cn(
          'bg-card border border-border rounded-lg p-4',
          hoverable && 'transition-all hover:border-accent/50 hover:shadow-lg cursor-pointer',
          className
        )}
        ref={ref}
        {...props}
      />
    );
  }
);
Card.displayName = 'Card';
