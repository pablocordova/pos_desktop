
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.WindowConstants;
import modelo.ClientesDAO;
import modelo.VentasDAO;
import vista.JFEstadisticaCliente;
import vista.JFEstadisticaProducto;
import vista.JFEstadisticas;
import vista.JFEstadisticasVP;

public class ControladorEstadisticas {
    JFEstadisticas vistaEstadisticas;
    
    public ControladorEstadisticas(JFEstadisticas vistaEstadisticas) {
        this.vistaEstadisticas = vistaEstadisticas;
        
        vistaEstadisticas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaEstadisticas.btnProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnProductoActionPerformed(evt);
            }
        });
        this.vistaEstadisticas.btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });
        this.vistaEstadisticas.btnPrecio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
    }
    
    public void btnProductoActionPerformed(ActionEvent evt) {
        JFEstadisticaProducto vistaEstadisticaProducto = new JFEstadisticaProducto();
        VentasDAO modeloVentas= new VentasDAO();
        ControladorEstadisticaProducto estadisticaProducto = new ControladorEstadisticaProducto(vistaEstadisticaProducto, modeloVentas);
        vistaEstadisticaProducto.setVisible(true);
        vistaEstadisticaProducto.setLocationRelativeTo(null);
        estadisticaProducto.InicializarEstadisticaProducto();
    }
    public void btnClienteActionPerformed(ActionEvent evt) {
        JFEstadisticaCliente vistaEstadisticaCliente = new JFEstadisticaCliente();
        ClientesDAO modeloClientes= new ClientesDAO();
        ControladorEstadisticaCliente estadisticaCliente = new ControladorEstadisticaCliente(vistaEstadisticaCliente, modeloClientes);
        vistaEstadisticaCliente.setVisible(true);
        vistaEstadisticaCliente.setLocationRelativeTo(null);
        estadisticaCliente.InicializarEstadisticaCliente();
    }
    public void btnVentasActionPerformed(ActionEvent evt) {
        JFEstadisticasVP vistaEstadisticaVP= new JFEstadisticasVP();
        VentasDAO modeloVentas= new VentasDAO();
        ControladorEstadisticasVP estadisticaVP = new ControladorEstadisticasVP(vistaEstadisticaVP, modeloVentas);
        vistaEstadisticaVP.setVisible(true);
        vistaEstadisticaVP.setLocationRelativeTo(null);
        estadisticaVP.InicializarEstadisticaVP();
    }
    
}
