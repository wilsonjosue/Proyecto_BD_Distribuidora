//Soure packages/Controladores/AlmacenController.java
package Controladores.referenciales;

import Entidades.referenciales.Almacen;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlmacenController extends BaseMantenimiento<Almacen> {

    public AlmacenController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(Almacen obj) {
        String sql = "INSERT INTO \"ALMACEN\" (\"AlmIde\", \"AlmNom\", \"AlmDir\", \"AlmCap\", \"AlmEstReg\") VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, obj.getAlmIde());
            stmt.setString(2, obj.getAlmNom());
            stmt.setString(3, obj.getAlmDir());
            stmt.setInt(4, obj.getAlmCap());
            stmt.setString(5, obj.getAlmEstReg());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar almacen: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(Almacen obj) {
        String sql = "UPDATE \"ALMACEN\" SET \"AlmNom\"=?, \"AlmDir\"=?, \"AlmCap\"=?, \"AlmEstReg\"=? WHERE \"AlmIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getAlmNom());
            stmt.setString(2, obj.getAlmDir());
            stmt.setInt(3, obj.getAlmCap());
            stmt.setString(4, obj.getAlmEstReg());
            stmt.setInt(5, obj.getAlmIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar almacen: " + e.getMessage());
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
        String sql = "UPDATE \"ALMACEN\" SET \"AlmEstReg\"=? WHERE \"AlmIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado del almacen: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Almacen> listar() {
        List<Almacen> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"ALMACEN\" ORDER BY \"AlmIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Almacen a = new Almacen();
                a.setAlmIde(rs.getInt("AlmIde"));
                a.setAlmNom(rs.getString("AlmNom"));
                a.setAlmDir(rs.getString("AlmDir"));
                a.setAlmCap(rs.getInt("AlmCap"));
                a.setAlmEstReg(rs.getString("AlmEstReg"));
                lista.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar almacenes: " + e.getMessage());
        }
        return lista;
    }
}
