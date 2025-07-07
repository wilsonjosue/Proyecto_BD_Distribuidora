//Soure packages/Interfaces.maestras/RepresentantesVentasUI.java
package Interfaces.maestras;

import Controladores.maestras.RepresentantesVentasController;
import Entidades.maestras.RepresentantesVentas;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RepresentantesVentasUI extends JFrame {
    private JTextField txtIde, txtNom, txtApe, txtEda, txtOfi, txtCar,
                       txtAnioCon, txtMesCon, txtDiaCon, txtNumVen, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private RepresentantesVentasController controller;
    private JFrame principal;
    private int modoOperacion = 0;

    public RepresentantesVentasUI(JFrame principal) {
        this.principal = principal;
        controller = new RepresentantesVentasController(Conexion.conectar());

        setTitle("Gestión de Representantes de Ventas");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(6, 4));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Representante"));

        txtIde = new JTextField();
        txtNom = new JTextField();
        txtApe = new JTextField();
        txtEda = new JTextField();
        txtOfi = new JTextField();
        txtCar = new JTextField();
        txtAnioCon = new JTextField();
        txtMesCon = new JTextField();
        txtDiaCon = new JTextField();
        txtNumVen = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        panelForm.add(new JLabel("ID:"));         panelForm.add(txtIde);
        panelForm.add(new JLabel("Nombre:"));     panelForm.add(txtNom);
        panelForm.add(new JLabel("Apellido:"));   panelForm.add(txtApe);
        panelForm.add(new JLabel("Edad:"));       panelForm.add(txtEda);
        panelForm.add(new JLabel("Oficina ID:")); panelForm.add(txtOfi);
        panelForm.add(new JLabel("Cargo ID:"));   panelForm.add(txtCar);
        panelForm.add(new JLabel("Año Contrato:")); panelForm.add(txtAnioCon);
        panelForm.add(new JLabel("Mes Contrato:")); panelForm.add(txtMesCon);
        panelForm.add(new JLabel("Día Contrato:")); panelForm.add(txtDiaCon);
        panelForm.add(new JLabel("Número Ventas:")); panelForm.add(txtNumVen);
        panelForm.add(new JLabel("Estado:"));     panelForm.add(txtEstado);

        add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Nombre", "Apellido", "Edad", "Oficina", "Cargo",
            "Año", "Mes", "Día", "Ventas", "Estado"
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
                txtNom.setText(tabla.getValueAt(fila, 1).toString());
                txtApe.setText(tabla.getValueAt(fila, 2).toString());
                txtEda.setText(tabla.getValueAt(fila, 3).toString());
                txtOfi.setText(tabla.getValueAt(fila, 4).toString());
                txtCar.setText(tabla.getValueAt(fila, 5).toString());
                txtAnioCon.setText(tabla.getValueAt(fila, 6).toString());
                txtMesCon.setText(tabla.getValueAt(fila, 7).toString());
                txtDiaCon.setText(tabla.getValueAt(fila, 8).toString());
                txtNumVen.setText(tabla.getValueAt(fila, 9).toString());
                txtEstado.setText(tabla.getValueAt(fila, 10).toString());
            }
        });

        cargarTabla();
    }

    private void setEditableCampos(boolean editable) {
        txtIde.setEditable(editable);
        txtNom.setEditable(editable);
        txtApe.setEditable(editable);
        txtEda.setEditable(editable);
        txtOfi.setEditable(editable);
        txtCar.setEditable(editable);
        txtAnioCon.setEditable(editable);
        txtMesCon.setEditable(editable);
        txtDiaCon.setEditable(editable);
        txtNumVen.setEditable(editable);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<RepresentantesVentas> lista = controller.listar();
        for (RepresentantesVentas r : lista) {
            modeloTabla.addRow(new Object[]{
                r.getRepVenIde(), r.getRepVenNom(), r.getRepVenApe(),
                r.getRepVenEda(), r.getRepVenOfi(), r.getRepVenCar(),
                r.getRepVenAnioCon(), r.getRepVenMesCon(), r.getRepVenDiaCon(),
                r.getRepVenNumVen(), r.getRepVenEstReg()
            });
        }
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
            RepresentantesVentas r = new RepresentantesVentas(
                Integer.parseInt(txtIde.getText()),
                txtNom.getText().trim(),
                txtApe.getText().trim(),
                Integer.parseInt(txtEda.getText()),
                Integer.parseInt(txtOfi.getText()),
                Integer.parseInt(txtCar.getText()),
                Integer.parseInt(txtAnioCon.getText()),
                Integer.parseInt(txtMesCon.getText()),
                Integer.parseInt(txtDiaCon.getText()),
                Integer.parseInt(txtNumVen.getText()),
                txtEstado.getText().trim()
            );

            if (r.getRepVenNom().isEmpty() || r.getRepVenApe().isEmpty() || r.getRepVenEda() < 18)
                throw new IllegalArgumentException("Datos inválidos. Verifique los campos.");

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(r);
                case 2 -> controller.modificar(r);
                case 3, 4, 5 -> controller.cambiarEstado(r.getRepVenIde(), r.getRepVenEstReg());
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
        txtApe.setText("");
        txtEda.setText("");
        txtOfi.setText("");
        txtCar.setText("");
        txtAnioCon.setText("");
        txtMesCon.setText("");
        txtDiaCon.setText("");
        txtNumVen.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true);
    }
}

