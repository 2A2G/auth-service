# Usar la imagen oficial de PostgreSQL
FROM postgres:latest

# Exponer el puerto de PostgreSQL
EXPOSE 5432

# Copiar el script de inicialización
COPY init.sql /docker-entrypoint-initdb.d/
