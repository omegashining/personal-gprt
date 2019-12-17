package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Transporte {
    
    private String licensePlate;
    private String transport;
    private String proprietor;
    private String mark;
    private String type;
    private String model;
    private String capacity;

    public Transporte() {
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getProprietor() {
        return proprietor;
    }

    public void setProprietor(String proprietor) {
        this.proprietor = proprietor;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String insert() {
        return "INSERT INTO Transportes (placas, medio_transporte, propietario, marca, tipo, modelo, capacidad) VALUES ('" + licensePlate + "','" + transport + "','" + proprietor + "','" + mark + "','" + type + "'," + model + ",'" + capacity + "')";
    }

    public String update() {
        return "UPDATE Transportes SET placas = '" + licensePlate + "', medio_transporte = '" + transport + "', propietario = '" + proprietor + "', marca = '" + mark + "', tipo = '" + type + "', modelo = " + model + ", capacidad = '" + capacity + "' WHERE placas = '" + licensePlate + "'";
    }

    public String delete() {
        return "DELETE FROM Transportes WHERE placas = '" + licensePlate + "'";
    }

    public String search() {
        return "SELECT * FROM Transportes WHERE placas = '" + licensePlate + "'";
    }

    public String getAllTransports() {
        return "SELECT * FROM Transportes";
    }

    public boolean areEmptyFields() {
        if(licensePlate.equals("") || proprietor.equals("")  || transport.equals("") || proprietor.equals("") || mark.equals("") || type.equals("") || model.equals("") || capacity.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return licensePlate + " " + proprietor + " " + transport + " " + proprietor + " " + mark + " " + type + " " + model + " " + capacity;
    }

}
