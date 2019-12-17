package aserradero.dialogs.addressee;

import aserradero.Application;
import aserradero.entities.Destinatario;
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
public class JSeeAddressee extends JFrame implements ActionListener {
    
    private int rowSelected;

    private Application application;
    private Container container;
    private JCenterTable addressTable;
    private NotEditTableModel addressModel;
    private PopupMenu popupMenu;
    private MenuItem miModify;
    private MenuItem miDelete;
    private JButton close;

    public JSeeAddressee(Application frame) {
        super( "Ver Destinatarios" );

        this.application = frame;

        initComponents();
        initGUI();
        setAddresseeTable();
        addListeners();

        this.add( popupMenu );
        this.setIconImage( ImageUtil.get("images/See Transport.png") );
        this.setResizable( false );
        this.setSize( 650, 380 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        rowSelected = -1;

        container = this.getContentPane();

        addressTable = new JCenterTable( new String[] {"No. Destinatario","Código Identificación","Nombre","C.U.R.P."} );

        addressModel = (NotEditTableModel) addressTable.getModel();
        
        popupMenu = new PopupMenu();
        miModify  = new MenuItem( "Modificar" );
        miDelete  = new MenuItem( "Eliminar" );

        close    = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        popupMenu.add( miModify );

        if(application.getAccount().havePermissions() || application.getAccount().havePermissions())
            popupMenu.add( miDelete );

        container.add( new JScrollPane(addressTable), BorderLayout.CENTER );
        container.add( close, BorderLayout.SOUTH );
    }

    private void setAddresseeTable() {
        TableColumn column;
        column = addressTable.getColumn( "No. Destinatario" );
        column.setPreferredWidth( 50 );
        column = addressTable.getColumn( "Código Identificación" );
        column.setPreferredWidth( 100 );
        column = addressTable.getColumn( "Nombre" );
        column.setPreferredWidth( 120 );

        new Thread( new FillAddresseeTable(addressModel) ).start();
    }

    private void addListeners() {
        addressTable.addMouseListener( new PopupListener() );
        miModify.addActionListener( this );
        miDelete.addActionListener( this );
        close.addActionListener( this );
    }

    public void reload() {
        Destinatario addressee = new Destinatario();
        addressee.setIdentificationCode( addressModel.getValueAt(rowSelected,1).toString() );

        Consulta enquery = new Consulta();
        Destinatario[] addressees = (Destinatario[]) enquery.executeQuery( addressee.search(), "Destinatarios" );

        if(addressees.length > 0) {
            addressModel.setValueAt(addressees[0].getName(), rowSelected, 2);
            addressModel.setValueAt(addressees[0].getCurp(), rowSelected, 3);
        }
    }

    private void modify() {
        Destinatario addressee = new Destinatario();
        addressee.setIdentificationCode( addressModel.getValueAt(rowSelected,1).toString() );

        Consulta enquery = new Consulta();
        Destinatario[] addressees = (Destinatario[]) enquery.executeQuery( addressee.search(), "Destinatarios" );

        if(addressees.length > 0)
            new JModifyAddressee( this, addressees[0] );
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog( JSeeAddressee.this, "¿Está seguro de eliminar el destinatario número " + (rowSelected+1) + "?" );

        if (JOptionPane.OK_OPTION == confirm) {
            Destinatario addressee = new Destinatario();
            addressee.setIdentificationCode( addressModel.getValueAt(rowSelected,1).toString() );

            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(addressee.delete())) {
                addressModel.removeRow( rowSelected );

                for(int i = 0; i < addressModel.getRowCount(); i++)
                    addressModel.setValueAt( i+1, i, 0);

                JOptionPane.showMessageDialog( null, "Destinatario eliminado satisfactoriamente." );
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
              rowSelected = addressTable.rowAtPoint(e.getPoint());
              addressTable.setRowSelectionInterval(rowSelected, rowSelected);
              popupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
        }
    }

}
