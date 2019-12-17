package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Resolucion {

    private String rfn;
    private String resolutionType;
    private String number;
    private String date;
    private String validity;
    private String volumeAnuality;
    private String cantityAnuality;
    private String paymentAnuality;
    private String product;
    private String reembarqueProgressiveFolio;

    public Resolucion() {
    }

    public String getRfn() {
        return rfn;
    }

    public void setRfn(String rfn) {
        this.rfn = rfn;
    }

    public String getResolutionType() {
        return resolutionType;
    }

    public void setResolutionType(String resolutionType) {
        this.resolutionType = resolutionType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getVolumeAnuality() {
        return volumeAnuality;
    }

    public void setVolumeAnuality(String volumeAnuality) {
        this.volumeAnuality = volumeAnuality;
    }

    public String getCantityAnuality() {
        return cantityAnuality;
    }

    public void setCantityAnuality(String cantityAnuality) {
        this.cantityAnuality = cantityAnuality;
    }

    public String getPaymentAnuality() {
        return paymentAnuality;
    }

    public void setPaymentAnuality(String paymentAnuality) {
        this.paymentAnuality = paymentAnuality;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getReembarqueProgressiveFolio() {
        return reembarqueProgressiveFolio;
    }

    public void setReembarqueProgressiveFolio(String reembarqueProgressiveFolio) {
        this.reembarqueProgressiveFolio = reembarqueProgressiveFolio;
    }

    public String insert() {
        return "INSERT INTO Resolucion (rfn, tipo_resolucion, numero, fecha, vigencia, volumen_anualidad, cantidad_anualidad, precio_anualidad, producto, reembarques_folio_progresivo) VALUES ('" + rfn + "','" + resolutionType + "','" + number + "','" + date + "','" + validity + "','" + volumeAnuality+ "'," + cantityAnuality + "," + paymentAnuality + ",'" + product + "'," + reembarqueProgressiveFolio + ")";
    }

    public String update() {
        return "UPDATE Resolucion SET rfn = '" + rfn + "', tipo_resolucion = '" + resolutionType + "', numero = '" + number + "', fecha = '" + date + "', vigencia = '" + validity + "', volumen_anualidad= '" + volumeAnuality + "', cantidad_anualidad = " + cantityAnuality + ", precio_anualidad = " + paymentAnuality + ", producto = '" + product + "', reembarques_folio_progresivo = " + reembarqueProgressiveFolio + " WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String delete() {
        return "DELETE FROM Resolucion WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String search() {
        return "SELECT * FROM Resolucion WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String getAllResolutions() {
        return "SELECT * FROM Resolucion";
    }

    public boolean areEmptyFields() {
        if(date.equals("") || resolutionType.equals("") || number.equals("") || rfn.equals("") || validity.equals("") || volumeAnuality.equals("") || cantityAnuality.equals("") || paymentAnuality.equals("") || product.equals("") || reembarqueProgressiveFolio.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return date + " " + resolutionType + " " + number + " " + rfn + " " + validity + " " + volumeAnuality + " " + cantityAnuality + " " +paymentAnuality + " " + product + " " + reembarqueProgressiveFolio;
    }

}
