package modelo;
import java.sql.*;
import java.util.ArrayList;

public class UsuariosDAO {
    Conexion conexion;
    public UsuariosDAO() {
        conexion = new Conexion();
    }
    
    public Usuarios verificaUsario(String dni,
                                   String password) {
        Usuarios usuario = null;
        Connection accesoDB = conexion.getConexion();
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select * from usuarios where dni=? and password=?" );
            ps.setString(1, dni);
            ps.setString(2, password);
            //ps.setString(3, privilegios);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                usuario = new Usuarios();
                usuario.setDni(rs.getString(2));
                usuario.setPassword(rs.getString(3));
                usuario.setApellidos(rs.getString(4));
                usuario.setNombres(rs.getString(5));
                usuario.setPrivilegios(rs.getString(6));
                return usuario;
            }
        } catch (Exception e) {
            System.out.println("Error conectando base de datos, UsuariosDAO");
        }
        return usuario;
    }
    
    public ArrayList<Usuarios> getUsuarios() {
        Usuarios usuarios = null;
        ArrayList<Usuarios> losUsuariosLista = new ArrayList<Usuarios>();
        Connection accesoDB = conexion.getConexion();
        
        try {
            PreparedStatement ps = accesoDB.prepareStatement("select * from usuarios");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                usuarios = new Usuarios();
                usuarios.setId(rs.getInt(1));
                usuarios.setDni(rs.getString(2));
                usuarios.setPassword(rs.getString(3));
                usuarios.setApellidos(rs.getString(4));
                usuarios.setNombres(rs.getString(5));
                usuarios.setPrivilegios(rs.getString(6));
                losUsuariosLista.add(usuarios);
            }            
        } catch ( Exception e) {
            System.out.println("Error conectando base de datos, getUsuarios()" + e);
        }
        return losUsuariosLista;
    }
    
    public void eliminarUsuario(int idUsuario) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
          PreparedStatement ps = accesoDB.prepareStatement("delete from usuarios where idusuarios=?");
          ps.setInt(1, idUsuario);
          rs = ps.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println("Problema en base de datos, eliminarUsuario() : " + ex);
        }
    }
    
    public int CrearUsuario(Usuarios usuario) {
        Connection accesoDB = conexion.getConexion();
        int rs = 0;
        try {
            PreparedStatement ps = accesoDB.prepareStatement("insert into usuarios (dni, password, apellidos, nombres, privilegios) values (?,?,?,?,?)");
            ps.setInt(1, Integer.parseInt(usuario.dni));
            ps.setString(2, usuario.password);
            ps.setString(3, usuario.apellidos);
            ps.setString(4, usuario.nombres);
            ps.setString(5, usuario.privilegios);
            rs = ps.executeUpdate();
            System.out.println("respuesta: " + rs);
        }
        catch (Exception ex) {
            System.out.println("Problema en base de datos, crearUsuario() : " + ex);
        }
        
        return rs;
    }
    
}
