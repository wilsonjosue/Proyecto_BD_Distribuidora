//Soure packages/Interfaces/TipoTransaccionInventarioUI.java
package Interfaces.referenciales;
import Controladores.referenciales.TipoTransaccionInventarioController;
import Entidades.referenciales.TipoTransaccionInventario;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TipoTransaccionInventarioUI extends JFrame  {
    private JTextField txtIde, txtDescripcion, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private TipoTransaccionInventarioController controller;
    private JFrame principal;
    private int modoOperacion = 0;
    
    public TipoTransaccionInventarioUI(JFrame principal) {
        this.principal = principal;
        this.controller = new TipoTransaccionInventarioController(Conexion.conectar());

        setTitle("Gestión de Tipo de Transacción");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(3, 2));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Tipo de Transacción"));

        txtIde = new JTextField();
        txtDescripcion = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        panelForm.add(new JLabel("ID:"));
        panelForm.add(txtIde);
        panelForm.add(new JLabel("Descripción:"));
        panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("Estado:"));
        panelForm.add(txtEstado);

        add(panelForm, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Descripción", "Estado"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Botones
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

        // Eventos
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
    
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<TipoTransaccionInventario> lista = controller.listar();
        for (TipoTransaccionInventario t : lista) {
            modeloTabla.addRow(new Object[]{
                t.getTipTraIde(),
                t.getTipTraDes(),
                t.getTipTraEstReg()
            });
        }
    }
    
    private void prepararAdicionar() {
        limpiarFormulario();
        txtEstado.setText("A");
        txtIde.setEditable(true);
        txtDescripcion.setEditable(true);
        modoOperacion = 1;
    }

    private void prepararModificar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para modificar.");
            return;
        }
        txtIde.setEditable(false);
        txtDescripcion.setEditable(true);
        modoOperacion = 2;
    }

    private void prepararCambioEstado(String nuevoEstado) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro.");
            return;
        }
        txtEstado.setText(nuevoEstado);
        txtIde.setEditable(false);
        txtDescripcion.setEditable(false);
        modoOperacion = switch (nuevoEstado) {
            case "*" -> 3;
            case "I" -> 4;
            case "A" -> 5;
            default -> 0;
        };
    }

    private void ejecutarOperacion() {
        try {
            int id = Integer.parseInt(txtIde.getText());
            String descripcion = txtDescripcion.getText().trim();
            String estado = txtEstado.getText().trim();

            if (descripcion.isEmpty() || descripcion.length() > 50)
                throw new IllegalArgumentException("Descripción inválida (vacía o demasiado larga).");

            TipoTransaccionInventario t = new TipoTransaccionInventario(id, descripcion, estado);

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(t);
                case 2 -> controller.modificar(t);
                case 3, 4, 5 -> controller.cambiarEstado(t.getTipTraIde(), t.getTipTraEstReg());
                default -> false;
            };

            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación exitosa.");
                cargarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo completar la operación.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico válido.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Validación: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtIde.setText("");
        txtDescripcion.setText("");
        txtEstado.setText("A");
        txtIde.setEditable(true);
        txtDescripcion.setEditable(true);
        modoOperacion = 0;
    }
   
}
