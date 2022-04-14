package prog3.grafos.util.adicionales;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.util.ColaGenerica;

public class EjercicioCompuertas {

    /* ENUNCIADO
    En un circuito electrónico digital la electricidad viaja a la velocidad de la luz. Sin embargo, las
    diferentes compuertas tienen un tiempo de demora entre que la señal llega por sus entradas y se genera
    el resultado. Si el circuito está mal diseñado, cuando se conectan varias compuertas entre sí ocurre
    lo que se llama una condición de carrera, donde las dos señales que le llegan a una compuerta NAND lo hacen
    a diferente tiempo, y la salida de la compuerta tardará en estabilizarse. Asuma que tiempo que demoran las
    señales en viajar por los cables es cero, y el tiempo que demoran las señales en atravesar una
    compuerta es el mismo para todas. Se tiene un grafo que representa el circuito electrónico, y una única
    entrada que generan una señal. Hay tres tipos de nodos: el nodo entrada que genera las señales, los nodos
    de salida que reciben las señales, y los nodos de compuertas, que siempre tienen dos aristas de entrada y
    una arista de salida. */

    public boolean tieneCarrera(Grafo<String> circuito){
        boolean result = false;

        if (circuito != null && !circuito.esVacio()){
            ListaGenerica<Vertice<String>> nodos = circuito.listaDeVertices();
            Vertice<String> act, nodoE = null;

            // entrada
            nodos.comenzar();
            while (!nodos.fin() && nodoE == null){
                act = nodos.proximo();
                if (act.dato().equals("Entrada"))
                    nodoE = act;
            }

            // no hay salida única
            if (nodoE != null){
                int[] marcas = new int[nodos.tamanio()];    // todo en 0
                result = this.tieneCarrera(circuito, nodoE, marcas);
            }
        }

        return result;
    }

    // asumimos grafo dirigido (fuertemente conexo??) y acíclico
    // todas las compuertas están del nivel 1 en adelante
    private boolean tieneCarrera(Grafo<String> grafo, Vertice<String> entrada, int[] marcas){
        ColaGenerica<Vertice<String>> cola = new ColaGenerica<>();
        ListaGenerica<Arista<String>> adyacencias;
        Vertice<String> actual;
        boolean ok = true;
        int nivel = 0;

        // encolar nivel 0
        cola.encolar(entrada);
        cola.encolar(null);

        // procesar por niveles
        while (!cola.esVacia() && ok){
            actual = cola.desencolar();
            if (actual != null){
                ok = this.verificarCompuerta(actual, marcas, nivel);
                adyacencias = grafo.listaDeAdyacentes(actual);
                adyacencias.comenzar();
                while (!adyacencias.fin())
                    cola.encolar(adyacencias.proximo().verticeDestino());
            } else if (!cola.esVacia()){
                cola.encolar(null);
                nivel++;
            }
        }

        return !ok;
    }

    private boolean verificarCompuerta(Vertice<String> comp, int[] marcas, int nivel){
        if (marcas[comp.posicion()] == 0){
            marcas[comp.posicion()] = nivel;
        } else return marcas[comp.posicion()] == nivel;

        return true;
    }

}
