package aserradero.dialogs.transport;

import aserradero.database.Consulta;
import aserradero.entities.Transporte;

import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;

/**
 *
 * @author Gabriel
 */
public class FillTransportTable implements Runnable {

    private NotEditTableModel model;

    public FillTransportTable(NotEditTableModel model) {
        this.model = model;
    }

    public void run() {
        Transporte transport = new Transporte();
        Consulta enquery = new Consulta();
        Transporte[] transports = (Transporte[]) enquery.executeQuery( transport.getAllTransports(), "Transportes" );

        if( transports != null ) {
            for( int i = 0; i < transports.length; i++ ) {
                Object[] o = new Object[ 5 ];
                o[ 0 ] = i + 1;
                o[ 1 ] = transports[ i ].getLicensePlate();
                o[ 2 ] = transports[ i ].getTransport();
                o[ 3 ] = transports[ i ].getMark();
                o[ 4 ] = transports[ i ].getModel();
                
                model.addRow( o );
            }
        }
    }

}
