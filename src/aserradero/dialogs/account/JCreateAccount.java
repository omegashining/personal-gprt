package aserradero.dialogs.account;

import aserradero.entities.Cuenta;
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
public class JCreateAccount extends JAccount implements ActionListener {

    private JButton create;
    private JButton close;

    public JCreateAccount(JFrame frame) {
        super(frame, "Crear Cuenta");

        initComponents();
        initGUI();
        addListeners();
        
        this.setIconImage( ImageUtil.get("images/Create Account.png") );
    }

    private void initComponents() {
        create = new JButton( "Crear", ImageUtil.getIcon("images/Add.png") );
        close  = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        Container container = this.getContentPane();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridwidth = 9;
        container.add( create, gbc );
        gbc.gridx = 12;
        container.add( close , gbc );

        gbc.gridx = 0;
        gbc.gridy = 12;
        container.add( new JLabel(" "), gbc );
    }

    private void addListeners() {
        username.addKeyListener( new EnterListener() );
        password.addKeyListener( new EnterListener() );
        rewrite.addKeyListener( new EnterListener() );
        create.addActionListener( this );
        close.addActionListener( this );
    }

    private void create() {
        Cuenta account = getCuentaFromFields();

        if(!account.areEmptyFields()) {
            if(account.isPasswordValid()) {
                Consulta enquery = new Consulta();

                if(enquery.executeUpdate(account.insert())) {
                    this.dispose();

                    JOptionPane.showMessageDialog( null, "Cuenta creada satisfactoriamente." );
                } else
                    JOptionPane.showMessageDialog( null, "Ya existe una cuenta con el mismo nombre de Usuario." );
            } else
                JOptionPane.showMessageDialog( null, "Los parámetros Contraseña y Confirmar Contraseña no coinciden." );
        } else
            JOptionPane.showMessageDialog( null, "Debe llenar todos los campos vacíos." );
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Crear")) {
            create();
        } else if(s.equals("Cerrar")) {
            this.dispose();
        }
    }

    class EnterListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
            if(e.getKeyChar() == '\n')
                create();
        }

        public void keyPressed(KeyEvent e) {}
        
        public void keyReleased(KeyEvent e) {}

    }

}
