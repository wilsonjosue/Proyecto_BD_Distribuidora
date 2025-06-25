//Soure packages/Interfaces/AlmacenUI.java
package Interfaces.referenciales;

import Controladores.referenciales.AlmacenController;
import Entidades.referenciales.Almacen;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AlmacenUI extends JFrame {
    private JTextField txtIde, txtNom, txtDir, txtCap, txtEstado;
    private AlmacenController controller;
    private JFrame principal;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private int modoOperacion = 0;

    public AlmacenUI(JFrame principal) {
        this.principal = principal;
        this.controller = new AlmacenController(Conexion.conectar());

        setTitle("Gestión de Almacenes");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(5, 2));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Almacén"));

        txtIde = new JTextField();
        txtNom = new JTextField();
        txtDir = new JTextField();
        txtCap = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        panelForm.add(new JLabel("ID:")); panelForm.add(txtIde);
        panelForm.add(new JLabel("Nombre:")); panelForm.add(txtNom);
        panelForm.add(new JLabel("Dirección:")); panelForm.add(txtDir);
        panelForm.add(new JLabel("Capacidad:")); panelForm.add(txtCap);
        panelForm.add(new JLabel("Estado:")); panelForm.add(txtEstado);

        add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Dirección", "Capacidad", "Estado"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4));
        String[] acciones = {"Adicionar", "Modificar", "Eliminar", "Inactivar", "Reactivar", "Actualizar", "Cancelar", "Salir"};
        JButton[] botones = new JButton[acciones.length];

        for (int i = 0; i < acciones.length; i++) {
            botones[i] = new JButton(acciones[i]);
            panelBotones.add(botones[i]);
        }

        add(panelBotones, BorderLayout.SOUTH);

        cargarTabla();

        botones[0].addActionListener(e -> prepararAdicionar());
        botones[1].addActionListener(e -> prepararModificar());
        botones[2].addActionListener(e -> prepararCambioEstado("*"));
        botones[3].addActionListener(e -> prepararCambioEstado("I"));
        botones[4].addActionListener(e -> prepararCambioEstado("A"));
        botones[5].addActionListener(e -> ejecutarOperacion());
        botones[6].addActionListener(e -> limpiarFormulario());
        botones[7].addActionListener(e -> { dispose(); principal.setVisible(true); });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                int fila = tabla.getSelectedRow();
                txtIde.setText(tabla.getValueAt(fila, 0).toString());
                txtNom.setText(tabla.getValueAt(fila, 1).toString());
                txtDir.setText(tabla.getValueAt(fila, 2).toString());
                txtCap.setText(tabla.getValueAt(fila, 3).toString());
                txtEstado.setText(tabla.getValueAt(fila, 4).toString());
            }
        });
    }

    private void setEditableCampos(boolean ide, boolean nom, boolean dir, boolean cap, boolean est) {
        txtIde.setEditable(ide);
        txtNom.setEditable(nom);
        txtDir.setEditable(dir);
        txtCap.setEditable(cap);
        txtEstado.setEditable(est);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Almacen> lista = controller.listar();
        for (Almacen a : lista) {
            modeloTabla.addRow(new Object[]{a.getAlmIde(), a.getAlmNom(), a.getAlmDir(), a.getAlmCap(), a.getAlmEstReg()});
        }
    }

    private void prepararAdicionar() {
        limpiarFormulario();
        txtEstado.setText("A");
        setEditableCampos(true, true, true, true, false);
        modoOperacion = 1;
    }

    private void prepararModificar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para modificar.");
            return;
        }
        modoOperacion = 2;
        setEditableCampos(false, true, true, true, false);
    }

    private void prepararCambioEstado(String nuevoEstado) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro.");
            return;
        }
        txtEstado.setText(nuevoEstado);
        modoOperacion = switch (nuevoEstado) {
            case "*" -> 3;
            case "I" -> 4;
            case "A" -> 5;
            default -> 0;
        };
        setEditableCampos(false, false, false, false, false);
    }

    private void ejecutarOperacion() {
        try {
            int id = Integer.parseInt(txtIde.getText());
            String nom = txtNom.getText().trim();
            String dir = txtDir.getText().trim();
            int cap = Integer.parseInt(txtCap.getText());
            String est = txtEstado.getText().trim();

            if (nom.isEmpty() || dir.isEmpty()) throw new IllegalArgumentException("Nombre y Dirección son obligatorios.");

            Almacen a = new Almacen(id, nom, dir, cap, est);

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(a);
                case 2 -> controller.modificar(a);
                case 3, 4, 5 -> controller.cambiarEstado(a.getAlmIde(), a.getAlmEstReg());
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
        txtNom.setText("");
        txtDir.setText("");
        txtCap.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true, true, true, true, false);
    }
}
