//Soure packages/Controladores/TipoTransaccionInventarioController.java
package Controladores.referenciales;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;
import Entidades.referenciales.TipoTransaccionInventario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoTransaccionInventarioController extends BaseMantenimiento<TipoTransaccionInventario> {
    
    public TipoTransaccionInventarioController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(TipoTransaccionInventario obj) {
        String sql = "INSERT INTO \"TIPO_TRANSACCION\" (\"TipTraIde\", \"TipTraDes\", \"TipTraEstReg\") VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, obj.getTipTraIde());
            stmt.setString(2, obj.getTipTraDes());
            stmt.setString(3, obj.getTipTraEstReg());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar tipo de transacción: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(TipoTransaccionInventario obj) {
        String sql = "UPDATE \"TIPO_TRANSACCION\" SET \"TipTraDes\"=?, \"TipTraEstReg\"=? WHERE \"TipTraIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getTipTraDes());
            stmt.setString(2, obj.getTipTraEstReg());
            stmt.setInt(3, obj.getTipTraIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar tipo de transacción: " + e.getMessage());
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

    public boolean cambiarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE \"TIPO_TRANSACCION\" SET \"TipTraEstReg\"=? WHERE \"TipTraIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al cambiar estado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<TipoTransaccionInventario> listar() {
        List<TipoTransaccionInventario> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"TIPO_TRANSACCION\" ORDER BY \"TipTraIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TipoTransaccionInventario t = new TipoTransaccionInventario();
                t.setTipTraIde(rs.getInt("TipTraIde"));
                t.setTipTraDes(rs.getString("TipTraDes"));
                t.setTipTraEstReg(rs.getString("TipTraEstReg"));
                lista.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar tipos de transacción: " + e.getMessage());
        }
        return lista;
    }
    
}
