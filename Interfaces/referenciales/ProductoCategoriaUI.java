//Soure packages/Interfaces/ProductoCategoriaUI.java
package Interfaces.referenciales;
import Controladores.referenciales.ProductoCategoriaController;
import Entidades.referenciales.ProductoCategoria;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class ProductoCategoriaUI extends JFrame {
    private JTextField txtId, txtNombre, txtDescripcion, txtEstado;
    private ProductoCategoriaController controller;
    private DefaultTableModel modelo;
    private JTable tabla;
    private int modoOperacion = 0; // 1: adicionar, 2: modificar, etc.

    public ProductoCategoriaUI(JFrame principal) {
        this.controller = new ProductoCategoriaController(Conexion.conectar());

        setTitle("Categorías de Productos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel form = new JPanel(new GridLayout(4, 2));
        form.setBorder(BorderFactory.createTitledBorder("Datos"));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtDescripcion = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Descripción:")); form.add(txtDescripcion);
        form.add(new JLabel("Estado:")); form.add(txtEstado);

        add(form, BorderLayout.NORTH);

        // Tabla
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripción", "Estado"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botones
        JPanel botones = new JPanel(new GridLayout(2, 4));
        String[] textos = {"Adicionar", "Modificar", "Eliminar", "Inactivar", "Reactivar", "Actualizar", "Cancelar", "Salir"};
        JButton[] btns = new JButton[textos.length];
        for (int i = 0; i < textos.length; i++) {
            btns[i] = new JButton(textos[i]);
            botones.add(btns[i]);
        }
        add(botones, BorderLayout.SOUTH);

        cargarTabla();

        btns[0].addActionListener(e -> prepararAdicionar());
        btns[1].addActionListener(e -> prepararModificar());
        btns[2].addActionListener(e -> prepararCambioEstado("*"));
        btns[3].addActionListener(e -> prepararCambioEstado("I"));
        btns[4].addActionListener(e -> prepararCambioEstado("A"));
        btns[5].addActionListener(e -> ejecutarOperacion());
        btns[6].addActionListener(e -> limpiarFormulario());
        btns[7].addActionListener(e -> {
            this.dispose();
            principal.setVisible(true);
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                int fila = tabla.getSelectedRow();
                txtId.setText(tabla.getValueAt(fila, 0).toString());
                txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                txtDescripcion.setText(tabla.getValueAt(fila, 2).toString());
                txtEstado.setText(tabla.getValueAt(fila, 3).toString());
            }
        });
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (ProductoCategoria pc : controller.listar()) {
            modelo.addRow(new Object[]{
                pc.getProCatIde(),
                pc.getProCatNom(),
                pc.getProCatDes(),
                pc.getProCatEstReg()
            });
        }
    }

    private void prepararAdicionar() {
        limpiarFormulario();
        txtEstado.setText("A");
        modoOperacion = 1;
    }

    private void prepararModificar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro.");
            return;
        }
        modoOperacion = 2;
    }

    private void prepararCambioEstado(String estado) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro.");
            return;
        }
        txtEstado.setText(estado);
        modoOperacion = switch (estado) {
            case "*" -> 3;
            case "I" -> 4;
            case "A" -> 5;
            default -> 0;
        };
    }

    private void ejecutarOperacion() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String estado = txtEstado.getText().trim();

            ProductoCategoria pc = new ProductoCategoria(id, nombre, descripcion, estado);
            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(pc);
                case 2 -> controller.modificar(pc);
                case 3, 4, 5 -> controller.cambiarEstado(id, estado);
                default -> false;
            };

            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación realizada.");
                cargarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al ejecutar operación.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser un número.");
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
    }
}
