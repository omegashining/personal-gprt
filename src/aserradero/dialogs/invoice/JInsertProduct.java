package aserradero.dialogs.invoice;

import aserradero.entities.Producto;
import aserradero.database.Consulta;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mx.gob.org.ipn.cic.du.listeners.IntegerListener;
import mx.gob.org.ipn.cic.du.panels.JImagePanel;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JInsertProduct extends JDialog implements ActionListener {

    private int productSelected;
    private Producto[] products;

    private JPanel panel;
    private JComboBox cbProduct;
    private JTextField price;
    private JTextField measure;
    private JTextField cantity;
    private JButton insert;
    private JButton close;
    
    public JInsertProduct() {
        super(new JFrame(), "Insertar Producto", true);

        initComponents();
        initGUI();
        setComboBoxProduct();
        addListeners();
        
        this.add( panel );
        this.setIconImage( ImageUtil.get("images/Add.png") );
        this.setResizable( false );
        this.setSize( 480, 280 );
        this.setLocationRelativeTo( null );
        this.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        productSelected = 0;

        panel = new JPanel( new GridBagLayout() );

        cbProduct = new JComboBox();
        price   = new JTextField();
        measure = new JTextField();
        cantity = new JTextField();

        insert = new JButton( "Insertar", ImageUtil.getIcon("images/Add.png") );
        close  = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;

        JLabel[] columns = new JLabel[ 28 ];
        for(int column = 0; column < columns.length; column++) {
            gbc.gridx = column;
            columns[column] = new JLabel( "     " );
            panel.add( columns[column], gbc );
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = columns.length;
        panel.add( new JLabel("Datos del Producto"), gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 4;
        gbc.gridwidth = 6;
        panel.add( new JLabel("Descripción:"), gbc );
        gbc.gridx = 6;
        gbc.gridwidth = 12;
        panel.add( cbProduct, gbc );

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 6;
        gbc.gridwidth = 6;
        panel.add( new JLabel("Precio:"), gbc );
        gbc.gridx = 6;
        gbc.gridwidth = 12;
        panel.add( price, gbc );

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 8;
        gbc.gridwidth = 6;
        panel.add( new JLabel("Unidad de medida:"), gbc );
        gbc.gridx = 6;
        gbc.gridwidth = 12;
        panel.add( measure, gbc );

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 10;
        gbc.gridwidth = 6;
        panel.add( new JLabel("Cantidad:"), gbc );
        gbc.gridx = 6;
        gbc.gridwidth = 12;
        panel.add( cantity, gbc );

        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 12;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 5;
        gbc.gridy = 13;
        gbc.gridwidth = 7;
        panel.add( insert, gbc );
        gbc.gridx = 16;
        gbc.gridwidth = 7;
        panel.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 14;
        panel.add( new JLabel(" "), gbc );

        JImagePanel imagePanel = new JImagePanel(ImageUtil.getIcon("images/Tree.png"));
        gbc.gridx = 20;
        gbc.gridy = 4;
        gbc.gridwidth = 7;
        gbc.gridheight = 6;
        panel.add( imagePanel, gbc );

        price.setEditable( false );
        measure.setEditable( false );

        cbProduct.setActionCommand("box");
        cbProduct.setMaximumRowCount( 5 );
    }

    private void setComboBoxProduct() {
        Producto product = new Producto();
        Consulta enquery = new Consulta();
        products = (Producto[]) enquery.executeQuery( product.getAllProducts(), "Productos" );

        for(Producto ps: products)
            cbProduct.addItem( ps.getDescription() );

        if(products.length > 0) {
            product = products[0];
            price.setText( products[0].getPrice() );
            measure.setText( products[0].getMeasure() );
            cantity.setText( "1" );
        }
    }

    private void addListeners() {
        cbProduct.addActionListener( this );
        cantity.addKeyListener( new IntegerListener() );
        insert.addActionListener( this );
        close.addActionListener( this );
    }

    public Producto getProduct() {
        if(products.length > 0)
            return products[productSelected];

        return null;
    }

    public String getCantity() {
        return cantity.getText();
    }

    private void setCurrentProduct() {
        if(products.length > 0) {
            productSelected = cbProduct.getSelectedIndex();
            
            measure.setText( products[productSelected].getMeasure() );
            price.setText( products[productSelected].getPrice() );
            cantity.setText( "1" );
        }
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Insertar") ) {
            this.dispose();
        } else if(s.equals("Cerrar")) {
            this.dispose();
        } else if(s.equals("box")) {
            setCurrentProduct();
        }
    }

}
