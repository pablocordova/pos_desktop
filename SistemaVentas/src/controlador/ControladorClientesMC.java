package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import modelo.Clientes;
import modelo.ClientesDAO;
import vista.JFClientesMC;

public class ControladorClientesMC {
    ClientesDAO modeloClientes;  
    JFClientesMC vistaClientesMC;
    ControladorClientes controladorClientes;
    Clientes cliente;
    public ControladorClientesMC(ClientesDAO modeloClientes, JFClientesMC vistaClientesMC, ControladorClientes controladorClientes) {
        this.modeloClientes= modeloClientes;
        this.vistaClientesMC = vistaClientesMC;
        this.controladorClientes = controladorClientes;
        
        vistaClientesMC.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaClientesMC.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        
        this.vistaClientesMC.btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
    }
    
    public void InicializarClientesMC(Clientes cliente){
        vistaClientesMC.lblModificarCrearClientes.setText("CREAR CLIENTE");
        // Caso sea actualizar, rellenar los campos con los datos recibidos en producto
        if(cliente.id > 0){
            vistaClientesMC.lblModificarCrearClientes.setText("MODIFICAR CLIENTE");
            vistaClientesMC.txtNombres.setText(cliente.nombres);
            vistaClientesMC.txtApellidos.setText(cliente.apellidos);
            vistaClientesMC.txtDni.setText(cliente.dni);
            vistaClientesMC.txtDireccion.setText(cliente.direccion);
            vistaClientesMC.txtTelefono.setText(cliente.telefono);
            vistaClientesMC.txtRuc.setText(cliente.ruc);
        }
        vistaClientesMC.idClientes.setText(String.valueOf(cliente.id));
    }
    
    public void btnGuardarActionPerformed (ActionEvent evt){
        cliente = new Clientes();
        cliente.id = Integer.parseInt(vistaClientesMC.idClientes.getText());
        cliente.nombres = vistaClientesMC.txtNombres.getText();
        cliente.apellidos = vistaClientesMC.txtApellidos.getText();
        cliente.dni = vistaClientesMC.txtDni.getText();
        cliente.direccion = vistaClientesMC.txtDireccion.getText();
        cliente.telefono = vistaClientesMC.txtTelefono.getText();
        cliente.ruc = vistaClientesMC.txtRuc.getText();
        
        // Para detectar si no ha rellenado los datos obligatorios
        if(cliente.nombres.equals("")) {
            JOptionPane.showMessageDialog(null, "Rellene campo obligatorio Nombre","Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Detectar en cual de los 2 estados se cuentra actualmente:
        // -1 : guardar clientes
        // >0 : actualizar cliente, el cual el idcliente es dado por este numero.
        int opcion = Integer.parseInt(vistaClientesMC.idClientes.getText());
        if(opcion > 0) {
            // ACTUALIZAR CLIENTE
            modeloClientes.actualizarCliente(cliente);
            
        }
        else {
            // GENERAR NUEVO CLIENTE
            int result = modeloClientes.crearCliente(cliente);
            if(result == 1) {
                // caso se guardo correctamente en la BD
                JOptionPane.showMessageDialog(null, "Guardado Correctamente");
                vistaClientesMC.dispose();
            }
            else {
                // Caso se halla cometido alguna violacion en los datos guardados en la BD
                JOptionPane.showMessageDialog(null, "Rellene correctamente","Alerta", JOptionPane.ERROR_MESSAGE);
            }
        }
        vistaClientesMC.dispose();
        controladorClientes.limpiaTabla();
        controladorClientes.InicializarClientes();
    }
    public void btnCancelarActionPerformed (ActionEvent evt){
        vistaClientesMC.dispose();
    }
}
