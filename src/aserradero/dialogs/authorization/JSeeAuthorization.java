package aserradero.dialogs.authorization;

import aserradero.Application;
import aserradero.entities.Autorizacion;
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
import mx.gob.org.ipn.cic.mu.classes.MyDate;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JSeeAuthorization extends JFrame implements ActionListener {

    private int rowSelected;

    private Application application;
    private Container container;
    private JCenterTable authorizationTable;
    private NotEditTableModel authorizationModel;
    private PopupMenu popupMenu;
    private MenuItem miModify;
    private MenuItem miDelete;
    private JButton close;

    public JSeeAuthorization(Application frame) {
        super( "Ver Autorizaciones" );

        this.application = frame;

        initComponents();
        initGUI();
        setAuthorizationTable();
        addListeners();

        this.add( popupMenu );
        this.setIconImage( ImageUtil.get("images/See Authorization.png") );
        this.setResizable( false );
        this.setSize( 650, 380 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        rowSelected = -1;

        container = this.getContentPane();

        authorizationTable = new JCenterTable( new String[] {"No. Autorización","No. de Oficio","Fecha Autorización","Fecha Vencimiento"} );

        authorizationModel = (NotEditTableModel) authorizationTable.getModel();
        
        popupMenu = new PopupMenu();
        miModify = new MenuItem( "Modificar" );
        miDelete = new MenuItem( "Eliminar" );

        close = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        popupMenu.add( miModify );

        if(application.getAccount().havePermissions() || application.getAccount().havePermissions())
            popupMenu.add( miDelete );

        container.add( new JScrollPane(authorizationTable), BorderLayout.CENTER );
        container.add( close, BorderLayout.SOUTH );
    }

    private void setAuthorizationTable() {
        TableColumn column;
        column = authorizationTable.getColumn( "No. Autorización" );
        column.setPreferredWidth( 50 );
        column = authorizationTable.getColumn( "No. de Oficio" );
        column.setPreferredWidth( 150 );

        new Thread( new FillAuthorizationTable(authorizationModel) ).start();
    }

    private void addListeners() {
        authorizationTable.addMouseListener( new PopupListener() );
        miModify.addActionListener( this );
        miDelete.addActionListener( this );
        close.addActionListener( this );
    }

    public void reload() {
        Autorizacion authorization = new Autorizacion();
        authorization.setOfficeNumber( authorizationModel.getValueAt(rowSelected,1).toString() );
        MyDate date = new MyDate();
        date.setLatinDate( authorizationModel.getValueAt(rowSelected,2).toString() );
        authorization.setDate( date.toString() );

        Consulta enquery = new Consulta(  );
        Autorizacion[] authorizations = (Autorizacion[]) enquery.executeQuery( authorization.search(), "Autorizaciones" );

        if(authorizations.length > 0) {
            date.setDatabaseDate( authorizations[0].getDate() );
            authorizationModel.setValueAt( date.format(), rowSelected, 2 );

            date.setDatabaseDate( authorizations[0].getExpiration() );
            authorizationModel.setValueAt( date.format(), rowSelected, 3 );
        }
    }

    private void modify() {
        Autorizacion authorization = new Autorizacion();
        authorization.setOfficeNumber( authorizationModel.getValueAt(rowSelected,1).toString() );
        MyDate date = new MyDate();
        date.setLatinDate( authorizationModel.getValueAt(rowSelected,2).toString() );
        authorization.setDate( date.toString() );

        Consulta enquery = new Consulta();
        Autorizacion[] authorizations = (Autorizacion[]) enquery.executeQuery( authorization.search(), "Autorizaciones" );

        if(authorizations.length > 0)
            new JModifyAuthorization( this, authorizations[0] );
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog( JSeeAuthorization.this, "¿Está seguro de eliminar la autorización número " + (rowSelected+1) + "?" );

        if(JOptionPane.OK_OPTION == confirm) {
            Autorizacion authorization = new Autorizacion();
            authorization.setOfficeNumber( authorizationModel.getValueAt(rowSelected,1).toString() );

            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(authorization.delete())) {
                authorizationModel.removeRow( rowSelected );

                for(int i = 0; i < authorizationModel.getRowCount(); i++)
                    authorizationModel.setValueAt( i+1, i, 0 );

                JOptionPane.showMessageDialog( null, "Autorización eliminada satisfactoriamente." );
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
              rowSelected = authorizationTable.rowAtPoint(e.getPoint());
              authorizationTable.setRowSelectionInterval(rowSelected, rowSelected);
              popupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
        }
    }

}
