package prog3.colaprioridades.usos;

import org.jetbrains.annotations.NotNull;

public class Documento implements Comparable<Documento> {
    private String texto;

    public Documento(String texto) {
        this.texto = texto;
    }

    public Documento() {
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "texto='" + texto + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NotNull Documento o) {
        return Integer.compare(this.getTexto().length(), o.getTexto().length());
    }
}
