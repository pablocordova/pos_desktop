
package SistemaVentas;

import controlador.ControladorLogin;
import modelo.UsuariosDAO;
import vista.JFLogin;

public class SistemaVentasMVC {
    
    public static void main(String[] args) {
        // Instanciar a la clase JFLogin, llamamos al objeto creado vistaL
        JFLogin vistaL = new JFLogin();
        // Instanciar a la clase Usuarios DAO, llamamos al objeto creado modeloL
        UsuariosDAO modeloL = new UsuariosDAO();
        // Pasamos los objetos creados, ya que estos seran los usados.
        ControladorLogin controladorL = new ControladorLogin(vistaL, modeloL);
        // Mostrar primera pantalla (Login) y centrarla
        //vistaL.setVisible(true);
        //vistaL.setLocationRelativeTo(null);
    }
}
