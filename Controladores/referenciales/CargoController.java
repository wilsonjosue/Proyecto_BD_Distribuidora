//Soure packages/Controladores/CargoController.java
package Controladores.referenciales;

import bd.proyecto.distribuidora.jdbc.BaseMantenimiento;
import Entidades.referenciales.Cargo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CargoController extends BaseMantenimiento {
    private final Scanner scanner;
    private int carFlaAct = 0;
    private Cargo cargoActual;

    public CargoController(Connection conn) {
        super(conn);
        this.scanner = new Scanner(System.in);
        if (conn == null) {
            System.err.println(">>> ERROR: Conexión es null");
        } else {
        System.out.println(">>> Conexión exitosa con la base de datos");
        }
    }

    @Override
    public boolean adicionar(Object obj) {
        if (!(obj instanceof Cargo)) return false;

        Cargo cargo = (Cargo) obj;

        String sql = "INSERT INTO \"CARGO\" (\"CarIde\", \"CarCuoPre\", \"CarVenRea\", \"CarDes\", \"CarSue\", \"CarEstReg\") VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cargo.getCarIde());
            stmt.setDouble(2, cargo.getCarCuoPre());
            stmt.setDouble(3, cargo.getCarVenRea());
            stmt.setString(4, cargo.getCarDes());
            stmt.setDouble(5, cargo.getCarSue());
            stmt.setString(6, cargo.getCarEstReg());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar cargo: " + e.getMessage());
            return false;
        }
    }
    
    public boolean insertarCargo(Cargo cargo) {
        return adicionar(cargo); // Usa el método adicionar
    }
    
     @Override
    public boolean modificar(Object obj) {
        if (!(obj instanceof Cargo)) return false;
        Cargo cargo = (Cargo) obj;

        String sql = "UPDATE \"CARGO\" SET \"CarCuoPre\"=?, \"CarVenRea\"=?, \"CarDes\"=?, \"CarSue\"=?, \"CarEstReg\"=? WHERE \"CarIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, cargo.getCarCuoPre());
            stmt.setDouble(2, cargo.getCarVenRea());
            stmt.setString(3, cargo.getCarDes());
            stmt.setDouble(4, cargo.getCarSue());
            stmt.setString(5, cargo.getCarEstReg());
            stmt.setInt(6, cargo.getCarIde());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar cargo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Cargo> listar() {
        List<Cargo> lista = new ArrayList<>();
        // Agrega ORDER BY CarIde para garantizar el orden
        String sql = "SELECT * FROM \"CARGO\" ORDER BY \"CarIde\"";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cargo c = new Cargo();
                c.setCarIde(rs.getInt("CarIde"));
                c.setCarCuoPre(rs.getDouble("CarCuoPre"));
                c.setCarVenRea(rs.getDouble("CarVenRea"));
                c.setCarDes(rs.getString("CarDes"));
                c.setCarSue(rs.getDouble("CarSue"));
                c.setCarEstReg(rs.getString("CarEstReg"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar cargos: " + e.getMessage());
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

    public boolean cambiarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE \"CARGO\" SET \"CarEstReg\"=? WHERE \"CarIde\"=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado de cargo: " + e.getMessage());
            return false;
        }
    }
}
