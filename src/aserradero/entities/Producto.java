package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Producto {

    private String description;
    private String measure;
    private String price;

    public Producto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String insert() {
        return "INSERT INTO Productos (descripcion, unidad_medida, precio) VALUES ('" + description + "','" + measure + "'," + price + ")";
    }

    public String update() {
        return "UPDATE Productos SET descripcion = '" + description + "', unidad_medida = '" + measure + "', precio = " + price + " WHERE descripcion = '" + description + "'";
    }

    public String delete() {
        return "DELETE FROM Productos WHERE descripcion = '" + description + "'";
    }

    public String search() {
        return "SELECT * FROM Productos WHERE descripcion = '" + description + "'";
    }

    public String getAllProducts() {
        return "SELECT * FROM Productos";
    }
    
    public boolean areEmptyFields() {
        if(description.equals("") || measure.equals("") || price.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return description + " " + measure + " " + price;
    }

}
