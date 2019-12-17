package aserradero.dialogs.titular;

import aserradero.entities.Titular;
import aserradero.database.Consulta;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mx.gob.org.ipn.cic.du.listeners.IntegerListener;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JTitular extends JFrame implements ActionListener {
    
    private Container container;
    private JTextField name;
    private JTextField address;
    private JTextField town;
    private JTextField entity;
    private JTextField cp;
    private JTextField curp;
    private JTextField siem;
    private JTextField code;
    private JButton save;
    private JButton close;

    public JTitular(JFrame frame) {
        super("Datos del Titular");

        initComponents();
        initGUI();
        addListeners();

        this.setIconImage( ImageUtil.get("images/Titular.png") );
        this.setResizable( false );
        this.setSize( 680, 320 );
        this.setLocationRelativeTo( frame );
        this.setVisible( true );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
    }

    private void initComponents() {
        container = this.getContentPane();

        name    = new JTextField();
        address = new JTextField();
        cp      = new JTextField();
        town    = new JTextField();
        entity  = new JTextField();
        curp    = new JTextField();
        siem    = new JTextField();
        code    = new JTextField();

        save  = new JButton( "Guardar Cambios", ImageUtil.getIcon("images/Save.png") );
        close = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        container.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        JLabel[] columns = new JLabel[ 40 ];
        for( int i = 0; i < columns.length; i++ ) {
            gbc.gridx = i;
            columns[ i ] = new JLabel( "     " );
            container.add( columns[ i ], gbc );
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        container.add( new JLabel("Nombre:"), gbc );
        gbc.gridx = 5;
        gbc.gridwidth = 35;
        container.add( name, gbc );

        gbc.gridx = 0;
        gbc.gridy = 2;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 3;
        gbc.gridwidth = 5;
        container.add( new JLabel("Dirección:"), gbc );
        gbc.gridx = 5;
        gbc.gridwidth = 35;
        container.add( address, gbc );

        gbc.gridx = 0;
        gbc.gridy = 4;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 5;
        gbc.gridwidth = 5;
        container.add( new JLabel("Municipio:"), gbc );
        gbc.gridx = 5;
        gbc.gridwidth = 10;
        container.add( town, gbc );
        gbc.gridx = 16;
        gbc.gridwidth = 5;
        container.add( new JLabel("Entidad:"), gbc );
        gbc.gridx = 21;
        gbc.gridwidth = 10;
        container.add( entity, gbc );
        gbc.gridx = 32;
        gbc.gridwidth = 4;
        container.add( new JLabel("C.P.:"), gbc );
        gbc.gridx = 35;
        gbc.gridwidth = 5;
        container.add( cp, gbc );

        gbc.gridx = 0;
        gbc.gridy = 6;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 7;
        gbc.gridwidth = 5;
        container.add( new JLabel("C.U.R.P:"), gbc );
        gbc.gridx = 5;
        gbc.gridwidth = 14;
        container.add( curp, gbc );
        gbc.gridx = 20;
        gbc.gridwidth = 5;
        container.add( new JLabel("Registro SIEM:"), gbc );
        gbc.gridx = 26;
        gbc.gridwidth = 14;
        container.add( siem, gbc );

        gbc.gridx = 0;
        gbc.gridy = 8;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 8;
        gbc.gridy = 9;
        gbc.gridwidth = 10;
        container.add( new JLabel("Código de Identificación:"), gbc );
        gbc.gridx = 18;
        gbc.gridwidth = 14;
        container.add( code, gbc );

        gbc.gridx = 0;
        gbc.gridy = 10;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 11;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 8;
        gbc.gridy = 12;
        gbc.gridwidth = 11;
        container.add( save, gbc );
        gbc.gridx = 21;
        gbc.gridwidth = 11;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 13;
        container.add( new JLabel(" "), gbc );

        Titular titular = new Titular();
        Consulta enquery = new Consulta();
        Titular[] titulars = (Titular[]) enquery.executeQuery( titular.getAllTitulars(), "Titular" );

        if(titulars.length > 0)
            setFields( titulars[0] );
    }

    private void addListeners() {
        name.addKeyListener( new EnterListener() );
        address.addKeyListener( new EnterListener() );
        cp.addKeyListener( new EnterListener() );
        town.addKeyListener( new EnterListener() );
        entity.addKeyListener( new EnterListener() );
        curp.addKeyListener( new EnterListener() );
        siem.addKeyListener( new EnterListener() );
        code.addKeyListener( new EnterListener() );
        cp.addKeyListener( new IntegerListener() );
        save.addActionListener( this );
        close.addActionListener( this );
    }

    private void setFields(Titular t) {
        name.setText( t.getName() );
        address.setText( t.getAddress() );
        cp.setText( t.getCp() );
        town.setText( t.getTown() );
        entity.setText( t.getEntity() );
        curp.setText( t.getCurp() );
        siem.setText( t.getSiem() );
        code.setText( t.getIdentificationCode() );
    }

    private Titular getTitularFromFields() {
        Titular t = new Titular();
        t.setName( name.getText() );
        t.setAddress( address.getText() );
        t.setCp( cp.getText() );
        t.setTown( town.getText() );
        t.setEntity( entity.getText() );
        t.setCurp( curp.getText() );
        t.setSiem( siem.getText() );
        t.setIdentificationCode( code.getText() );

        return t;
    }

    private void save() {
        Titular titular = getTitularFromFields();

        if(!titular.areEmptyFields()) {
            Consulta enquery = new Consulta();
            Titular[] titulars = (Titular[]) enquery.executeQuery( titular.getAllTitulars(), "Titular" );

            if(titulars.length == 0) {
                if(enquery.executeUpdate(titular.insert()))
                    JOptionPane.showMessageDialog( null, "Datos guardados correctamente." );
                else
                    JOptionPane.showMessageDialog( null, "No se pudieron guardar los datos." );
            } else {
                if(enquery.executeUpdate(titular.update()))
                    JOptionPane.showMessageDialog( null, "Datos guardados correctamente." );
                else
                    JOptionPane.showMessageDialog( null, "No se pudieron guardar los datos." );
            }
        } else
            JOptionPane.showMessageDialog( null, "Debe llenar todos los campos vacíos." );
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if( s.equals( "Guardar Cambios" ) ) {
            save();
        } else if( s.equals( "Cerrar" ) ) {
            this.dispose();
        }
    }

    class EnterListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
            if(e.getKeyChar() == '\n')
                save();
        }

        public void keyPressed(KeyEvent e) {}

        public void keyReleased(KeyEvent e) {}

    }

}
