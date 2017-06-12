package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import modelo.Usuarios;
import modelo.UsuariosDAO;
import vista.JFConfiguracionUsuariosCrear;

public class ControladorConfiguracionUsuariosCrear {
    UsuariosDAO modeloUsuarios;
    JFConfiguracionUsuariosCrear vistaConfiguracionUsuariosCrear;
    ControladorConfiguracion controladorConfiguracion;
    
    public ControladorConfiguracionUsuariosCrear(UsuariosDAO modeloUsuarios, JFConfiguracionUsuariosCrear vistaConfiguracionUsuariosCrear, ControladorConfiguracion controladorConfiguracion) {
        this.modeloUsuarios = modeloUsuarios;
        this.vistaConfiguracionUsuariosCrear = vistaConfiguracionUsuariosCrear;
        this.controladorConfiguracion = controladorConfiguracion;
        
        vistaConfiguracionUsuariosCrear.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaConfiguracionUsuariosCrear.btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        this.vistaConfiguracionUsuariosCrear.btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
    }
    public void InicializarConfiguracionUsuariosCrear(){
    }
    
    public void btnCrearActionPerformed(ActionEvent evt) {
        Usuarios usuario = new Usuarios();
        usuario.setDni(vistaConfiguracionUsuariosCrear.txtDni.getText());
        usuario.setPassword(vistaConfiguracionUsuariosCrear.txtPassword.getText());
        usuario.setApellidos(vistaConfiguracionUsuariosCrear.txtApellidos.getText());
        usuario.setNombres(vistaConfiguracionUsuariosCrear.txtNombres.getText());
        usuario.setPrivilegios(vistaConfiguracionUsuariosCrear.cbPrivilegio.getSelectedItem().toString());
        
        if(usuario.dni.isEmpty() || usuario.password.isEmpty() || usuario.apellidos.isEmpty() || usuario.nombres.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese datos completos");
            return;
        }
        // Caso los datos se han igresado completos
        
        modeloUsuarios.CrearUsuario(usuario);
        vistaConfiguracionUsuariosCrear.dispose();
        controladorConfiguracion.limpiaTabla();
        controladorConfiguracion.InicializarConfiguracion();
        
    }
    
    public void btnCancelarActionPerformed(ActionEvent evt) {
        vistaConfiguracionUsuariosCrear.dispose();
    }
    
}
