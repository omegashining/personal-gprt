package aserradero.dialogs.about;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JAbout extends JFrame implements ActionListener {
    
    private JButton accept;
    
    public JAbout(JFrame frame){
        super("Acerca de...");
        
        initComponents();
        initGUI();
        addListeners();

        this.setIconImage( ImageUtil.get("images/About.png") );
        this.setSize( 380, 210 );
        this.setResizable( false );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        accept = new JButton( "Aceptar" );
    }

    private void initGUI() {
        Container container = this.getContentPane();
        container.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel[] columns = new JLabel[ 20 ];
        for(int i = 0; i < columns.length; i++) {
            gbc.gridx = i;
            columns[i] = new JLabel( "      " );
            container.add( columns[i], gbc );
        }

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = columns.length;
        container.add( new JLabel("Grupo Productivo Rancho Texas"), gbc );

        gbc.gridy = 2;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 3;
        container.add( new JLabel("DESARROLLADO POR: Gabriel Baltazar Pérez"), gbc );

        gbc.gridy = 4;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 5;
        container.add( new JLabel("Versión 1.0.0"), gbc );

        gbc.gridy = 7;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 8;
        container.add( new JLabel("Copyright © 2010, Cybermagna, Todos los derechos reservados."), gbc );

        gbc.gridy = 9;
        container.add( new JLabel(" "), gbc );
        
        gbc.gridy = 10;
        container.add( accept, gbc );

        gbc.gridy = 11;
        container.add( new JLabel(" "), gbc );
    }

    private void addListeners() {
        accept.addActionListener( this );
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Aceptar")) {
            this.dispose();
        }
    }

}
