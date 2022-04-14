package prog3.grafos.util;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;

public class Delta {

    public int maxIslasDistintas(Grafo<String> grafo){
        if (grafo == null) return -1;
        if (grafo.esVacio()) return 0;

        ListaGenerica<Vertice<String>> vertices = grafo.listaDeVertices();
        Vertice<String> aux, origen = null;

        vertices.comenzar();
        while (!vertices.fin() && origen == null){
            aux = vertices.proximo();
            if (esMuelle(aux))
                origen = aux;
        }

        if (origen == null) return 0;

        boolean[] visitados = new boolean[vertices.tamanio()];      // todo en false
        return this.maxIslasDistintas(grafo, origen, visitados);
    }

    public RutaMinima caminoMasCorto(Grafo<String> grafo, String islaO, String islaD){
        RutaMinima result = new RutaMinima();

        if (grafo != null && !grafo.esVacio()){
            ListaGenerica<Vertice<String>> vertices = grafo.listaDeVertices();
            Vertice<String> aux, origen = null, dest = null;

            // Busco existencia de extremos
            vertices.comenzar();
            while (!vertices.fin() && (origen == null || dest == null)){
                aux = vertices.proximo();
                if (aux.dato().equals(islaO)) origen = aux;
                if (aux.dato().equals(islaD)) dest = aux;
            }

            // Disparo un dfs
            if (origen != null && dest != null) {
                boolean[] visited = new boolean[vertices.tamanio()];
                this.caminoMasCorto(grafo, origen, dest, result, visited);
            }
        }

        return result;
    }

    /** MÉTODO AUXILIAR - INCISO 1 */
    private int maxIslasDistintas(Grafo<String> grafo, Vertice<String> v, boolean[] marcas){
        if (marcas[v.posicion()]) return -1;    // ya fue visitado en este recorrido ?
        marcas[v.posicion()] = true;            // me visito para evitar entrar en ciclos
        ListaGenerica<Arista<String>> aristas = grafo.listaDeAdyacentes(v);
        int maxRec, max = 0;

        aristas.comenzar();
        while (!aristas.fin()){
            maxRec = this.maxIslasDistintas(grafo, aristas.proximo().verticeDestino(), marcas);
            max = Math.max(max, maxRec);
        }

        marcas[v.posicion()] = false;       // me desmarco para permitir otros recorridos
        return max + 1;                     // devuelvo max por recursión + yo mismo
    }

    /** MÉTODO AUXILIAR - INCISO 2 */
    private int caminoMasCorto(Grafo<String> grafo, Vertice<String> o, Vertice<String> d, RutaMinima r, boolean[] v){
        if (o.equals(d)){
            r.agregar(o.dato());            // me agrego al posible camino
            return 0;                       // retorno longitud 0
        }

        v[o.posicion()] = true;                 // me visito para evitar entrar en ciclos
        int minRec, min = Integer.MAX_VALUE;
        ListaGenerica<Arista<String>> aristas = grafo.listaDeAdyacentes(o);
        Arista<String> actual;
        RutaMinima rutRec;

        aristas.comenzar();
        while (!aristas.fin()){
            actual = aristas.proximo();
            if (!v[actual.verticeDestino().posicion()]){
                rutRec = new RutaMinima();
                minRec = this.caminoMasCorto(grafo, actual.verticeDestino(), d, rutRec, v);
                if (minRec > -1){
                    minRec += actual.peso();
                    if (minRec < min){
                        min = minRec;
                        r.reemplazar(rutRec);
                    }
                }
            }
        }

        v[o.posicion()] = false;            // me desmarco para permitir otros recorridos
        if (r.existeCamino()) {
            if (esMuelle(o))
                r.setBoletoUnico(false);
            r.agregar(o.dato());            // me agrego al principio para respetar orden
            return min;                     // devuelvo longitud del camino más corto
        } else return -1;                   // si no hubo camino válido, r queda vacío
    }

    /** MÉTODO AUXILIAR COMÚN */
    private boolean esMuelle(Vertice<String> v){
        return v.dato().equals("MUELLE");
    }
}
