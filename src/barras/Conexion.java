/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*MODIFICACION CHIDA 123*/
package barras;
import java.sql.*;
 
public class Conexion {
private Connection conexion;
static String bd="sca";
static String user="root";
static String password="";
static String server="jdbc:mysql://localhost/"+bd;
 
/** Creates a new instance of BasedeDatos */
public Conexion() {

}
 
public void establecerConexion(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
        conexion = DriverManager.getConnection(server,user,password);
        System.out.println("Conexion realizada amigos !");
    }
    catch(Exception e){
    //System.out.println("Imposible realizar conexion con la BD");
    e.printStackTrace();
    }
}
 
public Connection getConexion(){
    return conexion;
}
 
public void cerrar(ResultSet rs){
    if(rs !=null){
        try{
            rs.close();
        }
        catch(Exception e){
            System.out.print("No es posible cerrar la Conexion");
        }
    }
}
 
public void cerrar(java.sql.Statement stmt){
    if(stmt !=null){
        try{
            stmt.close();
        }
        catch(Exception e){}
    }
}

 
public void destruir(){

   
if(conexion !=null){
 
try{
conexion.close();
}
catch(Exception e){}
}
}
}
