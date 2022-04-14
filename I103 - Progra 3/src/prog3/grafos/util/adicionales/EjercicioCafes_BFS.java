package prog3.grafos.util.adicionales;

import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;
import prog3.util.ColaGenerica;

public class EjercicioCafes_BFS {

    public ListaGenerica<String> esquinasValiosas(Grafo<String> ciudad, int x, int y){
        ListaGenerica<String> result = new ListaGenericaEnlazada<>();

        if (ciudad != null && !ciudad.esVacio()){
            ListaGenerica<Vertice<String>> esquinas = ciudad.listaDeVertices();

            esquinas.comenzar();
            while (!esquinas.fin()){
                Vertice<String> aux = esquinas.proximo();
                ColaGenerica<Vertice<String>> cola = new ColaGenerica<>();
                boolean[] visitados = new boolean[esquinas.tamanio()];
                int cafesEncontrados = 0, dx = x;

                // nivel 0 (esquina origen)
                cola.encolar(aux);
                cola.encolar(null);
            }
        }

        return result;
    }

}
