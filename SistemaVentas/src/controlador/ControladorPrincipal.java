package controlador;

import modelo.UsuariosDAO;
import vista.JFPrincipal;
import java.awt.event.*;
import modelo.ClientesDAO;
import modelo.ProductosDAO;
import modelo.Usuarios;
import modelo.VentasDAO;
import vista.JFClientes;
import vista.JFConfiguracion;
import vista.JFEstadisticas;
import vista.JFInventario;
import vista.JFProductos;
import vista.JFVentas;

public class ControladorPrincipal{
    UsuariosDAO modeloPrincipal = new UsuariosDAO();
    JFPrincipal vistaPrincipal = new JFPrincipal();
    Usuarios usuario = new Usuarios();
    String dni, password;
    
    public ControladorPrincipal(JFPrincipal vistaPrincipal, 
                                UsuariosDAO modeloPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.modeloPrincipal = modeloPrincipal;
        this.vistaPrincipal.btnClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        this.vistaPrincipal.btnProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        this.vistaPrincipal.btnVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        this.vistaPrincipal.btnInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });
        this.vistaPrincipal.btnEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnEstadisticasActionPerformed(evt);
            }
        });
        this.vistaPrincipal.btnConfiguracion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnConfiguracionActionPerformed(evt);
            }
        });
    }
    
    public void InicializarPrincipal(String dni, String password) {
        this.dni = dni;
        this.password = password;
    }
    
    public void btnClientesActionPerformed(ActionEvent evt) {
        JFClientes vistaClientes = new JFClientes();
        ClientesDAO modeloClientes = new ClientesDAO();
        ControladorClientes clientes = new ControladorClientes(vistaClientes, modeloClientes);
        vistaClientes.setVisible(true);
        vistaClientes.setLocationRelativeTo(null);
        clientes.InicializarClientes();
    }   
    
    public void btnProductosActionPerformed(ActionEvent evt) {
        JFProductos vistaProductos = new JFProductos();
        ProductosDAO modeloProductos = new ProductosDAO();
        ControladorProductos productos = new ControladorProductos(vistaProductos, modeloProductos);
        vistaProductos.setVisible(true);
        vistaProductos.setLocationRelativeTo(null);
        productos.InicializarProductos();
    }
    
    public void btnVentasActionPerformed(ActionEvent evt) {
        JFVentas vistaVentas = new JFVentas();
        VentasDAO modeloVentas= new VentasDAO();
        ControladorVentas ventas = new ControladorVentas(vistaVentas, modeloVentas);
        vistaVentas.setVisible(true);
        vistaVentas.setLocationRelativeTo(null);
        ventas.InicializarVentas();
    }
    
    public void btnInventarioActionPerformed(ActionEvent evt) {
        JFInventario vistaInventario = new JFInventario();
        ProductosDAO modeloProductos= new ProductosDAO();
        ControladorInventario inventario = new ControladorInventario(vistaInventario, modeloProductos);
        vistaInventario.setVisible(true);
        vistaInventario.setLocationRelativeTo(null);
        inventario.InicializarInventario();
    }
    
    public void btnEstadisticasActionPerformed(ActionEvent evt) {
        JFEstadisticas vistaEstadisticas = new JFEstadisticas();
        ControladorEstadisticas estadistica = new ControladorEstadisticas(vistaEstadisticas);
        vistaEstadisticas.setVisible(true);
        vistaEstadisticas.setLocationRelativeTo(null);
    }
    
    public void btnConfiguracionActionPerformed(ActionEvent evt) {
        JFConfiguracion vistaConfiguracion = new JFConfiguracion();
        UsuariosDAO modeloUsuarios = new UsuariosDAO();
        ControladorConfiguracion configuracion = new ControladorConfiguracion(vistaConfiguracion, modeloUsuarios);
        vistaConfiguracion.setVisible(true);
        vistaConfiguracion.setLocationRelativeTo(null);
        configuracion.InicializarConfiguracion();
    }
    
}
