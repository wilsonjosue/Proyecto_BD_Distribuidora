//Soure packages/Controladores.transacciones/TransaccionInventarioController.java
package Controladores.transacciones;

import Entidades.transacciones.TransaccionInventario;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaccionInventarioController extends BaseMantenimiento<TransaccionInventario> {

    public TransaccionInventarioController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(TransaccionInventario t) {
        String sql = """
            INSERT INTO \"TRANSACCION_INVENTARIO\" (
                \"TraInvIde\", \"TraInvProIde\", \"TraInvTipTraIde\", \"TraInvAlmIde\",
                \"TraInvRepVenIde\", \"TraInvAnio\", \"TraInvMes\", \"TraInvDia\", \"TraInvCan\", \"TraInvEstReg\"
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try {
            if (!existeId("PRODUCTO", "ProIde", t.getTraInvProIde()))
                throw new SQLException("ID de Producto no válido o inactivo.");
            if (!existeId("TIPO_TRANSACCION", "TipTraIde", t.getTraInvTipTraIde()))
                throw new SQLException("ID de TipoTransacción no válido o inactivo.");
            if (!existeId("ALMACEN", "AlmIde", t.getTraInvAlmIde()))
                throw new SQLException("ID de Almacén no válido o inactivo.");
            if (!existeId("REP_VENTA", "RepVenIde", t.getTraInvRepVenIde()))
                throw new SQLException("ID de Representante no válido o inactivo.");

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, t.getTraInvIde());
                stmt.setInt(2, t.getTraInvProIde());
                stmt.setInt(3, t.getTraInvTipTraIde());
                stmt.setInt(4, t.getTraInvAlmIde());
                stmt.setInt(5, t.getTraInvRepVenIde());
                stmt.setInt(6, t.getTraInvAnio());
                stmt.setInt(7, t.getTraInvMes());
                stmt.setInt(8, t.getTraInvDia());
                stmt.setInt(9, t.getTraInvCan());
                stmt.setString(10, t.getTraInvEstReg());

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
            System.out.println("Error al adicionar TRANSACCION_INVENTARIO: " + e.getMessage());
            return false;
        }
    }

    private boolean existeId(String tabla, String columna, int valor) throws SQLException {
        String estadoCol = switch (tabla) {
            case "PRODUCTO" -> "ProEstReg";
            case "TIPO_TRANSACCION" -> "TipTraEstReg";
            case "ALMACEN" -> "AlmEstReg";
            case "REP_VENTA" -> "RepVenEstReg";
            default -> "EstReg";
        };

        String sql = "SELECT 1 FROM \"" + tabla + "\" WHERE \"" + columna + "\"=? AND \"" + estadoCol + "\"='A'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);
            return stmt.executeQuery().next();
        }
    }

    @Override
    public boolean modificar(TransaccionInventario t) {
        String sql = """
            UPDATE \"TRANSACCION_INVENTARIO\" SET 
                \"TraInvProIde\"=?, \"TraInvTipTraIde\"=?, \"TraInvAlmIde\"=?, \"TraInvRepVenIde\"=?,
                \"TraInvAnio\"=?, \"TraInvMes\"=?, \"TraInvDia\"=?, \"TraInvCan\"=?, \"TraInvEstReg\"=?
            WHERE \"TraInvIde\"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, t.getTraInvProIde());
            stmt.setInt(2, t.getTraInvTipTraIde());
            stmt.setInt(3, t.getTraInvAlmIde());
            stmt.setInt(4, t.getTraInvRepVenIde());
            stmt.setInt(5, t.getTraInvAnio());
            stmt.setInt(6, t.getTraInvMes());
            stmt.setInt(7, t.getTraInvDia());
            stmt.setInt(8, t.getTraInvCan());
            stmt.setString(9, t.getTraInvEstReg());
            stmt.setInt(10, t.getTraInvIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar TRANSACCION_INVENTARIO: " + e.getMessage());
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
        String sql = "UPDATE \"TRANSACCION_INVENTARIO\" SET \"TraInvEstReg\"=? WHERE \"TraInvIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado TRANSACCION_INVENTARIO: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<TransaccionInventario> listar() {
        List<TransaccionInventario> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"TRANSACCION_INVENTARIO\" ORDER BY \"TraInvIde\"";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TransaccionInventario t = new TransaccionInventario(
                    rs.getInt("TraInvIde"),
                    rs.getInt("TraInvProIde"),
                    rs.getInt("TraInvTipTraIde"),
                    rs.getInt("TraInvAlmIde"),
                    rs.getInt("TraInvRepVenIde"),
                    rs.getInt("TraInvAnio"),
                    rs.getInt("TraInvMes"),
                    rs.getInt("TraInvDia"),
                    rs.getInt("TraInvCan"),
                    rs.getString("TraInvEstReg")
                );
                lista.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar TRANSACCION_INVENTARIO: " + e.getMessage());
        }
        return lista;
    }
}
