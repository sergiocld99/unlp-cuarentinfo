package prog3.colaprioridades;

public interface ColaPrioridades<T extends Comparable<T>> {
    boolean esVacia();
    boolean agregar(T x);
    T eliminar();
    T tope();
}
