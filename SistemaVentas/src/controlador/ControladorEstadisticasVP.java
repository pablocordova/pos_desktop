
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.WindowConstants;
import modelo.Ventas;
import modelo.VentasDAO;
import vista.JFEstadisticasVP;


public class ControladorEstadisticasVP {
    JFEstadisticasVP vistaEstadisticaVP;
    VentasDAO modeloVentas;
    public ControladorEstadisticasVP(JFEstadisticasVP vistaEstadisticaVP, VentasDAO modeloVentas) {
        this.vistaEstadisticaVP = vistaEstadisticaVP;
        this.modeloVentas = modeloVentas;
        
        vistaEstadisticaVP.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaEstadisticaVP.btnVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });
    
    }
    public void InicializarEstadisticaVP() {
    
    }
    
    public void btnVerActionPerformed(ActionEvent evt) {
        // ------------------------------
        String buscar = "";
        // Conseguir los datos de las fechas
        int numeroMes = vistaEstadisticaVP.cbFecha.getSelectedIndex() + 1;
        String mes = String.valueOf(numeroMes);
        if(numeroMes<10) {
            mes = "0" + String.valueOf(numeroMes);
        }
        String ano = vistaEstadisticaVP.cbAno.getSelectedItem().toString();
        
        int numeroTipo = vistaEstadisticaVP.cbTipo.getSelectedIndex();
        if(numeroTipo == 0) {
            // Caso se seleccione por mes
            buscar = mes + "/" + ano;
        }
        else {
            // caso se seleccione por año
            buscar =  ano;
        }
        // Obtener todos los idventas que contengan en su fecha el string "buscar"
        ArrayList<Integer> idVentas = modeloVentas.getIdVentas(buscar);
        
        int minIdVentas = getMinIdVentas(idVentas);
        int maxIdVentas = getMaxIdVentas(idVentas);
        // --------------------------------------------
        int optionSelected = vistaEstadisticaVP.cbTipoPrecio.getSelectedIndex() + 1;
        ArrayList<Ventas> arrayCantidadPrecio = new ArrayList<Ventas>();
        arrayCantidadPrecio = modeloVentas.getCantPrecioProducto(optionSelected, minIdVentas, maxIdVentas);
        Float sumaTotal = 0f, precio, total;
        int cantidad;
        for(int i = 0; i < arrayCantidadPrecio.size(); i++ ) {
            cantidad = Integer.parseInt(arrayCantidadPrecio.get(i).getCantidadProducto());
            precio = Float.parseFloat(arrayCantidadPrecio.get(i).getPrecioProducto());
            total = precio*cantidad;
            sumaTotal = sumaTotal + total;
        }
        
        vistaEstadisticaVP.lbTotalVendido.setText(String.valueOf(sumaTotal));
        
    }
    
    public int getMinIdVentas(ArrayList<Integer> idVentas) {
        int minIdVentas = -1;
        if(idVentas.size() > 0){
             minIdVentas = idVentas.get(0);
            for(int i = 1; i < idVentas.size(); i++)
            {
                // Solo falta hacer la funcion minimo y maximo
                if(minIdVentas > idVentas.get(i)){
                    minIdVentas = idVentas.get(i);
                }
            }
        }
        return minIdVentas;
    }
    
    public int getMaxIdVentas(ArrayList<Integer> idVentas) {
        int maxIdVentas = -1;
        if(idVentas.size() > 0){
            maxIdVentas = idVentas.get(0);
            for(int i = 1; i < idVentas.size(); i++)
            {
                // Solo falta hacer la funcion minimo y maximo
                if(maxIdVentas < idVentas.get(i)){
                    maxIdVentas = idVentas.get(i);
                }
            }
        }
        return maxIdVentas;
    }
}
