import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Parcial {

    // MÉTODO SOLICITADO
    public ListaGenerica<String> resolver(Grafo<Ciudad> ciudades, String origen, String destino, int minFase){
        ListaGenerica<String> result = new ListaGenericaEnlazada<>();

        // comprobación de valores pasados por parámetro
        if (ciudades != null && !ciudades.esVacio() && origen != null && destino != null){
            ListaGenerica<Vertice<Ciudad>> vertices = ciudades.listaDeVertices();
            Vertice<Ciudad> vOrigen = null, vDest = null, aux;

            // buscar vértices origen y destino
            vertices.comenzar();
            while (!vertices.fin() && (vOrigen == null || vDest == null)){
                aux = vertices.proximo();
                if (aux.dato().getNombre().equals(origen)) vOrigen = aux;
                else if (aux.dato().getNombre().equals(destino)) vDest = aux;
            }

            // compruebo que se hayan encontrado los extremos del posible camino
            // además, para evitar un recorrido en profundidad innecesario, verifico
            // que el destino tenga una fase que cumpla con lo solicitado
            if (vOrigen != null && vDest != null && vDest.dato().getFase() > minFase){
                ListaGenerica<String> caminoAux = new ListaGenericaEnlazada<>();
                boolean[] visitados = new boolean[vertices.tamanio()];      // todo en false
                this.hallarCamino(ciudades, vOrigen, vDest, result, caminoAux, visitados, minFase);
            }
        }

        return result;
    }


    // DFS MODIFICADO
    private boolean hallarCamino(Grafo<Ciudad> grafo, Vertice<Ciudad> entrada, Vertice<Ciudad> salida,
                              ListaGenerica<String> result, ListaGenerica<String> camino,
                              boolean[] visitados, int faseMin){
        // acciones iniciales comunes
        visitados[entrada.posicion()] = true;
        camino.agregarInicio(entrada.dato().getNombre());
        boolean encontre = false;

        // circular sólo si no es menor o igual a la fase mínima requerida
        if (entrada.dato().getFase() > faseMin){
            // caso base: es ciudad destino
            if (entrada.equals(salida)){
                this.copiarInvertido(camino, result);
                encontre = true;
            } else {
                ListaGenerica<Arista<Ciudad>> adyacencias = grafo.listaDeAdyacentes(entrada);
                Arista<Ciudad> arista;

                // buscar el primer camino válido
                adyacencias.comenzar();
                while (!adyacencias.fin() && !encontre){
                    arista = adyacencias.proximo();
                    // si la ciudad no está visitada, y la ruta está liberada (peso = 0)
                    if (!visitados[arista.verticeDestino().posicion()] && arista.peso() == 0){
                        encontre = this.hallarCamino(grafo, arista.verticeDestino(),
                                salida, result, camino, visitados, faseMin);
                    }
                }
            }
        }

        // acciones finales comunes
        camino.eliminarEn(0);

        // como se pasa 1 vez por una ciudad, y no es necesario devolver un camino mínimo
        // no desmarco el vértice al retornar de la llamada recursiva

        return encontre;
    }


    // ESTO SOLO SE REALIZA UNA ÚNICA VEZ -- DESTINO ESTÁ VACÍA
    private void copiarInvertido(ListaGenerica<String> fuente, ListaGenerica<String> dest){
        fuente.comenzar();
        while (!fuente.fin())
            dest.agregarInicio(fuente.proximo());
    }

}
