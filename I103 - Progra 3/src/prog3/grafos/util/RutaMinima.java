package prog3.grafos.util;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class RutaMinima {
    private final ListaGenerica<String> lista;
    private boolean boletoUnico;

    public RutaMinima() {
        this.lista = new ListaGenericaEnlazada<>();
        this.boletoUnico = true;
    }

    /** MODO: POSTORDEN */
    public void agregar(String dato){
        this.lista.agregarInicio(dato);
    }

    public boolean existeCamino(){
        return !lista.esVacia();
    }

    /** DESCARTAR ANTERIOR Y COPIAR NUEVO EN ORDEN */
    public void reemplazar(RutaMinima nuevaRuta){
        ListaGenerica<String> nueva = nuevaRuta.getLista();
        this.setBoletoUnico(nuevaRuta.isBoletoUnico());
        while (!lista.fin()) lista.eliminarEn(0);
        while (!nueva.fin()) lista.agregarFinal(nueva.proximo());
    }

    public ListaGenerica<String> getLista() {
        return lista;
    }

    public boolean isBoletoUnico() {
        return this.boletoUnico;
    }

    public void setBoletoUnico(boolean boletoUnico) {
        this.boletoUnico = boletoUnico;
    }
}
