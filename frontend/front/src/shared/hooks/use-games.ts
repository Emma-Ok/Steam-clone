'use client';

import { useCallback, useState } from 'react';
import { Game } from '@/types';
import { API_ENDPOINTS } from '@/src/shared/constants/api';
import { get } from '@/src/shared/utils/api-client';
import { GameResponseDto, mapGameDtoToGame } from '@/src/shared/utils/transformers';

export function useGames() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchGames = useCallback(
    async (filters?: { genre?: string; platform?: string; page?: number }): Promise<Game[] | null> => {
      setLoading(true);
      setError(null);
      try {
        const response = await get<GameResponseDto[]>(API_ENDPOINTS.GAMES, {
          params: filters as Record<string, string | number>,
        });

        if (response.error) {
          setError(response.error.message);
          return null;
        }
        return (response.data ?? []).map(mapGameDtoToGame);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Failed to fetch games');
        return null;
      } finally {
        setLoading(false);
      }
    },
    []
  );

  const fetchGameDetail = useCallback(async (gameId: string): Promise<Game | null> => {
    setLoading(true);
    setError(null);
    try {
      const response = await get<GameResponseDto>(API_ENDPOINTS.GAME_DETAIL(gameId));

      if (response.error) {
        setError(response.error.message);
        return null;
      }
      return response.data ? mapGameDtoToGame(response.data) : null;
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to fetch game');
      return null;
    } finally {
      setLoading(false);
    }
  }, []);

  return { fetchGames, fetchGameDetail, loading, error };
}
