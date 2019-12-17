package aserradero.dialogs.reembarque;

import aserradero.Application;
import aserradero.reports.ReembarqueReport;
import aserradero.entities.Destinatario;
import aserradero.entities.Autorizacion;
import aserradero.entities.Reembarque;
import aserradero.entities.Resolucion;
import aserradero.database.Consulta;
import aserradero.entities.Informacion;
import aserradero.entities.ProductoAmparado;
import aserradero.entities.Saldo;
import aserradero.entities.Titular;
import aserradero.entities.Transporte;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class JSeeReembarque extends JFrame implements ActionListener {

    private int rowSelected;

    private Application application;
    private Container container;
    private JCenterTable reembarquesTable;
    private NotEditTableModel reembarquesModel;
    private JScrollPane reembarqueScroll;
    private PopupMenu popupMenu;
    private MenuItem miDelete;
    private JButton see;
    private JButton close;

    public JSeeReembarque(Application frame) {
        super( "Reembarques" );

        this.application = frame;

        initComponents();
        initGUI();
        setReembarqueTable();
        addListeners();

        this.add( popupMenu );
        this.setIconImage( ImageUtil.get("images/Reembarque.png") );
        this.setResizable( false );
        this.setSize( 700, 400 );
        this.setLocationRelativeTo( frame );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setVisible( true );
    }

    private void initComponents() {
        rowSelected = -1;

        container = this.getContentPane();

        reembarquesTable = new JCenterTable( new String[] {"No. Reembarque","Folio Progresivo","Folio Autorizado"} );
        reembarquesModel = (NotEditTableModel) reembarquesTable.getModel();
        reembarqueScroll = new JScrollPane( reembarquesTable );

        popupMenu = new PopupMenu();
        miDelete  = new MenuItem( "Eliminar Reembarque" );

        see   = new JButton( "Visualizar", ImageUtil.getIcon("images/PDF.png") );
        close = new JButton( "Cerrar", ImageUtil.getIcon("images/Close.png") );
    }

    private void initGUI() {
        container.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel[] columns = new JLabel[ 45 ];
        for(int i = 0; i < columns.length; i++) {
            gbc.gridx = i;
            columns[ i ] = new JLabel( "     " );
            container.add( columns[ i ], gbc );
        }

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 45;
        container.add( new JLabel( "Reembarques" ), gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        container.add( new JLabel( " " ), gbc );

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 43;
        container.add( reembarqueScroll, gbc );

        gbc.gridx = 0;
        gbc.gridy = 4;
        container.add( new JLabel( " " ), gbc );

        gbc.gridx = 12;
        gbc.gridy = 5;
        gbc.gridwidth = 7;
        container.add( see, gbc );
        gbc.gridx = 24;
        gbc.gridwidth = 7;
        container.add( close, gbc );

        gbc.gridx = 0;
        gbc.gridy = 6;
        container.add( new JLabel( " " ), gbc );

        if(application.getAccount().havePermissions() || application.getAccount().havePermissions())
            popupMenu.add( miDelete );
    }

    private void addListeners() {
        reembarquesTable.addMouseListener( new PopupListener() );
        miDelete.addActionListener( this );
        see.addActionListener( this );
        close.addActionListener( this );

        reembarquesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rowSelected = reembarquesTable.rowAtPoint(e.getPoint());
            }
        });
    }

    private void setReembarqueTable() {
        TableColumn column;
        column = reembarquesTable.getColumn( "No. Reembarque" );
        column.setPreferredWidth( 60 );

        reembarqueScroll.setPreferredSize( new Dimension(600,240) );

        new Thread( new FillReembarqueTable( reembarquesModel ) ).start();
    }

    private void renameReembarqueNumbers() {
        for( int i = 0; i < reembarquesModel.getRowCount(); i++ )
            reembarquesModel.setValueAt( i + 1, i, 0);
    }

    private void delete() {
        int confirm = JOptionPane.showConfirmDialog( this, "¿Está seguro de eliminar el reembarque número "+(rowSelected+1)+"?");

        if ( JOptionPane.OK_OPTION == confirm ) {
            Reembarque reembarque = new Reembarque();
            reembarque.setProgressiveFolio( reembarquesModel.getValueAt(rowSelected,1).toString() );
            reembarque.setAuthorizedFolio( reembarquesModel.getValueAt(rowSelected,2).toString() );

            Informacion i = new Informacion();
            i.setReembarqueProgressiveFolio(reembarque.getProgressiveFolio());
            Resolucion r = new Resolucion();
            r.setReembarqueProgressiveFolio(reembarque.getProgressiveFolio());
            ProductoAmparado pa = new ProductoAmparado();
            pa.setReembarqueProgressiveFolio(reembarque.getProgressiveFolio());
            Saldo l = new Saldo();
            l.setReembarqueProgressiveFolio(reembarque.getProgressiveFolio());

            Consulta enquery = new Consulta();
            enquery.executeUpdate(i.delete());
            enquery.executeUpdate(r.delete());
            enquery.executeUpdate(pa.delete());
            enquery.executeUpdate(l.delete());

            if(enquery.executeUpdate(reembarque.delete())) {
                reembarquesModel.removeRow( rowSelected );
                renameReembarqueNumbers();

                JOptionPane.showMessageDialog( null, "Reembarque eliminado satisfactoriamente." );
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

                Reembarque reembarque = new Reembarque();
                reembarque.setProgressiveFolio( reembarquesModel.getValueAt(rowSelected,1).toString() );
                reembarque.setAuthorizedFolio( reembarquesModel.getValueAt(rowSelected,2).toString() );

                String reportName = reembarque.getProgressiveFolio();

                String directory = System.getProperty("user.home") + "\\Reports\\Reembarques\\" + reportName + ".pdf";

                File pdfFile = new File( directory );

                if( pdfFile.exists() ) {
                    String command = "cmd /c \"" + directory + "\"";
                    Process p = Runtime.getRuntime().exec( command );
                } else {
                    Reembarque[] reembarques = (Reembarque[]) enquery.executeQuery( reembarque.search(), "Reembarques" );

                    Resolucion resolution = new Resolucion();
                    resolution.setReembarqueProgressiveFolio( reembarque.getProgressiveFolio() );
                    Resolucion[] resolutions = (Resolucion[]) enquery.executeQuery( resolution.search(), "Resolucion" );

                    ProductoAmparado product = new ProductoAmparado();
                    product.setReembarqueProgressiveFolio( reembarque.getProgressiveFolio() );
                    ProductoAmparado[] products = (ProductoAmparado[]) enquery.executeQuery( product.search(), "Producto Amparado" );

                    Saldo balance = new Saldo();
                    balance.setReembarqueProgressiveFolio( reembarque.getProgressiveFolio() );
                    Saldo[] balances = (Saldo[]) enquery.executeQuery( balance.search(), "Saldos" );

                    Informacion information = new Informacion();
                    information.setReembarqueProgressiveFolio( reembarque.getProgressiveFolio() );
                    Informacion[] informations = (Informacion[]) enquery.executeQuery( information.search(), "Informacion" );

                    Autorizacion authorization = new Autorizacion();
                    authorization.setOfficeNumber( informations[0].getAuthorizationOfficeNumber() );
                    Autorizacion[] authorizations = (Autorizacion[]) enquery.executeQuery( authorization.search(), "Autorizaciones" );

                    Transporte transport = new Transporte();
                    transport.setLicensePlate( informations[0].getTransportLicensePlate() );
                    Transporte[] transports = (Transporte[]) enquery.executeQuery( transport.search(), "Transportes" );

                    Destinatario addressee = new Destinatario();
                    addressee.setIdentificationCode( informations[0].getAddresseIdentificationCode() );
                    Destinatario[] addressees = (Destinatario[]) enquery.executeQuery( addressee.search(), "Destinatarios" );

                    Titular titular = new Titular();
                    Titular[] titulars = (Titular[]) enquery.executeQuery( titular.search(), "Titular" );

                    ReembarqueReport report = new ReembarqueReport(reembarques[0],resolutions[0],products[0],balances[0],authorizations[0],transports[0],addressees[0],titulars[0]);
                    report.create();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Eliminar Reembarque")) {
            delete();
        } if(s.equals("Visualizar")) {
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
              rowSelected = reembarquesTable.rowAtPoint(e.getPoint());
              reembarquesTable.setRowSelectionInterval(rowSelected, rowSelected);
              popupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
        }
    }

}
