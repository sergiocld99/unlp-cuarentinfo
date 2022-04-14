package prog3.grafos.util;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Mapa {
    private final Grafo<String> mapaCiudades;

    public Mapa(Grafo<String> mapaCiudades) {
        this.mapaCiudades = mapaCiudades;
    }

    /** MÉTODO COMÚN PARA TODOS LOS PÚBLICOS */
    private Vertice<String> encontrarOrigen(String nombreCiudad, ListaGenerica<Vertice<String>> vertices){
        Vertice<String> aux;

        // ir buscando
        vertices.comenzar();
        while (!vertices.fin()) {
            aux = vertices.proximo();
            if (aux.dato().equals(nombreCiudad)) return aux;
        }

        // no se encontró
        return null;
    }

    /** MÉTODO COMÚN PARA ACTUALIZACIÓN DE MÍNIMOS */
    private void actualizarCamino(ListaGenerica<String> original, ListaGenerica<String> nuevo){
        nuevo.comenzar();

        // descartar anterior
        while (!original.esVacia())
            original.eliminarEn(0);

        // ir agregando
        while (!nuevo.fin())
            original.agregarFinal(nuevo.proximo());
    }

    /** MÉTODOS PÚBLICOS */
    public ListaGenerica<String> devolverCamino(String ciudad1, String ciudad2){
        return this.devolverCaminoExceptuando(ciudad1, ciudad2, new ListaGenericaEnlazada<>());
    }
    public ListaGenerica<String> devolverCaminoExceptuando(String ciudad1, String ciudad2, ListaGenerica<String> ciudades){
        ListaGenerica<String> result = new ListaGenericaEnlazada<>();

        if (mapaCiudades != null && !mapaCiudades.esVacio()){
            ListaGenerica<Vertice<String>> vertices = mapaCiudades.listaDeVertices();
            Vertice<String> origen = this.encontrarOrigen(ciudad1, vertices);

            if (origen != null){
                boolean[] marcas = new boolean[vertices.tamanio()];     // todo en false
                this.hallarCamino(origen, ciudad2, result, ciudades, marcas);
            }
        }

        return result;
    }
    public ListaGenerica<String> caminoMasCorto(String ciudad1, String ciudad2){
        ListaGenerica<String> result = new ListaGenericaEnlazada<>();

        if (mapaCiudades != null && !mapaCiudades.esVacio()){
            ListaGenerica<Vertice<String>> vertices = mapaCiudades.listaDeVertices();
            Vertice<String> origen = this.encontrarOrigen(ciudad1, vertices);

            if (origen != null){
                boolean[] marcas = new boolean[vertices.tamanio()];     // todo en false
                int distancia = this.caminoMasCorto(origen, ciudad2, result, marcas);
                System.out.print("Distancia de " + distancia + " km: ");
            }
        }

        return result;
    }
    public ListaGenerica<String> caminoSinCargarCombustible(String ciudad1, String ciudad2, int tanqueAuto){
        ListaGenerica<String> result = new ListaGenericaEnlazada<>();

        if (mapaCiudades != null && !mapaCiudades.esVacio()){
            ListaGenerica<Vertice<String>> vertices = mapaCiudades.listaDeVertices();
            Vertice<String> origen = this.encontrarOrigen(ciudad1, vertices);

            if (origen != null){
                boolean[] marcas = new boolean[vertices.tamanio()];     // todo en false
                int distancia = this.caminoSinCargar(origen, ciudad2, result, tanqueAuto * 7, marcas);
                System.out.println("Se recorren " + distancia + " km, que gastan " + distancia / 7 + " litros");
            }
        }

        return result;
    }
    public ListaGenerica<String> caminoConMenosCargaDeCombustible(String ciudad1, String ciudad2, int tanqueAuto){
        ListaGenerica<String> result = new ListaGenericaEnlazada<>();

        if (mapaCiudades != null && !mapaCiudades.esVacio()){
            ListaGenerica<Vertice<String>> vertices = mapaCiudades.listaDeVertices();
            Vertice<String> origen = this.encontrarOrigen(ciudad1, vertices);

            if (origen != null){
                boolean[] marcas = new boolean[vertices.tamanio()];     // todo en false
                int cargas = this.caminoMenosCarga(origen, ciudad2, result, tanqueAuto * 7, tanqueAuto * 7, marcas);
                System.out.println("Es necesario cargar un mínimo de " + cargas + " veces");
            }
        }

        return result;
    }

    /** MÉTODO AUXILIAR PARA LOS DOS PRIMEROS INCISOS */    // todo distinto de null - armado de lista en postOrden
    private boolean hallarCamino(Vertice<String> actual, String destino, ListaGenerica<String> l, ListaGenerica<String> ex, boolean[] visitados){
        // compruebo si ya fui visitado
        if (visitados[actual.posicion()])
            return false;

        // compruebo si estoy en la lista "negra"
        if (!ex.esVacia()){
            ex.comenzar();
            while (!ex.fin())
                if (ex.proximo().equals(actual.dato()))
                    return false;
        }

        // soy ciudad destino ? --> me agrego al final
        if (actual.dato().equals(destino)){
            l.agregarFinal(actual.dato());
            return true;
        }

        // visitarme
        visitados[actual.posicion()] = true;

        // obtener lista de posibles caminos
        ListaGenerica<Arista<String>> aristas = mapaCiudades.listaDeAdyacentes(actual);
        aristas.comenzar();

        // ir buscando
        boolean encontrado = false;
        while (!aristas.fin() && !encontrado)
            encontrado = this.hallarCamino(aristas.proximo().verticeDestino(), destino, l, ex, visitados);

        // si se encontró camino, me agrego al principio (respeto el orden)
        if (encontrado) l.agregarInicio(actual.dato());

        // desmarcarme
        visitados[actual.posicion()] = false;

        return encontrado;
    }

    /** MÉTODO AUXILIAR PARA CAMINO MÁS CORTO */            // todo distinto de null - armado de lista en postOrden
    private int caminoMasCorto(Vertice<String> actual, String destino, ListaGenerica<String> l, boolean[] visited){
        // compruebo si ya fui visitado
        if (visited[actual.posicion()])
            return -1;

        // soy ciudad destino ? --> posible resultado
        if (actual.dato().equals(destino)){
            l.agregarFinal(actual.dato());
            return 0;
        }

        // visitarme
        visited[actual.posicion()] = true;

        // obtengo posibles caminos
        ListaGenerica<Arista<String>> aristas = mapaCiudades.listaDeAdyacentes(actual);
        aristas.comenzar();

        // ir seleccionando
        ListaGenerica<String> candidatos;
        int dist, min = Integer.MAX_VALUE;
        Vertice<String> aux;

        while (!aristas.fin()){
            aux = aristas.proximo().verticeDestino();
            candidatos = new ListaGenericaEnlazada<>();
            dist = this.caminoMasCorto(aux, destino, candidatos, visited);
            // si dist = -1, no se modificó la lista de candidatos
            if (dist > -1){
                dist += mapaCiudades.peso(actual, aux);
                // verificar si es más corto
                if (dist < min){
                    min = dist;
                    // descartar anterior
                    // copiar candidatos a resultado
                    this.actualizarCamino(l, candidatos);
                }
            }
        }

        // me desmarco
        visited[actual.posicion()] = false;

        // si encontré algún camino válido, me agrego al principio
        if (l.tamanio() > 0){
            l.agregarInicio(actual.dato());
            return min;
        } else return -1;
    }

    /** MÉTODO AUXILIAR PARA CAMINO SIN CARGAR */           // todo distinto de null - armado de lista en postOrden
    private int caminoSinCargar(Vertice<String> actual, String destino, ListaGenerica<String> l, int limite, boolean[] visited){
        // comprobar si ya fui visitado
        if (visited[actual.posicion()])
            return -1;

        // soy ciudad destino ? --> posible resultado
        if (actual.dato().equals(destino)){
            l.agregarFinal(actual.dato());
            return 0;
        }

        // me visito
        visited[actual.posicion()] = true;

        // obtengo posibles caminos
        ListaGenerica<Arista<String>> aristas = mapaCiudades.listaDeAdyacentes(actual);
        aristas.comenzar();

        // ir buscando
        Vertice<String> aux;
        boolean encontrado = false;
        int dist = -1, res;

        while (!aristas.fin() && !encontrado){
            aux = aristas.proximo().verticeDestino();
            dist = mapaCiudades.peso(actual, aux);
            // si me quedo sin combustible en el camino, no sigo
            if (dist <= limite){
                res = this.caminoSinCargar(aux, destino, l, limite-dist, visited);
                // si devolvió un numero positivo, se encontró un camino válido
                if (res > -1){
                    dist += res;
                    encontrado = true;
                }
            }
        }

        // me desmarco
        visited[actual.posicion()] = false;

        // si encontré camino, me agrego al inicio (respeto el orden)
        if (encontrado){
            l.agregarInicio(actual.dato());
            return dist;
        } else return -1;
    }

    /** MÉTODO AUXILIAR PARA CAMINO CON POCAS CARGAS */     // todo distinto de null - armado de lista en postOrden
    private int caminoMenosCarga(Vertice<String> actual, String destino, ListaGenerica<String> l, int disponible, int limite, boolean[] visited){
        // comprebo si ya fui visitado
        if (visited[actual.posicion()])
            return -1;

        // soy ciudad destino ? --> posible resultado
        if (actual.dato().equals(destino)){
            l.agregarFinal(actual.dato());
            return 0;
        }

        // me visito
        visited[actual.posicion()] = true;

        // obtengo posibles caminos
        ListaGenerica<Arista<String>> aristas = mapaCiudades.listaDeAdyacentes(actual);
        aristas.comenzar();

        // ir seleccionando
        ListaGenerica<String> candidatos;
        Vertice<String> aux;
        int min = Integer.MAX_VALUE;
        int consumoProx, cargas, dispAct, res;

        while (!aristas.fin()){
            aux = aristas.proximo().verticeDestino();
            candidatos = new ListaGenericaEnlazada<>();
            consumoProx = mapaCiudades.peso(actual, aux);
            // verificar que el camino no sea demasiado largo
            if (consumoProx <= limite){
                // cargar combustible si es necesario
                if (consumoProx > disponible){
                    dispAct = limite;
                    cargas = 1;
                } else {
                    dispAct = disponible;
                    cargas = 0;
                }
                // llamado recursivo
                res = this.caminoMenosCarga(aux, destino, candidatos, dispAct - consumoProx, limite, visited);
                // si devuelve -1, no se encontró un camino
                if (res > -1){
                    cargas += res;
                    if (cargas < min){
                        min = cargas;
                        // descartar un camino anterior
                        // agregar candidatos a lista retorno
                        this.actualizarCamino(l, candidatos);
                    }
                }
            }
        }

        // me desmarco
        visited[actual.posicion()] = false;

        // si se encontró un camino, agregarme respetando el orden
        if (l.tamanio() > 0){
            l.agregarInicio(actual.dato());
            return min;
        } else return -1;
    }
}
