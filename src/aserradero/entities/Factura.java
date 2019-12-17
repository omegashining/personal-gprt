package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Factura {

    private String invoiceNumber;
    private String date;
    private String iva;
    private String clientRfc;

    public Factura() {
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getClientRfc() {
        return clientRfc;
    }

    public void setRfc(String clientRfc) {
        this.clientRfc = clientRfc;
    }

    public String insert() {
        return "INSERT INTO Facturas (no_factura, fecha, iva, clientes_rfc) VALUES (" + invoiceNumber + ",'" + date+ "'," + iva + ",'" + clientRfc + "')";
    }

    public String update() {
        return "UPDATE Facturas SET no_factura = " + invoiceNumber + " fecha = '" + date + "', iva = " + iva + ", clientes_rfc = '" + clientRfc + "' WHERE no_factura = " + invoiceNumber + " AND fecha = '" + date + "'";
    }

    public String delete() {
        return "DELETE FROM Facturas WHERE no_factura = " + invoiceNumber + " AND fecha = '" + date + "'";
    }

    public String deleteForClient() {
        return "DELETE FROM Facturas WHERE clientes_rfc = '" + clientRfc + "'";
    }

    public String search() {
        return "SELECT * FROM Facturas WHERE no_factura = " + invoiceNumber + " AND fecha = '" + date + "'";
    }

    public String searchForClient() {
        return "SELECT * FROM Facturas WHERE clientes_rfc = '" + clientRfc + "'";
    }

    public String getAllInvoices() {
        return "SELECT * FROM Facturas";
    }

    public boolean areEmptyFields() {
        if(invoiceNumber.equals("") || date.equals("") || iva.equals("") || clientRfc.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return invoiceNumber + " " + date + " " + iva + " " + clientRfc;
    }

}
