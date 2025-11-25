'use client';

import React from 'react';
import Image from 'next/image';
import { Game } from '@/src/types/index';
import { Badge } from '@/src/design-system/atoms/badge';
import { Card } from '@/src/design-system/atoms/card';

interface GameCardProps {
  game: Game;
  onClick?: () => void;
}

export const GameCard: React.FC<GameCardProps> = ({ game, onClick }) => {
  const priceFormatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: game.currency || 'USD',
    minimumFractionDigits: 2,
  });
  const displayRating = typeof game.rating === 'number' ? `${game.rating.toFixed(1)}★` : '—';

  return (
    <button
      type="button"
      onClick={onClick}
      className="cursor-pointer text-left w-full focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary rounded-lg"
    >
      <Card hoverable className="overflow-hidden flex flex-col h-full">
        <div className="relative w-full aspect-video mb-3 bg-muted rounded overflow-hidden">
          <Image
            src={game.coverImage || '/placeholder.svg?height=300&width=400&query=game%20cover'}
            alt={game.title}
            fill
            className="object-cover"
          />
        </div>
        
        <h3 className="font-semibold text-lg truncate mb-2">{game.title}</h3>
        
        <p className="text-muted-foreground text-sm line-clamp-2 mb-3">
          {game.description}
        </p>
        
        <div className="flex flex-wrap gap-2 mb-3">
          {game.genres.slice(0, 2).map((genre) => (
            <Badge key={genre} variant="secondary" className="text-xs">
              {genre}
            </Badge>
          ))}
        </div>
        
        <div className="flex items-center justify-between mt-auto">
          <span className="text-primary font-bold text-lg">{priceFormatter.format(game.price)}</span>
          <span className="text-accent font-semibold">{displayRating}</span>
        </div>
      </Card>
    </button>
  );
};
