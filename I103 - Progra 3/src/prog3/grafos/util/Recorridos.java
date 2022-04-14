package prog3.grafos.util;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;
import prog3.util.ColaGenerica;

public class Recorridos<T> {

    public ListaGenerica<Vertice<T>> dfs(Grafo<T> grafo){
        ListaGenerica<Vertice<T>> result = new ListaGenericaEnlazada<>();

        if (grafo != null && !grafo.esVacio()){
            ListaGenerica<Vertice<T>> vertices = grafo.listaDeVertices();
            boolean[] visitados = new boolean[vertices.tamanio()];      // todo en false

            vertices.comenzar();
            while (!vertices.fin() && result.tamanio() < vertices.tamanio())
                this.dfs(grafo, vertices.proximo(), result, visitados);
        }

        return result;
    }

    // todo preorden - ninguno de los parámetros es nulo
    private void dfs(Grafo<T> g, Vertice<T> v, ListaGenerica<Vertice<T>> l, boolean[] visited){
        // si ya fui visitado, no hago nada
        if (visited[v.posicion()]) return;

        // me visito y agrego a la lista
        visited[v.posicion()] = true;
        l.agregarFinal(v);

        // para cada uno de mis adyacentes
        ListaGenerica<Arista<T>> aristas = g.listaDeAdyacentes(v);
        aristas.comenzar();

        while (!aristas.fin())
            this.dfs(g, aristas.proximo().verticeDestino(), l, visited);
    }


    public ListaGenerica<Vertice<T>> bfs(Grafo<T> grafo){
        ListaGenerica<Vertice<T>> result = new ListaGenericaEnlazada<>();

        if (grafo != null && !grafo.esVacio()){
            ListaGenerica<Vertice<T>> vertices = grafo.listaDeVertices();
            ColaGenerica<Vertice<T>> cola = new ColaGenerica<>();
            boolean[] visitados = new boolean[vertices.tamanio()];      // todo en false

            // variables auxiliares
            ListaGenerica<Arista<T>> aristas;
            Vertice<T> aux;

            // para cada vértice del grafo
            vertices.comenzar();
            while (!vertices.fin() && result.tamanio() < vertices.tamanio()){
                cola.encolar(vertices.proximo());
                cola.encolar(null);

                // todo por nivel
                while (!cola.esVacia()){
                    aux = cola.desencolar();
                    if (aux != null) {
                        if (!visitados[aux.posicion()]){
                            visitados[aux.posicion()] = true;       // me visito
                            result.agregarFinal(aux);               // me agrego a la lista
                            aristas = grafo.listaDeAdyacentes(aux);     // encolo mis adyacentes
                            aristas.comenzar();
                            while (!aristas.fin())
                                cola.encolar(aristas.proximo().verticeDestino());
                        }
                    } else if (!cola.esVacia())
                        cola.encolar(null);
                }
            }
        }

        return result;
    }

}
