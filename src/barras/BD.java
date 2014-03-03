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
    
    static Connection con;
    
    public void conexion(){
        
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
    
    public static void insertar(int num ,int tiposerv, int tiemposerv, int tllegada){
        try {
            
            PreparedStatement ps= con.prepareStatement("INSERT INTO Clientes(nocliente,"
                    + "                                                      tiposervicio,"
                    + "                                                      tiemposervicio,"
                    + "                                                      horallegada)"
                    + "                                 VALUES (?,?,?,?)");
            
            ps.setInt(1, num);
            ps.setInt(2, tiposerv);
            ps.setInt(3, tiemposerv);
            ps.setInt(4, tllegada);
            
            ps.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public static void tiempoSalida(int h, int n){
        
        try {
            
            PreparedStatement ps= con.prepareStatement("UPDATE Clientes"
                    + "                                 SET horasalida =?"
                    + "                                 WHERE nocliente=?");
            
            ps.setInt(1, h);
            ps.setInt(2, n);
            
            ps.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void llegadaCaja(int n, int c){
        
        try{
        
        PreparedStatement ps= con.prepareStatement("UPDATE Clientes"
                + "                             SET llegadaCaja = ?"
                + "                             WHERE nocliente=?");
        
        ps.setInt(1, c);
        ps.setInt(2, n);
            
        ps.execute();
        }
        
        catch(SQLException ex){
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void atencionCaja(int n, int a){
        
        try{
        
        PreparedStatement ps= con.prepareStatement("UPDATE Clientes"
                + "                             SET atencionCaja = ?"
                + "                             WHERE nocliente=?");
        
        ps.setInt(1, a);
        ps.setInt(2, n);
            
        ps.execute();
        }
        
        catch(SQLException ex){
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void tiempoCaja(int n, int t){
        
        try{
        
        PreparedStatement ps= con.prepareStatement("UPDATE Clientes"
                + "                             SET tiempoCaja = ?"
                + "                             WHERE nocliente=?");
        
        ps.setInt(1, t);
        ps.setInt(2, n);
            
        ps.execute();
        }
        
        catch(SQLException ex){
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   public static void salidaCaja(int n, int s){
        
        try{
        
        PreparedStatement ps= con.prepareStatement("UPDATE Clientes"
                + "                             SET salidaCaja = ?"
                + "                             WHERE nocliente=?");
        
        ps.setInt(1, s);
        ps.setInt(2, n);
            
        ps.execute();
        }
        
        catch(SQLException ex){
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void tiempoAtencion(int h, int n){
        
        try {
            
            PreparedStatement ps= con.prepareStatement("UPDATE Clientes"
                    + "                                 SET atencion =?"
                    + "                                 WHERE nocliente=?");
            
            ps.setInt(1, h);
            ps.setInt(2, n );
            
            ps.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void borrarTodo(){
        
        try {
            
            PreparedStatement ps= con.prepareStatement("DELETE FROM CLIENTES");
            
            ps.execute();
            con.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static ResultSet obtenerRegistros(){
        
        ResultSet rs=null;
        try {
            
            PreparedStatement ps= con.prepareStatement("SELECT * FROM CLIENTES Order by nocliente ASC");
            rs=ps.executeQuery();
            
            return rs;
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
        
    }
    
}
