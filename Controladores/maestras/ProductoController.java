//Soure packages/Controladores.maestras/ProductoController.java
package Controladores.maestras;

import Entidades.maestras.Producto;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoController extends BaseMantenimiento<Producto> {

    public ProductoController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(Producto p) {
        String sql = "INSERT INTO \"PRODUCTO\" (\"ProIde\", \"ProNom\", \"ProDes\", \"ProPreUni\", \"ProSto\", \"ProUniMed\", " +
                     "\"ProClaABC\", \"ProSto_Min\", \"ProSto_Max\", \"ProCatIde\", \"ProEstReg\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getProIde());
            stmt.setString(2, p.getProNom());
            stmt.setString(3, p.getProDes());
            stmt.setDouble(4, p.getProPreUni());
            stmt.setInt(5, p.getProSto());
            stmt.setString(6, p.getProUniMed());
            stmt.setString(7, p.getProClaABC());
            stmt.setInt(8, p.getProStoMin());
            stmt.setInt(9, p.getProStoMax());
            stmt.setInt(10, p.getProCatIde());
            stmt.setString(11, p.getProEstReg());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al adicionar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(Producto p) {
        String sql = "UPDATE \"PRODUCTO\" SET \"ProNom\"=?, \"ProDes\"=?, \"ProPreUni\"=?, \"ProSto\"=?, \"ProUniMed\"=?, " +
                     "\"ProClaABC\"=?, \"ProSto_Min\"=?, \"ProSto_Max\"=?, \"ProCatIde\"=?, \"ProEstReg\"=? WHERE \"ProIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getProNom());
            stmt.setString(2, p.getProDes());
            stmt.setDouble(3, p.getProPreUni());
            stmt.setInt(4, p.getProSto());
            stmt.setString(5, p.getProUniMed());
            stmt.setString(6, p.getProClaABC());
            stmt.setInt(7, p.getProStoMin());
            stmt.setInt(8, p.getProStoMax());
            stmt.setInt(9, p.getProCatIde());
            stmt.setString(10, p.getProEstReg());
            stmt.setInt(11, p.getProIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar producto: " + e.getMessage());
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
        String sql = "UPDATE \"PRODUCTO\" SET \"ProEstReg\"=? WHERE \"ProIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado del producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"PRODUCTO\" ORDER BY \"ProIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("ProIde"),
                    rs.getString("ProNom"),
                    rs.getString("ProDes"),
                    rs.getDouble("ProPreUni"),
                    rs.getInt("ProSto"),
                    rs.getString("ProUniMed"),
                    rs.getString("ProClaABC"),
                    rs.getInt("ProSto_Min"),
                    rs.getInt("ProSto_Max"),
                    rs.getInt("ProCatIde"),
                    rs.getString("ProEstReg")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }
}
