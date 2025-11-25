'use client';

import { useState, useCallback } from 'react';
import { ApiResponse } from '@/types';

interface UseApiState<T> {
  data: T | null;
  loading: boolean;
  error: string | null;
}

export function useApi<T>(
  fetchFunction: () => Promise<ApiResponse<T>>,
  immediate = true
) {
  const [state, setState] = useState<UseApiState<T>>({
    data: null,
    loading: immediate,
    error: null,
  });

  const execute = useCallback(async () => {
    setState({ data: null, loading: true, error: null });
    try {
      const response = await fetchFunction();
      
      if (response.error) {
        setState({
          data: null,
          loading: false,
          error: response.error.message,
        });
      } else {
        setState({
          data: response.data || null,
          loading: false,
          error: null,
        });
      }
    } catch (err) {
      setState({
        data: null,
        loading: false,
        error: err instanceof Error ? err.message : 'Unknown error',
      });
    }
  }, [fetchFunction]);

  if (immediate && state.loading) {
    execute();
  }

  return { ...state, execute };
}
