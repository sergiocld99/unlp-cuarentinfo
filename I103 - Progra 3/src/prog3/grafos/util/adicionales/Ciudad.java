package prog3.grafos.util.adicionales;

public class Ciudad {
    private String nombre;
    private int diasDeVisita;

    public Ciudad() {
    }

    public Ciudad(String nombre, int diasDeVisita) {
        this.nombre = nombre;
        this.diasDeVisita = diasDeVisita;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDiasDeVisita() {
        return diasDeVisita;
    }

    public void setDiasDeVisita(int diasDeVisita) {
        this.diasDeVisita = diasDeVisita;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Ciudad)
            return ((Ciudad) o).getNombre().equals(this.getNombre());
        else return false;
    }

    @Override
    public String toString() {
        return this.getNombre() + " (" + this.getDiasDeVisita() + " días)";
    }
}
