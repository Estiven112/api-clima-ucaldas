# API Clima UCaldas - Monitoreo Meteorológico

API REST desarrollada en Spring Boot (Java 17) y PostgreSQL para el monitoreo de datos climatológicos, estaciones meteorológicas y gestión de usuarios.

---

## 🚀 Requisitos Previos

* Java 17 o superior.
* PostgreSQL corriendo localmente.

---

## 🛠️ Pasos para Inicializar el Proyecto

### 1. Base de Datos
Crea una base de datos en PostgreSQL:

```sql
CREATE DATABASE clima_db;
Ajusta las credenciales en src/main/resources/application.properties:Propertiesserver.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/clima_db
spring.datasource.username=postgres
spring.datasource.password=TU_CONTRASEÑA
spring.jpa.hibernate.ddl-auto=update
2. Clonar y EjecutarBashgit clone https://github.com/TU_USUARIO/TU_REPOSITORIO.git
cd TU_REPOSITORIO
./mvnw spring-boot:run
La API iniciará en: http://localhost:8081📌 Endpoints Principales

* **POST /api/ciudades** — Registrar ciudad.
* **POST /api/estaciones** — Registrar estación asociada a ciudad.
* **POST /api/usuarios** — Crear usuario.
* **POST /api/usuarios/{id}/roles/{rolId}** — Asignar rol a usuario.
* **POST /api/mediciones** — Registrar lectura climatológica.
* **GET /api/mediciones/estacion/{id}** — Historial de lecturas por estación.
* **GET /api/mediciones/promedio/ciudad/{ciudadId}** — Cálculo de promedio (JPQL).