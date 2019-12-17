package aserradero;

import aserradero.database.Conexion;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mx.gob.org.ipn.cic.du.dialogs.JLoading;

/**
 *
 * @author Gabriel
 */
public class Main {

    public static void main(String[] args) {
        Conexion conexion = new Conexion();

        if(conexion.connect() != null) {
            conexion.close();
            
            JLoading loading = new JLoading("images/gprt.jpg");
            JFrame.setDefaultLookAndFeelDecorated(true);
            Application application = new Application();

            Progress progress = new Progress(loading, application);

            Thread thread = new Thread(progress);
            thread.start();

            /*JLoading loading = new JLoading();
            loading.iterate();

            JFrame.setDefaultLookAndFeelDecorated( true );
            SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                    Skin skin = new Skin();
                    try {
                        if(skin.getSkinName().equals(""))
                            UIManager.setLookAndFeel( "org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel" );
                        else
                            SubstanceLookAndFeel.setSkin( skin.getSkinName() );
                    } catch (Exception ex) {
                        System.out.println( "Error al cargar el Skin." );
                    }
                    new Application( skin.getSkinName() );
                }
            });*/
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog( null, "No se puede abrir otra instancia de Programa" );
        }
    }

}
