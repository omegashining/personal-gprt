package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Titular {

    private String identificationCode;
    private String name;
    private String address;
    private String town;
    private String entity;
    private String cp;
    private String curp;
    private String siem;

    public Titular() {
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getSiem() {
        return siem;
    }

    public void setSiem(String siem) {
        this.siem = siem;
    }

    public String insert() {
        return "INSERT INTO Titular (id, codigo_identificacion, nombre, direccion, municipio, entidad, cp, curp, siem) VALUES (1,'" + identificationCode + "','" + name + "','" + address + "','" + town + "','" + entity + "'," + cp + ",'" + curp + "','" + siem + "')";
    }

    public String update() {
        return "UPDATE Titular SET codigo_identificacion = '" + identificationCode + "', nombre = '" + name + "', direccion = '" + address + "', municipio = '" + town + "', entidad = '" + entity + "', cp = " + cp + ", curp = '" + curp + "', siem = '" + siem + "' WHERE id = 1";
    }

    public String delete() {
        return "DELETE FROM Titular WHERE id = 1";
    }

    public String search() {
        return "SELECT * FROM Titular WHERE id = 1";
    }

    public String getAllTitulars() {
        return "SELECT * FROM Titular";
    }
    
    public boolean areEmptyFields() {
        if(identificationCode.equals("") || name.equals("") || address.equals("") || town .equals("") || entity.equals("") || curp.equals("") || siem.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return identificationCode + " " + name + " " + address + " " + town + " " + entity + " " + curp + " " + siem;
    }
    
}
