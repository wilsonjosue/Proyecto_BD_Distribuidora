//Soure packages/Controladores.maestras/ClienteController.java
package Controladores.maestras;

import Entidades.maestras.Cliente;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteController extends BaseMantenimiento<Cliente> {

    public ClienteController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(Cliente c) {
        String sql = "INSERT INTO \"CLIENTE\" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getCliIde());
            stmt.setString(2, c.getCliEmp());
            stmt.setString(3, c.getCliNom());
            stmt.setString(4, c.getCliApePat());
            stmt.setString(5, c.getCliApeMat());
            stmt.setString(6, c.getCliDir());
            stmt.setString(7, c.getCliTel());
            stmt.setString(8, c.getCliCor());
            stmt.setString(9, c.getCliDep());
            stmt.setInt(10, c.getCliCatIde());
            stmt.setString(11, c.getCliEstReg());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al adicionar CLIENTE: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(Cliente c) {
        String sql = """
            UPDATE "CLIENTE" SET 
              "CliEmp"=?, "CliNom"=?, "CliApePat"=?, "CliApeMat"=?, "CliDir"=?,
              "CliTel"=?, "CliCor"=?, "CliDep"=?, "CliCatIde"=?, "CliEstReg"=?
            WHERE "CliIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getCliEmp());
            stmt.setString(2, c.getCliNom());
            stmt.setString(3, c.getCliApePat());
            stmt.setString(4, c.getCliApeMat());
            stmt.setString(5, c.getCliDir());
            stmt.setString(6, c.getCliTel());
            stmt.setString(7, c.getCliCor());
            stmt.setString(8, c.getCliDep());
            stmt.setInt(9, c.getCliCatIde());
            stmt.setString(10, c.getCliEstReg());
            stmt.setInt(11, c.getCliIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar CLIENTE: " + e.getMessage());
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
        String sql = "UPDATE \"CLIENTE\" SET \"CliEstReg\"=? WHERE \"CliIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado CLIENTE: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"CLIENTE\" ORDER BY \"CliIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCliIde(rs.getInt("CliIde"));
                c.setCliEmp(rs.getString("CliEmp"));
                c.setCliNom(rs.getString("CliNom"));
                c.setCliApePat(rs.getString("CliApePat"));
                c.setCliApeMat(rs.getString("CliApeMat"));
                c.setCliDir(rs.getString("CliDir"));
                c.setCliTel(rs.getString("CliTel"));
                c.setCliCor(rs.getString("CliCor"));
                c.setCliDep(rs.getString("CliDep"));
                c.setCliCatIde(rs.getInt("CliCatIde"));
                c.setCliEstReg(rs.getString("CliEstReg"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar CLIENTE: " + e.getMessage());
        }
        return lista;
    }
}
