//Soure packages/Controladores/FormaPagoController.java
package Controladores.referenciales;
import Entidades.referenciales.FormaPago;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormaPagoController extends BaseMantenimiento<FormaPago> {
    public FormaPagoController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(FormaPago obj) {
        String sql = "INSERT INTO \"FORMA_PAGO\" (\"ForPagIde\", \"ForPagDes\", \"ForPagEstReg\") VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, obj.getForPagIde());
            stmt.setString(2, obj.getForPagDes());
            stmt.setString(3, obj.getForPagEstReg());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar forma de pago: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(FormaPago obj) {
        String sql = "UPDATE \"FORMA_PAGO\" SET \"ForPagDes\"=?, \"ForPagEstReg\"=? WHERE \"ForPagIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getForPagDes());
            stmt.setString(2, obj.getForPagEstReg());
            stmt.setInt(3, obj.getForPagIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar forma de pago: " + e.getMessage());
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
        String sql = "UPDATE \"FORMA_PAGO\" SET \"ForPagEstReg\"=? WHERE \"ForPagIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado de forma de pago: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FormaPago> listar() {
        List<FormaPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"FORMA_PAGO\" ORDER BY \"ForPagIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FormaPago f = new FormaPago();
                f.setForPagIde(rs.getInt("ForPagIde"));
                f.setForPagDes(rs.getString("ForPagDes"));
                f.setForPagEstReg(rs.getString("ForPagEstReg"));
                lista.add(f);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar formas de pago: " + e.getMessage());
        }
        return lista;
    }
}
