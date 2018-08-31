package lifecycle.icesi.edu.co.listas;

public class Estudiante {
    private String nombre;
    private String telefono;

    public Estudiante(String nombre, String codigo) {
        this.nombre = nombre;
        this.telefono = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
