//Soure packages/Controladores.transacciones/PagoController.java
package Controladores.transacciones;

import Entidades.transacciones.Pago;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoController extends BaseMantenimiento<Pago> {

    public PagoController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(Pago p) {
        String sql = """
            INSERT INTO "PAGO" (
                "PagIde", "PagAnio", "PagMes", "PagDia", 
                "PagMon", "PagFacIde", "PagForPagIde", "PagoForEstReg"
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try {
            // Validación de claves foráneas
            if (!existeId("FACTURA", "FacIde", p.getPagFacIde()))
                throw new SQLException("Factura no existe o está inactiva.");
            if (!existeId("FORMA_PAGO", "ForPagIde", p.getPagForPagIde()))
                throw new SQLException("Forma de pago no existe o está inactiva.");

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, p.getPagIde());
                stmt.setInt(2, p.getPagAnio());
                stmt.setInt(3, p.getPagMes());
                stmt.setInt(4, p.getPagDia());
                stmt.setDouble(5, p.getPagMon());
                stmt.setInt(6, p.getPagFacIde());
                stmt.setInt(7, p.getPagForPagIde());
                stmt.setString(8, p.getPagoForEstReg());

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
            System.out.println("Error al adicionar PAGO: " + e.getMessage());
            return false;
        }
    }

    private boolean existeId(String tabla, String columna, int valor) throws SQLException {
        String estadoCol = switch (tabla) {
            case "FACTURA" -> "FacEstReg";
            case "FORMA_PAGO" -> "ForPagEstReg";
            default -> "EstReg";
        };

        String sql = "SELECT 1 FROM \"" + tabla + "\" WHERE \"" + columna + "\"=? AND \"" + estadoCol + "\"='A'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);
            return stmt.executeQuery().next();
        }
    }

    @Override
    public boolean modificar(Pago p) {
        String sql = """
            UPDATE "PAGO" SET 
                "PagAnio"=?, "PagMes"=?, "PagDia"=?, "PagMon"=?, 
                "PagFacIde"=?, "PagForPagIde"=?, "PagoForEstReg"=?
            WHERE "PagIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getPagAnio());
            stmt.setInt(2, p.getPagMes());
            stmt.setInt(3, p.getPagDia());
            stmt.setDouble(4, p.getPagMon());
            stmt.setInt(5, p.getPagFacIde());
            stmt.setInt(6, p.getPagForPagIde());
            stmt.setString(7, p.getPagoForEstReg());
            stmt.setInt(8, p.getPagIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar PAGO: " + e.getMessage());
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
        String sql = "UPDATE \"PAGO\" SET \"PagoForEstReg\"=? WHERE \"PagIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado PAGO: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Pago> listar() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"PAGO\" ORDER BY \"PagIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pago p = new Pago(
                    rs.getInt("PagIde"),
                    rs.getInt("PagAnio"),
                    rs.getInt("PagMes"),
                    rs.getInt("PagDia"),
                    rs.getDouble("PagMon"),
                    rs.getInt("PagFacIde"),
                    rs.getInt("PagForPagIde"),
                    rs.getString("PagoForEstReg")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar PAGOS: " + e.getMessage());
        }
        return lista;
    }
}
