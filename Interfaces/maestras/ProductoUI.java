//Soure packages/Interfaces.maestras/ProductoUI.jav
package Interfaces.maestras;

import Controladores.maestras.ProductoController;
import Entidades.maestras.Producto;
import Entidades.referenciales.ProductoCategoria;
import Controladores.referenciales.ProductoCategoriaController;
import bd.proyecto.distribuidora.jdbc.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductoUI extends JFrame {

    private JTextField txtIde, txtNom, txtDes, txtPreUni, txtSto, txtUniMed, txtClaABC, txtStoMin, txtStoMax, txtEstado;
    private JComboBox<ProductoCategoria> cboCategoria;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private ProductoController controller;
    private JFrame principal;
    private int modoOperacion = 0;

    public ProductoUI(JFrame principal) {
        this.principal = principal;
        controller = new ProductoController(Conexion.conectar());

        setTitle("Gestión de Productos");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(6, 4));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        txtIde = new JTextField();
        txtNom = new JTextField();
        txtDes = new JTextField();
        txtPreUni = new JTextField();
        txtSto = new JTextField();
        txtUniMed = new JTextField();
        txtClaABC = new JTextField();
        txtStoMin = new JTextField();
        txtStoMax = new JTextField();
        cboCategoria = new JComboBox<>();
        txtEstado = new JTextField("A");
        txtEstado.setEditable(false);

        cargarCategorias();

        panelForm.add(new JLabel("ID:"));         panelForm.add(txtIde);
        panelForm.add(new JLabel("Nombre:"));     panelForm.add(txtNom);
        panelForm.add(new JLabel("Descripción:"));panelForm.add(txtDes);
        panelForm.add(new JLabel("Precio Unitario:")); panelForm.add(txtPreUni);
        panelForm.add(new JLabel("Stock:"));       panelForm.add(txtSto);
        panelForm.add(new JLabel("Unidad Medida:")); panelForm.add(txtUniMed);
        panelForm.add(new JLabel("Clase ABC:"));   panelForm.add(txtClaABC);
        panelForm.add(new JLabel("Stock Mínimo:"));panelForm.add(txtStoMin);
        panelForm.add(new JLabel("Stock Máximo:"));panelForm.add(txtStoMax);
        panelForm.add(new JLabel("Categoría:"));   panelForm.add(cboCategoria);
        panelForm.add(new JLabel("Estado:"));      panelForm.add(txtEstado);

        add(panelForm, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Nombre", "Descripción", "Precio", "Stock", "UMedida", "ABC", "Min", "Max", "Categoría", "Estado"
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
                txtNom.setText(tabla.getValueAt(fila, 1).toString());
                txtDes.setText(tabla.getValueAt(fila, 2).toString());
                txtPreUni.setText(tabla.getValueAt(fila, 3).toString());
                txtSto.setText(tabla.getValueAt(fila, 4).toString());
                txtUniMed.setText(tabla.getValueAt(fila, 5).toString());
                txtClaABC.setText(tabla.getValueAt(fila, 6).toString());
                txtStoMin.setText(tabla.getValueAt(fila, 7).toString());
                txtStoMax.setText(tabla.getValueAt(fila, 8).toString());

                int proCatIde = Integer.parseInt(tabla.getValueAt(fila, 9).toString());
                for (int i = 0; i < cboCategoria.getItemCount(); i++) {
                    if (cboCategoria.getItemAt(i).getProCatIde() == proCatIde) {
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
        ProductoCategoriaController catController = new ProductoCategoriaController(Conexion.conectar());
        List<ProductoCategoria> categorias = catController.listar();
        for (ProductoCategoria cat : categorias) {
            cboCategoria.addItem(cat);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Producto> lista = controller.listar();
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getProIde(),
                p.getProNom(),
                p.getProDes(),
                p.getProPreUni(),
                p.getProSto(),
                p.getProUniMed(),
                p.getProClaABC(),
                p.getProStoMin(),
                p.getProStoMax(),
                p.getProCatIde(),
                p.getProEstReg()
            });
        }
    }

    private void setEditableCampos(boolean estado) {
        txtIde.setEditable(estado);
        txtNom.setEditable(estado);
        txtDes.setEditable(estado);
        txtPreUni.setEditable(estado);
        txtSto.setEditable(estado);
        txtUniMed.setEditable(estado);
        txtClaABC.setEditable(estado);
        txtStoMin.setEditable(estado);
        txtStoMax.setEditable(estado);
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
            JOptionPane.showMessageDialog(this, "Seleccione un producto para modificar.");
            return;
        }
        setEditableCampos(true);
        txtIde.setEditable(false);
        modoOperacion = 2;
    }

    private void prepararCambioEstado(String estado) {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
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
            String des = txtDes.getText().trim();
            double preUni = Double.parseDouble(txtPreUni.getText());
            int sto = Integer.parseInt(txtSto.getText());
            String uni = txtUniMed.getText().trim();
            String abc = txtClaABC.getText().trim().toUpperCase();
            int min = Integer.parseInt(txtStoMin.getText());
            int max = Integer.parseInt(txtStoMax.getText());
            int catIde = ((ProductoCategoria) cboCategoria.getSelectedItem()).getProCatIde();
            String estado = txtEstado.getText().trim();

            // Validaciones
            if (nom.isEmpty() || nom.length() > 100 || !abc.matches("[ABC]") || preUni < 0 || sto < 0 || min < 0 || max < 0)
                throw new IllegalArgumentException("Datos inválidos. Verifique los campos.");

            Producto p = new Producto(id, nom, des, preUni, sto, uni, abc, min, max, catIde, estado);
            boolean exito = switch (modoOperacion) {
                case 1 -> controller.adicionar(p);
                case 2 -> controller.modificar(p);
                case 3, 4, 5 -> controller.cambiarEstado(p.getProIde(), p.getProEstReg());
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
        txtDes.setText("");
        txtPreUni.setText("");
        txtSto.setText("");
        txtUniMed.setText("");
        txtClaABC.setText("");
        txtStoMin.setText("");
        txtStoMax.setText("");
        txtEstado.setText("A");
        cboCategoria.setSelectedIndex(0);
        modoOperacion = 0;
        setEditableCampos(true);
    }
}

