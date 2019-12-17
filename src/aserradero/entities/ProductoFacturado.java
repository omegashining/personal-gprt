package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class ProductoFacturado {

    private String description;
    private String cantity;
    private String price;
    private String invoiceInvoiceNumber;
    private String invoiceDate;

    public ProductoFacturado() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCantity() {
        return cantity;
    }

    public void setCantity(String cantity) {
        this.cantity = cantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInvoiceInvoiceNumber() {
        return invoiceInvoiceNumber;
    }

    public void setInvoiceInvoiceNumber(String invoiceInvoiceNumber) {
        this.invoiceInvoiceNumber = invoiceInvoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String insert() {
        return "INSERT INTO Productos_Facturados (descripcion, cantidad, precio, facturas_no_factura, facturas_fecha) VALUES ('" + description + "'," + cantity + "," + price + "," + invoiceInvoiceNumber + ",'" + invoiceDate + "')";
    }

    public String update() {
        return "UPDATE Productos_Facturados' SET descripcion = '" + description + "', cantidad = " + cantity + ", precio = " + price + ", facturas_no_factura = " + invoiceInvoiceNumber + ", facturas_fecha = '" + invoiceDate + "' WHERE descripcion = '" + description + "' AND facturas_no_factura = " + invoiceInvoiceNumber + " AND facturas_fecha = '" + invoiceDate + "'";
    }

    public String delete() {
        return "DELETE FROM Productos_Facturados WHERE descripcion = '" + description + "' AND facturas_no_factura = " + invoiceInvoiceNumber + " AND facturas_fecha = '" + invoiceDate + "'";
    }

    public String deleteForInvoice() {
        return "DELETE FROM Productos_Facturados WHERE facturas_no_factura = " + invoiceInvoiceNumber + " AND facturas_fecha = '" + invoiceDate + "'";
    }

    public String search() {
        return "SELECT * FROM Productos_Facturados WHERE facturas_no_factura = " + invoiceInvoiceNumber + " AND facturas_fecha = '" + invoiceDate + "'";
    }

    public String getAllInvoicesProducts() {
        return "SELECT * FROM Productos_Facturados";
    }

    public boolean areEmptyFields() {
        if( description.equals("") || cantity.equals("") || price.equals("") || invoiceInvoiceNumber.equals("") || invoiceDate.equals("") )
            return true;

        return false;
    }

    @Override
    public String toString() {
        return description + " " + cantity + " " + price + " " + invoiceInvoiceNumber + " " + invoiceDate;
    }

}
