package prog3.util;

import prog3.listaenteros.ListaDeEnteros;
import prog3.listaenteros.ListaDeEnterosEnlazada;

public class UtilitariosLista {

    public static ListaDeEnteros mergeSort(ListaDeEnteros aList){
        if (aList == null || aList.tamanio() < 2) return aList;

        ListaDeEnteros left = new ListaDeEnterosEnlazada();
        ListaDeEnteros right = new ListaDeEnterosEnlazada();
        ListaDeEnteros merged = new ListaDeEnterosEnlazada();

        aList.comenzar();

        for (int i=0; i<aList.tamanio()/2; i++) left.agregarFinal(aList.proximo());
        while (!aList.fin()) right.agregarFinal(aList.proximo());

        left = mergeSort(left);
        right = mergeSort(right);

        left.comenzar();
        right.comenzar();

        int numL = left.proximo();
        int numR = right.proximo();

        for (int i=0; i<aList.tamanio(); i++){
            if (numL < numR){
                merged.agregarFinal(numL);
                if (left.fin()) numL = Integer.MAX_VALUE;
                else numL = left.proximo();
            } else {
                merged.agregarFinal(numR);
                if (right.fin()) numR = Integer.MAX_VALUE;
                else numR = right.proximo();
            }
        }

        return merged;
    }

}
