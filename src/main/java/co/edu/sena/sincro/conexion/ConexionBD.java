package co.edu.sena.sincro.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Administra la conexion con la base de datos MySQL del proyecto Sincro.
 */
public class ConexionBD {

    // datos de conexion, cambiar si la base esta en otro equipo o puerto
    private static final String URL = "jdbc:mysql://localhost:3306/sincro";
    private static final String USUARIO = "root";
    private static final String CLAVE = "1234";

    /**
     * Abre una conexion nueva con la base de datos.
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    /**
     * Cierra la conexion si sigue abierta.
     */
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.out.println("No se pudo cerrar la conexion: " + e.getMessage());
            }
        }
    }

    /**
     * Prueba rapida para verificar que la base responde.
     */
    public static boolean probarConexion() {
        try (Connection conexion = obtenerConexion()) {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
            return false;
        }
    }
}
