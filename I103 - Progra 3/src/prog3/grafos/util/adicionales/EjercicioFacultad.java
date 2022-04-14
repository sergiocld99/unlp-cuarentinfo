package prog3.grafos.util.adicionales;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class EjercicioFacultad {

    /* ENUNCIADO
    La facultad de Ciencias Exactas tiene docentes dando clases en diferentes Facultades:
    Informática, Química, Astronomía, Medicina, etc. María es contratada por Exactas para realizar encuestas
    a los alumnos de las diferentes comisiones de los diferentes cursos de todas las facultades,
    y quiere saber cuál es la ruta óptima que menos tiempo le lleve para visitar todos los
    cursos de los diferentes edificios. Dado un grafo con vértices que representan los diferentes cursos
    de todas las facultades, y con aristas representando cuánto cuesta llegar desde un curso
    a otro, haga un programa en Java que le ayude a María definir en qué orden visitar los diferentes cursos
    para que le cueste lo menos posible. Asuma que el primer curso lo puede elegir sin costo, y los cursos
    se pueden visitar en cualquier momento
     */

    public ListaGenerica<String> rutaOptima(Grafo<String> grafo){
        ListaGenerica<String> result = new ListaGenericaEnlazada<>();

        if (grafo != null && !grafo.esVacio()){
            ListaGenerica<Vertice<String>> cursos = grafo.listaDeVertices();
            ListaGenerica<String> camino = new ListaGenericaEnlazada<>();
            boolean[] enCamino = new boolean[cursos.tamanio()];
            int[] tiempoMin = new int[]{Integer.MAX_VALUE};

            cursos.comenzar();
            while (!cursos.fin()){
                // disparamos dfs para cada vértice origen
                this.rutaOptima(grafo, cursos.proximo(), result,
                        camino, enCamino, tiempoMin, 0, cursos.tamanio()-1);
            }
        }

        return result;
    }

    // asumimos un grafo conexo (todos los nodos son alcanzables desde algún vértice)
    private void rutaOptima(Grafo<String> g, Vertice<String> v, ListaGenerica<String> caminoMin,
                            ListaGenerica<String> camino, boolean[] marcas, int[] min, int time, int faltan){

        // caso base "malo": ya no es ruta óptima
        if (time >= min[0]) return;

        // candidatos invertidos
        camino.agregarInicio(v.dato());

        // caso base "bueno": visité todos los cursos
        if (faltan == 0){
            min[0] = time;
            this.copiarInvertido(camino, caminoMin);
            return;
        }

        // evito entrar en ciclos
        marcas[v.posicion()] = true;

        // caso recursivo: recorrer adyacencias
        ListaGenerica<Arista<String>> adyacentes = g.listaDeAdyacentes(v);
        Arista<String> arista;

        adyacentes.comenzar();
        while (!adyacentes.fin()){
            arista = adyacentes.proximo();
            if (!marcas[arista.verticeDestino().posicion()]){
                this.rutaOptima(g, arista.verticeDestino(), caminoMin,
                        camino, marcas, min, time + arista.peso(), faltan - 1);
            }
        }

        // actualización eficiente
        camino.eliminarEn(0);

        // permito otros caminos
        marcas[v.posicion()] = false;
    }

    private void copiarInvertido(ListaGenerica<String> fuente, ListaGenerica<String> dest){
        // descartamos el anterior
        while (!dest.esVacia())
            dest.eliminarEn(0);

        // agregar nuevo
        fuente.comenzar();
        while (!fuente.fin())
            dest.agregarInicio(fuente.proximo());
    }
}
