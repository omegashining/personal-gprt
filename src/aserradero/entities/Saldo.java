package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Saldo {

    private String observation;
    private String previousBalance;
    private String actualBalance;
    private String nextBalance;
    private String reembarqueProgressiveFolio;

    public Saldo() {
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(String previousBalance) {
        this.previousBalance = previousBalance;
    }

    public String getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(String actualBalance) {
        this.actualBalance = actualBalance;
    }

    public String getNextBalance() {
        return nextBalance;
    }

    public void setNextBalance(String nextBalance) {
        this.nextBalance = nextBalance;
    }

    public String getReembarqueProgressiveFolio() {
        return reembarqueProgressiveFolio;
    }

    public void setReembarqueProgressiveFolio(String reembarqueProgressiveFolio) {
        this.reembarqueProgressiveFolio = reembarqueProgressiveFolio;
    }

    public String insert() {
        return "INSERT INTO Saldos (observaciones, saldo_anterior, saldo_actual, saldo_siguiente, reembarques_folio_progresivo) VALUES ('" + observation + "'," + previousBalance + "," + actualBalance + "," + nextBalance + "," + reembarqueProgressiveFolio + ")";
    }

    public String update() {
        return "UPDATE Saldos SET observaciones = '" + observation + "', saldo_anterior = " + previousBalance + ", saldo_actual = " + actualBalance + ", saldo_siguiente = " + nextBalance + ", reembarques_folio_progresivo = " + reembarqueProgressiveFolio + " WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String delete() {
        return "DELETE FROM Saldos WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String search() {
        return "SELECT * FROM Saldos WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String getAllBalances() {
        return "SELECT * FROM Saldos";
    }

    public boolean areEmptyFields() {
        if( observation.equals("") || previousBalance.equals("") || actualBalance.equals("") || nextBalance.equals("") || reembarqueProgressiveFolio.equals("") )
            return true;

        return false;
    }

    @Override
    public String toString() {
        return observation + " " + previousBalance + " " + actualBalance + " " + nextBalance + " " + reembarqueProgressiveFolio;
    }

}
