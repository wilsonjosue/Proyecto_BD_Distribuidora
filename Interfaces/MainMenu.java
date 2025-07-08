//Soure packages/Interfaces/MainMenu.java
package Interfaces;

import Interfaces.referenciales.*;
import Interfaces.maestras.*;
import Interfaces.transacciones.*;
import Interfaces.sistema.*;
import Interfaces.consultas.*;
import Interfaces.Vistas.*;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.function.Supplier;
import bd.proyecto.distribuidora.jdbc.Conexion;
import java.sql.SQLException;


public class MainMenu extends JFrame {
    
    private Connection conn;

    public MainMenu() {
        conn = Conexion.conectar();  // crea una única conexión
        setTitle("Menú Principal - Distribuidora");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelBotones = new JPanel(new GridLayout(0, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(panelBotones);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        addBoton(panelBotones, "Gestionar Cargo", () -> new CargoUI(this));
        addBoton(panelBotones, "Categorías de Productos", () -> new ProductoCategoriaUI(this));
        addBoton(panelBotones, "Tipo de Transacción", () -> new TipoTransaccionInventarioUI(this));
        addBoton(panelBotones, "Formas de Pago", () -> new FormaPagoUI(this));
        addBoton(panelBotones, "Categorías de Clientes", () -> new ClienteCategoriaUI(this));
        addBoton(panelBotones, "Transporte", () -> new TransporteUI(this));
        addBoton(panelBotones, "Almacenes", () -> new AlmacenUI(this));
        addBoton(panelBotones, "Gestionar Productos", () -> new ProductoUI(this));
        addBoton(panelBotones, "Clientes", () -> new ClienteUI(this));
        addBoton(panelBotones, "Oficina", () -> new OficinaUI(this));
        addBoton(panelBotones, "Proveedor", () -> new ProveedorUI(this));
        addBoton(panelBotones, "RepDeVentas", () -> new RepresentantesVentasUI(this));
        addBoton(panelBotones, "Compra", () -> new CompraUI(this, conn));
        addBoton(panelBotones, "Compra Detalle", () -> new CompraDetalleUI(this, conn));
        addBoton(panelBotones, "Factura", () -> new FacturaUI(this, conn));
        addBoton(panelBotones, "Factura Detalle", () -> new FacturaDetalleUI(this, conn));
        addBoton(panelBotones, "Facturacion Pedido", () -> new FacturacionPedidoUI(this, conn));
        addBoton(panelBotones, "Pago", () -> new PagoUI(this, conn));
        addBoton(panelBotones, "Pedido Cabecera", () -> new PedidoCabeceraUI(this, conn));
        addBoton(panelBotones, "Pedido Detalle", () -> new PedidoDetalleUI(this, conn));
        addBoton(panelBotones, "Transaccion Invetario", () -> new TransaccionInventarioUI(this, conn));
        addBoton(panelBotones, "Usuario Sistema", () -> new UsuarioSistemaUI(this, conn));
        addBoton(panelBotones, "Login", () -> new LoginUI(conn));
        addBoton(panelBotones, "Consulta General", () -> new ConsultaGeneralUI(this, conn));
        addBoton(panelBotones, "Consulta PedCliRepVentMonto", () -> new ConsultaPedidoClienteRepVentaUI(this, conn));
        addBoton(panelBotones, "Vista Productos y Categorías", () -> new VistaProductoCategoriaUI(this, conn));
        addBoton(panelBotones, "Vista Clientes x Categoría", () -> new VistaClienteCategoriaUI(this, conn));
        addBoton(panelBotones, "Resumen Compras x Proveedor", () -> new ResumenComprasProveedorUI(this, conn));
        addBoton(panelBotones, "Resumen Ventas x Año", () -> new ResumenVentasAnualesUI(this, conn));
        addBoton(panelBotones, "Salir", () -> {
            try {
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
            return null;// requerido por el tipo Supplier
        });
    }

    private void addBoton(JPanel panel, String texto, Supplier<JFrame> proveedorUI) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> {
            if (!texto.equals("Salir")) this.setVisible(false);
            JFrame ventana = proveedorUI.get();
            if (ventana != null) ventana.setVisible(true);
        });
        panel.add(boton);
    }

}