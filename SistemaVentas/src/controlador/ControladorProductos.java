
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
import modelo.Productos;
import modelo.ProductosDAO;
import vista.JFProductos;
import vista.JFProductosMC;

public class ControladorProductos {
    // Variables
    JFProductos vistaProductos;
    ProductosDAO modeloProductos;
    private TableRowSorter<TableModel> modeloOrdenado;
    // Constructor
    public ControladorProductos(JFProductos vistaProductos, ProductosDAO modeloProductos) {
        this.vistaProductos = vistaProductos;
        this.modeloProductos = modeloProductos;
        
        vistaProductos.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaProductos.txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
        });
        this.vistaProductos.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        
        this.vistaProductos.btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        
        this.vistaProductos.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        
    }
    
    public void InicializarProductos() {
        ArrayList<Productos> arrayProductos = new ArrayList<Productos>();
        arrayProductos = modeloProductos.getProductos();
        DefaultTableModel model = (DefaultTableModel) vistaProductos.tbProductos.getModel();
        for (int i = 0; i < arrayProductos.size(); i++) {
            model.addRow(new Object[]{arrayProductos.get(i).id, arrayProductos.get(i).nombre, arrayProductos.get(i).marca, arrayProductos.get(i).categoria, arrayProductos.get(i).costo, arrayProductos.get(i).precio1_10, arrayProductos.get(i).precio10_20, arrayProductos.get(i).precio20_, arrayProductos.get(i).cantidad});
        }
    }
    
    void limpiaTabla(){
        try{
            DefaultTableModel temp = (DefaultTableModel) vistaProductos.tbProductos.getModel();
            int a =temp.getRowCount();
            for(int i=0; i<a; i++)
                temp.removeRow(0);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void txtNombreKeyReleased (KeyEvent evt) {
        
        String busqueda = vistaProductos.txtNombre.getText();
        DefaultTableModel model = (DefaultTableModel) vistaProductos.tbProductos.getModel();
        modeloOrdenado = new TableRowSorter<TableModel>(model);
        vistaProductos.tbProductos.setRowSorter(modeloOrdenado);
        // Filtramos las filas de la tabla, por nombre(columna 1)
        modeloOrdenado.setRowFilter(RowFilter.regexFilter("(?i)" + busqueda, 1));
        if(evt.getKeyCode() == 10){
            vistaProductos.tbProductos.requestFocusInWindow();
        }
    }
    
    public void btnEliminarActionPerformed (ActionEvent evt) {
        // Eliminar el cliente que es seleccionado
        DefaultTableModel model = (DefaultTableModel) vistaProductos.tbProductos.getModel();
        int selectedRowIndex = vistaProductos.tbProductos.getSelectedRow();
        if(selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Producto para eliminar");
            return;
        }
        int realRow = Integer.parseInt(vistaProductos.tbProductos.getModel().getValueAt(vistaProductos.tbProductos.convertRowIndexToModel(selectedRowIndex), 0).toString());
        int realRowTable = vistaProductos.tbProductos.convertRowIndexToModel(selectedRowIndex);
        
        // Remover de la DB
        modeloProductos.eliminarProductos(realRow);
        // Remover de la vista
        model.removeRow(realRowTable);
    }
    
    public void btnCrearActionPerformed (ActionEvent evt) {
        Productos producto = new Productos();
        producto.setId(-1);
        JFProductosMC vistaProductosMC= new JFProductosMC();
        ControladorProductosMC productosMC = new ControladorProductosMC(modeloProductos, vistaProductosMC, this);
        // Inicializar clienteModificar
        productosMC.InicializarProductosMC(producto);
        vistaProductosMC.setVisible(true);
        vistaProductosMC.setLocationRelativeTo(null);
    }
    
    public void btnModificarActionPerformed (ActionEvent evt) {
        Productos producto = new Productos();
        
        // Rellenar con los datos de la fila seleccionada
        DefaultTableModel model = (DefaultTableModel) vistaProductos.tbProductos.getModel();
        //Clientes cliente = new Clientes();
        int selectedRowIndex = vistaProductos.tbProductos.getSelectedRow();
        if(selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Producto para modificar");
            return;
        }
        int realRow = vistaProductos.tbProductos.convertRowIndexToModel(selectedRowIndex);
        
        producto.setId(Integer.parseInt(model.getValueAt(realRow, 0).toString()));
        producto.setNombre((String) model.getValueAt(realRow, 1));
        producto.setMarca((String) model.getValueAt(realRow, 2));
        producto.setCategoria((String) model.getValueAt(realRow, 3));
        producto.setCosto((String) model.getValueAt(realRow, 4));
        producto.setPrecio1_10((String) model.getValueAt(realRow, 5));
        producto.setPrecio10_20((String) model.getValueAt(realRow, 6));
        producto.setPrecio20_((String) model.getValueAt(realRow, 7));
        producto.setCantidad((String) model.getValueAt(realRow, 8));
        producto.setInputImagen(modeloProductos.getImagenProducto(Integer.parseInt(model.getValueAt(realRow, 0).toString())));
        ///-----------
        
        JFProductosMC vistaProductosMC= new JFProductosMC();
        ControladorProductosMC productosMC = new ControladorProductosMC(modeloProductos, vistaProductosMC, this);
        productosMC.InicializarProductosMC(producto);
        vistaProductosMC.setVisible(true);
        vistaProductosMC.setLocationRelativeTo(null);
    }
    
}
