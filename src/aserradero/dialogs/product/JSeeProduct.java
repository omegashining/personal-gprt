package aserradero.dialogs.product;

import aserradero.Application;
import aserradero.entities.Producto;
import aserradero.database.Consulta;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import mx.gob.org.ipn.cic.du.tables.JCenterTable;
import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JSeeProduct extends JFrame implements ActionListener {

    private int rowSelected;

    private Application application;
    private Container container;
    private JCenterTable productTable;
    private NotEditTableModel productModel;
    private PopupMenu popupMenu;
    private MenuItem miModify;
    private MenuItem miDelete;
    private JButton buttonClose;

    public JSeeProduct(Application frame) {
        super( "Ver Productos" );

        this.application = frame;

        initComponents();
        initGUI();
        setProductTable();
        addListeners();

        this.add( popupMenu );
        this.setIconImage( ImageUtil.get("images/See Product.png") );
        this.setResizable( false );
        this.setSize( 650, 380 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        rowSelected = -1;

        container = this.getContentPane();

        productTable = new JCenterTable( new String[] {"No. Producto","Descripción","Unidad de Medida","Precio"} );

        productModel = (NotEditTableModel) productTable.getModel();
        
        popupMenu = new PopupMenu();
        miModify  = new MenuItem( "Modificar" );
        miDelete  = new MenuItem( "Eliminar" );

        buttonClose = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        popupMenu.add( miModify );

        if(application.getAccount().havePermissions() || application.getAccount().havePermissions())
            popupMenu.add( miDelete );

        container.add( new JScrollPane(productTable), BorderLayout.CENTER );
        container.add( buttonClose, BorderLayout.SOUTH );
    }

    private void setProductTable() {
        TableColumn column;
        column = productTable.getColumn( "No. Producto" );
        column.setPreferredWidth( 40 );
        column = productTable.getColumn( "Descripción" );
        column.setPreferredWidth( 200 );
        column = productTable.getColumn( "Precio" );
        column.setPreferredWidth( 50 );

        new Thread( new FillProductTable(productModel) ).start();
    }

    private void addListeners() {
        productTable.addMouseListener( new PopupListener() );
        miModify.addActionListener( this );
        miDelete.addActionListener( this );
        buttonClose.addActionListener( this );
    }

    public void reload() {
        Producto product = new Producto();
        product.setDescription( productModel.getValueAt(rowSelected,1).toString() );

        Consulta enquery = new Consulta();
        Producto[] products = (Producto[]) enquery.executeQuery( product.search(), "Productos" );

        if(products.length > 0) {
            productModel.setValueAt(products[0].getMeasure(), rowSelected, 2);
            productModel.setValueAt(products[0].getPrice(), rowSelected, 3);
        }
    }

    private void modify() {
        Producto product = new Producto();
        product.setDescription( productModel.getValueAt(rowSelected,1).toString() );

        Consulta enquery = new Consulta();
        Producto[] products = (Producto[]) enquery.executeQuery( product.search(), "Productos" );

        if(products.length > 0)
            new JModifyProduct( this, products[0] );
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog( JSeeProduct.this, "¿Está seguro de eliminar el producto número " + (rowSelected+1) + "?" );

        if (JOptionPane.OK_OPTION == confirm) {
            Producto product = new Producto();
            product.setDescription( productModel.getValueAt(rowSelected,1).toString() );

            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(product.delete())) {
                productModel.removeRow( rowSelected );

                for(int i = 0; i < productModel.getRowCount(); i++)
                    productModel.setValueAt( i+1, i, 0 );

                JOptionPane.showMessageDialog( null, "Producto eliminado satisfactoriamente." );
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Modificar")) {
            modify();
        } else if(s.equals("Eliminar")) {
            delete();
        } else if(s.equals("Cerrar")) {
            this.dispose();
        }
    }

    class PopupListener implements MouseListener {

        public void mouseClicked(MouseEvent e) { }

        public void mouseEntered(MouseEvent e) { }

        public void mouseExited(MouseEvent e) { }

        public void mousePressed(MouseEvent e) {
          showPopup( e );
        }

        public void mouseReleased(MouseEvent e) {
          showPopup( e );
        }

        private void showPopup(MouseEvent e) {
          if (e.isPopupTrigger()) {
              rowSelected = productTable.rowAtPoint(e.getPoint());
              productTable.setRowSelectionInterval(rowSelected, rowSelected);
              popupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
        }
    }

}
