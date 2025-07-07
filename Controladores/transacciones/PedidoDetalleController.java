//Soure packages/Controladores.transacciones/PedidoDetalleController.java
package Controladores.transacciones;

import Entidades.transacciones.PedidoDetalle;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDetalleController extends BaseMantenimiento<PedidoDetalle> {

    public PedidoDetalleController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(PedidoDetalle pd) {
        String sql = """
            INSERT INTO \"PEDIDO_DET\" (
                \"PedDetIde\", \"PedDetPed\", \"PedDetProIde\", \"PedDetCan\", \"PedDetPreUni\", \"PedDetEstReg\"
            ) VALUES (?, ?, ?, ?, ?, ?)
        """;

        try {
            if (!existeId("PEDIDO_CAB", "PedCabIde", pd.getPedDetPed()))
                throw new SQLException("ID de PedidoCabecera no válido o inactivo.");
            if (!existeId("PRODUCTO", "ProIde", pd.getPedDetProIde()))
                throw new SQLException("ID de Producto no válido o inactivo.");

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, pd.getPedDetIde());
                stmt.setInt(2, pd.getPedDetPed());
                stmt.setInt(3, pd.getPedDetProIde());
                stmt.setInt(4, pd.getPedDetCan());
                stmt.setDouble(5, pd.getPedDetPreUni());
                stmt.setString(6, pd.getPedDetEstReg());

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
            System.out.println("Error al adicionar PEDIDO_DET: " + e.getMessage());
            return false;
        }
    }

    private boolean existeId(String tabla, String columna, int valor) throws SQLException {
        String estadoCol = switch (tabla) {
            case "PRODUCTO" -> "ProEstReg";
            case "PEDIDO_CAB" -> "PedCabEstReg";
            default -> "EstReg";
        };

        String sql = "SELECT 1 FROM \"" + tabla + "\" WHERE \"" + columna + "\"=? AND \"" + estadoCol + "\"='A'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);
            return stmt.executeQuery().next();
        }
    }

    @Override
    public boolean modificar(PedidoDetalle pd) {
        String sql = """
            UPDATE \"PEDIDO_DET\" SET 
                \"PedDetPed\"=?, \"PedDetProIde\"=?, \"PedDetCan\"=?, \"PedDetPreUni\"=?, \"PedDetEstReg\"=?
            WHERE \"PedDetIde\"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pd.getPedDetPed());
            stmt.setInt(2, pd.getPedDetProIde());
            stmt.setInt(3, pd.getPedDetCan());
            stmt.setDouble(4, pd.getPedDetPreUni());
            stmt.setString(5, pd.getPedDetEstReg());
            stmt.setInt(6, pd.getPedDetIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar PEDIDO_DET: " + e.getMessage());
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
        String sql = "UPDATE \"PEDIDO_DET\" SET \"PedDetEstReg\"=? WHERE \"PedDetIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado PEDIDO_DET: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<PedidoDetalle> listar() {
        List<PedidoDetalle> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"PEDIDO_DET\" ORDER BY \"PedDetIde\"";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PedidoDetalle pd = new PedidoDetalle(
                    rs.getInt("PedDetIde"),
                    rs.getInt("PedDetPed"),
                    rs.getInt("PedDetProIde"),
                    rs.getInt("PedDetCan"),
                    rs.getDouble("PedDetPreUni"),
                    rs.getString("PedDetEstReg")
                );
                lista.add(pd);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar PEDIDO_DET: " + e.getMessage());
        }
        return lista;
    }
}
