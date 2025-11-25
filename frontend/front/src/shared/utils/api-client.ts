import { ApiResponse } from '@/types';
import { API_BASE_URL } from '@/src/shared/constants/api';

interface FetchOptions extends RequestInit {
  params?: Record<string, string | number>;
}

function buildUrl(endpoint: string, params?: Record<string, string | number>) {
  const url = new URL(`${API_BASE_URL}${endpoint}`);
  if (params) {
    for (const [key, value] of Object.entries(params)) {
      url.searchParams.append(key, String(value));
    }
  }
  return url.toString();
}

async function parseBody(response: Response) {
  const rawBody = await response.text();
  if (!rawBody) {
    return undefined;
  }
  try {
    return JSON.parse(rawBody);
  } catch {
    return rawBody;
  }
}

function normalizeData<T>(payload: unknown): T | null {
  if (payload && typeof payload === 'object' && 'data' in (payload as Record<string, unknown>)) {
    return ((payload as Record<string, unknown>).data ?? null) as T | null;
  }
  return (payload as T) ?? null;
}

function isApiErrorShape(payload: unknown): payload is { code?: string; message?: string; details?: Record<string, string> } {
  return Boolean(
    payload &&
      typeof payload === 'object' &&
      ('message' in (payload as Record<string, unknown>) || 'code' in (payload as Record<string, unknown>))
  );
}

function resolveError(response: Response, payload: unknown) {
  if (response.ok) {
    return undefined;
  }
  const fallbackMessage = response.statusText || 'Request failed';
  const fallbackCode = `HTTP_${response.status}`;
  if (payload && typeof payload === 'object' && 'error' in (payload as Record<string, any>)) {
    return (payload as Record<string, any>).error;
  }
  if (isApiErrorShape(payload)) {
    const body = payload as Record<string, any>;
    return {
      message: typeof body.message === 'string' && body.message.trim().length > 0 ? body.message : fallbackMessage,
      code: typeof body.code === 'string' && body.code.length > 0 ? body.code : fallbackCode,
      details: body.details as Record<string, string> | undefined,
    };
  }
  if (typeof payload === 'string' && payload.trim().length > 0) {
    return { message: payload, code: fallbackCode };
  }
  return { message: fallbackMessage, code: fallbackCode };
}

function buildHeaders(headersInit: HeadersInit | undefined, token: string | null) {
  const headers = new Headers(headersInit);
  if (!headers.has('Content-Type')) {
    headers.set('Content-Type', 'application/json');
  }
  if (token) {
    headers.set('Authorization', `Bearer ${token}`);
  }
  return headers;
}

export async function apiClient<T>(
  endpoint: string,
  options: FetchOptions = {}
): Promise<ApiResponse<T>> {
  const { params, ...fetchOptions } = options;
  const url = buildUrl(endpoint, params);

  try {
    const hasWindow = typeof globalThis.window === 'object';
    const token = hasWindow ? globalThis.window.localStorage.getItem('authToken') : null;
    const headers = buildHeaders(fetchOptions.headers || {}, token);

    const response = await fetch(url, {
      ...fetchOptions,
      headers,
    });

    const payload = await parseBody(response);
    const data = normalizeData<T>(payload) ?? undefined;
    const error = resolveError(response, payload);

    return {
      data,
      status: response.status,
      error,
    };
  } catch (error) {
    return {
      status: 500,
      error: {
        message: error instanceof Error ? error.message : 'Unknown error',
        code: 'FETCH_ERROR',
      },
    };
  }
}

export async function get<T>(endpoint: string, options?: FetchOptions) {
  return apiClient<T>(endpoint, { ...options, method: 'GET' });
}

export async function post<T>(endpoint: string, body?: unknown, options?: FetchOptions) {
  return apiClient<T>(endpoint, {
    ...options,
    method: 'POST',
    body: body ? JSON.stringify(body) : undefined,
  });
}

export async function put<T>(endpoint: string, body?: unknown, options?: FetchOptions) {
  return apiClient<T>(endpoint, {
    ...options,
    method: 'PUT',
    body: body ? JSON.stringify(body) : undefined,
  });
}

export async function del<T>(endpoint: string, options?: FetchOptions) {
  return apiClient<T>(endpoint, { ...options, method: 'DELETE' });
}
