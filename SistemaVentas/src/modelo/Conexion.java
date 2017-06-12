package modelo;
import java.sql.*;

public class Conexion {
    
    public Conexion() {
    }
    
    public Connection getConexion() {
        Connection con = null;
        try {
            // Carga/Registra el driver JDBC encargado de la comunicacion
            // entre Java con Mysql
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Obtener la conexion
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas_isaac","root","1234");
        }
        catch(SQLException ex) {
            System.out.println("Error conectando base de datos");
        } catch(Exception e) {
            System.out.println("Error Exception");
        }
        return con;
    }
}
