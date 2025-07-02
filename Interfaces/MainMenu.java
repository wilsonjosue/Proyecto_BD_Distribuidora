//Source packages/Interfaces/MainMenu.java
package Interfaces;
import Interfaces.referenciales.*;
import Interfaces.maestras.*;
import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class MainMenu extends JFrame {
    
    public MainMenu() {
        setTitle("Menú Principal - Distribuidora");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal con diseño más espacioso
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        add(panelBotones);
        
        // Botones principales del menú
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
        MenuSecundario menuPrincipales = new MenuSecundario(this, "Menú Principales");
        
        menuPrincipales.addBoton("Cliente", () -> new ClienteUI(this));
        menuPrincipales.addBoton("Oficina", () -> new OficinaUI(this));
        menuPrincipales.addBoton("Producto", () -> new ProductoUI(this));
        menuPrincipales.addBoton("Proveedor", () -> new ProveedorUI(this));
        menuPrincipales.addBoton("Representante de Ventas", () -> new RepresentantesVentasUI(this));
        
        menuPrincipales.setVisible(true);
        this.setVisible(false);
    }
    
    private void abrirMenuReferenciales() {
        MenuSecundario menuReferenciales = new MenuSecundario(this, "Menú Referenciales");
        
        menuReferenciales.addBoton("Almacén", () -> new AlmacenUI(this));
        menuReferenciales.addBoton("Cargo", () -> new CargoUI(this));
        menuReferenciales.addBoton("Cliente Categoría", () -> new ClienteCategoriaUI(this));
        menuReferenciales.addBoton("Forma de Pago", () -> new FormaPagoUI(this));
        menuReferenciales.addBoton("Producto Categoría", () -> new ProductoCategoriaUI(this));
        menuReferenciales.addBoton("Tipo de Transacción e Inventario", () -> new TipoTransaccionInventarioUI(this));
        menuReferenciales.addBoton("Transporte", () -> new TransporteUI(this));
        
        menuReferenciales.setVisible(true);
        this.setVisible(false);
    }
    
    private void abrirMenuSistema() {
        MenuSecundario menuSistema = new MenuSecundario(this, "Menú Sistema");
        
        // Estos son nuevos módulos que necesitarás crear
        menuSistema.addBoton("Auditoría", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Auditoría - En desarrollo");
            return null;
        });
        menuSistema.addBoton("Usuarios de Sistema", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Usuarios - En desarrollo");
            return null;
        });
        
        menuSistema.setVisible(true);
        this.setVisible(false);
    }
    
    private void abrirMenuTransacciones() {
        MenuSecundario menuTransacciones = new MenuSecundario(this, "Menú Transacciones");
        
        // Estos son nuevos módulos que necesitarás crear
        menuTransacciones.addBoton("Compra", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Compra - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Compra Detalle", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Compra Detalle - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Factura", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Factura - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Factura Detalle", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Factura Detalle - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Facturación de Pedido", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Facturación de Pedido - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Pago", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Pago - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Pedido Cabecera", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Pedido Cabecera - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Pedido Detalle", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Pedido Detalle - En desarrollo");
            return null;
        });
        menuTransacciones.addBoton("Transacción Inventario", () -> {
            JOptionPane.showMessageDialog(this, "Módulo de Transacción Inventario - En desarrollo");
            return null;
        });
        
        menuTransacciones.setVisible(true);
        this.setVisible(false);
    }
}

// Clase auxiliar para los menús secundarios
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
        
        // Panel inferior con botón de regreso
        JPanel panelInferior = new JPanel(new FlowLayout());
        JButton botonVolver = new JButton("Volver al Menú Principal");
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