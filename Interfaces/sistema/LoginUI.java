//Soure packages/Interfaces.sistema/LoginUI.java
package Interfaces.sistema;

import Controladores.sistema.UsuarioSistemaController;
import Entidades.sistema.UsuarioSistema;
import Interfaces.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginUI extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar, btnCancelar;

    private UsuarioSistemaController controller;
    private Connection conn;

    public LoginUI(Connection conn) {
        this.conn = conn;
        this.controller = new UsuarioSistemaController(conn);

        setTitle("Inicio de Sesión - Distribuidora");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initUI();
    }

    private void initUI() {
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createTitledBorder("Ingrese sus credenciales"));

        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();

        panelCampos.add(new JLabel("Usuario:"));
        panelCampos.add(txtUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(txtContrasena);

        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnIngresar = new JButton("Ingresar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnIngresar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });

        btnCancelar.addActionListener(e -> System.exit(0));
    }

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña.");
            return;
        }

        UsuarioSistema usu = controller.autenticar(usuario, clave);
         /*
        if (usu != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + usu.getUsuSisNom());
            dispose(); // cierra login
            new MainMenu(conn, usu).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas o usuario inactivo.");
        }
         */
    }
}
