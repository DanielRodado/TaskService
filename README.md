# TaskService

TaskService es un microservicio que forma parte de una arquitectura de microservicios diseñada para la gestión de tareas. Este servicio maneja las operaciones relacionadas con la creación, actualización, eliminación y consulta de tareas. Utiliza tecnologías modernas para garantizar un rendimiento eficiente y una experiencia de usuario óptima.

## Tecnologías Usadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Reactive Web**: Módulo de Spring que permite la programación reactiva para manejar solicitudes y respuestas de manera no bloqueante.
- **Spring Data R2DBC**: Proporciona soporte para el acceso a bases de datos reactivas en tiempo real usando R2DBC.
- **PostgreSQL**: Sistema de gestión de bases de datos relacional usado para almacenar los datos de los usuarios.
- **Spring Security**: Autenticación y autorización.
- **JWT (JSON Web Tokens)**: Mecanismo para autenticar a los usuarios de manera segura.
- **Postman**: Herramienta utilizada para probar y verificar las APIs del microservicio.

## Descripción del Servicio

### Funcionalidades

- **Creación de Tarea**: Permite crear una nueva tarea en el sistema.
- **Actualización de Tarea**: Permite modificar los detalles de una tarea existente.
- **Eliminación de Tarea**: Permite eliminar una tarea del sistema.
- **Obtención de Tarea**: Permite consultar la información de una tarea específica.
- **Listado de Tareas**: Permite obtener una lista de todas las tareas existentes en el sistema.

### Endpoints

- **POST /api/tasks**: Crea una nueva tarea.
  - **Request Body**: `TaskDTO` con los detalles de la tarea.
  - **Response**: Detalles de la tarea creada.

- **PUT /api/tasks/{id}**: Actualiza la información de una tarea existente.
  - **Request Body**: `TaskUpdateDTO` con los datos a actualizar.
  - **Response**: Detalles de la tarea actualizada.

- **DELETE /api/tasks/{id}**: Elimina una tarea del sistema.
  - **Response**: Confirmación de la eliminación.

- **GET /api/tasks/{id}**: Obtiene la información de una tarea específica.
  - **Response**: Detalles de la tarea solicitada.

- **GET /api/tasks**: Obtiene una lista de todas las tareas.
  - **Response**: Lista de tareas.

## Seguridad

La seguridad de este microservicio está gestionada de forma centralizada en el **Gateway**. Por lo tanto, el `TaskService` en sí no implementa medidas de seguridad específicas. Todas las solicitudes que llegan al `TaskService` deben ser previamente autenticadas y autorizadas a través del Gateway.

El Gateway utiliza **Spring Security** con **JWT** para autenticar y autorizar a los usuarios. Cada solicitud debe incluir un token JWT válido en el encabezado `Authorization` en el formato `Bearer <token>`. El Gateway se encarga de validar el token y aplicar las políticas de seguridad antes de enrutar las solicitudes al `TaskService`.

Para más detalles sobre la configuración de seguridad, consulta la documentación del **[Gateway Service](https://github.com/DanielRodado/GatewayService-ToDoList)**.



