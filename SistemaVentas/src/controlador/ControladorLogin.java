package controlador;

import javax.swing.*;
import java.awt.event.*;
import modelo.Usuarios;
import modelo.UsuariosDAO;
import vista.JFLogin;
import vista.JFPrincipal;

public class ControladorLogin implements ActionListener{
    // Definir variables
    JFLogin vistaLogin;
    UsuariosDAO modeloLogin; 
    Usuarios usuario = new Usuarios();
    
    public ControladorLogin(JFLogin vistaLogin, UsuariosDAO modeloLogin) {
        this.vistaLogin = vistaLogin;
        this.modeloLogin = modeloLogin;
        // Configurar evento de click en el boton para el boton Ingresar
        this.vistaLogin.btnIngresar.addActionListener(this);
        // Mostrar la ventana de login
        this.vistaLogin.setVisible(true);
        this.vistaLogin.setLocationRelativeTo(null);
        
        this.vistaLogin.txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });
    }
    
    public void InicializarLogin() {
    
    }
    
    //Evento cuando se da click en el bototon Ingresar
    public void actionPerformed(ActionEvent e) {
        String dni = vistaLogin.txtDni.getText();
        String password = String.valueOf(vistaLogin.txtPassword.getPassword());
        usuario = modeloLogin.verificaUsario(dni, password);
        if(usuario == null) {
            JOptionPane.showMessageDialog(vistaLogin, "Usuario incorrecto");
        } else {
            JFPrincipal vistaP = new JFPrincipal();
            vistaP.lblUsuario.setText("Bienvenido " + usuario.getNombres());
            ControladorPrincipal controladorP = new ControladorPrincipal(vistaP, modeloLogin);
            controladorP.InicializarPrincipal(dni, password);
            vistaP.setVisible(true);
            //vistaP.setExtendedState(vistaP.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            // Activar botones deacuerdo a prioridad de privilegios (Administrador o vendedor)}
            System.out.println(usuario.privilegios);
            if(usuario.privilegios.equals("V")) {
                vistaP.btnProductos.setEnabled(false);
                vistaP.btnEstadisticas.setEnabled(false);
                vistaP.btnConfiguracion.setEnabled(false);
            }
            vistaP.setLocationRelativeTo(null);
            vistaLogin.setVisible(false);
        }
    }
    
    public void txtPasswordKeyReleased(KeyEvent evt) {
        if(evt.getKeyCode() == 10) {
            vistaLogin.btnIngresar.doClick();
        }
    }
}
