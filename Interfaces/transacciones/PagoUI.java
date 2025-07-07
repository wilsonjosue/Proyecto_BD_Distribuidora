//Soure packages/Interfaces.transacciones/PagoUI.java
package Interfaces.transacciones;

import Entidades.transacciones.Pago;
import Controladores.transacciones.PagoController;
import Interfaces.MainMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;

public class PagoUI extends JFrame {
    private JTextField txtIde, txtAnio, txtMes, txtDia, txtMon, txtFacIde, txtForPagIde, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private PagoController controller;
    private MainMenu principal;
    private int modoOperacion = 0;

    public PagoUI(MainMenu principal, Connection conn) {
        this.principal = principal;
        this.controller = new PagoController(conn);
        setTitle("Mantenimiento de Pagos");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        cargarTabla();
    }

    private void initUI() {
        JPanel pnlCampos = new JPanel(new GridLayout(4, 4));
        pnlCampos.setBorder(BorderFactory.createTitledBorder("Datos de Pago"));

        txtIde = new JTextField();
        txtAnio = new JTextField();
        txtMes = new JTextField();
        txtDia = new JTextField();
        txtMon = new JTextField();
        txtFacIde = new JTextField();
        txtForPagIde = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        pnlCampos.add(new JLabel("ID Pago:")); pnlCampos.add(txtIde);
        pnlCampos.add(new JLabel("Año:")); pnlCampos.add(txtAnio);
        pnlCampos.add(new JLabel("Mes:")); pnlCampos.add(txtMes);
        pnlCampos.add(new JLabel("Día:")); pnlCampos.add(txtDia);
        pnlCampos.add(new JLabel("Monto:")); pnlCampos.add(txtMon);
        pnlCampos.add(new JLabel("ID Factura:")); pnlCampos.add(txtFacIde);
        pnlCampos.add(new JLabel("ID Forma Pago:")); pnlCampos.add(txtForPagIde);
        pnlCampos.add(new JLabel("Estado:")); pnlCampos.add(txtEstado);

        add(pnlCampos, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Año", "Mes", "Día", "Monto", "Factura", "FormaPago", "Estado"
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
                txtAnio.setText(tabla.getValueAt(fila, 1).toString());
                txtMes.setText(tabla.getValueAt(fila, 2).toString());
                txtDia.setText(tabla.getValueAt(fila, 3).toString());
                txtMon.setText(tabla.getValueAt(fila, 4).toString());
                txtFacIde.setText(tabla.getValueAt(fila, 5).toString());
                txtForPagIde.setText(tabla.getValueAt(fila, 6).toString());
                txtEstado.setText(tabla.getValueAt(fila, 7).toString());
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
            Pago p = new Pago(
                Integer.parseInt(txtIde.getText()),
                Integer.parseInt(txtAnio.getText()),
                Integer.parseInt(txtMes.getText()),
                Integer.parseInt(txtDia.getText()),
                Double.parseDouble(txtMon.getText()),
                Integer.parseInt(txtFacIde.getText()),
                Integer.parseInt(txtForPagIde.getText()),
                txtEstado.getText().trim()
            );

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(p);
                case 2 -> controller.modificar(p);
                case 3, 4, 5 -> controller.cambiarEstado(p.getPagIde(), p.getPagoForEstReg());
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
        List<Pago> lista = controller.listar();
        for (Pago p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getPagIde(), p.getPagAnio(), p.getPagMes(), p.getPagDia(),
                p.getPagMon(), p.getPagFacIde(), p.getPagForPagIde(), p.getPagoForEstReg()
            });
        }
    }

    private void limpiarFormulario() {
        txtIde.setText("");
        txtAnio.setText("");
        txtMes.setText("");
        txtDia.setText("");
        txtMon.setText("");
        txtFacIde.setText("");
        txtForPagIde.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true);
    }

    private void setEditableCampos(boolean editable) {
        txtIde.setEditable(editable);
        txtAnio.setEditable(editable);
        txtMes.setEditable(editable);
        txtDia.setEditable(editable);
        txtMon.setEditable(editable);
        txtFacIde.setEditable(editable);
        txtForPagIde.setEditable(editable);
    }
}
