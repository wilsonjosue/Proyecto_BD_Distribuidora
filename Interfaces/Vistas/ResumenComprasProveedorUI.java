//Soure packages/Interfaces.Vistas/VistaProductoCategoriaUI.java
package Interfaces.Vistas;
import Interfaces.MainMenu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ResumenComprasProveedorUI extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private Connection conn;
    private MainMenu principal;

    public ResumenComprasProveedorUI(MainMenu principal, Connection conn) {
        this.principal = principal;
        this.conn = conn;

        setTitle("Resumen de Compras por Proveedor");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        cargarDatos();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> {
            dispose();
            principal.setVisible(true);
        });

        JPanel pnlBoton = new JPanel();
        pnlBoton.add(btnSalir);
        add(pnlBoton, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        String sql = """
            SELECT 
                prv."PrvNom",
                COUNT(c."ComIde") AS total_compras,
                SUM(c."ComMonTot") AS monto_total,
                AVG(c."ComMonTot") AS promedio_compra
            FROM 
                "COMPRA" c
            JOIN 
                "PROVEEDOR" prv ON c."ComPrvIde" = prv."PrvIde"
            WHERE 
                c."ComEstReg" = 'A'
            GROUP BY 
                prv."PrvNom"
            ORDER BY 
                monto_total DESC
        """;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // Columnas
            String[] columnas = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnas[i] = meta.getColumnName(i + 1);
            }
            modelo.setColumnIdentifiers(columnas);

            // Filas
            while (rs.next()) {
                Object[] fila = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage());
        }
    }
}