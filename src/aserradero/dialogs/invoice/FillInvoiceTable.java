package aserradero.dialogs.invoice;

import aserradero.database.Consulta;
import aserradero.entities.Factura;

import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;
import mx.gob.org.ipn.cic.mu.classes.MyDate;

/**
 *
 * @author Gabriel
 */
public class FillInvoiceTable implements Runnable {

    private NotEditTableModel model;

    public FillInvoiceTable(NotEditTableModel model) {
        this.model = model;
    }

    public void run() {
        Factura invoice = new Factura();
        Consulta enquery = new Consulta();
        Factura[] invoices = (Factura[]) enquery.executeQuery( invoice.getAllInvoices(), "Facturas" );

        if( invoices != null ) {
            MyDate date = new MyDate();

            for( int i = 0; i < invoices.length; i++ ) {
                Object[] oi = new Object[ 4 ];
                oi[0] = i + 1;
                oi[1] = invoices[i].getInvoiceNumber();
                date.setDatabaseDate( invoices[i].getDate() );
                oi[2] = date.format();
                oi[3] = invoices[i].getClientRfc();
                
                model.addRow( oi );
            }
        }
    }
}
