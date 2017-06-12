package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Clientes;
import modelo.ClientesDAO;
import modelo.Ventas;
import modelo.VentasDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import vista.JFEstadisticaCliente;

public class ControladorEstadisticaCliente {
    JFEstadisticaCliente vistaEstadisticaCliente;
    ClientesDAO modeloClientes;
    private TableRowSorter<TableModel> modeloOrdenado;
    public ControladorEstadisticaCliente(JFEstadisticaCliente vistaEstadisticaCliente, ClientesDAO modeloClientes) {
        this.vistaEstadisticaCliente = vistaEstadisticaCliente;
        this.modeloClientes = modeloClientes;
        
        vistaEstadisticaCliente.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaEstadisticaCliente.txtCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                txtClienteKeyReleased(evt);
            }
        });
        this.vistaEstadisticaCliente.btnVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });
    }
    
    public void InicializarEstadisticaCliente() {
        ArrayList<Clientes> arrayClientes = new ArrayList<Clientes>();
        arrayClientes= modeloClientes.getClientes();
        DefaultTableModel model = (DefaultTableModel) vistaEstadisticaCliente.tbClientes.getModel();
        for (int i = 0; i < arrayClientes.size(); i++) {
            model.addRow(new Object[]{arrayClientes.get(i).id, arrayClientes.get(i).nombres, arrayClientes.get(i).apellidos, arrayClientes.get(i).dni});
        }
    }
    
    public void btnVerActionPerformed(ActionEvent evt) {
        vistaEstadisticaCliente.panelProductos.removeAll();
        ChartPanel panel;
        JFreeChart chart = null;
        VentasDAO modeloVentas = new VentasDAO();
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        String nombre = "Productos";
        // Aqui es todo, aqui me dejo el alma.
        DefaultTableModel model = (DefaultTableModel) vistaEstadisticaCliente.tbClientes.getModel();
        //Clientes cliente = new Clientes();
        int selectedRowIndex = vistaEstadisticaCliente.tbClientes.getSelectedRow();
        if(selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Cliente");
            return;
        }
        int idCliente = Integer.parseInt(vistaEstadisticaCliente.tbClientes.getModel().getValueAt(vistaEstadisticaCliente.tbClientes.convertRowIndexToModel(selectedRowIndex), 0).toString());
        
        ArrayList<int[]> idscantidad = new ArrayList<int[]>();
        int k = 0;
        
        ArrayList<Integer> idVentas = modeloVentas.getIdVentasCliente(idCliente);
        //-------------------------------------
        if(idVentas.size() != 0){
            for (int i = 0; i < idVentas.size(); i++) {
                ArrayList<Ventas> ventasProductos = modeloVentas.getIdCantidadProductos(Integer.parseInt(idVentas.get(i).toString()));
                for (int j = 0; j < ventasProductos.size(); j++) {
                    // Aqui analizar para todos los productos
                    // Aqui tengo el idProducto y sus cantidades
                    k = 0;
                    for (k = 0; k < idscantidad.size(); k++) {
                        if(idscantidad.get(k)[0] == ventasProductos.get(j).getIdProducto()) {
                            idscantidad.get(k)[1] = idscantidad.get(k)[1] + Integer.parseInt(ventasProductos.get(j).getCantidadProducto()); 
                            break;
                        }
                    }
                    if(k == idscantidad.size()) {
                        idscantidad.add(new int[]{ventasProductos.get(j).getIdProducto(), Integer.parseInt(ventasProductos.get(j).getCantidadProducto())});
                    }  
                }
            }
        }
        
        for(int z = 0; z < idscantidad.size(); z++) {
            // orden:  cantidad, valor(idProducto, nombreprodcuto)
            String nameProducto = modeloVentas.getNameProducto(idscantidad.get(z)[0]);
            data.addValue(idscantidad.get(z)[1], nombre, nameProducto);
        }
        
        chart = ChartFactory.createBarChart("Graficos de barra", "Productos", 
                "Cantidad", data, PlotOrientation.VERTICAL, true, true, true);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDomainGridlinesVisible(true);
                
        panel = new ChartPanel(chart);
        panel.setBounds(0, 0, vistaEstadisticaCliente.panelProductos.getWidth(), vistaEstadisticaCliente.panelProductos.getHeight());
        
        vistaEstadisticaCliente.panelProductos.add(panel);
        vistaEstadisticaCliente.panelProductos.repaint();
    }
    
    public void txtClienteKeyReleased(KeyEvent evt) {
        String busqueda = vistaEstadisticaCliente.txtCliente.getText();
        DefaultTableModel model = (DefaultTableModel) vistaEstadisticaCliente.tbClientes.getModel();
        modeloOrdenado = new TableRowSorter<TableModel>(model);
        vistaEstadisticaCliente.tbClientes.setRowSorter(modeloOrdenado);
        // Filtramos las filas de la tabla, por nombre(columna 1)
        modeloOrdenado.setRowFilter(RowFilter.regexFilter("(?i)" + busqueda, 1));
        if(evt.getKeyCode() == 10){
            vistaEstadisticaCliente.tbClientes.requestFocusInWindow();
        }
    }
    
}
