package aserradero.dialogs.product;

import aserradero.database.Consulta;
import aserradero.entities.Producto;

import mx.gob.org.ipn.cic.du.tables.models.NotEditTableModel;

/**
 *
 * @author Gabriel
 */
public class FillProductTable implements Runnable {

    private NotEditTableModel model;

    public FillProductTable(NotEditTableModel model) {
        this.model = model;
    }

    public void run() {
        Producto product = new Producto();
        Consulta enquery = new Consulta();
        Producto[] products = (Producto[]) enquery.executeQuery( product.getAllProducts(), "Productos" );

        if( products != null ) {
            for(int i = 0; i < products.length; i++) {
                Object[] object = new Object[ 4 ];
                object[0] = i + 1;
                object[1] = products[i].getDescription();
                object[2] = products[i].getMeasure();
                object[3] = products[i].getPrice();

                model.addRow( object );
            }
        }
    }
    
    
}
