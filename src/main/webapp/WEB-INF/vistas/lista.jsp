<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, co.edu.sena.sincro.modelo.Cliente" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clientes | Sincro</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>

<header>
    <h1>Sincro</h1>
    <p>Modulo de gestion de clientes</p>
</header>

<main>

    <%-- El mensaje llega por la direccion despues de guardar, editar o eliminar --%>
    <% String mensaje = request.getParameter("mensaje"); %>
    <% if (mensaje != null) { %>
        <p class="mensaje"><%= mensaje %></p>
    <% } %>

    <div class="barra">
        <h2>Clientes registrados</h2>
        <a class="boton" href="clientes?accion=nuevo">Registrar cliente</a>
    </div>

    <%
        List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
    %>

    <% if (clientes == null || clientes.isEmpty()) { %>

        <p class="vacio">Todavia no hay clientes registrados.</p>

    <% } else { %>

        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Nombre completo</th>
                    <th>Telefono</th>
                    <th>Correo</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
            <% for (Cliente cliente : clientes) { %>
                <tr>
                    <td><%= cliente.getIdCliente() %></td>
                    <td><%= cliente.getNombreCompleto() %></td>
                    <td><%= cliente.getTelefonoContacto() %></td>
                    <td><%= cliente.getEmailUsuario() %></td>
                    <td class="acciones">

                        <a href="clientes?accion=editar&id=<%= cliente.getIdCliente() %>">Editar</a>

                        <form method="post" action="clientes">
                            <input type="hidden" name="accion" value="eliminar">
                            <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">
                            <button type="submit" class="enlace-rojo">Eliminar</button>
                        </form>

                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>

        <p class="total">Total: <%= request.getAttribute("total") %> clientes</p>

    <% } %>

</main>

<footer>
    <p>Sincro &middot; Centro Agroturistico de San Gil &middot; Ficha 3336228</p>
</footer>

</body>
</html>
