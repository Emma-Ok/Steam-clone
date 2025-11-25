'use client';

import { useCallback, useState } from 'react';
import { Review, CreateReviewInput } from '@/types';
import { API_ENDPOINTS } from '@/src/shared/constants/api';
import { get, post, put } from '@/src/shared/utils/api-client';

export function useReviews() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchGameReviews = useCallback(async (gameId: string): Promise<Review[] | null> => {
    setLoading(true);
    setError(null);
    try {
      const response = await get<Review[]>(API_ENDPOINTS.GAME_REVIEWS(gameId));

      if (response.error) {
        setError(response.error.message);
        return null;
      }
      return response.data || [];
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to fetch reviews');
      return null;
    } finally {
      setLoading(false);
    }
  }, []);

  const createReview = useCallback(async (reviewData: CreateReviewInput) => {
    setLoading(true);
    setError(null);
    try {
      const response = await post<Review>(API_ENDPOINTS.REVIEWS, reviewData);

      if (response.error) {
        setError(response.error.message);
        return { success: false, error: response.error.message };
      }
      return { success: true, review: response.data } as const;
    } catch (err) {
      const errorMsg = err instanceof Error ? err.message : 'Failed to create review';
      setError(errorMsg);
      return { success: false, error: errorMsg };
    } finally {
      setLoading(false);
    }
  }, []);

  const updateReview = useCallback(
    async (
      reviewId: string,
      reviewData: Partial<Omit<CreateReviewInput, 'gameId'>>
    ) => {
      setLoading(true);
      setError(null);
      try {
        const response = await put<Review>(API_ENDPOINTS.REVIEW_DETAIL(reviewId), reviewData);

        if (response.error) {
          setError(response.error.message);
          return { success: false, error: response.error.message };
        }
        return { success: true, review: response.data } as const;
      } catch (err) {
        const errorMsg = err instanceof Error ? err.message : 'Failed to update review';
        setError(errorMsg);
        return { success: false, error: errorMsg };
      } finally {
        setLoading(false);
      }
    },
    []
  );

  return { fetchGameReviews, createReview, updateReview, loading, error };
}
