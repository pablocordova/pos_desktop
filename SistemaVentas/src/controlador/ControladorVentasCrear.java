
package controlador;

import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import modelo.ClientesDAO;
import modelo.Productos;
import modelo.ProductosDAO;
import modelo.Ventas;
import modelo.VentasDAO;
import vista.JFClientes;
import vista.JFVentasCrear;

public class ControladorVentasCrear {
    VentasDAO modeloVentas;
    JFVentasCrear vistaVentasCrear;
    ControladorVentas controladorVentas;
    private TableRowSorter<TableModel> modeloOrdenado;
    
    public  ControladorVentasCrear(VentasDAO modeloVentas, JFVentasCrear vistaVentasCrear, ControladorVentas controladorVentas) {
        this.modeloVentas = modeloVentas;
        this.vistaVentasCrear = vistaVentasCrear;
        this.controladorVentas = controladorVentas;
        
        vistaVentasCrear.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaVentasCrear.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        this.vistaVentasCrear.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        this.vistaVentasCrear.btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        this.vistaVentasCrear.txtProducto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                txtProductoKeyReleased(evt);
            }
        });
        this.vistaVentasCrear.cbPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                txtcbPrecioKeyReleased(evt);
            }
        });
        this.vistaVentasCrear.btnVuelto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnVueltoActionPerformed(evt);
            }
        });
        this.vistaVentasCrear.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        this.vistaVentasCrear.txtCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
        });
        this.vistaVentasCrear.tbProductos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                tbProductosKeyReleased(evt);
            }
        });
        this.vistaVentasCrear.tbProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                tbProductosMouseClicked(evt);
            }
        });
        
    }
    public void InicializarVentasCrear() {
        // Rellenar la tabla Productos con todos los productos
        ProductosDAO modeloProductos = new ProductosDAO();
        ArrayList<Productos> arrayProductos;
        arrayProductos = modeloProductos.getProductos();
        DefaultTableModel model = (DefaultTableModel) vistaVentasCrear.tbProductos.getModel();
        for (int i = 0; i < arrayProductos.size(); i++) {
            model.addRow(new Object[]{arrayProductos.get(i).id, arrayProductos.get(i).nombre, arrayProductos.get(i).marca, arrayProductos.get(i).categoria, arrayProductos.get(i).cantidad, arrayProductos.get(i).precio1_10, arrayProductos.get(i).precio10_20, arrayProductos.get(i).precio20_, arrayProductos.get(i).costo});
        }
        // Para desactivar el movimiento hacia abajo cuando se da enter dentro de la tabla
        createKeybindings(vistaVentasCrear.tbProductos);
        //vistaVentasCrear.btnBuscar.requestFocus();
        vistaVentasCrear.txtProducto.requestFocusInWindow();
    }

    public void btnBuscarActionPerformed(ActionEvent evt) {
        JFClientes vistaClientes = new JFClientes();
        ClientesDAO modeloClientes = new ClientesDAO();
        ControladorClientes clientes = new ControladorClientes(vistaClientes, modeloClientes);
        vistaClientes.setVisible(true);
        vistaClientes.setLocationRelativeTo(null);
        clientes.InicializarClientes();
        clientes.ClientesVentas(vistaVentasCrear);
    }
    public void btnGuardarActionPerformed(ActionEvent evt) {
        // Verificar que todos los datos necesarios existan
        String idClienteElegido = vistaVentasCrear.lblIdCliente.getText();
        if(idClienteElegido.isEmpty()){
            JOptionPane.showMessageDialog(null, "Ingrese Cliente");
            return;
        }
        String totalVentas = vistaVentasCrear.lblTotal.getText();
        if(totalVentas.equals("-")){
            JOptionPane.showMessageDialog(null, "Ingrese Productos");
            return;
        }
        
        Ventas venta = new Ventas();
        ProductosDAO modeloProductos = new ProductosDAO();
        int idVentasGenerado = 0;
        Date date = new Date();
        DefaultTableModel model = (DefaultTableModel) vistaVentasCrear.tbVenta.getModel();
        
        // adquirir fecha y hora en formato string
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Verificar si ninguno idProducto es repetido
        String[] idsProductos = new String[model.getRowCount()];
        for (int i = 0; i < model.getRowCount(); i++) {
            idsProductos[i] = model.getValueAt(i, 0).toString();
        }
        if(checkDuplicate(idsProductos)){
            JOptionPane.showMessageDialog(null,"Existen Productos Repetidos");
            return;
        }
        
        venta.setIdCliente(Integer.parseInt(idClienteElegido));
        venta.setNombre(vistaVentasCrear.txtCliente.getText());
        venta.setFecha(dateFormat.format(date));
        venta.setHora(hourFormat.format(date));
        venta.setSubtotal(vistaVentasCrear.lblSubTotal.getText());
        venta.setIgv(vistaVentasCrear.lblIgv.getText());
        venta.setMonto(totalVentas);
        venta.setGananciaTotal(vistaVentasCrear.lblGananciaTotal.getText());
        
        idVentasGenerado = modeloVentas.crearVenta(venta);

        //----------------------------------
        // Esto se debe repetir en un bucle y ser obtenido de la tabla
        
        // Rellenar con los datos de la fila seleccionada

        for (int i = 0; i < model.getRowCount(); i++) {
            venta.setIdVenta(idVentasGenerado); // averiguar
            venta.setIdProducto(Integer.parseInt(model.getValueAt(i, 0).toString()));
            venta.setNombre((String) model.getValueAt(i, 1));
            venta.setCantidadProducto((String) model.getValueAt(i, 2));
            venta.setPrecioProducto((String) model.getValueAt(i, 3));
            venta.setTipoPrecioProducto((String) model.getValueAt(i, 5));
            venta.setGananciaProducto((String) model.getValueAt(i, 6)); //averiguar
            modeloVentas.crearVentaProducto(venta);
            // Tambien modificar la cantidad en cada producto
            // Pasos: obtener la cantidad de cada producto con el idProducto
            // restarlo con la cantidad pedida
            Productos producto = modeloProductos.getProducto(Integer.parseInt(model.getValueAt(i, 0).toString()));
            // Cantidad que sobra despues de la compra
            Integer cantidadResultante = Integer.parseInt(producto.cantidad) - Integer.parseInt((String) model.getValueAt(i, 2));
            // Ahora guardar este resultado
            modeloProductos.actualizarCantidadProducto(Integer.parseInt(model.getValueAt(i, 0).toString()), cantidadResultante.toString());
        }
        ///----------- 
        vistaVentasCrear.dispose();
        controladorVentas.limpiaTabla();
        controladorVentas.InicializarVentas();
    }
    public void btnCancelarActionPerformed(ActionEvent evt) {
        vistaVentasCrear.dispose();
    }
    public void txtProductoKeyReleased (KeyEvent evt) {
        
        String busqueda = vistaVentasCrear.txtProducto.getText();
        DefaultTableModel model = (DefaultTableModel) vistaVentasCrear.tbProductos.getModel();
        modeloOrdenado = new TableRowSorter<TableModel>(model);
        vistaVentasCrear.tbProductos.setRowSorter(modeloOrdenado);
        // Filtramos las filas de la tabla, por nombre(columna 1)
        modeloOrdenado.setRowFilter(RowFilter.regexFilter("(?i)" + busqueda, 1));
        // caso presione enter, pasar a seleccionar a las filas de la tabla
        if(evt.getKeyCode() == 10){
            vistaVentasCrear.tbProductos.requestFocusInWindow();
        }
    }
    
    public void txtCantidadKeyReleased (KeyEvent evt) {
        
        if(evt.getKeyCode() == 10 && !vistaVentasCrear.txtCantidad.getText().isEmpty()){
            vistaVentasCrear.cbPrecio.requestFocusInWindow();
        }
    }
    
    private void createKeybindings(JTable table) {
    table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        table.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // limpiar el combobox de precios
                // limpiarvistaVentasCrear
                vistaVentasCrear.txtProductoSeleccionado.setText("");
                vistaVentasCrear.txtCantidad.setText("");
                vistaVentasCrear.cbPrecio.removeAllItems();
                //vistaVentasCrear.cbPrecio.removeAllItems();
                DefaultTableModel model = (DefaultTableModel) vistaVentasCrear.tbProductos.getModel();
                //Clientes cliente = new Clientes();
                int selectedRowIndex = vistaVentasCrear.tbProductos.getSelectedRow();
                int realRow = vistaVentasCrear.tbProductos.convertRowIndexToModel(selectedRowIndex);
                
                // Grabo los datos en el objeto cliente recibido como argumento.
                vistaVentasCrear.lblIdProducto.setText(model.getValueAt(realRow, 0).toString());
                vistaVentasCrear.txtProductoSeleccionado.setText((String) model.getValueAt(realRow, 1));
                vistaVentasCrear.lblCantidadProducto.setText((String) model.getValueAt(realRow, 4));
                vistaVentasCrear.lblCostoProducto.setText((String) model.getValueAt(realRow, 8));
                vistaVentasCrear.cbPrecio.addItem((String) model.getValueAt(realRow, 5));
                vistaVentasCrear.cbPrecio.addItem((String) model.getValueAt(realRow, 6));
                vistaVentasCrear.cbPrecio.addItem((String) model.getValueAt(realRow, 7));
                
                vistaVentasCrear.txtCantidad.requestFocusInWindow();
            }
        });
    }
    
    public void txtcbPrecioKeyReleased(KeyEvent evt) {
        try{
            if(evt.getKeyCode() == 10) {
                String cantidadPedida = vistaVentasCrear.txtCantidad.getText();

                if(cantidadPedida.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese cantidad");
                    return;
                }

                String precioOpciones = "";
                try{
                precioOpciones = vistaVentasCrear.cbPrecio.getSelectedItem().toString();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Ingrese producto");
                }
                
                // Llenar la tabla venta y revizar stock
                DefaultTableModel model = (DefaultTableModel) vistaVentasCrear.tbVenta.getModel();
                Float cantidad = Float.parseFloat(cantidadPedida);
                Float cantidadProducto = Float.parseFloat(vistaVentasCrear.lblCantidadProducto.getText());

                if(cantidadProducto < cantidad) {
                    JOptionPane.showMessageDialog(null,"No hay Suficiente Stock");
                    return;
                }

                Float costo = Float.parseFloat(vistaVentasCrear.lblCostoProducto.getText());
                Float precio = Float.parseFloat(precioOpciones);
                String totalPrecioProducto = String.valueOf(cantidad*precio);
                String gananciaProducto = String.valueOf((precio-costo)*cantidad);
                model.addRow(new Object[]{vistaVentasCrear.lblIdProducto.getText(), vistaVentasCrear.txtProductoSeleccionado.getText(), vistaVentasCrear.txtCantidad.getText(), vistaVentasCrear.cbPrecio.getSelectedItem().toString(), totalPrecioProducto, String.valueOf(vistaVentasCrear.cbPrecio.getSelectedIndex()+1), gananciaProducto});
                
                // Debe recorrer toda la tabla y sumar los totales para luego sacar el subtotal e igv
                calculosTablaVenta();
                
                vistaVentasCrear.txtProducto.requestFocusInWindow();
                // limpiarvistaVentasCrear
                vistaVentasCrear.txtProductoSeleccionado.setText("");
                vistaVentasCrear.txtCantidad.setText("");
                vistaVentasCrear.cbPrecio.removeAllItems();
            }
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Error general");
        }
    }
    
    
    public void calculosTablaVenta() {
        // Debe recorrer toda la tabla y sumar los totales para luego sacar el subtotal e igv
        DefaultTableModel model = (DefaultTableModel) vistaVentasCrear.tbVenta.getModel();
        double SumaTotalProductos = 0f;
        double SumaGananciaProductos = 0f;
        for (int i = 0; i < model.getRowCount(); i++) {
            // Conseguir todos los totales que se encuentra en la posicion 4, contando tb 0
            SumaTotalProductos = SumaTotalProductos + Double.parseDouble((String)model.getValueAt(i, 4));
            SumaGananciaProductos = SumaGananciaProductos + Double.parseDouble((String)model.getValueAt(i, 6));
        }
        double subTotal = SumaTotalProductos/1.18f;
        subTotal = redondearDecimales(subTotal,2);
        double Igv = redondearDecimales(SumaTotalProductos - subTotal,2);
        vistaVentasCrear.lblTotal.setText(String.valueOf(SumaTotalProductos));
        vistaVentasCrear.lblSubTotal.setText(String.valueOf(subTotal));
        vistaVentasCrear.lblIgv.setText(String.valueOf(Igv));
        vistaVentasCrear.lblGananciaTotal.setText(String.valueOf(SumaGananciaProductos));
        //---------------
    }
    
    public void btnVueltoActionPerformed(ActionEvent evt) {
        Float pago = Float.parseFloat(vistaVentasCrear.txtPago.getText());
        Float total = Float.parseFloat(vistaVentasCrear.lblTotal.getText());
        vistaVentasCrear.lblVuelto.setText(String.valueOf(pago-total));
        
    }
    
    public void btnEliminarActionPerformed(ActionEvent evt) {
        // Eliminar el producto que es seleccionado
        DefaultTableModel model = (DefaultTableModel) vistaVentasCrear.tbVenta.getModel();
        int selectedRowIndex = vistaVentasCrear.tbVenta.getSelectedRow();
        // Remover de la vista
        model.removeRow(selectedRowIndex);
        // actualizar el resto de labels
        calculosTablaVenta();
    }
    
    public void tbProductosKeyReleased(KeyEvent evt) {
        // Caso se presionen las teclas direccionales arriba o abajo respectivamente
        if(evt.getKeyCode() == 38 || evt.getKeyCode() == 40){
            mostrarImagen();
        }
    }
    
    public void tbProductosMouseClicked(MouseEvent evt) {
        //Caso click en la tabl, para mostrar imagen
        mostrarImagen();
    }
    
    public void mostrarImagen() {
        DefaultTableModel model = (DefaultTableModel) vistaVentasCrear.tbProductos.getModel();
        //Clientes cliente = new Clientes();
        int selectedRowIndex = vistaVentasCrear.tbProductos.getSelectedRow();
        int realRow = vistaVentasCrear.tbProductos.convertRowIndexToModel(selectedRowIndex);

        // Grabo los datos en el objeto cliente recibido como argumento.
        int idProductoImagen = Integer.parseInt(model.getValueAt(realRow, 0).toString());
        ProductosDAO modeloProductos = new ProductosDAO();
        Productos producto = modeloProductos.getProducto(idProductoImagen);
        try{
            ImageIcon icon = new ImageIcon(readFully(producto.getInputImagen()));
            vistaVentasCrear.lblImagen.setIcon(icon);
        }
        catch(Exception ex){
            vistaVentasCrear.lblImagen.setIcon(null);
            //System.out.println("Error Mostrar imagen" + ex);
        }
    }
    
    //Copiado de internet
    //http://javarevisited.blogspot.pe/2012/02/how-to-check-or-detect-duplicate.html
    public static boolean checkDuplicate(String[] input) {
        Set tempSet = new HashSet();
        for (String str : input) {
            if (!tempSet.add(str)) {
                return true;
            }
        }
        return false;
    }
    
    // Copiado de internet
    // http://www.aprenderaprogramar.com/index.php?option=com_content&view=article&id=960:java-redondear-a-2-o-mas-decimales-errores-precision-bigdecimal-roundingmode-biginteger-cu00907c&catid=58:curso-lenguaje-programacion-java-nivel-avanzado-i&Itemid=180
    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
    
    //Copiado de internet
    
    public static byte[] readFully(InputStream input) throws IOException
    {
        byte[] buffer = new byte[40537];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1)
        {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }
}
