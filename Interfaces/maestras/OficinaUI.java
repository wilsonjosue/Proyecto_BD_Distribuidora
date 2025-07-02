//Soure packages/Interfaces.maestras/OficinaUI.java
package Interfaces.maestras;

import Controladores.maestras.OficinaController;
import Entidades.maestras.Oficina;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OficinaUI extends JFrame {

    private JTextField txtIde, txtCiu, txtReg, txtDir, txtObjPro, txtVenRea, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private OficinaController controller;
    private JFrame principal;
    private int modoOperacion = 0;

    public OficinaUI(JFrame principal) {
        this.principal = principal;
        controller = new OficinaController(Conexion.conectar());

        setTitle("Gestión de Oficinas");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 4));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de Oficina"));

        txtIde = new JTextField();
        txtCiu = new JTextField();
        txtReg = new JTextField();
        txtDir = new JTextField();
        txtObjPro = new JTextField();
        txtVenRea = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        panelForm.add(new JLabel("ID:"));       panelForm.add(txtIde);
        panelForm.add(new JLabel("Ciudad:"));   panelForm.add(txtCiu);
        panelForm.add(new JLabel("Región:"));   panelForm.add(txtReg);
        panelForm.add(new JLabel("Dirección:"));panelForm.add(txtDir);
        panelForm.add(new JLabel("Objetivo Proyectado:")); panelForm.add(txtObjPro);
        panelForm.add(new JLabel("Venta Real:"));    panelForm.add(txtVenRea);
        panelForm.add(new JLabel("Estado:"));        panelForm.add(txtEstado);

        add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Ciudad", "Región", "Dirección", "Objetivo", "Venta", "Estado"}, 0);
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
                txtCiu.setText(tabla.getValueAt(fila, 1).toString());
                txtReg.setText(tabla.getValueAt(fila, 2).toString());
                txtDir.setText(tabla.getValueAt(fila, 3).toString());
                txtObjPro.setText(tabla.getValueAt(fila, 4).toString());
                txtVenRea.setText(tabla.getValueAt(fila, 5).toString());
                txtEstado.setText(tabla.getValueAt(fila, 6).toString());
            }
        });

        cargarTabla();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Oficina> lista = controller.listar();
        for (Oficina o : lista) {
            modeloTabla.addRow(new Object[]{
                o.getOfiIde(), o.getOfiCiu(), o.getOfiReg(), o.getOfiDirn(),
                o.getOfiObjPro(), o.getOfiVenRea(), o.getOfiEstReg()
            });
        }
    }

    private void setEditableCampos(boolean estado) {
        txtIde.setEditable(estado);
        txtCiu.setEditable(estado);
        txtReg.setEditable(estado);
        txtDir.setEditable(estado);
        txtObjPro.setEditable(estado);
        txtVenRea.setEditable(estado);
    }

    private void prepararAdicionar() {
        limpiarFormulario();
        txtEstado.setText("A");
        setEditableCampos(true);
        modoOperacion = 1;
    }

    private void prepararModificar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una oficina para modificar.");
            return;
        }
        setEditableCampos(true);
        txtIde.setEditable(false);
        modoOperacion = 2;
    }

    private void prepararCambioEstado(String estado) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una oficina.");
            return;
        }
        txtEstado.setText(estado);
        modoOperacion = switch (estado) {
            case "*" -> 3;
            case "I" -> 4;
            case "A" -> 5;
            default -> 0;
        };
        setEditableCampos(false);
    }

    private void ejecutarOperacion() {
        try {
            int id = Integer.parseInt(txtIde.getText());
            String ciu = txtCiu.getText().trim();
            String reg = txtReg.getText().trim();
            String dir = txtDir.getText().trim();
            double obj = Double.parseDouble(txtObjPro.getText());
            double ven = Double.parseDouble(txtVenRea.getText());
            String estado = txtEstado.getText().trim();

            if (ciu.isEmpty() || reg.isEmpty() || dir.isEmpty() || obj < 0 || ven < 0) {
                throw new IllegalArgumentException("Datos inválidos. Verifique los campos.");
            }

            Oficina o = new Oficina(id, ciu, reg, dir, obj, ven, estado);
            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(o);
                case 2 -> controller.modificar(o);
                case 3, 4, 5 -> controller.cambiarEstado(o.getOfiIde(), o.getOfiEstReg());
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
        txtIde.setText("");
        txtCiu.setText("");
        txtReg.setText("");
        txtDir.setText("");
        txtObjPro.setText("");
        txtVenRea.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true);
    }
}
