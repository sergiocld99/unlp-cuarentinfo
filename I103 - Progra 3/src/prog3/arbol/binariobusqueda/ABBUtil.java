package prog3.arbol.binariobusqueda;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class ABBUtil {

    // todo funcionando :)
    // EJERCICIO 3: devolver ordenado mediante in orden
    public ListaGenerica<Integer> menoresOrdenados(ArbolBinarioDeBusqueda<Integer> abb, int num){
        ListaGenerica<Integer> result = new ListaGenericaEnlazada<>();

        if (abb != null && !abb.esVacio()) this.menoresOrdenados(result, abb, num);
        return result;
    }

    // todo: abb no es vacio
    private void menoresOrdenados(ListaGenerica<Integer> l, ArbolBinarioDeBusqueda<Integer> abb, int num){
        if (abb.getDatoRaiz() < num){
            // el nodo actual es menor, ambos hijos podrían cumplir la condición
            if (!abb.getHijoIzquierdo().esVacio()) this.menoresOrdenados(l, abb.getHijoIzquierdo(), num);
            l.agregarFinal(abb.getDatoRaiz());
            if (!abb.getHijoDerecho().esVacio()) this.menoresOrdenados(l, abb.getHijoDerecho(), num);
        } else {
            // el nodo actual es igual o mayor, solo obtendré menores por la izquierda
            if (!abb.getHijoIzquierdo().esVacio()) this.menoresOrdenados(l, abb.getHijoIzquierdo(), num);
        }
    }


    // todo funcionando, por suerte :)
    // EJERCICIO 4: devolver una lista con todos los caminos posibles que den una suma determinada
    public ListaGenerica<ListaGenerica<Integer>> sumaCaminos(ArbolBinarioDeBusqueda<Integer> abb, int objetivo){
        ListaGenerica<ListaGenerica<Integer>> result = new ListaGenericaEnlazada<>();
        result.agregarInicio(new ListaGenericaEnlazada<>());

        if (abb != null && !abb.esVacio()) this.sumaCaminos(result, abb, 0, objetivo);
        return result;
    }

    private void sumaCaminos(ListaGenerica<ListaGenerica<Integer>> l, ArbolBinarioDeBusqueda<Integer> abb, int suma, int obj){
        l.elemento(0).agregarFinal(abb.getDatoRaiz());
        suma += abb.getDatoRaiz();

        // CASO BASE 1: AL SUMAR DIO JUSTO EL VALOR OBJETIVO
        if (suma == obj) return;

        // CASO BASE 2: AL SUMAR ME PASÉ DEL OBJETIVO, O BIEN, ME FALTA PERO SE ACABÓ EL CAMINO
        if (suma > obj || abb.esHoja()){
            l.eliminarEn(0);
            return;
        }

        // RECURSIÓN: si tiene ambos hijos, debería crear una copia (bifurcación)
        if (!abb.getHijoIzquierdo().esVacio() && !abb.getHijoDerecho().esVacio()){
            ListaGenerica<Integer> copia = new ListaGenericaEnlazada<>();
            ListaGenerica<Integer> caminoAct = l.elemento(0);
            caminoAct.comenzar();
            while (!caminoAct.fin()) copia.agregarFinal(caminoAct.proximo());
            this.sumaCaminos(l, abb.getHijoDerecho(), suma, obj);
            l.agregarInicio(copia);
            this.sumaCaminos(l, abb.getHijoIzquierdo(), suma, obj);
        } else {
            // solo tiene un hijo, continuar por ahi
            if (abb.getHijoIzquierdo().esVacio()) this.sumaCaminos(l, abb.getHijoDerecho(), suma, obj);
            else this.sumaCaminos(l, abb.getHijoIzquierdo(), suma, obj);
        }
    }


    // todo funcionando, supuestamente...
    // EJERCICIO 5: devolver una lista con el único camino a un nodo (suponemos que existe), aplicando NEG a los hijos izquierda
    public ListaGenerica<Integer> caminoSegunLado(ArbolBinarioDeBusqueda<Integer> abb, int num){
        ListaGenerica<Integer> result = new ListaGenericaEnlazada<>();

        if (abb != null && !abb.esVacio()) this.caminoSegunLado(result, abb, num, false);
        return result;
    }

    private void caminoSegunLado(ListaGenerica<Integer> l, ArbolBinarioDeBusqueda<Integer> abb, int objetivo, boolean izq){
        if (izq) l.agregarFinal(abb.getDatoRaiz() * (-1));
        else l.agregarFinal(abb.getDatoRaiz());

        if (objetivo > abb.getDatoRaiz()) this.caminoSegunLado(l, abb.getHijoDerecho(), objetivo, false);
        else if (objetivo < abb.getDatoRaiz()) this.caminoSegunLado(l, abb.getHijoIzquierdo(), objetivo, true);
    }
}
