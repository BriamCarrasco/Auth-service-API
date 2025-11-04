# Auth Service API

Microservicio de autenticación y gestión de usuarios construido con Spring Boot 3 (Java 21), que expone endpoints para registro, login y operaciones CRUD básicas sobre usuarios. Incluye documentación OpenAPI/Swagger, hash de contraseñas con BCrypt y conexión a Oracle Database.

## 🧱 Stack tecnológico

- Java 21
- Spring Boot 3 (Web, Data JPA, Security, Validation)
- Oracle Database (OJDBC11)
- Springdoc OpenAPI (Swagger UI)
- Maven
- Docker (multi-stage build)

## 🚀 Características

- Registro de usuarios con validaciones y contraseña encriptada (BCrypt)
- Login por `username` + `password`
- CRUD básico de usuarios (listar, obtener por id, actualizar, eliminar)
- Documentación automática con Swagger UI
- Configuración de seguridad abierta (permitAll) pensada para desarrollo
- Imagen Docker con configuración de Wallet para Oracle

## 🏗️ Arquitectura (vista rápida)

- `controller`: expone endpoints REST (`/auth`, `/users`)
- `service`: reglas de negocio (registro, login, actualización)
- `repository`: persistencia con Spring Data JPA
- `model`: entidad `User`
- `security`: `SecurityConfig` con `PasswordEncoder` (BCrypt) y reglas HTTP

## 📚 Endpoints principales

Base URL por defecto: `http://localhost:8081`

- Autenticación (`/auth`)
  - `POST /auth/register` — Registra un usuario
  - `POST /auth/login` — Autentica un usuario

- Usuarios (`/users`)
  - `GET /users` — Lista todos los usuarios
  - `GET /users/{id}` — Obtiene un usuario por id
  - `PUT /users` — Actualiza un usuario existente
  - `DELETE /users/{id}` — Elimina un usuario por id

### Ejemplos de payload

Registro (`POST /auth/register`):
```json
{
  "name": "Ana",
  "firstLastname": "Pérez",
  "secondLastname": "Gómez",
  "email": "ana.perez@example.com",
  "username": "anap",
  "password": "P@ssw0rd!",
  "rut": "12345678-5"
}
```

Login (`POST /auth/login`):
```json
{
  "username": "anap",
  "password": "P@ssw0rd!"
}
```

Actualización (`PUT /users`):
```json
{
  "id": 1,
  "name": "Ana Carolina",
  "firstLastname": "Pérez",
  "secondLastname": "Gómez",
  "email": "ana.perez@example.com",
  "username": "anap",
  "password": "",
  "role": "users",
  "rut": "12345678-5"
}
```
- Si `password` viene vacío o null, se mantiene la contraseña anterior.

## 📖 Swagger / OpenAPI

- UI: `http://localhost:8081/swagger-ui/index.html`
- Docs JSON: `http://localhost:8081/v3/api-docs`

> La seguridad HTTP permite todo (`permitAll`) para facilitar el acceso a la documentación durante el desarrollo.

## 🧪 Requisitos previos

- JDK 21
- Maven 3.9+
- Base de datos Oracle accesible y Wallet válido

## ▶️ Cómo ejecutar (Maven)

- Compilar (Linux/macOS):

```bash
mvn clean package -DskipTests
```

- Compilar (Windows PowerShell):

```powershell
./mvnw.cmd clean package -DskipTests
```

- Ejecutar (Linux/macOS):

```bash
mvn spring-boot:run
```

- Ejecutar (Windows PowerShell):

```powershell
./mvnw.cmd spring-boot:run
```

La app arrancará en `http://localhost:8081`.

## 🐳 Ejecutar con Docker

Este proyecto incluye un Dockerfile multi-stage que construye la app y corre en una imagen JRE:

Construir imagen:

```bash
docker build -t auth-service-api:latest .
```

Correr contenedor (ejemplo mapeando puerto 8081):

```bash
docker run --name auth-service-api -p 8081:8081 \
  -e SPRING_CONFIG_LOCATION=./application.properties \
  auth-service-api:latest
```

## ✅ Tests

Ejecutar tests:

```bash
mvn test
```

## 🔒 Seguridad

- `BCryptPasswordEncoder` para hash de contraseñas.
- Config actual: CSRF deshabilitado y `permitAll` para todas las rutas (útil en dev). Para producción, configura reglas de autorización, CSRF y autenticación adecuadas (JWT, sesiones, etc.).

## 📦 Dependencias principales (pom.xml)

- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `spring-boot-starter-validation`
- `springdoc-openapi-starter-webmvc-ui`
- `ojdbc11` (runtime)

## 🧭 Estructura del proyecto

```
src/
  main/
    java/com/briamcarrasco/auth_service_api/
      controller/        # AuthController, UserController
      exception/         # Manejador global, exception personalizada
      model/             # Entidad User
      repository/        # UserRepository
      security/          # SecurityConfig
      service/           # UserService y UserServiceImpl
    resources/
      application.properties
```

## 📝 Notas

- `spring.jpa.hibernate.ddl-auto=none`: no genera ni actualiza el esquema automáticamente. Asegúrate de tener la tabla `tb_users` creada en Oracle con las columnas esperadas.
- Evita registrar contraseñas en logs. Este proyecto no imprime contraseñas, pero recuerda no loguearlas en controladores/servicios.

---

