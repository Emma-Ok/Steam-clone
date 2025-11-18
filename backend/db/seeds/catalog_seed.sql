BEGIN;

-- Ensure reference data exists (idempotent upserts)
INSERT INTO genres (id, code, name) VALUES
    ('5f808b77-7b98-4c10-8e32-496f9965d9a1', 'ACTION', 'Acción'),
    ('1c6f17d5-395f-4103-92ba-40fb5dd75ef3', 'RPG', 'Rol'),
    ('4ea820a4-5830-47fb-8f7a-0b6caf0d327e', 'STRATEGY', 'Estrategia'),
    ('f6bd94cb-2f89-47a9-8813-214f3f7e44c1', 'INDIE', 'Indie'),
    ('387ea0d9-0f06-4f5d-b4ce-2d8a1169e9ee', 'SPORTS', 'Deportes'),
    ('be6f31ce-73f8-4954-9d8a-41d948e57843', 'RACING', 'Carreras'),
    ('03d4d1de-61d9-4184-a092-0a0ba54bb6ef', 'HORROR', 'Terror'),
    ('de21a74f-c65a-4eab-af0a-810afc017b8e', 'PUZZLE', 'Rompecabezas'),
    ('4c4c5f51-86d3-4a7e-a77c-1373ad2de78f', 'ADVENTURE', 'Aventura'),
    ('921a02b4-5d66-4a06-a2a2-93139c14f6cf', 'SIM', 'Simulación')
ON CONFLICT (code) DO UPDATE SET name = EXCLUDED.name;

INSERT INTO platforms (id, code, name) VALUES
    ('8dc3f16a-735f-4c29-a14d-6d26ac0f3e50', 'PC_WINDOWS', 'PC (Windows)'),
    ('7b8f6a2c-56c9-4b2f-9b5f-92b25c5d16dc', 'PC_LINUX', 'PC (Linux)'),
    ('3a822230-491a-432f-9a31-6be6af9fe61e', 'PLAYSTATION', 'PlayStation 5'),
    ('d5e0c860-8514-4a8c-8a2a-c60f8c48b957', 'XBOX', 'Xbox Series'),
    ('4f35f93d-4994-431a-ad49-cfb175c879a9', 'SWITCH', 'Nintendo Switch'),
    ('6f265777-6dc2-4525-aec2-ff5aae252f87', 'MOBILE', 'iOS / Android')
ON CONFLICT (code) DO UPDATE SET name = EXCLUDED.name;

-- Base game catalog (10 entries mixing genres/platforms/prices)
INSERT INTO games (id, title, description,
                   base_price_amount, base_price_currency,
                   current_price_amount, current_price_currency,
                   release_date)
VALUES
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b001', 'Eclipse Odyssey', 'RPG espacial con combate táctico y narrativa ramificada.', 69.99, 'USD', 59.99, 'USD', '2024-03-15T00:00:00Z'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b002', 'Neon Drifters', 'Arcade de carreras futuristas con soporte online para 12 jugadores.', 49.99, 'USD', 34.99, 'USD', '2023-11-10T00:00:00Z'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b003', 'Legends Unbound', 'Acción cooperativa ambientada en reinos fantásticos.', 59.99, 'USD', 44.99, 'USD', '2022-06-21T00:00:00Z'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b004', 'Farmstead Valley', 'Simulador de granja relajante con clima dinámico y cooperativo.', 39.99, 'USD', 29.99, 'USD', '2024-02-02T00:00:00Z'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b005', 'Starborne Tactics', 'Estrategia 4X con flotas modulares y exploración procedural.', 54.99, 'USD', 54.99, 'USD', '2023-09-08T00:00:00Z'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b006', 'Chrono Strikers', 'Deportes futuristas 3v3 con habilidades especiales y ranking global.', 29.99, 'USD', 19.99, 'USD', '2022-12-01T00:00:00Z'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b007', 'Haunted Meridian', 'Survival horror narrativo con decisiones permanentes.', 44.99, 'USD', 44.99, 'USD', '2024-10-31T00:00:00Z'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b008', 'Pocket Architect', 'Construye imperios urbanos desde el móvil con sincronización en la nube.', 14.99, 'USD', 9.99, 'USD', '2023-05-05T00:00:00Z'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b009', 'Arctic Outriders', 'Shooter cooperativo ambientado en colonias polares.', 59.99, 'USD', 49.99, 'USD', '2024-01-19T00:00:00Z'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b00a', 'Mythic Tiles', 'Puzzle táctico con campañas semanales y modo roguelite.', 19.99, 'USD', 14.99, 'USD', '2022-04-14T00:00:00Z')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    description = EXCLUDED.description,
    current_price_amount = EXCLUDED.current_price_amount,
    current_price_currency = EXCLUDED.current_price_currency,
    release_date = EXCLUDED.release_date;

-- Map games to genres
INSERT INTO game_genres (game_id, genre_id)
SELECT m.game_id::uuid, g.id
FROM (VALUES
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b001', 'RPG'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b001', 'ADVENTURE'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b002', 'RACING'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b002', 'ACTION'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b003', 'ACTION'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b003', 'RPG'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b004', 'SIM'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b004', 'INDIE'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b005', 'STRATEGY'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b006', 'SPORTS'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b006', 'ACTION'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b007', 'HORROR'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b007', 'ADVENTURE'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b008', 'SIM'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b008', 'INDIE'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b009', 'ACTION'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b009', 'ADVENTURE'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b009', 'RPG'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b00a', 'PUZZLE'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b00a', 'INDIE')
) AS m(game_id, genre_code)
JOIN genres g ON g.code = m.genre_code
ON CONFLICT DO NOTHING;

-- Map games to platforms
INSERT INTO game_platforms (game_id, platform_id)
SELECT m.game_id::uuid, p.id
FROM (VALUES
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b001', 'PC_WINDOWS'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b001', 'PC_LINUX'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b001', 'PLAYSTATION'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b002', 'PC_WINDOWS'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b002', 'XBOX'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b003', 'PC_WINDOWS'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b003', 'PLAYSTATION'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b004', 'PC_WINDOWS'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b004', 'SWITCH'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b005', 'PC_WINDOWS'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b005', 'PC_LINUX'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b006', 'PC_WINDOWS'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b006', 'PLAYSTATION'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b007', 'PC_WINDOWS'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b007', 'PLAYSTATION'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b008', 'MOBILE'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b008', 'SWITCH'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b009', 'PC_WINDOWS'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b009', 'XBOX'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b009', 'PLAYSTATION'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b00a', 'PC_WINDOWS'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b00a', 'SWITCH'),
    ('1f1b0d40-3a3a-4a60-8e79-8cb9d5f3b00a', 'MOBILE')
) AS m(game_id, platform_code)
JOIN platforms p ON p.code = m.platform_code
ON CONFLICT DO NOTHING;

COMMIT;
