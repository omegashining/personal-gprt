package aserradero.dialogs.reembarque;

import aserradero.database.Consulta;
import aserradero.dialogs.addressee.JModifyAddressee;
import aserradero.dialogs.authorization.JModifyAuthorization;
import aserradero.dialogs.transport.JModifyTransport;
import aserradero.entities.Autorizacion;
import aserradero.entities.Destinatario;
import aserradero.entities.Informacion;
import aserradero.entities.ProductoAmparado;
import aserradero.entities.Reembarque;
import aserradero.entities.Resolucion;
import aserradero.entities.Saldo;
import aserradero.entities.Transporte;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mx.gob.org.ipn.cic.du.listeners.DoubleListener;
import mx.gob.org.ipn.cic.du.listeners.IntegerListener;
import mx.gob.org.ipn.cic.mu.classes.MyDate;

/**
 *
 * @author Gabriel
 */
public class JReembarqueForm implements ActionListener {

    private int authorizationSelected = 0;
    private int addresseeSelected = 0;
    private int transportSelected = 0;

    private Autorizacion[] authorizations;
    private Destinatario[] addressees;
    private Transporte[] transports;

    private JFrame frame;
    private JTextField progressiveFolio;
    private JTextField authorizedFolio;
    private JTextField rfn;
    private JTextField resolutionType;
    private JTextField reolutionNumber;
    private JTextField validity;
    private JTextField volumeAnuality;
    private JTextField cantityAnuality;
    private JTextField paymentAnuality;
    private JTextField product;
    private JTextField descriptionProduct;
    private JTextField cantityProduct;
    private JTextField volumeProduct;
    private JTextField measureProduct;
    private JTextField letter;
    private JTextField observation;
    private JTextField previousBalance;
    private JTextField cantityBalance;
    private JTextField nextBalance;
    private JComboBox cbEDay;
    private JComboBox cbEMonth;
    private JComboBox cbEYear;
    private JComboBox cbVDay;
    private JComboBox cbVMonth;
    private JComboBox cbVYear;
    private JComboBox cbRDay;
    private JComboBox cbRMonth;
    private JComboBox cbRYear;
    private JComboBox cbAuthorization;
    private JComboBox cbAddressee;
    private JComboBox cbTransport;
    private JButton update1;
    private JButton update2;
    private JButton update3;
    private JButton seeAuthorization;
    private JButton seeAddressee;
    private JButton seeTransport;

    public JReembarqueForm(JFrame frame) {
        this.frame = frame;

        initComponents();
        setComoboBoxes();
        addListeners();
    }
    
    private void initComponents() {
        progressiveFolio   = new JTextField();
        authorizedFolio    = new JTextField();
        rfn                = new JTextField();
        resolutionType     = new JTextField();
        reolutionNumber    = new JTextField();
        validity           = new JTextField();
        volumeAnuality     = new JTextField();
        cantityAnuality    = new JTextField();
        paymentAnuality    = new JTextField();
        product            = new JTextField();
        descriptionProduct = new JTextField();
        cantityProduct     = new JTextField();
        volumeProduct      = new JTextField();
        measureProduct     = new JTextField();
        letter             = new JTextField();
        observation        = new JTextField();
        previousBalance    = new JTextField();
        cantityBalance     = new JTextField();
        nextBalance        = new JTextField();

        cbEDay          = new JComboBox();
        cbEMonth        = new JComboBox();
        cbEYear         = new JComboBox();
        cbVDay          = new JComboBox();
        cbVMonth        = new JComboBox();
        cbVYear         = new JComboBox();
        cbAuthorization = new JComboBox();
        cbAddressee     = new JComboBox();
        cbTransport     = new JComboBox();
        cbRDay          = new JComboBox();
        cbRMonth        = new JComboBox();
        cbRYear         = new JComboBox();

        update1          = new JButton("Actualizar");
        update2          = new JButton("Actualizar");
        update3          = new JButton("Actualizar");
        seeAuthorization = new JButton("Ver Autorización");
        seeAddressee     = new JButton("Ver Destinatario");
        seeTransport     = new JButton("Ver Transporte");

        update1.setActionCommand("u1");
        update2.setActionCommand("u2");
        update3.setActionCommand("u3");
    }

