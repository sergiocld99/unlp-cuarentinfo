package prog3.grafos.util.adicionales.galapagos;

public class Isla {
    private String nombre;
    private boolean tieneTortugas;

    public Isla() { }

    public Isla(String nombre, boolean tieneTortugas) {
        this.nombre = nombre;
        this.tieneTortugas = tieneTortugas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean hasTortugas() {
        return tieneTortugas;
    }

    public void setTieneTortugas(boolean tieneTortugas) {
        this.tieneTortugas = tieneTortugas;
    }
}
