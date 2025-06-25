//Soure packages/Interfaces/FormaPagoUI.java
package Interfaces.referenciales;
import Controladores.referenciales.FormaPagoController;
import Entidades.referenciales.FormaPago;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class FormaPagoUI extends JFrame {
    private JTextField txtIde, txtDescripcion, txtEstado;
    private FormaPagoController controller;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private int modoOperacion = 0;
    private JFrame principal;

    public FormaPagoUI(JFrame principal) {
        this.principal = principal;
        this.controller = new FormaPagoController(Conexion.conectar());

        setTitle("Gestión de Formas de Pago");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(3, 2));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de Forma de Pago"));

        txtIde = new JTextField();
        txtDescripcion = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        panelForm.add(new JLabel("ID:")); panelForm.add(txtIde);
        panelForm.add(new JLabel("Descripción:")); panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("Estado:")); panelForm.add(txtEstado);

        add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Descripción", "Estado"}, 0);
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

        panelBotones.add(btnAdicionar); panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar); panelBotones.add(btnInactivar);
        panelBotones.add(btnReactivar); panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar); panelBotones.add(btnSalir);

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
                txtDescripcion.setText(tabla.getValueAt(fila, 1).toString());
                txtEstado.setText(tabla.getValueAt(fila, 2).toString());
            }
        });
    }

    private void setEditableCampos(boolean id, boolean descripcion, boolean estado) {
        txtIde.setEditable(id);
        txtDescripcion.setEditable(descripcion);
        txtEstado.setEditable(estado);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<FormaPago> lista = controller.listar();
        for (FormaPago f : lista) {
            modeloTabla.addRow(new Object[]{
                    f.getForPagIde(),
                    f.getForPagDes(),
                    f.getForPagEstReg()
            });
        }
    }

    private void prepararAdicionar() {
        limpiarFormulario();
        txtEstado.setText("A");
        setEditableCampos(true, true, false);
        modoOperacion = 1;
    }

    private void prepararModificar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro.");
            return;
        }
        setEditableCampos(false, true, false);
        modoOperacion = 2;
    }

    private void prepararCambioEstado(String estado) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro.");
            return;
        }
        txtEstado.setText(estado);
        setEditableCampos(false, false, false);
        modoOperacion = switch (estado) {
            case "*" -> 3;
            case "I" -> 4;
            case "A" -> 5;
            default -> 0;
        };
    }

    private void ejecutarOperacion() {
        try {
            int id = Integer.parseInt(txtIde.getText().trim());
            String descripcion = txtDescripcion.getText().trim();
            String estado = txtEstado.getText().trim();

            FormaPago f = new FormaPago(id, descripcion, estado);
            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(f);
                case 2 -> controller.modificar(f);
                case 3, 4, 5 -> controller.cambiarEstado(f.getForPagIde(), f.getForPagEstReg());
                default -> false;
            };

            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación exitosa.");
                cargarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Error en la operación.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtIde.setText("");
        txtDescripcion.setText("");
        txtEstado.setText("A");
        setEditableCampos(true, true, false);
        modoOperacion = 0;
    }
}
