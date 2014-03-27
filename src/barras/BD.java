/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package barras;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
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
    
    public static void insertarMateria(String idmateria, String nombre, String modalidad,
                                       String entrada, String tolerancia, String usuario){
         
         PreparedStatement ps;
         String consulta;
       
         try {
             
             consulta = "INSERT INTO materias VALUES(?,?,?,?,?)";
             ps = con.prepareStatement(consulta);
             ps.setString(1, idmateria);
             ps.setString(2, nombre);
             ps.setString(3, modalidad);
             ps.setString(4, entrada);
             ps.setString(5, tolerancia);
             ps.executeUpdate();
             ps.close();
             
             consulta = "INSERT INTO imparte VALUES(?,?)";
             ps = con.prepareStatement(consulta);
             ps.setString(1, usuario);
             ps.setString(2, idmateria);
             ps.executeUpdate();
             
         } catch (SQLException ex) {
             Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public static boolean eliminarMateria(String idmateria, String maestro){
         
         PreparedStatement ps;
         String consulta;
         boolean valor = true;
       
         try {
             
             consulta = "DELETE FROM imparte WHERE FK_IdMateria = ? AND FK_Maestro = ?"; 
             
             ps = con.prepareStatement(consulta);
             ps.setString(1, idmateria);
             ps.setString(2, maestro);
             ps.executeUpdate();
             
             consulta = "DELETE FROM materias WHERE IdMateria = ?";
             
             
         } catch (SQLException ex) {
             Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
             valor = false;
         }
        try {
            consulta = "DELETE FROM materias WHERE IdMateria = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, idmateria);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            valor = false;
        }
         
        return valor;
                 
    }
    
    public static boolean materiaDuplicada(String idMateria, String maestro){
        PreparedStatement ps;
        ResultSet rs = null;
        boolean valor =false;
            
        String consulta;
        consulta = "SELECT * FROM imparte WHERE FK_maestro =? AND FK_IdMateria =?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, maestro );
            ps.setString(2, idMateria);
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
    
    public static DefaultComboBoxModel obtenerMaterias(String usuario){
        
        PreparedStatement ps;
        ResultSet rs = null;
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        
        String consulta;
        consulta = "SELECT materias.IdMateria"
                + " FROM materias INNER JOIN imparte ON materias.IdMateria = imparte.FK_idMateria"
                + " INNER JOIN usuarios ON usuarios.maestro = imparte.FK_Maestro"
                + " WHERE usuarios.maestro = ?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario );
            rs = ps.executeQuery();
            
            
            
            while(rs.next()){
                
                modelo.addElement(rs.getObject(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return modelo;
    }
    
    public static String obtenerNombMateria(String idmat){
        
        PreparedStatement ps;
        ResultSet rs = null;
        String i ="";
        
        String consulta;
        
        consulta = "SELECT nombMateria"
                + " FROM materias WHERE IdMateria = ?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, idmat);
            rs = ps.executeQuery();
            
            
            if(rs.next()){
                i = rs.getString(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i;
    }
    
    public static String [] obtenerIdMateria(String usuario){
        
        PreparedStatement ps;
        ResultSet rs = null;
        int longitud = 0;
        String [] modelo = null ;
        
        String consulta;
        
        consulta = "SELECT COUNT(*)"
                + " FROM materias INNER JOIN imparte ON materias.IdMateria = imparte.FK_idMateria"
                + " INNER JOIN usuarios ON usuarios.maestro = imparte.FK_Maestro"
                + " WHERE usuarios.maestro = ?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario );
            rs = ps.executeQuery();
            
            
            
            while(rs.next()){
                
                longitud = (rs.getInt(1));
            }
            
            if (longitud == 0){
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        consulta = "SELECT materias.IdMateria"
                + " FROM materias INNER JOIN imparte ON materias.IdMateria = imparte.FK_idMateria"
                + " INNER JOIN usuarios ON usuarios.maestro = imparte.FK_Maestro"
                + " WHERE usuarios.maestro = ?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario );
            rs = ps.executeQuery();
            
            
            int i = 0;
            
            modelo = new String [longitud];
            
            while(i < longitud){
                
                modelo[i]=(String) rs.getObject(1);
                i++;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return modelo;
    }
    
    
    
    
}
