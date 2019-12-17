package aserradero.dialogs.addressee;

import aserradero.entities.Destinatario;
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

import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JAddAddressee extends JAddressee implements ActionListener {

    private JButton add;
    private JButton close;

    public JAddAddressee(JFrame frame) {
        super(frame, "Agregar Destinatario");

        initComponents();
        initGUI();
        addListeners();

        this.setIconImage( ImageUtil.get("images/Add Addressee.png") );
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
        gbc.gridy = 21;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 4;
        gbc.gridy = 22;
        gbc.gridwidth = 8;
        container.add( add, gbc );
        gbc.gridx = 18;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 23;
        container.add( new JLabel(" "), gbc );
    }

    private void addListeners() {
        identificationCode.addKeyListener( new EnterListener() );
        curp.addKeyListener( new EnterListener() );
        name.addKeyListener( new EnterListener() );
        rfn.addKeyListener( new EnterListener() );
        residenceDestiny.addKeyListener( new EnterListener() );
        population.addKeyListener( new EnterListener() );
        town.addKeyListener( new EnterListener() );
        entity.addKeyListener( new EnterListener() );
        residence.addKeyListener( new EnterListener() );
        add.addActionListener( this );
        close.addActionListener( this );
    }

    private void clearFields() {
        identificationCode.setText( "" );
        curp.setText( "" );
        name.setText( "" );
        rfn.setText( "" );
        residenceDestiny.setText( "" );
        population.setText( "" );
        town.setText( "" );
        entity.setText( "" );
        residence.setText( "" );
    }

    private void add() {
        Destinatario addressee = getAddresseeFromFields();

        if(!addressee.areEmptyFields()) {
            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(addressee.insert())) {
                JOptionPane.showMessageDialog( null, "Destinatario agregado satisfactoriamente." );

                int option = JOptionPane.showConfirmDialog( JAddAddressee.this, "¿Desea agregar otro destinatario?" );

                if(JOptionPane.NO_OPTION == option || JOptionPane.CANCEL_OPTION == option || JOptionPane.CLOSED_OPTION == option)
                    this.dispose();
                
                clearFields();
            } else
                JOptionPane.showMessageDialog( null, "Ya existe un destinatario con el mismo Código de Identificación." );
        } else
            JOptionPane.showMessageDialog( null, "Debe llenar todos los campos vacíos." );
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if( s.equals( "Agregar" ) ) {
            add();
        } else if(s.equals("Cerrar")) {
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
