//Soure packages/Interfaces.transacciones/FacturacionPedidoUI.java
package Interfaces.transacciones;

import Entidades.transacciones.FacturacionPedido;
import Controladores.transacciones.FacturacionPedidoController;
import Interfaces.MainMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;

public class FacturacionPedidoUI extends JFrame {
    private JTextField txtIde, txtFacIde, txtCabIde, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private FacturacionPedidoController controller;
    private MainMenu principal;
    private int modoOperacion = 0;

    public FacturacionPedidoUI(MainMenu principal, Connection conn) {
        this.principal = principal;
        this.controller = new FacturacionPedidoController(conn);
        setTitle("Mantenimiento de Facturacion Pedido");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        cargarTabla();
    }

    private void initUI() {
        JPanel pnlCampos = new JPanel(new GridLayout(2, 4));
        pnlCampos.setBorder(BorderFactory.createTitledBorder("Datos de Facturacion Pedido"));

        txtIde = new JTextField();
        txtFacIde = new JTextField();
        txtCabIde = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        pnlCampos.add(new JLabel("ID Facturación Pedido:")); pnlCampos.add(txtIde);
        pnlCampos.add(new JLabel("ID Factura:")); pnlCampos.add(txtFacIde);
        pnlCampos.add(new JLabel("ID Pedido Cabecera:")); pnlCampos.add(txtCabIde);
        pnlCampos.add(new JLabel("Estado:")); pnlCampos.add(txtEstado);

        add(pnlCampos, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Factura", "Pedido Cab", "Estado"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4));
        String[] botones = {"Adicionar", "Modificar", "Eliminar", "Inactivar", "Reactivar", "Actualizar", "Cancelar", "Salir"};
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
                txtFacIde.setText(tabla.getValueAt(fila, 1).toString());
                txtCabIde.setText(tabla.getValueAt(fila, 2).toString());
                txtEstado.setText(tabla.getValueAt(fila, 3).toString());
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
            FacturacionPedido f = new FacturacionPedido(
                Integer.parseInt(txtIde.getText()),
                Integer.parseInt(txtFacIde.getText()),
                Integer.parseInt(txtCabIde.getText()),
                txtEstado.getText().trim()
            );

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(f);
                case 2 -> controller.modificar(f);
                case 3, 4, 5 -> controller.cambiarEstado(f.getFacPedIde(), f.getFacPedEstReg());
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
        List<FacturacionPedido> lista = controller.listar();
        for (FacturacionPedido f : lista) {
            modeloTabla.addRow(new Object[]{
                f.getFacPedIde(), f.getFacPedFacIde(), f.getFacPedCabIde(), f.getFacPedEstReg()
            });
        }
    }

    private void limpiarFormulario() {
        txtIde.setText("");
        txtFacIde.setText("");
        txtCabIde.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true);
    }

    private void setEditableCampos(boolean editable) {
        txtIde.setEditable(editable);
        txtFacIde.setEditable(editable);
        txtCabIde.setEditable(editable);
    }
}
