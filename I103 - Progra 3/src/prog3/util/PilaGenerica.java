package prog3.util;

import prog3.listagenerica.ListaGenericaEnlazada;

public class PilaGenerica<T> extends ListaGenericaEnlazada<T>{

    public PilaGenerica(){}

    public void apilar(T elem){
        super.agregarInicio(elem);
    }

    public T desapilar(){
        T result = this.tope();
        super.eliminarEn(0);
        return result;
    }

    public T tope(){
        return super.elemento(0);
    }

    public boolean esVacia(){
        return super.esVacia();
    }
}
