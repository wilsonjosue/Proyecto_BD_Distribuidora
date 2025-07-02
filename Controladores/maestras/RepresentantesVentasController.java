//Soure packages/Controladores.maestras/RepresentantesVentasController.java
package Controladores.maestras;

import Entidades.maestras.RepresentantesVentas;
import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepresentantesVentasController extends BaseMantenimiento<RepresentantesVentas> {

    public RepresentantesVentasController(Connection conn) {
        super(conn);
    }

    @Override
    public boolean adicionar(RepresentantesVentas r) {
        String sql = """
            INSERT INTO "REP_VENTA" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, r.getRepVenIde());
            stmt.setString(2, r.getRepVenNom());
            stmt.setString(3, r.getRepVenApe());
            stmt.setInt(4, r.getRepVenEda());
            stmt.setInt(5, r.getRepVenOfi());
            stmt.setInt(6, r.getRepVenCar());
            stmt.setInt(7, r.getRepVenAnioCon());
            stmt.setInt(8, r.getRepVenMesCon());
            stmt.setInt(9, r.getRepVenDiaCon());
            stmt.setInt(10, r.getRepVenNumVen());
            stmt.setString(11, r.getRepVenEstReg());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al adicionar REP_VENTA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificar(RepresentantesVentas r) {
        String sql = """
            UPDATE "REP_VENTA" SET 
                "RepVenNom"=?, "RepVenApe"=?, "RepVenEda"=?, "RepVenOfi"=?, "RepVenCar"=?,
                "RepVenAnioCon"=?, "RepVenMesCon"=?, "RepVenDiaCon"=?, "RepVenNumVen"=?, "RepVenEstReg"=?
            WHERE "RepVenIde"=?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getRepVenNom());
            stmt.setString(2, r.getRepVenApe());
            stmt.setInt(3, r.getRepVenEda());
            stmt.setInt(4, r.getRepVenOfi());
            stmt.setInt(5, r.getRepVenCar());
            stmt.setInt(6, r.getRepVenAnioCon());
            stmt.setInt(7, r.getRepVenMesCon());
            stmt.setInt(8, r.getRepVenDiaCon());
            stmt.setInt(9, r.getRepVenNumVen());
            stmt.setString(10, r.getRepVenEstReg());
            stmt.setInt(11, r.getRepVenIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar REP_VENTA: " + e.getMessage());
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
        String sql = "UPDATE \"REP_VENTA\" SET \"RepVenEstReg\"=? WHERE \"RepVenIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado REP_VENTA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<RepresentantesVentas> listar() {
        List<RepresentantesVentas> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"REP_VENTA\" ORDER BY \"RepVenIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                RepresentantesVentas r = new RepresentantesVentas();
                r.setRepVenIde(rs.getInt("RepVenIde"));
                r.setRepVenNom(rs.getString("RepVenNom"));
                r.setRepVenApe(rs.getString("RepVenApe"));
                r.setRepVenEda(rs.getInt("RepVenEda"));
                r.setRepVenOfi(rs.getInt("RepVenOfi"));
                r.setRepVenCar(rs.getInt("RepVenCar"));
                r.setRepVenAnioCon(rs.getInt("RepVenAnioCon"));
                r.setRepVenMesCon(rs.getInt("RepVenMesCon"));
                r.setRepVenDiaCon(rs.getInt("RepVenDiaCon"));
                r.setRepVenNumVen(rs.getInt("RepVenNumVen"));
                r.setRepVenEstReg(rs.getString("RepVenEstReg"));
                lista.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar REP_VENTA: " + e.getMessage());
        }
        return lista;
    }
}

