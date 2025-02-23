# Auth Service

## Descripción

**Auth Service** es un microservicio de autenticación desarrollado con **Spring Boot**, **JWT** y **OAuth2**, diseñado para gestionar la autenticación y autorización de usuarios dentro de un ecosistema de microservicios.

Este servicio permite la autenticación tanto mediante **OAuth2** como por **usuario y contraseña**, asegurando flexibilidad y seguridad. Además, está preparado para ser ejecutado en entornos **Dockerizados**, facilitando su despliegue y escalabilidad.

## Tecnologías utilizadas

- **Spring Boot** - Framework para el desarrollo del microservicio.
- **Spring Security** - Para la gestión de autenticación y autorización.
- **JWT (JSON Web Token)** - Para la gestión de autenticación basada en tokens.
- **OAuth2** - Para autenticación con terceros.
- **PostgreSQL** - Base de datos para el almacenamiento de usuarios.
- **Docker** - Para la contenedorización y despliegue del servicio.

## Requisitos previos

Antes de ejecutar el servicio, asegúrate de tener instalado:

- **Java 17+**
- **Maven**
- **Docker y Docker Compose**
- **PostgreSQL** (si se ejecuta sin Docker)

## Configuración

### `application.properties`

```properties
# Configuración general
spring.application.name=Auth Service
logging.level.org.springframework=DEBUG
logging.level.org.springframework.security=DEBUG

# Configuración de la base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/auth-service
spring.datasource.username=postgres
spring.datasource.password=12345678
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de JPA
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Swagger UI
springdoc.swagger-ui.path=/swagger-ui.html

# DevTools
spring.devtools.restart.enabled=true
spring.devtools.livereload.enabled=true
spring.devtools.add-properties=true

# JWT Configuración
jwt.secret=EstaEsUnaClaveSuperSeguraDeAlMenos32Caracteres123!
jwt.expiration=3600000

# Configuración de OAuth2
spring.security.oauth2.client.registration.my-client.client-id=my-client-id
spring.security.oauth2.client.registration.my-client.client-secret=my-client-secret
spring.security.oauth2.client.registration.my-client.authorization-grant-type=client_credentials
spring.security.oauth2.client.registration.my-client.scope=read
spring.security.oauth2.client.registration.my-client.provider=my-provider

# Definición del provider
spring.security.oauth2.client.provider.my-provider.token-uri=https://example.com/oauth2/token
```

## Instalación y ejecución

1. Clona el repositorio:

   ```bash
   git clone https://github.com/2A2G/auth-service.git
   cd auth-service
   ```

2. Configura las variables de entorno en `application.properties` o usa un archivo `.env`.

3. Construye el proyecto con Maven:

   ```bash
   mvn clean package
   ```

4. Ejecuta el servicio localmente:

   ```bash
   java -jar target/auth-service.jar
   ```

5. Para ejecutarlo con Docker:

   ```bash
   docker build -t auth-service .
   docker run -p 8080:8080 auth-service
   ```

## Despliegue con Docker Compose

Puedes usar Docker Compose para desplegar el servicio junto con la base de datos:

   ```bash
   docker-compose up -d
   ```

## Licencia

Todos los derechos reservados © [2A2G](https://github.com/2A2G). Este repositorio es de uso privado y no se permite su distribución sin autorización.

