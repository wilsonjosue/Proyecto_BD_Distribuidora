//Soure packages/Controladores.transacciones/CompraController.java
package Controladores.transacciones;

import Entidades.transacciones.Compra;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraController extends BaseMantenimiento<Compra> {

    public CompraController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(Compra c) {
        String sql = """
            INSERT INTO "COMPRA" (
                "ComIde", "ComAnio", "ComMes", "ComDia", "ComMonTot",
                "ComPrvIde", "ComEmpIde", "ComEstReg"
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            // Validación de claves foráneas
            if (!existeId("PROVEEDOR", "PrvIde", c.getComPrvIde())) {
                throw new SQLException("ID de proveedor no existe.");
            }
            if (!existeId("REP_VENTA", "RepVenIde", c.getComEmpIde())) {
                throw new SQLException("ID de representante no existe.");
            }

            conn.setAutoCommit(false); // Inicia transacción

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, c.getComIde());
                stmt.setInt(2, c.getComAnio());
                stmt.setInt(3, c.getComMes());
                stmt.setInt(4, c.getComDia());
                stmt.setDouble(5, c.getComMonTot());
                stmt.setInt(6, c.getComPrvIde());
                stmt.setInt(7, c.getComEmpIde());
                stmt.setString(8, c.getComEstReg());

                int filas = stmt.executeUpdate();

                conn.commit();
                return filas > 0;
            } catch (SQLException e) {
                conn.rollback(); // Deshacer si hay error
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al adicionar COMPRA: " + e.getMessage());
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
    public boolean modificar(Compra c) {
        String sql = """
            UPDATE "COMPRA" SET 
                "ComAnio"=?, "ComMes"=?, "ComDia"=?, "ComMonTot"=?,
                "ComPrvIde"=?, "ComEmpIde"=?, "ComEstReg"=?
            WHERE "ComIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getComAnio());
            stmt.setInt(2, c.getComMes());
            stmt.setInt(3, c.getComDia());
            stmt.setDouble(4, c.getComMonTot());
            stmt.setInt(5, c.getComPrvIde());
            stmt.setInt(6, c.getComEmpIde());
            stmt.setString(7, c.getComEstReg());
            stmt.setInt( 8, c.getComIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar COMPRA: " + e.getMessage());
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
        String sql = "UPDATE \"COMPRA\" SET \"ComEstReg\"=? WHERE \"ComIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado COMPRA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Compra> listar() {
        List<Compra> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"COMPRA\" ORDER BY \"ComIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Compra c = new Compra(
                    rs.getInt("ComIde"),
                    rs.getInt("ComAnio"),
                    rs.getInt("ComMes"),
                    rs.getInt("ComDia"),
                    rs.getDouble("ComMonTot"),
                    rs.getInt("ComPrvIde"),
                    rs.getInt("ComEmpIde"),
                    rs.getString("ComEstReg")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar COMPRA: " + e.getMessage());
        }
        return lista;
    }
}
