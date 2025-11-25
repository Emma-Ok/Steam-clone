📄 README.md 

# 🎮 Steam Clone — Fullstack Monorepo
Plataforma inspirada en Steam para explorar arquitectura hexagonal, diseño atómico y autenticación moderna end-to-end. Este monorepo contiene todo lo necesario para levantar el frontend en Next.js y el backend en Spring Boot, incluida la integración con Supabase y OAuth2 (Google/GitHub).

---

## ✨ Funcionalidades destacadas
- **Autenticación completa**: registro/login tradicional con JWT + inicio de sesión social (Google y GitHub) con redirección segura al frontend.
- **Catálogo público**: juegos, géneros y plataformas expuestos como endpoints públicos para navegación sin sesión.
- **Biblioteca personal**: manejo de la librería de juegos del usuario autenticado.
- **Reseñas y calificaciones**: endpoint listo para listar/opinar sobre juegos (estructura preparada para habilitar moderación).
- **Arquitectura hexagonal real**: dominio separado de infraestructura, facilitando pruebas y cambios de proveedores.
- **Design System reutilizable**: componentes atómicos, moléculas y organismos listos para escalar la UI.

---

## 🧱 Stack

| Capa        | Tecnologías principales |
|-------------|--------------------------|
| Frontend    | Next.js 14, React 18, TypeScript, TailwindCSS, React Query, Zustand, Atomic Design |
| Backend     | Java 21, Spring Boot 3.3, Spring Security, OAuth2 Client, JWT, MapStruct, Flyway |
| Datos       | Supabase (PostgreSQL) |
| Tooling     | Gradle Kotlin DSL, pnpm/npm, Jest/JUnit, Mockito, Testcontainers |

---

## 📂 Estructura del repo

```
Steam-clone/
├── backend/                      # API Spring Boot (arquitectura hexagonal)
│   ├── src/main/java/com/alidev/steamclone/
│   │   ├── domain/               # Entidades, value objects, puertos
│   │   ├── application/          # Casos de uso, servicios
│   │   └── infrastructure/       # REST, seguridad, persistencia, OAuth
│   └── build.gradle.kts
│
├── frontend/
│   └── front/                    # App Next.js + Design System
│       ├── app/                  # Rutas App Router
│       ├── src/design-system/    # Atomic design (atoms/molecules/organisms)
│       ├── src/shared/           # hooks, providers, tipos, utils
│       └── package.json
└── README.md
```

---

## ✅ Requisitos previos
- Node.js ≥ 18.x (recomendado usar `nvm`)
- pnpm o npm (el proyecto funciona con ambos; los ejemplos usan npm)
- Java 21 (Temurin u OpenJDK)
- Gradle Wrapper (incluido)
- Cuenta Supabase con una base PostgreSQL creada
- Credenciales OAuth2 para Google y GitHub

---

## ⚙️ Configuración inicial

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/<tu-user>/steam-clone.git
   cd steam-clone
   ```

2. **Variables de entorno frontend** (`frontend/front/.env`)
   ```bash
   NEXT_PUBLIC_API_URL=http://localhost:8080
   ```

3. **Configurar el backend** (`backend/src/main/resources/application.yml`)
   ```yaml
   server:
     port: 8080

   spring:
     datasource:
       url: jdbc:postgresql://<SUPABASE_HOST>:5432/postgres?sslmode=require
       username: <SUPABASE_USER>
       password: <SUPABASE_PASSWORD>
     security:
       oauth2:
         client:
           registration:
             google:
               client-id: <GOOGLE_CLIENT_ID>
               client-secret: <GOOGLE_CLIENT_SECRET>
               redirect-uri: "{baseUrl}/login/oauth2/code/google"
             github:
               client-id: <GITHUB_CLIENT_ID>
               client-secret: <GITHUB_CLIENT_SECRET>
               redirect-uri: "{baseUrl}/login/oauth2/code/github"
   security:
     jwt:
       secret: <JWT_SECRET_SUPER_SEGURO>
   app:
     cors:
       allowed-origins: http://localhost:3000
   ```

4. **Registrar URIs de redirección OAuth**
   - Google → `http://localhost:8080/login/oauth2/code/google`
   - GitHub → `http://localhost:8080/login/oauth2/code/github`

5. **Sembrar datos**: puedes usar Supabase SQL editor para importar juegos/géneros/plataformas o cargar tus propios datos.

---

## ▶️ Cómo correr el proyecto

### Frontend
```bash
cd frontend/front
npm install   # o pnpm install
npm run dev
```
Disponible en `http://localhost:3000`.

### Backend
```bash
cd backend
./gradlew bootRun
```
Disponible en `http://localhost:8080`.

> Consejo: levanta primero `bootRun` y luego `npm run dev` para que el callback OAuth pueda redirigir correctamente al frontend (`/oauth-callback`).

### Tests Backend
```bash
cd backend
./gradlew test
```

### Tests Frontend (si aplica)
```bash
cd frontend/front
npm run test
```

---

## 🔐 Flujo de autenticación
1. El usuario inicia sesión con email/contraseña o pulsa **Google/GitHub**.
2. Spring Security gestiona `/oauth2/authorization/{provider}` y recibe el callback en `/login/oauth2/code/{provider}`.
3. `OAuth2LoginSuccessHandler` genera un JWT y redirige al frontend: `http://localhost:3000/oauth-callback?token=<JWT>`.
4. El frontend guarda el token (localStorage/cookie) y React Query actualiza el estado de sesión.

---

## 📡 Funcionalidades expuestas por la API
- `/auth/**` → registro, login clásico, logout, refresh.
- `/oauth2/**` → inicio social (Google/GitHub).
- `/games`, `/genres`, `/platforms` → catálogos públicos.
- `/library/**` → biblioteca del usuario autenticado.
- `/reviews/**` → reseñas de juegos.
- `/actuator/health` → healthcheck para despliegues.

Próximos endpoints: recomendaciones, wishlist y OpenAPI documentado.

---

## 🧪 Calidad y pruebas
- **JUnit + Mockito** para dominio y casos de uso.
- **Testcontainers** disponible para pruebas de integración con PostgreSQL.
- **React Testing Library** (pendiente de habilitar) para componentes críticos.

Ejecutar cobertura: `./gradlew test jacocoTestReport` (configurar plugin Jacoco).

---

## 🛠️ Troubleshooting rápido
| Problema | Causa común | Solución |
|----------|-------------|----------|
| Google devuelve `redirect_uri_mismatch` | URI registrada con `/api` o puerto incorrecto | Usar exactamente `http://localhost:8080/login/oauth2/code/google` |
| Botón Google abre pantalla "Login with OAuth 2.0" | Backend aún con `context-path: /api` o servidor caído | Asegúrate de que `context-path` sea `/` y que `bootRun` esté activo |
| Estado no se actualiza tras login | Callback `/oauth-callback` no guarda token | Verifica hook `use-auth-actions` y almacenamiento del JWT |

---

## 🤝 Convenciones
- **Ramas**: `main` (estable), `dev`, `feature/*`.
- **Commits**: Conventional Commits (`feat:`, `fix:`, `docs:`, etc.).
- **Code style**: ESLint + Prettier en frontend, Checkstyle/Spotless pendiente en backend.

---

## 📜 Licencia & contribuciones
Uso libre para fines educativos y experimentales. Se aceptan PRs que sigan buenas prácticas y mantengan la arquitectura limpia.

---
