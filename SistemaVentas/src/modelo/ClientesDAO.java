package modelo;

import java.sql.*;
import java.util.ArrayList;

public class ClientesDAO {
    Conexion conexion;
    
    public ClientesDAO() {
        conexion = new Conexion();
    }
    
    public ArrayList<Clientes> getClientes() {
        Clientes clientes = null;
        ArrayList<Clientes> losClientesLista = new ArrayList<Clientes>();
        Connection accesoDB = conexion.getConexion();
        
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select * from clientes where idclientes!=9 order by idclientes desc");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                clientes = new Clientes();
                clientes.setId(rs.getInt(1));
                clientes.setNombres(rs.getString(2));
                clientes.setApellidos(rs.getString(3));
                clientes.setDni(rs.getString(4));
                clientes.setDireccion(rs.getString(5));
                clientes.setTelefono(rs.getString(6));
                clientes.setRuc(rs.getString(7));
                losClientesLista.add(clientes);
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, ClientesDAO" + e);
        }
        return losClientesLista;
    }
    
    public int crearCliente(Clientes cliente) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
            PreparedStatement ps = accesoDB.prepareStatement("insert into clientes (nombres, apellidos, dni, direccion, telefono, ruc) values (?,?,?,?,?,?)");
            ps.setString(1, cliente.nombres);
            ps.setString(2, cliente.apellidos);
            ps.setString(3, cliente.dni);
            ps.setString(4, cliente.direccion);
            ps.setString(5, cliente.telefono);
            ps.setString(6, cliente.ruc);
            rs = ps.executeUpdate();
            System.out.println("respuesta: " + rs);
        }
        catch (Exception ex) {
            System.out.println("Problema en base de datos, Crear Cliente : " + ex);
        }
        
        return rs;
    }
    
    public void actualizarCliente(Clientes cliente) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
            PreparedStatement ps = accesoDB.prepareStatement("update clientes set nombres=?,apellidos=?,dni=?,direccion=?,telefono=?,ruc=? where idclientes=?");
            ps.setString(1, cliente.nombres);
            ps.setString(2, cliente.apellidos);
            ps.setString(3, cliente.dni);
            ps.setString(4, cliente.direccion);
            ps.setString(5, cliente.telefono);
            ps.setString(6, cliente.ruc);
            ps.setInt(7, cliente.id);
            rs = ps.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println("Problema en base de datos, Actualizar Cliente : " + ex);
        }
    }
    
    public void eliminarClientes(int idCliente) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
          PreparedStatement ps = accesoDB.prepareStatement("delete from clientes where idclientes=?");
          ps.setInt(1, idCliente);
          rs = ps.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println("Problema en base de datos, Eliminar Cliente : " + ex);
        }
    }
    
    public Clientes getCliente(int idCliente) {
        Clientes cliente = new Clientes();
        Connection accesoDB = conexion.getConexion();
        
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select * from clientes where idclientes=?");
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                cliente.setId(rs.getInt(1));
                cliente.setNombres(rs.getString(2));
                cliente.setApellidos(rs.getString(3));
                cliente.setDni(rs.getString(4));
                cliente.setDireccion(rs.getString(5));
                cliente.setTelefono(rs.getString(6));
                cliente.setRuc(rs.getString(7));
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, getCliente" + e);
        }
        return cliente;
    }
    
}
