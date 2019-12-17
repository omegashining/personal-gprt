package aserradero.dialogs.account;

import aserradero.Application;
import aserradero.entities.Cuenta;
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
public class JDeleteAccounts extends JFrame implements ActionListener {

    private int rowSelected;

    private Container container;
    private JCenterTable accountTable;
    private NotEditTableModel accountModel;
    private PopupMenu popupMenu;
    private MenuItem miDelete;
    private JButton close;

    public JDeleteAccounts(Application frame) {
        super("Cuentas Registradas");

        initComponents();
        initGUI();
        setAccountTable();
        addListeners();

        this.add( popupMenu );
        this.setIconImage( ImageUtil.get("images/Delete Account.png") );
        this.setResizable( false );
        this.setSize( 500, 300 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        rowSelected = 0;

        container = this.getContentPane();

        accountTable = new JCenterTable( new String[] {"No. Cuenta","Usuario","Permisos"} );
        
        accountModel = (NotEditTableModel) accountTable.getModel();

        popupMenu = new PopupMenu();
        miDelete  = new MenuItem( "Eliminar" );

        close = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        popupMenu.add( miDelete );
        container.add( new JScrollPane(accountTable), BorderLayout.CENTER );
        container.add( close, BorderLayout.SOUTH );
    }

    private void setAccountTable() {
        TableColumn column = accountTable.getColumn( "No. Cuenta" );
        column.setPreferredWidth( 25 );

        new Thread( new FillAccountTable(accountModel)).start();
    }

    private void addListeners() {
        accountTable.addMouseListener( new PopupListener() );
        miDelete.addActionListener( this );
        close.addActionListener( this );
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog( JDeleteAccounts.this, "¿Está seguro de eliminar la cuenta número " + (rowSelected+1) + "?" );

        if(JOptionPane.OK_OPTION == confirm) {
            Cuenta account = new Cuenta();
            account.setUsername( accountModel.getValueAt(rowSelected,1).toString() );

            Consulta enquery = new Consulta();

            if(enquery.executeUpdate(account.delete())) {
                accountModel.removeRow( rowSelected );

                for(int i = 0; i < accountModel.getRowCount(); i++)
                    accountModel.setValueAt( i+1, i, 0 );

                JOptionPane.showMessageDialog( null, "Cuenta eliminada satisfactoriamente." );
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Eliminar")) {
            delete();
        } if(s.equals("Cerrar")) {
            this.dispose();
        }
    }

    class PopupListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
          showPopup( e );
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {
          showPopup( e );
        }

        public void mouseReleased(MouseEvent e) {
          showPopup( e );
        }

        private void showPopup(MouseEvent e) {
          if(e.isPopupTrigger()) {
              rowSelected = accountTable.rowAtPoint(e.getPoint());
              accountTable.setRowSelectionInterval(rowSelected, rowSelected);
              popupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
        }
    }

}
