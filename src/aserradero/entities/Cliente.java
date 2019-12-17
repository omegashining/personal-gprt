package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Cliente {

    private String rfc;
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private String address;
    private String city;

    public Cliente() {
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaternalSurname() {
        return paternalSurname;
    }

    public void setPaternalSurname(String paternalSurname) {
        this.paternalSurname = paternalSurname;
    }

    public String getMaternalSurname() {
        return maternalSurname;
    }

    public void setMaternalSurname(String maternalSurname) {
        this.maternalSurname = maternalSurname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String insert() {
        return "INSERT INTO Clientes (rfc, nombre, apellido_paterno, apellido_materno, direccion, ciudad) VALUES ('" + rfc + "','" + name + "','" + paternalSurname + "','" + maternalSurname + "','" + address + "','" + city + "')";
    }

    public String update() {
        return "UPDATE Clientes SET rfc = '" + rfc + "', nombre = '" + name + "', apellido_paterno = '" + paternalSurname + "', apellido_materno = '" + maternalSurname + "', direccion = '" + address + "', ciudad = '" + city + "' WHERE rfc = '" + rfc + "'";
    }

    public String delete() {
        return "DELETE FROM Clientes WHERE rfc = '" + rfc + "'";
    }

    public String search() {
        return "SELECT * FROM Clientes WHERE rfc = '" + rfc + "'";
    }

    public String getAllClients() {
        return "SELECT * FROM Clientes";
    }

    public boolean areEmptyFields() {
        if(rfc.equals("") || name.equals("") || paternalSurname.equals("") || maternalSurname.equals("") || address.equals("") || city.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return rfc + " " + name + " " + paternalSurname + " " + maternalSurname + " " + address + " " + city;
    }
    
}
