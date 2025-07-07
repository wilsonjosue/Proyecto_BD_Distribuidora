//Soure packages/Controladores.transacciones/FacturacionPedidoController.java
package Controladores.transacciones;

import Entidades.transacciones.FacturacionPedido;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturacionPedidoController extends BaseMantenimiento<FacturacionPedido> {

    public FacturacionPedidoController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(FacturacionPedido f) {
        String sql = """
            INSERT INTO "FACTURACION_PEDIDO" (
                "FacPedIde", "FacPedFacIde", "FacPedCabIde", "FacPedEstReg"
            ) VALUES (?, ?, ?, ?)
        """;

        try {
            // Validación de claves foráneas
            if (!existeId("FACTURA", "FacIde", f.getFacPedFacIde())) {
                throw new SQLException("ID de factura no existe.");
            }
            if (!existeId("PEDIDO_CAB", "PedCabIde", f.getFacPedCabIde())) {
                throw new SQLException("ID de pedido no existe.");
            }

            conn.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, f.getFacPedIde());
                stmt.setInt(2, f.getFacPedFacIde());
                stmt.setInt(3, f.getFacPedCabIde());
                stmt.setString(4, f.getFacPedEstReg());

                int filas = stmt.executeUpdate();
                conn.commit();
                return filas > 0;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Error al adicionar FACTURACION_PEDIDO: " + e.getMessage());
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
    public boolean modificar(FacturacionPedido f) {
        String sql = """
            UPDATE "FACTURACION_PEDIDO" SET 
                "FacPedFacIde"=?, "FacPedCabIde"=?, "FacPedEstReg"=?
            WHERE "FacPedIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getFacPedFacIde());
            stmt.setInt(2, f.getFacPedCabIde());
            stmt.setString(3, f.getFacPedEstReg());
            stmt.setInt(4, f.getFacPedIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar FACTURACION_PEDIDO: " + e.getMessage());
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
        String sql = "UPDATE \"FACTURACION_PEDIDO\" SET \"FacPedEstReg\"=? WHERE \"FacPedIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado FACTURACION_PEDIDO: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FacturacionPedido> listar() {
        List<FacturacionPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"FACTURACION_PEDIDO\" ORDER BY \"FacPedIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FacturacionPedido f = new FacturacionPedido(
                    rs.getInt("FacPedIde"),
                    rs.getInt("FacPedFacIde"),
                    rs.getInt("FacPedCabIde"),
                    rs.getString("FacPedEstReg")
                );
                lista.add(f);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar FACTURACION_PEDIDO: " + e.getMessage());
        }
        return lista;
    }
}
