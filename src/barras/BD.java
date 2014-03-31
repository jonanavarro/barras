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
import javax.swing.table.DefaultTableModel;
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
            //JOptionPane.showMessageDialog(null, "Se hizo la conexion !");
             
             
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
    
    public static boolean existeAlumno(String matricula, String idmateria){
        
        PreparedStatement ps;
            ResultSet rs;
            boolean valor =false;
            
            String consulta;
            
            consulta = "SELECT FK_Matricula FROM grupos WHERE FK_Matricula =? AND FK_IdMateria = ?";
            
        try{    
            ps = con.prepareStatement(consulta);
            ps.setString(1, matricula);
            ps.setString(2, idmateria);
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
    
    public static boolean existeEnGrupo(String matricula, String idmateria, String usuario){
        
        PreparedStatement ps;
        ResultSet rs;
        boolean valor =false;
            
        String consulta;
            
        consulta = "SELECT FK_Matricula FROM grupos WHERE FK_Matricula =? AND FK_IdMateria = ? AND FK_maestro = ? ";
            
        try{    
            ps = con.prepareStatement(consulta);
            ps.setString(1, matricula);
            ps.setString(2, idmateria);
            ps.setString(3, usuario);
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
             
             consulta = "INSERT INTO materias VALUES(?,?,?,?,?,?)";
             ps = con.prepareStatement(consulta);
             ps.setString(1, idmateria);
             ps.setString(2, nombre);
             ps.setString(3, modalidad);
             ps.setString(4, entrada);
             ps.setString(5, tolerancia);
             ps.setString(6, usuario);
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
    public static boolean alumnoRegistrado (String matricula){
        
        PreparedStatement ps;
        ResultSet rs;
        boolean valor =false;
            
        String consulta;
        consulta = "SELECT matricula FROM alumnos WHERE matricula =?";
            
        try{    
            ps = con.prepareStatement(consulta);
            ps.setString(1, matricula);
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
    
    
    public static void insertarAlumno(String matricula, String nombre, String carrera, String idmateria, String maestro){
        PreparedStatement ps;
        String consulta;
        
        if(alumnoRegistrado(matricula)){
            
            consulta = "INSERT INTO grupos VALUES(?,?,?)";
            
            try {
                ps = con.prepareStatement(consulta);
                ps.setString(1, idmateria);
                ps.setString(2, matricula);
                ps.setString(3, maestro);
                ps.executeUpdate();
            
            } catch (SQLException ex) {
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            consulta = "INSERT INTO alumnos VALUES(?,?,?)";
            
            try {
                ps = con.prepareStatement(consulta);
                ps.setString(1, matricula);
                ps.setString(2, nombre);
                ps.setString(3, carrera);
                ps.executeUpdate();
            
            } catch (SQLException ex) {
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            consulta = "INSERT INTO grupos VALUES(?,?,?)";
            try {
                ps = con.prepareStatement(consulta);
                ps.setString(1, idmateria);
                ps.setString(2, matricula);
                ps.setString(3, maestro);
                ps.executeUpdate();
            
            } catch (SQLException ex) {
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static boolean alumnoRegistradoOtrasMaterias(String matricula){
        PreparedStatement ps;
            ResultSet rs;
            boolean valor =false;
            int i=0;
            String consulta;
            consulta = "SELECT COUNT (*) FROM grupos WHERE FK_Matricula =?";
            
        try{    
            ps = con.prepareStatement(consulta);
            ps.setString(1, matricula);
            rs = ps.executeQuery();
            
            if(rs.next()){
                i = (int) rs.getObject(1);
                
                if(i>1){
                    valor = true;    
                }
                else{
                    valor = false;
                }
                
                
            }
            else{
                valor = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
        
    }
    
    public static void eliminarAlumno(String matricula, String idmateria, String maestro){
        PreparedStatement ps;
        String consulta;
        
        if(alumnoRegistradoOtrasMaterias(matricula)){
            
            consulta = "DELETE FROM grupos WHERE FK_IdMateria =? AND FK_Matricula = ? AND FK_maestro = ?";
            
            try {
                ps = con.prepareStatement(consulta);
                ps.setString(1, idmateria);
                ps.setString(2, matricula);
                ps.setString(3, maestro);
                ps.executeUpdate();
            
            } catch (SQLException ex) {
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            consulta = "DELETE FROM listas WHERE FK_IdMateria =? AND FK_Matricula =? AND FK_Maestro =?";
            
            try {
                ps = con.prepareStatement(consulta);
                ps.setString(1, idmateria);
                ps.setString(2, matricula);
                ps.setString(3, maestro);
                ps.executeUpdate();
            
            } catch (SQLException ex) {
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else{
            consulta = "DELETE FROM grupos WHERE FK_IdMateria =? AND FK_Matricula = ? AND FK_maestro=?";
            
            try {
                ps = con.prepareStatement(consulta);
                ps.setString(1, idmateria);
                ps.setString(2, matricula);
                ps.setString(2, maestro);
                ps.executeUpdate();
            
            } catch (SQLException ex) {
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            consulta = "DELETE FROM listas WHERE FK_IdMateria =? AND FK_Matricula = ? AND FK_Maestro=?";
            
            try {
                ps = con.prepareStatement(consulta);
                ps.setString(1, idmateria);
                ps.setString(2, matricula);
                ps.setString(3, maestro);
                ps.executeUpdate();
            
            } catch (SQLException ex) {
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            consulta = "DELETE FROM alumnos WHERE matricula= ?";
            try {
                ps = con.prepareStatement(consulta);
                ps.setString(1,matricula);
                ps.executeUpdate();
            
            } catch (SQLException ex) {
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
    
    
    public static boolean eliminarMateria(String idmateria, String maestro, String horaMateria){
         
         PreparedStatement ps;
         String consulta;
         boolean valor = true;
       
         try {
             
             consulta = "DELETE FROM imparte WHERE FK_IdMateria = ? AND FK_Maestro = ?"; 
             
             ps = con.prepareStatement(consulta);
             ps.setString(1, idmateria);
             ps.setString(2, maestro);
             ps.executeUpdate();
               
         } catch (SQLException ex) {
             Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
             valor = false;
         }
        try {
            consulta = "DELETE FROM materias WHERE IdMateria = ? AND FK_maestro =? AND HoraEntrada = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, idmateria);
            ps.setString(2, maestro);
            ps.setString(3, horaMateria);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            valor = false;
        }
        
        try {
            consulta = "DELETE FROM grupos WHERE FK_IdMateria = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, idmateria);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            valor = false;
        }
         
        return valor;
                 
    }
    
    /*public static DefaultTableModel obtenerLista(String usuario, String idmateria){
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        
        modelo.
        
        return modelo;
    }
    */
    
    public static void insertarLista (String idmateria, String matricula, String periodo, String fecha, String maestro,String hora, String asistencia){
        PreparedStatement ps;
        String consulta;
       
        try {
             
             consulta = "INSERT INTO listas VALUES(?,?,?,?,?,?,?)";
             ps = con.prepareStatement(consulta);
             ps.setString(1, idmateria);
             ps.setString(2, matricula);
             ps.setString(3, periodo);
             ps.setString(4, fecha);
             ps.setString(5, maestro);
             ps.setString(6, hora);
             ps.setString(7, asistencia);
             ps.executeUpdate();
             
         } catch (SQLException ex) {
             Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
    
    public static boolean yaRegistradoLista (String materia,String matricula, String fecha, String maestro){
        PreparedStatement ps;
            ResultSet rs;
            boolean valor =false;
            
            String consulta;
            consulta = "SELECT FK_Matricula FROM listas "
                    + "WHERE FK_IdMateria =? AND FK_Matricula =? AND Fecha = ? AND FK_Maestro=?";
            
        try{    
            ps = con.prepareStatement(consulta);
            ps.setString(1, materia);
            ps.setString(2, matricula);
            ps.setString(3, fecha);
            ps.setString(4, maestro);
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
        consulta = "SELECT imparte.FK_IdMateria"
                + " FROM imparte"
                + " WHERE imparte.FK_maestro = ?";
            
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
    
    public static DefaultComboBoxModel obtenerNombAlumnos(String usuario, String idmateria){
        
        PreparedStatement ps;
        ResultSet rs = null;
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        String consulta;
        
        consulta = "SELECT alumnos.nombre"
                + " FROM alumnos INNER JOIN grupos ON alumnos.matricula = grupos.FK_Matricula"
                + " INNER JOIN imparte ON grupos.FK_IdMateria = imparte.FK_IdMateria"
                + " WHERE imparte.FK_maestro = ? AND imparte.FK_IdMateria = ? AND grupos.FK_maestro = ?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario );
            ps.setString(2, idmateria );
            ps.setString(3, usuario );
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
    
    public static String obtenerNombAlumno(String matricula){
        
        PreparedStatement ps;
        ResultSet rs = null;
        String i ="";
        
        String consulta;
        
        consulta = "SELECT nombre"
                + " FROM alumnos WHERE matricula = ?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, matricula);
            rs = ps.executeQuery();
            
            
            if(rs.next()){
                i = rs.getString(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i;
    }
    
    public static String obtenerHoraMateria(String usuario, String materia){
        
        PreparedStatement ps;
        ResultSet rs = null;
        String i ="";
        
        String consulta;
        
        consulta = "SELECT HoraEntrada"
                + " FROM materias " 
                + " WHERE FK_Maestro = ? AND IdMateria = ?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario);
            ps.setString(2, materia);
            rs = ps.executeQuery();
            
            
            if(rs.next()){
                i = rs.getString(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i;
    }
    
    public static String obtenerToleranciaMateria(String usuario, String materia){
        
        PreparedStatement ps;
        ResultSet rs = null;
        String i ="";
        
        String consulta;
        
        consulta = "SELECT tolerancia"
                + " FROM materias " 
                + " WHERE FK_Maestro = ? AND IdMateria = ?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario);
            ps.setString(2, materia);
            rs = ps.executeQuery();
            
            
            if(rs.next()){
                i = rs.getString(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i;
    }
    
    
    
    public static String obtenerMatriculaAlumno(String nombre){
        
        PreparedStatement ps;
        ResultSet rs = null;
        String mat="";
        
        String consulta;
        
        consulta = "SELECT matricula FROM alumnos WHERE nombre=?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            
            
            if(rs.next()){
                mat = rs.getString(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mat;
    }
    
    public static String obtenerCarreraAlumno(String nombre){
        
        PreparedStatement ps;
        ResultSet rs = null;
        String mat="";
        
        String consulta;
        
        consulta = "SELECT FK_IdCarrera FROM alumnos WHERE nombre=?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            
            
            if(rs.next()){
                mat = rs.getString(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mat;
    }
    
    public static DefaultTableModel obtenerLista(String usuario, String materia){
        
        PreparedStatement ps;
        ResultSet rs = null;
        DefaultTableModel modelo = new DefaultTableModel();
        
        String consulta;
        consulta = "SELECT  alumnos.nombre, listas.Fecha, listas.Hora, listas.Asistencia"
                + " FROM alumnos INNER JOIN listas ON alumnos.matricula = listas.FK_Matricula"
                
                + " WHERE listas.FK_Maestro = ? AND listas.FK_IdMateria = ?";
            
        try{    
            
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario );
            ps.setString(2, materia );
            
            rs = ps.executeQuery();
         
            modelo.addColumn("Nombre");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora");
            modelo.addColumn("Asistencia");
            
            String []datos = new String [4];
            
            while(rs.next()){
                
                datos[0] = (String) rs.getObject(1);
                datos[1] = (String) rs.getObject(2);
                datos[2] = (String) rs.getObject(3);
                datos[3] = (String) rs.getObject(4);
         
                modelo.addRow(datos);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return modelo;
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
