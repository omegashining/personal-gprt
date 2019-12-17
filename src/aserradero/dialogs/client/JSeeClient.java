package aserradero.dialogs.client;

import aserradero.Application;
import aserradero.entities.Cliente;
import aserradero.database.Consulta;
import aserradero.entities.Factura;
import aserradero.entities.ProductoFacturado;

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
public class JSeeClient extends JFrame implements ActionListener {

    private int rowSelected;

    private Application application;
    private Container container;
    private JCenterTable clientTable;
    private NotEditTableModel clientModel;
    private PopupMenu popupMenu;
    private MenuItem miModify;
    private MenuItem miDelete;
    private JButton close;

    public JSeeClient(Application frame) {
        super( "Ver Clientes" );

        this.application = frame;

        initComponents();
        initGUI();
        setAccountTable();
        addListeners();

        this.add( popupMenu );
        this.setIconImage( ImageUtil.get("images/See Client.png") );
        this.setResizable( false );
        this.setSize( 650, 380 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        rowSelected = -1;

        container = this.getContentPane();

        clientTable = new JCenterTable( new String[] {"No. Cliente","R.F.C.","Nombre","Apellido Paterno","Apellido Materno"} );

        clientModel = (NotEditTableModel) clientTable.getModel();
        
        popupMenu = new PopupMenu();
        miModify  = new MenuItem( "Modificar" );
        miDelete  = new MenuItem( "Eliminar" );

        close = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        popupMenu.add( miModify );
        
        if(application.getAccount().havePermissions() || application.getAccount().havePermissions())
            popupMenu.add( miDelete );

        container.add( new JScrollPane(clientTable), BorderLayout.CENTER );
        container.add( close, BorderLayout.SOUTH );
    }

    private void setAccountTable() {
        TableColumn column;
        column = clientTable.getColumn( "No. Cliente" );
        column.setPreferredWidth( 35 );
        column = clientTable.getColumn( "R.F.C." );
        column.setPreferredWidth( 100 );
        column = clientTable.getColumn( "Nombre" );
        column.setPreferredWidth( 140 );

        new Thread( new FillClientTable(clientModel) ).start();
    }

    private void addListeners() {
        clientTable.addMouseListener( new PopupListener() );
        miModify.addActionListener( this );
        miDelete.addActionListener( this );
        close.addActionListener( this );
    }

    public void reload() {
        Cliente client = new Cliente();
        client.setRfc( clientModel.getValueAt(rowSelected,1).toString() );

        Consulta enquery = new Consulta();
        Cliente[] clients = (Cliente[]) enquery.executeQuery( client.search(), "Clientes" );

        if(clients.length > 0) {
            clientModel.setValueAt(clients[0].getName(), rowSelected, 2);
            clientModel.setValueAt(clients[0].getPaternalSurname(), rowSelected, 3);
            clientModel.setValueAt(clients[0].getMaternalSurname(), rowSelected, 4);
        }
    }

    private void modify() {
        Cliente client = new Cliente();
        client.setRfc( clientModel.getValueAt(rowSelected,1).toString() );

        Consulta enquery = new Consulta();
        Cliente[] clients = (Cliente[]) enquery.executeQuery( client.search(), "Clientes" );

        if(clients.length > 0)
            new JModifyClient( this, clients[0] );
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog( JSeeClient.this, "¿Está seguro de eliminar el cliente número " + (rowSelected+1) + "?" );

        if (JOptionPane.OK_OPTION == confirm) {
            Consulta enquery = new Consulta();

            Cliente client = new Cliente();
            client.setRfc( clientModel.getValueAt(rowSelected,1).toString() );

            Factura invoice = new Factura();
            invoice.setRfc( client.getRfc() );
            Factura[] invoices = (Factura[]) enquery.executeQuery( invoice.searchForClient(), "Facturas" );

            ProductoFacturado shelterProducts;
            for(Factura i: invoices) {
                shelterProducts = new ProductoFacturado();
                shelterProducts.setInvoiceInvoiceNumber( i.getInvoiceNumber() );
                shelterProducts.setInvoiceDate( i.getDate() );
                enquery.executeUpdate( shelterProducts.deleteForInvoice() );
            }

            if(enquery.executeUpdate(client.delete())) {
                clientModel.removeRow( rowSelected );

                for(int i = 0; i < clientModel.getRowCount(); i++)
                    clientModel.setValueAt( i+1, i, 0 );

                JOptionPane.showMessageDialog( null, "Cliente eliminado satisfactoriamente." );
            } else
                JOptionPane.showMessageDialog( null, "No se pudo eliminar el cliente." );
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
              rowSelected = clientTable.rowAtPoint(e.getPoint());
              clientTable.setRowSelectionInterval(rowSelected, rowSelected);
              popupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
        }
    }

}
