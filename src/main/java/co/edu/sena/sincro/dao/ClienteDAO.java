package co.edu.sena.sincro.dao;

import co.edu.sena.sincro.conexion.ConexionBD;
import co.edu.sena.sincro.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Operaciones de base de datos sobre la tabla t_cliente.
 * DAO viene de Data Access Object, es la clase que habla con la base
 * para que el resto del programa no tenga que saber SQL.
 */
public class ClienteDAO {

    /**
     * Inserta un cliente nuevo y le devuelve el id que le asigno la base.
     */
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO t_cliente (nombre_completo, telefono_contacto, email_usuario) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, cliente.getNombreCompleto());
            sentencia.setString(2, cliente.getTelefonoContacto());
            sentencia.setString(3, cliente.getEmailUsuario());

            int filas = sentencia.executeUpdate();

            if (filas > 0) {
                ResultSet claves = sentencia.getGeneratedKeys();
                if (claves.next()) {
                    cliente.setIdCliente(claves.getInt(1));
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            // 1062 es el error de MySQL cuando se viola una restriccion UNIQUE
            if (e.getErrorCode() == 1062) {
                System.out.println("Ya existe un cliente con ese correo.");
            } else {
                System.out.println("Error al insertar: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Trae todos los clientes ordenados por nombre.
     */
    public List<Cliente> consultarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre_completo, telefono_contacto, email_usuario FROM t_cliente ORDER BY nombre_completo";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Cliente cliente = new Cliente(
                        resultado.getInt("id_cliente"),
                        resultado.getString("nombre_completo"),
                        resultado.getString("telefono_contacto"),
                        resultado.getString("email_usuario"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }
        return clientes;
    }

    /**
     * Busca un cliente por su id. Devuelve null si no existe.
     */
    public Cliente consultarPorId(int idCliente) {
        String sql = "SELECT id_cliente, nombre_completo, telefono_contacto, email_usuario FROM t_cliente WHERE id_cliente = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idCliente);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                return new Cliente(
                        resultado.getInt("id_cliente"),
                        resultado.getString("nombre_completo"),
                        resultado.getString("telefono_contacto"),
                        resultado.getString("email_usuario"));
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    /**
     * Actualiza los datos de un cliente existente.
     */
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE t_cliente SET nombre_completo = ?, telefono_contacto = ?, email_usuario = ? WHERE id_cliente = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cliente.getNombreCompleto());
            sentencia.setString(2, cliente.getTelefonoContacto());
            sentencia.setString(3, cliente.getEmailUsuario());
            sentencia.setInt(4, cliente.getIdCliente());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente por su id.
     */
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM t_cliente WHERE id_cliente = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idCliente);
            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            // 1451 pasa cuando el cliente tiene citas registradas
            if (e.getErrorCode() == 1451) {
                System.out.println("No se puede eliminar: el cliente tiene citas registradas.");
            } else {
                System.out.println("Error al eliminar: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Cuenta cuantos clientes hay registrados.
     */
    public int contar() {
        String sql = "SELECT COUNT(*) FROM t_cliente";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            if (resultado.next()) {
                return resultado.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al contar: " + e.getMessage());
        }
        return 0;
    }
}
