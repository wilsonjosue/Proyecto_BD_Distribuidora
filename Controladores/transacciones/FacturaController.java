//Soure packages/Controladores.transacciones/FacturaController.java
package Controladores.transacciones;

import Entidades.transacciones.Factura;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaController extends BaseMantenimiento<Factura> {

    public FacturaController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(Factura f) {
        String sql = """
            INSERT INTO "FACTURA" (
                "FacIde", "FacAnio", "FacMes", "FacDia",
                "FacMonImp", "FacMonTot", "FacTipImp", "FacEstReg"
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, f.getFacIde());
                stmt.setInt(2, f.getFacAnio());
                stmt.setInt(3, f.getFacMes());
                stmt.setInt(4, f.getFacDia());
                stmt.setDouble(5, f.getFacMonImp());
                stmt.setDouble(6, f.getFacMonTot());
                stmt.setString(7, f.getFacTipImp());
                stmt.setString(8, f.getFacEstReg());

                int rows = stmt.executeUpdate();
                conn.commit();
                return rows > 0;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al adicionar FACTURA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(Factura f) {
        String sql = """
            UPDATE "FACTURA" SET
                "FacAnio"=?, "FacMes"=?, "FacDia"=?,
                "FacMonImp"=?, "FacMonTot"=?, "FacTipImp"=?, "FacEstReg"=?
            WHERE "FacIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getFacAnio());
            stmt.setInt(2, f.getFacMes());
            stmt.setInt(3, f.getFacDia());
            stmt.setDouble(4, f.getFacMonImp());
            stmt.setDouble(5, f.getFacMonTot());
            stmt.setString(6, f.getFacTipImp());
            stmt.setString(7, f.getFacEstReg());
            stmt.setInt(8, f.getFacIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar FACTURA: " + e.getMessage());
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
        String sql = "UPDATE \"FACTURA\" SET \"FacEstReg\"=? WHERE \"FacIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado FACTURA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Factura> listar() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"FACTURA\" ORDER BY \"FacIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Factura f = new Factura(
                    rs.getInt("FacIde"),
                    rs.getInt("FacAnio"),
                    rs.getInt("FacMes"),
                    rs.getInt("FacDia"),
                    rs.getDouble("FacMonImp"),
                    rs.getDouble("FacMonTot"),
                    rs.getString("FacTipImp"),
                    rs.getString("FacEstReg")
                );
                lista.add(f);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar FACTURA: " + e.getMessage());
        }
        return lista;
    }
}
