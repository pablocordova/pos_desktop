
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
import modelo.Ventas;
import modelo.VentasDAO;
import vista.JFVentaVer;
import vista.JFVentas;
import vista.JFVentasCrear;


public class ControladorVentas {
    JFVentas vistaVentas;
    VentasDAO modeloVentas;
    private TableRowSorter<TableModel> modeloOrdenado;
    
    public ControladorVentas(JFVentas vistaVentas, VentasDAO modeloVentas) {
        this.vistaVentas = vistaVentas;
        this.modeloVentas = modeloVentas;
        
        vistaVentas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaVentas.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        
        this.vistaVentas.btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        
        this.vistaVentas.btnVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });
        this.vistaVentas.txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
        });
    }
    
    public void InicializarVentas() {
        ArrayList<Ventas> arrayVentas;
        arrayVentas= modeloVentas.getVentas();
        DefaultTableModel model = (DefaultTableModel) vistaVentas.tbVentas.getModel();
        for (int i = 0; i < arrayVentas.size(); i++) {
            //model.addRow(new Object[]{arrayVentas.get(i).idVenta, arrayVentas.get(i).idCliente, arrayVentas.get(i).nombre, arrayVentas.get(i).fecha, arrayVentas.get(i).hora, arrayVentas.get(i).subtotal, arrayVentas.get(i).igv, arrayVentas.get(i).monto, arrayVentas.get(i).gananciaTotal});
            model.addRow(new Object[]{arrayVentas.get(i).idVenta, arrayVentas.get(i).nombre, arrayVentas.get(i).monto, arrayVentas.get(i).fecha, arrayVentas.get(i).hora, arrayVentas.get(i).comentario});
        }
        vistaVentas.btnCrear.requestFocusInWindow();
    }
    
    public void btnEliminarActionPerformed(ActionEvent evt) {
        // Eliminar el cliente que es seleccionado
        DefaultTableModel model = (DefaultTableModel) vistaVentas.tbVentas.getModel();
        int selectedRowIndex = vistaVentas.tbVentas.getSelectedRow();
        if(selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione una Venta para eliminar");
            return;
        }
        // Correccion para que consiga la correcta fila
        int realRowTable = vistaVentas.tbVentas.convertRowIndexToModel(selectedRowIndex);
        int realRow = Integer.parseInt(vistaVentas.tbVentas.getModel().getValueAt(vistaVentas.tbVentas.convertRowIndexToModel(selectedRowIndex), 0).toString());

        // Remover de la DB
        modeloVentas.eliminarVenta(realRow);
        // Remover de la vista
        model.removeRow(realRowTable);
    }
    
    public void btnCrearActionPerformed(ActionEvent evt) {
        JFVentasCrear vistaVentasCrear= new JFVentasCrear();
        ControladorVentasCrear ventasCrear = new ControladorVentasCrear(modeloVentas, vistaVentasCrear, this);
        ventasCrear.InicializarVentasCrear();
        vistaVentasCrear.setVisible(true);
        vistaVentasCrear.setLocationRelativeTo(null);
    }
    
    public void btnVerActionPerformed(ActionEvent evt) {

        DefaultTableModel model = (DefaultTableModel) vistaVentas.tbVentas.getModel();
        int selectedRowIndex = vistaVentas.tbVentas.getSelectedRow();
        if(selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Venta para ver");
            return;
        }
        int realRow = Integer.parseInt(vistaVentas.tbVentas.getModel().getValueAt(vistaVentas.tbVentas.convertRowIndexToModel(selectedRowIndex), 0).toString());

        JFVentaVer vistaVentaVer= new JFVentaVer();
        ControladorVentaVer ventaVer = new ControladorVentaVer(modeloVentas, vistaVentaVer);
        ventaVer.InicializarVentaVer(realRow);
        vistaVentaVer.setVisible(true);
        vistaVentaVer.setLocationRelativeTo(null);
    }
    
    void limpiaTabla(){
        try{
            DefaultTableModel temp = (DefaultTableModel) vistaVentas.tbVentas.getModel();
            int a =temp.getRowCount();
            for(int i=0; i<a; i++)
                temp.removeRow(0);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void txtNombreKeyReleased (KeyEvent evt) {
        
        String busqueda = vistaVentas.txtNombre.getText();
        DefaultTableModel model = (DefaultTableModel) vistaVentas.tbVentas.getModel();
        modeloOrdenado = new TableRowSorter<TableModel>(model);
        vistaVentas.tbVentas.setRowSorter(modeloOrdenado);
        // Filtramos las filas de la tabla, por nombre(columna 1)
        modeloOrdenado.setRowFilter(RowFilter.regexFilter("(?i)" + busqueda, 1));
        // caso presione enter, pasar a seleccionar a las filas de la tabla
        if(evt.getKeyCode() == 10){
            vistaVentas.tbVentas.requestFocusInWindow();
        }
    }    
}
