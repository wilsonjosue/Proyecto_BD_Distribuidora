//Soure packages/Controladores.sistema/UsuarioSistemaController.java
package Controladores.sistema;

import Entidades.sistema.UsuarioSistema;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioSistemaController extends BaseMantenimiento<UsuarioSistema> {

    public UsuarioSistemaController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(UsuarioSistema u) {
        String sql = """
            INSERT INTO "USUARIO_SISTEMA" (
                "UsuSisIde", "UsuSisNom", "UsuSisCon", 
                "UsuSisRol", "UsuSisRepVen", "UsuSisEstReg"
            ) VALUES (?, ?, ?, ?, ?, ?)
        """;

        try {
            if (!existeId("REP_VENTA", "RepVenIde", u.getUsuSisRepVen()))
                throw new SQLException("ID de Representante de Ventas no válido o inactivo.");

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, u.getUsuSisIde());
                stmt.setString(2, u.getUsuSisNom());
                stmt.setString(3, u.getUsuSisCon());
                stmt.setString(4, u.getUsuSisRol());
                stmt.setInt(5, u.getUsuSisRepVen());
                stmt.setString(6, u.getUsuSisEstReg());

                int filas = stmt.executeUpdate();
                conn.commit();
                return filas > 0;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Error al adicionar USUARIO_SISTEMA: " + e.getMessage());
            return false;
        }
    }

    private boolean existeId(String tabla, String columna, int valor) throws SQLException {
        String estadoCol = switch (tabla) {
            case "REP_VENTA" -> "RepVenEstReg";
            default -> "EstReg";
        };
        String sql = "SELECT 1 FROM \"" + tabla + "\" WHERE \"" + columna + "\" = ? AND \"" + estadoCol + "\" = 'A'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);
            return stmt.executeQuery().next();
        }
    }

    @Override
    public boolean modificar(UsuarioSistema u) {
        String sql = """
            UPDATE "USUARIO_SISTEMA" SET
                "UsuSisNom"=?, "UsuSisCon"=?, "UsuSisRol"=?,
                "UsuSisRepVen"=?, "UsuSisEstReg"=?
            WHERE "UsuSisIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getUsuSisNom());
            stmt.setString(2, u.getUsuSisCon());
            stmt.setString(3, u.getUsuSisRol());
            stmt.setInt(4, u.getUsuSisRepVen());
            stmt.setString(5, u.getUsuSisEstReg());
            stmt.setInt(6, u.getUsuSisIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar USUARIO_SISTEMA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        return cambiarEstado(id, "*");
    }

    @Override
    public boolean inactivar(int id) {
        return cambiarEstado(id, "I");
    }

    @Override
    public boolean reactivar(int id) {
        return cambiarEstado(id, "A");
    }

    public boolean cambiarEstado(int id, String estado) {
        String sql = "UPDATE \"USUARIO_SISTEMA\" SET \"UsuSisEstReg\"=? WHERE \"UsuSisIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado USUARIO_SISTEMA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<UsuarioSistema> listar() {
        List<UsuarioSistema> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"USUARIO_SISTEMA\" ORDER BY \"UsuSisIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UsuarioSistema u = new UsuarioSistema(
                    rs.getInt("UsuSisIde"),
                    rs.getString("UsuSisNom"),
                    rs.getString("UsuSisCon"),
                    rs.getString("UsuSisRol"),
                    rs.getInt("UsuSisRepVen"),
                    rs.getString("UsuSisEstReg")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar USUARIO_SISTEMA: " + e.getMessage());
        }
        return lista;
    }

    // 🔐 Función de autenticación para LoginUI
    public UsuarioSistema autenticar(String usuario, String clave) {
        String sql = """
            SELECT * FROM "USUARIO_SISTEMA" 
            WHERE "UsuSisNom"=? AND "UsuSisCon"=? AND "UsuSisEstReg"='A'
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, clave);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UsuarioSistema(
                    rs.getInt("UsuSisIde"),
                    rs.getString("UsuSisNom"),
                    rs.getString("UsuSisCon"),
                    rs.getString("UsuSisRol"),
                    rs.getInt("UsuSisRepVen"),
                    rs.getString("UsuSisEstReg")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error autenticando usuario: " + e.getMessage());
        }
        return null;
    }
}
