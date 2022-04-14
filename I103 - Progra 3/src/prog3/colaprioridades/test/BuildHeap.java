package prog3.colaprioridades.test;

import prog3.colaprioridades.ColaPrioridades;
import prog3.listagenerica.ListaGenerica;

/** TIPO MIN HEAP*/
public class BuildHeap<T extends Comparable<T>> implements ColaPrioridades<T> {
    private final T[] datos;
    private int cantElementos;

    public BuildHeap() {
        this.datos = (T[]) new Comparable[100];
    }

    // se recibe una lista y se agrega cada elemento
    public BuildHeap(ListaGenerica<T> lista) {
        this();
        boolean hayEspacio = true;

        lista.comenzar();
        while (!lista.fin() && hayEspacio) {
            hayEspacio = this.agregar(lista.proximo());
        }

        for (int i = cantElementos/2; i > 0; i--) percolateDown(i);
    }

    // se recibe un vector y agrega cada elemento
    public BuildHeap(T[] vector) {
        this();
        boolean hayEspacio = true;

        for (int i = 0; i < vector.length && hayEspacio; i++)
            hayEspacio = this.agregar(vector[i]);

        for (int i = cantElementos/2; i > 0; i--) percolateDown(i);
    }

    @Override
    public boolean esVacia() {
        return cantElementos == 0;
    }

    @Override
    public T eliminar() {
        T retorno = this.tope();
        this.datos[1] = this.datos[cantElementos--];
        this.percolateDown(1);
        return retorno;
    }

    @Override
    public boolean agregar(T x) {
        if (cantElementos == datos.length) return false;
        this.datos[++cantElementos] = x;
        // no percolate up
        return true;
    }

    private void percolateDown(int p){
        boolean stop = false;
        int h_min;
        this.datos[0] = this.datos[p];

        // mientras tenga hijo y falte seguir chequeando
        while (2*p <= cantElementos && !stop){
            h_min = 2 * p;      // mi hijo izquierdo es el minimo

            // si tengo hijo derecho y éste es menor que su hermano
            if (h_min != cantElementos && datos[h_min+1].compareTo(datos[h_min]) < 0)
                h_min += 1;     // mi hijo derecho es el minimo

            // verifico si no se cumple propiedad de orden
            if (this.datos[0].compareTo(datos[h_min]) > 0){
                this.datos[p] = this.datos[h_min];  // copio hijo en padre
                p = h_min;                          // bajo un nivel
            } else stop = true;
        }

        this.datos[p] = this.datos[0];      // ubicacion correcta
    }

    public void imprimir(){
        for (int i=1; i<cantElementos; i++){
            System.out.print(datos[i] + ", ");
        }
        if (cantElementos > 0) System.out.print(datos[cantElementos]);
        System.out.println();
    }

    @Override
    public T tope(){
        return this.datos[1];
    }
}