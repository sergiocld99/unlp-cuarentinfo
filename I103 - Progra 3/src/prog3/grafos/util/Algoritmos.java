package prog3.grafos.util;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;

public class Algoritmos<T> {

    /** MÉTODOS PÚBLICOS */
    public boolean subgrafoCuadrado(Grafo<T> grafo){
        if (grafo == null || grafo.esVacio()) return false;

        ListaGenerica<Vertice<T>> vertices = grafo.listaDeVertices();
        boolean[] marcas = new boolean[vertices.tamanio()];     // todo en false

        // ir buscando
        boolean encontrado = false;
        vertices.comenzar();

        while (!vertices.fin() && !encontrado)
            encontrado = this.hallarCiclo(grafo, vertices.proximo(), 0, 4, marcas);

        return encontrado;
    }

    public int getGrado(Grafo<T> grafo){
        if (grafo == null) return -1;
        if (grafo.esVacio()) return 0;

        // todos los vértices
        ListaGenerica<Vertice<T>> vertices = grafo.listaDeVertices();
        int[] grados = new int[vertices.tamanio()];

        // ir completando
        vertices.comenzar();
        while (!vertices.fin())
            this.grado(grafo, vertices.proximo(), grados);

        // hallar el máximo
        int max = 0, indMax = -1;
        for (int i=0; i<vertices.tamanio(); i++) {
            if (grados[i] > max){
                max = grados[i];
                indMax = i;
            }
        }

        System.out.println("El vértice de mayor grado es " + grafo.vertice(indMax).dato());
        return max;
    }

    public boolean tieneCiclo(Grafo<T> grafo){
        if (grafo == null || grafo.esVacio()) return false;

        ListaGenerica<Vertice<T>> vertices = grafo.listaDeVertices();
        boolean[] marcas = new boolean[vertices.tamanio()];     // todo en false

        // ir buscando
        boolean encontrado = false;
        vertices.comenzar();

        while (!vertices.fin() && !encontrado)
            encontrado = this.hallarCiclo(grafo, vertices.proximo(), 0, -1, marcas);

        return encontrado;
    }

    /** MÉTODO AUXILIAR PARA HALLAR CICLO DE LONGITUD PASADA POR PARÁMETRO */
    private boolean hallarCiclo(Grafo<T> grafo, Vertice<T> actual, int longAct, int longObj, boolean[] marcas){
        // verifico si encontré un ciclo
        if (marcas[actual.posicion()]){
            // reviso si es longitud objetivo
            return longObj < 0 || (longAct == longObj);
        }

        // me visito
        marcas[actual.posicion()] = true;

        // posibles caminos
        ListaGenerica<Arista<T>> aristas = grafo.listaDeAdyacentes(actual);
        aristas.comenzar();

        // ir buscando
        boolean encontrado = false;
        while (!aristas.fin() && !encontrado)
            encontrado = this.hallarCiclo(grafo, aristas.proximo().verticeDestino(), longAct+1, longObj, marcas);

        // me desmarco
        marcas[actual.posicion()] = false;

        return encontrado;
    }

    /** MÉTODO AUXILIAR PARA CALCULAR GRADO (ENTRANTES + SALIENTES) */
    private void grado(Grafo<T> grafo, Vertice<T> actual, int[] marcas){
        // si ya fui visitado, incremento grado entrante
        if (marcas[actual.posicion()] > 0){
            marcas[actual.posicion()]++;
            return;
        }

        // obtengo lista de adyacencias salientes
        ListaGenerica<Arista<T>> salientes = grafo.listaDeAdyacentes(actual);
        salientes.comenzar();

        // sumo mi grado de salientes
        marcas[actual.posicion()] += salientes.tamanio();

        // ir visitando
        while (!salientes.fin())
            this.grado(grafo, salientes.proximo().verticeDestino(), marcas);
    }
}
