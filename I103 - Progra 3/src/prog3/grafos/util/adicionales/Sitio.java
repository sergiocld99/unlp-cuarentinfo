package prog3.grafos.util.adicionales;

public class Sitio {
    private String nombre;
    private boolean tieneMafia;

    public Sitio() {
    }

    public Sitio(String nombre, boolean tieneMafia) {
        this.nombre = nombre;
        this.tieneMafia = tieneMafia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isTieneMafia() {
        return tieneMafia;
    }

    public void setTieneMafia(boolean tieneMafia) {
        this.tieneMafia = tieneMafia;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Sitio)
            return ((Sitio) o).getNombre().equals(this.getNombre());
        return false;
    }
}
