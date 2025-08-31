# spring-boot-starter-parent – Microservicios (Atenciones Médicas & Publicaciones)

**GroupId**: `org.springframework.boot` • **ArtifactId**: `spring-boot-starter-parent` • **Version**: `3.5.5`  
**Java**: 17 • **Spring Boot**: 3.x • **Puerto**: `8080`

Este proyecto contiene dos microservicios lógicos dentro del mismo Spring Boot app:  
1) **Atenciones Médicas** (pacientes, historial de visitas)  
2) **Publicaciones** (posts, comentarios, promedio de calificaciones)

## Requisitos
- Java 17
- Maven 3.9+
- Postman (opcional, para probar endpoints)

## Cómo ejecutar
```bash
mvn spring-boot:run
# o bien
mvn clean package && java -jar target/spring-boot-starter-parent-3.5.5.jar
```
La app se levanta en: `http://localhost:8080`

## Endpoints
### Atenciones Médicas
- `GET /api/medical/patients`
- `GET /api/medical/patients/by-rut`
- `GET /api/medical/patients/{id}`
- `GET /api/medical/patients/{id}/visits`
- `GET /api/medical/visits`

### Publicaciones
- `GET /api/posts/comments`
- `GET /api/posts/{id}`
- `GET /api/posts/{id}/comments`
- `GET /api/posts/{id}/rating/average`


## Validaciones y manejo de errores
- Se aplican validaciones con `jakarta.validation` (por ejemplo `@Min`, `@Max`, `@Pattern`).  
- Recomendado: usar `@ControllerAdvice` global que devuelva un JSON de error consistente (`ApiError`) y códigos HTTP adecuados (`400` para validaciones, `404` para recursos no encontrados).

### Ejemplos de respuestas
**200 – Lista de publicaciones**
```json
[
  { "id": 1, "title": "Bienvenidos", "content": "..." },
  { "id": 2, "title": "Novedades", "content": "..." }
]
```
**404 – Recurso no encontrado (sugerido)**
```json
{ "message": "No se encontró la publicación con id 999", "path": "/api/posts/999", "timestamp": "2025-08-31T18:00:00Z" }
```

## Probar con cURL
```bash
# Publicaciones
curl -s http://localhost:8080/api/posts | jq .
curl -s http://localhost:8080/api/posts/1 | jq .
curl -s http://localhost:8080/api/posts/1/comments | jq .
curl -s http://localhost:8080/api/posts/1/rating/average
curl -s http://localhost:8080/api/posts/comments?minRating=1


# Médicas (ajusta ID/RUT según datos semilla)
curl -s http://localhost:8080/api/medical/patients | jq .
curl -s http://localhost:8080/api/medical/patients/1 | jq .
curl -s "http://localhost:8080/api/medical/patients/by-rut?rut=12.345.678-5" | jq .
curl -s http://localhost:8080/api/medical/patients/1/visits | jq .
```

## Spring Initializr (opcional)
```bash
spring init   --dependencies=web,validation   --java-version=17   --boot-version=3.3.2   --name=spring-boot-starter-parent   --package-name=org.springframework.boot.springbootstarterparent   spring-boot-starter-parent
```

