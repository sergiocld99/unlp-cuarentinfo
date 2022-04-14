package prog3.grafos.util;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.util.ColaGenerica;


public class GradosDeSeparacion {
    public int maximoGradoDeSeparacion(Grafo<String> grafo){
        if (grafo == null) return -1;
        if (grafo.esVacio()) return 0;

        ListaGenerica<Vertice<String>> v = grafo.listaDeVertices();
        int maxBfs, maximo = 0;

        v.comenzar();
        while (!v.fin()){
            maxBfs = this.bfs(grafo, v.proximo(), v.tamanio());
            maximo = Math.max(maxBfs, maximo);
        }

        return maximo;
    }


    /** DEVUELVE EL MÁXIMO GRADO DE SEPARACIÓN DE UNA PERSONA */
    private int bfs(Grafo<String> grafo, Vertice<String> v, int cantV){
        ColaGenerica<Vertice<String>> cola = new ColaGenerica<>();
        ListaGenerica<Arista<String>> aristas;
        Vertice<String> actual, aux;

        boolean[] visitados = new boolean[cantV];      // todo en false
        int faltanVisitar = cantV, nivel = 0;

        cola.encolar(v);
        cola.encolar(null);
        while (!cola.esVacia() && faltanVisitar > 0){
            actual = cola.desencolar();
            if (actual != null){
                faltanVisitar--;
                visitados[actual.posicion()] = true;
                aristas = grafo.listaDeAdyacentes(actual);
                aristas.comenzar();
                while (!aristas.fin()){
                    aux = aristas.proximo().verticeDestino();
                    if (!visitados[aux.posicion()]) cola.encolar(aux);
                }
            } else if (!cola.esVacia()){
                cola.encolar(null);
                nivel++;
            }
        }

        return (faltanVisitar > 0) ? -1 : nivel;
    }
}
