package aserradero.dialogs.invoice;

import aserradero.entities.Cliente;
import aserradero.entities.Factura;
import aserradero.entities.ProductoFacturado;
import aserradero.database.Consulta;
import aserradero.entities.Producto;
import aserradero.reports.InvoiceReport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import mx.gob.org.ipn.cic.du.listeners.IntegerListener;
import mx.gob.org.ipn.cic.du.tables.JCenterTable;
import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;
import mx.gob.org.ipn.cic.mu.classes.MyDate;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JInvoiceForm implements ActionListener {
    
    private int rowDelete;
    private String rfcaux;

    private JFrame frame;
    private JTextField number;
    private JTextField rfc;
    private JTextField name;
    private JTextField address;
    private JTextField place;
    private JTextField subTotal;
    private JTextField ivaValue;
    private JTextField iva;
    private JTextField total;
    private JComboBox cbDay;
    private JComboBox cbMonth;
    private JComboBox cbYear;
    private JCenterTable productsTable;
    private JCenterTable balancesTable;
    private NotEditTableModel productsModel;
    private NotEditTableModel balancesModel;
    private JScrollPane productsScroll;
    private JScrollPane balancesScroll;
    private PopupMenu popupMenu;
    private MenuItem miDelete;
    private MenuItem miDeleteAll;
    private JButton add;
    private JButton search;
    private JButton create;
    private JButton clear;

    public JInvoiceForm(JFrame frame) {
        this.frame = frame;

        initComponents();
        setBackgroundColors();
        setTables();
        setActualDay();
        noEditableFields();
        addListeners();
        clearAllFields();
    }

    private void initComponents() {
        rowDelete = 0;

        number   = new JTextField();
        rfc      = new JTextField();
        name     = new JTextField();
        address  = new JTextField();
        place    = new JTextField();
        subTotal = new JTextField();
        ivaValue = new JTextField();
        iva      = new JTextField();
        total    = new JTextField();
        
        cbDay   = new JComboBox();
        cbMonth = new JComboBox();
        cbYear  = new JComboBox();

        productsTable  = new JCenterTable( new String[] {"Fila","Cantidad","Descripción","P. Unitario","Importe"} );
        balancesTable  = new JCenterTable( new String[] {"SUB-TOTAL","I.V.A.","TOTAL"} );
        productsModel = (NotEditTableModel) productsTable.getModel();
        balancesModel = (NotEditTableModel) balancesTable.getModel();
        productsScroll = new JScrollPane(productsTable);
        balancesScroll = new JScrollPane(balancesTable);

        popupMenu   = new PopupMenu();
        miDelete    = new MenuItem( "Eliminar" );
        miDeleteAll = new MenuItem( "Eliminar todo" );

        add    = new JButton( ImageUtil.getIcon("images/Add.png") );
        search = new JButton( "Buscar", ImageUtil.getIcon("images/Search.png") );
        create = new JButton( "Crear Factura", ImageUtil.getIcon("images/PDF.png") );
        clear  = new JButton( "Limpiar campos", ImageUtil.getIcon("images/Clear.png") );
    }

    private void setBackgroundColors() {
        number.setBackground( Color.CYAN );
        ivaValue.setBackground( Color.CYAN );
        subTotal.setBackground( Color.LIGHT_GRAY );
        iva.setBackground( Color.LIGHT_GRAY );
        total.setBackground( Color.DARK_GRAY );
    }

    private void addListeners() {
        search.addActionListener( this );
        add.addActionListener( this );
        miDelete.addActionListener( this );
        miDeleteAll.addActionListener( this );
        create.addActionListener( this );
        clear.addActionListener( this );
        number.addKeyListener( new IntegerListener() );
        ivaValue.addKeyListener( new IntegerListener() );
        productsTable.addMouseListener( new PopupListener() );
    }

    private void setTables() {
        TableColumn column;
        column = productsTable.getColumn( "Fila" );
        column.setPreferredWidth( 30 );
        column = productsTable.getColumn( "Cantidad" );
        column.setPreferredWidth( 50 );
        column = productsTable.getColumn( "Descripción" );
        column.setPreferredWidth( 320 );

        productsModel.setRowCount( 0 );
        productsScroll.setPreferredSize( new Dimension(600,150) );

        balancesModel.setRowCount( 1 );
        balancesScroll.setPreferredSize( new Dimension(300,38) );
    }

    private void setActualDay() {
        for( int i = 1; i <= 31; i++ )
            cbDay.addItem( i );

        for( int i = 1; i <= 12; i++ )
            cbMonth.addItem( i );

        for( int i = 0; i <= 30; i++ )
            cbYear.addItem( 2000 + i );

        MyDate d = new MyDate();
        cbDay.setSelectedItem( d.getDay() );
        cbMonth.setSelectedItem( d.getMonth() );
        cbYear.setSelectedItem( d.getYear() );
    }

    private void noEditableFields() {
        name.setEditable( false );
        address.setEditable( false );
        place.setEditable( false );
        subTotal.setEditable( false );
        iva.setEditable( false );
        total.setEditable( false );
    }

    public JPanel getPanel() {
        JPanel panel = new JPanel( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;

        JLabel[] columns = new JLabel[ 45 ];
        for(int column = 0; column < columns.length; column++) {
            gbc.gridx = column;
            columns[column] = new JLabel( "     " );
            panel.add( columns[column], gbc );
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 45;
        JLabel factura = new JLabel("DATOS DE LA FACTURA");
        factura.setForeground(Color.RED);
        panel.add( factura, gbc );

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        panel.add( new JLabel("No."), gbc );
        gbc.gridx = 2;
        gbc.gridwidth = 3;
        panel.add( number, gbc );

        gbc.gridx = 34;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add( new JLabel("Día:"), gbc );
        gbc.gridx = 38;
        panel.add( new JLabel("Mes:"), gbc );
        gbc.gridx = 42;
        panel.add( new JLabel("Año:"), gbc );

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        panel.add( search, gbc );
        gbc.gridx = 9;
        gbc.gridwidth = 7;
        panel.add( new JLabel("R.F.C. del cliente:"), gbc );
        gbc.gridx = 16;
        gbc.gridwidth = 11;
        panel.add( rfc, gbc );
        gbc.gridx = 29;
        gbc.gridwidth = 3;
        panel.add( new JLabel("Fecha:"), gbc );
        gbc.gridx = 33;
        gbc.gridwidth = 3;
        panel.add( cbDay, gbc );
        gbc.gridx = 37;
        gbc.gridwidth = 3;
        panel.add( cbMonth, gbc );
        gbc.gridx = 41;
        gbc.gridwidth = 4;
        panel.add( cbYear, gbc );

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 5;
        panel.add( new JLabel("Nombre:"), gbc );
        gbc.gridx = 5;
        gbc.gridwidth = 40;
        panel.add( name, gbc );

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 5;
        panel.add( new JLabel("Dirección:"), gbc );
        gbc.gridx = 5;
        gbc.gridwidth = 40;
        panel.add( address, gbc );

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 5;
        panel.add( new JLabel("Lugar:"), gbc );
        gbc.gridx = 5;
        gbc.gridwidth = 40;
        panel.add( place, gbc );

        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 12;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        panel.add( add, gbc );
        gbc.gridx = 5;
        gbc.gridwidth = 40;
        panel.add( productsScroll, gbc );
        
        gbc.gridx = 0;
        gbc.gridy = 13;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 5;
        panel.add( new JLabel("Valor de I.V.A. (%)"), gbc );
        gbc.gridx = 6;
        gbc.gridwidth = 5;
        panel.add( ivaValue, gbc );
        gbc.gridx = 25;
        gbc.gridwidth = 20;
        panel.add( balancesScroll, gbc );

        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridheight = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 10;
        gbc.gridy = 16;
        gbc.gridwidth = 10;
        panel.add( create, gbc );
        gbc.gridx = 28;
        gbc.gridwidth = 10;
        panel.add( clear, gbc );

        gbc.gridx = 0;
        gbc.gridy = 17;
        gbc.gridheight = 1;
        panel.add( new JLabel(" "), gbc );
        
        number.setHorizontalAlignment( JTextField.CENTER );
        ivaValue.setHorizontalAlignment( JTextField.CENTER );
        add.setActionCommand( "Add" );

        popupMenu.add( miDelete );
        popupMenu.add( miDeleteAll );
        panel.add( popupMenu );

        return panel;
    }

    private void setTotal() {
        if( productsModel.getRowCount() != 0 ) {
            Double subtotal = 0.0;
            for( int i = 0; i < productsModel.getRowCount(); i++ )
                subtotal += Double.parseDouble(productsModel.getValueAt( i, 4 ).toString());

            Double i = 0.01 * subtotal * Double.parseDouble( ivaValue.getText() );
            Double t = subtotal + i;

            balancesModel.setValueAt( subtotal, 0, 0 );
            balancesModel.setValueAt( i, 0, 1 );
            balancesModel.setValueAt( t, 0, 2 );
        } else {
            balancesModel.setValueAt( "", 0, 0 );
            balancesModel.setValueAt( "", 0, 1 );
            balancesModel.setValueAt( "", 0, 2 );
        }
    }

    private void clearAllFields() {
        number.setText( "" );
        rfc.setText( "" );
        name.setText( "" );
        address.setText( "" );
        place.setText( "" );
        ivaValue.setText( "" );

        for( int i = productsModel.getRowCount()-1; i >= 0; i-- )
            productsModel.removeRow( i );

        setTotal();
    }

    private void setClientFields(Cliente client) {
        if( client != null ) {
            rfcaux = client.getRfc();
            name.setText( client.getName() + " " + client.getPaternalSurname() + " " + client.getMaternalSurname() );
            address.setText( client.getAddress() );
            place.setText( client.getCity() );
        }
    }

    private MyDate getDate() {
        int d = Integer.parseInt( cbDay.getSelectedItem().toString() );
        int m = Integer.parseInt( cbMonth.getSelectedItem().toString() );
        int y = Integer.parseInt( cbYear.getSelectedItem().toString() );
        MyDate date = new MyDate( d, m, y );

        return date;
    }

    private ProductoFacturado[] getSalesFromFields() {
        if( !name.getText().equals( "" ) && productsModel.getRowCount() != 0 ) {
            ProductoFacturado[] sales = new ProductoFacturado[ productsModel.getRowCount() ];
            for( int i = 0; i < sales.length; i++ ) {
                sales[ i ] = new ProductoFacturado();
                sales[ i ].setInvoiceInvoiceNumber( number.getText() );
                sales[ i ].setInvoiceDate( getDate().toString() );
                sales[ i ].setCantity( productsModel.getValueAt(i,1).toString() );
                sales[ i ].setDescription( productsModel.getValueAt(i,2).toString() );
                sales[ i ].setPrice( productsModel.getValueAt(i,3).toString() );
            }

            return sales;
        }

        return null;
    }

    private void search() {
        Cliente client = new Cliente();
        client.setRfc( rfc.getText() );

        Consulta enquery = new Consulta();
        Cliente[] clients = (Cliente[]) enquery.executeQuery( client.search(), "Clientes" );

        if(clients.length > 0)
            setClientFields( clients[0] );
        else
            JOptionPane.showMessageDialog( null, "No se encontro ningún cliente con el R.F.C.:\n" + rfc.getText() );
    }

    private void add() {
        if(!ivaValue.getText().equals("") ) {
            JInsertProduct insertProduct = new JInsertProduct();
            Producto product = insertProduct.getProduct();

            if(product != null) {
                boolean status = true;

                for(int i = 0; i < productsModel.getRowCount(); i++) {
                    String productTable = productsModel.getValueAt(i, 2).toString();

                    if((product.getDescription()+" ("+product.getMeasure()+")").equals(productTable)) {
                        int cantidad = Integer.parseInt( productsModel.getValueAt(i, 1).toString() );
                        cantidad = cantidad + Integer.parseInt( insertProduct.getCantity() );
                        productsModel.setValueAt( cantidad, i, 1 );
                        Double amount = Double.parseDouble( product.getPrice() ) * cantidad;
                        productsModel.setValueAt( amount, i, 4);
                        status = false;
                    }
                }

                if(status) {
                    Object[] o = new Object[ 5 ];
                    int row = productsModel.getRowCount();
                    productsModel.insertRow( row, o );
                    productsModel.setValueAt( Integer.parseInt( insertProduct.getCantity()), row, 1 );
                    productsModel.setValueAt( (product.getDescription()+" ("+product.getMeasure()+")"), row, 2 );
                    productsModel.setValueAt( Double.parseDouble( product.getPrice() ), row, 3 );
                    Double amount = Double.parseDouble( product.getPrice() ) * Integer.parseInt( insertProduct.getCantity() );
                    productsModel.setValueAt( amount, row, 4);
                }

                for( int i = 0; i < productsModel.getRowCount(); i++ )
                    productsModel.setValueAt( i + 1, i, 0);

                setTotal();
            }
        } else
            JOptionPane.showMessageDialog( frame, "Tiene que especificar primero el valor del I.V.A." );
    }

    private void deleteRow() {
        int confirm = JOptionPane.showConfirmDialog( frame, "¿Está seguro de eliminar el producto "+(rowDelete+1)+"?");

        if (JOptionPane.OK_OPTION == confirm) {
            productsModel.removeRow( rowDelete );

            for( int i = 0; i < productsModel.getRowCount(); i++ )
                productsModel.setValueAt( i + 1, i, 0);

            setTotal();
        }
    }

    private void deleteAll() {
        int confirm = JOptionPane.showConfirmDialog( frame, "¿Está seguro de eliminar todos los productos?" );

        if (JOptionPane.OK_OPTION == confirm) {
            for( int i = productsModel.getRowCount()-1; i >= 0; i-- )
                productsModel.removeRow( i );

            setTotal();
        }
    }

    private void create() {
        setTotal();

        if(!number.getText().equals("") && !name.getText().equals("") && !balancesModel.getValueAt(0,2).toString().equals("")) {
            Factura invoice = new Factura();
            invoice.setInvoiceNumber( number.getText() );
            invoice.setDate( getDate().databaseFormat() );
            invoice.setIva( ivaValue.getText() );
            invoice.setRfc( rfcaux );

            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(invoice.insert())) {
                ProductoFacturado[] sales = getSalesFromFields();

                boolean status = true;
                for(ProductoFacturado sale: sales) {
                    if(!enquery.executeUpdate(sale.insert()))
                        status = false;
                }

                Cliente client = new Cliente();
                client.setRfc( invoice.getClientRfc() );
                Cliente[] clients = (Cliente[]) enquery.executeQuery( client.search(), "Clientes" );

                if(status) {
                    JOptionPane.showMessageDialog( null, "Los datos fueron guardados correctamente\n" +
                                "Ahora Pulse Aceptar y Espere un momento para que se genere el documento." );
                    
                    InvoiceReport report = new InvoiceReport( invoice, clients[0], sales );
                    report.create();
                } else
                    JOptionPane.showMessageDialog( null, "Error al guardar los datos" );
            } else
                JOptionPane.showMessageDialog( null, "Ya existe una factura con el mismo Número de Factura en el mismo día." );
        } else {
            JOptionPane.showMessageDialog( null, "Debe llenar todos los campos vacíos, para que se genere el documento." );
        }
    }

    private void clear() {
        int confirm = JOptionPane.showConfirmDialog( frame, "¿Está seguro de limpiar todos los campos?");

        if ( JOptionPane.OK_OPTION == confirm )
            clearAllFields();
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if( s.equals( "Buscar" ) ) {
            search();
        } else if( s.equals( "Add" ) ) {
            add();
        } else if( s.equals( "Eliminar" ) ) {
            deleteRow();
        } else if( s.equals("Eliminar todo") ) {
            deleteAll();
        } else if( s.equals( "Crear Factura" ) ) {
            create();
        } else if( s.equals( "Limpiar campos" ) ) {
            clear();
        }
    }

    class PopupListener implements MouseListener {

        public void mouseClicked(MouseEvent e) { }

        public void mouseEntered(MouseEvent e) { }

        public void mouseExited(MouseEvent e) { }

        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }
        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
            if(e.isPopupTrigger()) {
                rowDelete = productsTable.rowAtPoint(e.getPoint());
                productsTable.setRowSelectionInterval(rowDelete, rowDelete);
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

}
