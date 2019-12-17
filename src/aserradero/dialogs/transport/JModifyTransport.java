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
public class JModifyTransport extends JTransport implements ActionListener {

    private JSeeTransport seeTransport;
    private JButton save;
    private JButton close;

    public JModifyTransport(JSeeTransport frame, Transporte transport) {
        super(frame, "Modificar Transporte");

        this.seeTransport = frame;

        initComponents();
        initGUI();
        addListeners();
        setFields( transport );

        this.setIconImage( ImageUtil.get("images/Edit.png") );
    }

    public JModifyTransport(JFrame frame, Transporte transport) {
        super(frame, "Modificar Transporte");

        initComponents();
        initGUI();
        addListeners();
        setFields( transport );

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
        gbc.gridy = 17;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 4;
        gbc.gridy = 18;
        gbc.gridwidth = 10;
        container.add( save, gbc );
        gbc.gridx = 18;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 19;
        container.add( new JLabel(" "), gbc );

        licensePlate.setEditable( false );
    }

    private void addListeners() {
        name.addKeyListener( new EnterListener() );
        proprietor.addKeyListener( new EnterListener() );
        mark.addKeyListener( new EnterListener() );
        type.addKeyListener( new EnterListener() );
        model.addKeyListener( new EnterListener() );
        capacity.addKeyListener( new EnterListener() );
        model.addKeyListener( new IntegerListener() );
        save.addActionListener( this);
        close.addActionListener( this );
    }

    private void setFields(Transporte transport) {
        if( transport != null ) {
            licensePlate.setText( transport.getLicensePlate() );
            name.setText( transport.getTransport() );
            proprietor.setText( transport.getProprietor() );
            mark.setText( transport.getMark() );
            type.setText( transport.getType() );
            model.setText( transport.getModel() );
            capacity.setText( transport.getCapacity() );
        }
    }

    private void save() {
        Transporte transport = getTransportFromFields();

        if(!transport.areEmptyFields()) {
            Consulta enquery = new Consulta();
            
            if(enquery.executeUpdate(transport.update())) {
                JOptionPane.showMessageDialog( null, "Datos guardados satisfactoriamente." );

                if(seeTransport != null)
                    seeTransport.reload();
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
