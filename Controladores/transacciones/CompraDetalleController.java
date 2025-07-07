//Soure packages/Controladores.transacciones/CompraDetalleConrroller.java
package Controladores.transacciones;

import Entidades.transacciones.CompraDetalle;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDetalleController extends BaseMantenimiento<CompraDetalle> {
    public CompraDetalleController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(CompraDetalle d) {
        String sql = """
            INSERT INTO "COMPRA_DET" (
                "ComDetIde", "ComDetComIde", "ComDetProIde",
                "ComDetCant", "ComDetPreUni", "ComDetEstReg"
            ) VALUES (?, ?, ?, ?, ?, ?)
        """;
        try {
            // Validaciones de integridad referencial
            if (!existeId("COMPRA", "ComIde", d.getComDetComIde())) {
                throw new SQLException("ID de compra no existe.");
            }
            if (!existeId("PRODUCTO", "ProIde", d.getComDetProIde())) {
                throw new SQLException("ID de producto no existe.");
            }

            conn.setAutoCommit(false); // Inicia transacción

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, d.getComDetIde());
                stmt.setInt(2, d.getComDetComIde());
                stmt.setInt(3, d.getComDetProIde());
                stmt.setInt(4, d.getComDetCant());
                stmt.setDouble(5, d.getComDetPreUni());
                stmt.setString(6, d.getComDetEstReg());

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
            System.out.println("Error al adicionar COMPRA_DET: " + e.getMessage());
            return false;
        }
    }

    private boolean existeId(String tabla, String columna, int valor) throws SQLException {
        String sql = "SELECT 1 FROM \"" + tabla + "\" WHERE \"" + columna + "\"=? AND \"" + columna.replace("Ide", "EstReg") + "\"='A'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    @Override
    public boolean modificar(CompraDetalle d) {
        String sql = """
            UPDATE "COMPRA_DET" SET
                "ComDetComIde"=?, "ComDetProIde"=?, "ComDetCant"=?,
                "ComDetPreUni"=?, "ComDetEstReg"=?
            WHERE "ComDetIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, d.getComDetComIde());
            stmt.setInt(2, d.getComDetProIde());
            stmt.setInt(3, d.getComDetCant());
            stmt.setDouble(4, d.getComDetPreUni());
            stmt.setString(5, d.getComDetEstReg());
            stmt.setInt(6, d.getComDetIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar COMPRA_DET: " + e.getMessage());
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
        String sql = "UPDATE \"COMPRA_DET\" SET \"ComDetEstReg\"=? WHERE \"ComDetIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado COMPRA_DET: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<CompraDetalle> listar() {
        List<CompraDetalle> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"COMPRA_DET\" ORDER BY \"ComDetIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CompraDetalle d = new CompraDetalle(
                    rs.getInt("ComDetIde"),
                    rs.getInt("ComDetComIde"),
                    rs.getInt("ComDetProIde"),
                    rs.getInt("ComDetCant"),
                    rs.getDouble("ComDetPreUni"),
                    rs.getString("ComDetEstReg")
                );
                lista.add(d);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar COMPRA_DET: " + e.getMessage());
        }
        return lista;
    }
}
