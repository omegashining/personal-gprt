package aserradero.database;

import aserradero.entities.Autorizacion;
import aserradero.entities.Cliente;
import aserradero.entities.Cuenta;
import aserradero.entities.Destinatario;
import aserradero.entities.Factura;
import aserradero.entities.Informacion;
import aserradero.entities.Producto;
import aserradero.entities.ProductoAmparado;
import aserradero.entities.ProductoFacturado;
import aserradero.entities.Reembarque;
import aserradero.entities.Resolucion;
import aserradero.entities.Saldo;
import aserradero.entities.Titular;
import aserradero.entities.Transporte;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Gabriel
 */
public class Consulta {
    
    private Conexion conexion;

    public Consulta() {
        this.conexion = new Conexion();
    }

    public boolean executeUpdate(String query) {
        boolean result = false;

        try {
            Connection connection = conexion.connect();

            if(connection != null) {
                Statement statement = connection.createStatement();
                statement.executeUpdate( query );
                statement.close();

                result = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        conexion.close();

        return result;
    }

    public Object[] executeQuery(String query, String table) {
        try {
            Connection connection = conexion.connect();

            if(connection != null) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet resultSet = statement.executeQuery( query );

                int rows = 0;
                resultSet.last();
                rows = resultSet.getRow();
                resultSet.first();

                if(table.equals("Autorizaciones")) {
                    Autorizacion[] authorizations = new Autorizacion[ rows ];

                    for(int i = 0; i < rows; i++) {
                        authorizations[i] = new Autorizacion();
                        authorizations[i].setOfficeNumber( resultSet.getString(1) );
                        authorizations[i].setDate( resultSet.getString(2) );
                        authorizations[i].setShelterCantity( resultSet.getString(3) );
                        authorizations[i].setInicialFolio( resultSet.getString(4) );
                        authorizations[i].setFinalFolio( resultSet.getString(5) );
                        authorizations[i].setMeasure( resultSet.getString(6) );
                        authorizations[i].setExpiration( resultSet.getString(7) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();
                    
                    return authorizations;
                } else if(table.equals("Clientes")) {
                    Cliente[] clients = new Cliente[ rows ];

                    for(int i = 0; i < rows; i++) {
                        clients[i] = new Cliente();
                        clients[i].setRfc( resultSet.getString(1) );
                        clients[i].setName( resultSet.getString(2) );
                        clients[i].setPaternalSurname( resultSet.getString(3) );
                        clients[i].setMaternalSurname( resultSet.getString(4) );
                        clients[i].setAddress( resultSet.getString(5) );
                        clients[i].setCity( resultSet.getString(6) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();
                    
                    return clients;
                } else if(table.equals("Cuentas")) {
                    Cuenta[] accounts = new Cuenta[ rows ];

                    for(int i = 0; i < rows; i++) {
                        accounts[i] = new Cuenta();
                        accounts[i].setUsername( resultSet.getString(2) );
                        accounts[i].setPassword( resultSet.getString(3) );
                        accounts[i].setRewrite( accounts[i].getPassword() );
                        accounts[i].setPermission( resultSet.getString(4) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return accounts;
                } else if(table.equals("Destinatarios")) {
                    Destinatario[] addressees = new Destinatario[ rows ];

                    for(int i = 0; i < rows; i++) {
                        addressees[i] = new Destinatario();
                        addressees[i].setIdentificationCode( resultSet.getString(1) );
                        addressees[i].setName( resultSet.getString(2) );
                        addressees[i].setCurp( resultSet.getString(3) );
                        addressees[i].setRfn( resultSet.getString(4) );
                        addressees[i].setResidenceDestiny( resultSet.getString(5) );
                        addressees[i].setPopulation( resultSet.getString(6) );
                        addressees[i].setTown( resultSet.getString(7) );
                        addressees[i].setEntity( resultSet.getString(8) );
                        addressees[i].setResidence( resultSet.getString(9) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return addressees;
                } else if(table.equals("Facturas")) {
                    Factura[] invoices = new Factura[ rows ];

                    for(int i = 0; i < rows; i++) {
                        invoices[i] = new Factura();
                        invoices[i].setInvoiceNumber( resultSet.getString(1) );
                        invoices[i].setDate( resultSet.getString(2) );
                        invoices[i].setIva( resultSet.getString(3) );
                        invoices[i].setRfc( resultSet.getString(4) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return invoices;
                } else if(table.equals("Informacion")) {
                    Informacion[] informations = new Informacion[ rows ];

                    for(int i = 0; i < rows; i++) {
                        informations[i] = new Informacion();
                        informations[i].setAuthorizationOfficeNumber( resultSet.getString(1) );
                        informations[i].setTransportLicensePlate( resultSet.getString(2) );
                        informations[i].setAddresseIdentificationCode( resultSet.getString(3) );
                        informations[i].setReembarqueProgressiveFolio( resultSet.getString(4) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return informations;
                } else if(table.equals("Productos")) {
                    Producto[] products = new Producto[ rows ];

                    for(int i = 0; i < rows; i++) {
                        products[i] = new Producto();
                        products[i].setDescription( resultSet.getString(2) );
                        products[i].setMeasure( resultSet.getString(3) );
                        products[i].setPrice( resultSet.getString(4) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return products;
                } else if(table.equals("Producto Amparado")) {
                    ProductoAmparado[] shelterProducts = new ProductoAmparado[ rows ];

                    for(int i = 0; i < rows; i++) {
                        shelterProducts[i] = new ProductoAmparado();
                        shelterProducts[i].setCantity( resultSet.getString(1) );
                        shelterProducts[i].setDescription( resultSet.getString(2) );
                        shelterProducts[i].setVolume( resultSet.getString(3) );
                        shelterProducts[i].setMeasure( resultSet.getString(4) );
                        shelterProducts[i].setReembarqueProgressiveFolio( resultSet.getString(5) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return shelterProducts;
                } else if(table.equals("Productos Facturados")) {
                    ProductoFacturado[] sales = new ProductoFacturado[ rows ];

                    for(int i = 0; i < rows; i++) {
                        sales[i] = new ProductoFacturado();
                        sales[i].setDescription( resultSet.getString(1) );
                        sales[i].setCantity( resultSet.getString(2) );
                        sales[i].setPrice( resultSet.getString(3) );
                        sales[i].setInvoiceInvoiceNumber( resultSet.getString(4) );
                        sales[i].setInvoiceDate( resultSet.getString(5) );
                        
                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return sales;
                } else if(table.equals("Reembarques")) {
                    Reembarque[] reembarques = new Reembarque[ rows ];

                    for(int i = 0; i < rows; i++) {
                        reembarques[i] = new Reembarque();
                        reembarques[i].setProgressiveFolio( resultSet.getString(1) );
                        reembarques[i].setAuthorizedFolio( resultSet.getString(2) );
                        reembarques[i].setExpedition( resultSet.getString(3) );
                        reembarques[i].setExpiration( resultSet.getString(4) );
                        reembarques[i].setLetter( resultSet.getString(5) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return reembarques;
                } else if(table.equals("Resolucion")) {
                    Resolucion[] resolutions = new Resolucion[ rows ];

                    for( int i = 0; i < rows; i++ ) {
                        resolutions[i] = new Resolucion();
                        resolutions[i].setRfn( resultSet.getString(1) );
                        resolutions[i].setResolutionType( resultSet.getString(2) );
                        resolutions[i].setNumber( resultSet.getString(3) );
                        resolutions[i].setDate( resultSet.getString(4) );
                        resolutions[i].setValidity( resultSet.getString(5) );
                        resolutions[i].setVolumeAnuality( resultSet.getString(6) );
                        resolutions[i].setCantityAnuality( resultSet.getString(7) );
                        resolutions[i].setPaymentAnuality( resultSet.getString(8) );
                        resolutions[i].setProduct( resultSet.getString(9) );
                        resolutions[i].setReembarqueProgressiveFolio( resultSet.getString(10) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return resolutions;
                } else if(table.equals("Saldos")) {
                    Saldo[] balances = new Saldo[ rows ];

                    for(int i = 0; i < rows; i++) {
                        balances[i] = new Saldo();
                        balances[i].setObservation( resultSet.getString(1) );
                        balances[i].setPreviousBalance( resultSet.getString(2) );
                        balances[i].setActualBalance( resultSet.getString(3) );
                        balances[i].setNextBalance( resultSet.getString(4) );
                        balances[i].setReembarqueProgressiveFolio( resultSet.getString(5) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return balances;
                } else if(table.equals("Titular")) {
                    Titular[] titular = new Titular[ rows ];

                    for(int i = 0; i < rows; i++) {
                        titular[i] = new Titular();
                        titular[i].setIdentificationCode( resultSet.getString(2) );
                        titular[i].setName( resultSet.getString(3) );
                        titular[i].setAddress( resultSet.getString(4) );
                        titular[i].setTown( resultSet.getString(5) );
                        titular[i].setEntity( resultSet.getString(6) );
                        titular[i].setCp( resultSet.getString(7) );
                        titular[i].setCurp( resultSet.getString(8) );
                        titular[i].setSiem( resultSet.getString(9) );

                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return titular;
                } else if(table.equals("Transportes")) {
                    Transporte[] transports = new Transporte[ rows ];

                    for(int i = 0; i < rows; i++) {
                        transports[i] = new Transporte();
                        transports[i].setLicensePlate( resultSet.getString(1) );
                        transports[i].setTransport( resultSet.getString(2) );
                        transports[i].setProprietor( resultSet.getString(3) );
                        transports[i].setMark( resultSet.getString(4) );
                        transports[i].setType( resultSet.getString(5) );
                        transports[i].setModel( resultSet.getString(6) );
                        transports[i].setCapacity( resultSet.getString(7) );
                        
                        resultSet.next();
                    }

                    resultSet.close();
                    statement.close();
                    conexion.close();

                    return transports;
                }

                resultSet.close();
                statement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        conexion.close();

        return null;
    }

    public int getRowsFrom(String table) {
        Connection connection = conexion.connect();

        int rows = 0;

        try {
            Statement statement = connection.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM " + table );

            resultSet.last();
            rows = resultSet.getRow();
            
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        conexion.close();
        
        return rows;
    }

}
