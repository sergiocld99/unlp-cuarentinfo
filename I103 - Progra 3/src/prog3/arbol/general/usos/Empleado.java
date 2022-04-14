package prog3.arbol.general.usos;

import java.util.Objects;

public class Empleado {
    private String nombre;
    private int antiguedad;
    private int categoria;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Empleado empleado = (Empleado) o;

        return this.getNombre().equals(empleado.getNombre())
                && this.getCategoria() == empleado.getCategoria()
                && this.getAntiguedad() == empleado.getAntiguedad();
    }

}
