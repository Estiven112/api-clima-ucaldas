Sistema de Monitoreo Climatológico - API REST
API REST desarrollada en Spring Boot para la gestión y monitoreo de datos climatológicos, estaciones meteorológicas, alertas y administración de usuarios.

🛠️ Tecnologías Utilizadas
Lenguaje: Java 17

Framework: Spring Boot 3.x

Persistencia: Spring Data JPA / Hibernate

Base de Datos: PostgreSQL

Herramientas de Soporte: Lombok, Jakarta Validation

Pruebas de API: Postman

🏗️ Arquitectura del Proyecto
El proyecto está estructurado bajo una arquitectura multicapa estándar (edu.ucaldas.clima):

Plaintext
src/main/java/edu/ucaldas/clima/
├── controllers/    # Endpoints REST (@RestController)
├── service/        # Lógica de negocio e intermediación
├── repository/     # Consultas a base de datos (Spring Data JPA & JPQL)
├── dto/            # Data Transfer Objects para requests y responses
└── model/          # Entidades JPA (Ciudad, Estacion, Usuario, Rol, TipoMedicion, RegistroClima, Alerta)
🗄️ Modelo de Datos y Relaciones
Ciudad 1:N Estación (Una ciudad contiene múltiples estaciones meteorológicas).

Estación 1:N RegistroClima (Una estación registra múltiples lecturas).

RegistroClima N:M TipoMedicion (Relación mapeada a través de registro_tipo_medicion).

RegistroClima 1:N Alerta (Un registro climático puede generar alertas automáticas).

Usuario N:M Rol (Gestión de roles y permisos a través de usuario_rol).

⚙️ Configuración e Instalación
1. Requisitos Previos
JDK 17 o superior instalado.

PostgreSQL en ejecución.

Maven instalado (o utilizar el wrapper ./mvnw).

2. Configurar la Base de Datos
Crea una base de datos en PostgreSQL llamada clima_db (o ajusta el nombre según tu entorno).

Configura el archivo src/main/resources/application.properties:

Properties
server.port=8081

# Configuración de PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/clima_db
spring.datasource.username=postgres
spring.datasource.password=TU_CONTRASEÑA

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
3. Ejecutar la Aplicación
Clona el repositorio y ejecuta el proyecto:

Bash
git clone https://github.com/TU_USUARIO/TU_REPOSITORIO.git
cd TU_REPOSITORIO
./mvnw spring-boot:run
La API estará escuchando en http://localhost:8081.

🚀 Endpoints Principales
🏙️ Ciudades y Estaciones
POST /api/ciudades — Registrar nueva ciudad.

GET /api/ciudades — Listar ciudades.

POST /api/estaciones — Registrar nueva estación asociada a una ciudad.

👤 Usuarios y Roles
POST /api/usuarios — Crear nuevo usuario.

POST /api/usuarios/{id}/roles/{rolId} — Asignar un rol a un usuario.

GET /api/usuarios/rol/{nombre} — Listar usuarios por rol.

🌡️ Mediciones y Clima
POST /api/mediciones — Registrar nueva lectura de clima.

GET /api/mediciones/estacion/{id} — Consultar historial de lecturas por estación.

GET /api/mediciones/promedio/ciudad/{ciudadId} — (JPQL) Calcular el promedio de temperatura por ciudad.

🧪 Pruebas con Postman
El proyecto incluye la colección de pruebas en la raíz del repositorio:

Abre Postman.

Haz clic en Import.

Selecciona el archivo coleccion_postman.json (o el nombre exportado en la raíz).

Ejecuta las peticiones en el siguiente orden para probar la integridad referencial:

Crear Ciudad

Crear Estación

Crear Usuario

Registrar Clima

Consultar Promedio por Ciudad