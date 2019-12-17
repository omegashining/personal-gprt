package aserradero.dialogs.product;

import aserradero.entities.Producto;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import mx.gob.org.ipn.cic.du.listeners.DoubleListener;
import mx.gob.org.ipn.cic.du.panels.JImagePanel;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public abstract class JProduct extends JFrame {

    protected JTextField description;
    protected JTextField price;
    protected JTextField measure;

    public JProduct(JFrame frame, String title) {
        super( title );

        initComponents();
        initGUI();
        addListeners();
        
        this.setResizable( false );
        this.setSize( 540, 290 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        description = new JTextField();
        price       = new JTextField();
        measure     = new JTextField();
    }

    private void initGUI() {
        Container container = this.getContentPane();
        container.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel[] columns = new JLabel[30 ];
        for(int column = 0; column < columns.length; column++) {
            gbc.gridx = column;
            columns[column] = new JLabel( "     " );
            container.add( columns[column], gbc );
        }

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = columns.length;
        container.add( new JLabel("DATOS DEL PRODUCTO"), gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 4;
        gbc.gridwidth = 8;
        container.add( new JLabel("Descripción:"), gbc );
        gbc.gridx = 8;
        gbc.gridwidth = 12;
        container.add( description, gbc );

        gbc.gridx = 0;
        gbc.gridy = 5;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 6;
        gbc.gridwidth = 8;
        container.add( new JLabel("Precio:"), gbc );
        gbc.gridx = 8;
        gbc.gridwidth = 12;
        container.add( price, gbc );

        gbc.gridx = 0;
        gbc.gridy = 7;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 8;
        gbc.gridwidth = 8;
        container.add( new JLabel("Unidad de Medida:"), gbc );
        gbc.gridx = 8;
        gbc.gridwidth = 12;
        container.add( measure, gbc );

        gbc.gridx = 0;
        gbc.gridy = 9;
        container.add( new JLabel(" "), gbc );

        JImagePanel imagePanel = new JImagePanel(ImageUtil.getIcon("images/Tree.png"));
        gbc.gridx = 22;
        gbc.gridy = 4;
        gbc.gridwidth = 7;
        gbc.gridheight = 6;
        container.add( imagePanel, gbc );
    }

    private void addListeners() {
        price.addKeyListener( new DoubleListener() );
    }

    protected Producto getProductFromFields() {
        Producto p = new Producto();
        p.setDescription( description.getText() );
        p.setPrice( price.getText() );
        p.setMeasure( measure.getText() );
        
        return p;
    }

}
