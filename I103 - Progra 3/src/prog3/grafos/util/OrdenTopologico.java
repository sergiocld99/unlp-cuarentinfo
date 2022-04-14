package prog3.grafos.util;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;
import prog3.util.PilaGenerica;

public class OrdenTopologico<T> {
    public ListaGenerica<Vertice<T>> ordenTopologico(Grafo<T> grafo){
        ListaGenerica<Vertice<T>> result, vertices;
        result = new ListaGenericaEnlazada<>();

        if (grafo != null && !grafo.esVacio()) {
            vertices = grafo.listaDeVertices();
            boolean[] visitados = new boolean[vertices.tamanio()];      // todo en false
            PilaGenerica<Vertice<T>> pila = new PilaGenerica<>();

            vertices.comenzar();
            while (!vertices.fin())
                this.dfs(grafo, vertices.proximo(), pila, visitados);

            while (!pila.esVacia())
                result.agregarFinal(pila.desapilar());
        }

        return result;
    }

    // ordenacion version 3 - recorrido en profundidad usando una pila
    private void dfs(Grafo<T> g, Vertice<T> v, PilaGenerica<Vertice<T>> p, boolean[] visitados){
        if (visitados[v.posicion()]) return;
        visitados[v.posicion()] = true;

        // post orden
        ListaGenerica<Arista<T>> aristas = g.listaDeAdyacentes(v);
        aristas.comenzar();

        while (!aristas.fin())
            this.dfs(g, aristas.proximo().verticeDestino(), p, visitados);

        p.apilar(v);
        visitados[v.posicion()] = false;
    }
}
