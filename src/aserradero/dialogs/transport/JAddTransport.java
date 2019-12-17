package aserradero.dialogs.transport;

import aserradero.entities.Transporte;
import aserradero.database.Consulta;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import mx.gob.org.ipn.cic.du.listeners.IntegerListener;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JAddTransport extends JTransport implements ActionListener {

    private JButton add;
    private JButton close;

    public JAddTransport(JFrame frame) {
        super(frame, "Agregar Transporte");

        initComponents();
        initGUI();
        addListeners();

        this.setIconImage( ImageUtil.get("images/Add Transport.png") );
    }

    private void initComponents() {
        add   = new JButton( "Agregar", ImageUtil.getIcon("images/Add.png") );
        close = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        Container container = this.getContentPane();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 17;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 5;
        gbc.gridy = 18;
        gbc.gridwidth = 10;
        container.add( add, gbc );
        gbc.gridx = 18;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 19;
        container.add( new JLabel(" "), gbc );
    }

    private void addListeners() {
        licensePlate.addKeyListener( new EnterListener() );
        name.addKeyListener( new EnterListener() );
        proprietor.addKeyListener( new EnterListener() );
        mark.addKeyListener( new EnterListener() );
        type.addKeyListener( new EnterListener() );
        model.addKeyListener( new EnterListener() );
        capacity.addKeyListener( new EnterListener() );
        model.addKeyListener( new IntegerListener() );
        add.addActionListener( this );
        close.addActionListener( this );
    }

    private void clearFields() {
        licensePlate.setText( "" );
        name.setText( "" );
        proprietor.setText( "" );
        mark.setText( "" );
        type.setText( "" );
        model.setText( "" );
        capacity.setText( "" );
    }

    private void add() {
        Transporte trasnport = getTransportFromFields();

        if( !trasnport.areEmptyFields() ) {
            Consulta enquery = new Consulta();
            
            if(enquery.executeUpdate(trasnport.insert())) {
                JOptionPane.showMessageDialog( null, "Transporte agregado satisfactoriamente." );

                int option = JOptionPane.showConfirmDialog( JAddTransport.this, "¿Desea agregar otro transporte?" );

                if(JOptionPane.NO_OPTION == option || JOptionPane.CANCEL_OPTION == option || JOptionPane.CLOSED_OPTION == option)
                    this.dispose();
                
                clearFields();
            } else
                JOptionPane.showMessageDialog( null, "Ya existe un transporte con las mismas placas" );
        } else
            JOptionPane.showMessageDialog( null, "Debe llenar todos los campos vacíos." );
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if( s.equals( "Agregar" ) ) {
            add();
        } else if( s.equals( "Cerrar" ) ) {
            this.dispose();
        }
    }

    class EnterListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
            if(e.getKeyChar() == '\n')
                add();
        }

        public void keyPressed(KeyEvent e) {}

        public void keyReleased(KeyEvent e) {}

    }

}
