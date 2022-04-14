package prog3.grafos.util.adicionales;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Parcial_2011_02 {

    /*
    Se cuenta con un grafo con ciudades conectadas por rutas. Cada ciudad tiene una cantidad de días
    que deben ocuparse para visitarla completamente (ni más ni menos). Implemente un método que devuelva
    un itinerario de ciudades que ocupe exactamente una cantidad de días pasado por parámetro, y que
    además contega la mayor cantidad de ciudades posibles (será necesario recorrer todo el grafo).
     */

    /** DEVUELVE UNA LISTA MÁXIMA DE CIUDADES QUE TARDE UNA CANTIDAD DE DÍAS EN VISITARLAS A TODAS */
    public ListaGenerica<Ciudad> obtenerItinerario(Grafo<Ciudad> grafo, int dias){
        ListaGenerica<Ciudad> result = new ListaGenericaEnlazada<>();

        if (grafo != null && !grafo.esVacio()){
            ListaGenerica<Vertice<Ciudad>> ciudades = grafo.listaDeVertices();
            ListaGenerica<Ciudad> listaAux = new ListaGenericaEnlazada<>();
            boolean[] marcas = new boolean[ciudades.tamanio()];     // todo en false

            ciudades.comenzar();
            while (!ciudades.fin()){
                this.dfs(grafo, result, listaAux, ciudades.proximo(), marcas, dias);
            }
        }

        return result;
    }

    private void dfs(Grafo<Ciudad> grafo, ListaGenerica<Ciudad> result, ListaGenerica<Ciudad> caminoTemp,
                     Vertice<Ciudad> ciudadAct, boolean[] visitados, int diasRestantes){
        // acciones iniciales comunes
        visitados[ciudadAct.posicion()] = true;
        caminoTemp.agregarInicio(ciudadAct.dato());
        diasRestantes -= ciudadAct.dato().getDiasDeVisita();

        // caso base: itinerario de días exactos
        if (diasRestantes == 0){
            if (caminoTemp.tamanio() > result.tamanio())
                this.copiarInvertido(caminoTemp, result);
        } else if (diasRestantes > 0) {
            ListaGenerica<Arista<Ciudad>> adyacencias = grafo.listaDeAdyacentes(ciudadAct);
            Vertice<Ciudad> ciudadProx;

            adyacencias.comenzar();
            while (!adyacencias.fin()){
                ciudadProx = adyacencias.proximo().verticeDestino();
                if (!visitados[ciudadProx.posicion()])
                    this.dfs(grafo, result, caminoTemp, ciudadProx, visitados, diasRestantes);
            }
        }

        // acciones finales comunes
        caminoTemp.eliminarEn(0);
        visitados[ciudadAct.posicion()] = false;
    }

    /** RECIBE UNA LISTA CON EL NUEVO CAMINO MÍNIMO, PERO INVERTIDO, Y ACTUALIZA EL RESULTADO */
    private void copiarInvertido(ListaGenerica<Ciudad> fuente, ListaGenerica<Ciudad> dest){
        while (!dest.esVacia())
            dest.eliminarEn(0);

        fuente.comenzar();
        while (!fuente.fin())
            dest.agregarInicio(fuente.proximo());
    }
}
