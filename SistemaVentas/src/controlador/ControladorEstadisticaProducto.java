package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.WindowConstants;
import modelo.Ventas;
import modelo.VentasDAO;
import vista.JFEstadisticaProducto;
// Libreria jfreechart
import org.jfree.chart.*;
// Librerias solo para el grafico de barras
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ControladorEstadisticaProducto {
    JFEstadisticaProducto vistaEstadisticaProducto;
    VentasDAO modeloVentas;
    public ControladorEstadisticaProducto(JFEstadisticaProducto vistaEstadisticaProducto, VentasDAO modeloVentas) {
        this.vistaEstadisticaProducto = vistaEstadisticaProducto;
        this.modeloVentas = modeloVentas;
        
        vistaEstadisticaProducto.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaEstadisticaProducto.btnVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });
    }
    
    public void InicializarEstadisticaProducto() {
    
    }
    
    public void btnVerActionPerformed(ActionEvent evt) {
        vistaEstadisticaProducto.panelGrafica.removeAll();
        ChartPanel panel;
        JFreeChart chart = null;
        int k = 0;
        String buscar = "";
        // Grafico de barras
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        String nombre = "Productos";
        ArrayList<float[]> idscantidad = new ArrayList<float[]>();

        // Conseguir los datos de las fechas
        int numeroMes = vistaEstadisticaProducto.cbFecha.getSelectedIndex() + 1;
        String mes = String.valueOf(numeroMes);
        if(numeroMes<10) {
            mes = "0" + String.valueOf(numeroMes);
        }
        String ano = vistaEstadisticaProducto.cbAno.getSelectedItem().toString();
        
        int numeroTipo = vistaEstadisticaProducto.cbTipo.getSelectedIndex();
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
        // Ahora en la tabla de ventas por productos adquirir el id del producto
        // y su cantidad respectiva
        if(idVentas.size() != 0){
            for (int i = 0; i < idVentas.size(); i++) {
                System.out.println(Integer.parseInt(idVentas.get(i).toString()));
                ArrayList<Ventas> ventasProductos = modeloVentas.getIdCantidadProductos(Integer.parseInt(idVentas.get(i).toString()));
                for (int j = 0; j < ventasProductos.size(); j++) {
                    System.out.println(ventasProductos.get(j).getIdProducto() +":"+ ventasProductos.get(j).getCantidadProducto());
                    // Aqui analizar para todos los productos
                    // Aqui tengo el idProducto y sus cantidades
                    k = 0;
                    for (k = 0; k < idscantidad.size(); k++) {
                        if(idscantidad.get(k)[0] == ventasProductos.get(j).getIdProducto()) {
                            idscantidad.get(k)[1] = idscantidad.get(k)[1] + Float.parseFloat(ventasProductos.get(j).getCantidadProducto());    
                            break;
                        }
                    }
                    if(k == idscantidad.size()) {
                        idscantidad.add(new float[]{ventasProductos.get(j).getIdProducto(), Float.parseFloat(ventasProductos.get(j).getCantidadProducto())});
                    }  
                }
            }
        }
                    
        for(int z = 0; z < idscantidad.size(); z++) {
            // orden:  cantidad, valor(idProducto, nombreprodcuto)
            System.out.println((int) idscantidad.get(z)[0]);
            String nameProducto = modeloVentas.getNameProducto((int) idscantidad.get(z)[0]);
            data.addValue(idscantidad.get(z)[1], nombre, nameProducto);
        }
        chart = ChartFactory.createBarChart("Graficos de barra", "Productos", 
                "Cantidad", data, PlotOrientation.VERTICAL, true, true, true);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDomainGridlinesVisible(true);
                
        panel = new ChartPanel(chart);
        panel.setBounds(0, 0, vistaEstadisticaProducto.panelGrafica.getWidth(), vistaEstadisticaProducto.panelGrafica.getHeight());
        
        vistaEstadisticaProducto.panelGrafica.add(panel);
        vistaEstadisticaProducto.panelGrafica.repaint();
    }
}
