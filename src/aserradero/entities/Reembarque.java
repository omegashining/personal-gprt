package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Reembarque {

    private String progressiveFolio;
    private String authorizedFolio;
    private String expedition;
    private String expiration;
    private String letter;

    public Reembarque() {
    }

    public String getProgressiveFolio() {
        return progressiveFolio;
    }

    public void setProgressiveFolio(String progressiveFolio) {
        this.progressiveFolio = progressiveFolio;
    }

    public String getAuthorizedFolio() {
        return authorizedFolio;
    }

    public void setAuthorizedFolio(String authorizedFolio) {
        this.authorizedFolio = authorizedFolio;
    }

    public String getExpedition() {
        return expedition;
    }

    public void setExpedition(String expedition) {
        this.expedition = expedition;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String insert() {
        return "INSERT INTO Reembarques (folio_progresivo, folio_autorizado, fecha_expedicion, fecha_vencimiento, letra) VALUES (" + progressiveFolio + "," + authorizedFolio + ",'" + expedition + "','" + expiration + "','" + letter + "')";
    }

    public String update() {
        return "UPDATE Reembarques SET folio_progresivo = " + progressiveFolio + ", folio_autorizado = " + authorizedFolio + ", fecha_expedicion = '" + expedition + "', fecha_vencimiento = '" + expiration + "','" + letter + "' WHERE folio_progresivo = " + progressiveFolio;
    }

    public String delete() {
        return "DELETE FROM Reembarques WHERE folio_progresivo = " + progressiveFolio;
    }

    public String search() {
        return "SELECT * FROM Reembarques WHERE folio_progresivo = " + progressiveFolio;
    }

    public String getAllReembarques() {
        return "SELECT * FROM Reembarques";
    }

    public boolean areEmptyFields() {
        if(progressiveFolio.equals("") || authorizedFolio.equals("") || expedition.equals("") || expiration.equals("") || letter.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return progressiveFolio + " " + authorizedFolio + " " + expedition + " " + expiration + " " + letter;
    }

}
