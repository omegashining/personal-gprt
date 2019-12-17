package aserradero.dialogs.transport;

import aserradero.Application;
import aserradero.entities.Transporte;
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
public class JSeeTransport extends JFrame implements ActionListener {
    
    private int rowSelected;

    private Application application;
    private Container container;
    private JCenterTable transportTable;
    private NotEditTableModel transportModel;
    private PopupMenu popupMenu;
    private MenuItem miModify;
    private MenuItem miDelete;
    private JButton close;

    public JSeeTransport(Application frame) {
        super( "Ver Transportes" );

        this.application = frame;

        initComponents();
        initGUI();
        addListeners();
        setTransportTable();

        this.add( popupMenu );
        this.setIconImage( ImageUtil.get("images/See Transport.png") );
        this.setResizable( false );
        this.setSize( 680, 380 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        rowSelected = -1;

        container = this.getContentPane();

        transportTable = new JCenterTable( new String[] {"No. Transporte","Placas","Medio de Transporte","Marca","Modelo"} );
        
        transportModel = (NotEditTableModel) transportTable.getModel();
        
        popupMenu = new PopupMenu();
        miModify  = new MenuItem( "Modificar" );
        miDelete  = new MenuItem( "Eliminar" );

        close = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        popupMenu.add( miModify );

        if(application.getAccount().havePermissions() || application.getAccount().havePermissions())
            popupMenu.add( miDelete );

        container.add( new JScrollPane(transportTable), BorderLayout.CENTER );
        container.add( close, BorderLayout.SOUTH );
    }

    private void addListeners() {
        transportTable.addMouseListener( new PopupListener() );
        miModify.addActionListener( this );
        miDelete.addActionListener( this );
        close.addActionListener( this );
    }

    private void setTransportTable() {
        TableColumn column;
        column = transportTable.getColumn( "No. Transporte" );
        column.setPreferredWidth( 60 );
        column = transportTable.getColumn( "Placas" );
        column.setPreferredWidth( 100 );
        column = transportTable.getColumn( "Medio de Transporte" );
        column.setPreferredWidth( 200 );
        column = transportTable.getColumn( "Marca" );
        column.setPreferredWidth( 100 );

        new Thread( new FillTransportTable(transportModel) ).start();
    }

    public void reload() {
        Transporte transport = new Transporte();
        transport.setLicensePlate( transportModel.getValueAt(rowSelected,1).toString() );

        Consulta enquery = new Consulta();
        Transporte[] transports = (Transporte[]) enquery.executeQuery( transport.search(), "Transportes" );

        if(transports.length > 0) {
            transportModel.setValueAt(transports[0].getTransport(), rowSelected, 2);
            transportModel.setValueAt(transports[0].getMark(), rowSelected, 3);
            transportModel.setValueAt(transports[0].getModel(), rowSelected, 4);
        }
    }

    private void modify() {
        Transporte transport = new Transporte();
        transport.setLicensePlate( transportModel.getValueAt(rowSelected,1).toString() );

        Consulta enquery = new Consulta();
        Transporte[] transports = (Transporte[]) enquery.executeQuery( transport.search(), "Transportes" );

        if(transports.length > 0) {
            new JModifyTransport( this, transports[0] );
        }
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog( JSeeTransport.this, "¿Está seguro de eliminar el transporte " + (rowSelected+1) + "?" );

        if (JOptionPane.OK_OPTION == confirm) {
            Transporte transport = new Transporte();
            transport.setLicensePlate( transportModel.getValueAt(rowSelected,1).toString() );

            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(transport.delete())) {
                transportModel.removeRow( rowSelected );

                for(int i = 0; i < transportModel.getRowCount(); i++)
                    transportModel.setValueAt( i + 1, i, 0);

                JOptionPane.showMessageDialog( null, "Transporte eliminado satisfactoriamente." );
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
              rowSelected = transportTable.rowAtPoint(e.getPoint());
              transportTable.setRowSelectionInterval(rowSelected, rowSelected);
              popupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
        }
    }

}
