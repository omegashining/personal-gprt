package aserradero.dialogs.client;

import aserradero.entities.Cliente;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import mx.gob.org.ipn.cic.du.panels.JImagePanel;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public abstract class JClient extends JFrame {

    protected JTextField rfc;
    protected JTextField name;
    protected JTextField paternal;
    protected JTextField maternal;
    protected JTextField address;
    protected JTextField place;

    public JClient(JFrame frame, String title) {
        super( title );

        initComponents();
        initGUI();

        this.setResizable( false );
        this.setSize( 600, 390 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        rfc      = new JTextField();
        name     = new JTextField();
        paternal = new JTextField();
        maternal = new JTextField();
        address  = new JTextField();
        place    = new JTextField();
    }

    private void initGUI() {
        Container container = this.getContentPane();
        container.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel[] columns = new JLabel[ 33 ];
        for(int column = 0; column < columns.length; column++) {
            gbc.gridx = column;
            columns[column] = new JLabel( "     " );
            container.add( columns[column], gbc );
        }

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = columns.length;
        container.add( new JLabel("DATOS DEL CLIENTE"), gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 16;
        gbc.gridy = 3;
        gbc.gridwidth = 8;
        container.add( new JLabel("R.F.C. del cliente:"), gbc );
        gbc.gridx = 24;
        gbc.gridwidth = 9;
        container.add( rfc, gbc );

        gbc.gridx = 0;
        gbc.gridy = 4;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 5;
        gbc.gridwidth = 7;
        container.add( new JLabel("Nombre(s):"), gbc );
        gbc.gridx = 7;
        gbc.gridwidth = 15;
        container.add( name, gbc );

        gbc.gridx = 0;
        gbc.gridy = 6;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 7;
        gbc.gridwidth = 7;
        container.add( new JLabel("Apellido Paterno:"), gbc );
        gbc.gridx = 7;
        gbc.gridwidth = 15;
        container.add( paternal, gbc );

        gbc.gridx = 0;
        gbc.gridy = 8;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 9;
        gbc.gridwidth = 7;
        container.add( new JLabel("Apellido Materno:"), gbc );
        gbc.gridx = 7;
        gbc.gridwidth = 15;
        container.add( maternal, gbc );

        gbc.gridx = 0;
        gbc.gridy = 10;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 11;
        gbc.gridwidth = 7;
        container.add( new JLabel("Dirección:"), gbc );
        gbc.gridx = 7;
        gbc.gridwidth = 26;
        container.add( address, gbc );

        gbc.gridx = 0;
        gbc.gridy = 12;
        container.add( new JLabel(" "), gbc );
        
        gbc.gridy = 13;
        gbc.gridwidth = 7;
        container.add( new JLabel("Lugar:"), gbc );
        gbc.gridx = 7;
        gbc.gridwidth = 26;
        container.add( place, gbc );

        gbc.gridx = 0;
        gbc.gridy = 14;
        container.add( new JLabel(" "), gbc );

        JImagePanel imagePanel = new JImagePanel(ImageUtil.getIcon("images/Client.png"));
        gbc.gridx = 25;
        gbc.gridy = 5;
        gbc.gridwidth = 6;
        gbc.gridheight = 5;
        container.add( imagePanel, gbc );
    }

    protected Cliente getClientFromFields() {
        Cliente c = new Cliente();
        c.setRfc( rfc.getText() );
        c.setName( name.getText() );
        c.setPaternalSurname( paternal.getText() );
        c.setMaternalSurname( maternal.getText() );
        c.setAddress( address.getText() );
        c.setCity( place.getText() );

        return c;
    }

}
