//Soure packages/Interfaces/TransporteUI.java
package Interfaces.referenciales;

import Controladores.referenciales.TransporteController;
import Entidades.referenciales.Transporte;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransporteUI extends JFrame {
    private JTextField txtIde, txtNomTrp, txtPlaVeh, txtTel, txtEmpTra, txtEstReg;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private TransporteController controller;
    private JFrame principal;
    private int modoOperacion = 0;

    public TransporteUI(JFrame principal) {
        this.principal = principal;
        this.controller = new TransporteController(Conexion.conectar());

        setTitle("Gestión de Transporte");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(6, 2));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Transporte"));

        txtIde = new JTextField();
        txtNomTrp = new JTextField();
        txtPlaVeh = new JTextField();
        txtTel = new JTextField();
        txtEmpTra = new JTextField();
        txtEstReg = new JTextField("A");
        txtEstReg.setEditable(false);

        panelForm.add(new JLabel("ID:")); panelForm.add(txtIde);
        panelForm.add(new JLabel("Nombre Transportista:")); panelForm.add(txtNomTrp);
        panelForm.add(new JLabel("Placa Vehículo:")); panelForm.add(txtPlaVeh);
        panelForm.add(new JLabel("Teléfono:")); panelForm.add(txtTel);
        panelForm.add(new JLabel("Empresa Transporte:")); panelForm.add(txtEmpTra);
        panelForm.add(new JLabel("Estado:")); panelForm.add(txtEstReg);

        add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Placa", "Teléfono", "Empresa", "Estado"}, 0);
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

        cargarTabla();

        btns[0].addActionListener(e -> prepararAdicionar());
        btns[1].addActionListener(e -> prepararModificar());
        btns[2].addActionListener(e -> prepararCambioEstado("*"));
        btns[3].addActionListener(e -> prepararCambioEstado("I"));
        btns[4].addActionListener(e -> prepararCambioEstado("A"));
        btns[5].addActionListener(e -> ejecutarOperacion());
        btns[6].addActionListener(e -> limpiarFormulario());
        btns[7].addActionListener(e -> { this.dispose(); principal.setVisible(true); });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                int fila = tabla.getSelectedRow();
                txtIde.setText(tabla.getValueAt(fila, 0).toString());
                txtNomTrp.setText(tabla.getValueAt(fila, 1).toString());
                txtPlaVeh.setText(tabla.getValueAt(fila, 2).toString());
                txtTel.setText(tabla.getValueAt(fila, 3).toString());
                txtEmpTra.setText(tabla.getValueAt(fila, 4).toString());
                txtEstReg.setText(tabla.getValueAt(fila, 5).toString());
            }
        });
    }

    private void setEditableCampos(boolean ide, boolean nom, boolean pla, boolean tel, boolean emp, boolean est) {
        txtIde.setEditable(ide);
        txtNomTrp.setEditable(nom);
        txtPlaVeh.setEditable(pla);
        txtTel.setEditable(tel);
        txtEmpTra.setEditable(emp);
        txtEstReg.setEditable(est);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Transporte> lista = controller.listar();
        for (Transporte t : lista) {
            modeloTabla.addRow(new Object[]{
                t.getTraIde(), t.getTraNomTrp(), t.getTraPlaVeh(), t.getTraTel(), t.getTraEmpTra(), t.getTraEstReg()
            });
        }
    }

    private void prepararAdicionar() {
        limpiarFormulario();
        txtEstReg.setText("A");
        setEditableCampos(true, true, true, true, true, false);
        modoOperacion = 1;
    }

    private void prepararModificar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro.");
            return;
        }
        setEditableCampos(false, true, true, true, true, false);
        modoOperacion = 2;
    }

    private void prepararCambioEstado(String estado) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro.");
            return;
        }
        txtEstReg.setText(estado);
        modoOperacion = switch (estado) {
            case "*" -> 3;
            case "I" -> 4;
            case "A" -> 5;
            default -> 0;
        };
        setEditableCampos(false, false, false, false, false, false);
    }

    private void ejecutarOperacion() {
        try {
            int id = Integer.parseInt(txtIde.getText());
            String nom = txtNomTrp.getText().trim();
            String pla = txtPlaVeh.getText().trim();
            String tel = txtTel.getText().trim();
            String emp = txtEmpTra.getText().trim();
            String est = txtEstReg.getText().trim();

            Transporte t = new Transporte(id, nom, pla, tel, emp, est);

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(t);
                case 2 -> controller.modificar(t);
                case 3, 4, 5 -> controller.cambiarEstado(t.getTraIde(), t.getTraEstReg());
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

    private void limpiarFormulario() {
        txtIde.setText(""); txtNomTrp.setText(""); txtPlaVeh.setText("");
        txtTel.setText(""); txtEmpTra.setText(""); txtEstReg.setText("A");
        modoOperacion = 0;
        setEditableCampos(true, true, true, true, true, false);
    }
}
