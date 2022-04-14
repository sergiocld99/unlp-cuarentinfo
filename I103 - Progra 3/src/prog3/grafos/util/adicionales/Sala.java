package prog3.grafos.util.adicionales;

public class Sala {
    private String nombre;
    private int minutos;

    public Sala(){}

    public Sala(String nombre, int minutos) {
        this.nombre = nombre;
        this.minutos = minutos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Sala){
            return this.getNombre().equals(((Sala) o).getNombre());
        } else return false;
    }
}
