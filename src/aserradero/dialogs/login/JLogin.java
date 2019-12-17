package aserradero.dialogs.login;

import aserradero.Application;
import aserradero.entities.Cuenta;
import aserradero.database.Consulta;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import mx.gob.org.ipn.cic.du.panels.JImagePanel;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JLogin extends JDialog implements ActionListener {

    private Application application;
    private JImagePanel imagePanel;
    private JTextField username;
    private JPasswordField password;
    private JButton accept;
    private JButton cancel;

    public JLogin(Application frame) {
        super(frame, "Iniciar Sesión", true);

        this.application = frame;

        initComponents();
        initGUI();
        addListeners();

        this.add( imagePanel );
        this.setSize( 400, 250 );
        this.setResizable( false );
        this.setLocationRelativeTo( frame );
        this.setUndecorated( true );
        this.setVisible( true );
    }

    private void initComponents() {
        imagePanel = new JImagePanel(ImageUtil.getIcon("images/Thunder Blue.jpg"));

        username = new JTextField( "", 12 );
        password = new JPasswordField( "", 12 );

        accept = new JButton( "Aceptar" );
        cancel = new JButton( "Cancelar" );
    }

    private void initGUI() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridwidth = 3;
        imagePanel.add( new JLabel("DATOS DE LA CUENTA"), gbc );
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        imagePanel.add( new JLabel(" "), gbc );

        gbc.gridy = 2;
        imagePanel.add( new JLabel("Usuario:"), gbc );
        gbc.gridx = 1;
        imagePanel.add( new JLabel(" "), gbc );
        gbc.gridx = 2;
        imagePanel.add( username, gbc );

        gbc.gridy = 3;
        gbc.gridx = 0;
        imagePanel.add( new JLabel(" "), gbc );

        gbc.gridy = 4;
        imagePanel.add( new JLabel("Contraseña:"), gbc );
        gbc.gridx = 1;
        imagePanel.add( new JLabel(" "), gbc );
        gbc.gridx = 2;
        imagePanel.add( password, gbc );

        gbc.gridy = 5;
        gbc.gridx = 0;
        imagePanel.add( new JLabel(" "), gbc );

        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.CENTER;
        imagePanel.add( accept, gbc );
        gbc.gridx = 1;
        imagePanel.add( new JLabel(" "), gbc );
        gbc.gridx = 2;
        imagePanel.add( cancel, gbc );
    }

    private void addListeners() {
        username.addKeyListener( new EnterListener() );
        password.addKeyListener( new EnterListener() );
        accept.addActionListener( this );
        cancel.addActionListener( this );
    }

    private void check() {
        Cuenta account = new Cuenta();
        account.setUsername( username.getText() );
        account.setPassword( password.getText() );

        Consulta enquery = new Consulta();
        Cuenta[] accounts = (Cuenta[]) enquery.executeQuery( account.search(), "Cuentas" );

        if(accounts.length > 0) {
            if(accounts[0].getPassword().equals(account.getPassword())) {
                application.setAccount( accounts[0] );
                application.setEneable();

                this.dispose();
            } else {
                password.setText( "" );

                JOptionPane.showMessageDialog( null, "Usuario y/o contraseña incorrectos." );
            }
        } else {
            username.setText( "" );
            password.setText( "" );

            JOptionPane.showMessageDialog( null, "Usuario y/o contraseña incorrectos." );
        }
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Aceptar")) {
            check();
        } else if(s.equals("Cancelar")) {
            this.dispose();
        }
    }

    class EnterListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
            if(e.getKeyChar() == '\n')
                check();
        }

        public void keyPressed(KeyEvent e) {}

        public void keyReleased(KeyEvent e) {}

    }

}
