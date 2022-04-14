package prog3.grafos.util;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class GuiaDeTurismo {

    public ListaGenerica<String> caminoConMenorNrodeViajes(Grafo<String> grafo, String pOrigen, String pDest){
        ListaGenerica<String> result = new ListaGenericaEnlazada<>();

        if (grafo != null && !grafo.esVacio()){
            ListaGenerica<Vertice<String>> vertices = grafo.listaDeVertices();
            Vertice<String> aux, vo = null, vd = null;

            vertices.comenzar();
            while (!vertices.fin() && (vo == null || vd == null)){
                aux = vertices.proximo();
                if (aux.dato().equals(pOrigen)) vo = aux;
                if (aux.dato().equals(pDest)) vd = aux;
            }

            if (vo != null && vd != null){
                boolean[] visited = new boolean[vertices.tamanio()];        // todo en false
                int max = this.dfs(grafo, vo, vd, result, visited);
                if (max > -1) result.agregarFinal(pDest);
            }
        }

        return result;
    }

    private int dfs(Grafo<String> grafo, Vertice<String> o, Vertice<String> d, ListaGenerica<String> l, boolean[] v){
        if (v[o.posicion()]) return -1;
        if (o.equals(d)) return 0;
        v[o.posicion()] = true;

        int maxP = -1, maxAct;
        ListaGenerica<Arista<String>> aristas = grafo.listaDeAdyacentes(o);
        ListaGenerica<String> candidatos;
        Arista<String> actual;

        aristas.comenzar();
        while (!aristas.fin()){
            actual = aristas.proximo();
            candidatos = new ListaGenericaEnlazada<>();
            maxAct = this.dfs(grafo, actual.verticeDestino(), d, candidatos, v);
            if (maxAct == 0) maxP = Math.max(maxP, actual.peso());       // adyacente es destino
            else if (maxAct > 0){
                maxAct = Math.min(actual.peso(), maxAct);   // quedarme con el max permitido
                if (maxAct > maxP){
                    maxP = maxAct;
                    candidatos.comenzar();
                    while (!l.esVacia()) l.eliminarEn(0);   // descartar anterior
                    while (!candidatos.fin()) l.agregarFinal(candidatos.proximo());
                }
            }
        }

        if (maxP > 0) l.agregarInicio(o.dato());
        v[o.posicion()] = false;
        return maxP;
    }
}
