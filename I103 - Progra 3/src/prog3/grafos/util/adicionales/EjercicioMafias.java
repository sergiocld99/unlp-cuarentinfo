package prog3.grafos.util.adicionales;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

// observación: me disgusta el eliminarEn(tamanio-1) de la lista candidato
// porque SIEMPRE tiene que recorrer la lista secuencialmente

public class EjercicioMafias {

    public ListaGenerica<Sitio> rutaMasSegura(Grafo<Sitio> ciudad){
        ListaGenerica<Sitio> result = new ListaGenericaEnlazada<>();

        // comprobacion de nulos
        if (ciudad != null && !ciudad.esVacio()){
            // listado de todos los sitios
            ListaGenerica<Vertice<Sitio>> sitios = ciudad.listaDeVertices();
            Vertice<Sitio> aux, origen = null, destino = null;

            // hallar origen y destino
            sitios.comenzar();
            while (!sitios.fin() && (origen == null || destino == null)){
                aux = sitios.proximo();
                if (aux.dato().getNombre().equals("Casa del Intendente")) origen = aux;
                else if (aux.dato().getNombre().equals("Municipalidad")) destino = aux;
            }

            // disparar dfs
            if (origen != null && destino != null){
                boolean[] visitados = new boolean[sitios.tamanio()];            // marcas anti-ciclo
                ListaGenerica<Sitio> temp = new ListaGenericaEnlazada<>();      // candidatos
                int[] mafiasMin = new int[]{Integer.MAX_VALUE};     // por referencia
                this.dfs(ciudad, origen, destino, temp, result, visitados, 0, mafiasMin);
            }
        }

        // retornar lista (puede estar vacía)
        return result;
    }

    // PROCESAMIENTO POR PRE-ORDEN
    private void dfs(Grafo<Sitio> grafo, Vertice<Sitio> o, Vertice<Sitio> d,
                     ListaGenerica<Sitio> lista, ListaGenerica<Sitio> listaMin,
                     boolean[] marcas, int mafiasActuales, int[] mafiasMin){
        // me visito
        marcas[o.posicion()] = true;

        // me agrego a lista candidatos
        lista.agregarFinal(o.dato());

        // actualizo cantidad de mafias
        if (o.dato().isTieneMafia()) mafiasActuales++;

        // caso base (encontré el destino)
        if (o.dato().equals(d.dato())){
            // actualizo resultados si soy el mínimo
            if (mafiasActuales < mafiasMin[0]){
                mafiasMin[0] = mafiasActuales;
                this.copiar(lista, listaMin);
            }
        } else {
            // posible caso recursivo: adyacencias actuales
            ListaGenerica<Arista<Sitio>> aristas = grafo.listaDeAdyacentes(o);
            Arista<Sitio> aux;

            // ir buscando candidatos
            aristas.comenzar();
            while (!aristas.fin()){
                aux = aristas.proximo();
                // llamado recursivo si no forma parte del camino
                if (!marcas[aux.verticeDestino().posicion()]){
                    this.dfs(grafo, aux.verticeDestino(), d, lista, listaMin,
                            marcas, mafiasActuales + aux.peso(), mafiasMin);
                }
            }
        }

        // me quito de lista candidatos (siempre) [no me gusta esta instrucción]
        // esto debe hacerse porque se pasa por referencia, no así mafiasActuales
        lista.eliminarEn(lista.tamanio()-1);

        // me desmarco (siempre)
        marcas[o.posicion()] = false;
    }

    private void copiar(ListaGenerica<Sitio> fuente, ListaGenerica<Sitio> destino){
        // descartar anterior
        while (!destino.esVacia())
            destino.eliminarEn(0);

        // copiar nuevo
        fuente.comenzar();
        while (!fuente.fin())
            destino.agregarFinal(fuente.proximo());
    }
}
