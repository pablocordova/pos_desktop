package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Clientes;
import modelo.ClientesDAO;
import vista.JFClientes;
import vista.JFClientesMC;
import vista.JFVentasCrear;

public class ControladorClientes {
    // Variables
    JFClientes vistaClientes;
    ClientesDAO modeloClientes;
    JFVentasCrear vistaVentaCrear;
    private TableRowSorter<TableModel> modeloOrdenado;
    
    public ControladorClientes(JFClientes vistaClientes, ClientesDAO modeloClientes) {
        this.vistaClientes = vistaClientes;
        this.modeloClientes= modeloClientes;
        
        vistaClientes.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaClientes.txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
        });
        this.vistaClientes.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        
        this.vistaClientes.btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        
        this.vistaClientes.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
    }
    
    public void InicializarClientes() {
        ArrayList<Clientes> arrayClientes = new ArrayList<Clientes>();
        arrayClientes= modeloClientes.getClientes();
        DefaultTableModel model = (DefaultTableModel) vistaClientes.tbClientes.getModel();
        for (int i = 0; i < arrayClientes.size(); i++) {
            model.addRow(new Object[]{arrayClientes.get(i).id, arrayClientes.get(i).nombres, arrayClientes.get(i).apellidos, arrayClientes.get(i).dni, arrayClientes.get(i).direccion, arrayClientes.get(i).telefono, arrayClientes.get(i).ruc});
        }
        // Para desactivar el movimiento hacia abajo cuando se da enter dentro de la tabla
        createKeybindings(vistaClientes.tbClientes);
    }
    
    void limpiaTabla(){
        try{
            DefaultTableModel temp = (DefaultTableModel) vistaClientes.tbClientes.getModel();
            int a =temp.getRowCount();
            for(int i=0; i<a; i++)
                temp.removeRow(0);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void txtNombreKeyReleased (KeyEvent evt) {
        
        String busqueda = vistaClientes.txtNombre.getText();
        DefaultTableModel model = (DefaultTableModel) vistaClientes.tbClientes.getModel();
        modeloOrdenado = new TableRowSorter<TableModel>(model);
        vistaClientes.tbClientes.setRowSorter(modeloOrdenado);
        // Filtramos las filas de la tabla, por nombre(columna 1)
        modeloOrdenado.setRowFilter(RowFilter.regexFilter("(?i)" + busqueda, 1));
        if(evt.getKeyCode() == 10){
            vistaClientes.tbClientes.requestFocusInWindow();
        }
    }
    
    public void btnEliminarActionPerformed (ActionEvent evt) {
        // Eliminar el cliente que es seleccionado
        DefaultTableModel model = (DefaultTableModel) vistaClientes.tbClientes.getModel();
        int selectedRowIndex = vistaClientes.tbClientes.getSelectedRow();
        if(selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Cliente para eliminar");
            return;
        }
        int realRow = Integer.parseInt(vistaClientes.tbClientes.getModel().getValueAt(vistaClientes.tbClientes.convertRowIndexToModel(selectedRowIndex), 0).toString());

        // Remover de la DB
        modeloClientes.eliminarClientes(realRow);
        // Remover de la vista
        model.removeRow(selectedRowIndex);
    }
    
    public void btnCrearActionPerformed (ActionEvent evt) {
        Clientes clientes = new Clientes();
        clientes.setId(-1);
        JFClientesMC vistaClientesMC= new JFClientesMC();
        ControladorClientesMC clientesMC = new ControladorClientesMC(modeloClientes, vistaClientesMC, this);
        // Inicializar clienteModificar
        clientesMC.InicializarClientesMC(clientes);
        vistaClientesMC.setVisible(true);
        vistaClientesMC.setLocationRelativeTo(null);
    }
    
    public void btnModificarActionPerformed (ActionEvent evt) {
        Clientes clientes = new Clientes();
        // Rellenar con los datos de la fila seleccionada
        DefaultTableModel model = (DefaultTableModel) vistaClientes.tbClientes.getModel();
        //Clientes cliente = new Clientes();
        int selectedRowIndex = vistaClientes.tbClientes.getSelectedRow();

        if(selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Cliente para modificar");
            return;
        }
        int realRow = vistaClientes.tbClientes.convertRowIndexToModel(selectedRowIndex);

        
        clientes.setId(Integer.parseInt(model.getValueAt(realRow, 0).toString()));
        clientes.setNombres((String) model.getValueAt(realRow, 1));
        clientes.setApellidos((String) model.getValueAt(realRow, 2));
        clientes.setDni((String) model.getValueAt(realRow, 3));
        clientes.setDireccion((String) model.getValueAt(realRow, 4));
        clientes.setTelefono((String) model.getValueAt(realRow, 5));
        clientes.setRuc((String) model.getValueAt(realRow, 6));
      
        JFClientesMC vistaClientesMC= new JFClientesMC();
        ControladorClientesMC clientesMC = new ControladorClientesMC(modeloClientes, vistaClientesMC, this);
        // Inicializar clienteModificar
        clientesMC.InicializarClientesMC(clientes);
        vistaClientesMC.setVisible(true);
        vistaClientesMC.setLocationRelativeTo(null);
        
    }
    
    private void createKeybindings(JTable table) {
    table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        table.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(Integer.parseInt(vistaClientes.lblEnVentas.getText()) == 1) {
                    
                    DefaultTableModel model = (DefaultTableModel) vistaClientes.tbClientes.getModel();
                    
                    //Clientes cliente = new Clientes();
                    int selectedRowIndex = vistaClientes.tbClientes.getSelectedRow();
                    int realRow = vistaClientes.tbClientes.convertRowIndexToModel(selectedRowIndex);
                    
                    Clientes cliente = new Clientes();
                    cliente = modeloClientes.getCliente(Integer.parseInt(model.getValueAt(realRow, 0).toString()));
                    
                    // Grabo los datos en el objeto cliente recibido como argumento.
                    vistaVentaCrear.lblIdCliente.setText(model.getValueAt(realRow, 0).toString());
                    vistaVentaCrear.txtCliente.setText((String) model.getValueAt(realRow, 1) + " " + cliente.apellidos);
                    // Con el idCliente preguntar sobre el resto de datos
                    
                    //vistaVentaCrear.lblDni.setText(cliente.dni);
                    vistaVentaCrear.lblRuc.setText(cliente.ruc);
                    vistaVentaCrear.lblDireccion.setText(cliente.direccion);
                    
                    vistaClientes.dispose();
                }
            }
        });
    }
    
    public void ClientesVentas(JFVentasCrear vistaVentaCrear) {
        // Activar la opcion para que cuando se presione la tecla enter, la informacion pase a Ventas
        vistaClientes.lblEnVentas.setText("1");
        this.vistaVentaCrear = vistaVentaCrear;   
    }
    
}
