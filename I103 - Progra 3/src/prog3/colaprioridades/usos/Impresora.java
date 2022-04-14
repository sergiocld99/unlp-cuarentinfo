package prog3.colaprioridades.usos;
import prog3.colaprioridades.MinHeap;

public class Impresora implements DispositivoImpresion {
    private final MinHeap<Documento> colaImpresion;

    public Impresora(){
        this.colaImpresion = new MinHeap<>();
    }

    @Override
    public void nuevoDocumento(Documento d) {
        this.colaImpresion.agregar(d);
    }

    @Override
    public boolean imprimir() {
        if (this.colaImpresion.esVacia()) return false;
        System.out.println(this.colaImpresion.eliminar());
        return true;
    }
}
