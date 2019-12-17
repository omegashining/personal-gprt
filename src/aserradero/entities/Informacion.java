package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Informacion {

    private String authorizationOfficeNumber;
    private String transportLicensePlate;
    private String adrresseIdentificationCode;
    private String reembarqueProgressiveFolio;

    public String getAuthorizationOfficeNumber() {
        return authorizationOfficeNumber;
    }

    public void setAuthorizationOfficeNumber(String authorizationOfficeNumber) {
        this.authorizationOfficeNumber = authorizationOfficeNumber;
    }

    public String getTransportLicensePlate() {
        return transportLicensePlate;
    }

    public void setTransportLicensePlate(String transportLicensePlate) {
        this.transportLicensePlate = transportLicensePlate;
    }

    public String getAddresseIdentificationCode() {
        return adrresseIdentificationCode;
    }

    public void setAddresseIdentificationCode(String addresseIdentificationCode) {
        this.adrresseIdentificationCode = addresseIdentificationCode;
    }

    public String getReembarqueProgressiveFolio() {
        return reembarqueProgressiveFolio;
    }

    public void setReembarqueProgressiveFolio(String reembarqueProgressiveFolio) {
        this.reembarqueProgressiveFolio = reembarqueProgressiveFolio;
    }

    public String insert() {
        return "INSERT INTO Informacion (autorizacion_no_oficio, transporte_placas, destinatario_codigo_identificacion, reembarques_folio_progresivo) VALUES ('" + authorizationOfficeNumber + "','" + transportLicensePlate + "','" + adrresseIdentificationCode + "'," + reembarqueProgressiveFolio + ")";
    }

    public String update() {
        return "UPDATE Informacion SET autorizacion_no_oficio = '" + authorizationOfficeNumber + "', transporte_placas = '" + transportLicensePlate + "', destinatario_codigo_identificacion = '" + adrresseIdentificationCode + "', reembarques_folio_progresivo = " + reembarqueProgressiveFolio + " WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String delete() {
        return "DELETE FROM Informacion WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String search() {
        return "SELECT * FROM Informacion WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String getAllShelterProducts() {
        return "SELECT * FROM Informacion";
    }

    public boolean areEmptyFields() {
        if(authorizationOfficeNumber.equals("") || transportLicensePlate.equals("") || adrresseIdentificationCode.equals("") || reembarqueProgressiveFolio.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return authorizationOfficeNumber + " " + transportLicensePlate + " " + adrresseIdentificationCode + " " + reembarqueProgressiveFolio;
    }

}
