import React from 'react';
import { cn } from '@/lib/utils';

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
}

export const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, label, error, type = 'text', ...props }, ref) => {
    return (
      <div className="w-full">
        {label && (
          <label className="block text-sm font-medium mb-2 text-foreground">
            {label}
          </label>
        )}
        <input
          type={type}
          className={cn(
            'w-full px-4 py-2 rounded-md bg-input border border-border text-foreground placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring',
            error && 'border-destructive',
            className
          )}
          ref={ref}
          {...props}
        />
        {error && <p className="text-destructive text-sm mt-1">{error}</p>}
      </div>
    );
  }
);
Input.displayName = 'Input';
