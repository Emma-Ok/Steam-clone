import { Game } from '@/types';

export interface MoneyDto {
  amount: number;
  currency: string;
}

export interface GameResponseDto {
  id: string;
  title: string;
  description: string;
  basePrice?: MoneyDto;
  currentPrice?: MoneyDto;
  releaseDate?: string;
  genres?: string[];
  platforms?: string[];
}

const FALLBACK_COVER = '/placeholder.svg?height=400&width=700&text=Game';
const FALLBACK_CURRENCY = 'USD';

type CoverRule = {
  keywords: string[];
  cover: string;
};

const EXACT_TITLE_COVERS: Record<string, string> = {
  'eclipse odyssey': '/elden-ring-inspired-cover.png',
  'neon drifters': '/cyberpunk-2077-inspired-cover.png',
  'legends unbound': '/baldurs-gate-3-cover.png',
  'farmstead valley': '/palworld-game-cover.jpg',
  'starborne tactics': '/cyberpunk-game-cover.png',
  'chrono strikers': '/palworld-inspired-cover.png',
  'haunted meridian': '/baldurs-gate-3-inspired-cover.png',
  'pocket architect': '/placeholder-logo.png',
  'arctic outriders': '/placeholder.jpg',
  'mythic tiles': '/generic-fantasy-game-cover.png',
};

const TITLE_COVER_RULES: CoverRule[] = [
  { keywords: ["baldur", "gate"], cover: '/baldurs-gate-3-cover.png' },
  { keywords: ["baldur", "gate", "inspired"], cover: '/baldurs-gate-3-inspired-cover.png' },
  { keywords: ["elden", "ring"], cover: '/elden-ring-inspired-cover.png' },
  { keywords: ["cyberpunk", "2077"], cover: '/cyberpunk-2077-inspired-cover.png' },
  { keywords: ["cyberpunk"], cover: '/cyberpunk-game-cover.png' },
  { keywords: ["palworld", "early"], cover: '/palworld-inspired-cover.png' },
  { keywords: ["palworld"], cover: '/palworld-game-cover.jpg' },
];

const GENRE_FALLBACKS: CoverRule[] = [
  { keywords: ['rpg'], cover: '/generic-fantasy-game-cover.png' },
  { keywords: ['fantasy'], cover: '/generic-fantasy-game-cover.png' },
  { keywords: ['adventure'], cover: '/generic-fantasy-game-cover.png' },
  { keywords: ['sci-fi'], cover: '/cyberpunk-game-cover.png' },
  { keywords: ['sci'], cover: '/cyberpunk-game-cover.png' },
  { keywords: ['cyber'], cover: '/cyberpunk-game-cover.png' },
];

const ROTATING_COVERS = [
  '/baldurs-gate-3-cover.png',
  '/baldurs-gate-3-inspired-cover.png',
  '/cyberpunk-2077-inspired-cover.png',
  '/cyberpunk-game-cover.png',
  '/elden-ring-inspired-cover.png',
  '/palworld-game-cover.jpg',
  '/palworld-inspired-cover.png',
  '/generic-fantasy-game-cover.png',
  '/placeholder.jpg',
  '/placeholder-logo.png',
];

function hashString(value: string): number {
  let hash = 0;
  for (const char of value) {
    const codePoint = char.codePointAt(0) ?? 0;
    hash = Math.trunc(hash * 31 + codePoint);
  }
  return hash;
}

function pickRotatingCover(seed?: string): string {
  if (!ROTATING_COVERS.length) {
    return FALLBACK_COVER;
  }
  const normalizedSeed = seed && seed.length > 0 ? seed : `${Date.now()}`;
  const index = Math.abs(hashString(normalizedSeed)) % ROTATING_COVERS.length;
  return ROTATING_COVERS[index];
}

function resolveCoverImage(dto: GameResponseDto): string {
  const normalizedTitle = dto.title?.trim().toLowerCase() ?? '';
  if (normalizedTitle && EXACT_TITLE_COVERS[normalizedTitle]) {
    return EXACT_TITLE_COVERS[normalizedTitle];
  }
  for (const rule of TITLE_COVER_RULES) {
    if (rule.keywords.every((keyword) => normalizedTitle.includes(keyword))) {
      return rule.cover;
    }
  }

  const genres = dto.genres?.map((genre) => genre.toLowerCase()) ?? [];
  for (const rule of GENRE_FALLBACKS) {
    if (genres.some((genre) => rule.keywords.every((keyword) => genre.includes(keyword)))) {
      return rule.cover;
    }
  }

  return pickRotatingCover(dto.id ?? dto.title ?? 'fallback');
}

export function mapGameDtoToGame(dto: GameResponseDto): Game {
  const currentPrice = dto.currentPrice ?? dto.basePrice;
  const originalPrice = dto.basePrice;
  const currency = currentPrice?.currency ?? originalPrice?.currency ?? FALLBACK_CURRENCY;

  return {
    id: dto.id,
    title: dto.title,
    description: dto.description,
    coverImage: resolveCoverImage(dto),
    price: currentPrice?.amount ?? 0,
    currency,
    originalPrice: originalPrice?.amount,
    originalCurrency: originalPrice?.currency ?? currency,
    rating: undefined,
    releaseDate: dto.releaseDate ?? new Date().toISOString(),
    genres: dto.genres ?? [],
    platforms: dto.platforms ?? [],
    developer: 'Unknown studio',
  };
}
