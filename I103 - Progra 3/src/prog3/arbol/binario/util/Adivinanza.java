package prog3.arbol.binario.util;

import prog3.arbol.binario.ArbolBinario;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Adivinanza {
    public static void main(String[] args) {
        // LEFT IS ALWAYS YES, RIGHT IS NO
        ArbolBinario<String> root = new ArbolBinario<>("¿Tiene 4 patas?");
        ArbolBinario<String> leftSon = new ArbolBinario<>("¿Se mueve?");
        ArbolBinario<String> rightSon = new ArbolBinario<>("¿Tiene alguna pata?");
        ArbolBinario<String> leftSon2 = new ArbolBinario<>("¿Ladra?");
        leftSon2.agregarHijoIzquierdo(new ArbolBinario<>("Es un perro"));
        leftSon2.agregarHijoDerecho(new ArbolBinario<>("Es un gato"));
        leftSon.agregarHijoIzquierdo(leftSon2);
        leftSon.agregarHijoDerecho(new ArbolBinario<>("Es una mesa"));
        rightSon.agregarHijoDerecho(new ArbolBinario<>("Es una pelota"));
        rightSon.agregarHijoIzquierdo(new ArbolBinario<>("Es un ventilador"));
        root.agregarHijoIzquierdo(leftSon);
        root.agregarHijoDerecho(rightSon);

        Adivinanza adivinanza = new Adivinanza();
        //System.out.println(adivinanza.secuenciaConMasPreguntas(root));
        ListaGenerica<ListaGenerica<String>> roads = adivinanza.secuenciaConMasPreguntas2(root);
        while (!roads.fin()) System.out.println(roads.proximo());
    }

    public ListaGenericaEnlazada<String> secuenciaConMasPreguntas(ArbolBinario<String> ab){
        ListaGenericaEnlazada<String> ret = new ListaGenericaEnlazada<>();

        if (ab != null && !ab.esVacio()){
            ret.agregarFinal(ab.getDatoRaiz());
            ListaGenerica<String> left = secuenciaConMasPreguntas(ab.getHijoIzquierdo());
            ListaGenerica<String> right = secuenciaConMasPreguntas(ab.getHijoDerecho());
            if (left.tamanio() > right.tamanio()){
                ret.agregarFinal("SI");
                while (!left.fin()) ret.agregarFinal(left.proximo());
            } else if (right.tamanio() > 0) {
                ret.agregarFinal("NO");
                while (!right.fin()) ret.agregarFinal(right.proximo());
            }
        }

        return ret;
    }

    // TODO: ALRIGHT! IT'S WORKING FINE
    public ListaGenerica<ListaGenerica<String>> secuenciaConMasPreguntas2(ArbolBinario<String> ab){
        ListaGenerica<ListaGenerica<String>> roads = new ListaGenericaEnlazada<>();
        ListaGenerica<String> aux;
        int maxLength = 0;

        if (ab != null){
            if (ab.esHoja()){
                aux = new ListaGenericaEnlazada<>();
                aux.agregarFinal(ab.getDatoRaiz());
                roads.agregarFinal(aux);
            } else {
                ListaGenerica<ListaGenerica<String>> left = secuenciaConMasPreguntas2(ab.getHijoIzquierdo());
                ListaGenerica<ListaGenerica<String>> right = secuenciaConMasPreguntas2(ab.getHijoDerecho());

                while (!left.fin()) {
                    aux = left.proximo();
                    aux.agregarInicio("SI");
                    aux.agregarInicio(ab.getDatoRaiz());
                    if (aux.tamanio() >= maxLength){
                        maxLength = aux.tamanio();
                        roads.agregarFinal(aux);
                    }
                }

                while (!right.fin()){
                    aux = right.proximo();
                    aux.agregarInicio("NO");
                    aux.agregarInicio(ab.getDatoRaiz());
                    if (aux.tamanio() >= maxLength){
                        maxLength = aux.tamanio();
                        roads.agregarFinal(aux);
                    }
                }
            }
        }

        return roads;
    }
}
