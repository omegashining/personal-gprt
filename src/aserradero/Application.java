package aserradero;

import aserradero.dialogs.about.JAbout;
import aserradero.dialogs.account.JCreateAccount;
import aserradero.dialogs.account.JDeleteAccounts;
import aserradero.dialogs.addressee.JAddAddressee;
import aserradero.dialogs.addressee.JSeeAddressee;
import aserradero.dialogs.authorization.JAddAuthorization;
import aserradero.dialogs.authorization.JSeeAuthorization;
import aserradero.dialogs.client.JAddClient;
import aserradero.dialogs.client.JSeeClient;
import aserradero.dialogs.invoice.JInvoiceForm;
import aserradero.dialogs.invoice.JSeeInvoice;
import aserradero.dialogs.login.JLogin;
import aserradero.dialogs.product.JAddProduct;
import aserradero.dialogs.product.JSeeProduct;
import aserradero.dialogs.reembarque.JReembarqueWizard;
import aserradero.dialogs.reembarque.JSeeReembarque;
import aserradero.dialogs.titular.JTitular;
import aserradero.dialogs.transport.JAddTransport;
import aserradero.dialogs.transport.JSeeTransport;
import aserradero.entities.Cuenta;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import mx.gob.org.ipn.cic.du.interfaces.InterfaceWindow;
import mx.gob.org.ipn.cic.du.panels.JImagePanel;
import mx.gob.org.ipn.cic.du.util.SubstanceUtil;
import mx.gob.org.ipn.cic.mu.utl.ImageUtil;


/**
 *
 * @author Gabriel
 */
public class Application extends JFrame implements InterfaceWindow {
    
    private Cuenta account;

    private Container container;
    private JMenuBar menubar;
    private JMenu mFile;
    private JMenu mSawmill;
    private JMenu mAuthorization;
    private JMenu mDocuments;
    private JMenu mProduct;
    private JMenu mTransport;
    private JMenu mClient;
    private JMenu mAddressee;
    private JMenu mTools;
    private JMenu mSkins;
    private JMenu mHelp;
    private JMenuItem miInitSession;
    private JMenuItem miCloseSession;
    private JMenuItem miAddAccount;
    private JMenuItem miDeleteAccounts;
    private JMenuItem miQuit;
    private JMenuItem miTitular;
    private JMenuItem miAddAuthorization;
    private JMenuItem miSeeAuthorizations;
    private JMenuItem miSeeInvoices;
    private JMenuItem miSeeReembarques;
    private JMenuItem miAddProduct;
    private JMenuItem miSeeProducts;
    private JMenuItem miAddTransport;
    private JMenuItem miSeeTransports;
    private JMenuItem miAddClient;
    private JMenuItem miSeeClients;
    private JMenuItem miAddAddressee;
    private JMenuItem miSeeAddressee;
    private JMenuItem miAbout;
    private JTabbedPane tabbedPane;
    private JImagePanel welcomePanel;
    private JInvoiceForm invoiceForm    ;
    private JReembarqueWizard reembarqueWizard;
    private JLabel status;

    public Application() {
        super("Grupo Productivo Rancho Texas");
    }

