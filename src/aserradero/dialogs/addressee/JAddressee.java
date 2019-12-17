package aserradero.dialogs.addressee;

import aserradero.entities.Destinatario;

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
public abstract class JAddressee extends JFrame {

    protected JTextField curp;
    protected JTextField name;
    protected JTextField identificationCode;
    protected JTextField rfn;
    protected JTextField residenceDestiny;
    protected JTextField population;
    protected JTextField town;
    protected JTextField entity;
    protected JTextField residence;

    public JAddressee(JFrame frame, String title) {
        super( title );

        initComponents();
        initGUI();

        this.setResizable( false );
        this.setSize( 620, 500 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        curp               = new JTextField();
        name               = new JTextField();
        identificationCode = new JTextField();
        rfn                = new JTextField();
        residenceDestiny   = new JTextField();
        population         = new JTextField();
        town               = new JTextField();
        entity             = new JTextField();
        residence          = new JTextField();
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
        container.add( new JLabel("DATOS DEL DESTINATARIO"), gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 14;
        gbc.gridy = 3;
        gbc.gridwidth = 10;
        container.add( new JLabel("Código de Identificación:"), gbc );
        gbc.gridx = 24;
        gbc.gridwidth = 9;
        container.add( identificationCode, gbc );

        gbc.gridx = 0;
        gbc.gridy = 4;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 5;
        gbc.gridwidth = 10;
        container.add( new JLabel("Nombre:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 23;
        container.add( name, gbc );

        gbc.gridx = 0;
        gbc.gridy = 6;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 7;
        gbc.gridwidth = 10;
        container.add( new JLabel("C.U.R.P:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 23;
        container.add( curp, gbc );

        gbc.gridx = 0;
        gbc.gridy = 8;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 9;
        gbc.gridwidth = 10;
        container.add( new JLabel("R.F.N.:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 23;
        container.add( rfn, gbc );

        gbc.gridx = 0;
        gbc.gridy = 10;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 11;
        gbc.gridwidth = 10;
        container.add( new JLabel("Domicilio destino:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 23;
        container.add( residenceDestiny, gbc );

        gbc.gridx = 0;
        gbc.gridy = 12;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 13;
        gbc.gridwidth = 10;
        container.add( new JLabel("Población:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 10;
        container.add( population, gbc );

        gbc.gridx = 0;
        gbc.gridy = 14;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 15;
        gbc.gridwidth = 10;
        container.add( new JLabel("Municipio:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 10;
        container.add( town, gbc );

        gbc.gridx = 0;
        gbc.gridy = 16;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 17;
        gbc.gridwidth = 10;
        container.add( new JLabel("Entidad:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 10;
        container.add( entity, gbc );

        gbc.gridx = 0;
        gbc.gridy = 18;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 19;
        gbc.gridwidth = 10;
        container.add( new JLabel("Domicilio:"), gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 23;
        container.add( residence, gbc );

        gbc.gridx = 0;
        gbc.gridy = 20;
        container.add( new JLabel(" "), gbc );
        
        gbc.gridx = 21;
        gbc.gridy = 12;
        gbc.gridwidth = 12;
        gbc.gridheight = 7;
        container.add( new JImagePanel(ImageUtil.getIcon("images/Addressee.png")), gbc );
    }

    protected Destinatario getAddresseeFromFields() {
        Destinatario t = new Destinatario();
        t.setIdentificationCode( identificationCode.getText() );
        t.setName( name.getText() );
        t.setCurp( curp.getText() );
        t.setRfn( rfn.getText() );
        t.setResidenceDestiny( residenceDestiny.getText() );
        t.setPopulation( population.getText() );
        t.setTown( town.getText() );
        t.setEntity( entity.getText() );
        t.setResidence( residence.getText() );

        return t;
    }
    
}
