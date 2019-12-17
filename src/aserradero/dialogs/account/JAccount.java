package aserradero.dialogs.account;

import aserradero.entities.Cuenta;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Gabriel
 */
public abstract class JAccount extends JFrame {

    protected JTextField username;
    protected JPasswordField password;
    protected JPasswordField rewrite;

    public JAccount(JFrame frame, String title) {
        super( title );

        initComponents();
        initGUI();

        this.setResizable( false );
        this.setSize( 420, 290 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        username = new JTextField();
        password = new JPasswordField();
        rewrite  = new JPasswordField();
    }

    private void initGUI() {
        Container container = this.getContentPane();
        container.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel[] columns = new JLabel[ 22 ];
        for(int column = 0; column < columns.length; column++) {
            gbc.gridx = column;
            columns[column] = new JLabel( "     " );
            container.add( columns[column], gbc );
        }

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = columns.length;
        container.add( new JLabel("DATOS DE LA CUENTA"), gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 3;
        gbc.gridwidth = 9;
        container.add( new JLabel("Usuario:"), gbc );
        gbc.gridx = 9;
        gbc.gridwidth = 13;
        container.add( username, gbc );

        gbc.gridx = 0;
        gbc.gridy = 4;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 5;
        gbc.gridwidth = 9;
        container.add( new JLabel("Contraseña:"), gbc );
        gbc.gridx = 9;
        gbc.gridwidth = 13;
        container.add( password, gbc );

        gbc.gridx = 0;
        gbc.gridy = 6;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 7;
        gbc.gridwidth = 9;
        container.add( new JLabel("Confirmar contraseña:"), gbc );
        gbc.gridx = 9;
        gbc.gridwidth = 13;
        container.add( rewrite, gbc );

        gbc.gridx = 0;
        gbc.gridy = 8;
        container.add( new JLabel(" "), gbc );
        
        gbc.gridy = 9;
        gbc.gridwidth = 10;
        container.add( new JLabel(" "), gbc );
    }

    protected Cuenta getCuentaFromFields() {
        Cuenta account = new Cuenta();

        account.setUsername( username.getText() );
        account.setPassword( password.getText() );
        account.setRewrite( rewrite.getText());
        account.setPermission( "0" );

        return account;
    }

}
