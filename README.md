# Quorom Académico

**API REST del backend de Quorom Académico**, una plataforma que permite a los estudiantes universitarios consolidar de forma colectiva sus solicitudes de asignaturas y horarios, facilitando a la dirección de carrera la toma de decisiones sobre apertura de secciones.

## 1. Contexto y propósito

En la práctica actual, cuando un grupo de estudiantes necesita que se abra una sección de una asignatura en un horario determinado, cada uno suele gestionar su solicitud de forma individual. Esto genera solicitudes duplicadas, dificulta cuantificar la demanda real y hace más lento el proceso de decisión para la dirección de carrera.

**Quorom Académico** propone un flujo distinto:

1. Un estudiante crea una solicitud para una asignatura y un horario específico.
2. Otros estudiantes interesados **se suman** a esa misma solicitud en lugar de crear una nueva.
3. El número de estudiantes sumados a cada solicitud es visible para todos, aportando transparencia sobre la demanda real.
4. La dirección de carrera dispone de una vista consolidada de todas las solicitudes, ordenada por nivel de interés, para apoyar sus decisiones de planificación académica.

Este repositorio corresponde al **backend**: la API que gestiona usuarios, solicitudes y adhesiones. El frontend (interfaz web) consume esta API y se gestiona en un repositorio aparte.

### Enlaces

| Recurso | URL |
|---|---|
| Plataforma (frontend) | https://solicitud-asignaturas-frontend.vercel.app |
| API (backend) | https://solicitud-asignaturas.onrender.com |

> **Nota:** el backend está desplegado en un plan gratuito que "duerme" tras un período de inactividad. La primera petición tras un tiempo sin uso puede tardar entre 30 y 50 segundos en responder mientras el servicio se reactiva.

### Acceso de demostración

El registro de nuevos usuarios desde la pantalla de inicio de sesión aún no está habilitado en esta versión. Para probar la plataforma, puede ingresar con la siguiente cuenta de prueba:

- **Correo:** `uno@test.com`
- **Contraseña:** `miClave123`

## 2. Arquitectura y tecnologías

| Componente | Tecnología |
|---|---|
| Lenguaje / Framework | Java 21 + Spring Boot |
| Persistencia | Spring Data JPA / Hibernate |
| Base de datos (producción) | PostgreSQL |
| Base de datos (desarrollo local) | H2 / PostgreSQL local |
| Seguridad de contraseñas | BCrypt (hashing, nunca texto plano) |
| Despliegue | Docker sobre Render |
| CORS | Configurado explícitamente por origen permitido |


## 3. Consideraciones de seguridad

- Las contraseñas de los usuarios **nunca se almacenan en texto plano**; se procesan con BCrypt antes de guardarse.
- Las credenciales de la base de datos (usuario, contraseña, URL de conexión) se gestionan mediante **variables de entorno** en el servidor de despliegue y no se incluyen en el código fuente ni en este documento.
- El acceso entre dominios (CORS) está restringido explícitamente a los orígenes autorizados (el frontend oficial de la plataforma y el entorno de desarrollo local).
- **Estado actual:** los endpoints no requieren un token de autenticación por solicitud (no se ha implementado JWT); el control de qué puede ver o hacer cada usuario se maneja actualmente desde el frontend según el rol del usuario autenticado. Esto es una limitación conocida del MVP y es el primer punto señalado en la sección de próximos pasos, antes de un uso en producción con datos reales de estudiantes.


## 4. Modelo de datos

**Usuario**
| Campo | Descripción |
|---|---|
| `id` | Identificador único, autogenerado |
| `nombre` | Nombre del usuario |
| `email` | Correo electrónico, único por usuario |
| `password` | Contraseña, almacenada como hash (nunca visible en texto plano) |
| `rol` | `ESTUDIANTE` o `DIRECTOR` |

**Solicitud**
| Campo | Descripción |
|---|---|
| `id` | Identificador único, autogenerado |
| `asignatura` | Nombre de la asignatura solicitada |
| `horario` | Día y franja horaria propuestos |
| `fechaCreacion` | Fecha y hora de creación, asignada automáticamente |
| `creador` | Usuario que creó la solicitud |

**Adhesion**
| Campo | Descripción |
|---|---|
| `id` | Identificador único, autogenerado |
| `usuario` | Usuario que se sumó a la solicitud |
| `solicitud` | Solicitud a la que se sumó |
| `fecha` | Fecha y hora de la adhesión, asignada automáticamente |

