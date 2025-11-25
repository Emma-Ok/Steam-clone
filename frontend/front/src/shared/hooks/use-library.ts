'use client';

import { useCallback, useState } from 'react';
import { LibraryItem, LibraryStatus } from '@/types';
import { useSession } from '@/src/shared/hooks/use-session';
import { API_ENDPOINTS } from '@/src/shared/constants/api';
import { get, post, del } from '@/src/shared/utils/api-client';
import { GameResponseDto, mapGameDtoToGame } from '@/src/shared/utils/transformers';

interface LibraryEntryDto {
  id: string;
  userId: string;
  gameId: string;
  statuses: LibraryStatus[];
  progressMinutes: number;
  addedAt: string;
}

export function useLibrary() {
  const { user } = useSession();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const hydrateEntry = useCallback(async (entry: LibraryEntryDto): Promise<LibraryItem> => {
    const gameResponse = await get<GameResponseDto>(API_ENDPOINTS.GAME_DETAIL(entry.gameId));
    const game = gameResponse.data ? mapGameDtoToGame(gameResponse.data) : undefined;
    return { ...entry, game };
  }, []);

  const fetchLibrary = useCallback(async () => {
    if (!user) {
      setError('User not authenticated');
      return null;
    }

    setLoading(true);
    setError(null);
    try {
      const response = await get<LibraryEntryDto[]>(API_ENDPOINTS.USER_LIBRARY(user.id));

      if (response.error) {
        setError(response.error.message);
        return null;
      }
      const entries = response.data ?? [];
      return Promise.all(entries.map(hydrateEntry));
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to fetch library');
      return null;
    } finally {
      setLoading(false);
    }
  }, [user, hydrateEntry]);

  const upsertLibraryEntry = useCallback(
    async (gameId: string, statuses: LibraryStatus[] = ['OWNED'], progressMinutes?: number) => {
      if (!user) {
        return { success: false, error: 'User not authenticated' };
      }

      try {
        const response = await post<LibraryEntryDto>(API_ENDPOINTS.LIBRARY_ADD, {
          gameId,
          statuses,
          progressMinutes,
        });
        if (response.error || !response.data) {
          return {
            success: false,
            error: response.error?.message || 'Failed to save library entry',
          } as const;
        }
        const entry = await hydrateEntry(response.data);
        return { success: true, entry } as const;
      } catch (err) {
        return {
          success: false,
          error: err instanceof Error ? err.message : 'Failed to save library entry',
        } as const;
      }
    },
    [user, hydrateEntry]
  );

  const removeGameFromLibrary = useCallback(
    async (gameId: string) => {
      try {
        const response = await del(API_ENDPOINTS.LIBRARY_REMOVE(gameId));
        return response.error
          ? { success: false, error: response.error.message }
          : { success: true };
      } catch (err) {
        return {
          success: false,
          error: err instanceof Error ? err.message : 'Failed to remove game from library',
        };
      }
    },
    []
  );

  return { fetchLibrary, upsertLibraryEntry, removeGameFromLibrary, loading, error };
}
