package prog3.grafos.util.adicionales;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class EjercicioCafes_DFS<T> {

    /*
    Es sólo una moda o vino para quedarse? No se sabe, pero como parte de un plan fuerte de
expansión en Argentina el número de locales abiertos de una muy conocida cadena internacional de
café ha aumentado 50% en la primera mitad de 2013 con respecto al mismo período del año 2012.
    Al parecer las personas se han vuelto tan adictos a estas tiendas de café gourmet que los inmuebles
que están cerca de estas cafeterías obtienen mejores rentas. Esto ha sido notado por una compañía
de bienes raíces, que está interesada en identificar si una determinada esquina es un lugar valioso
en términos de su proximidad al mayor números de cafés de esta cadena.
Para ello cuentan con un mapa de la ciudad representada en un grafo donde, cada arista indica la
cantidad de cafés. Supongamos que una persona promedio está dispuesta a caminar un número de
cuadras fijo x para obtener su café matinal. Usted tiene que determinar si la esquina en cuestión es
valiosa. Es considerada valiosa si el número de cafés en los que una persona puede alcanzar es
mayor a un número fijo arbitrario y
     */

    // x: cantidad máxima de cuadras que una persona está dispuesta a caminar
    // y: cantidad a superar de cafés para que la esquina sea considerada valiosa
    // la ciudad es un grafo pesado, donde cada arista es la cantidad de cafés
    public ListaGenerica<T> esquinasValiosas(Grafo<T> ciudad, int x, int y){
        ListaGenerica<T> result = new ListaGenericaEnlazada<>();

        if (ciudad != null && !ciudad.esVacio()){
            ListaGenerica<Vertice<T>> esquinas = ciudad.listaDeVertices();
            boolean[] visitados = new boolean[esquinas.tamanio()];

            esquinas.comenzar();
            while (!esquinas.fin()){
                Vertice<T> aux = esquinas.proximo();
                if (this.esEsqValiosa(ciudad, aux, visitados, x, y))
                    result.agregarFinal(aux.dato());
            }
        }

        return result;
    }

    private boolean esEsqValiosa(Grafo<T> g, Vertice<T> v, boolean[] visited, int dx, int dy) {
        // caso base "bueno": alcancé cafés pedidos
        if (dy <= 0) return true;

        // caso base "malo": no debo caminar más
        if (dx == 0 || visited[v.posicion()])
            return false;

        // sino sigo...
        boolean cumple = false;
        visited[v.posicion()] = true;
        ListaGenerica<Arista<T>> cuadras = g.listaDeAdyacentes(v);
        Arista<T> arista;

        cuadras.comenzar();
        while (!cuadras.fin() && !cumple){
            arista = cuadras.proximo();
            cumple = this.esEsqValiosa(g, arista.verticeDestino(), visited, dx-1, dy-arista.peso());
        }

        visited[v.posicion()] = false;
        return cumple;
    }
}