Regla de negocio: un mismo usuario no puede sumarse dos veces a la misma solicitud.


## 5. Endpoints de la API

Todas las rutas parten de la URL base del servicio desplegado. Los cuerpos de solicitud y respuesta están en formato JSON.

### 5.1 Usuarios — `/usuarios`

| Método | Ruta | Qué hace |
|---|---|---|
| `POST` | `/usuarios` | Registra un nuevo usuario en la plataforma. Valida que el correo no esté ya registrado y guarda la contraseña de forma segura (hash). |
| `GET` | `/usuarios/{email}` | Consulta los datos de un usuario a partir de su correo electrónico. |
| `POST` | `/usuarios/login` | Verifica el correo y la contraseña de un usuario. Si coinciden, devuelve sus datos; si no, devuelve un error de credenciales inválidas. |

**Ejemplo — Registro de usuario**
```
POST /usuarios
{
  "nombre": "María Pérez",
  "email": "maria.perez@universidad.edu",
  "password": "********",
  "rol": "ESTUDIANTE"
}
```

**Ejemplo — Inicio de sesión**
```
POST /usuarios/login
{
  "email": "maria.perez@universidad.edu",
  "password": "********"
}
```

### 5.2 Solicitudes — `/solicitudes`

| Método | Ruta | Qué hace |
|---|---|---|
| `POST` | `/solicitudes` | Crea una nueva solicitud de asignatura y horario, asociada al usuario que la crea. |
| `GET` | `/solicitudes/listar` | Devuelve todas las solicitudes existentes, sin el conteo de adhesiones. |
| `GET` | `/solicitudes/director` | Devuelve todas las solicitudes junto con el número de estudiantes sumados a cada una. Es la vista pensada para que cualquier usuario, incluyendo la dirección de carrera, vea el nivel de interés real. |
| `PUT` | `/solicitudes/{id}` | Actualiza la asignatura y/o el horario de una solicitud existente, identificada por su `id`. |
| `DELETE` | `/solicitudes/{id}` | Elimina una solicitud existente, identificada por su `id`. |
| `POST` | `/solicitudes/{solicitudId}/unirse/{usuarioId}` | Suma al usuario indicado a la solicitud indicada. Si el usuario ya estaba sumado, devuelve un error. |
| `DELETE` | `/solicitudes/{solicitudId}/salir/{usuarioId}` | Elimina la adhesión del usuario indicado a la solicitud indicada (darse de baja). |
| `GET` | `/solicitudes/misAdhesiones/{usuarioId}` | Devuelve la lista de identificadores de las solicitudes a las que un usuario específico está sumado. Permite al frontend saber a cuáles solicitudes ya pertenece. |

**Ejemplo — Crear una solicitud**
```
POST /solicitudes
{
  "asignatura": "Base de Datos II",
  "horario": "Lunes de 7:00 PM a 9:00 PM",
  "creador": { "id": 4 }
}
```

**Ejemplo — Respuesta de la vista consolidada (`/solicitudes/director`)**
```json
[
  {
    "id": 1,
    "asignatura": "Base de Datos II",
    "horario": "Lunes de 7:00 PM a 9:00 PM",
    "totalAdhesiones": 12,
    "creador": { "id": 4, "nombre": "María Pérez", "...": "..." }
  }
]
```


## 6. Variables de entorno requeridas (despliegue)

Estas variables se configuran directamente en la plataforma de despliegue (no se incluyen en el repositorio):

| Variable | Propósito |
|---|---|
| `DB_URL` | URL de conexión JDBC a la base de datos PostgreSQL |
| `DB_USERNAME` | Usuario de la base de datos |
| `DB_PASSWORD` | Contraseña de la base de datos |
| `PORT` | Puerto en el que se expone el servicio (asignado por la plataforma de despliegue) |


## 7. Alcance actual y próximos pasos

Este backend corresponde a un **producto mínimo viable (MVP)**, construido para validar el flujo central de la plataforma. Los siguientes puntos están identificados como mejoras necesarias antes de un despliegue a mayor escala:

- Autenticación basada en tokens (JWT) para proteger los endpoints según el rol del usuario.
- Códigos de respuesta HTTP más específicos ante errores de negocio (por ejemplo, `409 Conflict` al intentar sumarse dos veces), en lugar del código genérico de error de servidor.
- Funcionalidad de comentarios o sugerencias de horario alternativo sobre una solicitud existente.
- Migración de la infraestructura de despliegue gratuita a un plan con disponibilidad continua, para uso en producción.
