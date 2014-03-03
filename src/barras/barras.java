package barras;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Regina
 */
public class barras {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(barras.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Conexion con = new Conexion();
        //con.establecerConexion();
        BD base = new BD();
        base.conexion();
        
        new principal().setVisible(true);
        
    }
}
