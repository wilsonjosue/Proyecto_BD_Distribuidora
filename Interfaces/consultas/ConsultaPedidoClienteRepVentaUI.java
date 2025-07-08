//Soure packages/Interfaces.consultas/ConsultaPedidoClienteRepVentaUI.java
package Interfaces.consultas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import Interfaces.MainMenu;

public class ConsultaPedidoClienteRepVentaUI extends JFrame {

    private JTable tablaResultado;
    private DefaultTableModel modelo;
    private Connection conn;
    private MainMenu principal;

    public ConsultaPedidoClienteRepVentaUI(MainMenu principal, Connection conn) {
        this.conn = conn;
        this.principal = principal;

        setTitle("Pedidos con Cliente y Representante");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new String[]{
            "ID Pedido", "Cliente", "Representante", "Monto Total", "Estado"
        }, 0);

        tablaResultado = new JTable(modelo);
        add(new JScrollPane(tablaResultado), BorderLayout.CENTER);

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> {
            dispose();
            principal.setVisible(true);
        });

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.add(btnSalir);
        add(panelInferior, BorderLayout.SOUTH);

        cargarDatos();
    }

    private void cargarDatos() {
        String sql = """
            SELECT pc."PedCabIde", c."CliNom", rv."RepVenNom", pc."PedCabMonTot", pc."PedCabEstReg"
            FROM "PEDIDO_CAB" pc
            JOIN "CLIENTE" c ON pc."PedCabCliIde" = c."CliIde"
            JOIN "REP_VENTA" rv ON pc."PedCabRepVenIde" = rv."RepVenIde"
            WHERE pc."PedCabEstReg" = 'A'
        """;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("PedCabIde"),
                    rs.getString("CliNom"),
                    rs.getString("RepVenNom"),
                    rs.getBigDecimal("PedCabMonTot"),
                    rs.getString("PedCabEstReg")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
}