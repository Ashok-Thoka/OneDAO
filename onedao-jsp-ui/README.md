# OneDAO JSP + REST + Microsoft SQL Server

A single Spring Boot application serving separate JSP screens and a reusable REST API. Both UI JavaScript and external clients (Postman/mobile/other web clients) call the same `/api/**` endpoints and share the same service/repository/business logic.

## Technology
- Java 21, Spring Boot 3.5.7, Spring Security 6 (managed by Boot), JSP/JSTL, embedded Tomcat Jasper
- JWT (JJWT 0.12.6), BCrypt password hashing
- Microsoft SQL Server + Spring Data JPA for products
- In-memory user registry and in-memory JWT logout blacklist (as requested by the machine test's in-memory user-auth requirement)
- Swagger UI / OpenAPI

## Project layout
- `src/main/webapp/WEB-INF/jsp/`: login, signup, dashboard, settings, products, error JSP pages
- `src/main/resources/static/js/`: one JS file per screen/service plus common API/auth helper
- `src/main/resources/static/css/app.css`: shared styles
- `controller/`: REST controllers and browser page controller
- `service/`: shared business logic used by REST endpoints
- `database/onedao_mssql.sql`: database creation script
- `postman/OneDAO-MSSQL.postman_collection.json`: REST test collection

## Important architecture
`PageController` only returns JSP view names. It does not implement business rules. The browser JavaScript calls `/api/**` with `Authorization: Bearer <JWT>`. Postman and other applications call exactly the same REST endpoints. Product CRUD is persisted in SQL Server. Users are in memory, so they are lost on application restart, matching the in-memory user requirement.

## Requirements covered
- Signup: username 8–50 characters; email must contain `@` and `_`; password 8–12 characters with uppercase, lowercase, number and special character; Standard User or Administrator.
- Country check on signup: private/local IP uses `LOCAL_COUNTRY` (default India); public IP lookup uses configured geo provider; default blocked countries Syria, Afghanistan, Iran.
- BCrypt password hashing; JWT includes subject, role, issue time and expiration.
- Login returns JWT; invalid credentials return HTTP 401 with `Login Failed`.
- Dashboard requires JWT and returns `Welcome <Username>`.
- Settings API is protected by `@PreAuthorize` and only Administrator can access it.
- Logout blacklists the presented JWT until expiry; a blacklisted token cannot call protected APIs.
- Product CRUD; only authenticated users can list/create/read/update; only Administrator can delete. Pagination, filtering and sorting are supported.
- Swagger UI and Postman collection.

The PDF mentions OTP in its general job description but does not specify an OTP workflow, delivery channel, expiry, retry rules, or verification contract in the machine-test requirements. This implementation does not invent an OTP workflow. Add it if the evaluator provides those details.

## SQL Server setup
1. Run `database/onedao_mssql.sql` in SSMS. The script creates the `OneDAO` database.
2. Make sure SQL Server TCP/IP is enabled and listening on port 1433.
3. Set `DB_USERNAME` and `DB_PASSWORD` to your SQL Server credentials.

## Run from Windows CMD
Requires JDK 21 and Maven 3.9+.

```cmd
set DB_URL=jdbc:sqlserver://localhost:1433;databaseName=OneDAO;encrypt=true;trustServerCertificate=true
set DB_USERNAME=sa
set DB_PASSWORD=YourActualPassword
set JWT_SECRET=ReplaceWithASecretAtLeast32CharactersLong
mvn clean spring-boot:run
```

Or open the folder in IntelliJ, set Project SDK to JDK 21, reload Maven and run `com.onedao.backend.OneDaoBackendApplication`.

## Browser URLs
- Login: `http://localhost:8080/` or `/login`
- Sign up: `http://localhost:8080/signup`
- Dashboard: `http://localhost:8080/dashboard`
- Products: `http://localhost:8080/products`
- Settings: `http://localhost:8080/settings` (API data is administrator-only)
- Swagger: `http://localhost:8080/swagger-ui/index.html`

The JSP routes are view routes. They load the page shell; protected data is fetched from secured REST APIs. If no JWT exists, page JavaScript redirects to `/login`.

## REST endpoints
| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/auth/signup` | Public |
| POST | `/api/auth/login` | Public |
| GET | `/api/auth/error` | Public; returns Login Failed message |
| POST | `/api/auth/logout` | Valid JWT required by endpoint logic |
| GET | `/api/dashboard` | Authenticated |
| GET | `/api/settings` | Administrator only |
| GET | `/api/products` | Authenticated; pagination/filter/sort |
| GET | `/api/products/{id}` | Authenticated |
| POST | `/api/products` | Authenticated |
| PUT | `/api/products/{id}` | Authenticated |
| DELETE | `/api/products/{id}` | Administrator only |

## Signup JSON
```json
{
  "username": "ashok123",
  "email": "ashok_demo@example.com",
  "password": "Test@123",
  "userType": "STANDARD_USER"
}
```
The email pattern intentionally enforces both `@` and `_`, as stated in the supplied PDF.

## Security note
This is a machine-test/local demonstration. The in-memory users and blacklist are not shared across multiple application instances and are cleared on restart. For production, use a persistent user store and shared token revocation strategy, enforce HTTPS, configure trusted proxy IP handling, keep JWT secrets outside source control, and prefer HttpOnly Secure SameSite cookies for browser authentication rather than JavaScript-readable storage. Role self-selection at public signup is retained to match the machine-test input; production systems must restrict Administrator assignment.
