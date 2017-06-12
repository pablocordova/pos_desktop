
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import modelo.Productos;
import modelo.ProductosDAO;
import vista.JFProductosMC;

public class ControladorProductosMC {
    ProductosDAO modeloProductos;  
    JFProductosMC vistaProductosMC;
    ControladorProductos controladorProductos;
    Productos producto;
    InputStream imageInputStream;
    public ControladorProductosMC(ProductosDAO modeloProductos, JFProductosMC vistaProductosMC, ControladorProductos controladorProductos) {
        this.modeloProductos = modeloProductos;
        this.vistaProductosMC = vistaProductosMC;
        this.controladorProductos = controladorProductos;
        
        vistaProductosMC.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaProductosMC.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        
        this.vistaProductosMC.btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        
        this.vistaProductosMC.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        this.vistaProductosMC.btnSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });
    }
    
    public void InicializarProductosMC(Productos producto){
        // Caso sea actualizar, rellenar los campos con los datos recibidos en producto
        vistaProductosMC.lblTitle.setText("CREAR PRODUCTO");
        if(producto.id > 0){
            vistaProductosMC.lblTitle.setText("MODIFICAR PRODUCTO");
            vistaProductosMC.txtNombre.setText(producto.nombre);
            vistaProductosMC.txtMarca.setText(producto.marca);
            vistaProductosMC.cbCategoria.setSelectedItem(producto.categoria);
            vistaProductosMC.lblCosto.setText(producto.costo);
            vistaProductosMC.txtPrecio1_10.setText(producto.precio1_10);
            vistaProductosMC.txtPrecio10_20.setText(producto.precio10_20);
            vistaProductosMC.txtPrecio20_.setText(producto.precio20_);
            vistaProductosMC.lblCantidad.setText(producto.cantidad);
            imageInputStream = producto.inputImagen;
            try {
                imageInputStream = producto.inputImagen;
                //byte[] bytes = IOUtils.toByteArray(imageInputStream);
                ImageIcon icon = new ImageIcon(readFully(imageInputStream));
                vistaProductosMC.lblImagen.setIcon(icon);
            } catch (Exception ex) {
                System.out.println("Imagen no encontrada");
            }
        }
        vistaProductosMC.idProducto.setText(String.valueOf(producto.id));
    }
    
    public void btnGuardarActionPerformed (ActionEvent evt){
        producto = new Productos();
        producto.id = Integer.parseInt(vistaProductosMC.idProducto.getText());
        producto.nombre = vistaProductosMC.txtNombre.getText();
        producto.marca = vistaProductosMC.txtMarca.getText();
        producto.categoria = vistaProductosMC.cbCategoria.getSelectedItem().toString();
        //producto.costo = vistaProductosMC.lblCosto.getText();
        producto.costo = "0";
        producto.precio1_10 = vistaProductosMC.txtPrecio1_10.getText();
        producto.precio10_20 = vistaProductosMC.txtPrecio10_20.getText();
        producto.precio20_ = vistaProductosMC.txtPrecio20_.getText();
        producto.cantidad = vistaProductosMC.lblCantidad.getText();
        
        try {
            Float number = Float.parseFloat(producto.precio1_10);
            number = Float.parseFloat(producto.precio10_20);
            number = Float.parseFloat(producto.precio10_20);
            number = Float.parseFloat(producto.precio20_);
            int num = Integer.parseInt(producto.cantidad);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Formato de numeros invalido");
        }

        try {
            producto.inputImagen = imageInputStream;
        } catch (Exception ex) {
            System.out.println("Imagen no encontrada");
        }

        // Para detectar si no ha rellenado los datos obligatorios
        if(producto.nombre.equals("") || producto.marca.equals("") || producto.costo.equals("") || producto.precio1_10.equals("")) {
            JOptionPane.showMessageDialog(null, "Rellene campos obligatorios","Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Detectar en cual de los 2 estados se cuentra actualmente:
        // -1 : guardar clientes
        // >0 : actualizar cliente, el cual el idcliente es dado por este numero.
        int opcion = Integer.parseInt(vistaProductosMC.idProducto.getText());
        if(opcion > 0) {
            // ACTUALIZAR CLIENTE
            modeloProductos.actualizarProducto(producto);
        }
        else {
            // GENERAR NUEVO CLIENTE
            int result = modeloProductos.crearProducto(producto);
            if(result == 1) {
                // caso se guardo correctamente en la BD
                JOptionPane.showMessageDialog(null, "Guardado Correctamente");
                vistaProductosMC.dispose();
            }
            else {
                // Caso se halla cometido alguna violacion en los datos guardados en la BD
                JOptionPane.showMessageDialog(null, "Rellene correctamente","Alerta", JOptionPane.ERROR_MESSAGE);
            }
        }
        vistaProductosMC.dispose();
        controladorProductos.limpiaTabla();
        controladorProductos.InicializarProductos();
    }
    public void btnCancelarActionPerformed (ActionEvent evt){
        vistaProductosMC.dispose();
    }
    
    public void btnAgregarActionPerformed (ActionEvent evt){
        Float costoAgregar;
        Integer cantidadAgregar;
        DecimalFormat df = new DecimalFormat("###.##");
        try{
            // Obtener el costo y la cantidad que queremos agregar
            //costoAgregar = Float.parseFloat(vistaProductosMC.txtCosto.getText());
            costoAgregar = 0F;
            cantidadAgregar = Integer.parseInt(vistaProductosMC.txtCantidad.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ingrese datos validos");
            return;
        }
        
        // Obtener el costo y la cantidad actual
        Float costoActual = Float.parseFloat(vistaProductosMC.lblCosto.getText());
        Integer cantidadActual = Integer.parseInt(vistaProductosMC.lblCantidad.getText());
        
        // Antes de sumar cantidades, preguntar de advertencia ya que es importante esto.
        int resp = JOptionPane.showConfirmDialog(null,"Se añadira Costo: " + costoAgregar.toString() + ", Cantidad: " + cantidadAgregar.toString());
        if (JOptionPane.OK_OPTION == resp){
            // Sumar cantidades
            Integer sumaCantidades = cantidadAgregar + cantidadActual;
            vistaProductosMC.lblCantidad.setText(sumaCantidades.toString());

            // Sumar costos, colocando el promedio
            if(costoActual == 0) {
               // Caso aun recien se coloque el primer coste, pasarlo defrente
               vistaProductosMC.lblCosto.setText(costoAgregar.toString());
            }
            else {
               // Caso ya exista un costo actual
               Float sumaCostos = (costoAgregar + costoActual)/2;
               // Redonder el valor solo a 2 decimales
               vistaProductosMC.lblCosto.setText(df.format(sumaCostos));
            }
        } 
    }
    
    public void btnSeleccionarActionPerformed (ActionEvent evt){
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            try{
                Image image = ImageIO.read(selectedFile);
                ImageIcon icon = new ImageIcon(image);
                if(icon.getIconWidth()!= 208 || icon.getIconHeight()!= 127){
                    JOptionPane.showMessageDialog(null, "Ingrese imagen 208x127");
                    return;
                }
                //Image imageResized = image.getScaledInstance(208, 127, Image.SCALE_DEFAULT);
                imageInputStream = new FileInputStream(selectedFile.getPath());
                vistaProductosMC.lblImagen.setIcon(icon);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Solo soporta formato png");
                //System.out.println("Error seleccionar imagen" + ex);
            }
        }
    }
    
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
