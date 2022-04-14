package prog3.grafos.util;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;

public class Floyd<T> {

    public Costo[][] floyd(Grafo<T> grafo){
        if (grafo == null) return null;

        ListaGenerica<Vertice<T>> vertices = grafo.listaDeVertices();
        Costo[][] M = new Costo[vertices.tamanio()][vertices.tamanio()];

        // inicializar la matriz
        for (int i=0; i<vertices.tamanio(); i++) {
            for (int j=0; j<vertices.tamanio(); j++) {
                if (i == j) M[i][j] = new Costo(0, 0);
                else M[i][j] = new Costo(9999, 0);
            }
        }

        // agregar adyacencias
        ListaGenerica<Arista<T>> aristas;
        Vertice<T> v;
        Arista<T> a;

        vertices.comenzar();
        while (!vertices.fin()){
            v = vertices.proximo();
            aristas = grafo.listaDeAdyacentes(v);
            aristas.comenzar();
            while (!aristas.fin()){
                a = aristas.proximo();
                M[v.posicion()][a.verticeDestino().posicion()] = new Costo(a.peso(), v.posicion());
            }
        }

        // algoritmo iterativo de O(|V|^3)
        for (int k=0; k<vertices.tamanio(); k++){       // vértice intermedio
            for (int i=0; i<vertices.tamanio(); i++){       // vértice origen
                for (int j=0; j<vertices.tamanio(); j++) {      // vértice destino
                    if (M[i][j].getCostoMin() > M[i][k].getCostoMin() + M[k][j].getCostoMin()) {
                        M[i][j].setCostoMin(M[i][k].getCostoMin() + M[k][j].getCostoMin());
                        M[i][j].setPosVerticePrevio(k);
                    }
                }
            }
        }

        /*// solamente lo solicitado
        for (int k=0; k<vertices.tamanio(); k++){       // vértice intermedio
            for (int i=1; i<vertices.tamanio(); i++){       // vértice origen
                if (M[i][0].getCostoMin() > M[i][k].getCostoMin() + M[k][0].getCostoMin()) {
                    M[i][0].setCostoMin(M[i][k].getCostoMin() + M[k][0].getCostoMin());
                    M[i][0].setPosVerticePrevio(k);
                }
            }
        }*/

        return M;
    }

}
