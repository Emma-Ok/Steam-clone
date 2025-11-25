export const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL?.replace(/\/$/, '') || 'http://localhost:8080/api';

export const API_ENDPOINTS = {
  // Auth
  REGISTER: '/auth/register',
  LOGIN: '/auth/login',
  LOGOUT: '/auth/logout',
  ME: '/auth/me',
  OAUTH_GOOGLE: '/auth/oauth2/authorization/google',
  OAUTH_GITHUB: '/auth/oauth2/authorization/github',

  // Games
  GAMES: '/games',
  GAME_DETAIL: (id: string) => `/games/${id}`,
  GAME_REVIEWS: (id: string) => `/games/${id}/reviews`,

  // Users
  USER_PROFILE: (id: string) => `/users/${id}`,
  USER_LIBRARY: (id: string) => `/users/${id}/library`,
  USER_UPDATE: (id: string) => `/users/${id}`,
  
  // Library
  LIBRARY_ADD: '/library/add',
  LIBRARY_REMOVE: (gameId: string) => `/library/remove/${gameId}`,

  // Reviews
  REVIEWS: '/reviews',
  REVIEW_DETAIL: (id: string) => `/reviews/${id}`,

  // Recommendations
  RECOMMENDATIONS: '/recommendations',
  RECOMMENDATIONS_USER: (userId: string) => `/recommendations/${userId}`,

  // Catalogs
  GENRES: '/genres',
  PLATFORMS: '/platforms',
};

export const HTTP_STATUS = {
  OK: 200,
  CREATED: 201,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  INTERNAL_SERVER_ERROR: 500,
};
