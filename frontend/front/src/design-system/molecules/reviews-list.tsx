'use client';

import React from 'react';
import { Review } from '@/types';
import { Card } from '@/src/design-system/atoms/card';
import { Badge } from '@/src/design-system/atoms/badge';

interface ReviewsListProps {
  reviews?: Review[];
  isLoading?: boolean;
}

const ratingVariant = (rating: number) => {
  if (rating >= 4) return 'accent';
  if (rating >= 2.5) return 'secondary';
  return 'destructive';
};

export const ReviewsList: React.FC<ReviewsListProps> = ({ reviews, isLoading }) => {
  if (isLoading) {
    return (
      <div className="text-center py-8">
        <p className="text-muted-foreground">Cargando reseñas...</p>
      </div>
    );
  }

  if (!reviews || reviews.length === 0) {
    return (
      <div className="text-center py-8">
        <p className="text-muted-foreground">Sé el primero en reseñar este juego.</p>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      {reviews.map((review) => (
        <Card key={review.id} className="p-4 space-y-3">
          <div className="flex items-start justify-between">
            <div>
              <p className="text-xs uppercase tracking-wide text-muted-foreground">
                Review #{review.id.slice(0, 8)}
              </p>
              <p className="text-sm text-muted-foreground">
                Publicado el {new Date(review.createdAt).toLocaleDateString('es-ES')}
              </p>
            </div>
            <Badge variant={ratingVariant(review.rating)} className="text-sm">
              {review.rating}/5
            </Badge>
          </div>

          <p className="text-foreground whitespace-pre-line">{review.comment}</p>

          {review.updatedAt !== review.createdAt && (
            <p className="text-xs text-muted-foreground">
              Editado el {new Date(review.updatedAt).toLocaleDateString('es-ES')}
            </p>
          )}
        </Card>
      ))}
    </div>
  );
};