    public void initComponents() {
        container = this.getContentPane();

        menubar = new JMenuBar();

        mFile          = new JMenu( "Archivo" );
        mSawmill       = new JMenu( "Aserradero" );
        mAuthorization = new JMenu( "Autorizacion" );
        mDocuments     = new JMenu( "Documentos" );
        mProduct       = new JMenu( "Producto" );
        mTransport     = new JMenu( "Transporte" );
        mClient        = new JMenu( "Cliente" );
        mAddressee     = new JMenu( "Destinatario" );
        mTools         = new JMenu( "Herramientas" );
        mSkins         = SubstanceUtil.skinsMenu();
        mHelp          = new JMenu( "Ayuda" );

        miInitSession       = new JMenuItem( "Iniciar Sesión" );
        miCloseSession      = new JMenuItem( "Cerrar Sesión" );
        miAddAccount        = new JMenuItem( "Crear Cuenta" );
        miDeleteAccounts    = new JMenuItem( "Eliminar Cuenta" );
        miQuit              = new JMenuItem( "Salir", ImageUtil.getIcon("images/Exit.png") );
        miTitular           = new JMenuItem( "Datos del Titular", ImageUtil.getIcon("images/Titular.png") );
        miAddAuthorization  = new JMenuItem( "Agregar Autorización", ImageUtil.getIcon("images/Add Authorization.png") );
        miSeeAuthorizations = new JMenuItem( "Ver Autorizaciones", ImageUtil.getIcon("images/See Authorization.png") );
        miSeeInvoices       = new JMenuItem( "Ver Facturas", ImageUtil.getIcon("images/Invoice.png") );
        miSeeReembarques    = new JMenuItem( "Ver Reembarques", ImageUtil.getIcon("images/Reembarque.png") );
        miAddProduct        = new JMenuItem( "Agregar Producto", ImageUtil.getIcon("images/Add Product.png") );
        miSeeProducts       = new JMenuItem( "Ver Productos", ImageUtil.getIcon("images/See Product.png") );
        miAddTransport      = new JMenuItem( "Agregar Transporte", ImageUtil.getIcon("images/Add Transport.png") );
        miSeeTransports     = new JMenuItem( "Ver Transportes", ImageUtil.getIcon("images/See Transport.png") );
        miAddClient         = new JMenuItem( "Agregar Cliente", ImageUtil.getIcon("images/Add Client.png") );
        miSeeClients        = new JMenuItem( "Ver Clientes", ImageUtil.getIcon("images/See Client.png") );
        miAddAddressee      = new JMenuItem( "Agregar Destinatario", ImageUtil.getIcon("images/Add Addressee.png") );
        miSeeAddressee      = new JMenuItem( "Ver Destinatarios", ImageUtil.getIcon("images/See Addressee.png") );
        miAbout             = new JMenuItem( "Acerca de...", ImageUtil.getIcon("images/About.png") );

        tabbedPane       = new JTabbedPane();
        welcomePanel     = new JImagePanel( ImageUtil.getIcon("images/WelcomePanel.png") );
        invoiceForm      = new JInvoiceForm( this );
        reembarqueWizard = new JReembarqueWizard( this );

        status = new JLabel( " Usuario: ninguno" );
    }
    
