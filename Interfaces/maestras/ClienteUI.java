//Soure packages/Interfaces.maestras/ClienteUI.java
package Interfaces.maestras;

import Controladores.maestras.ClienteController;
import Controladores.referenciales.ClienteCategoriaController;
import Entidades.maestras.Cliente;
import Entidades.referenciales.ClienteCategoria;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteUI extends JFrame {

    private JTextField txtIde, txtEmp, txtNom, txtApePat, txtApeMat, txtDir, txtTel, txtCor, txtDep, txtEstado;
    private JComboBox<ClienteCategoria> cboCategoria;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private ClienteController controller;
    private JFrame principal;
    private int modoOperacion = 0;

    public ClienteUI(JFrame principal) {
        this.principal = principal;
        controller = new ClienteController(Conexion.conectar());

        setTitle("Gestión de Clientes");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(6, 4));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        txtIde = new JTextField();
        txtEmp = new JTextField();
        txtNom = new JTextField();
        txtApePat = new JTextField();
        txtApeMat = new JTextField();
        txtDir = new JTextField();
        txtTel = new JTextField();
        txtCor = new JTextField();
        txtDep = new JTextField();
        cboCategoria = new JComboBox<>();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        cargarCategorias();

        panelForm.add(new JLabel("ID:"));        panelForm.add(txtIde);
        panelForm.add(new JLabel("Empresa:"));   panelForm.add(txtEmp);
        panelForm.add(new JLabel("Nombre:"));    panelForm.add(txtNom);
        panelForm.add(new JLabel("Ape. Paterno:")); panelForm.add(txtApePat);
        panelForm.add(new JLabel("Ape. Materno:")); panelForm.add(txtApeMat);
        panelForm.add(new JLabel("Dirección:")); panelForm.add(txtDir);
        panelForm.add(new JLabel("Teléfono:"));  panelForm.add(txtTel);
        panelForm.add(new JLabel("Correo:"));    panelForm.add(txtCor);
        panelForm.add(new JLabel("Departamento:")); panelForm.add(txtDep);
        panelForm.add(new JLabel("Categoría:")); panelForm.add(cboCategoria);
        panelForm.add(new JLabel("Estado:"));    panelForm.add(txtEstado);

        add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Empresa", "Nombre", "ApePat", "ApeMat", "Dirección", "Teléfono", "Correo", "Departamento", "Categoría", "Estado"
        }, 0);
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
                txtEmp.setText(tabla.getValueAt(fila, 1).toString());
                txtNom.setText(tabla.getValueAt(fila, 2).toString());
                txtApePat.setText(tabla.getValueAt(fila, 3).toString());
                txtApeMat.setText(tabla.getValueAt(fila, 4).toString());
                txtDir.setText(tabla.getValueAt(fila, 5).toString());
                txtTel.setText(tabla.getValueAt(fila, 6).toString());
                txtCor.setText(tabla.getValueAt(fila, 7).toString());
                txtDep.setText(tabla.getValueAt(fila, 8).toString());

                int cliCatIde = Integer.parseInt(tabla.getValueAt(fila, 9).toString());
                for (int i = 0; i < cboCategoria.getItemCount(); i++) {
                    if (cboCategoria.getItemAt(i).getCliCatIde() == cliCatIde) {
                        cboCategoria.setSelectedIndex(i);
                        break;
                    }
                }
                txtEstado.setText(tabla.getValueAt(fila, 10).toString());
            }
        });

        cargarTabla();
    }

    private void cargarCategorias() {
        ClienteCategoriaController catController = new ClienteCategoriaController(Conexion.conectar());
        List<ClienteCategoria> categorias = catController.listar();
        for (ClienteCategoria cat : categorias) cboCategoria.addItem(cat);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Cliente> lista = controller.listar();
        for (Cliente c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getCliIde(), c.getCliEmp(), c.getCliNom(), c.getCliApePat(),
                c.getCliApeMat(), c.getCliDir(), c.getCliTel(),
                c.getCliCor(), c.getCliDep(), c.getCliCatIde(), c.getCliEstReg()
            });
        }
    }

    private void setEditableCampos(boolean estado) {
        txtIde.setEditable(estado);
        txtEmp.setEditable(estado);
        txtNom.setEditable(estado);
        txtApePat.setEditable(estado);
        txtApeMat.setEditable(estado);
        txtDir.setEditable(estado);
        txtTel.setEditable(estado);
        txtCor.setEditable(estado);
        txtDep.setEditable(estado);
        cboCategoria.setEnabled(estado);
    }

    private void prepararAdicionar() {
        limpiarFormulario();
        txtEstado.setText("A");
        setEditableCampos(true);
        modoOperacion = 1;
    }

    private void prepararModificar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para modificar.");
            return;
        }
        setEditableCampos(true);
        txtIde.setEditable(false);
        modoOperacion = 2;
    }

    private void prepararCambioEstado(String estado) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
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
            String emp = txtEmp.getText().trim();
            String nom = txtNom.getText().trim();
            String apePat = txtApePat.getText().trim();
            String apeMat = txtApeMat.getText().trim();
            String dir = txtDir.getText().trim();
            String tel = txtTel.getText().trim();
            String cor = txtCor.getText().trim();
            String dep = txtDep.getText().trim();
            int catIde = ((ClienteCategoria) cboCategoria.getSelectedItem()).getCliCatIde();
            String estado = txtEstado.getText().trim();

            if (nom.isEmpty() || apePat.isEmpty() || tel.length() < 6)
                throw new IllegalArgumentException("Datos inválidos. Verifique los campos.");

            Cliente c = new Cliente(id, emp, nom, apePat, apeMat, dir, tel, cor, dep, catIde, estado);
            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(c);
                case 2 -> controller.modificar(c);
                case 3, 4, 5 -> controller.cambiarEstado(c.getCliIde(), c.getCliEstReg());
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
        txtIde.setText(""); txtEmp.setText(""); txtNom.setText(""); txtApePat.setText("");
        txtApeMat.setText(""); txtDir.setText(""); txtTel.setText(""); txtCor.setText("");
        txtDep.setText(""); txtEstado.setText("A"); cboCategoria.setSelectedIndex(0);
        modoOperacion = 0;
        setEditableCampos(true);
    }
}
