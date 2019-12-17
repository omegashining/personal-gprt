package aserradero.dialogs.client;

import aserradero.entities.Cliente;
import aserradero.database.Consulta;

import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;

/**
 *
 * @author Gabriel
 */
public class FillClientTable implements Runnable {

    private NotEditTableModel model;

    public FillClientTable(NotEditTableModel model) {

        this.model = model;
    }

    public void run() {
        Cliente client = new Cliente();
        Consulta enquery = new Consulta();
        Cliente[] clients = (Cliente[]) enquery.executeQuery( client.getAllClients(), "Clientes" );

        if( clients != null ) {
            for( int i = 0; i < clients.length; i++ ) {
                Object[] onject = new Object[ 5 ];
                onject[0] = i + 1;
                onject[1] = clients[i].getRfc();
                onject[2] = clients[i].getName();
                onject[3] = clients[i].getPaternalSurname();
                onject[4] = clients[i].getMaternalSurname();
                
                model.addRow( onject );
            }
        }
    }

}
