package co.edu.sena.sincro;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

/**
 * Arranca el servidor web del modulo de clientes de Sincro.
 * Usa Tomcat embebido para no tener que instalar el servidor aparte.
 */
public class Launcher {

    private static final int PUERTO = 8080;

    public static void main(String[] args) throws Exception {

        String rutaWeb = new File("src/main/webapp").getAbsolutePath();
        String rutaClases = new File("target/classes").getAbsolutePath();

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("target/tomcat");
        tomcat.setPort(PUERTO);
        tomcat.getConnector();

        Context contexto = tomcat.addWebapp("", rutaWeb);

        // Le indica a Tomcat donde estan las clases compiladas del proyecto
        WebResourceRoot recursos = new StandardRoot(contexto);
        recursos.addPreResources(new DirResourceSet(recursos, "/WEB-INF/classes", rutaClases, "/"));
        contexto.setResources(recursos);

        tomcat.start();

        System.out.println("Servidor iniciado en http://localhost:" + PUERTO + "/clientes");

        tomcat.getServer().await();
    }
}
