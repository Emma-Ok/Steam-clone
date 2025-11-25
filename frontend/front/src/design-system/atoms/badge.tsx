import React from 'react';
import { cn } from '@/lib/utils';

interface BadgeProps extends React.HTMLAttributes<HTMLDivElement> {
  variant?: 'primary' | 'secondary' | 'accent' | 'destructive';
}

export const Badge = React.forwardRef<HTMLDivElement, BadgeProps>(
  ({ className, variant = 'primary', ...props }, ref) => {
    const variants = {
      primary: 'bg-primary/20 text-primary border border-primary/30',
      secondary: 'bg-muted text-muted-foreground border border-border',
      accent: 'bg-accent/20 text-accent border border-accent/30',
      destructive: 'bg-destructive/20 text-destructive border border-destructive/30',
    };

    return (
      <div
        className={cn(
          'inline-flex items-center px-3 py-1 rounded-full text-sm font-medium',
          variants[variant],
          className
        )}
        ref={ref}
        {...props}
      />
    );
  }
);
Badge.displayName = 'Badge';
