//Soure packages/Controladores/ClienteCategoriaController.java
package Controladores.referenciales;

import Entidades.referenciales.ClienteCategoria;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteCategoriaController extends BaseMantenimiento<ClienteCategoria> {

    public ClienteCategoriaController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(ClienteCategoria obj) {
        String sql = "INSERT INTO \"CLIENTE_CAT\" (\"CliCatIde\", \"CliCatDes\", \"CliCatLimMin\", \"CliCatLimMax\", \"CliCatEstReg\") VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, obj.getCliCatIde());
            stmt.setString(2, obj.getCliCatDes());
            stmt.setDouble(3, obj.getCliCatLimMin());
            stmt.setDouble(4, obj.getCliCatLimMax());
            stmt.setString(5, obj.getCliCatEstReg());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar ClienteCategoria: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(ClienteCategoria obj) {
        String sql = "UPDATE \"CLIENTE_CAT\" SET \"CliCatDes\"=?, \"CliCatLimMin\"=?, \"CliCatLimMax\"=?, \"CliCatEstReg\"=? WHERE \"CliCatIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getCliCatDes());
            stmt.setDouble(2, obj.getCliCatLimMin());
            stmt.setDouble(3, obj.getCliCatLimMax());
            stmt.setString(4, obj.getCliCatEstReg());
            stmt.setInt(5, obj.getCliCatIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar ClienteCategoria: " + e.getMessage());
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
        String sql = "UPDATE \"CLIENTE_CAT\" SET \"CliCatEstReg\"=? WHERE \"CliCatIde\"=?";
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
    public List<ClienteCategoria> listar() {
        List<ClienteCategoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"CLIENTE_CAT\" ORDER BY \"CliCatIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ClienteCategoria c = new ClienteCategoria();
                c.setCliCatIde(rs.getInt("CliCatIde"));
                c.setCliCatDes(rs.getString("CliCatDes"));
                c.setCliCatLimMin(rs.getDouble("CliCatLimMin"));
                c.setCliCatLimMax(rs.getDouble("CliCatLimMax"));
                c.setCliCatEstReg(rs.getString("CliCatEstReg"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar ClienteCategoria: " + e.getMessage());
        }
        return lista;
    }
}

