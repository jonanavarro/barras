package barras;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

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
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
// If Nimbus is not available, you can set the GUI to another look and feel.
        }
        
        BD.conexion();
        
        principal p =new principal();
        p.setLocationRelativeTo(null);
        p.setVisible(true);
        
    }
}
