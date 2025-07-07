//Soure packages/Interfaces.transacciones/TransaccionInventarioUI.java
package Interfaces.transacciones;

import Controladores.transacciones.TransaccionInventarioController;
import Entidades.transacciones.TransaccionInventario;
import Interfaces.MainMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class TransaccionInventarioUI extends JFrame {
    private JTextField txtIde, txtProIde, txtTipTraIde, txtAlmIde, txtRepVenIde;
    private JTextField txtAnio, txtMes, txtDia, txtCan, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private TransaccionInventarioController controller;
    private MainMenu principal;
    private int modoOperacion = 0;

    public TransaccionInventarioUI(MainMenu principal, Connection conn) {
        this.principal = principal;
        this.controller = new TransaccionInventarioController(conn);
        setTitle("Mantenimiento de Transacciones de Inventario");
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        cargarTabla();
    }

    private void initUI() {
        JPanel pnlCampos = new JPanel(new GridLayout(5, 4));
        pnlCampos.setBorder(BorderFactory.createTitledBorder("Datos de Transacción"));

        txtIde = new JTextField();
        txtProIde = new JTextField();
        txtTipTraIde = new JTextField();
        txtAlmIde = new JTextField();
        txtRepVenIde = new JTextField();
        txtAnio = new JTextField();
        txtMes = new JTextField();
        txtDia = new JTextField();
        txtCan = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        pnlCampos.add(new JLabel("ID:")); pnlCampos.add(txtIde);
        pnlCampos.add(new JLabel("Producto ID:")); pnlCampos.add(txtProIde);
        pnlCampos.add(new JLabel("Tipo Transacción ID:")); pnlCampos.add(txtTipTraIde);
        pnlCampos.add(new JLabel("Almacén ID:")); pnlCampos.add(txtAlmIde);
        pnlCampos.add(new JLabel("Representante ID:")); pnlCampos.add(txtRepVenIde);
        pnlCampos.add(new JLabel("Año:")); pnlCampos.add(txtAnio);
        pnlCampos.add(new JLabel("Mes:")); pnlCampos.add(txtMes);
        pnlCampos.add(new JLabel("Día:")); pnlCampos.add(txtDia);
        pnlCampos.add(new JLabel("Cantidad:")); pnlCampos.add(txtCan);
        pnlCampos.add(new JLabel("Estado:")); pnlCampos.add(txtEstado);

        add(pnlCampos, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Producto", "TipoTrans", "Almacén", "Rep. Venta",
            "Año", "Mes", "Día", "Cantidad", "Estado"
        }, 0);
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
                txtProIde.setText(tabla.getValueAt(fila, 1).toString());
                txtTipTraIde.setText(tabla.getValueAt(fila, 2).toString());
                txtAlmIde.setText(tabla.getValueAt(fila, 3).toString());
                txtRepVenIde.setText(tabla.getValueAt(fila, 4).toString());
                txtAnio.setText(tabla.getValueAt(fila, 5).toString());
                txtMes.setText(tabla.getValueAt(fila, 6).toString());
                txtDia.setText(tabla.getValueAt(fila, 7).toString());
                txtCan.setText(tabla.getValueAt(fila, 8).toString());
                txtEstado.setText(tabla.getValueAt(fila, 9).toString());
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
            TransaccionInventario t = new TransaccionInventario(
                Integer.parseInt(txtIde.getText()),
                Integer.parseInt(txtProIde.getText()),
                Integer.parseInt(txtTipTraIde.getText()),
                Integer.parseInt(txtAlmIde.getText()),
                Integer.parseInt(txtRepVenIde.getText()),
                Integer.parseInt(txtAnio.getText()),
                Integer.parseInt(txtMes.getText()),
                Integer.parseInt(txtDia.getText()),
                Integer.parseInt(txtCan.getText()),
                txtEstado.getText().trim()
            );

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(t);
                case 2 -> controller.modificar(t);
                case 3, 4, 5 -> controller.cambiarEstado(t.getTraInvIde(), t.getTraInvEstReg());
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
        List<TransaccionInventario> lista = controller.listar();
        for (TransaccionInventario t : lista) {
            modeloTabla.addRow(new Object[]{
                t.getTraInvIde(), t.getTraInvProIde(), t.getTraInvTipTraIde(), t.getTraInvAlmIde(),
                t.getTraInvRepVenIde(), t.getTraInvAnio(), t.getTraInvMes(), t.getTraInvDia(),
                t.getTraInvCan(), t.getTraInvEstReg()
            });
        }
    }

    private void limpiarFormulario() {
        txtIde.setText("");
        txtProIde.setText("");
        txtTipTraIde.setText("");
        txtAlmIde.setText("");
        txtRepVenIde.setText("");
        txtAnio.setText("");
        txtMes.setText("");
        txtDia.setText("");
        txtCan.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true);
    }

    private void setEditableCampos(boolean editable) {
        txtIde.setEditable(editable);
        txtProIde.setEditable(editable);
        txtTipTraIde.setEditable(editable);
        txtAlmIde.setEditable(editable);
        txtRepVenIde.setEditable(editable);
        txtAnio.setEditable(editable);
        txtMes.setEditable(editable);
        txtDia.setEditable(editable);
        txtCan.setEditable(editable);
    }
}
