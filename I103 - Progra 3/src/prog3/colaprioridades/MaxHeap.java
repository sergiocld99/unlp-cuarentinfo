package prog3.colaprioridades;

import prog3.listagenerica.ListaGenerica;

public class MaxHeap<T extends Comparable<T>> implements ColaPrioridades<T> {
    private final T[] datos;
    private int cantElementos;
    // todo: datos[0] se utiliza como auxiliar

    public MaxHeap() {
        // inicializar heap vacia
        this.datos = (T[]) new Comparable[100];
    }

    // se recibe una lista y agregar cada elemento
    public MaxHeap(ListaGenerica<T> lista) {
        this();
        boolean hayEspacio = true;

        lista.comenzar();
        while (!lista.fin() && hayEspacio) {
            hayEspacio = this.agregar(lista.proximo());
        }
    }

    // se recibe un vector y agrega cada elemento
    public MaxHeap(T[] vector) {
        this();
        boolean hayEspacio = true;

        for (int i = 0; i < vector.length && hayEspacio; i++)
            hayEspacio = this.agregar(vector[i]);
    }

    @Override
    public boolean esVacia() {
        return cantElementos == 0;
    }

    @Override
    public T eliminar() {
        T retorno = this.tope();
        this.datos[1] = this.datos[cantElementos--];
        this.percolateDown();
        return retorno;
    }

    @Override
    public boolean agregar(T x) {
        if (cantElementos == datos.length) return false;
        this.datos[++cantElementos] = x;
        this.percolateUp(cantElementos);
        return true;
    }

    private void percolateDown(){
        this.percolateDown(1);
    }

    private void percolateDown(int p) {
        boolean stop = false;
        int h_max;
        this.datos[0] = this.datos[p];

        // mientras tenga hijo y falte seguir chequeando
        while (2 * p <= cantElementos && !stop) {
            h_max = 2 * p;      // mi hijo izquierdo es el maximo

            // si tengo hijo derecho y éste es mayor que su hermano
            if (h_max != cantElementos && datos[h_max + 1].compareTo(datos[h_max]) > 0)
                h_max += 1;     // mi hijo derecho es el maximo

            // verifico si no se cumple propiedad de orden
            if (this.datos[0].compareTo(datos[h_max]) < 0) {
                this.datos[p] = this.datos[h_max];  // copio hijo en padre
                p = h_max;                          // bajo un nivel
            } else stop = true;
        }

        this.datos[p] = this.datos[0];      // ubicacion correcta
    }

    private void percolateUp(int i) {
        this.datos[0] = this.datos[i];    // valor a filtrar

        // mientras no sea la raiz y no se cumpla propiedad de orden
        while (i / 2 > 0 && datos[i / 2].compareTo(datos[0]) < 0) {
            this.datos[i] = this.datos[i / 2];    // asignar valor del padre
            i /= 2;                             // subo un nivel
        }

        this.datos[i] = this.datos[0];      // ubicacion correcta
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


    // todo práctica 6 - ejercicio 4
    public void bajarPrioridad(int p, int delta){
        // this.datos[p].setPrioridad(this.datos[p].getPrioridad - delta);

        // al disminuir el valor de un nodo, puede ocurrir que alguno de sus hijos
        // quede con un valor mayor que él, rompiéndose la propiedad de orden

        this.percolateDown(p);
    }


    // todo out of context
    public void heapSort(){
        int original = cantElementos;

        while (cantElementos > 1){
            // intercambio el primero con el ultimo
            this.datos[0] = this.datos[1];
            this.datos[1] = this.datos[cantElementos];
            this.datos[cantElementos] = this.datos[0];

            // decremento el tamaño
            this.cantElementos--;

            // filtrado hacia abajo
            this.percolateDown();
        }

        this.cantElementos = original;
        this.imprimir();
    }
}
