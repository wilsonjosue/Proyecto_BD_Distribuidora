//Soure packages/Interfaces.transacciones/PedidoCabeceraUI.java
package Interfaces.transacciones;

import Controladores.transacciones.PedidoCabeceraController;
import Entidades.transacciones.PedidoCabecera;
import Interfaces.MainMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;

public class PedidoCabeceraUI extends JFrame {
    private JTextField txtIde, txtAnio, txtMes, txtDia, txtMonTot, txtCliIde, txtRepVenIde, txtTraIde, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private PedidoCabeceraController controller;
    private MainMenu principal;
    private int modoOperacion = 0;

    public PedidoCabeceraUI(MainMenu principal, Connection conn) {
        this.principal = principal;
        this.controller = new PedidoCabeceraController(conn);
        setTitle("Mantenimiento de Pedidos (Cabecera)");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        cargarTabla();
    }

    private void initUI() {
        JPanel pnlCampos = new JPanel(new GridLayout(5, 4));
        pnlCampos.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));

        txtIde = new JTextField();
        txtAnio = new JTextField();
        txtMes = new JTextField();
        txtDia = new JTextField();
        txtMonTot = new JTextField();
        txtCliIde = new JTextField();
        txtRepVenIde = new JTextField();
        txtTraIde = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        pnlCampos.add(new JLabel("ID Pedido:")); pnlCampos.add(txtIde);
        pnlCampos.add(new JLabel("Año:")); pnlCampos.add(txtAnio);
        pnlCampos.add(new JLabel("Mes:")); pnlCampos.add(txtMes);
        pnlCampos.add(new JLabel("Día:")); pnlCampos.add(txtDia);
        pnlCampos.add(new JLabel("Monto Total:")); pnlCampos.add(txtMonTot);
        pnlCampos.add(new JLabel("ID Cliente:")); pnlCampos.add(txtCliIde);
        pnlCampos.add(new JLabel("ID Vendedor:")); pnlCampos.add(txtRepVenIde);
        pnlCampos.add(new JLabel("ID Transporte:")); pnlCampos.add(txtTraIde);
        pnlCampos.add(new JLabel("Estado:")); pnlCampos.add(txtEstado);

        add(pnlCampos, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Año", "Mes", "Día", "Monto Total", "Cliente", "Vendedor", "Transporte", "Estado"
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
                txtMonTot.setText(tabla.getValueAt(fila, 4).toString());
                txtCliIde.setText(tabla.getValueAt(fila, 5).toString());
                txtRepVenIde.setText(tabla.getValueAt(fila, 6).toString());
                txtTraIde.setText(tabla.getValueAt(fila, 7).toString());
                txtEstado.setText(tabla.getValueAt(fila, 8).toString());
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
            PedidoCabecera p = new PedidoCabecera(
                Integer.parseInt(txtIde.getText()),
                Integer.parseInt(txtAnio.getText()),
                Integer.parseInt(txtMes.getText()),
                Integer.parseInt(txtDia.getText()),
                Double.parseDouble(txtMonTot.getText()),
                Integer.parseInt(txtCliIde.getText()),
                Integer.parseInt(txtRepVenIde.getText()),
                Integer.parseInt(txtTraIde.getText()),
                txtEstado.getText().trim()
            );

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(p);
                case 2 -> controller.modificar(p);
                case 3, 4, 5 -> controller.cambiarEstado(p.getPedCabIde(), p.getPedCabEstReg());
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
        List<PedidoCabecera> lista = controller.listar();
        for (PedidoCabecera p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getPedCabIde(), p.getPedCabAnio(), p.getPedCabMes(), p.getPedCabDia(),
                p.getPedCabMonTot(), p.getPedCabCliIde(), p.getPedCabRepVenIde(),
                p.getPedCabTraIde(), p.getPedCabEstReg()
            });
        }
    }

    private void limpiarFormulario() {
        txtIde.setText("");
        txtAnio.setText("");
        txtMes.setText("");
        txtDia.setText("");
        txtMonTot.setText("");
        txtCliIde.setText("");
        txtRepVenIde.setText("");
        txtTraIde.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true);
    }

    private void setEditableCampos(boolean editable) {
        txtIde.setEditable(editable);
        txtAnio.setEditable(editable);
        txtMes.setEditable(editable);
        txtDia.setEditable(editable);
        txtMonTot.setEditable(editable);
        txtCliIde.setEditable(editable);
        txtRepVenIde.setEditable(editable);
        txtTraIde.setEditable(editable);
    }
}

