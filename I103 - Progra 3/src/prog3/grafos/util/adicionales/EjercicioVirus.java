package prog3.grafos.util.adicionales;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.util.ColaGenerica;

public class EjercicioVirus {

    /* ENUNCIADO
     Un poderoso e inteligente virus de computadora infecta cualquier computadora en 1 minuto,
     logrando infectar toda la red de una empresa con cientos de computadoras.
     Dado un grafo que representa las conexiones entre las computadoras de la empresa,
     y una computadora ya infectada, escriba un programa en Java que permita determinar el tiempo que
     demora el virus en infectar el resto de las computadoras.
     Asuma que todas las computadoras pueden ser infectadas, no todas las computadoras tienen conexión
     directa entre sí, y un mismo virus puede infectar un grupo de computadoras al mismo tiempo sin importar
     la cantidad. Por ejemplo, si la computadora A se conecta con la B, y la B solo con la
     C y D, el tiempo total desde la A será de dos minutos: la A ya está infectada,
     un minuto para la B, y un minuto para la C y D (ambas C y D al mismo tiempo).
     */

    public int tiempoInfeccion(Grafo<String> red, String compuInfectada){
        if (red == null || red.esVacio() || compuInfectada == null) return -1;

        // busco el vértice origen
        ListaGenerica<Vertice<String>> computadoras = red.listaDeVertices();
        Vertice<String> actual, origen = null;

        computadoras.comenzar();
        while (!computadoras.fin() && origen == null){
            actual = computadoras.proximo();
            if (actual.dato().equals(compuInfectada))
                origen = actual;
        }

        // disparo bfs desde el origen
        if (origen == null) return -1;
        else {
            ColaGenerica<Vertice<String>> cola = new ColaGenerica<>();
            ListaGenerica<Arista<String>> adyacencias;
            Arista<String> arista;

            boolean[] infectados = new boolean[computadoras.tamanio()];
            int tiempo = 0;     // equivalente al nivel

            // computadora ya infectada
            cola.encolar(origen);
            cola.encolar(null);

            // asumimos grafo conexo (todas las computadoras son alcanzables)
            while (!cola.esVacia()){
                actual = cola.desencolar();
                if (actual != null){
                    infectados[actual.posicion()] = true;
                    adyacencias = red.listaDeAdyacentes(actual);
                    adyacencias.comenzar();
                    while (!adyacencias.fin()){
                        arista = adyacencias.proximo();
                        if (!infectados[arista.verticeDestino().posicion()])
                            cola.encolar(arista.verticeDestino());
                    }
                } else if (!cola.esVacia()){
                    cola.encolar(null);
                    tiempo++;
                }
            }

            return tiempo;
        }
    }

}
