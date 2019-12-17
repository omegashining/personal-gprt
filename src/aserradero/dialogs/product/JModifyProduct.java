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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JModifyProduct extends JProduct implements ActionListener {

    private JSeeProduct seeProduct;
    private JButton save;
    private JButton close;

    public JModifyProduct(JSeeProduct frame, Producto product) {
        super(frame, "Modificar Producto");

        this.seeProduct = frame;

        initComponents();
        initGUI();
        setFields( product );
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
        gbc.gridy = 10;
        container.add( new JLabel( " " ), gbc );

        gbc.gridx = 4;
        gbc.gridy = 11;
        gbc.gridwidth = 10;
        container.add( save, gbc );
        gbc.gridx = 16;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 12;
        container.add( new JLabel( " " ), gbc );

        description.setEditable( false );
    }

    private void setFields(Producto product) {
        if( product != null ) {
            description.setText( product.getDescription() );
            price.setText( product.getPrice() );
            measure.setText( product.getMeasure() );
        }
    }

    private void addListeners() {
        price.addKeyListener( new EnterListener() );
        measure.addKeyListener( new EnterListener() );
        save.addActionListener( this );
        close.addActionListener( this );
    }

    private void save() {
        Producto product = getProductFromFields();

        if(!product.areEmptyFields()) {
            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(product.update())) {
                JOptionPane.showMessageDialog( null, "Datos guardados satisfactoriamente." );
                seeProduct.reload();
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
