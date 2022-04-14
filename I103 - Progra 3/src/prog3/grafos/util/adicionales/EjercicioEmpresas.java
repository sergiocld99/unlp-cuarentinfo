package prog3.grafos.util.adicionales;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

// TODO: FUNCIONARÍA BIEN, PERO ES INEFICIENTE
public class EjercicioEmpresas {

    public ListaGenerica<String> mejoresEmpresas(Grafo<String> red){
        ListaGenerica<String> result = new ListaGenericaEnlazada<>();

        if (red != null && !red.esVacio()){
            ListaGenerica<Vertice<String>> empresas = red.listaDeVertices();
            Vertice<String> aux;

            boolean[] visitados = new boolean[empresas.tamanio()]; // todo en false
            int[] cantidades = new int[empresas.tamanio()];

            // dfs para cada empresa (es imposible en menos ??)
            empresas.comenzar();
            while (!empresas.fin()){
                aux = empresas.proximo();
                cantidades[aux.posicion()] = this.dfs(red, aux, visitados);
            }

            // encontrar los 5 máximos
            for (int i=0; i<5 && i<empresas.tamanio(); i++){
                int max = -1, indMax = -1;
                // busco máximo actual
                for (int j=0; j<empresas.tamanio(); j++){
                    if (cantidades[j] > max){
                        max = cantidades[j];
                        indMax = j;
                    }
                }
                // agrego empresa a resultado
                result.agregarFinal(red.vertice(indMax).dato());
                // lo elimino para prox pasada
                cantidades[indMax] = -1;
            }
        }

        return result;
    }

    // devuelve la cantidad de empresas alcanzadas por el vértice "V"
    private int dfs(Grafo<String> g, Vertice<String> v, boolean[] visited){
        int nuevosAlcanzados = 0;

        // me visito
        visited[v.posicion()] = true;

        // recursión por adyacencias
        ListaGenerica<Arista<String>> aristas = g.listaDeAdyacentes(v);
        Arista<String> aux;

        aristas.proximo();
        while (!aristas.fin()){
            aux = aristas.proximo();
            if (!visited[aux.verticeDestino().posicion()]){
                nuevosAlcanzados += this.dfs(g, aux.verticeDestino(), visited);
            }
        }

        // me desmarco (siempre)
        visited[v.posicion()] = false;
        return nuevosAlcanzados;
    }

}
