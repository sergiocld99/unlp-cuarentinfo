package prog3.arbol.general;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class NodoGeneral<T> {

    private T dato;
    private ListaGenerica<NodoGeneral<T>> listaHijos;
    
    public NodoGeneral(T dato){

        this.dato = dato;
        listaHijos = new ListaGenericaEnlazada<NodoGeneral<T>>();
    }
    
    public T getDato(){

        return this.dato;
    }    
    
    public ListaGenerica<NodoGeneral<T>> getHijos(){

        return this.listaHijos;
    }     
    
    public void setDato(T dato){

        this.dato = dato;
    }
    
    public void setListaHijos(ListaGenerica<NodoGeneral<T>> lista){

        this.listaHijos = lista;
    }    
    
}
