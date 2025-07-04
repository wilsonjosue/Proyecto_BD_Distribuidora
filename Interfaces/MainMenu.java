//Source packages/Interfaces/MainMenu.java
package Interfaces;
import Interfaces.referenciales.*;
import Interfaces.maestras.*;
import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class MainMenu extends JFrame {
    
    public MainMenu() {
        setTitle("Men� Principal - Distribuidora");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal con dise�o m�s espacioso
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        add(panelBotones);
        
        // Botones principales del men�
        addBotonCategoria(panelBotones, "Principales", this::abrirMenuPrincipales);
        addBotonCategoria(panelBotones, "Referenciales", this::abrirMenuReferenciales);
        addBotonCategoria(panelBotones, "Sistema", this::abrirMenuSistema);
        addBotonCategoria(panelBotones, "Transacciones", this::abrirMenuTransacciones);
    }
    
    private void addBotonCategoria(JPanel panel, String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(200, 80));
        boton.addActionListener(e -> accion.run());
        panel.add(boton);
    }
    
    private void abrirMenuPrincipales() {
        MenuSecundario menuPrincipales = new MenuSecundario(this, "Men� Principales");
        
        menuPrincipales.addBoton("Cliente", () -> new ClienteUI(this));
        menuPrincipales.addBoton("Oficina", () -> new OficinaUI(this));
        menuPrincipales.addBoton("Producto", () -> new ProductoUI(this));
        menuPrincipales.addBoton("Proveedor", () -> new ProveedorUI(this));
        menuPrincipales.addBoton("Representante de Ventas", () -> new RepresentantesVentasUI(this));
        
        menuPrincipales.setVisible(true);
        this.setVisible(false);
    }
    
    private void abrirMenuReferenciales() {
        MenuSecundario menuReferenciales = new MenuSecundario(this, "Men� Referenciales");
        
        menuReferenciales.addBoton("Almac�n", () -> new AlmacenUI(this));
        menuReferenciales.addBoton("Cargo", () -> new CargoUI(this));
        menuReferenciales.addBoton("Cliente Categor�a", () -> new ClienteCategoriaUI(this));
        menuReferenciales.addBoton("Forma de Pago", () -> new FormaPagoUI(this));
        menuReferenciales.addBoton("Producto Categor�a", () -> new ProductoCategoriaUI(this));
        menuReferenciales.addBoton("Tipo de Transacci�n e Inventario", () -> new TipoTransaccionInventarioUI(this));
        menuReferenciales.addBoton("Transporte", () -> new TransporteUI(this));
        
        menuReferenciales.setVisible(true);
        this.setVisible(false);
    }
    
    private void abrirMenuSistema() {
        MenuSecundario menuSistema = new MenuSecundario(this, "Men� Sistema");
        
        // Estos son nuevos m�dulos que necesitar�s crear
        menuSistema.addBoton("Auditor�a", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Auditor�a - En desarrollo");
            return null;
        });
        menuSistema.addBoton("Usuarios de Sistema", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Usuarios - En desarrollo");
            return null;
        });
        
        menuSistema.setVisible(true);
        this.setVisible(false);
    }
    
    private void abrirMenuTransacciones() {
        MenuSecundario menuTransacciones = new MenuSecundario(this, "Men� Transacciones");
        
        // Estos son nuevos m�dulos que necesitar�s crear
        menuTransacciones.addBoton("Compra", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Compra - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Compra Detalle", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Compra Detalle - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Factura", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Factura - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Factura Detalle", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Factura Detalle - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Facturaci�n de Pedido", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Facturaci�n de Pedido - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Pago", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Pago - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Pedido Cabecera", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Pedido Cabecera - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Pedido Detalle", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Pedido Detalle - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Transacci�n Inventario", () -> {
            JOptionPane.showMessageDialog(this, "M�dulo de Transacci�n Inventario - En desarrollo");
            return null;
        });
        
        menuTransacciones.setVisible(true);
        this.setVisible(false);
    }
}

// Clase auxiliar para los men�s secundarios
class MenuSecundario extends JFrame {
    private JFrame menuPrincipal;
    
    public MenuSecundario(JFrame menuPrincipal, String titulo) {
        this.menuPrincipal = menuPrincipal;
        setTitle(titulo);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(menuPrincipal);
        
        // Panel con scroll para manejar muchos botones
        JPanel panelBotones = new JPanel(new GridLayout(0, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(panelBotones);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con bot�n de regreso
        JPanel panelInferior = new JPanel(new FlowLayout());
        JButton botonVolver = new JButton("Volver al Men� Principal");
        botonVolver.addActionListener(e -> {
            this.setVisible(false);
            menuPrincipal.setVisible(true);
        });
        panelInferior.add(botonVolver);
        add(panelInferior, BorderLayout.SOUTH);
        
        // Manejar el cierre de ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                MenuSecundario.this.setVisible(false);
                menuPrincipal.setVisible(true);
            }
        });
    }
    
    public void addBoton(String texto, Supplier<JFrame> proveedorUI) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> {
            JFrame ventana = proveedorUI.get();
            if (ventana != null) {
                this.setVisible(false);
                ventana.setVisible(true);
            }
        });
        
        // Obtener el panel de botones del ScrollPane
        JScrollPane scrollPane = (JScrollPane) getContentPane().getComponent(0);
        JViewport viewport = scrollPane.getViewport();
        JPanel panelBotones = (JPanel) viewport.getView();
        panelBotones.add(boton);
    }
}