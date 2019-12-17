package aserradero.entities;

/**
 *
 * @author Gabriel
 */
public class Destinatario {

    private String identificationCode;
    private String name;
    private String curp;
    private String rfn;
    private String residenceDestiny;
    private String population;
    private String town;
    private String entity;
    private String residence;

    public Destinatario() {
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

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfn() {
        return rfn;
    }

    public void setRfn(String rfn) {
        this.rfn = rfn;
    }

    public String getResidenceDestiny() {
        return residenceDestiny;
    }

    public void setResidenceDestiny(String residenceDestiny) {
        this.residenceDestiny = residenceDestiny;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
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

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String insert() {
        return "INSERT INTO Destinatarios (codigo_identificacion, nombre, curp, rfn, domicilio_destino, poblacion, municipio, entidad, domicilio) VALUES ('" + identificationCode + "','" + name + "','" + curp + "','" + rfn + "','" + residenceDestiny + "','" + population + "','" + town + "','" + entity + "','" + residence + "')";
    }

    public String update() {
        return "UPDATE Destinatarios SET codigo_identificacion = '" + identificationCode + "', nombre = '" + name + "', curp = '" + curp + "', rfn = '" + rfn + "', domicilio_destino = '" + residenceDestiny + "', poblacion = '" + population + "', municipio = '" + town + "', entidad = '" + entity + "', domicilio = '" + residence + "' WHERE codigo_identificacion = '" + identificationCode + "'";
    }

    public String delete() {
        return "DELETE FROM Destinatarios WHERE codigo_identificacion = '" + identificationCode + "'";
    }

    public String search() {
        return "SELECT * FROM Destinatarios WHERE codigo_identificacion = '" + identificationCode + "'";
    }

    public String getAllAddressees() {
        return "SELECT * FROM Destinatarios";
    }

    public boolean areEmptyFields() {
        if(identificationCode.equals("") || name.equals("") ||  curp.equals("") || rfn.equals("") || residenceDestiny.equals("") || population.equals("") || town.equals("") || entity.equals("") || residence.equals(""))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return identificationCode + " " + name + " " + curp + " " + rfn + " " + residenceDestiny + " " + population + " " + town + " " + entity + " " + residence;
    }

}
