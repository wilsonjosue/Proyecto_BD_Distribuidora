//Soure packages/Interfaces/MainMenu.java
package Interfaces;

import Interfaces.referenciales.CargoUI;
import Interfaces.referenciales.ProductoCategoriaUI;
import Interfaces.referenciales.TipoTransaccionInventarioUI;
import Interfaces.referenciales.FormaPagoUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Menú Principal - Distribuidora");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JButton btnCargo = new JButton("Gestionar Cargo");
        JButton btnProductoCat = new JButton("Categorías de Productos");
        JButton btnTipoTransaccion = new JButton("Tipos de Transacción");
        JButton btnFormaPago = new JButton("Formas de Pago");
        JButton btnSalir = new JButton("Salir");

        btnCargo.addActionListener(e -> {
            this.setVisible(false);
            new CargoUI(this).setVisible(true);
        });
        
        btnProductoCat.addActionListener(e -> {
            this.setVisible(false);
            new ProductoCategoriaUI(this).setVisible(true);
        });
        
        btnTipoTransaccion.addActionListener(e -> {
            this.setVisible(false);
            new TipoTransaccionInventarioUI(this).setVisible(true);
        });
        
        btnFormaPago.addActionListener(e -> {
            this.setVisible(false);
            new FormaPagoUI(this).setVisible(true);
        });

        btnSalir.addActionListener(e -> System.exit(0));

        add(btnCargo);
        add(btnProductoCat);
        add(btnTipoTransaccion); // ← este es el nuevo botón
        add(btnFormaPago);
        add(btnSalir);
    }
}