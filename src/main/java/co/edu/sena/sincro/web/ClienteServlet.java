package co.edu.sena.sincro.web;

import co.edu.sena.sincro.dao.ClienteDAO;
import co.edu.sena.sincro.modelo.Cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet que atiende las peticiones del modulo de clientes.
 * Las consultas llegan por GET y los cambios en la base por POST.
 */
@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    /**
     * Atiende las peticiones de consulta: listar, mostrar formulario y editar.
     */
    @Override
    protected void doGet(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {

        peticion.setCharacterEncoding("UTF-8");
        String accion = peticion.getParameter("accion");

        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {

            case "nuevo" -> {
                peticion.setAttribute("titulo", "Registrar cliente");
                reenviar(peticion, respuesta, "formulario");
            }

            case "editar" -> {
                int id = leerEntero(peticion.getParameter("id"));
                Cliente cliente = clienteDAO.consultarPorId(id);

                if (cliente == null) {
                    respuesta.sendRedirect("clientes?mensaje=No+se+encontro+el+cliente");
                    return;
                }

                peticion.setAttribute("titulo", "Editar cliente");
                peticion.setAttribute("cliente", cliente);
                reenviar(peticion, respuesta, "formulario");
            }

            default -> {
                List<Cliente> clientes = clienteDAO.consultarTodos();
                peticion.setAttribute("clientes", clientes);
                peticion.setAttribute("total", clientes.size());
                reenviar(peticion, respuesta, "lista");
            }
        }
    }

    /**
     * Atiende las peticiones que modifican la base: guardar, actualizar y eliminar.
     */
    @Override
    protected void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {

        peticion.setCharacterEncoding("UTF-8");
        String accion = peticion.getParameter("accion");

        if (accion == null) {
            accion = "guardar";
        }

        String mensaje;

        switch (accion) {

            case "actualizar" -> {
                Cliente cliente = new Cliente(
                        leerEntero(peticion.getParameter("idCliente")),
                        peticion.getParameter("nombreCompleto"),
                        peticion.getParameter("telefonoContacto"),
                        peticion.getParameter("emailUsuario"));

                mensaje = clienteDAO.actualizar(cliente)
                        ? "Cliente actualizado correctamente"
                        : "No se pudo actualizar el cliente";
            }

            case "eliminar" -> {
                int id = leerEntero(peticion.getParameter("idCliente"));

                mensaje = clienteDAO.eliminar(id)
                        ? "Cliente eliminado correctamente"
                        : "No se pudo eliminar el cliente";
            }

            default -> {
                Cliente cliente = new Cliente(
                        peticion.getParameter("nombreCompleto"),
                        peticion.getParameter("telefonoContacto"),
                        peticion.getParameter("emailUsuario"));

                mensaje = clienteDAO.insertar(cliente)
                        ? "Cliente registrado con el id " + cliente.getIdCliente()
                        : "No se pudo registrar, revise que el correo no este repetido";
            }
        }

        // Despues de un POST se redirige a un GET para que al recargar
        // el navegador no vuelva a enviar el formulario
        respuesta.sendRedirect("clientes?mensaje=" + codificar(mensaje));
    }

    /**
     * Envia la peticion a la pagina JSP que corresponda.
     */
    private void reenviar(HttpServletRequest peticion, HttpServletResponse respuesta, String vista)
            throws ServletException, IOException {

        peticion.getRequestDispatcher("/WEB-INF/vistas/" + vista + ".jsp")
                .forward(peticion, respuesta);
    }

    /**
     * Convierte un texto a numero y devuelve cero si el texto no es valido.
     */
    private int leerEntero(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Reemplaza los espacios para poder enviar el mensaje en la direccion.
     */
    private String codificar(String texto) {
        return texto.replace(" ", "+");
    }
}
