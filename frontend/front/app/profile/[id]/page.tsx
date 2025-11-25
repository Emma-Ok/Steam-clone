'use client';

import React, { useEffect, useState } from 'react';
import { useParams } from 'next/navigation';
import { Genre, LibraryItem, User } from '@/types';
import { MainLayout } from '@/src/design-system/templates/main-layout';
import { ProtectedRoute } from '@/src/design-system/organisms/protected-route';
import { ProfileCard } from '@/src/design-system/molecules/profile-card';
import { useSession } from '@/src/shared/hooks/use-session';
import { API_ENDPOINTS } from '@/src/shared/constants/api';
import { get, put } from '@/src/shared/utils/api-client';
import { Badge } from '@/src/design-system/atoms/badge';
import { Card } from '@/src/design-system/atoms/card';
import { Button } from '@/components/ui/button';
import { useLibrary } from '@/src/shared/hooks/use-library';
import { useAuthContext } from '@/src/shared/providers/auth-context';

export default function ProfilePage() {
  const params = useParams();
  const userId = params.id as string;
  const { user: sessionUser } = useSession();
  const { setUser: setSessionUser } = useAuthContext();
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const [genres, setGenres] = useState<Genre[]>([]);
  const [favoriteSelection, setFavoriteSelection] = useState<string[]>([]);
  const [savingFavorites, setSavingFavorites] = useState(false);
  const [favoritesMessage, setFavoritesMessage] = useState<string | null>(null);
  const [library, setLibrary] = useState<LibraryItem[]>([]);
  const { fetchLibrary, loading: libraryLoading } = useLibrary();

  const isOwnProfile = sessionUser?.id === userId;

  useEffect(() => {
    const fetchUser = async () => {
      const response = await get<User>(API_ENDPOINTS.USER_PROFILE(userId));
      if (response.data) {
        setUser(response.data);
        setFavoriteSelection(response.data.favoriteGenres ?? []);
      }
      setLoading(false);
    };
    fetchUser();
  }, [userId]);

  useEffect(() => {
    (async () => {
      const response = await get<Genre[]>(API_ENDPOINTS.GENRES);
      if (response.data) {
        setGenres(response.data);
      }
    })();
  }, []);

  useEffect(() => {
    if (!isOwnProfile) {
      setLibrary([]);
      return;
    }
    fetchLibrary().then((items) => {
      if (items) {
        setLibrary(items);
      }
    });
  }, [fetchLibrary, isOwnProfile]);

  const toggleFavoriteGenre = (code: string) => {
    setFavoriteSelection((prev) =>
      prev.includes(code) ? prev.filter((item) => item !== code) : [...prev, code]
    );
  };

  const saveFavorites = async () => {
    if (!user) return;
    setSavingFavorites(true);
    setFavoritesMessage(null);
    const response = await put<User>(API_ENDPOINTS.USER_UPDATE(user.id), {
      username: user.username,
      favoriteGenres: favoriteSelection,
    });
    if (response.data) {
      setUser(response.data);
      setSessionUser(response.data);
      setFavoritesMessage('Preferencias guardadas correctamente.');
    } else if (response.error) {
      setFavoritesMessage(response.error.message);
    }
    setSavingFavorites(false);
  };

  let profileContent: React.ReactNode;
  if (loading) {
    profileContent = (
      <div className="text-center py-12">
        <div className="animate-spin text-4xl mb-4">⚙️</div>
        <p className="text-muted-foreground">Loading profile...</p>
      </div>
    );
  } else if (user) {
    profileContent = (
      <>
        <ProfileCard user={user} />

              <section className="space-y-4">
                <div className="flex items-center justify-between">
                  <h2 className="text-2xl font-semibold">Favorite Genres</h2>
                  {isOwnProfile && (
                    <Button
                      type="button"
                      size="sm"
                      onClick={saveFavorites}
                      isLoading={savingFavorites}
                    >
                      Save changes
                    </Button>
                  )}
                </div>
                {favoritesMessage && (
                  <p className="text-sm text-muted-foreground">{favoritesMessage}</p>
                )}
                <div className="flex flex-wrap gap-2">
                  {(genres.length > 0 ? genres : (user.favoriteGenres || []).map((code) => ({
                    id: code,
                    code,
                    name: code,
                  }))
                  ).map((genre) => (
                    <Badge
                      key={genre.code}
                      variant={favoriteSelection.includes(genre.code) ? 'primary' : 'secondary'}
                      className={isOwnProfile ? 'cursor-pointer' : ''}
                      onClick={isOwnProfile ? () => toggleFavoriteGenre(genre.code) : undefined}
                    >
                      {genre.name}
                    </Badge>
                  ))}
                </div>
                {!isOwnProfile && (user.favoriteGenres?.length ?? 0) === 0 && (
                  <p className="text-muted-foreground text-sm">This user has not selected favorite genres yet.</p>
                )}
              </section>

        {isOwnProfile && (
          <section className="space-y-4">
            <div className="flex items-center justify-between">
              <h2 className="text-2xl font-semibold">Your Library</h2>
              {libraryLoading && <span className="text-sm text-muted-foreground">Loading...</span>}
            </div>
            {library.length === 0 && !libraryLoading ? (
              <p className="text-muted-foreground">Add games from the catalog to see them here.</p>
            ) : (
              <div className="space-y-3">
                {library.map((item) => (
                  <Card key={item.id} className="p-4 flex flex-col gap-2">
                    <div className="flex items-center justify-between">
                      <p className="font-semibold">{item.game?.title ?? 'Unknown game'}</p>
                      <div className="flex gap-2">
                        {item.statuses?.map((status) => (
                          <Badge key={status} variant="secondary">
                            {status}
                          </Badge>
                        ))}
                      </div>
                    </div>
                    <p className="text-sm text-muted-foreground">
                      Added on {new Date(item.addedAt).toLocaleDateString('es-ES')}
                    </p>
                  </Card>
                ))}
              </div>
            )}
          </section>
        )}
      </>
    );
  } else {
    profileContent = (
      <div className="text-center py-12">
        <p className="text-muted-foreground">User not found</p>
      </div>
    );
  }

  return (
    <ProtectedRoute>
      <MainLayout>
        <div className="max-w-4xl mx-auto space-y-8">
          <h1 className="text-3xl font-bold mb-2">
            {isOwnProfile ? 'Your Profile' : 'User Profile'}
          </h1>
          {profileContent}
        </div>
      </MainLayout>
    </ProtectedRoute>
  );
}
