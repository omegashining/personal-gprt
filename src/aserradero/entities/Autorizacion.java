package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Autorizacion {

    private String officeNumber;
    private String date;
    private String shelterCantity;
    private String inicialFolio;
    private String finalFolio;
    private String measure;
    private String expiration;

    public Autorizacion() {
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShelterCantity() {
        return shelterCantity;
    }

    public void setShelterCantity(String shelterCantity) {
        this.shelterCantity = shelterCantity;
    }

    public String getInicialFolio() {
        return inicialFolio;
    }

    public void setInicialFolio(String inicialFolio) {
        this.inicialFolio = inicialFolio;
    }

    public String getFinalFolio() {
        return finalFolio;
    }

    public void setFinalFolio(String finalFolio) {
        this.finalFolio = finalFolio;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String insert() {
        return "INSERT INTO Autorizaciones (no_oficio, fecha, cantidad_amparo, folio_inicial, folio_final, unidad_medida, fecha_vencimiento) VALUES ('" + officeNumber + "','" + date + "'," + shelterCantity + "," + inicialFolio + "," + finalFolio + ",'" + measure + "','" + expiration + "')";
    }

    public String update() {
        return "UPDATE Autorizaciones SET no_oficio = '" + officeNumber + "', fecha = '" + date + "', cantidad_amparo = " + shelterCantity + ", folio_inicial = " + inicialFolio + ", folio_final = " + finalFolio + ", unidad_medida = '" + measure + "', fecha_vencimiento = '" + expiration + "' WHERE no_oficio = '" + officeNumber + "'";
    }

    public String delete() {
        return "DELETE FROM Autorizaciones WHERE no_oficio = '" + officeNumber + "'";
    }

    public String search() {
        return "SELECT * FROM Autorizaciones WHERE no_oficio = '" + officeNumber + "'";
    }

    public String getAllAuthorizations() {
        return "SELECT * FROM Autorizaciones";
    }

    public boolean areEmptyFields() {
        if(officeNumber.equals("") || date.equals("") || shelterCantity.equals("") || inicialFolio.equals("") || finalFolio.equals("") || measure.equals("") || expiration.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return officeNumber + " " + date + " " + shelterCantity + " " + inicialFolio + " " + finalFolio + " " + measure + " " + expiration;
    }

}
