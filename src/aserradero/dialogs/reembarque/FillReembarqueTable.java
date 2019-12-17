package aserradero.dialogs.reembarque;

import aserradero.database.Consulta;
import aserradero.entities.Reembarque;

import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;

/**
 *
 * @author Gabriel
 */
public class FillReembarqueTable implements Runnable {

    private NotEditTableModel model;

    public FillReembarqueTable(NotEditTableModel model) {
        this.model = model;
    }

    public void run() {
        Reembarque reembarque = new Reembarque();
        Consulta enquery = new Consulta();
        Reembarque[] reembarques = (Reembarque[]) enquery.executeQuery( reembarque.getAllReembarques(), "Reembarques" );

        if( reembarques != null ) {
            for( int j = 0; j < reembarques.length; j++ ) {
                Object[] onject = new Object[ 3 ];
                onject[0] = j + 1;
                onject[1] = reembarques[j].getProgressiveFolio();
                onject[2] = reembarques[j].getAuthorizedFolio();
                
                model.addRow( onject );
            }
        }
    }
}
