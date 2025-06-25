//Soure packages/bd.proyecto.distribuidora.jdbc/Conexion.java
package bd.proyecto.distribuidora.jdbc;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/Data_distribuidora01";
    private static final String USER = "postgres";
    private static final String PASS = "1218";

    public static Connection conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión exitosa.");
            return conn;
        } catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
            return null;
        }
    }
}