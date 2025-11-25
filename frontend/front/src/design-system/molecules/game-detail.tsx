'use client';

import React, { useEffect, useMemo, useState } from 'react';
import Image from 'next/image';
import { Game, LibraryItem, LibraryStatus, Review } from '@/types';
import { Card } from '@/src/design-system/atoms/card';
import { Badge } from '@/src/design-system/atoms/badge';
import { ReviewsList } from '@/src/design-system/molecules/reviews-list';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { X } from 'lucide-react';
import { useLibrary } from '@/src/shared/hooks/use-library';
import { useReviews } from '@/src/shared/hooks/use-reviews';
import { useSession } from '@/src/shared/hooks/use-session';

interface GameDetailProps {
  game: Game;
  onClose: () => void;
}

export const GameDetail: React.FC<GameDetailProps> = ({ game, onClose }) => {
  const [activeTab, setActiveTab] = useState<'details' | 'reviews'>('details');
  const [reviews, setReviews] = useState<Review[]>([]);
  const [libraryEntry, setLibraryEntry] = useState<LibraryItem | null>(null);
  const [libraryMessage, setLibraryMessage] = useState<string | null>(null);
  const [libraryBusy, setLibraryBusy] = useState(false);
  const [newReview, setNewReview] = useState<{ rating: number; comment: string }>({ rating: 5, comment: '' });
  const [reviewMessage, setReviewMessage] = useState<string | null>(null);
  const [submittingReview, setSubmittingReview] = useState(false);

  const { user, isAuthenticated } = useSession();
  const { fetchLibrary, upsertLibraryEntry } = useLibrary();
  const { fetchGameReviews, createReview, loading: reviewsLoading, error: reviewsError } = useReviews();

  const releaseDate = useMemo(
    () =>
      new Date(game.releaseDate).toLocaleDateString('es-ES', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
      }),
    [game.releaseDate]
  );

  const priceFormatter = useMemo(
    () =>
      new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: game.currency || 'USD',
        minimumFractionDigits: 2,
      }),
    [game.currency, game.price]
  );

  const isFavorite = libraryEntry?.statuses?.includes('FAVORITE');
  const inLibrary = Boolean(libraryEntry);

  useEffect(() => {
    let mounted = true;
    fetchGameReviews(game.id).then((data) => {
      if (mounted && data) {
        setReviews(data);
      }
    });

    if (isAuthenticated) {
      fetchLibrary().then((entries) => {
        if (!mounted || !entries) return;
        const entry = entries.find((item) => item.gameId === game.id) || null;
        setLibraryEntry(entry);
      });
    } else {
      setLibraryEntry(null);
    }

    return () => {
      mounted = false;
    };
  }, [game.id, fetchGameReviews, fetchLibrary, isAuthenticated]);

  const handleLibraryAction = async (statuses: LibraryStatus[] = ['OWNED']) => {
    if (!isAuthenticated) {
      setLibraryMessage('Inicia sesión para gestionar tu biblioteca.');
      return;
    }
    setLibraryBusy(true);
    setLibraryMessage(null);
    const result = await upsertLibraryEntry(game.id, statuses);
    if (result.success && result.entry) {
      setLibraryEntry(result.entry);
      setLibraryMessage('Biblioteca actualizada correctamente.');
    } else {
      setLibraryMessage(result.error || 'No pudimos actualizar tu biblioteca.');
    }
    setLibraryBusy(false);
  };

  const toggleFavorite = async () => {
    const currentStatuses = new Set<LibraryStatus>(libraryEntry?.statuses ?? ['OWNED']);
    if (currentStatuses.has('FAVORITE')) {
      currentStatuses.delete('FAVORITE');
    } else {
      currentStatuses.add('FAVORITE');
      currentStatuses.add('OWNED');
    }
    await handleLibraryAction(Array.from(currentStatuses));
  };

  const handleReviewSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    if (!isAuthenticated || !user) {
      setReviewMessage('Inicia sesión para escribir una reseña.');
      return;
    }
    if (!newReview.comment.trim()) {
      setReviewMessage('Escribe un comentario antes de enviar.');
      return;
    }
    setSubmittingReview(true);
    setReviewMessage(null);
    const response = await createReview({
      gameId: game.id,
      rating: newReview.rating,
      comment: newReview.comment,
    });
    if (response.success && response.review) {
      setReviews((prev) => [response.review!, ...prev]);
      setNewReview({ rating: 5, comment: '' });
      setReviewMessage('¡Gracias por tu reseña!');
    } else {
      setReviewMessage(response.error || 'No pudimos guardar tu reseña.');
    }
    setSubmittingReview(false);
  };

  const priceLabel = priceFormatter.format(game.price);
  const ratingLabel = typeof game.rating === 'number' ? game.rating.toFixed(1) : '—';

  return (
    <div className="fixed inset-0 bg-black/70 flex items-center justify-center z-50 p-4 backdrop-blur-sm">
      <button
        type="button"
        className="absolute inset-0"
        onClick={onClose}
        aria-label="Cerrar modal"
      />

      <Card className="relative w-full max-w-3xl max-h-[90vh] overflow-hidden bg-background shadow-2xl flex flex-col border-2 border-primary/30">
        <button
          type="button"
          onClick={onClose}
          className="absolute top-4 right-4 z-20 bg-primary hover:bg-primary/80 p-2 rounded-lg transition-colors text-background"
          aria-label="Cerrar detalle del juego"
        >
          <X className="w-6 h-6" />
        </button>

        <div className="overflow-y-auto flex-1">
          <div className="space-y-6 p-6">
            <div className="relative w-full aspect-video rounded-lg overflow-hidden border border-border">
              <Image
                src={game.coverImage || '/placeholder.svg?height=400&width=700&query=game%20cover'}
                alt={game.title}
                fill
                className="object-cover"
                priority
              />
            </div>

            <div>
              <h1 className="text-4xl font-bold mb-4 text-foreground">{game.title}</h1>
              <div className="flex items-center gap-6 flex-wrap">
                <div className="flex items-center gap-2">
                  <span className="text-3xl font-bold text-primary">{priceLabel}</span>
                </div>
                <div className="flex items-center gap-2 bg-accent/20 px-4 py-2 rounded-lg">
                  <span className="text-2xl font-semibold text-accent">{ratingLabel}</span>
                  <span className="text-2xl text-amber-400">★</span>
                </div>
              </div>
            </div>

            <div className="flex gap-2 border-b border-border">
              <button
                onClick={() => setActiveTab('details')}
                className={`px-4 py-2 font-semibold transition-colors border-b-2 ${
                  activeTab === 'details'
                    ? 'border-primary text-foreground'
                    : 'border-transparent text-muted-foreground hover:text-foreground'
                }`}
              >
                Detalles
              </button>
              <button
                onClick={() => setActiveTab('reviews')}
                className={`px-4 py-2 font-semibold transition-colors border-b-2 ${
                  activeTab === 'reviews'
                    ? 'border-primary text-foreground'
                    : 'border-transparent text-muted-foreground hover:text-foreground'
                }`}
              >
                Reseñas
              </button>
            </div>

            {activeTab === 'details' ? (
              <div className="space-y-6">
                <div>
                  <h2 className="text-lg font-semibold mb-3 text-foreground">Descripción</h2>
                  <p className="text-base text-muted-foreground leading-relaxed">{game.description}</p>
                </div>

                <div className="grid grid-cols-2 gap-6">
                  <div>
                    <h3 className="text-sm font-semibold text-muted-foreground mb-2">Desarrollador</h3>
                    <p className="text-base font-medium">{game.developer || 'Unknown studio'}</p>
                  </div>

                  <div>
                    <h3 className="text-sm font-semibold text-muted-foreground mb-2">Fecha de Lanzamiento</h3>
                    <p className="text-base font-medium">{releaseDate}</p>
                  </div>

                  <div>
                    <h3 className="text-sm font-semibold text-muted-foreground mb-2">Plataformas</h3>
                    <div className="flex flex-wrap gap-2">
                      {game.platforms.map((platform) => (
                        <Badge key={platform} variant="secondary">
                          {platform}
                        </Badge>
                      ))}
                    </div>
                  </div>

                  <div>
                    <h3 className="text-sm font-semibold text-muted-foreground mb-2">Géneros</h3>
                    <div className="flex flex-wrap gap-2">
                      {game.genres.map((genre) => (
                        <Badge key={genre} variant="default">
                          {genre}
                        </Badge>
                      ))}
                    </div>
                  </div>
                </div>
              </div>
            ) : (
              <div className="space-y-6">
                <div>
                  <h2 className="text-lg font-semibold mb-4 text-foreground">Reseñas de la Comunidad</h2>
                  <ReviewsList reviews={reviews} isLoading={reviewsLoading} />
                  {reviewsError && <p className="text-sm text-destructive mt-2">{reviewsError}</p>}
                </div>
                <div className="space-y-3">
                  <h3 className="text-md font-semibold">Escribe tu reseña</h3>
                  {isAuthenticated ? (
                    <form onSubmit={handleReviewSubmit} className="space-y-3">
                      <label className="text-sm font-medium" htmlFor="rating-select">
                        Calificación
                      </label>
                      <select
                        id="rating-select"
                        className="w-full bg-card border border-border rounded-md px-3 py-2"
                        value={newReview.rating}
                        onChange={(e) => setNewReview((prev) => ({ ...prev, rating: Number(e.target.value) }))}
                      >
                        {[5, 4, 3, 2, 1].map((value) => (
                          <option key={value} value={value}>
                            {value} ★
                          </option>
                        ))}
                      </select>
                      <Textarea
                        placeholder="Comparte tu experiencia con este juego"
                        value={newReview.comment}
                        onChange={(e) => setNewReview((prev) => ({ ...prev, comment: e.target.value }))}
                        rows={4}
                      />
                      <Button type="submit" isLoading={submittingReview} disabled={submittingReview}>
                        Publicar reseña
                      </Button>
                      {reviewMessage && <p className="text-sm text-muted-foreground">{reviewMessage}</p>}
                    </form>
                  ) : (
                    <p className="text-sm text-muted-foreground">Inicia sesión para dejar una reseña.</p>
                  )}
                </div>
              </div>
            )}
          </div>
        </div>

        <div className="flex flex-col gap-3 p-6 border-t border-border bg-muted/50">
          {libraryMessage && <p className="text-sm text-muted-foreground">{libraryMessage}</p>}
          <div className="flex gap-3">
            <Button
              type="button"
              className="flex-1"
              size="lg"
              onClick={() => handleLibraryAction(['OWNED'])}
              disabled={libraryBusy}
            >
              {inLibrary ? 'Actualizar biblioteca' : 'Añadir a la biblioteca'}
            </Button>
            <Button
              type="button"
              variant={isFavorite ? 'secondary' : 'outline'}
              size="lg"
              onClick={toggleFavorite}
              disabled={libraryBusy}
            >
              {isFavorite ? 'Quitar de favoritos' : 'Añadir a favoritos'}
            </Button>
            <Button type="button" variant="ghost" size="lg" onClick={onClose}>
              Cerrar
            </Button>
          </div>
        </div>
      </Card>
    </div>
  );
};
