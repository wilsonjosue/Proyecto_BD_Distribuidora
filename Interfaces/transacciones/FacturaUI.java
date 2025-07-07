//Soure packages/Interfaces.transacciones/FacturaUI.java
package Interfaces.transacciones;

import Entidades.transacciones.Factura;
import Controladores.transacciones.FacturaController;
import Interfaces.MainMenu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class FacturaUI extends JFrame {
    private JTextField txtIde, txtAnio, txtMes, txtDia, txtMonImp, txtMonTot, txtTipImp, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private FacturaController controller;
    private MainMenu principal;
    private int modoOperacion = 0;

    public FacturaUI(MainMenu principal, Connection conn) {
        this.principal = principal;
        this.controller = new FacturaController(conn);
        setTitle("Mantenimiento de Facturas");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        cargarTabla();
    }

    private void initUI() {
        JPanel pnlCampos = new JPanel(new GridLayout(4, 4));
        pnlCampos.setBorder(BorderFactory.createTitledBorder("Datos de Factura"));

        txtIde = new JTextField();
        txtAnio = new JTextField();
        txtMes = new JTextField();
        txtDia = new JTextField();
        txtMonImp = new JTextField();
        txtMonTot = new JTextField();
        txtTipImp = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        pnlCampos.add(new JLabel("ID Factura:")); pnlCampos.add(txtIde);
        pnlCampos.add(new JLabel("Año:")); pnlCampos.add(txtAnio);
        pnlCampos.add(new JLabel("Mes:")); pnlCampos.add(txtMes);
        pnlCampos.add(new JLabel("Día:")); pnlCampos.add(txtDia);
        pnlCampos.add(new JLabel("Monto Impuesto:")); pnlCampos.add(txtMonImp);
        pnlCampos.add(new JLabel("Monto Total:")); pnlCampos.add(txtMonTot);
        pnlCampos.add(new JLabel("Tipo Impuesto:")); pnlCampos.add(txtTipImp);
        pnlCampos.add(new JLabel("Estado:")); pnlCampos.add(txtEstado);

        add(pnlCampos, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Año", "Mes", "Día", "Imp.", "Total", "Tipo Impuesto", "Estado"
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
                txtAnio.setText(tabla.getValueAt(fila, 1).toString());
                txtMes.setText(tabla.getValueAt(fila, 2).toString());
                txtDia.setText(tabla.getValueAt(fila, 3).toString());
                txtMonImp.setText(tabla.getValueAt(fila, 4).toString());
                txtMonTot.setText(tabla.getValueAt(fila, 5).toString());
                txtTipImp.setText(tabla.getValueAt(fila, 6).toString());
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
            Factura f = new Factura(
                Integer.parseInt(txtIde.getText()),
                Integer.parseInt(txtAnio.getText()),
                Integer.parseInt(txtMes.getText()),
                Integer.parseInt(txtDia.getText()),
                Double.parseDouble(txtMonImp.getText()),
                Double.parseDouble(txtMonTot.getText()),
                txtTipImp.getText().trim(),
                txtEstado.getText().trim()
            );

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(f);
                case 2 -> controller.modificar(f);
                case 3, 4, 5 -> controller.cambiarEstado(f.getFacIde(), f.getFacEstReg());
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
        List<Factura> lista = controller.listar();
        for (Factura f : lista) {
            modeloTabla.addRow(new Object[]{
                f.getFacIde(), f.getFacAnio(), f.getFacMes(), f.getFacDia(),
                f.getFacMonImp(), f.getFacMonTot(), f.getFacTipImp(), f.getFacEstReg()
            });
        }
    }

    private void limpiarFormulario() {
        txtIde.setText("");
        txtAnio.setText("");
        txtMes.setText("");
        txtDia.setText("");
        txtMonImp.setText("");
        txtMonTot.setText("");
        txtTipImp.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true);
    }

    private void setEditableCampos(boolean editable) {
        txtIde.setEditable(editable);
        txtAnio.setEditable(editable);
        txtMes.setEditable(editable);
        txtDia.setEditable(editable);
        txtMonImp.setEditable(editable);
        txtMonTot.setEditable(editable);
        txtTipImp.setEditable(editable);
    }
}
