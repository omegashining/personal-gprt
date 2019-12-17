package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class ProductoAmparado {

    private String cantity;
    private String description;
    private String volume;
    private String measure;
    private String reembarqueProgressiveFolio;

    public ProductoAmparado() {
    }

    public String getCantity() {
        return cantity;
    }

    public void setCantity(String cantity) {
        this.cantity = cantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getReembarqueProgressiveFolio() {
        return reembarqueProgressiveFolio;
    }

    public void setReembarqueProgressiveFolio(String reembarqueProgressiveFolio) {
        this.reembarqueProgressiveFolio = reembarqueProgressiveFolio;
    }

    public String insert() {
        return "INSERT INTO Producto_Amparado (cantidad, descripcion, volumen, unidad_medida, reembarques_folio_progresivo) VALUES (" + cantity + ",'" + description + "','" + volume + "','" + measure + "'," + reembarqueProgressiveFolio + ")";
    }

    public String update() {
        return "UPDATE Producto_Amparado SET cantidad = " + cantity + ", descripcion = '" + description + "', volumen = '" + volume + "', unidad_medida = '" + measure + "', reembarques_folio_progresico = " + reembarqueProgressiveFolio + " WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String delete() {
        return "DELETE FROM Producto_Amparado WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String search() {
        return "SELECT * FROM Producto_Amparado WHERE reembarques_folio_progresivo = " + reembarqueProgressiveFolio;
    }

    public String getAllShelterProducts() {
        return "SELECT * FROM Producto_Amparado";
    }

    public boolean areEmptyFields() {
        if(cantity.equals("") || description.equals("") || volume.equals("") || measure.equals("") || reembarqueProgressiveFolio.equals("") )
            return true;

        return false;
    }

    @Override
    public String toString() {
        return cantity + " " + description + " " + volume + " " + measure + " " + reembarqueProgressiveFolio;
    }

}