    public void initGUI() {
        mFile.setMnemonic( KeyEvent.VK_A );
        mSawmill.setMnemonic( KeyEvent.VK_R );
        mDocuments.setMnemonic( KeyEvent.VK_O );
        mProduct.setMnemonic( KeyEvent.VK_P );
        mTransport.setMnemonic( KeyEvent.VK_T );
        mClient.setMnemonic( KeyEvent.VK_C );
        mAddressee.setMnemonic( KeyEvent.VK_D );
        mTools.setMnemonic( KeyEvent.VK_H );
        mHelp.setMnemonic( KeyEvent.VK_Y );
        
        miQuit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK) );
        miTitular.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK) );
        miSeeInvoices.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK) );
        miSeeReembarques.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK) );
        miAddProduct.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK) );
        miAddTransport.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK) );
        miAddAddressee.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK) );
        miAddClient.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK) );
        
        menubar.add( mFile );
        menubar.add( mSawmill );
        menubar.add( mDocuments );
        menubar.add( mProduct );
        menubar.add( mTransport );
        menubar.add( mClient );
        menubar.add( mAddressee );
        menubar.add( mTools );
        menubar.add( mHelp );

        mFile.add( miInitSession );
        mFile.add( miCloseSession );
        mFile.addSeparator();
        mFile.add( miAddAccount );
        mFile.add( miDeleteAccounts );
        mFile.addSeparator();
        mFile.add( miQuit );

        mSawmill.add( miTitular );
        mSawmill.add( mAuthorization );

        mAuthorization.add( miAddAuthorization );
        mAuthorization.add( miSeeAuthorizations );

        mDocuments.add( miSeeInvoices );
        mDocuments.add( miSeeReembarques );

        mProduct.add( miAddProduct );
        mProduct.add( miSeeProducts );

        mTransport.add( miAddTransport );
        mTransport.add( miSeeTransports );

        mClient.add( miAddClient );
        mClient.add( miSeeClients );

        mAddressee.add( miAddAddressee );
        mAddressee.add( miSeeAddressee );

        mTools.add( mSkins );

        mHelp.add( miAbout );
        
        tabbedPane.addTab( "Bienvenida", ImageUtil.getIcon("images/Welcome.png"), welcomePanel );
        tabbedPane.addTab( "Factura", ImageUtil.getIcon("images/Invoice.png"), invoiceForm.getPanel() );
        tabbedPane.addTab( "Reembarque", ImageUtil.getIcon("images/Reembarque.png"), reembarqueWizard.getPanel() );

        container.add( tabbedPane, BorderLayout.CENTER );
        container.add( status, BorderLayout.SOUTH );
        
        setDisable();
    }

    public void initListeners() {
        miInitSession.addActionListener( this );
        miCloseSession.addActionListener(this);
        miAddAccount.addActionListener( this );
        miDeleteAccounts.addActionListener( this );
        miQuit.addActionListener( this );
        miTitular.addActionListener( this );
        miAddAuthorization.addActionListener( this );
        miSeeAuthorizations.addActionListener( this );
        miSeeInvoices.addActionListener( this );
        miSeeReembarques.addActionListener( this );
        miAddProduct.addActionListener( this );
        miSeeProducts.addActionListener( this );
        miAddTransport.addActionListener( this );
        miSeeTransports.addActionListener( this );
        miAddClient.addActionListener( this );
        miSeeClients.addActionListener( this );
        miAddAddressee.addActionListener( this );
        miSeeAddressee.addActionListener( this );
        miAbout.addActionListener( this );
    }

    public void initWindow() {
        SubstanceUtil.loadSkin();
        
        this.setJMenuBar( menubar );
        this.setIconImage( ImageUtil.get("images/Sylvester.png") );
        this.setMinimumSize( new Dimension(800,600) );
        this.setLocationRelativeTo( null );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    public void setEneable() {
        miInitSession.setEnabled( false );
        miCloseSession.setEnabled( true );
        miTitular.setEnabled( true );
        miAddAuthorization.setEnabled( true );
        miSeeAuthorizations.setEnabled( true );
        miSeeInvoices.setEnabled( true );
        miSeeReembarques.setEnabled( true );
        miAddProduct.setEnabled( true );
        miSeeProducts.setEnabled( true );
        miAddTransport.setEnabled( true );
        miSeeTransports.setEnabled( true );
        miAddClient.setEnabled( true );
        miSeeClients.setEnabled( true );
        miAddAddressee.setEnabled( true );
        miSeeAddressee.setEnabled( true );
        miAbout.setEnabled( true );

        if(account.havePermissions()) {
            miAddAccount.setEnabled( true );
            miDeleteAccounts.setEnabled( true );
        }

        tabbedPane.setEnabledAt( 1, true );
        tabbedPane.setEnabledAt( 2, true );

        status.setText( " Usuario: " + account.getUsername() );
    }

    public void setDisable() {
        miInitSession.setEnabled( true );
        miCloseSession.setEnabled( false );
        miAddAccount.setEnabled( false );
        miTitular.setEnabled( false );
        miAddAuthorization.setEnabled( false );
        miSeeAuthorizations.setEnabled( false );
        miSeeInvoices.setEnabled( false );
        miSeeReembarques.setEnabled( false );
        miAddProduct.setEnabled( false );
        miSeeProducts.setEnabled( false );
        miAddTransport.setEnabled( false );
        miSeeTransports.setEnabled( false );
        miAddClient.setEnabled( false );
        miSeeClients.setEnabled( false );
        miAddAddressee.setEnabled( false );
        miSeeAddressee.setEnabled( false );
        miAbout.setEnabled( false );
        miDeleteAccounts.setEnabled( false );

        tabbedPane.setEnabledAt( 1, false );
        tabbedPane.setEnabledAt( 2, false );
        tabbedPane.setSelectedIndex( 0 );
        
        status.setText( " Usuario: ninguno" );

        account = null;
    }

    public Cuenta getAccount() {
        return account;
    }

    public void setAccount(Cuenta account) {
        this.account = account;
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Cerrar Sesión")) {
            setDisable();
        } else if(s.equals("Iniciar Sesión")) {
            new JLogin( this );
        } else if(s.equals("Ver Facturas")) {
            new JSeeInvoice( this );
        } else if(s.equals("Ver Reembarques")) {
            new JSeeReembarque( this );
        } else if(s.equals("Crear Cuenta")) {
            new JCreateAccount( this );
        } else if(s.equals("Eliminar Cuenta")) {
            new JDeleteAccounts( this );
        } else if(s.equals("Salir")) {
            System.exit( 0 );
        } else if(s.equals("Datos del Titular")) {
            new JTitular( this );
        } else if(s.equals("Agregar Autorización")) {
            new JAddAuthorization( this );
        } else if(s.equals("Ver Autorizaciones")) {
            new JSeeAuthorization( this );
        } else if(s.equals("Agregar Producto")) {
            new JAddProduct( this );
        } else if(s.equals("Ver Productos")) {
            new JSeeProduct( this );
        } else if(s.equals("Agregar Transporte")) {
            new JAddTransport( this );
        } else if(s.equals("Ver Transportes")) {
            new JSeeTransport( this );
        } else if(s.equals("Agregar Cliente")) {
            new JAddClient( this);
        } else if(s.equals("Ver Clientes")) {
            new JSeeClient( this );
        } else if(s.equals("Agregar Destinatario")) {
            new JAddAddressee( this );
        } else if(s.equals("Ver Destinatarios")) {
            new JSeeAddressee( this );
        } else if(s.equals("Acerca de...") ) {
            new JAbout( this );
        }
    }

}
