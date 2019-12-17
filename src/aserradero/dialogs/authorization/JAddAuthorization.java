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

import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JAddAuthorization extends JAuthorization implements ActionListener {

    JButton add;
    JButton close;

    public JAddAuthorization(JFrame frame) {
        super(frame, "Agregar Autorización");

        initComponents();
        initGUI();
        addListeners();

        this.setIconImage( ImageUtil.get("images/Add Authorization.png") );
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
        officeNumber.addKeyListener( new EnterListener() );
        shelterCantity.addKeyListener( new EnterListener() );
        inicialFolio.addKeyListener( new EnterListener() );
        endFolio.addKeyListener( new EnterListener() );
        measure.addKeyListener( new EnterListener() );
        add.addActionListener( this );
        close.addActionListener( this );
    }

    private void clearFields() {
        officeNumber.setText( "" );
        shelterCantity.setText( "" );
        inicialFolio.setText( "" );
        endFolio.setText( "" );
        measure.setText( "" );
        cbDay.setSelectedIndex(0);
        cbMonth.setSelectedIndex(0);
        cbYear.setSelectedIndex(0);
        cbEDay.setSelectedIndex(0);
        cbEMonth.setSelectedIndex(0);
        cbEYear.setSelectedIndex(0);
    }

    private void add() {
        Autorizacion authorization = getAuthorizationFromFields();

        if(!authorization.areEmptyFields()) {
            Consulta enquery = new Consulta();

            if(getDate().isValid() && getExpiration().isValid()) {
                if(enquery.executeUpdate(authorization.insert())) {
                    JOptionPane.showMessageDialog( null, "Autorización agregada satisfactoriamente." );

                    int option = JOptionPane.showConfirmDialog( JAddAuthorization.this, "¿Desea agregar otra autorización?" );

                    if(JOptionPane.NO_OPTION == option || JOptionPane.CANCEL_OPTION == option || JOptionPane.CLOSED_OPTION == option)
                        this.dispose();

                    clearFields();
                } else
                    JOptionPane.showMessageDialog( null, "Ya existe una autorización con el mismo Número de Oficio de autorización de documentos." );
            } else
                JOptionPane.showMessageDialog( null, "Verifique las fechas. Ya que al menos una es incorrecta." );
        } else
            JOptionPane.showMessageDialog( null, "Debe llenar todos los campos vacíos." );
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Agregar")) {
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
