package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class VentasDAO {
    
    Conexion conexion;
    
    public VentasDAO() {
        
        conexion = new Conexion(); 
        
    }
    public ArrayList<Ventas> getVentas() {
        Ventas ventas = null;
        ArrayList<Ventas> lasVentasLista = new ArrayList<Ventas>();
        Connection accesoDB = conexion.getConexion();
        
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select * from ventas order by idventas desc");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                ventas = new Ventas();
                ventas.setIdVenta(rs.getInt(1));
                ventas.setIdCliente(rs.getInt(2));
                ventas.setNombre(rs.getString(3));
                ventas.setFecha(rs.getString(4));
                ventas.setHora(rs.getString(5));
                ventas.setSubtotal(rs.getString(6));
                ventas.setIgv(rs.getString(7));
                ventas.setMonto(rs.getString(8));
                ventas.setGananciaTotal(rs.getString(9));
                lasVentasLista.add(ventas);
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, VentasDAO" + e);
        }
        return lasVentasLista;
    }
    
    public Ventas getVenta(int idVenta) {
        Ventas venta = null;
        Connection accesoDB = conexion.getConexion();
        
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select * from ventas where idventas=?");
            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                venta = new Ventas();
                venta.setIdVenta(rs.getInt(1));
                venta.setIdCliente(rs.getInt(2));
                venta.setNombre(rs.getString(3));
                venta.setFecha(rs.getString(4));
                venta.setHora(rs.getString(5));
                venta.setSubtotal(rs.getString(6));
                venta.setIgv(rs.getString(7));
                venta.setMonto(rs.getString(8));
                venta.setGananciaTotal(rs.getString(9));
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, VentasDAO, getVenta()" + e);
        }
        
        return venta;
    }
    
    public ArrayList<Ventas> getVentasProducto(int idVenta) {
        Ventas ventas = null;
        ArrayList<Ventas> lasVentasLista = new ArrayList<Ventas>();
        Connection accesoDB = conexion.getConexion();
        
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select * from ventaproducto where idventa=?");
            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                ventas = new Ventas();
                ventas.setIdVenta(rs.getInt(2));
                ventas.setIdProducto(rs.getInt(3));
                ventas.setNombre(rs.getString(4));
                ventas.setCantidadProducto(rs.getString(5));
                ventas.setTipoPrecioProducto(rs.getString(6));
                ventas.setPrecioProducto(rs.getString(7));
                ventas.setGananciaProducto(rs.getString(8));
                lasVentasLista.add(ventas);
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, VentasDAO, getVentasProducto()" + e);
        }
        return lasVentasLista;
    }
    
    
    public void eliminarVenta(int idVenta) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
          PreparedStatement ps = accesoDB.prepareStatement("delete from ventas where idventas=?");
          ps.setInt(1, idVenta);
          rs = ps.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println("Problema en base de datos, Eliminar Venta : " + ex);
        }
        
        try {
          PreparedStatement ps = accesoDB.prepareStatement("delete from ventaproducto where idventa=?");
          ps.setInt(1, idVenta);
          rs = ps.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println("Problema en base de datos, Eliminar VentaProducto : " + ex);
        }
    }
    //
    public int crearVenta(Ventas venta) {
        Connection accesoDB = conexion.getConexion();
        int idVentaGenerado = 0;
        int rs = 0;
        try {
            PreparedStatement ps = accesoDB.prepareStatement("insert into ventas (idcliente, nombre, fecha, hora, subtotal, igv, monto,gananciatotal) values (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, venta.idCliente);
            ps.setString(2, venta.nombre);
            ps.setString(3, venta.fecha);
            ps.setString(4, venta.hora);
            ps.setString(5, venta.subtotal);
            ps.setString(6, venta.igv);
            ps.setString(7, venta.monto);
            ps.setString(8, venta.gananciaTotal);

            rs = ps.executeUpdate();
            System.out.println("respuesta: " + rs);
            
            ResultSet rss = ps.getGeneratedKeys();
            if (rss.next()){
                idVentaGenerado = rss.getInt(1);
            }
        }
        catch (Exception ex) {
            System.out.println("Problema en base de datos, crearVenta() : " + ex);
        }
        
        return idVentaGenerado;
    }
    
    public int crearVentaProducto(Ventas venta) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
            PreparedStatement ps = accesoDB.prepareStatement("insert into ventaproducto (idventa, idproducto, nombre, cantidad, tipoprecio, precio, ganancia) values (?,?,?,?,?,?,?)");
            ps.setInt(1, venta.idVenta);
            ps.setInt(2, venta.idProducto);
            ps.setString(3, venta.nombre);
            ps.setString(4, venta.cantidadProducto);
            ps.setString(5, venta.tipoPrecioProducto);
            ps.setString(6, venta.precioProducto);
            ps.setString(7, venta.gananciaProducto);

            rs = ps.executeUpdate();
            System.out.println("respuesta: " + rs);
        }
        catch (Exception ex) {
            System.out.println("Problema en base de datos, crearVentaProducto() : " + ex);
        }
        
        return rs;
    }
    
    public ArrayList<Integer> getIdVentas(String stringFecha) {
        ArrayList<Integer> idVentasLista = new ArrayList<Integer>();
        Connection accesoDB = conexion.getConexion();
        
        try {
            String query = "select idventas from ventas where fecha like '%" + stringFecha + "%'";
            PreparedStatement ps = accesoDB.prepareStatement(query);
            //ps.setString(1, stringFecha);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                idVentasLista.add(rs.getInt(1));
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, getIdVentas()" + e);
        }
        return idVentasLista;
    }
    
    public ArrayList<Integer> getIdVentasCliente(int idCliente) {
        ArrayList<Integer> idVentasLista = new ArrayList<Integer>();
        Connection accesoDB = conexion.getConexion();
        
        try {
            String query = "select idventas from ventas where idcliente=?";
            PreparedStatement ps = accesoDB.prepareStatement(query);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                idVentasLista.add(rs.getInt(1));
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, getIdVentas()" + e);
        }
        return idVentasLista;
    }
    
    public ArrayList<Ventas> getIdCantidadProductos(int idVentas) {
        ArrayList<Ventas> idVentasLista = new ArrayList<Ventas>();
        Connection accesoDB = conexion.getConexion();
        Ventas ventas = null;
        
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select idproducto,cantidad from ventaproducto where idventa=?");
            ps.setInt(1, idVentas);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                //idVentasLista.add(rs.getInt(1));
                ventas = new Ventas();
                ventas.setIdProducto(rs.getInt(1));
                ventas.setCantidadProducto(rs.getString(2));
                idVentasLista.add(ventas);
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, getIdCantidadProductos()" + e);
        }
        return idVentasLista;
    }
    
    public String getNameProducto(int idProducto) {
        String nameProducto = "";
        Connection accesoDB = conexion.getConexion();
       
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select nombre from productos where idproductos=?");
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                nameProducto = rs.getString(1);
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, getNameProducto()" + e);
        }
        return nameProducto;
    }
    
    public ArrayList<Ventas> getCantPrecioProducto(int tipoPrecio, int minIdVenta, int maxIdVenta) {
        Ventas ventas = null;
        ArrayList<Ventas> lasVentasLista = new ArrayList<Ventas>();
        Connection accesoDB = conexion.getConexion();
        
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select cantidad,precio from ventaproducto where tipoprecio=? and idventa>=? and idventa<=?");
            ps.setString(1, String.valueOf(tipoPrecio));
            ps.setInt(2, minIdVenta);
            ps.setInt(3, maxIdVenta);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                ventas = new Ventas();
                ventas.setCantidadProducto(rs.getString(1));
                ventas.setPrecioProducto(rs.getString(2));
                lasVentasLista.add(ventas);
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, VentasDAO, getCantPrecioProducto()" + e);
        }
        return lasVentasLista;
    }
    
    public int getIdCliente(int idVentas) {
        int idCliente = 0;
        Connection accesoDB = conexion.getConexion();
         
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select idcliente from ventas where idventas=?");
            ps.setInt(1, idVentas);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                idCliente = rs.getInt(1);
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, getIdCliente()" + e);
        }
        return idCliente;
    }
    
}
