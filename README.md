# Auth Service

## Descripción

Auth Service es un microservicio de autenticación desarrollado con **Spring Boot** y **JWT**, diseñado para gestionar la
autenticación y autorización de usuarios dentro de un ecosistema de microservicios.

Este servicio forma parte de un conjunto de microservicios y proporciona autenticación segura utilizando **JSON Web
Tokens (JWT)**. Además, está preparado para ser ejecutado en entornos **Dockerizados**, facilitando su despliegue y
escalabilidad.

## Tecnologías utilizadas

- **Spring Boot** - Framework para el desarrollo del microservicio.
- **JWT (JSON Web Token)** - Para la gestión de autenticación.
- **Docker** - Para la contenedorización y despliegue del servicio.
- **PostgreSQL/MySQL** - Base de datos para el almacenamiento de usuarios (según configuración).
- **Spring Security** - Para la gestión de permisos y roles de usuarios.

## Requisitos previos

Antes de ejecutar el servicio, asegúrate de tener instalado:

- **Java 17+**
- **Maven**
- **Docker y Docker Compose**

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

Todos los derechos reservados © [2A2G](https://github.com/2A2G). Este repositorio es de uso privado y no se permite su
distribución sin autorización.

