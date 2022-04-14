package prog3.grafos.util.adicionales.tokio;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Respuesta {
    private int distanciaMax;
    private ListaGenerica<String> listaEstaciones;

    public Respuesta() {
        this.distanciaMax = -1;
        this.listaEstaciones = new ListaGenericaEnlazada<>();
    }

    public Respuesta(int distanciaMax, ListaGenerica<String> listaEstaciones) {
        this.distanciaMax = distanciaMax;
        this.listaEstaciones = listaEstaciones;
    }

    public int getDistanciaMax() {
        return distanciaMax;
    }

    public void setDistanciaMax(int distanciaMax) {
        this.distanciaMax = distanciaMax;
    }

    public ListaGenerica<String> getListaEstaciones() {
        return listaEstaciones;
    }

    public void setListaEstaciones(ListaGenerica<String> listaEstaciones) {
        this.listaEstaciones = listaEstaciones;
    }
}
