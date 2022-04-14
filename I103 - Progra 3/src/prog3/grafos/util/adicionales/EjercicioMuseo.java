package prog3.grafos.util.adicionales;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class EjercicioMuseo {

    public ListaGenerica<Sala> mejorRecorrido(Grafo<Sala> museo){
        ListaGenerica<Sala> result = new ListaGenericaEnlazada<>();
        ListaGenerica<Sala> temp = new ListaGenericaEnlazada<>();

        if (museo != null && !museo.esVacio()){
            ListaGenerica<Vertice<Sala>> salas = museo.listaDeVertices();
            Vertice<Sala> aux, origen = null;

            salas.comenzar();
            while (!salas.fin() && origen == null){
                aux = salas.proximo();
                if (aux.dato().getNombre().equals("Entrada")) origen = aux;
            }

            if (origen != null){
                boolean[] visitados = new boolean[salas.tamanio()];
                int[] maximo = new int[]{-1};
                this.dfs(museo, origen, result, temp, visitados, maximo, 0, 120);
            }
        }

        return result;
    }

    // dt: cantidad de tiempo disponible
    private void dfs(Grafo<Sala> m, Vertice<Sala> v, ListaGenerica<Sala> efectiva, ListaGenerica<Sala> cand,
                     boolean[] visited, int[] salasMax, int salasV, int dt) {

        // caso base: me quedé sin tiempo
        // lo hago acá en lugar del while porque es más probable
        // que se exceda de tiempo en una sala que en un pasillo
        dt -= v.dato().getMinutos();
        if (dt < 0){
            // actualizo resultados (antes de visitarme)
            if (salasV > salasMax[0]){
                salasMax[0] = salasV;
                this.copiar(cand, efectiva);
            }
            return;
        }

        // me visito y agrego
        visited[v.posicion()] = true;
        cand.agregarInicio(v.dato());       // al revés para evitar eliminarEn(tamanio-1)

        // adyacencias para recursión
        ListaGenerica<Arista<Sala>> aristas = m.listaDeAdyacentes(v);
        Arista<Sala> aux;

        while (!aristas.fin()){
            aux = aristas.proximo();
            // evito entrar en un ciclo (V ya visitado)
            if (!visited[aux.verticeDestino().posicion()]){
                this.dfs(m, aux.verticeDestino(), efectiva, cand, visited, salasMax,
                        salasV+1, dt-aux.peso());
            }
        }

        // restauro candidatos
        cand.eliminarEn(0);     // más eficaz que recorrido secuencial

        // me desmarco (siempre)
        visited[v.posicion()] = false;
    }

    private void copiar(ListaGenerica<Sala> candidato, ListaGenerica<Sala> dest){
        while (!dest.esVacia())
            dest.eliminarEn(0);

        // en mi implementación: candidato está al revés!!
        candidato.comenzar();
        while (!candidato.fin())
            dest.agregarInicio(candidato.proximo());
    }
}
