package aserradero.dialogs.authorization;

import aserradero.entities.Autorizacion;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import mx.gob.org.ipn.cic.du.listeners.DoubleListener;
import mx.gob.org.ipn.cic.du.listeners.IntegerListener;
import mx.gob.org.ipn.cic.du.panels.JImagePanel;
import mx.gob.org.ipn.cic.mu.classes.MyDate;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public abstract class JAuthorization extends JFrame {

    protected JTextField officeNumber;
    protected JTextField shelterCantity;
    protected JTextField inicialFolio;
    protected JTextField endFolio;
    protected JTextField measure;
    protected JComboBox cbDay;
    protected JComboBox cbMonth;
    protected JComboBox cbYear;
    protected JComboBox cbEDay;
    protected JComboBox cbEMonth;
    protected JComboBox cbEYear;

    public JAuthorization(JFrame frame, String title) {
        super( title );

        initComponents();
        initGUI();
        addListeners();

        this.setResizable( false );
        this.setSize( 580, 430 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        officeNumber   = new JTextField();
        shelterCantity = new JTextField();
        inicialFolio   = new JTextField();
        endFolio       = new JTextField();
        measure        = new JTextField();

        cbDay    = new JComboBox();
        cbMonth  = new JComboBox();
        cbYear   = new JComboBox();
        cbEDay   = new JComboBox();
        cbEMonth = new JComboBox();
        cbEYear  = new JComboBox();
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
        container.add( new JLabel("DATOS DE LA AUTORIZACIÓN"), gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 3;
        gbc.gridwidth = 20;
        container.add( new JLabel("Número de Oficio de autorización de documentos:"), gbc );
        gbc.gridx = 20;
        gbc.gridwidth = 16;
        container.add( officeNumber, gbc );

        gbc.gridx = 0;
        gbc.gridy = 4;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 5;
        gbc.gridwidth = 9;
        container.add( new JLabel("Fecha:"), gbc );
        gbc.gridx = 9;
        gbc.gridwidth = 3;
        container.add( new JLabel("Día"), gbc );
        gbc.gridx = 12;
        gbc.gridwidth = 4;
        container.add( cbDay, gbc );
        gbc.gridx = 17;
        gbc.gridwidth = 3;
        container.add( new JLabel("Mes"), gbc );
        gbc.gridx = 20;
        gbc.gridwidth = 4;
        container.add( cbMonth, gbc );
        gbc.gridx = 25;
        gbc.gridwidth = 3;
        container.add( new JLabel("Año"), gbc );
        gbc.gridx = 28;
        gbc.gridwidth = 4;
        container.add( cbYear, gbc );

        gbc.gridx = 0;
        gbc.gridy = 6;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 7;
        gbc.gridwidth = 9;
        container.add( new JLabel("Cantidad que ampara:"), gbc );
        gbc.gridx = 9;
        gbc.gridwidth = 12;
        container.add( shelterCantity, gbc );

        gbc.gridx = 0;
        gbc.gridy = 8;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 9;
        gbc.gridwidth = 9;
        container.add( new JLabel("Folio inicial:"), gbc );
        gbc.gridx = 9;
        gbc.gridwidth = 12;
        container.add( inicialFolio, gbc );

        gbc.gridx = 0;
        gbc.gridy = 10;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 11;
        gbc.gridwidth = 9;
        container.add( new JLabel("Folio final:"), gbc );
        gbc.gridx = 9;
        gbc.gridwidth = 12;
        container.add( endFolio, gbc );

        gbc.gridx = 0;
        gbc.gridy = 12;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 13;
        gbc.gridwidth = 9;
        container.add( new JLabel("Unidad de medida:"), gbc );
        gbc.gridx = 9;
        gbc.gridwidth = 12;
        container.add( measure, gbc );

        gbc.gridx = 0;
        gbc.gridy = 14;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 15;
        gbc.gridwidth = 9;
        container.add( new JLabel("Fecha de vencimiento:"), gbc );
        gbc.gridx = 9;
        gbc.gridwidth = 3;
        container.add( new JLabel("Día"), gbc );
        gbc.gridx = 12;
        gbc.gridwidth = 4;
        container.add( cbEDay, gbc );
        gbc.gridx = 17;
        gbc.gridwidth = 3;
        container.add( new JLabel("Mes"), gbc );
        gbc.gridx = 20;
        gbc.gridwidth = 4;
        container.add( cbEMonth, gbc );
        gbc.gridx = 25;
        gbc.gridwidth = 3;
        container.add( new JLabel("Año"), gbc );
        gbc.gridx = 28;
        gbc.gridwidth = 4;
        container.add( cbEYear, gbc );

        gbc.gridx = 0;
        gbc.gridy = 16;
        container.add( new JLabel(" "), gbc );

        JImagePanel imagePanel = new JImagePanel(ImageUtil.getIcon("images/Authorization.png"));
        gbc.gridx = 24;
        gbc.gridy = 7;
        gbc.gridwidth = 7;
        gbc.gridheight = 7;
        container.add( imagePanel, gbc );

        for( int i = 1; i <= 31; i++ ) {
            cbDay.addItem( i );
            cbEDay.addItem( i );
        }

        for( int i = 1; i <= 12; i++ ) {
            cbMonth.addItem( i );
            cbEMonth.addItem( i );
        }

        for( int i = 0; i <= 30; i++ ) {
            cbYear.addItem( 2010 + i );
            cbEYear.addItem( 2010 + i );
        }
    }

    private void addListeners() {
        shelterCantity.addKeyListener( new DoubleListener() );
        inicialFolio.addKeyListener( new IntegerListener() );
        endFolio.addKeyListener( new IntegerListener() );
    }

    protected MyDate getDate() {
        int d = Integer.parseInt( cbDay.getSelectedItem().toString() );
        int m = Integer.parseInt( cbMonth.getSelectedItem().toString() );
        int y = Integer.parseInt( cbYear.getSelectedItem().toString() );
        MyDate date = new MyDate( d, m, y );

        return date;
    }

    protected MyDate getExpiration() {
        int ed = Integer.parseInt( cbEDay.getSelectedItem().toString() );
        int em = Integer.parseInt( cbEMonth.getSelectedItem().toString() );
        int ey = Integer.parseInt( cbEYear.getSelectedItem().toString() );
        MyDate expiration = new MyDate( ed, em, ey );

        return expiration;
    }

    protected Autorizacion getAuthorizationFromFields() {
        Autorizacion a = new Autorizacion();
        a.setOfficeNumber( officeNumber.getText() );
        a.setDate( getDate().toString() );
        a.setShelterCantity( shelterCantity.getText() );
        a.setInicialFolio( inicialFolio.getText() );
        a.setFinalFolio( endFolio.getText() );
        a.setMeasure( measure.getText() );
        a.setExpiration( getExpiration().toString() );

        return a;
    }

}
