//Soure packages/Interfaces.transacciones/CompraDetalleUI.java
package Interfaces.transacciones;
import Controladores.transacciones.CompraDetalleController;
import Entidades.transacciones.CompraDetalle;
import Interfaces.MainMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class CompraDetalleUI extends JFrame {
     private JTextField txtIde, txtComIde, txtProIde, txtCant, txtPreUni, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private CompraDetalleController controller;
    private MainMenu principal;
    private int modoOperacion = 0;

    public CompraDetalleUI(MainMenu principal, Connection conn) {
        this.principal = principal;
        this.controller = new CompraDetalleController(conn);
        setTitle("Mantenimiento Detalle de Compras");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        cargarTabla();
    }

    private void initUI() {
        JPanel pnlCampos = new JPanel(new GridLayout(3, 4));
        pnlCampos.setBorder(BorderFactory.createTitledBorder("Datos de Compra Detalle"));

        txtIde = new JTextField();
        txtComIde = new JTextField();
        txtProIde = new JTextField();
        txtCant = new JTextField();
        txtPreUni = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        pnlCampos.add(new JLabel("ID Detalle:")); pnlCampos.add(txtIde);
        pnlCampos.add(new JLabel("ID Compra:")); pnlCampos.add(txtComIde);
        pnlCampos.add(new JLabel("ID Producto:")); pnlCampos.add(txtProIde);
        pnlCampos.add(new JLabel("Cantidad:")); pnlCampos.add(txtCant);
        pnlCampos.add(new JLabel("Precio Unitario:")); pnlCampos.add(txtPreUni);
        pnlCampos.add(new JLabel("Estado:")); pnlCampos.add(txtEstado);

        add(pnlCampos, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{
            "ID Detalle", "ID Compra", "ID Producto", "Cantidad", "Precio Unitario", "Estado"
        }, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4));
        String[] botones = {"Adicionar", "Modificar", "Eliminar", "Inactivar",
                            "Reactivar", "Actualizar", "Cancelar", "Salir"};
        JButton[] btns = new JButton[botones.length];

        for (int i = 0; i < botones.length; i++) {
            btns[i] = new JButton(botones[i]);
            panelBotones.add(btns[i]);
        }
        add(panelBotones, BorderLayout.SOUTH);

        btns[0].addActionListener(e -> prepararAdicionar());
        btns[1].addActionListener(e -> prepararModificar());
        btns[2].addActionListener(e -> prepararCambioEstado("*"));
        btns[3].addActionListener(e -> prepararCambioEstado("I"));
        btns[4].addActionListener(e -> prepararCambioEstado("A"));
        btns[5].addActionListener(e -> ejecutarOperacion());
        btns[6].addActionListener(e -> limpiarFormulario());
        btns[7].addActionListener(e -> {
            dispose();
            principal.setVisible(true);
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                int fila = tabla.getSelectedRow();
                txtIde.setText(tabla.getValueAt(fila, 0).toString());
                txtComIde.setText(tabla.getValueAt(fila, 1).toString());
                txtProIde.setText(tabla.getValueAt(fila, 2).toString());
                txtCant.setText(tabla.getValueAt(fila, 3).toString());
                txtPreUni.setText(tabla.getValueAt(fila, 4).toString());
                txtEstado.setText(tabla.getValueAt(fila, 5).toString());
            }
        });
    }

    private void prepararAdicionar() {
        limpiarFormulario();
        txtEstado.setText("A");
        setEditableCampos(true);
        modoOperacion = 1;
    }

    private void prepararModificar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para modificar.");
            return;
        }
        txtIde.setEditable(false);
        setEditableCampos(true);
        modoOperacion = 2;
    }

    private void prepararCambioEstado(String estado) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro.");
            return;
        }
        txtEstado.setText(estado);
        setEditableCampos(false);
        modoOperacion = switch (estado) {
            case "*" -> 3;
            case "I" -> 4;
            case "A" -> 5;
            default -> 0;
        };
    }

    private void ejecutarOperacion() {
        try {
            CompraDetalle d = new CompraDetalle(
                Integer.parseInt(txtIde.getText()),
                Integer.parseInt(txtComIde.getText()),
                Integer.parseInt(txtProIde.getText()),
                Integer.parseInt(txtCant.getText()),
                Double.parseDouble(txtPreUni.getText()),
                txtEstado.getText().trim()
            );

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(d);
                case 2 -> controller.modificar(d);
                case 3, 4, 5 -> controller.cambiarEstado(d.getComDetIde(), d.getComDetEstReg());
                default -> false;
            };

            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación exitosa.");
                cargarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo completar la operación.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<CompraDetalle> lista = controller.listar();
        for (CompraDetalle d : lista) {
            modeloTabla.addRow(new Object[]{
                d.getComDetIde(), d.getComDetComIde(), d.getComDetProIde(),
                d.getComDetCant(), d.getComDetPreUni(), d.getComDetEstReg()
            });
        }
    }

    private void limpiarFormulario() {
        txtIde.setText("");
        txtComIde.setText("");
        txtProIde.setText("");
        txtCant.setText("");
        txtPreUni.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true);
    }

    private void setEditableCampos(boolean editable) {
        txtIde.setEditable(editable);
        txtComIde.setEditable(editable);
        txtProIde.setEditable(editable);
        txtCant.setEditable(editable);
        txtPreUni.setEditable(editable);
    }
}
