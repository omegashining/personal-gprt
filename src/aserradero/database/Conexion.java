package aserradero.database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author Gabriel
 */
public class Conexion {

    private Connection connection;
    
    public Conexion() {
        connection = null;
    }

    public Connection connect() {
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream( "database.properties" );
            properties.load( fis );
            fis.close();
            
            String drivers = properties.getProperty( "jdbc.drivers" );

            if(drivers != null)
                System.setProperty( "jdbc.drivers", drivers );
            
            String url = properties.getProperty( "jdbc.url" );
            String username = properties.getProperty( "jdbc.username" );
            String password = properties.getProperty( "jdbc.password" );

            connection = DriverManager.getConnection( url, username, password );
        } catch (Exception ex) {
            ex.printStackTrace();
            connection = null;
        }

        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
