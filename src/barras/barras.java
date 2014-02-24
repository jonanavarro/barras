package barras;

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
        Conexion con = new Conexion();
        con.establecerConexion();
        new principal().setVisible(true);
        
    }
}