    private void setComoboBoxes() {
        for( int i = 1; i <= 31; i++ ) {
            cbEDay.addItem( i );
            cbVDay.addItem( i );
            cbRDay.addItem( i );
        }

        for( int i = 1; i <= 12; i++ ) {
            cbEMonth.addItem( i );
            cbVMonth.addItem( i );
            cbRMonth.addItem( i );
        }

        for( int i = 0; i <= 30; i++ ) {
            cbEYear.addItem( 2003 + i );
            cbVYear.addItem( 2003 + i );
            cbRYear.addItem( 2003 + i );
        }
        
        MyDate d = new MyDate();
        cbEDay.setSelectedItem( d.getDay() );
        cbEMonth.setSelectedItem( d.getMonth() );
        cbEYear.setSelectedItem( d.getYear() );
        cbVDay.setSelectedItem( d.getDay() );
        cbVMonth.setSelectedItem( d.getMonth() );
        cbVYear.setSelectedItem( d.getYear() );

        Consulta enquery = new Consulta();

        Autorizacion authorization = new Autorizacion();
        authorizations = (Autorizacion[]) enquery.executeQuery(authorization.getAllAuthorizations(), "Autorizaciones");

        for(int i = 0; i < authorizations.length; i++)
            cbAuthorization.addItem(authorizations[i].getOfficeNumber());

        if(cbAuthorization.getItemCount() > 0)
            cbAuthorization.setSelectedIndex(authorizationSelected);

        Destinatario addressee = new Destinatario();
        addressees = (Destinatario[]) enquery.executeQuery(addressee.getAllAddressees(), "Destinatarios");

        for(int i = 0; i < addressees.length; i++)
            cbAddressee.addItem(addressees[i].getIdentificationCode());

        if(cbAddressee.getItemCount() > 0)
            cbAddressee.setSelectedIndex(addresseeSelected);

        Transporte transport = new Transporte();
        transports = (Transporte[]) enquery.executeQuery(transport.getAllTransports(), "Transportes");

        for(int i = 0; i < transports.length; i++)
            cbTransport.addItem(transports[i].getLicensePlate());

        if(cbTransport.getItemCount() > 0)
            cbTransport.setSelectedIndex(transportSelected);
    }

    private void update(int n) {
        Consulta enquery = new Consulta();

        if(n == 1) {
            Autorizacion authorization = new Autorizacion();
            authorizations = (Autorizacion[]) enquery.executeQuery(authorization.getAllAuthorizations(), "Autorizaciones");

            cbAuthorization.removeAllItems();
            for(int i = 0; i < authorizations.length; i++)
                cbAuthorization.addItem(authorizations[i].getOfficeNumber());

            if(cbAuthorization.getItemCount() > 0)
                cbAuthorization.setSelectedIndex(authorizationSelected);
        } else if(n == 2) {
            Destinatario addressee = new Destinatario();
            addressees = (Destinatario[]) enquery.executeQuery(addressee.getAllAddressees(), "Destinatarios");

            cbAddressee.removeAllItems();
            for(int i = 0; i < addressees.length; i++)
                cbAddressee.addItem(addressees[i].getIdentificationCode());

            if(cbAddressee.getItemCount() > 0)
                cbAddressee.setSelectedIndex(addresseeSelected);
        } else if(n == 3) {
            Transporte transport = new Transporte();
            transports = (Transporte[]) enquery.executeQuery(transport.getAllTransports(), "Transportes");

            cbTransport.removeAllItems();
            for(int i = 0; i < transports.length; i++)
                cbTransport.addItem(transports[i].getLicensePlate());

            if(cbTransport.getItemCount() > 0)
                cbTransport.setSelectedIndex(transportSelected);
        }
    }

    private void addListeners() {
        progressiveFolio.addKeyListener( new IntegerListener() );
        authorizedFolio.addKeyListener( new IntegerListener() );
        cantityAnuality.addKeyListener( new IntegerListener() );
        paymentAnuality.addKeyListener( new IntegerListener() );
        previousBalance.addKeyListener( new DoubleListener() );
        cantityBalance.addKeyListener( new DoubleListener() );
        nextBalance.addKeyListener( new DoubleListener() );
        update1.addActionListener( this );
        update2.addActionListener( this );
        update3.addActionListener( this );
        seeAuthorization.addActionListener( this );
        seeAddressee.addActionListener( this );
        seeTransport.addActionListener( this );
    }

