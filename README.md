# case-document-knowledge-service

**AI-powered answers for case documents — every response cited and auditable.**
Spring Boot 3 (Java 17) REST API with OpenAPI docs, PostgreSQL + Flyway, production-ready observability, and quality gates (Checkstyle, SpotBugs, JaCoCo).

---

## Features

- Versioned **REST API** under `/api/v1/**`
- **OpenAPI/Swagger UI** at `/swagger-ui.html`
- **PostgreSQL** persistence with **Flyway** migrations
- **Security**: dev **HTTP Basic** (default) or **JWT** resource server
- **Observability**: Actuator health, Prometheus metrics, OTLP tracing, JSON logs
- **Quality**: Checkstyle, SpotBugs, JaCoCo coverage reports

---

## Tech Stack

- Spring Boot **3.5.x**, Java **21**
- springdoc-openapi **2.x**
- Spring Data JPA, **PostgreSQL**, Flyway (+ `flyway-database-postgresql`)
- Micrometer + **Prometheus**, OpenTelemetry OTLP exporter
- Maven with Checkstyle, SpotBugs, JaCoCo

---

## Quickstart

### Prerequisites
- Java 21+
- Maven 3.9+
- Docker (optional, for Compose)

### Build
```bash
./mvnw -U clean verify
```

#### Common variations
```bash
# Faster local build (skip tests)
./mvnw -U -DskipTests clean package

# Only unit tests
./mvnw test

# Run a single test class
./mvnw -Dtest=AnyTest test

# Run Checkstyle / SpotBugs explicitly
./mvnw checkstyle:check
./mvnw spotbugs:spotbugs

# Skip Checkstyle / SpotBugs when needed
./mvnw -Dcheckstyle.skip=true -Dspotbugs.skip=true -DskipTests clean package

# Dependency tree (to debug conflicts)
./mvnw dependency:tree
```

### Run with Docker Compose (recommended)
```bash
# (Optional) pin host ports; otherwise the defaults are chosen automatically
echo "DB_HOST_PORT=5432"  > .env
echo "APP_HOST_PORT=8080" >> .env

# Build image & start Postgres + app
docker compose up -d --build

# Tail logs
docker compose logs -f app

# See what's running
docker compose ps
```

#### Stop & remove
```bash
# Stop containers but keep them
docker compose stop

# Stop and remove containers + default network
docker compose down

# Stop and remove containers + network + volumes (⚠️ deletes DB data)
docker compose down -v

# Also remove images built by compose
docker compose down --rmi local

# Remove exited containers for this project (no prompt)
docker compose rm -f
```

---

## Authentication (local dev)

By default (dev profile), endpoints other than `GET /api/v1/hello` require **HTTP Basic**:

- **Username:** `user`
- **Password:** `changeme`

These come from environment variables in `docker-compose.yml`:
```
SPRING_SECURITY_USER_NAME=user
SPRING_SECURITY_USER_PASSWORD=changeme
```

To switch to **JWT** mode in any environment, set:
```
SECURITY_JWT_ENABLED=true
OIDC_ISSUER_URI=<your-issuer>
```

---

## API Smoke Tests

Pick the app port (when using Compose):
```bash
APP_PORT="$(docker compose port app 8080 | awk -F: '{print $2}')"
: "${APP_PORT:=8080}"
echo "APP_PORT=${APP_PORT}"
```

Hello (no auth required):
```bash
curl -fsS "http://localhost:${APP_PORT}/api/v1/hello?name=MoJ" | jq
```

OpenAPI / Swagger UI:
```
http://localhost:${APP_PORT}/swagger-ui.html
```
---

## Observability & Health Checks

The service exposes standard Spring Boot Actuator endpoints on the same HTTP port as the API.

### 1) Pick the app port

If you used Docker Compose, the app port on your machine is set in `.env` as `APP_HOST_PORT` (defaults to `8080`).
To discover it dynamically:

```bash
APP_PORT="$(docker compose port app 8080 | awk -F: '{print $2}')"
: "${APP_PORT:=8080}"   # fallback if not running via Compose
echo "Using APP_PORT=${APP_PORT}"
```

### 2) Endpoints

| Endpoint                     | Purpose                         |
|-----------------------------|---------------------------------|
| `/actuator/health`          | Overall health (UP/DOWN)        |
| `/actuator/health/liveness` | Liveness probe                  |
| `/actuator/health/readiness`| Readiness probe                 |
| `/actuator/info`            | App/build info (if configured)  |
| `/actuator/prometheus`      | Prometheus/OpenMetrics scrape   |

> In this project, **health** and **prometheus** are exposed without auth.
> The **info** endpoint is exposed, but only shows data if you populate `info.*` or include build metadata.

### 3) Quick checks (curl)

Overall health:
```bash
curl -fsS "http://localhost:${APP_PORT}/actuator/health"
# -> {"status":"UP"}
```

Liveness & readiness:
```bash
curl -fsS "http://localhost:${APP_PORT}/actuator/health/liveness"
curl -fsS "http://localhost:${APP_PORT}/actuator/health/readiness"
```

Info:
```bash
curl -fsS "http://localhost:${APP_PORT}/actuator/info" | jq
```

Prometheus metrics (sample):
```bash
curl -fsS "http://localhost:${APP_PORT}/actuator/prometheus" | head -n 40
```

---


## License

MIT
