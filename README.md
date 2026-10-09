# Sincro - Modulo web de gestion de clientes

Modulo web del proyecto Sincro desarrollado con servlets, paginas JSP y
conexion a MySQL mediante JDBC.

Evidencia GA7-220501096-AA2-EV02
Julian Andres Albarracin Martinez - Ficha 3336228

## Estructura

```
src/main/java/co/edu/sena/sincro/
├── Launcher.java                 arranca el servidor Tomcat embebido
├── web/ClienteServlet.java       recibe las peticiones GET y POST
├── modelo/Cliente.java           representa la tabla t_cliente
├── conexion/ConexionBD.java      conexion con MySQL
└── dao/ClienteDAO.java           operaciones sobre la base

src/main/webapp/
├── WEB-INF/vistas/lista.jsp      tabla de clientes
├── WEB-INF/vistas/formulario.jsp formulario de registro y edicion
└── css/estilos.css               hoja de estilos
```

## Peticiones que atiende el servlet

| Metodo | Direccion | Que hace |
|---|---|---|
| GET | `/clientes` | lista todos los clientes |
| GET | `/clientes?accion=nuevo` | muestra el formulario vacio |
| GET | `/clientes?accion=editar&id=1` | muestra el formulario con los datos del cliente |
| POST | `/clientes` con accion `guardar` | inserta el cliente |
| POST | `/clientes` con accion `actualizar` | actualiza el cliente |
| POST | `/clientes` con accion `eliminar` | elimina el cliente |

Despues de cada POST se redirige a un GET, para que al recargar la pagina
el navegador no vuelva a enviar el formulario.

## Elementos de JSP utilizados

- Directivas `<%@ page %>` para el tipo de contenido y las importaciones
- Scriptlets `<% %>` para recorrer la lista de clientes
- Expresiones `<%= %>` para imprimir los datos de cada cliente
- Comentarios JSP `<%-- --%>`
- Lenguaje de expresiones `${titulo}` para los atributos de la peticion

## Requisitos

- JDK 21
- Maven (viene incluido en IntelliJ)
- MySQL 8.0 con la base de datos `sincro`

No requiere instalar Tomcat: el servidor viene como dependencia del proyecto.

## Ejecucion

```
mvn clean package
mvn exec:java -Dexec.mainClass=co.edu.sena.sincro.Launcher
```

O ejecutando la clase `Launcher` desde el entorno de desarrollo.
Luego abrir `http://localhost:8080/clientes` en el navegador.

## Estandares de codificacion aplicados

- Clases en PascalCase: `Cliente`, `ClienteDAO`, `ClienteServlet`
- Metodos y variables en camelCase: `consultarPorId`, `nombreCompleto`
- Paquetes en minuscula: `co.edu.sena.sincro.web`
- Constantes en mayuscula: `PUERTO`, `URL`, `USUARIO`
