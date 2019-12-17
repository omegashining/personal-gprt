package aserradero.dialogs.client;

import aserradero.database.Consulta;
import aserradero.entities.Cliente;

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
public class JAddClient extends JClient implements ActionListener {

    private JButton add;
    private JButton close;

    public JAddClient(JFrame frame) {
        super(frame, "Agregar Cliente");

        initComponents();
        initGUI();
        addListeners();
        
        this.setIconImage( ImageUtil.get("images/Add Client.png") );
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
        gbc.gridy = 15;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 5;
        gbc.gridy = 16;
        gbc.gridwidth = 8;
        container.add( add, gbc );
        gbc.gridx = 20;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 17;
        container.add( new JLabel(" "), gbc );
    }

    private void addListeners() {
        rfc.addKeyListener( new EnterListener() );
        name.addKeyListener( new EnterListener() );
        paternal.addKeyListener( new EnterListener() );
        maternal.addKeyListener( new EnterListener() );
        address.addKeyListener( new EnterListener() );
        place.addKeyListener( new EnterListener() );
        add.addActionListener( this );
        close.addActionListener( this );
    }

    private void clearFields() {
        rfc.setText( "" );
        name.setText( "" );
        paternal.setText( "" );
        maternal.setText( "" );
        address.setText( "" );
        place.setText( "" );
    }

    private void add() {
        Cliente client = getClientFromFields();

        if(!client.areEmptyFields()) {
            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(client.insert())) {
                JOptionPane.showMessageDialog( null, "Cliente agregado satisfactoriamente." );

                int option = JOptionPane.showConfirmDialog( JAddClient.this, "¿Desea agregar otro cliente?" );

                if(JOptionPane.NO_OPTION == option || JOptionPane.CANCEL_OPTION == option || JOptionPane.CLOSED_OPTION == option)
                    this.dispose();
                
                clearFields();
            } else
                JOptionPane.showMessageDialog( null, "Ya existe un cliente con el mismo R.F.C." );
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
