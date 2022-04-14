public class Ciudad {
    private String nombre;
    private int fase;

    public Ciudad() {
    }

    public Ciudad(String nombre, int fase) {
        this.nombre = nombre;
        this.fase = fase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Ciudad)
            return ((Ciudad) o).getNombre().equals(this.getNombre());
        else return false;
    }
}
