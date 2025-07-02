//Soure packages/Interfaces.maestras/ProveedorUI.java

package Interfaces.maestras;

import Controladores.maestras.ProveedorController;
import Entidades.maestras.Proveedor;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProveedorUI extends JFrame {

    private JTextField txtIde, txtNom, txtDir, txtTel, txtCor, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private ProveedorController controller;
    private JFrame principal;
    private int modoOperacion = 0;

    public ProveedorUI(JFrame principal) {
        this.principal = principal;
        controller = new ProveedorController(Conexion.conectar());

        setTitle("Gestión de Proveedores");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(3, 4));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Proveedor"));

        txtIde = new JTextField();
        txtNom = new JTextField();
        txtDir = new JTextField();
        txtTel = new JTextField();
        txtCor = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        panelForm.add(new JLabel("ID:")); panelForm.add(txtIde);
        panelForm.add(new JLabel("Nombre:")); panelForm.add(txtNom);
        panelForm.add(new JLabel("Dirección:")); panelForm.add(txtDir);
        panelForm.add(new JLabel("Teléfono:")); panelForm.add(txtTel);
        panelForm.add(new JLabel("Correo:")); panelForm.add(txtCor);
        panelForm.add(new JLabel("Estado:")); panelForm.add(txtEstado);

        add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Dirección", "Teléfono", "Correo", "Estado"}, 0);
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
                txtNom.setText(tabla.getValueAt(fila, 1).toString());
                txtDir.setText(tabla.getValueAt(fila, 2).toString());
                txtTel.setText(tabla.getValueAt(fila, 3).toString());
                txtCor.setText(tabla.getValueAt(fila, 4).toString());
                txtEstado.setText(tabla.getValueAt(fila, 5).toString());
            }
        });

        cargarTabla();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Proveedor> lista = controller.listar();
        for (Proveedor p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getPrvIde(), p.getPrvNom(), p.getPrvDir(), p.getPrvTel(), p.getPrvCor(), p.getPrvEstReg()
            });
        }
    }

    private void setEditableCampos(boolean estado) {
        txtIde.setEditable(estado);
        txtNom.setEditable(estado);
        txtDir.setEditable(estado);
        txtTel.setEditable(estado);
        txtCor.setEditable(estado);
    }

    private void prepararAdicionar() {
        limpiarFormulario();
        txtEstado.setText("A");
        setEditableCampos(true);
        modoOperacion = 1;
    }

    private void prepararModificar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para modificar.");
            return;
        }
        setEditableCampos(true);
        txtIde.setEditable(false);
        modoOperacion = 2;
    }

    private void prepararCambioEstado(String estado) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor.");
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
            String nom = txtNom.getText().trim();
            String dir = txtDir.getText().trim();
            String tel = txtTel.getText().trim();
            String cor = txtCor.getText().trim();
            String estado = txtEstado.getText().trim();

            if (nom.isEmpty() || tel.length() > 20 || cor.length() > 100)
                throw new IllegalArgumentException("Datos inválidos. Verifique los campos.");

            Proveedor p = new Proveedor(id, nom, dir, tel, cor, estado);
            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(p);
                case 2 -> controller.modificar(p);
                case 3, 4, 5 -> controller.cambiarEstado(p.getPrvIde(), p.getPrvEstReg());
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
        txtTel.setText("");
        txtCor.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true);
    }
} 
