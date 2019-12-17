package aserradero.dialogs.invoice;

import aserradero.Application;
import aserradero.entities.Cliente;
import aserradero.entities.Factura;
import aserradero.entities.ProductoFacturado;
import aserradero.reports.InvoiceReport;
import aserradero.database.Consulta;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import mx.gob.org.ipn.cic.du.tables.JCenterTable;
import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;
import mx.gob.org.ipn.cic.mu.classes.MyDate;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JSeeInvoice extends JFrame implements ActionListener {

    private int rowSelected;

    private Application application;
    private Container container;
    private JCenterTable invoicesTable;
    private JCenterTable productsTable;
    private NotEditTableModel invoicesModel;
    private NotEditTableModel productsModel;
    private JScrollPane invoicesScroll;
    private JScrollPane productsScroll;
    private PopupMenu popupMenu;
    private MenuItem miDelete;
    private JButton pdf;
    private JButton close;

    public JSeeInvoice(Application frame) {
        super( "Facturas" );

        this.application = frame;

        initComponents();
        initGUI();
        setTables();
        addListeners();

        this.add( popupMenu );
        this.setIconImage( ImageUtil.get("images/Invoice.png") );
        this.setResizable( false );
        this.setSize( 750, 550 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        rowSelected = -1;

        container = this.getContentPane();

        invoicesTable  = new JCenterTable( new String[] {"No. Factura","Folio Factura","Fecha","R.F.C."} );
        productsTable  = new JCenterTable( new String[] {"No. Venta","Cantidad","Producto","Total"} );
        invoicesModel = (NotEditTableModel) invoicesTable.getModel();
        productsModel = (NotEditTableModel) productsTable.getModel();
        invoicesScroll = new JScrollPane( invoicesTable );
        productsScroll = new JScrollPane( productsTable );

        popupMenu = new PopupMenu();
        miDelete  = new MenuItem( "Eliminar Factura" );

        pdf   = new JButton( "Visualizar", ImageUtil.getIcon("images/PDF.png") );
        close = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        container.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel[] columns = new JLabel[ 45 ];
        for( int i = 0; i < columns.length; i++ ) {
            gbc.gridx = i;
            columns[ i ] = new JLabel( "     " );
            container.add( columns[ i ], gbc );
        }

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = columns.length;
        container.add( new JLabel("Facturas"), gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 43;
        container.add( invoicesScroll, gbc );

        gbc.gridx = 0;
        gbc.gridy = 4;
        container.add( new JLabel(" "), gbc );

        gbc.gridy = 5;
        gbc.gridwidth = 45;
        gbc.fill = GridBagConstraints.CENTER;
        container.add( new JLabel("Productos registrados en la factura"), gbc );

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 43;
        container.add( productsScroll, gbc );

        gbc.gridx = 0;
        gbc.gridy = 8;
        container.add( new JLabel(" "), gbc );

        gbc.gridx = 12;
        gbc.gridy = 9;
        gbc.gridwidth = 7;
        container.add( pdf, gbc );
        gbc.gridx = 24;
        gbc.gridwidth = 7;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 10;
        container.add( new JLabel(" "), gbc );

        if(application.getAccount().havePermissions() || application.getAccount().havePermissions())
            popupMenu.add( miDelete );
    }

    private void setTables() {
        TableColumn column;
        column = invoicesTable.getColumn( "No. Factura" );
        column.setPreferredWidth( 40 );
        column = productsTable.getColumn( "No. Venta" );
        column.setPreferredWidth( 35 );

        invoicesScroll.setPreferredSize( new Dimension(600,200) );
        productsScroll.setPreferredSize( new Dimension(600,150) );

        new Thread( new FillInvoiceTable(invoicesModel) ).start();
    }

    private void addListeners() {
        invoicesTable.addMouseListener( new PopupListener() );
        miDelete.addActionListener( this );
        pdf.addActionListener( this );
        close.addActionListener( this );

        invoicesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rowSelected = invoicesTable.rowAtPoint(e.getPoint());
                setSalesFromInvoice();
            }
        });
    }

    private void setSalesFromInvoice() {
        ProductoFacturado product = new ProductoFacturado();
        product.setInvoiceInvoiceNumber( invoicesModel.getValueAt(rowSelected,1).toString() );
        product.setInvoiceDate( getDate().databaseFormat() );

        Consulta enquery = new Consulta();
        ProductoFacturado[] products = (ProductoFacturado[]) enquery.executeQuery( product.search(), "Productos Facturados" );

        for(int i = productsModel.getRowCount()-1; i >= 0; i--)
            productsModel.removeRow( i );

        for(int j = 0; j < products.length; j++) {
            Object[] os = new Object[ 5 ];
            os[0] = j + 1;
            os[1] = products[j].getCantity();
            os[2] = products[j].getDescription();
            os[3] = products[j].getPrice();

            productsModel.addRow( os );
        }
    }

    private void renameInvoiceNumbers() {
        for( int i = 0; i < invoicesModel.getRowCount(); i++ )
            invoicesModel.setValueAt( i+1, i, 0);
    }

    private MyDate getDate() {
        StringTokenizer st = new StringTokenizer( invoicesModel.getValueAt(rowSelected,2).toString(), "-" );

        int day = Integer.parseInt( st.nextToken().trim() );
        int month = Integer.parseInt( st.nextToken().trim() );
        int year = Integer.parseInt( st.nextToken().trim() );

        MyDate date = new MyDate( day, month, year );

        return date;
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog( this, "¿Está seguro de eliminar la factura número "+(rowSelected+1)+"?");

        if(JOptionPane.OK_OPTION == confirm) {
            Consulta enquery = new Consulta();

            ProductoFacturado product = new ProductoFacturado();
            product.setInvoiceInvoiceNumber( invoicesModel.getValueAt(rowSelected,1).toString() );
            product.setInvoiceDate( getDate().databaseFormat() );
            enquery.executeUpdate(product.deleteForInvoice());

            Factura invoice = new Factura();
            invoice.setInvoiceNumber( product.getInvoiceInvoiceNumber() );
            invoice.setDate( product.getInvoiceDate() );

            if(enquery.executeUpdate(invoice.delete())) {
                invoicesModel.removeRow( rowSelected );
                renameInvoiceNumbers();

                JOptionPane.showMessageDialog( null, "Factura eliminada satisfactoriamente." );
            }
            rowSelected = -1;
        }
    }

    private void see() {
        if( rowSelected == -1 )
            JOptionPane.showMessageDialog( null, "No se ha seleccionado ninguna factura." );
        else {
            try {
                Consulta enquery = new Consulta();

                Factura invoice = new Factura();
                invoice.setInvoiceNumber( invoicesModel.getValueAt(rowSelected,1).toString() );
                invoice.setDate( getDate().databaseFormat() );

                String reportName = getDate().format() + "_" + invoice.getInvoiceNumber();

                String directory = System.getProperty("user.home") + "\\Reports\\Invoices\\" + reportName + ".pdf";
                
                File file = new File( directory );

                if(file.exists()) {
                    String command = "cmd /c \"" + directory + "\"";
                    Process p = Runtime.getRuntime().exec( command );
                } else {
                    Cliente client = new Cliente();
                    client.setRfc( invoicesModel.getValueAt(rowSelected,3).toString() );

                    ProductoFacturado product = new ProductoFacturado();
                    product.setInvoiceInvoiceNumber( invoice.getInvoiceNumber() );
                    product.setInvoiceDate( invoice.getDate() );

                    Cliente[] clients = (Cliente[]) enquery.executeQuery( client.search(), "Clientes" );
                    
                    Factura[] invoices = (Factura[]) enquery.executeQuery( invoice.search(), "Facturas" );

                    ProductoFacturado[] products = (ProductoFacturado[]) enquery.executeQuery( product.search(), "Productos Facturados" );

                    InvoiceReport report = new InvoiceReport( invoices[0], clients[0], products );
                    report.create();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if( s.equals( "Eliminar Factura" ) ) {
            delete();
        } if( s.equals( "Visualizar" ) ) {
            see();
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
              rowSelected = invoicesTable.rowAtPoint(e.getPoint());
              invoicesTable.setRowSelectionInterval(rowSelected, rowSelected);
              popupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
        }
    }

}
