📄 **README.md **

```markdown
# 🎮 Steam Clone — Fullstack Monorepo  
Una plataforma tipo Steam simplificada, construida para practicar arquitectura, ingeniería full stack y diseño modular moderno.

Este proyecto combina:

- **Next.js + TypeScript + Atomic Design** (frontend)
- **Spring Boot 3.3 + Java 21 + Arquitectura Hexagonal + Gradle** (backend)
- **Supabase (PostgreSQL)** como base de datos
- Diseño limpio, modular y escalable

---

## 🚀 Tech Stack

### **Frontend (front/)**
- Next.js 14+
- React 18
- TypeScript
- TailwindCSS
- Atomic Design
- React Query
- Axios
- Zustand (o Context API, según diseño del agente)

### **Backend (back/)**
- Java 21
- Spring Boot 3.3.x
- Arquitectura Hexagonal
- Spring Web / Spring Security
- OAuth2 Login (Google + GitHub)
- JWT (stateless API)
- MapStruct
- PostgreSQL (Supabase)
- Flyway
- JUnit 5 + Mockito
- Gradle Kotlin DSL

### **Infraestructura**
- Supabase (PostgreSQL)
- GitHub + GitHub Actions (opcional)
- Docker-ready (cuando se agreguen Dockerfiles)

---

## 📁 Estructura del Monorepo

```

Steam-clone/
│
├── back/                # Backend Spring Boot Hexagonal
│   ├── src/main/java
│   ├── build.gradle.kts
│   └── ...
│
├── front/               # Next.js frontend
│   ├── src/
│   ├── package.json
│   └── ...
│
├── .gitignore
└── README.md

```

---

## 🏗️ Arquitectura del Backend

El backend implementa una arquitectura hexagonal real:

```

back/src/main/java/com/alidev/steamclone/
│
├── domain/              # Entidades, Value Objects, Exceptions, Ports
│
├── application/         # Use Cases, DTOs, Services (reglas de negocio)
│
└── infrastructure/      # Adapters REST, DB, Security, Configs

````

Beneficios:

- Bajo acoplamiento
- Separación estricta entre dominio e infraestructura
- Fácil testeo
- Reemplazo sencillo de adaptadores (DB, REST, OAuth, etc.)

---

## 🔧 Cómo correr el proyecto

### 1. Clonar el repositorio
```bash
git clone https://github.com/<tu-user>/steam-clone.git
cd steam-clone
````

---

# ▶️ FRONTEND

Ir al directorio `front/`:

```bash
cd front
npm install
npm run dev
```

El frontend correrá en:

```
http://localhost:3000
```

---

# ▶️ BACKEND

Ir al directorio `back/`:

```bash
cd back
./gradlew bootRun
```

El backend correrá en:

```
http://localhost:8080
```

---

## 🗄️ Configurar Supabase

Crear un archivo en `back/src/main/resources/application.yaml` con:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://<HOST>:5432/postgres
    username: <USER>
    password: <PASSWORD>
```

---

# 📡 Endpoints principales

La API expone operaciones para:

* Autenticación (OAuth2 + JWT)
* Usuarios
* Juegos
* Géneros
* Plataformas
* Biblioteca
* Reseñas
* Recomendaciones

Contrato completo: se incluirá un `openapi.yaml` en futuras versiones.

---

## 🧪 Testing

### Ejecutar tests:

```bash
cd back
./gradlew test
```

Se usa:

* JUnit 5
* Mockito
* Testcontainers (opcional)
* Jacoco para cobertura

---

## 🤝 Convenciones del Proyecto

### Branching Model

* `main` → producción / estable
* `dev` → desarrollo
* `feature/*` → nuevas features

### Commits (Conventional Commits)

* `feat:` nueva feature
* `fix:` corrección
* `docs:` documentación
* `refactor:` mejora interna
* `chore:` tareas varias

---

## 🚀 Deploy (futuro)

* Frontend → Vercel
* Backend → Railway / Fly.io / Render
* Base de datos → Supabase

(Se agregarán workflows de CI/CD cuando el proyecto esté más avanzado.)

---

## 📜 Licencia

Este proyecto es libre para uso personal, educativo y experimental.

---

## 🙌 Contribuciones

Abiertas a mejoras, sugerencias y PRs con buenas prácticas.

```
