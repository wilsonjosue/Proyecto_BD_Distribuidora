//Soure packages/Controladores/ProductoCategoriaController.java
package Controladores.referenciales;

import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;
import Entidades.referenciales.ProductoCategoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoCategoriaController extends BaseMantenimiento<ProductoCategoria> {
    public ProductoCategoriaController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(ProductoCategoria pc) {
        String sql = "INSERT INTO \"PRODUCTO_CAT\" (\"ProCatIde\", \"ProCatNom\", \"ProCatDes\", \"ProCatEstReg\") VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pc.getProCatIde());
            stmt.setString(2, pc.getProCatNom());
            stmt.setString(3, pc.getProCatDes());
            stmt.setString(4, pc.getProCatEstReg());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar categoría de producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(ProductoCategoria pc) {
        String sql = "UPDATE \"PRODUCTO_CAT\" SET \"ProCatNom\"=?, \"ProCatDes\"=?, \"ProCatEstReg\"=? WHERE \"ProCatIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pc.getProCatNom());
            stmt.setString(2, pc.getProCatDes());
            stmt.setString(3, pc.getProCatEstReg());
            stmt.setInt(4, pc.getProCatIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar categoría: " + e.getMessage());
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
        String sql = "UPDATE \"PRODUCTO_CAT\" SET \"ProCatEstReg\"=? WHERE \"ProCatIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<ProductoCategoria> listar() {
        List<ProductoCategoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"PRODUCTO_CAT\" ORDER BY \"ProCatIde\"";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ProductoCategoria pc = new ProductoCategoria();
                pc.setProCatIde(rs.getInt("ProCatIde"));
                pc.setProCatNom(rs.getString("ProCatNom"));
                pc.setProCatDes(rs.getString("ProCatDes"));
                pc.setProCatEstReg(rs.getString("ProCatEstReg"));
                lista.add(pc);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}
