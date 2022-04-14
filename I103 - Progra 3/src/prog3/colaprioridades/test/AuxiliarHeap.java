package prog3.colaprioridades.test;

public class AuxiliarHeap<T extends Comparable<T>> {

    /** Informa si es un tipo de Heap a partir de un vector que contiene los valores
     * de cada nodo por niveles. Recuerde que la raiz debe estar en datos[1] */
    public boolean esEsteArbolCompletoParcialmenteOrdenado(T[] datos){
        T r1 = isMinHeap(datos, datos.length-1);
        T r2 = isMaxHeap(datos, datos.length-1);

        if (r1 == null){
            System.out.println("Es Min Heap!");
            return true;
        }

        if (r2 == null){
            System.out.println("Es Max Heap!");
            return true;
        }

        System.out.println("El nodo " + r1 + " no permite que sea una Min Heap");
        System.out.println("El nodo " + r2 + " no permite que sea una Max Heap");
        return false;
    }

    private T isMinHeap(T[] datos, int ultPosition){
        // mientras tenga hijos
        for (int i=1; i*2 <= ultPosition; i++){
            // verifico si no hay propiedad de orden con hijo izquierdo
            if (datos[i*2].compareTo(datos[i]) < 0) return datos[i];
            // verifico si no hay propiedad de orden con hijo derecho solo si existe
            if (i*2 != ultPosition && datos[i*2+1].compareTo(datos[i]) < 0) return datos[i];
        }

        return null;
    }

    private T isMaxHeap(T[] datos, int ultPosition){
        // mientras tenga hijos
        for (int i=1; i*2 <= ultPosition; i++){
            // verifico si no hay propiedad de orden con hijo izquierdo
            if (datos[i*2].compareTo(datos[i]) > 0) return datos[i];
            // verifico si no hay propiedad de orden con hijo derecho solo si existe
            if (i*2 != ultPosition && datos[i*2+1].compareTo(datos[i]) > 0) return datos[i];
        }

        return null;
    }
}
