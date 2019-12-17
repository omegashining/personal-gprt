package aserradero.dialogs.authorization;

import aserradero.entities.Autorizacion;
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

import mx.gob.org.ipn.cic.mu.classes.MyDate;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JModifyAuthorization extends JAuthorization implements ActionListener {

    private JSeeAuthorization seeAuthorization;
    private JButton save;
    private JButton close;

    public JModifyAuthorization(JSeeAuthorization frame, Autorizacion authorization) {
        super(frame, "Modificar Autorización");

        this.seeAuthorization = frame;

        initComponents();
        initGUI();
        setFields( authorization );
        addListeners();

        this.setIconImage( ImageUtil.get("images/Edit.png") );
    }

    public JModifyAuthorization(JFrame frame, Autorizacion authorization) {
        super(frame, "Modificar Autorización");

        initComponents();
        initGUI();
        setFields( authorization );
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

        officeNumber.setEditable( false );
    }

    private void setFields(Autorizacion authorization) {
        MyDate date = new MyDate();

        if( authorization != null ) {
            officeNumber.setText( authorization.getOfficeNumber() );
            shelterCantity.setText( authorization.getShelterCantity() );
            inicialFolio.setText( authorization.getInicialFolio() );
            endFolio.setText( authorization.getFinalFolio() );
            measure.setText( authorization.getMeasure() );
            date.setDatabaseDate( authorization.getDate() );
            cbDay.setSelectedItem( date.getDay() );
            cbMonth.setSelectedItem( date.getMonth() );
            cbYear.setSelectedItem( date.getYear() );
            date.setDatabaseDate( authorization.getExpiration() );
            cbEDay.setSelectedItem( date.getDay() );
            cbEMonth.setSelectedItem( date.getMonth() );
            cbEYear.setSelectedItem( date.getYear() );
        }
    }

    private void addListeners() {
        shelterCantity.addKeyListener( new EnterListener() );
        inicialFolio.addKeyListener( new EnterListener() );
        endFolio.addKeyListener( new EnterListener() );
        measure.addKeyListener( new EnterListener() );
        save.addActionListener( this );
        close.addActionListener( this );
    }

    private void save() {
        Autorizacion authorization = getAuthorizationFromFields();

        if(!authorization.areEmptyFields()) {
            Consulta enquery = new Consulta();

            if(getDate().isValid() && getExpiration().isValid()) {
                if(enquery.executeUpdate(authorization.update())) {
                    JOptionPane.showMessageDialog( null, "Datos guardados satisfactoriamente." );

                    if(seeAuthorization != null)
                        seeAuthorization.reload();
                } else
                    JOptionPane.showMessageDialog( null, "No se pudieron guardar los datos." );
            } else
                JOptionPane.showMessageDialog( null, "Una de las fechas es incorrecta." );
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

