package prog3.arbol;

import prog3.arbol.general.ArbolGeneral;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;
import prog3.util.ColaGenerica;

public class Parcial {

    /** RECORRE EL ARBOL POR NIVELES, VERIFICANDO LA CONDICION PROPUESTA */
    public ListaGenerica<Integer> resolver(ArbolGeneral<Boolean> arbol){
        ListaGenerica<Integer> result = new ListaGenericaEnlazada<>();
        ColaGenerica<ArbolGeneral<Boolean>> cola = new ColaGenerica<>();

        ListaGenerica<ArbolGeneral<Boolean>> hijos;
        ArbolGeneral<Boolean> aux;
        int nivel = 0;

        // encolar raíz y fin de nivel 0
        if (arbol != null && !arbol.esVacio()){
            cola.encolar(arbol);
            cola.encolar(null);
        }

        while (!cola.esVacia()){
            aux = cola.desencolar();
            if (aux != null){       // si no es una marca de fin de nivel
                if (this.verificarPadre(aux))   // si cumple con lo propuesto
                    result.agregarFinal(nivel);     // añado nivel actual
                else if (!aux.esHoja()) {    // si no cumplió, algún descendiente es False
                    hijos = aux.getHijos();
                    hijos.comenzar();
                    while (!hijos.fin())
                        cola.encolar(hijos.proximo());
                }
            } else
                if (!cola.esVacia()) {        // llegué a fin de nivel, pero no es el último
                    cola.encolar(null);     // nuevo fin de nivel
                    nivel++;                        // incrementar nivel
                }
        }

        return result;
    }

    /** VERIFICA SI ES FALSE y SI TODOS SUS DESCENDIENTES SON TRUE */
    private boolean verificarPadre(ArbolGeneral<Boolean> arbol) {
        // el nodo debe ser False
        if (arbol.getDatoRaiz().equals(Boolean.TRUE)) return false;

        // si no tiene hijos, no cumple la condición
        if (arbol.esHoja()) return false;
        boolean ok = true;

        // lista de hijos
        ListaGenerica<ArbolGeneral<Boolean>> hijos = arbol.getHijos();
        hijos.comenzar();

        // evaluar si todos los descendientes son True
        // si alguno no cumple, no se sigue iterando
        while (!hijos.fin() && ok){
            ok = this.verificarHijo(hijos.proximo());
        }

        return ok;
    }

    /** VERIFICA SI TODOS SON TRUE */
    private boolean verificarHijo(ArbolGeneral<Boolean> arbol){
        // debo tener valor True
        if (arbol.getDatoRaiz().equals(Boolean.FALSE)) return false;

        // si soy una hoja con valor True, es correcto
        if (arbol.esHoja()) return true;
        boolean ok = true;

        // lista de mis hijos
        ListaGenerica<ArbolGeneral<Boolean>> misHijos = arbol.getHijos();
        misHijos.comenzar();

        // evaluar si todos los descendientes siguen cumpliendo
        // si se encontró algun False, no seguir iterando
        while (!misHijos.fin() && ok){
            ok = this.verificarHijo(misHijos.proximo());
        }

        return ok;
    }

}
