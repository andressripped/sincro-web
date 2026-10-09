<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.edu.sena.sincro.modelo.Cliente" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${titulo} | Sincro</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>

<header>
    <h1>Sincro</h1>
    <p>Modulo de gestion de clientes</p>
</header>

<main>

    <h2>${titulo}</h2>

    <%
        // Si viene un cliente es porque se esta editando, si no es un registro nuevo
        Cliente cliente = (Cliente) request.getAttribute("cliente");
        boolean edicion = cliente != null;
    %>

    <form method="post" action="clientes" class="formulario">

        <input type="hidden" name="accion" value="<%= edicion ? "actualizar" : "guardar" %>">

        <% if (edicion) { %>
            <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">
        <% } %>

        <label for="nombreCompleto">Nombre completo</label>
        <input type="text" id="nombreCompleto" name="nombreCompleto" maxlength="100" required
               value="<%= edicion ? cliente.getNombreCompleto() : "" %>">

        <label for="telefonoContacto">Telefono de contacto</label>
        <input type="text" id="telefonoContacto" name="telefonoContacto" maxlength="20" required
               value="<%= edicion ? cliente.getTelefonoContacto() : "" %>">

        <label for="emailUsuario">Correo electronico</label>
        <input type="email" id="emailUsuario" name="emailUsuario" maxlength="100" required
               value="<%= edicion ? cliente.getEmailUsuario() : "" %>">

        <div class="acciones-formulario">
            <button type="submit" class="boton"><%= edicion ? "Guardar cambios" : "Registrar" %></button>
            <a class="boton-secundario" href="clientes">Cancelar</a>
        </div>

    </form>

</main>

<footer>
    <p>Sincro &middot; Centro Agroturistico de San Gil &middot; Ficha 3336228</p>
</footer>

</body>
</html>
