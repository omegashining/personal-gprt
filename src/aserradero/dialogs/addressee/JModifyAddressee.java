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
public class JModifyAddressee extends JAddressee implements ActionListener {

    private JSeeAddressee seeAddressee;
    private JButton save;
    private JButton close;

    public JModifyAddressee(JSeeAddressee frame, Destinatario addresse) {
        super(frame, "Modificar Destinatario");
        
        this.seeAddressee = frame;

        initComponents();
        initGUI();
        setFields( addresse );
        addListeners();

        this.setIconImage( ImageUtil.get("images/Edit.png") );
    }

    public JModifyAddressee(JFrame frame, Destinatario addresse) {
        super(frame, "Modificar Destinatario");

        initComponents();
        initGUI();
        setFields( addresse );
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
        gbc.gridy = 21;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 4;
        gbc.gridy = 22;
        gbc.gridwidth = 10;
        container.add( save, gbc );
        gbc.gridx = 18;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 23;
        container.add( new JLabel(" "), gbc );

        identificationCode.setEditable( false );
    }

    private void setFields(Destinatario addressee) {
        if(addressee != null) {
            identificationCode.setText( addressee.getIdentificationCode() );
            curp.setText( addressee.getCurp() );
            name.setText( addressee.getName() );
            rfn.setText( addressee.getRfn() );
            residenceDestiny.setText( addressee.getResidenceDestiny() );
            population.setText( addressee.getPopulation() );
            town.setText( addressee.getTown() );
            entity.setText( addressee.getEntity() );
            residence.setText( addressee.getResidence() );
        }
    }

    private void addListeners() {
        curp.addKeyListener( new EnterListener() );
        name.addKeyListener( new EnterListener() );
        rfn.addKeyListener( new EnterListener() );
        residenceDestiny.addKeyListener( new EnterListener() );
        population.addKeyListener( new EnterListener() );
        town.addKeyListener( new EnterListener() );
        entity.addKeyListener( new EnterListener() );
        residence.addKeyListener( new EnterListener() );
        save.addActionListener( this );
        close.addActionListener( this );
    }

    private void save() {
        Destinatario addressee = getAddresseeFromFields();

        if(!addressee.areEmptyFields()) {
            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(addressee.update())) {
                JOptionPane.showMessageDialog( null, "Datos guardados satisfactoriamente." );
                
                if(seeAddressee != null)
                    seeAddressee.reload();
            } else
                JOptionPane.showMessageDialog( null, "No se pudieron guardar los datos." );
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
