package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Clientes;
import modelo.ClientesDAO;
import modelo.Ventas;
import modelo.VentasDAO;
import vista.JFVentaVer;

public class ControladorVentaVer {
    VentasDAO modeloVentas;
    JFVentaVer vistaVentaVer;
    public ControladorVentaVer(VentasDAO modeloVentas, JFVentaVer vistaVentaVer) {
        this.modeloVentas = modeloVentas;
        this.vistaVentaVer = vistaVentaVer;
        
        vistaVentaVer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaVentaVer.btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
    }
    
    public void InicializarVentaVer(int idVenta) {
        Clientes cliente;
        ClientesDAO modeloClientes = new ClientesDAO();
        // llamar a los metodos Ventas getVenta(idVenta) y ArrayList<Ventas> getVentasProducto(idVenta)
        // para rellenar todo el JFVentasVer
        Ventas venta = modeloVentas.getVenta(idVenta);
        ArrayList<Ventas> arrayVentasProducto = modeloVentas.getVentasProducto(idVenta);
        
        // Ahora con el idVenta conseguir el id cliente 
        int idCliente = modeloVentas.getIdCliente(idVenta);
        // Ahora con esto conseguir todos los datos del cliente
        cliente = modeloClientes.getCliente(idCliente);
        
        // Primero con el objeto venta
        vistaVentaVer.nombre.setText(venta.nombre);
        vistaVentaVer.fecha.setText(venta.fecha);
        vistaVentaVer.hora.setText(venta.hora);
        vistaVentaVer.subtotal.setText(venta.subtotal);
        vistaVentaVer.igv.setText(venta.igv);
        vistaVentaVer.total.setText(venta.monto);

        vistaVentaVer.lblDni.setText(cliente.dni);
        vistaVentaVer.lblRuc.setText(cliente.ruc);
        vistaVentaVer.lblDireccion.setText(cliente.direccion);
        
        // Ahora rellenar tabla con el array Ventas
        DefaultTableModel model = (DefaultTableModel) vistaVentaVer.tbVentaVer.getModel();
        float totalPrecioProducto = 0;
        for (int i = 0; i < arrayVentasProducto.size(); i++) {
            totalPrecioProducto = Float.parseFloat(arrayVentasProducto.get(i).cantidadProducto)*Float.parseFloat(arrayVentasProducto.get(i).precioProducto);
            model.addRow(new Object[]{arrayVentasProducto.get(i).nombre, arrayVentasProducto.get(i).cantidadProducto, arrayVentasProducto.get(i).precioProducto, String.valueOf(totalPrecioProducto)});
        }

    }
    
    public void btnAceptarActionPerformed(ActionEvent evt) {
        vistaVentaVer.dispose();
    }
    
}
