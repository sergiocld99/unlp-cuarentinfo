package prog3.grafos.util.adicionales;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class EjercicioGrafonia {

    /* ENUNCIADO
     Juan Carlos Verticio es un artista que vive en la ciudad de Grafonia, y
     entre sus pasiones personales está la de hacer dibujos de grafos, y recorrer
     la ciudad en bicicleta. Verticio desea entrenar para la final de ciclistas 2012,
     patrocinada por la División de Finanzas y Seguridad (DFS).
     Entre sus planes de entrenamiento, Verticio desea armar una serie de
     recorridos por la ciudad. A fin de optimizar su tiempo en sus ratos libres en
     la semana, él quiere poder salir y hacer un recorrido entre dos esquinas de la ciudad.
     Para ello necesita conocer cuáles caminos tienen exactamente una determinada longitud entre dos esquinas.
     El camino no puede terminar a mitad de cuadra.
     Por ejemplo, todos los caminos de la ciudad que entre dos esquinas miden 21 km.
     A partir de un grafo dirigido6 que representa la ciudad de Grafonia,
     realizar una función en Java que le permita a Verticio obtener una lista de todos
     los caminos de la ciudad con una longitud específica.
     Las aristas del grafo tienen todas valores positivos, siendo la distancia de las diferentes calles
     de la ciudad, medidas en Km como números enteros. Los vértices del grafo
     contienen un String que representa la esquina de la ciudad.
     */

    public ListaGenerica<ListaGenerica<String>> caminosDeLongitud(Grafo<String> ciudad, int longKm){
        ListaGenerica<ListaGenerica<String>> result = new ListaGenericaEnlazada<>();

        if (ciudad != null && !ciudad.esVacio()){
            ListaGenerica<Vertice<String>> esquinas = ciudad.listaDeVertices();
            ListaGenerica<String> caminoTemp = new ListaGenericaEnlazada<>();
            boolean[] visitados = new boolean[esquinas.tamanio()];

            esquinas.comenzar();
            while (!esquinas.fin()){
                this.dfs(ciudad, esquinas.proximo(), result, caminoTemp, visitados, longKm);
            }
        }

        return result;
    }

    private void dfs(Grafo<String> g, Vertice<String> esqAct, ListaGenerica<ListaGenerica<String>> l,
                     ListaGenerica<String> temp, boolean[] visited, int km){
        // acciones iniciales comunes
        visited[esqAct.posicion()] = true;
        temp.agregarInicio(esqAct.dato());

        // análisis del caso
        if (km <= 0){
            // sólo agrego si coincide exactamente
            if (km == 0) this.agregarCamino(l, temp);
        } else {
            ListaGenerica<Arista<String>> cuadras = g.listaDeAdyacentes(esqAct);
            Arista<String> arista;

            cuadras.comenzar();
            while (!cuadras.fin()){
                arista = cuadras.proximo();
                if (!visited[arista.verticeDestino().posicion()])
                    this.dfs(g, arista.verticeDestino(), l, temp, visited, km - arista.peso());
            }
        }

        // acciones finales comunes
        temp.eliminarEn(0);
        visited[esqAct.posicion()] = false;
    }

    // la lista temporal tiene un camino correcto, pero invertido
    private void agregarCamino(ListaGenerica<ListaGenerica<String>> l, ListaGenerica<String> temp){
        ListaGenerica<String> camino = new ListaGenericaEnlazada<>();
        temp.comenzar();
        while (!temp.fin())
            camino.agregarInicio(temp.proximo());

        l.agregarFinal(camino);
    }
}
