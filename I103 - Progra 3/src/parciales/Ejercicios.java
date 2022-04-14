package parciales;

import prog3.arbol.binario.ArbolBinario;
import prog3.arbol.general.ArbolGeneral;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Ejercicios<T> {

    // test personaje
    public static void main(String[] args) {
        ArbolGeneral<Personaje> p1, p2, p3, p4, p5;
        p1 = new ArbolGeneral<>(new Personaje("Dragon", "Negro"));
        p1.agregarHijo(new ArbolGeneral<>(new Personaje("Princesa", "Cenicienta")));
        p1.agregarHijo(new ArbolGeneral<>(new Personaje("Dragon", "Rojo")));
        p1.agregarHijo(new ArbolGeneral<>(new Personaje("Animal", "Pluto")));

        p2 = new ArbolGeneral<>(new Personaje("Animal", "Cid"));
        p2.agregarHijo(new ArbolGeneral<>(new Personaje("Princesa", "La Bella")));
        p2.agregarHijo(new ArbolGeneral<>(new Personaje("Animal", "Tweety")));

        p3 = new ArbolGeneral<>(new Personaje("Animal", "Coyote"));
        p3.agregarHijo(new ArbolGeneral<>(new Personaje("Animal", "Scooby")));
        p3.agregarHijo(p1);
        p3.agregarHijo(p2);

        ListaGenerica<Personaje> resultado = new Ejercicios<Personaje>().caminoPrincesa(p3);
        System.out.println(resultado);
    }

    // todo ejercicio princesa
    // precondicion: el arbol tiene al menos un camino valido
    public ListaGenerica<Personaje> caminoPrincesa(ArbolGeneral<Personaje> arbol){
        ListaGenerica<Personaje> result = new ListaGenericaEnlazada<>();
        this.auxiliarCamino(arbol, result);
        return result;
    }

    private boolean auxiliarCamino(ArbolGeneral<Personaje> arbol, ListaGenerica<Personaje> l){
        if (arbol.getDatoRaiz().esDragon()) return false;
        if (arbol.getDatoRaiz().esPrincesa()) {
            l.agregarFinal(arbol.getDatoRaiz());
            return true;
        }

        // llegué al final y no es una princesa
        if (arbol.esHoja()) return false;

        // recursion
        ListaGenerica<ArbolGeneral<Personaje>> hijos = arbol.getHijos();
        ArbolGeneral<Personaje> actual;
        boolean esCorrecto = false;

        hijos.comenzar();
        while (!hijos.fin() && !esCorrecto){
            actual = hijos.proximo();
            esCorrecto = this.auxiliarCamino(actual, l);
            if (esCorrecto) l.agregarInicio(arbol.getDatoRaiz());
        }

        return esCorrecto;
    }


    // todo ejercicio diciembre 2012 (generalizado)
    public ListaGenerica<T> caminoMasLargo(ArbolGeneral<T> unArbol){
        ListaGenerica<T> aux, max = new ListaGenericaEnlazada<>();
        ListaGenerica<ArbolGeneral<T>> hijos = unArbol.getHijos();

        // encontrar el camino mas largo de mis hijos
        // si soy una hoja, será una lista vacía
        hijos.comenzar();
        while (!hijos.fin()){
            aux = caminoMasLargo(hijos.proximo());
            if (aux.tamanio() > max.tamanio()) max = aux;
        }

        // me añado al inicio de la lista (para respetar el orden)
        max.agregarInicio(unArbol.getDatoRaiz());

        // devolver
        return max;
    }

    // todo ejercicio junio 2012 (similar a maximaSumaVertical de binario.Utiles)
    public int maxSuma(ArbolBinario<Integer> unArbol){
        if (unArbol == null || unArbol.esVacio()) return 0;

        // delegacion por recursion (suma = 0 si no existe)
        int ret1 = maxSuma(unArbol.getHijoIzquierdo());
        int ret2 = maxSuma(unArbol.getHijoDerecho());

        // devuelvo mi valor + el mayor obtenido
        return unArbol.getDatoRaiz() + Math.max(ret1, ret2);
    }
}
