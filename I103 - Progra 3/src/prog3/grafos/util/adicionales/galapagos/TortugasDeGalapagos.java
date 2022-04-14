package prog3.grafos.util.adicionales.galapagos;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class TortugasDeGalapagos {
    private final Grafo<Isla> mapaIslas;

    public TortugasDeGalapagos(Grafo<Isla> mapaIslas) {
        this.mapaIslas = mapaIslas;
    }

    public Respuesta maximizaTortugas(){
        Respuesta result = new Respuesta();

        if (!mapaIslas.esVacio()){
            ListaGenerica<Vertice<Isla>> vertices = mapaIslas.listaDeVertices();
            Vertice<Isla> origen1 = null, origen2 = null, aux;
            String isla1 = "Baltra", isla2 = "San Cristóbal";

            // buscar orígenes candidatos
            vertices.comenzar();
            while (!vertices.fin() && (origen1 == null || origen2 == null)){
                aux = vertices.proximo();
                if (aux.dato().getNombre().equals(isla1)) origen1 = aux;
                else if (aux.dato().getNombre().equals(isla2)) origen2 = aux;
            }

            // preparar los dfs
            if (origen1 != null && origen2 != null){
                ListaGenerica<String> temp = new ListaGenericaEnlazada<>();
                boolean[] visitados = new boolean[vertices.tamanio()];
                int[] max = new int[]{-1};

                // buscar desde primer origen
                this.dfs(origen1, result.getListaRecorrido(), temp, visitados, max, 0);
                int maxDesdeIsla1 = max[0];

                // buscar desde segundo origen y comparar resultados
                this.dfs(origen2, result.getListaRecorrido(), temp, visitados, max, 0);
                result.setNombreOrigen(max[0] > maxDesdeIsla1 ? isla2 : isla1);
            }
        }

        return result;
    }

    private void dfs(Vertice<Isla> v, ListaGenerica<String> listaMax, ListaGenerica<String> camino,
                     boolean[] visitados, int[] tortugasMax, int tortugasAct){
        // acciones iniciales comunes
        visitados[v.posicion()] = true;
        camino.agregarInicio(v.dato().getNombre());

        // actualizar cuando encuentre avistamientos
        if (v.dato().hasTortugas()) {
            tortugasAct++;
            if (tortugasAct > tortugasMax[0]) {
                this.copiarInvertido(camino, listaMax);
                tortugasMax[0] = tortugasAct;
            }
        }

        // seguir buscando (siempre)
        ListaGenerica<Arista<Isla>> adyacencias = this.mapaIslas.listaDeAdyacentes(v);
        Vertice<Isla> islaProx;

        adyacencias.comenzar();
        while (!adyacencias.fin()){
            islaProx = adyacencias.proximo().verticeDestino();
            if (!visitados[islaProx.posicion()])
                this.dfs(islaProx, listaMax, camino, visitados, tortugasMax, tortugasAct);
        }

        // acciones finales comunes
        camino.eliminarEn(0);
        visitados[v.posicion()] = false;
    }

    private void copiarInvertido(ListaGenerica<String> fuente, ListaGenerica<String> dest){
        while (!dest.esVacia())
            dest.eliminarEn(0);

        fuente.comenzar();
        while (!fuente.fin())
            dest.agregarInicio(fuente.proximo());
    }
}
