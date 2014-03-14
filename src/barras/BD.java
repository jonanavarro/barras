/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package barras;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Jonathan
 */
public class BD {
    public static Connection con;
    
    public static void conexion(){
        
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:sca.db");
            JOptionPane.showMessageDialog(null, "Se hizo la conexion !");
             
             
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static boolean esValido(String usuario, String pass){
        
        PreparedStatement ps;
        ResultSet rs;
        String consulta;
        boolean valido = false;
        
        consulta = "SELECT maestro FROM usuarios WHERE maestro =? AND"
                + " password =?";
        
        try {
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario);
            ps.setString(2, pass);
            rs = ps.executeQuery();    
            
            if(rs.next()){
                valido = true;
            }
            else{
                valido = false;
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valido;
        
    }
    
    public static void insertarUsuario(String usuario, String pass){
        try {
            PreparedStatement ps;
            String consulta;
            
            consulta = "INSERT INTO usuarios VALUES(?,?)";
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario);
            ps.setString(2, pass);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public static boolean existeUsuario(String usuario){
        
            PreparedStatement ps;
            ResultSet rs;
            boolean valor =false;
            
            String consulta;
            consulta = "SELECT maestro FROM usuarios WHERE maestro =?";
            
        try{    
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            
            if(rs.next()){
                valor = true;
            }
            else{
                valor = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
    }
    
}
