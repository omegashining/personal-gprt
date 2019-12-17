package aserradero.dialogs.transport;

import aserradero.entities.Transporte;

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
public abstract class JTransport extends JFrame {

    protected JTextField licensePlate;
    protected JTextField name;
    protected JTextField proprietor;
    protected JTextField mark;
    protected JTextField type;
    protected JTextField model;
    protected JTextField capacity;

    public JTransport(JFrame frame, String title) {
        super( title );

        initComponents();
        initGUI();
        
        this.setResizable( false );
        this.setSize( 580, 430 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        licensePlate = new JTextField();
        name         = new JTextField();
        proprietor   = new JTextField();
        mark         = new JTextField();
        type         = new JTextField();
        model        = new JTextField();
        capacity     = new JTextField();
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
        container.add( new JLabel("Datos del Transporte"), gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 16;
        gbc.gridy = 3;
        gbc.gridwidth = 9;
        container.add( new JLabel("Placas del transporte:"), gbc );
        gbc.gridx = 25;
        gbc.gridwidth = 9;
        container.add( licensePlate, gbc );

        gbc.gridx = 0;
        gbc.gridy = 4;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 5;
        gbc.gridwidth = 10;
        container.add( new JLabel("Medio de transporte:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 23;
        container.add( name, gbc );

        gbc.gridx = 0;
        gbc.gridy = 6;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 7;
        gbc.gridwidth = 10;
        container.add( new JLabel("Propietario:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 23;
        container.add( proprietor, gbc );

        gbc.gridx = 0;
        gbc.gridy = 8;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 9;
        gbc.gridwidth = 10;
        container.add( new JLabel("Marca:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 10;
        container.add( mark, gbc );

        gbc.gridx = 0;
        gbc.gridy = 10;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 11;
        gbc.gridwidth = 10;
        container.add( new JLabel("Tipo:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 10;
        container.add( type, gbc );

        gbc.gridx = 0;
        gbc.gridy = 12;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 13;
        gbc.gridwidth = 10;
        container.add( new JLabel("Modelo:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 10;
        container.add( model, gbc );

        gbc.gridx = 0;
        gbc.gridy = 14;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 15;
        gbc.gridwidth = 10;
        container.add( new JLabel("Capacidad:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 10;
        container.add( capacity, gbc );

        gbc.gridx = 0;
        gbc.gridy = 16;
        container.add( new JLabel(" "), gbc );

        JImagePanel imagePanel = new JImagePanel(ImageUtil.getIcon("images/Truck.png"));
        gbc.gridx = 21;
        gbc.gridy = 9;
        gbc.gridwidth = 12;
        gbc.gridheight = 8;
        container.add( imagePanel, gbc );
    }

    protected Transporte getTransportFromFields() {
        Transporte t = new Transporte();
        t.setLicensePlate( licensePlate.getText() );
        t.setTransport( name.getText() );
        t.setProprietor( proprietor.getText() );
        t.setMark( mark.getText() );
        t.setType( type.getText() );
        t.setModel( model.getText() );
        t.setCapacity( capacity.getText() );

        return t;
    }

}
