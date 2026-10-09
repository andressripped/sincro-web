package co.edu.sena.sincro.modelo;

/**
 * Representa un cliente del sistema Sincro.
 * Corresponde a la tabla t_cliente de la base de datos.
 */
public class Cliente {

    private int idCliente;
    private String nombreCompleto;
    private String telefonoContacto;
    private String emailUsuario;

    // constructor vacio, se usa cuando se van llenando los datos uno por uno
    public Cliente() {
    }

    // constructor para crear un cliente nuevo, sin id porque lo asigna la base
    public Cliente(String nombreCompleto, String telefonoContacto, String emailUsuario) {
        this.nombreCompleto = nombreCompleto;
        this.telefonoContacto = telefonoContacto;
        this.emailUsuario = emailUsuario;
    }

    // constructor completo, se usa cuando el cliente viene de la base de datos
    public Cliente(int idCliente, String nombreCompleto, String telefonoContacto, String emailUsuario) {
        this.idCliente = idCliente;
        this.nombreCompleto = nombreCompleto;
        this.telefonoContacto = telefonoContacto;
        this.emailUsuario = emailUsuario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    @Override
    public String toString() {
        return String.format("[%d] %-20s | %-12s | %s",
                idCliente, nombreCompleto, telefonoContacto, emailUsuario);
    }
}
