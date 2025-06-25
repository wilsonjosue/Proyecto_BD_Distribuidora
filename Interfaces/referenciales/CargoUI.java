//Soure packages/Interfaces/CargoUI.java
package Interfaces.referenciales;
import Controladores.referenciales.CargoController;
import Entidades.referenciales.Cargo;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CargoUI extends JFrame {
    private JTextField txtIde, txtCuota, txtVentas, txtDescripcion, txtSueldo, txtEstado;
    private CargoController controller;
    private JFrame principal;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private int modoOperacion = 0; // 0: nada, 1: adicionar, 2: modificar, 3: eliminar, 4: inactivar, 5: reactivar

    public CargoUI(JFrame principal) {
        this.principal = principal;
        this.controller = new CargoController(Conexion.conectar());

        setTitle("Gestión de Cargos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(6, 2));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Cargo"));

        txtIde = new JTextField();
        txtCuota = new JTextField();
        txtVentas = new JTextField();
        txtDescripcion = new JTextField();
        txtSueldo = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        panelForm.add(new JLabel("ID:")); panelForm.add(txtIde);
        panelForm.add(new JLabel("Cuota Prevista:")); panelForm.add(txtCuota);
        panelForm.add(new JLabel("Ventas Realizadas:")); panelForm.add(txtVentas);
        panelForm.add(new JLabel("Descripción:")); panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("Sueldo:")); panelForm.add(txtSueldo);
        panelForm.add(new JLabel("Estado:")); panelForm.add(txtEstado);

        add(panelForm, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Cuota", "Ventas", "Descripción", "Sueldo", "Estado"}, 0);
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
                txtCuota.setText(tabla.getValueAt(fila, 1).toString());
                txtVentas.setText(tabla.getValueAt(fila, 2).toString());
                txtDescripcion.setText(tabla.getValueAt(fila, 3).toString());
                txtSueldo.setText(tabla.getValueAt(fila, 4).toString());
                txtEstado.setText(tabla.getValueAt(fila, 5).toString());
            }
        });
    }
    
    private void setEditableCampos(boolean ide, boolean cuota, boolean ventas, boolean descripcion, boolean sueldo, boolean estado) {
    txtIde.setEditable(ide);
    txtCuota.setEditable(cuota);
    txtVentas.setEditable(ventas);
    txtDescripcion.setEditable(descripcion);
    txtSueldo.setEditable(sueldo);
    txtEstado.setEditable(estado);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Cargo> lista = controller.listar();
        for (Cargo c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getCarIde(),
                c.getCarCuoPre(),
                c.getCarVenRea(),
                c.getCarDes(),
                c.getCarSue(),
                c.getCarEstReg()
            });
        }
    }

    private void prepararAdicionar() {
        limpiarFormulario();
        txtEstado.setText("A");
        setEditableCampos(true, true, true, true, true, false);
        modoOperacion = 1;
    }

    private void prepararModificar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para modificar.");
            return;
        }
        modoOperacion = 2;
        setEditableCampos(false, true, true, true, true, false);
        // Aquí se activa el flag para actualización
        // Podrías almacenarlo en la clase UI o en la entidad Cargo
        // pero en este caso se usará directamente como lógica
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
        // Bloquear todos los campos excepto el botón Actualizar
        setEditableCampos(false, false, false, false, false, false);
    }

    private void ejecutarOperacion() {
        try {
            // Validación manual antes de crear el objeto
            int id = Integer.parseInt(txtIde.getText());
            if (id < 1) throw new IllegalArgumentException("El ID debe ser mayor que cero.");

            double cuota = Double.parseDouble(txtCuota.getText());
            double ventas = Double.parseDouble(txtVentas.getText());
            double sueldo = Double.parseDouble(txtSueldo.getText());

            if (cuota < 0 || ventas < 0 || sueldo < 0)
                throw new IllegalArgumentException("Los valores monetarios no pueden ser negativos.");

            String descripcion = txtDescripcion.getText().trim();
            if (descripcion.length() > 100)
                throw new IllegalArgumentException("La descripción no debe superar los 100 caracteres.");

            Cargo c = new Cargo();
            c.setCarIde(id);
            c.setCarCuoPre(cuota);
            c.setCarVenRea(ventas);
            c.setCarDes(descripcion);
            c.setCarSue(sueldo);
            c.setCarEstReg(txtEstado.getText().trim());
            
            boolean exito = switch (modoOperacion) {
                case 1 -> exito = controller.insertarCargo(c);
                case 2 -> exito = controller.modificar(c);
                case 3, 4, 5 -> exito = controller.cambiarEstado(c.getCarIde(), c.getCarEstReg());
                default -> false;   
            };

            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación exitosa.");
                cargarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo completar la operación.");
            }

        } catch (NumberFormatException nfe) {
                 JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos en ID, Cuota, Ventas y Sueldo.");
        } catch (IllegalArgumentException iae) {
                JOptionPane.showMessageDialog(this, "Validación: " + iae.getMessage());
        }catch (Exception ex) {
            ex.printStackTrace();  // Muestra la traza completa en consola
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtIde.setText("");
        txtCuota.setText("");
        txtVentas.setText("");
        txtDescripcion.setText("");
        txtSueldo.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        // Restaurar campos editables por defecto
        setEditableCampos(true, true, true, true, true, false);
    }
}