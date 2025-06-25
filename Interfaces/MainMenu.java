//Soure packages/Interfaces/MainMenu.java
package Interfaces;

import Interfaces.referenciales.*;
import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class MainMenu extends JFrame {

    public MainMenu() {
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

        addBoton(panelBotones, "Salir", () -> {
            System.exit(0);
            return null; // requerido por el tipo Supplier
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