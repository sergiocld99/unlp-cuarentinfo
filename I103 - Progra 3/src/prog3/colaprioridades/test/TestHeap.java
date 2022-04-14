package prog3.colaprioridades.test;
import org.junit.Assert;
import org.junit.Test;
import prog3.colaprioridades.MaxHeap;
import prog3.colaprioridades.MinHeap;
import prog3.colaprioridades.usos.Documento;
import prog3.colaprioridades.usos.Impresora;

public class TestHeap<T extends Comparable<T>> {

    @Test
    public void testImpresora(){
        Impresora impresora = new Impresora();
        impresora.nuevoDocumento(new Documento("Hola! ¿Cómo estás?"));
        impresora.nuevoDocumento(new Documento("Texto corto"));
        impresora.nuevoDocumento(new Documento(
                "Yo soy un texto muy extenso, así que debería imprimirme en último lugar " +
                        "para no bloquear las demás impresiones que son más rápidas que yo"
        ));

        impresora.imprimir();
        System.out.println();

        impresora.nuevoDocumento(new Documento("Soy un texto de seis palabras"));
        impresora.nuevoDocumento(new Documento("A"));

        while (true)
            if (!impresora.imprimir())
                break;
    }

    @Test
    public void testBuildHeap(){
        Integer[] datos = new Integer[]{5, 8, 12, 9, 7, 10, 21, 6, 14, 4};

        BuildHeap<Integer> buildHeap = new BuildHeap<>(datos);
        buildHeap.imprimir();
    }

    @Test
    public void testOrdenarVector(){
        Integer[] entrada = new Integer[]{50, 30, 18, 25, 22, 12};
        Integer[] expected = new Integer[]{12, 18, 22, 25, 30, 50};
        Integer[] salida = new Integer[entrada.length];

        // todo: orden creciente
        // construir una min heap
        MinHeap<Integer> minHeap = new MinHeap<>(entrada);

        // realizar delete min e ir guardando en otro vector
        for (int i=0; i<salida.length; i++)
            salida[i] = minHeap.eliminar();

        // todo check
        for (int i=0 ; i<expected.length; i++)
            Assert.assertEquals(expected[i], salida[i]);
    }

    @Test
    public void testHeapSort(){
        Integer[] entrada = new Integer[]{25, 22, 12, 15, 50, 9, 18, 30};

        // todo: vector -> orden creciente
        // construir una max heap
        MaxHeap<Integer> maxHeap = new MaxHeap<>(entrada);

        // algoritmo heap sort (ver implementacion)
        maxHeap.heapSort();
    }

    @Test
    public void testEsHeap(){
        Integer[] datos1 = new Integer[]{null, 10, 20, 90, 30, 60, 130, 100};
        Integer[] datos3 = new Integer[]{null, 10, 40, 120, 30, 60, 130};

        AuxiliarHeap<Integer> aux = new AuxiliarHeap<>();
        Assert.assertTrue(aux.esEsteArbolCompletoParcialmenteOrdenado(datos1));
        Assert.assertFalse(aux.esEsteArbolCompletoParcialmenteOrdenado(datos3));
    }

    @Test
    public void testMinHeap(){
        Integer[] datos = new Integer[]{30, 80, 70, 100, 90, 130, 110, 120};

        MinHeap<Integer> minHeap = new MinHeap<>(datos);
        minHeap.agregar(60);
        minHeap.agregar(75);
        minHeap.agregar(10);
        minHeap.imprimir();
    }

    @Test
    public void testMaxHeap(){
        Integer[] datos = new Integer[]{81, 53, 60, 39, 2,15, 4, 11, 8, 1};

        MaxHeap<Integer> maxHeap = new MaxHeap<>(datos);
        maxHeap.imprimir();
    }
}
