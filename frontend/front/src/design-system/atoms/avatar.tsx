import React from 'react';

interface AvatarProps {
  src: string;
  alt: string;
  className?: string;
}

export const Avatar: React.FC<AvatarProps> = ({ src, alt, className = 'w-8 h-8' }) => {
  return (
    <img
      src={src || "/placeholder.svg"}
      alt={alt}
      className={`rounded-full object-cover ${className}`}
    />
  );
};
