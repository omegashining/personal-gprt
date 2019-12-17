package aserradero.reports;


public class Universidad {
    private String universidad;
    private String rector;
    private String direccion;
    private Integer alumnos;

    //Obligatorio tener un constructor sin parametros.
    public Universidad(){

    }
    
    //Constructor util para setear las propiedades inicialmente.
    public Universidad(String universidad,String rector,String direccion,Integer alumnos){
            this.universidad = universidad;
            this.rector = rector;
            this.direccion = direccion;
            this.alumnos = alumnos;
    }

    public Integer getAlumnos() {
            return alumnos;
    }

    public void setAlumnos(Integer alumnos) {
            this.alumnos = alumnos;
    }

    public String getDireccion() {
            return direccion;
    }

    public void setDireccion(String direccion) {
            this.direccion = direccion;
    }

    public String getRector() {
            return rector;
    }

    public void setRector(String rector) {
            this.rector = rector;
    }

    public String getUniversidad() {
            return universidad;
    }

    public void setUniversidad(String universidad) {
            this.universidad = universidad;
    }
	
}
