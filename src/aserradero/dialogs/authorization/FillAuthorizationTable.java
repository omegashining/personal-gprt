package aserradero.dialogs.authorization;

import aserradero.entities.Autorizacion;
import aserradero.database.Consulta;

import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;
import mx.gob.org.ipn.cic.mu.classes.MyDate;

/**
 *
 * @author Gabriel
 */
public class FillAuthorizationTable implements Runnable {

    private NotEditTableModel model;

    public FillAuthorizationTable(NotEditTableModel model) {
        this.model = model;
    }

    public void run() {
        Autorizacion authorization = new Autorizacion();
        Consulta enquery = new Consulta();
        Autorizacion[] authorizations = (Autorizacion[]) enquery.executeQuery( authorization.getAllAuthorizations(), "Autorizaciones" );

        if( authorizations != null ) {
            MyDate date = new MyDate();

            for( int i = 0; i < authorizations.length; i++ ) {
                Object[] object = new Object[ 4 ];
                object[0] = i + 1;
                object[1] = authorizations[i].getOfficeNumber();
                date.setDatabaseDate( authorizations[i].getDate() );
                object[2] = date.format();
                date.setDatabaseDate( authorizations[i].getExpiration() );
                object[3] = date.format();

                model.addRow( object );
            }
        }
    }

}
