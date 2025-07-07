//Soure packages/Controladores.transacciones/FacturaDetalleController.java
package Controladores.transacciones;

import Entidades.transacciones.FacturaDetalle;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDetalleController extends BaseMantenimiento<FacturaDetalle> {

    public FacturaDetalleController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(FacturaDetalle f) {
        String sql = """
            INSERT INTO "FACTURA_DET" (
                "FacDetIde", "FacDetFacIde", "FacDetProIde", "FacDetCan", "FacDetPreUni", "FacDetEstReg"
            ) VALUES (?, ?, ?, ?, ?, ?)
        """;
        try {
            if (!existeId("FACTURA", "FacIde", f.getFacDetFacIde())) {
                throw new SQLException("ID de factura no existe.");
            }
            if (!existeId("PRODUCTO", "ProIde", f.getFacDetProIde())) {
                throw new SQLException("ID de producto no existe.");
            }

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, f.getFacDetIde());
                stmt.setInt(2, f.getFacDetFacIde());
                stmt.setInt(3, f.getFacDetProIde());
                stmt.setInt(4, f.getFacDetCan());
                stmt.setDouble(5, f.getFacDetPreUni());
                stmt.setString(6, f.getFacDetEstReg());

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
            System.out.println("Error al adicionar FACTURA_DET: " + e.getMessage());
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
    public boolean modificar(FacturaDetalle f) {
        String sql = """
            UPDATE "FACTURA_DET" SET
                "FacDetFacIde"=?, "FacDetProIde"=?, "FacDetCan"=?, 
                "FacDetPreUni"=?, "FacDetEstReg"=?
            WHERE "FacDetIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getFacDetFacIde());
            stmt.setInt(2, f.getFacDetProIde());
            stmt.setInt(3, f.getFacDetCan());
            stmt.setDouble(4, f.getFacDetPreUni());
            stmt.setString(5, f.getFacDetEstReg());
            stmt.setInt(6, f.getFacDetIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar FACTURA_DET: " + e.getMessage());
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
        String sql = "UPDATE \"FACTURA_DET\" SET \"FacDetEstReg\"=? WHERE \"FacDetIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado FACTURA_DET: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FacturaDetalle> listar() {
        List<FacturaDetalle> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"FACTURA_DET\" ORDER BY \"FacDetIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FacturaDetalle f = new FacturaDetalle(
                    rs.getInt("FacDetIde"),
                    rs.getInt("FacDetFacIde"),
                    rs.getInt("FacDetProIde"),
                    rs.getInt("FacDetCan"),
                    rs.getDouble("FacDetPreUni"),
                    rs.getString("FacDetEstReg")
                );
                lista.add(f);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar FACTURA_DET: " + e.getMessage());
        }
        return lista;
    }
}
