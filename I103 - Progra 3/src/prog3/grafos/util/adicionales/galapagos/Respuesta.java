package prog3.grafos.util.adicionales.galapagos;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Respuesta {
    private String nombreOrigen;
    private ListaGenerica<String> listaRecorrido;

    public Respuesta() {
        this.listaRecorrido = new ListaGenericaEnlazada<>();
    }

    public Respuesta(String nombreOrigen, ListaGenerica<String> listaRecorrido) {
        this.nombreOrigen = nombreOrigen;
        this.listaRecorrido = listaRecorrido;
    }

    public String getNombreOrigen() {
        return nombreOrigen;
    }

    public void setNombreOrigen(String nombreOrigen) {
        this.nombreOrigen = nombreOrigen;
    }

    public ListaGenerica<String> getListaRecorrido() {
        return listaRecorrido;
    }

    public void setListaRecorrido(ListaGenerica<String> listaRecorrido) {
        this.listaRecorrido = listaRecorrido;
    }
}
