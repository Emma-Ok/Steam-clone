// Authentication Types
export interface User {
  id: string;
  email: string;
  username: string;
  roles: string[];
  favoriteGenres?: string[];
  createdAt: string;
  avatar?: string;
}

export interface AuthResponse {
  userId: string;
  username: string;
  email: string;
  roles: string[];
  accessToken: string;
}

// Game Types
export interface Game {
  id: string;
  title: string;
  description: string;
  coverImage?: string;
  price: number;
  currency?: string;
  originalPrice?: number;
  originalCurrency?: string;
  rating?: number;
  releaseDate: string;
  genres: string[];
  platforms: string[];
  developer?: string;
}

export interface GameDetails extends Game {
  longDescription?: string;
  screenshots?: string[];
  systemRequirements?: SystemRequirements;
}

export interface SystemRequirements {
  minimum: string;
  recommended: string;
}

// Review Types
export interface Review {
  id: string;
  gameId: string;
  userId: string;
  rating: number;
  comment: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateReviewInput {
  gameId: string;
  rating: number;
  comment: string;
}

// Library Types
export type LibraryStatus = 'OWNED' | 'WISHLIST' | 'FAVORITE';

export interface LibraryItem {
  id: string;
  userId: string;
  gameId: string;
  statuses: LibraryStatus[];
  progressMinutes: number;
  addedAt: string;
  game?: Game;
}

// Recommendation Types
export interface Recommendation {
  id: string;
  userId: string;
  games: Game[];
  reason: string;
}

// API Response Types
export interface ApiResponse<T> {
  data?: T;
  error?: {
    message: string;
    code: string;
    details?: Record<string, string>;
  };
  status: number;
}

export interface PaginatedResponse<T> {
  items: T[];
  total: number;
  page: number;
  pageSize: number;
}

export interface Genre {
  id: string;
  code: string;
  name: string;
}

export interface Platform {
  id: string;
  code: string;
  name: string;
}
