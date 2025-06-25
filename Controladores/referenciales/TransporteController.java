//Soure packages/Controladores/TransporteController.java
package Controladores.referenciales;

import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;
import Entidades.referenciales.Transporte;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransporteController extends BaseMantenimiento<Transporte> {

    public TransporteController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(Transporte t) {
        String sql = "INSERT INTO \"TRANSPORTE\" (\"TraIde\", \"TraNom_Trp\", \"TraPlaVeh\", \"TraTel\", \"TraEmpTra\", \"TraEstReg\") VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, t.getTraIde());
            stmt.setString(2, t.getTraNomTrp());
            stmt.setString(3, t.getTraPlaVeh());
            stmt.setString(4, t.getTraTel());
            stmt.setString(5, t.getTraEmpTra());
            stmt.setString(6, t.getTraEstReg());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar transporte: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(Transporte t) {
        String sql = "UPDATE \"TRANSPORTE\" SET \"TraNom_Trp\"=?, \"TraPlaVeh\"=?, \"TraTel\"=?, \"TraEmpTra\"=?, \"TraEstReg\"=? WHERE \"TraIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getTraNomTrp());
            stmt.setString(2, t.getTraPlaVeh());
            stmt.setString(3, t.getTraTel());
            stmt.setString(4, t.getTraEmpTra());
            stmt.setString(5, t.getTraEstReg());
            stmt.setInt(6, t.getTraIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar transporte: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Transporte> listar() {
        List<Transporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"TRANSPORTE\" ORDER BY \"TraIde\"";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transporte t = new Transporte(
                    rs.getInt("TraIde"),
                    rs.getString("TraNom_Trp"),
                    rs.getString("TraPlaVeh"),
                    rs.getString("TraTel"),
                    rs.getString("TraEmpTra"),
                    rs.getString("TraEstReg")
                );
                lista.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar transportes: " + e.getMessage());
        }
        return lista;
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
        String sql = "UPDATE \"TRANSPORTE\" SET \"TraEstReg\"=? WHERE \"TraIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado transporte: " + e.getMessage());
            return false;
        }
    }
}
