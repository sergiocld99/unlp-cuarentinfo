package parciales;

public class Personaje {
    private String tipo, nombre;

    public Personaje(String tipo, String nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public Personaje() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean esDragon(){
        return this.getTipo().equals("Dragon");
    }

    public boolean esPrincesa(){
        return this.getTipo().equals("Princesa");
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "tipo='" + tipo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
