package parciales.mayo2015;

import org.junit.Before;
import org.junit.Test;
import prog3.arbol.binario.ArbolBinario;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class TestZigZag {
    private ArbolBinario<Character> ejemplo;

    @Before
    public void setUp(){
        ArbolBinario<Character> n1, n2, n3, n4, n5, n6, n7, n8, n9;

        // todo: si al constructor de arbol binario no se le pasa ningún parámetro, no se podrá añadirle hijos
        n1 = new ArbolBinario<>(null);
        n1.agregarHijoIzquierdo(new ArbolBinario<>('C'));
        n1.agregarHijoDerecho(new ArbolBinario<>('S'));

        n2 = new ArbolBinario<>(null);
        n2.agregarHijoIzquierdo(n1);

        n3 = new ArbolBinario<>(null);
        n3.agregarHijoIzquierdo(new ArbolBinario<>('A'));
        n3.agregarHijoDerecho(new ArbolBinario<>('G'));

        n4 = new ArbolBinario<>(null);
        n4.agregarHijoIzquierdo(n3);
        n4.agregarHijoDerecho(new ArbolBinario<>('R'));

        n5 = new ArbolBinario<>(null);
        n5.agregarHijoIzquierdo(n4);
        n5.agregarHijoDerecho(new ArbolBinario<>('E'));

        n6 = new ArbolBinario<>(null);
        n6.agregarHijoDerecho(new ArbolBinario<>('O'));

        n7 = new ArbolBinario<>(null);
        n7.agregarHijoIzquierdo(new ArbolBinario<>('D'));
        n7.agregarHijoDerecho(n6);

        n8 = new ArbolBinario<>(null);
        n8.agregarHijoIzquierdo(n5);
        n8.agregarHijoDerecho(n7);

        n9 = new ArbolBinario<>(null);
        n9.agregarHijoDerecho(n8);
        n9.agregarHijoIzquierdo(n2);

        this.ejemplo = n9;
    }

    @Test
    public void testDescifrar(){
        CodigoZigZag zigZag = new CodigoZigZag();
        ListaGenerica<String> secuencias = new ListaGenericaEnlazada<>();
        //String[] entrada = new String[]{"101", "001", "10001", "1111", "1001", "110", "1111"};
        String[] entrada = new String[]{"000", "10000", "001", "10000"};
        for (String s : entrada) secuencias.agregarFinal(s);

        ListaGenerica<Character> palabra = zigZag.descifrarCodigo(this.ejemplo, secuencias);
        System.out.println(palabra);

        zigZag.imprimirLetrasOrdenadas(this.ejemplo);
    }
}
