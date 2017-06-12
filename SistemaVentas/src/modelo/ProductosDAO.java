
package modelo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductosDAO {
    Conexion conexion;
    
    public ProductosDAO() {
        conexion = new Conexion();
    }
    
    public ArrayList<Productos> getProductos() {
       Productos productos;
       ArrayList<Productos> listaProductos = new ArrayList<Productos>();
       Connection accesoDB = conexion.getConexion();
       try {
           PreparedStatement ps = accesoDB.prepareStatement("select * from productos order by idproductos desc");
           ResultSet rs = ps.executeQuery();
           
           while(rs.next()) {
               productos = new Productos();
               productos.setId(rs.getInt(1));
               productos.setNombre(rs.getString(2));
               productos.setMarca(rs.getString(3));
               productos.setCategoria(rs.getString(4));
               productos.setCosto(rs.getString(5));
               productos.setPrecio1_10(rs.getString(6));
               productos.setPrecio10_20(rs.getString(7));
               productos.setPrecio20_(rs.getString(8));
               productos.setCantidad(rs.getString(9));
               productos.setInputImagen(rs.getBinaryStream(10));
               listaProductos.add(productos);
           }
       }
       catch(Exception e) {
           System.out.println("Error conectando base de datos, getProductos" + e);
       }
       return listaProductos;
    }
    
    public Productos getProducto(int idProducto) {
       Productos producto = null;
       Connection accesoDB = conexion.getConexion();
       try {
           PreparedStatement ps = accesoDB.prepareStatement("select * from productos where idproductos=?");
           ps.setInt(1, idProducto);
           ResultSet rs = ps.executeQuery();
           
           while(rs.next()) {
               producto = new Productos();
               producto.setId(rs.getInt(1));
               producto.setNombre(rs.getString(2));
               producto.setMarca(rs.getString(3));
               producto.setCategoria(rs.getString(4));
               producto.setCosto(rs.getString(5));
               producto.setPrecio1_10(rs.getString(6));
               producto.setPrecio10_20(rs.getString(7));
               producto.setPrecio20_(rs.getString(8));
               producto.setCantidad(rs.getString(9));
               producto.setInputImagen(rs.getBinaryStream(10));
           }
       }
       catch(Exception e) {
           System.out.println("Error conectando base de datos, getProducto()" + e);
       }
       return producto;
    }
    
    public void eliminarProductos(int idProducto) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
          System.out.println("Eliminando idproducto: " + idProducto);
          PreparedStatement ps = accesoDB.prepareStatement("delete from productos where idproductos=?");
          ps.setInt(1, idProducto);
          rs = ps.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println("Problema en base de datos, Eliminar Productos : " + ex);
        }
    }
    
    public int crearProducto(Productos producto) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
            PreparedStatement ps = accesoDB.prepareStatement("insert into productos (nombre, marca, categoria, costo, precio1_10, precio10_20, precio20_,cantidad,imagen) values (?,?,?,?,?,?,?,?,?)");
            ps.setString(1, producto.nombre);
            ps.setString(2, producto.marca);
            ps.setString(3, producto.categoria);
            ps.setString(4, producto.costo);
            ps.setString(5, producto.precio1_10);
            ps.setString(6, producto.precio10_20);
            ps.setString(7, producto.precio20_);
            ps.setString(8, producto.cantidad);
            //ps.setBytes(9, producto.imagen);
            try {
                //ps.setBinaryStream (9, producto.imagen, (int) producto.fileImagen.length() );
                //ps.setBinaryStream(9, producto.imagen, producto.imagen.available());
                ps.setBinaryStream(9, producto.inputImagen, producto.inputImagen.available());
                System.out.println("tamaño maximo: "+ producto.inputImagen.available());
            }
            catch (Exception ex) {
                ps.setBinaryStream (9, null);
            }
            rs = ps.executeUpdate();
            System.out.println("respuesta: " + rs);
        }
        catch (Exception ex) {
            System.out.println("Problema en base de datos, Crear Producto : " + ex);
        }
        
        return rs;
    }
    
    public void actualizarProducto(Productos producto) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
            PreparedStatement ps = accesoDB.prepareStatement("update productos set nombre=?,marca=?,categoria=?,costo=?,precio1_10=?,precio10_20=?,precio20_=?,cantidad=?,imagen=? where idproductos=?");
            ps.setString(1, producto.nombre);
            ps.setString(2, producto.marca);
            ps.setString(3, producto.categoria);
            ps.setString(4, producto.costo);
            ps.setString(5, producto.precio1_10);
            ps.setString(6, producto.precio10_20);
            ps.setString(7, producto.precio20_);
            ps.setString(8, producto.cantidad);
            try{
                //ps.setBinaryStream (9, producto.imagen, (int) producto.fileImagen.length() );
                ps.setBinaryStream(9, producto.inputImagen, producto.inputImagen.available());
            }
            catch(Exception ex) {
                ps.setBinaryStream (9, null);
                System.out.println("TODO: Al actualizar tambien tengo que tener la imagen");
            }
            ps.setInt(10, producto.id);
            rs = ps.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println("Problema en base de datos, Actualizar producto : " + ex);
        }
    }
    
    public void actualizarCantidadProducto(int idProducto, String cantidad) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
            PreparedStatement ps = accesoDB.prepareStatement("update productos set cantidad=? where idproductos=?");
            ps.setString(1, cantidad);
            ps.setInt(2, idProducto);
            rs = ps.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println("Problema en base de datos, actualizarCantidadProducto() : " + ex);
        }
    }
    
    public InputStream getImagenProducto(int idProducto) {
        InputStream imagen = null;
        Connection accesoDB = conexion.getConexion();
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select imagen from productos where idproductos=?");
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                imagen = rs.getBinaryStream(1);
            }
        }
        catch(Exception e) {
            System.out.println("Error conectando base de datos, getImagenProducto()" + e);
        }
        return imagen;
    }
    
}
