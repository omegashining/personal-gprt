package aserradero.dialogs.addressee;

import aserradero.entities.Destinatario;
import aserradero.database.Consulta;

import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;

/**
 *
 * @author Gabriel
 */
public class FillAddresseeTable implements Runnable {

    private NotEditTableModel model;

    public FillAddresseeTable(NotEditTableModel model) {
        this.model = model;
    }

    public void run() {
        Destinatario addressee = new Destinatario();
        Consulta enquery = new Consulta();
        Destinatario[] addressees = (Destinatario[]) enquery.executeQuery( addressee.getAllAddressees(), "Destinatarios" );

        if(addressees != null) {
            for(int i = 0; i < addressees.length; i++) {
                Object[] object = new Object[ 4 ];
                object[0] = i + 1;
                object[1] = addressees[i].getIdentificationCode();
                object[2] = addressees[i].getName();
                object[3] = addressees[i].getCurp();

                model.addRow( object );
            }
        }
    }

}
