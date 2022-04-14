package prog3.grafos.util;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Antenas {

    public ListaGenerica<ListaGenerica<Integer>> caminosMinimos(int[][] mapa){
        ListaGenerica<ListaGenerica<Integer>> result = new ListaGenericaEnlazada<>();
        ListaGenerica<Integer> listaMin, listaTemp = new ListaGenericaEnlazada<>();

        if (mapa != null){
            boolean[] visited = new boolean[mapa.length];   // todo en false
            // hallar todos los caminos mínimos desde antena 1 hacia las demás
            for (int i=1; i<mapa.length; i++){
                listaMin = new ListaGenericaEnlazada<>();
                int[] valorMin = new int[]{Integer.MAX_VALUE};
                this.distanciaMin(mapa, listaTemp, listaMin, visited, valorMin, 0, 0, i);
                result.agregarFinal(listaMin);
            }
        }

        return result;
    }

    private void distanciaMin(int[][] M, ListaGenerica<Integer> camino, ListaGenerica<Integer> caminoMin,
                            boolean[] visited, int[] distMin, int distAct, int posAct, final int posDest){
        // me visito y agrego
        visited[posAct] = true;
        camino.agregarInicio(posAct+1);

        // caso base: llegué a destino (última antena)
        if (posAct == posDest){
            if (distAct < distMin[0]){
                distMin[0] = distAct;
                this.copiar(camino, caminoMin);
            }
        } else {
            // caso recursivo: seguir explorando
            for (int i=1; i<M.length; i++){
                if (!visited[i] && M[posAct][i] >= 0){
                    this.distanciaMin(M, camino, caminoMin, visited, distMin,
                            distAct + M[posAct][i], i, posDest);
                }
            }
        }

        // restauro camino
        camino.eliminarEn(0);

        // me desmarco (siempre)
        visited[posAct] = false;
    }

    private void copiar(ListaGenerica<Integer> candidato, ListaGenerica<Integer> dest){
        while (!dest.esVacia())
            dest.eliminarEn(0);

        // en mi implementación: candidato está al revés!!
        candidato.comenzar();
        while (!candidato.fin())
            dest.agregarInicio(candidato.proximo());
    }
}
