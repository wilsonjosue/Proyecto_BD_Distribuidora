//Soure packages/Interfaces.sistema/UsuarioSistemaUI.java
package Interfaces.sistema;

import Entidades.sistema.UsuarioSistema;
import Controladores.sistema.UsuarioSistemaController;
import Interfaces.MainMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class UsuarioSistemaUI extends JFrame {

    private JTextField txtIde, txtNombre, txtClave, txtRol, txtRepVen, txtEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private UsuarioSistemaController controller;
    private MainMenu principal;
    private int modoOperacion = 0;

    public UsuarioSistemaUI(MainMenu principal, Connection conn) {
        this.principal = principal;
        this.controller = new UsuarioSistemaController(conn);
        setTitle("Mantenimiento de Usuario del Sistema");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        cargarTabla();
    }

    private void initUI() {
        JPanel pnlCampos = new JPanel(new GridLayout(3, 4));
        pnlCampos.setBorder(BorderFactory.createTitledBorder("Datos de Usuario"));

        txtIde = new JTextField();
        txtNombre = new JTextField();
        txtClave = new JTextField();
        txtRol = new JTextField();
        txtRepVen = new JTextField();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        pnlCampos.add(new JLabel("ID:")); pnlCampos.add(txtIde);
        pnlCampos.add(new JLabel("Nombre:")); pnlCampos.add(txtNombre);
        pnlCampos.add(new JLabel("Clave:")); pnlCampos.add(txtClave);
        pnlCampos.add(new JLabel("Rol:")); pnlCampos.add(txtRol);
        pnlCampos.add(new JLabel("ID RepVentas:")); pnlCampos.add(txtRepVen);
        pnlCampos.add(new JLabel("Estado:")); pnlCampos.add(txtEstado);

        add(pnlCampos, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Nombre", "Clave", "Rol", "RepVentas", "Estado"
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

        // Eventos botones
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
                txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                txtClave.setText(tabla.getValueAt(fila, 2).toString());
                txtRol.setText(tabla.getValueAt(fila, 3).toString());
                txtRepVen.setText(tabla.getValueAt(fila, 4).toString());
                txtEstado.setText(tabla.getValueAt(fila, 5).toString());
            }
        });
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
            UsuarioSistema u = new UsuarioSistema(
                Integer.parseInt(txtIde.getText()),
                txtNombre.getText().trim(),
                txtClave.getText().trim(),
                txtRol.getText().trim(),
                Integer.parseInt(txtRepVen.getText()),
                txtEstado.getText().trim()
            );

            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(u);
                case 2 -> controller.modificar(u);
                case 3, 4, 5 -> controller.cambiarEstado(u.getUsuSisIde(), u.getUsuSisEstReg());
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

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<UsuarioSistema> lista = controller.listar();
        for (UsuarioSistema u : lista) {
            modeloTabla.addRow(new Object[]{
                u.getUsuSisIde(), u.getUsuSisNom(), u.getUsuSisCon(),
                u.getUsuSisRol(), u.getUsuSisRepVen(), u.getUsuSisEstReg()
            });
        }
    }

    private void limpiarFormulario() {
        txtIde.setText("");
        txtNombre.setText("");
        txtClave.setText("");
        txtRol.setText("");
        txtRepVen.setText("");
        txtEstado.setText("A");
        modoOperacion = 0;
        setEditableCampos(true);
    }

    private void setEditableCampos(boolean editable) {
        txtIde.setEditable(editable);
        txtNombre.setEditable(editable);
        txtClave.setEditable(editable);
        txtRol.setEditable(editable);
        txtRepVen.setEditable(editable);
    }
}