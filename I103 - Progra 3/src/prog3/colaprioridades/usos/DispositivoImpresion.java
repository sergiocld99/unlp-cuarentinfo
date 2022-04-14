package prog3.colaprioridades.usos;

public interface DispositivoImpresion {
    // Almacena un nuevo documento en el dispositivo
    void nuevoDocumento(Documento d);

    // Imprime (saca por pantalla) el documento almacenado más corto
    // y lo elimina de memoria. Si no hay documentos en espera devuelve false
    boolean imprimir();

}
