package prog3.grafos.util.adicionales;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Parcial_2011_01 {

    /*
    Se cuenta con un grafo con ciudades conectadas por rutas. Cada ciudad tiene una cantidad de días
    que deben ocuparse para visitarla completamente (ni más ni menos). Implemente un método que devuelva
    un itinerario de ciudades que ocupe exactamente una cantidad de días pasado por parámetro.
     */

    /** DEVUELVE UNA LISTA CUALQUIERA DE CIUDADES QUE TARDE UNA CANTIDAD DE DÍAS EN VISITARLAS A TODAS */
    public ListaGenerica<Ciudad> obtenerItinerario(Grafo<Ciudad> grafo, int dias){
        ListaGenerica<Ciudad> result = new ListaGenericaEnlazada<>();

        if (grafo != null && !grafo.esVacio()){
            ListaGenerica<Vertice<Ciudad>> ciudades = grafo.listaDeVertices();
            ListaGenerica<Ciudad> listaAux = new ListaGenericaEnlazada<>();
            boolean[] marcas = new boolean[ciudades.tamanio()];     // todo en false
            boolean corte = false;

            ciudades.comenzar();
            while (!ciudades.fin() && !corte){
                corte = this.dfs(grafo, result, listaAux, ciudades.proximo(), marcas, dias);
            }
        }

        return result;
    }

    private boolean dfs(Grafo<Ciudad> grafo, ListaGenerica<Ciudad> result, ListaGenerica<Ciudad> caminoTemp,
                     Vertice<Ciudad> ciudadAct, boolean[] visitados, int diasRestantes){
        // acciones iniciales comunes
        visitados[ciudadAct.posicion()] = true;
        caminoTemp.agregarInicio(ciudadAct.dato());
        diasRestantes -= ciudadAct.dato().getDiasDeVisita();
        boolean encontre = false;

        // caso base: itinerario de días exactos
        if (diasRestantes == 0){
            this.copiarInvertido(caminoTemp, result);
            encontre = true;
        } else if (diasRestantes > 0) {
            ListaGenerica<Arista<Ciudad>> adyacencias = grafo.listaDeAdyacentes(ciudadAct);
            Vertice<Ciudad> ciudadProx;

            adyacencias.comenzar();
            while (!adyacencias.fin() && !encontre){
                ciudadProx = adyacencias.proximo().verticeDestino();
                if (!visitados[ciudadProx.posicion()])
                    encontre = this.dfs(grafo, result, caminoTemp, ciudadProx, visitados, diasRestantes);
            }
        }

        // acciones finales comunes
        caminoTemp.eliminarEn(0);
        visitados[ciudadAct.posicion()] = false;
        return encontre;
    }

    /** RECIBE UNA LISTA CON EL NUEVO CAMINO MÍNIMO, PERO INVERTIDO, Y ACTUALIZA EL RESULTADO
     SE ASUME QUE LA LISTA RESULTANTE ESTÁ VACÍA (SE DEVUELVE UN CAMINO CUALQUIERA QUE CUMPLA) */
    private void copiarInvertido(ListaGenerica<Ciudad> fuente, ListaGenerica<Ciudad> dest){
        fuente.comenzar();
        while (!fuente.fin())
            dest.agregarInicio(fuente.proximo());
    }
}
