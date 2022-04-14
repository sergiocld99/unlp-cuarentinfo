package prog3.grafos.util;

import prog3.colaprioridades.MinHeap;
import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;

// SIN VERIFICAR
public class Dijkstra<T extends Comparable<T>> {

    /** Este vector almacena los datos del camino mínimo
    desde el origen v a cada uno de los restantes vértices del grafo. El vector es de dimensión igual a la cantidad
    de vértices, cada posición del mismo representa la información obtenida para el vértice con igual posición.
    Costo es un objeto que contiene el costo mínimo de acceder al vértice y la posición del Vértice por el cual hay
    que pasar previamente, a fin de poder rearmar el camino mínimo */
    public Costo[] dijkstraSinHeap(Grafo<T> grafo, Vertice<T> v){
        ListaGenerica<Vertice<T>> vertices = grafo.listaDeVertices();
        boolean[] conocidos = new boolean[vertices.tamanio()];
        Costo[] result = new Costo[vertices.tamanio()];

        // inicializar el vector
        for (Costo costo : result) {
            costo.setPosVerticePrevio(0);
            costo.setCostoMin(Integer.MAX_VALUE);
        }

        // origen
        result[v.posicion()].setCostoMin(0);

        // repetir algoritmo
        int faltanProcesar = conocidos.length;
        while (faltanProcesar > 0){
            int min = Integer.MAX_VALUE;
            int posU = -1;

            // vértice desconocido de menor distancia
            for (int i=0; i<result.length; i++){
                if (!conocidos[i] && result[i].getCostoMin() < min){
                    min = result[i].getCostoMin();
                    posU = i;
                }
            }

            // marcarlo como conocido
            conocidos[posU] = true;
            faltanProcesar--;

            // actualizo distancia de todos los adyacentes
            ListaGenerica<Arista<T>> listaW = grafo.listaDeAdyacentes(grafo.vertice(posU));
            Arista<T> arista;
            Vertice<T> w;

            listaW.comenzar();
            while (!listaW.fin()){
                arista = listaW.proximo();
                w = arista.verticeDestino();
                int distAct = result[w.posicion()].getCostoMin();
                int distCand = result[posU].getCostoMin() + arista.peso();
                if (distAct > distCand) {
                    result[w.posicion()].setCostoMin(distCand);
                    result[w.posicion()].setPosVerticePrevio(posU);
                }
            }
        }

        return result;
    }

    // SIN TERMINAR
    public Costo[] dijkstraConHeap(Grafo<T> grafo, Vertice<T> v){
        ListaGenerica<Vertice<T>> vertices = grafo.listaDeVertices();
        boolean[] procesados = new boolean[vertices.tamanio()];
        Costo[] result = new Costo[vertices.tamanio()];
        MinHeap<T> heap = new MinHeap<>();

        // inicializar el vector
        for (int i=0; i<result.length; i++){
            result[i].setPosVerticePrevio(0);
            result[i].setCostoMin(Integer.MAX_VALUE);
        }

        // origen
        result[v.posicion()].setCostoMin(0);

        return result;
    }
}
