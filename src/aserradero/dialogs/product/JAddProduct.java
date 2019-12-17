package aserradero.dialogs.product;

import aserradero.database.Consulta;
import aserradero.entities.Producto;

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
public class JAddProduct extends JProduct implements ActionListener {

    private JButton add;
    private JButton close;

    public JAddProduct(JFrame frame) {
        super(frame, "Agregar Producto");

        initComponents();
        initGUI();
        addListeners();

        this.setIconImage( ImageUtil.get("images/Add Product.png") );
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
        gbc.gridy = 10;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 4;
        gbc.gridy = 11;
        gbc.gridwidth = 8;
        container.add( add, gbc );
        gbc.gridx = 18;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 12;
        container.add( new JLabel(" "), gbc );
    }

    private void addListeners() {
        description.addKeyListener( new EnterListener() );
        price.addKeyListener( new EnterListener() );
        measure.addKeyListener( new EnterListener() );
        add.addActionListener( this );
        close.addActionListener( this );
    }

    private void clearFields() {
        description.setText( "" );
        price.setText( "" );
        measure.setText( "" );
    }

    private void add() {
        Producto product = getProductFromFields();

        if(!product.areEmptyFields()) {
            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(product.insert())) {
                JOptionPane.showMessageDialog( null, "Producto agregado satisfactoriamente." );

                int option = JOptionPane.showConfirmDialog( JAddProduct.this, "¿Desea agregar otro producto?" );

                if(JOptionPane.NO_OPTION == option || JOptionPane.CANCEL_OPTION == option || JOptionPane.CLOSED_OPTION == option)
                    this.dispose();
                
                clearFields();
            } else
                JOptionPane.showMessageDialog( null, "Ya existe un producto con la misma Descripción" );
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
