'use client';

import React, { useEffect, useMemo, useState } from 'react';
import { Game, Genre } from '@/types';
import { GameCard } from '@/src/design-system/molecules/game-card';
import { GameDetail } from '@/src/design-system/molecules/game-detail';
import { Input } from '@/src/design-system/atoms/input';
import { Badge } from '@/src/design-system/atoms/badge';
import { GENRES, PLATFORMS } from '@/src/shared/constants/genres';
import { useGames } from '@/src/shared/hooks/use-games';
import { useSession } from '@/src/shared/hooks/use-session';
import { API_ENDPOINTS } from '@/src/shared/constants/api';
import { get } from '@/src/shared/utils/api-client';
import { GameResponseDto, mapGameDtoToGame } from '@/src/shared/utils/transformers';

interface RecommendationResponseDto {
  userId: string;
  games: GameResponseDto[];
}

const fallbackGenres = GENRES.map((genre) => ({ code: genre, name: genre }));
const fallbackPlatforms = PLATFORMS.map((platform) => ({ code: platform, name: platform }));

export const HomePage: React.FC = () => {
  const [games, setGames] = useState<Game[]>([]);
  const [selectedGenres, setSelectedGenres] = useState<string[]>([]);
  const [selectedPlatform, setSelectedPlatform] = useState<string>('');
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedGame, setSelectedGame] = useState<Game | null>(null);
  const [genres, setGenres] = useState<Array<{ code: string; name: string }>>(fallbackGenres);
  const [platforms, setPlatforms] = useState<Array<{ code: string; name: string }>>(fallbackPlatforms);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [recommendations, setRecommendations] = useState<Game[]>([]);
  const [recommendationsLoading, setRecommendationsLoading] = useState(false);

  const { fetchGames } = useGames();
  const { user, isAuthenticated } = useSession();

  useEffect(() => {
    let mounted = true;
    (async () => {
      setLoading(true);
      const data = await fetchGames();
      if (mounted) {
        if (data) {
          setGames(data);
        }
        setLoading(false);
      }
    })().catch((err) => {
      console.error('Games fetch failed', err);
      if (mounted) {
        setError('No pudimos cargar los juegos.');
        setLoading(false);
      }
    });
    return () => {
      mounted = false;
    };
  }, [fetchGames]);

  useEffect(() => {
    (async () => {
      const response = await get<Genre[]>(API_ENDPOINTS.GENRES);
      if (response.data && response.data.length > 0) {
        setGenres(response.data.map((genre) => ({ code: genre.code, name: genre.name })));
      }
    })();
    (async () => {
      const response = await get<Array<{ id: string; code: string; name: string }>>(API_ENDPOINTS.PLATFORMS);
      if (response.data && response.data.length > 0) {
        setPlatforms(response.data.map((platform) => ({ code: platform.code, name: platform.name })));
      }
    })();
  }, []);

  useEffect(() => {
    if (!isAuthenticated || !user) {
      setRecommendations([]);
      return;
    }
    setRecommendationsLoading(true);
    (async () => {
      const response = await get<RecommendationResponseDto>(API_ENDPOINTS.RECOMMENDATIONS_USER(user.id));
      if (response.data) {
        const mapped = response.data.games.map(mapGameDtoToGame);
        setRecommendations(mapped);
      } else {
        setRecommendations([]);
      }
      setRecommendationsLoading(false);
    })().catch((err) => {
      console.error('Recommendations fetch failed', err);
      setRecommendations([]);
      setRecommendationsLoading(false);
    });
  }, [user, isAuthenticated]);

  const filteredGames = useMemo(() => {
    return games.filter((game) => {
      const matchesSearch = game.title.toLowerCase().includes(searchQuery.toLowerCase());
      const matchesGenres =
        selectedGenres.length === 0 || selectedGenres.some((genre) => game.genres.includes(genre));
      const matchesPlatform =
        !selectedPlatform || game.platforms.includes(selectedPlatform);
      return matchesSearch && matchesGenres && matchesPlatform;
    });
  }, [games, searchQuery, selectedGenres, selectedPlatform]);

  const toggleGenre = (genre: string) => {
    setSelectedGenres((prev) =>
      prev.includes(genre) ? prev.filter((g) => g !== genre) : [...prev, genre]
    );
  };

  return (
    <div className="space-y-8">
      <section className="bg-linear-to-r from-primary/20 to-accent/20 rounded-lg p-12 border border-primary/30">
        <h1 className="text-5xl font-bold mb-4">Welcome to SteamClone</h1>
        <p className="text-xl text-muted-foreground">
          Discover, play, and share your favorite games with the community.
        </p>
      </section>

      <div className="space-y-4">
        <Input
          placeholder="Search games..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="space-y-2">
            <p className="text-sm font-medium">Genres</p>
            <div className="flex flex-wrap gap-2">
              {genres.map((genre) => (
                <Badge
                  key={genre.code}
                  variant={selectedGenres.includes(genre.code) ? 'primary' : 'secondary'}
                  className="cursor-pointer"
                  onClick={() => toggleGenre(genre.code)}
                >
                  {genre.name}
                </Badge>
              ))}
            </div>
          </div>

          <div className="space-y-2">
            <p className="text-sm font-medium">Platforms</p>
            <select
              className="w-full bg-card border border-border rounded-md px-3 py-2"
              value={selectedPlatform}
              onChange={(e) => setSelectedPlatform(e.target.value)}
            >
              <option value="">All platforms</option>
              {platforms.map((platform) => (
                <option key={platform.code} value={platform.code}>
                  {platform.name}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>

      {isAuthenticated && (
        <section className="space-y-4">
          <div className="flex items-center justify-between">
            <h2 className="text-2xl font-bold">Recommended for you</h2>
            {recommendationsLoading && <span className="text-sm text-muted-foreground">Loading...</span>}
          </div>
          {recommendations.length === 0 && !recommendationsLoading ? (
            <p className="text-muted-foreground text-sm">
              Choose some favorite genres in your profile to get tailored recommendations.
            </p>
          ) : (
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              {recommendations.map((game) => (
                <GameCard key={game.id} game={game} onClick={() => setSelectedGame(game)} />
              ))}
            </div>
          )}
        </section>
      )}

      <section>
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-2xl font-bold">Available Games</h2>
          {loading && <span className="text-sm text-muted-foreground">Loading...</span>}
        </div>
        {error && <p className="text-sm text-destructive mb-4">{error}</p>}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {filteredGames.map((game) => (
            <GameCard key={game.id} game={game} onClick={() => setSelectedGame(game)} />
          ))}
        </div>
        {!loading && filteredGames.length === 0 && (
          <div className="text-center py-12">
            <p className="text-muted-foreground">No games found. Try adjusting your filters.</p>
          </div>
        )}
      </section>

      {selectedGame && <GameDetail game={selectedGame} onClose={() => setSelectedGame(null)} />}
    </div>
  );
};
