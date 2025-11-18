CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE roles (
    id UUID PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE genres (
    id UUID PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(120) NOT NULL
);

CREATE TABLE platforms (
    id UUID PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(120) NOT NULL
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(320) UNIQUE NOT NULL,
    username VARCHAR(120) NOT NULL,
    password_hash VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE user_roles (
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    role_id UUID REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE user_favorite_genres (
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    genre_id UUID REFERENCES genres(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, genre_id)
);

CREATE TABLE user_favorite_platforms (
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    platform_code VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, platform_code)
);

CREATE TABLE games (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    base_price_amount NUMERIC(10,2) NOT NULL,
    base_price_currency VARCHAR(3) NOT NULL,
    current_price_amount NUMERIC(10,2) NOT NULL,
    current_price_currency VARCHAR(3) NOT NULL,
    release_date TIMESTAMPTZ NOT NULL
);

CREATE TABLE game_genres (
    game_id UUID REFERENCES games(id) ON DELETE CASCADE,
    genre_id UUID REFERENCES genres(id) ON DELETE CASCADE,
    PRIMARY KEY (game_id, genre_id)
);

CREATE TABLE game_platforms (
    game_id UUID REFERENCES games(id) ON DELETE CASCADE,
    platform_id UUID REFERENCES platforms(id) ON DELETE CASCADE,
    PRIMARY KEY (game_id, platform_id)
);

CREATE TABLE price_history (
    id UUID PRIMARY KEY,
    game_id UUID REFERENCES games(id) ON DELETE CASCADE,
    amount NUMERIC(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    changed_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE reviews (
    id UUID PRIMARY KEY,
    game_id UUID REFERENCES games(id) ON DELETE CASCADE,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    rating INT NOT NULL,
    comment TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE library_entries (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    game_id UUID REFERENCES games(id) ON DELETE CASCADE,
    progress_minutes INT DEFAULT 0,
    added_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE library_entry_statuses (
    entry_id UUID REFERENCES library_entries(id) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL,
    PRIMARY KEY (entry_id, status)
);

CREATE TABLE recommendations (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    generated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE recommendation_games (
    recommendation_id UUID REFERENCES recommendations(id) ON DELETE CASCADE,
    game_id UUID REFERENCES games(id) ON DELETE CASCADE
);
