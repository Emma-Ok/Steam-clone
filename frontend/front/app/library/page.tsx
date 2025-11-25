'use client';

import React, { useEffect, useState } from 'react';
import { LibraryItem, LibraryStatus } from '@/types';
import { MainLayout } from '@/src/design-system/templates/main-layout';
import { ProtectedRoute } from '@/src/design-system/organisms/protected-route';
import { useLibrary } from '@/src/shared/hooks/use-library';
import { Card } from '@/src/design-system/atoms/card';
import { Badge } from '@/src/design-system/atoms/badge';
import { Button } from '@/components/ui/button';

export default function LibraryPage() {
  const { fetchLibrary, upsertLibraryEntry, removeGameFromLibrary, loading, error } = useLibrary();
  const [library, setLibrary] = useState<LibraryItem[]>([]);
  const [actionMessage, setActionMessage] = useState<string | null>(null);

  useEffect(() => {
    fetchLibrary().then((items) => {
      if (items) {
        setLibrary(items);
      }
    });
  }, [fetchLibrary]);

  const refreshLibrary = () => {
    fetchLibrary().then((items) => {
      if (items) {
        setLibrary(items);
      }
    });
  };

  const handleRemove = async (gameId: string) => {
    const result = await removeGameFromLibrary(gameId);
    if (result.success) {
      setActionMessage('Juego eliminado de tu biblioteca.');
      refreshLibrary();
    } else {
      setActionMessage(result.error || 'No pudimos eliminar el juego.');
    }
  };

  const toggleFavorite = async (item: LibraryItem) => {
    const statuses = new Set<LibraryStatus>(item.statuses ?? ['OWNED']);
    if (statuses.has('FAVORITE')) {
      statuses.delete('FAVORITE');
    } else {
      statuses.add('FAVORITE');
      statuses.add('OWNED');
    }
    const result = await upsertLibraryEntry(item.gameId, Array.from(statuses));
    if (result.success) {
      setActionMessage('Actualizamos el estado del juego.');
      if (result.entry) {
        setLibrary((prev) => prev.map((entry) => (entry.id === result.entry?.id ? result.entry : entry)));
      } else {
        refreshLibrary();
      }
    } else {
      setActionMessage(result.error || 'No pudimos actualizar el juego.');
    }
  };

  return (
    <ProtectedRoute>
      <MainLayout>
        <div className="max-w-5xl mx-auto space-y-6">
          <div className="flex items-center justify-between">
            <h1 className="text-3xl font-bold">Your Library</h1>
            {loading && <span className="text-sm text-muted-foreground">Loading...</span>}
          </div>
          {error && <p className="text-sm text-destructive">{error}</p>}
          {actionMessage && <p className="text-sm text-muted-foreground">{actionMessage}</p>}

          {library.length === 0 && !loading ? (
            <div className="text-center py-12">
              <p className="text-muted-foreground">You have no games in your library yet.</p>
            </div>
          ) : (
            <div className="space-y-4">
              {library.map((item) => (
                <Card key={item.id} className="p-4 flex flex-col gap-3">
                  <div className="flex items-center justify-between gap-4">
                    <div>
                      <p className="font-semibold">{item.game?.title ?? 'Unknown game'}</p>
                      <p className="text-sm text-muted-foreground">
                        Added on {new Date(item.addedAt).toLocaleDateString('es-ES')}
                      </p>
                    </div>
                    <div className="flex gap-2">
                      {item.statuses?.map((status) => (
                        <Badge key={status} variant={status === 'FAVORITE' ? 'accent' : 'secondary'}>
                          {status}
                        </Badge>
                      ))}
                    </div>
                  </div>
                  <div className="flex flex-wrap gap-3">
                    <Button
                      type="button"
                      size="sm"
                      variant={item.statuses?.includes('FAVORITE') ? 'secondary' : 'outline'}
                      onClick={() => toggleFavorite(item)}
                    >
                      {item.statuses?.includes('FAVORITE') ? 'Remove favorite' : 'Mark favorite'}
                    </Button>
                    <Button type="button" size="sm" variant="destructive" onClick={() => handleRemove(item.gameId)}>
                      Remove
                    </Button>
                  </div>
                </Card>
              ))}
            </div>
          )}
        </div>
      </MainLayout>
    </ProtectedRoute>
  );
}
