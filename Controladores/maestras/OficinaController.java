//Soure packages/Controladores.maestras/OficinaController.java
package Controladores.maestras;

import Entidades.maestras.Oficina;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OficinaController extends BaseMantenimiento<Oficina> {

    public OficinaController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(Oficina o) {
        String sql = "INSERT INTO \"OFICINA\" VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, o.getOfiIde());
            stmt.setString(2, o.getOfiCiu());
            stmt.setString(3, o.getOfiReg());
            stmt.setString(4, o.getOfiDirn());
            stmt.setDouble(5, o.getOfiObjPro());
            stmt.setDouble(6, o.getOfiVenRea());
            stmt.setString(7, o.getOfiEstReg());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al adicionar OFICINA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(Oficina o) {
        String sql = """
            UPDATE "OFICINA" SET 
              "OfiCiu"=?, "OfiReg"=?, "OfiDirn"=?, 
              "OfiObjPro"=?, "OfiVenRea"=?, "OfiEstReg"=?
            WHERE "OfiIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, o.getOfiCiu());
            stmt.setString(2, o.getOfiReg());
            stmt.setString(3, o.getOfiDirn());
            stmt.setDouble(4, o.getOfiObjPro());
            stmt.setDouble(5, o.getOfiVenRea());
            stmt.setString(6, o.getOfiEstReg());
            stmt.setInt(7, o.getOfiIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar OFICINA: " + e.getMessage());
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
        String sql = "UPDATE \"OFICINA\" SET \"OfiEstReg\"=? WHERE \"OfiIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado OFICINA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Oficina> listar() {
        List<Oficina> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"OFICINA\" ORDER BY \"OfiIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Oficina o = new Oficina(
                    rs.getInt("OfiIde"),
                    rs.getString("OfiCiu"),
                    rs.getString("OfiReg"),
                    rs.getString("OfiDirn"),
                    rs.getDouble("OfiObjPro"),
                    rs.getDouble("OfiVenRea"),
                    rs.getString("OfiEstReg")
                );
                lista.add(o);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar OFICINA: " + e.getMessage());
        }
        return lista;
    }
}
