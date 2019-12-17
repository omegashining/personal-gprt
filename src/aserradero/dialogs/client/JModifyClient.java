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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JModifyClient extends JClient implements ActionListener {

    private JSeeClient seeClient;
    private JButton save;
    private JButton close;

    public JModifyClient(JSeeClient frame, Cliente client) {
        super(frame, "Modificar Cliente");

        this.seeClient = frame;

        initComponents();
        initGUI();
        setFields( client );
        addListeners();

        this.setIconImage( ImageUtil.get("images/Edit.png") );
    }

    private void initComponents() {
        save  = new JButton( "Guardar Cambios", ImageUtil.getIcon("images/Save.png") );
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
        gbc.gridwidth = 10;
        container.add( save, gbc );
        gbc.gridx = 19;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 17;
        container.add( new JLabel(" "), gbc );

        rfc.setEditable( false );
    }

    private void setFields(Cliente client) {
        if( client != null ) {
            rfc.setText( client.getRfc() );
            name.setText( client.getName() );
            paternal.setText( client.getPaternalSurname() );
            maternal.setText( client.getMaternalSurname() );
            address.setText( client.getAddress() );
            place.setText( client.getCity() );
        }
    }

    private void addListeners() {
        name.addKeyListener( new EnterListener() );
        paternal.addKeyListener( new EnterListener() );
        maternal.addKeyListener( new EnterListener() );
        address.addKeyListener( new EnterListener() );
        place.addKeyListener( new EnterListener() );
        save.addActionListener( this);
        close.addActionListener( this );
    }

    private void save() {
        Cliente client = getClientFromFields();

        if(!client.areEmptyFields()) {
            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(client.update())) {
                JOptionPane.showMessageDialog( null, "Datos guardados satisfactoriamente." );
                seeClient.reload();
            } else
                JOptionPane.showMessageDialog( null, "No se pudieron guardar los datos." );
        } else
            JOptionPane.showMessageDialog( null, "Debe llenar todos los campos vacíos." );
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Guardar Cambios")) {
            save();
        } else if(s.equals("Cerrar")) {
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
