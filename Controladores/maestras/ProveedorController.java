//Soure packages/Controladores.maestras/ProveedorController.java
package Controladores.maestras;

import Entidades.maestras.Proveedor;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorController extends BaseMantenimiento<Proveedor> {

    public ProveedorController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(Proveedor p) {
        String sql = "INSERT INTO \"PROVEEDOR\" VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getPrvIde());
            stmt.setString(2, p.getPrvNom());
            stmt.setString(3, p.getPrvDir());
            stmt.setString(4, p.getPrvTel());
            stmt.setString(5, p.getPrvCor());
            stmt.setString(6, p.getPrvEstReg());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al adicionar PROVEEDOR: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(Proveedor p) {
        String sql = """
            UPDATE "PROVEEDOR" SET 
              "PrvNom"=?, "PrvDir"=?, "PrvTel"=?, "PrvCor"=?, "PrvEstReg"=?
            WHERE "PrvIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getPrvNom());
            stmt.setString(2, p.getPrvDir());
            stmt.setString(3, p.getPrvTel());
            stmt.setString(4, p.getPrvCor());
            stmt.setString(5, p.getPrvEstReg());
            stmt.setInt(6, p.getPrvIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar PROVEEDOR: " + e.getMessage());
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
        String sql = "UPDATE \"PROVEEDOR\" SET \"PrvEstReg\"=? WHERE \"PrvIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado PROVEEDOR: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"PROVEEDOR\" ORDER BY \"PrvIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setPrvIde(rs.getInt("PrvIde"));
                p.setPrvNom(rs.getString("PrvNom"));
                p.setPrvDir(rs.getString("PrvDir"));
                p.setPrvTel(rs.getString("PrvTel"));
                p.setPrvCor(rs.getString("PrvCor"));
                p.setPrvEstReg(rs.getString("PrvEstReg"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar PROVEEDOR: " + e.getMessage());
        }
        return lista;
    }
}
