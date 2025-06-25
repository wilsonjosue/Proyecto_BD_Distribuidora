//Soure packages/Interfaces/ClienteCategoriaUI.java
package Interfaces.referenciales;

import Controladores.referenciales.ClienteCategoriaController;
import Entidades.referenciales.ClienteCategoria;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteCategoriaUI extends JFrame {
    private JTextField txtIde, txtDes, txtLimMin, txtLimMax, txtEstado;
    private ClienteCategoriaController controller;
    private JFrame principal;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private int modoOperacion = 0;

    public ClienteCategoriaUI(JFrame principal) {
        this.principal = principal;
        this.controller = new ClienteCategoriaController(Conexion.conectar());

        setTitle("Gestión de Categoría de Clientes");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(5, 2));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Categoría"));

        txtIde = new JTextField();
        txtDes = new JTextField();
        txtLimMin = new JTextField();
        txtLimMax = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        panelForm.add(new JLabel("ID:")); panelForm.add(txtIde);
        panelForm.add(new JLabel("Descripción:")); panelForm.add(txtDes);
        panelForm.add(new JLabel("Límite Mínimo:")); panelForm.add(txtLimMin);
        panelForm.add(new JLabel("Límite Máximo:")); panelForm.add(txtLimMax);
        panelForm.add(new JLabel("Estado:")); panelForm.add(txtEstado);

        add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Descripción", "Límite Mín", "Límite Máx", "Estado"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnInactivar = new JButton("Inactivar");
        JButton btnReactivar = new JButton("Reactivar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnAdicionar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnInactivar);
        panelBotones.add(btnReactivar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);
        cargarTabla();

        btnAdicionar.addActionListener(e -> prepararAdicionar());
        btnModificar.addActionListener(e -> prepararModificar());
        btnEliminar.addActionListener(e -> prepararCambioEstado("*"));
        btnInactivar.addActionListener(e -> prepararCambioEstado("I"));
        btnReactivar.addActionListener(e -> prepararCambioEstado("A"));
        btnActualizar.addActionListener(e -> ejecutarOperacion());
        btnCancelar.addActionListener(e -> limpiarFormulario());
        btnSalir.addActionListener(e -> {
            this.dispose();
            principal.setVisible(true);
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                int fila = tabla.getSelectedRow();
                txtIde.setText(tabla.getValueAt(fila, 0).toString());
                txtDes.setText(tabla.getValueAt(fila, 1).toString());
                txtLimMin.setText(tabla.getValueAt(fila, 2).toString());
                txtLimMax.setText(tabla.getValueAt(fila, 3).toString());
                txtEstado.setText(tabla.getValueAt(fila, 4).toString());
            }
        });
    }

    private void setEditableCampos(boolean ide, boolean des, boolean limMin, boolean limMax, boolean estado) {
        txtIde.setEditable(ide);
        txtDes.setEditable(des);
        txtLimMin.setEditable(limMin);
        txtLimMax.setEditable(limMax);
        txtEstado.setEditable(estado);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<ClienteCategoria> lista = controller.listar();
        for (ClienteCategoria c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getCliCatIde(),
                c.getCliCatDes(),
                c.getCliCatLimMin(),
                c.getCliCatLimMax(),
                c.getCliCatEstReg()
            });
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
            double limMin = Double.parseDouble(txtLimMin.getText());
            double limMax = Double.parseDouble(txtLimMax.getText());
            String des = txtDes.getText().trim();

            if (id < 1 || limMin < 0 || limMax < 0 || des.length() > 100)
                throw new IllegalArgumentException("Datos inválidos. Verifique los campos.");

            ClienteCategoria cat = new ClienteCategoria(id, des, limMin, limMax, txtEstado.getText().trim());
            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(cat);
                case 2 -> controller.modificar(cat);
                case 3, 4, 5 -> controller.cambiarEstado(cat.getCliCatIde(), cat.getCliCatEstReg());
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
        txtDes.setText("");
        txtLimMin.setText("");
        txtLimMax.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true, true, true, true, false);
    }
}
