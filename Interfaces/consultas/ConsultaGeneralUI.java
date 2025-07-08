//Soure packages/Interfaces.consultas/ConsultaGeneralUI.java
package Interfaces.consultas;
  
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import Interfaces.MainMenu;

import bd.proyecto.distribuidora.jdbc.Conexion;

public class ConsultaGeneralUI extends JFrame {
    private MainMenu principal;
    private JTextField txtTabla;
    private JButton btnConsultar;
    private JTable tablaResultado;
    private DefaultTableModel modelo;
    private Connection conn;

    public ConsultaGeneralUI(MainMenu principal, Connection conn) {
        this.conn = conn;
        this.principal = principal; // AÑADIR ESTA LÍNEA
        setTitle("Consulta General de Tabla");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(new JLabel("Nombre de la tabla:"));
        txtTabla = new JTextField(20);
        panelSuperior.add(txtTabla);
        btnConsultar = new JButton("Consultar");
        panelSuperior.add(btnConsultar);

        tablaResultado = new JTable();
        JScrollPane scroll = new JScrollPane(tablaResultado);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Al final del constructor:
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalir = new JButton("Salir");
        panelInferior.add(btnSalir);
        add(panelInferior, BorderLayout.SOUTH);
        
        btnConsultar.addActionListener(e -> consultarDatos());
        btnSalir.addActionListener(e -> {
            dispose();
            principal.setVisible(true);
        }); 
    }

    private void consultarDatos() {
        String nombreTabla = txtTabla.getText().trim();
        if (nombreTabla.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre de la tabla.");
            return;
        }

        String sql = "SELECT * FROM \"" + nombreTabla + "\"";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            String[] columnas = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnas[i] = meta.getColumnName(i + 1);
            }

            modelo = new DefaultTableModel(columnas, 0);

            while (rs.next()) {
                Object[] fila = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

            tablaResultado.setModel(modelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}