//Soure packages/Controladores.transacciones/PedidoCabeceraController.java
package Controladores.transacciones;

import Entidades.transacciones.PedidoCabecera;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoCabeceraController extends BaseMantenimiento<PedidoCabecera> {

    public PedidoCabeceraController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(PedidoCabecera p) {
        String sql = """
            INSERT INTO "PEDIDO_CAB" (
                "PedCabIde", "PedCabAnio", "PedCabMes", "PedCabDia",
                "PedCabMonTot", "PedCabCliIde", "PedCabRepVenIde", "PedCabTraIde", "PedCabEstReg"
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try {
            if (!existeId("CLIENTE", "CliIde", p.getPedCabCliIde()))
                throw new SQLException("Cliente no existe o está inactivo.");
            if (!existeId("REP_VENTA", "RepVenIde", p.getPedCabRepVenIde()))
                throw new SQLException("Representante de venta no existe o está inactivo.");
            if (!existeId("TRANSPORTE", "TraIde", p.getPedCabTraIde()))
                throw new SQLException("Transportista no existe o está inactivo.");

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, p.getPedCabIde());
                stmt.setInt(2, p.getPedCabAnio());
                stmt.setInt(3, p.getPedCabMes());
                stmt.setInt(4, p.getPedCabDia());
                stmt.setDouble(5, p.getPedCabMonTot());
                stmt.setInt(6, p.getPedCabCliIde());
                stmt.setInt(7, p.getPedCabRepVenIde());
                stmt.setInt(8, p.getPedCabTraIde());
                stmt.setString(9, p.getPedCabEstReg());

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
            System.out.println("Error al adicionar PEDIDO_CAB: " + e.getMessage());
            return false;
        }
    }

    private boolean existeId(String tabla, String columna, int valor) throws SQLException {
        String estadoCol = switch (tabla) {
            case "CLIENTE" -> "CliEstReg";
            case "REP_VENTA" -> "RepVenEstReg";
            case "TRANSPORTE" -> "TraEstReg";
            default -> "EstReg";
        };

        String sql = "SELECT 1 FROM \"" + tabla + "\" WHERE \"" + columna + "\"=? AND \"" + estadoCol + "\"='A'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);
            return stmt.executeQuery().next();
        }
    }

    @Override
    public boolean modificar(PedidoCabecera p) {
        String sql = """
            UPDATE "PEDIDO_CAB" SET
                "PedCabAnio"=?, "PedCabMes"=?, "PedCabDia"=?, "PedCabMonTot"=?,
                "PedCabCliIde"=?, "PedCabRepVenIde"=?, "PedCabTraIde"=?, "PedCabEstReg"=?
            WHERE "PedCabIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getPedCabAnio());
            stmt.setInt(2, p.getPedCabMes());
            stmt.setInt(3, p.getPedCabDia());
            stmt.setDouble(4, p.getPedCabMonTot());
            stmt.setInt(5, p.getPedCabCliIde());
            stmt.setInt(6, p.getPedCabRepVenIde());
            stmt.setInt(7, p.getPedCabTraIde());
            stmt.setString(8, p.getPedCabEstReg());
            stmt.setInt(9, p.getPedCabIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar PEDIDO_CAB: " + e.getMessage());
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
        String sql = "UPDATE \"PEDIDO_CAB\" SET \"PedCabEstReg\"=? WHERE \"PedCabIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado PEDIDO_CAB: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<PedidoCabecera> listar() {
        List<PedidoCabecera> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"PEDIDO_CAB\" ORDER BY \"PedCabIde\"";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PedidoCabecera p = new PedidoCabecera(
                    rs.getInt("PedCabIde"),
                    rs.getInt("PedCabAnio"),
                    rs.getInt("PedCabMes"),
                    rs.getInt("PedCabDia"),
                    rs.getDouble("PedCabMonTot"),
                    rs.getInt("PedCabCliIde"),
                    rs.getInt("PedCabRepVenIde"),
                    rs.getInt("PedCabTraIde"),
                    rs.getString("PedCabEstReg")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar PEDIDO_CAB: " + e.getMessage());
        }
        return lista;
    }
}