    public JPanel getCardOne() {
        JPanel panel = new JPanel( new GridBagLayout() );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;

        JLabel[] columns = new JLabel[ 40 ];
        for( int i = 0; i < columns.length; i++ ) {
            gbc.gridx = i;
            columns[ i ] = new JLabel( "     " );
            panel.add( columns[ i ], gbc );
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = columns.length;
        JLabel reembarque = new JLabel("DATOS DEL REEMBARQUE FORESTAL");
        reembarque.setForeground(Color.RED);
        panel.add( reembarque, gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 9;
        panel.add( new JLabel("Folio Progresivo No."), gbc );
        gbc.gridx = 11;
        gbc.gridwidth = 9;
        progressiveFolio.setBackground( Color.CYAN );
        progressiveFolio.setHorizontalAlignment( JTextField.CENTER );
        panel.add( progressiveFolio, gbc );
        gbc.gridx = 21;
        gbc.gridwidth = 9;
        panel.add( new JLabel("Folio Autorizado No."), gbc );
        gbc.gridx = 30;
        gbc.gridwidth = 9;
        panel.add( authorizedFolio, gbc );
        authorizedFolio.setBackground( Color.CYAN );
        authorizedFolio.setHorizontalAlignment( JTextField.CENTER );

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.gridwidth = 8;
        panel.add( new JLabel("Fecha de expedición:"), gbc );
        gbc.gridx = 14;
        gbc.gridwidth = 3;
        panel.add( new JLabel("Día:"), gbc );
        gbc.gridx = 17;
        gbc.gridwidth = 4;
        panel.add( cbEDay, gbc );
        gbc.gridx = 22;
        gbc.gridwidth = 3;
        panel.add( new JLabel("Mes:"), gbc );
        gbc.gridx = 25;
        gbc.gridwidth = 4;
        panel.add( cbEMonth, gbc );
        gbc.gridx = 30;
        gbc.gridwidth = 3;
        panel.add( new JLabel("Año:"), gbc );
        gbc.gridx = 33;
        gbc.gridwidth = 4;
        panel.add( cbEYear, gbc );

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 4;
        gbc.gridy = 8;
        gbc.gridwidth = 8;
        panel.add( new JLabel("Fecha de vencimiento:"), gbc );
        gbc.gridx = 14;
        gbc.gridwidth = 3;
        panel.add( new JLabel("Día:"), gbc );
        gbc.gridx = 17;
        gbc.gridwidth = 4;
        panel.add( cbVDay, gbc );
        gbc.gridx = 22;
        gbc.gridwidth = 3;
        panel.add( new JLabel("Mes:"), gbc );
        gbc.gridx = 25;
        gbc.gridwidth = 4;
        panel.add( cbVMonth, gbc );
        gbc.gridx = 30;
        gbc.gridwidth = 3;
        panel.add( new JLabel("Año:"), gbc );
        gbc.gridx = 33;
        gbc.gridwidth = 4;
        panel.add( cbVYear, gbc );

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 6;
        panel.add( new JLabel("Autorización:"), gbc );
        gbc.gridx = 7;
        gbc.gridwidth = 14;
        panel.add( cbAuthorization, gbc );
        gbc.gridx = 22;
        gbc.gridwidth = 9;
        panel.add( seeAuthorization, gbc );
        gbc.gridx = 32;
        gbc.gridwidth = 8;
        panel.add( update1, gbc );

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 13;
        gbc.gridwidth = columns.length;
        JLabel remitente = new JLabel("INOFORMACIÓN DEL REMITENTE");
        remitente.setForeground(Color.BLUE);
        panel.add( remitente, gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 3;
        panel.add( new JLabel("R.F.N."), gbc );
        gbc.gridx = 3;
        gbc.gridwidth = 11;
        panel.add( rfn, gbc );
        gbc.gridx = 15;
        gbc.gridwidth = 3;
        panel.add( new JLabel("Fecha:"), gbc );
        gbc.gridx = 18;
        gbc.gridwidth = 2;
        panel.add( new JLabel("Día:"), gbc );
        gbc.gridx = 20;
        gbc.gridwidth = 4;
        panel.add( cbRDay, gbc );
        gbc.gridx = 25;
        gbc.gridwidth = 3;
        panel.add( new JLabel("Mes:"), gbc );
        gbc.gridx = 28;
        gbc.gridwidth = 4;
        panel.add( cbRMonth, gbc );
        gbc.gridx = 33;
        gbc.gridwidth = 3;
        panel.add( new JLabel("Año:"), gbc );
        gbc.gridx = 36;
        gbc.gridwidth = 4;
        panel.add( cbRYear, gbc );

        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 17;
        gbc.gridwidth = 8;
        panel.add( new JLabel("Tipo de Resolución:"), gbc );
        gbc.gridx = 8;
        gbc.gridwidth = 14;
        panel.add( resolutionType, gbc );
        gbc.gridx = 24;
        gbc.gridwidth = 2;
        panel.add( new JLabel("No."), gbc );
        gbc.gridx = 26;
        gbc.gridwidth = 14;
        panel.add( reolutionNumber, gbc );

        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 19;
        gbc.gridwidth = 5;
        panel.add( new JLabel("Vigencia:"), gbc );
        gbc.gridx = 5;
        gbc.gridwidth = 10;
        panel.add( validity, gbc );
        gbc.gridx = 16;
        gbc.gridwidth = 9;
        panel.add( new JLabel("Volumen Autorizado:"), gbc );
        gbc.gridx = 25;
        gbc.gridwidth = 15;
        panel.add( volumeAnuality, gbc );

        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 21;
        gbc.gridwidth = 5;
        panel.add( new JLabel("Anualidad:"), gbc );
        gbc.gridx = 5;
        gbc.gridwidth = 5;
        panel.add( cantityAnuality, gbc );
        gbc.gridx = 11;
        gbc.gridwidth = 2;
        panel.add( new JLabel("de"), gbc );
        gbc.gridx = 13;
        gbc.gridwidth = 5;
        panel.add( paymentAnuality, gbc );
        gbc.gridx = 19;
        gbc.gridwidth = 4;
        panel.add( new JLabel("Producto:"), gbc );
        gbc.gridx = 23;
        gbc.gridwidth = 17;
        panel.add( product, gbc );

        gbc.gridx = 0;
        gbc.gridy = 22;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 23;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 24;
        gbc.gridwidth = 6;
        panel.add( new JLabel("Destinatario:"), gbc );
        gbc.gridx = 7;
        gbc.gridwidth = 14;
        panel.add( cbAddressee, gbc );
        gbc.gridx = 22;
        gbc.gridwidth = 9;
        panel.add( seeAddressee, gbc );
        gbc.gridx = 32;
        gbc.gridwidth = 8;
        panel.add( update2, gbc );

        gbc.gridy = 25;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        return panel;
    }

    public JPanel getCardTwo() {
        JPanel panel = new JPanel( new GridBagLayout() );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;

        JLabel[] columns = new JLabel[ 40 ];
        for( int i = 0; i < columns.length; i++ ) {
            gbc.gridx = i;
            columns[ i ] = new JLabel( "     " );
            panel.add( columns[ i ], gbc );
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = columns.length;
        JLabel reembarque = new JLabel("DATOS DEL REEMBARQUE FORESTAL");
        reembarque.setForeground(Color.RED);
        panel.add( reembarque, gbc );

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 4;
        gbc.gridwidth = columns.length;
        JLabel producto = new JLabel("INOFORMACIÓN SOBRE LA MATERIA PRIMA, PRODUCTO O SUBPRODUCTO");
        producto.setForeground(Color.BLUE);
        panel.add( producto, gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        panel.add( new JLabel("Cantidad:"), gbc );
        gbc.gridx = 4;
        gbc.gridwidth = 5;
        panel.add( cantityProduct, gbc );
        gbc.gridx = 10;
        gbc.gridwidth = 5;
        panel.add( new JLabel("Descripción:"), gbc );
        gbc.gridx = 16;
        gbc.gridwidth = 24;
        panel.add( descriptionProduct, gbc );

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 8;
        gbc.gridwidth = 4;
        panel.add( new JLabel("Volúmen:"), gbc );
        gbc.gridx = 4;
        gbc.gridwidth = 10;
        panel.add( volumeProduct, gbc );
        gbc.gridx = 15;
        gbc.gridwidth = 8;
        panel.add( new JLabel("Unidad de Medida:"), gbc );
        gbc.gridx = 23;
        gbc.gridwidth = 10;
        panel.add( measureProduct, gbc );

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 18;
        panel.add( new JLabel("Cantidad que ampara el documento con letra:"), gbc );
        gbc.gridx = 18;
        gbc.gridwidth = 22;
        panel.add( letter, gbc );

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 12;
        gbc.gridwidth = columns.length;
        JLabel saldos = new JLabel("INOFORMACIÓN SOBRE SALDOS");
        saldos.setForeground(Color.BLUE);
        panel.add( saldos, gbc );

        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 7;
        panel.add( new JLabel("Observaciones:"), gbc );
        gbc.gridx = 7;
        gbc.gridwidth = 33;
        panel.add( observation, gbc );

        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 16;
        gbc.gridx = 0;
        gbc.gridwidth = 17;
        panel.add( new JLabel("Saldo disponible según documento anterior:"), gbc );
        gbc.gridx = 17;
        gbc.gridwidth = 23;
        panel.add( previousBalance, gbc );

        gbc.gridx = 0;
        gbc.gridy = 17;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 18;
        gbc.gridx = 0;
        gbc.gridwidth = 17;
        panel.add( new JLabel("Cantidad que ampara este documento:"), gbc );
        gbc.gridx = 17;
        gbc.gridwidth = 23;
        panel.add( cantityBalance, gbc );

        gbc.gridx = 0;
        gbc.gridy = 19;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridy = 20;
        gbc.gridx = 0;
        gbc.gridwidth = 17;
        panel.add( new JLabel("Saldo que pasa al sigueinte documento:"), gbc );
        gbc.gridx = 17;
        gbc.gridwidth = 23;
        panel.add( nextBalance, gbc );

        gbc.gridx = 0;
        gbc.gridy = 21;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 22;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        gbc.gridx = 0;
        gbc.gridy = 23;
        gbc.gridwidth = 6;
        panel.add( new JLabel("Transporte:"), gbc );
        gbc.gridx = 7;
        gbc.gridwidth = 14;
        panel.add( cbTransport, gbc );
        gbc.gridx = 22;
        gbc.gridwidth = 9;
        panel.add( seeTransport, gbc );
        gbc.gridx = 32;
        gbc.gridwidth = 8;
        panel.add( update3, gbc );

        gbc.gridx = 0;
        gbc.gridy = 24;
        gbc.gridwidth = 1;
        panel.add( new JLabel(" "), gbc );

        return panel;
    }

    public void clearAllFields() {
        progressiveFolio.setText( "" );
        authorizedFolio.setText( "" );
        rfn.setText( "" );
        resolutionType.setText( "" );
        reolutionNumber.setText( "" );
        validity.setText( "" );
        volumeAnuality.setText( "" );
        cantityAnuality.setText( "" );
        paymentAnuality.setText( "" );
        product.setText( "" );
        descriptionProduct.setText( "" );
        cantityProduct.setText( "" );
        measureProduct.setText( "" );
        volumeProduct.setText( "" );
        letter.setText( "" );
        observation.setText( "" );
        previousBalance.setText( "" );
        cantityBalance.setText( "" );
        nextBalance.setText( "" );
    }

    private MyDate getDateExpedition() {
        int d = Integer.parseInt( cbEDay.getSelectedItem().toString() );
        int m = Integer.parseInt( cbEMonth.getSelectedItem().toString() );
        int y = Integer.parseInt( cbEYear.getSelectedItem().toString() );
        MyDate date = new MyDate( d, m, y );

        return date;
    }

    private MyDate getDateValidity() {
        int d = Integer.parseInt( cbVDay.getSelectedItem().toString() );
        int m = Integer.parseInt( cbVMonth.getSelectedItem().toString() );
        int y = Integer.parseInt( cbVYear.getSelectedItem().toString() );
        MyDate date = new MyDate( d, m, y );

        return date;
    }

    private MyDate getDateResolution() {
        int d = Integer.parseInt( cbRDay.getSelectedItem().toString() );
        int m = Integer.parseInt( cbRMonth.getSelectedItem().toString() );
        int y = Integer.parseInt( cbRYear.getSelectedItem().toString() );
        MyDate date = new MyDate( d, m, y );

        return date;
    }

    public Autorizacion getAuthorization() {
        authorizationSelected = cbAuthorization.getSelectedIndex();

        if(authorizations.length > 0)
            return authorizations[authorizationSelected];

        return null;
    }

    public Destinatario getAddressee() {
        addresseeSelected = cbAddressee.getSelectedIndex();

        if(addressees.length > 0)
            return addressees[addresseeSelected];

        return null;
    }

    public Transporte getTransport() {
        transportSelected = cbTransport.getSelectedIndex();

        if(transports.length > 0)
            return transports[transportSelected];

        return null;
    }

    public Reembarque getReembarqueFromFields() {
        Reembarque reembarque = new Reembarque();
        reembarque.setProgressiveFolio( progressiveFolio.getText() );
        reembarque.setAuthorizedFolio( authorizedFolio.getText() );
        reembarque.setExpedition( getDateExpedition().databaseFormat() );
        reembarque.setExpiration( getDateValidity().databaseFormat() );
        reembarque.setLetter( letter.getText() );

        return reembarque;
    }

    public Resolucion getResolucion() {
        Resolucion resolution = new Resolucion();
        resolution.setRfn( rfn.getText() );
        resolution.setResolutionType( resolutionType.getText() );
        resolution.setNumber( reolutionNumber.getText() );
        resolution.setDate( getDateResolution().databaseFormat() );
        resolution.setValidity( validity.getText() );
        resolution.setVolumeAnuality( volumeAnuality.getText() );
        resolution.setCantityAnuality( cantityAnuality.getText() );
        resolution.setPaymentAnuality( paymentAnuality.getText() );
        resolution.setProduct( product.getText() );
        resolution.setReembarqueProgressiveFolio( progressiveFolio.getText() );

        return resolution;
    }

    public ProductoAmparado getShelterProduct() {
        ProductoAmparado shelterProduct = new ProductoAmparado();
        shelterProduct.setCantity( cantityProduct.getText() );
        shelterProduct.setDescription( descriptionProduct.getText() );
        shelterProduct.setVolume( volumeProduct.getText() );
        shelterProduct.setMeasure( measureProduct.getText() );
        shelterProduct.setReembarqueProgressiveFolio( progressiveFolio.getText() );

        return shelterProduct;
    }

    public Saldo getBalance() {
        Saldo balance = new Saldo();
        balance.setObservation( observation.getText() );
        balance.setPreviousBalance( previousBalance.getText() );
        balance.setActualBalance( cantityBalance.getText() );
        balance.setNextBalance( nextBalance.getText() );
        balance.setReembarqueProgressiveFolio( progressiveFolio.getText() );

        return balance;
    }

    public Informacion getInformation() {
        Informacion information = new Informacion();
        information.setAuthorizationOfficeNumber( getAuthorization().getOfficeNumber() );
        information.setAddresseIdentificationCode( getAddressee().getIdentificationCode() );
        information.setTransportLicensePlate( getTransport().getLicensePlate() );
        information.setReembarqueProgressiveFolio( progressiveFolio.getText() );

        return information;
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if(s.equals("Ver Autorización")) {
            new JModifyAuthorization(frame,getAuthorization());
        } else if(s.equals("Ver Destinatario")) {
            new JModifyAddressee(frame,getAddressee());
        } else if(s.equals("Ver Transporte")) {
            new JModifyTransport(frame,getTransport());
        } else if(s.equals("u1")) {
            update(1);
        } else if(s.equals("u2")) {
            update(2);
        } else if(s.equals("u3")) {
            update(3);
        }
    }

}
