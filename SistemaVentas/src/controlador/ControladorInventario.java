
package controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Productos;
import modelo.ProductosDAO;
import vista.JFInventario;

public class ControladorInventario {
    JFInventario vistaInventario;
    ProductosDAO modeloProductos;
    private TableRowSorter<TableModel> modeloOrdenado;
    
    public ControladorInventario(JFInventario vistaInventario, ProductosDAO modeloProductos) {
        this.vistaInventario = vistaInventario;
        this.modeloProductos = modeloProductos;
        vistaInventario.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaInventario.txtProducto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                txtProductoKeyReleased(evt);
            }
        });
    }
    
    public void InicializarInventario() {
        ArrayList<Productos> arrayProductos = new ArrayList<Productos>();
        arrayProductos= modeloProductos.getProductos();
        DefaultTableModel model = (DefaultTableModel) vistaInventario.tbInventario.getModel();
        for (int i = 0; i < arrayProductos.size(); i++) {
            model.addRow(new Object[]{arrayProductos.get(i).nombre, arrayProductos.get(i).categoria, arrayProductos.get(i).marca, arrayProductos.get(i).cantidad});
        }
    }
    public void txtProductoKeyReleased (KeyEvent evt) {
        
        String busqueda = vistaInventario.txtProducto.getText();
        DefaultTableModel model = (DefaultTableModel) vistaInventario.tbInventario.getModel();
        modeloOrdenado = new TableRowSorter<TableModel>(model);
        vistaInventario.tbInventario.setRowSorter(modeloOrdenado);
        // Tabla, con columna 0
        modeloOrdenado.setRowFilter(RowFilter.regexFilter("(?i)" + busqueda, 0));
    }
}
