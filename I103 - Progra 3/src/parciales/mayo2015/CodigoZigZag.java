package parciales.mayo2015;

import prog3.arbol.binario.ArbolBinario;
import prog3.colaprioridades.MinHeap;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class CodigoZigZag {
    // todo sin variables de instancia


    // precondicion: la lista de secuencias solo contiene secuencias que sean válidas
    // devuelve una lista con los caracteres que representan los codigos pasados como strings (solo contienen ceros y unos)
    public ListaGenerica<Character> descifrarCodigo(ArbolBinario<Character> estructura, ListaGenerica<String> listaDeSecuencias){
        ListaGenerica<Character> retorno = new ListaGenericaEnlazada<>();
        ArbolBinario<Character> nodo;
        String sec;

        // preparar iteracion
        listaDeSecuencias.comenzar();

        // hallar todos los caracteres
        while (!listaDeSecuencias.fin()){
            sec = listaDeSecuencias.proximo();          // obtener secuencia actual
            nodo = estructura;                           // empezaré por la raiz
            for (Character c : sec.toCharArray())       // 0 izquierda, 1 derecha
                nodo = (c == '0') ? nodo.getHijoIzquierdo() : nodo.getHijoDerecho();
            retorno.agregarFinal(nodo.getDatoRaiz());    // agrego letra a la lista de retorno
        }

        // devolver
        return retorno;
    }


    // imprime las letras contenidas en la estructura ordenadas alfabéticamente
    public void imprimirLetrasOrdenadas(ArbolBinario<Character> estructura){
        if (estructura == null || estructura.esVacio()) return;

        boolean[] result = new boolean['Z'-'A'+1];
        this.buscarHojas(estructura, result);

        for (char c = 'A'; c <= 'Z'; c++)
            if (result[c - 'A']) System.out.print(c + " ");
    }


    private void buscarHojas(ArbolBinario<Character> nodo, boolean[] letras){
        if (nodo.esHoja()) letras[nodo.getDatoRaiz() - 'A'] = true;
        else {
            if (!nodo.getHijoIzquierdo().esVacio())
                this.buscarHojas(nodo.getHijoIzquierdo(), letras);
            if (!nodo.getHijoDerecho().esVacio())
                this.buscarHojas(nodo.getHijoDerecho(), letras);
        }
    }
}
