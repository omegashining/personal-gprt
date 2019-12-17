package aserradero.dialogs.account;

import aserradero.entities.Cuenta;
import aserradero.database.Consulta;

import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;

/**
 *
 * @author Gabriel
 */
public class FillAccountTable implements Runnable {

    private NotEditTableModel model;

    public FillAccountTable(NotEditTableModel model) {
        this.model = model;
    }

    public void run() {
        Cuenta account = new Cuenta();
        Consulta enquery = new Consulta();
        Cuenta[] accounts = (Cuenta[]) enquery.executeQuery( account.getAllAccounts(), "Cuentas" );

        int number = enquery.getRowsFrom( "Cuentas" );

        if(accounts != null && number > 2) {
            for(int i = 2; i < accounts.length; i++) {
                Object[] object = new Object[ 3 ];
                object[0] = i - 1;
                object[1] = accounts[i].getUsername();

                if(accounts[i].getPermission().equals("1"))
                    object[2] = "Todos";
                else
                    object[2] = "Ninguno";

                model.addRow( object );
            }
        }
    }
}
