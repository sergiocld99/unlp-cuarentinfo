package prog3.colaprioridades;

import prog3.listagenerica.ListaGenerica;

public class MinHeap<T extends Comparable<T>> implements ColaPrioridades<T> {
    private final T[] datos;
    private int cantElementos;
    // todo: datos[0] se utiliza como auxiliar

    public MinHeap(){
        // inicializar heap vacia
        this.datos = (T[]) new Comparable[100];
    }

    // se recibe una lista y agregar cada elemento
    public MinHeap(ListaGenerica<T> lista){
        this();
        boolean hayEspacio = true;

        lista.comenzar();
        while (!lista.fin() && hayEspacio){
            hayEspacio = this.agregar(lista.proximo());
        }
    }

    // se recibe un vector y agrega cada elemento
    public MinHeap(T[] vector){
        this();
        boolean hayEspacio = true;

        for (int i=0; i<vector.length && hayEspacio; i++)
            hayEspacio = this.agregar(vector[i]);
    }

    @Override
    public boolean esVacia(){
        return cantElementos == 0;
    }

    @Override
    public T eliminar(){
        T retorno = this.tope();
        this.datos[1] = this.datos[cantElementos--];
        this.percolateDown();
        return retorno;
    }

    @Override
    public boolean agregar(T x){
        if (cantElementos == datos.length) return false;
        this.datos[++cantElementos] = x;
        this.percolateUp(cantElementos);
        return true;
    }

    private void percolateDown(){
        boolean stop = false;
        int p = 1, h_min;
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

    private void percolateUp(int i){
        this.datos[0] = this.datos[i];    // valor a filtrar

        // mientras no sea la raiz y no se cumpla propiedad de orden
        while (i/2 > 0 && datos[i/2].compareTo(datos[0]) > 0){
            this.datos[i] = this.datos[i/2];    // asignar valor del padre
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


    // todo inciso c
    public T extract(){
        T retorno = null;
        int act = 2;
        while (act <= cantElementos && this.datos[act] != this.datos[act-1]) act++;
        if (act < cantElementos){
            retorno = this.datos[act];
            for (int i=act; i<cantElementos; i++) this.datos[act] = this.datos[act+1];
            this.cantElementos--;
        }
        return retorno;
    }
}
