package prog3.listaenteros.test;

import prog3.listaenteros.ListaDeEnteros;
import prog3.listaenteros.ListaDeEnterosEnlazada;
import prog3.util.UtilitariosLista;

import java.util.Random;

public class TestOrdenamientos {
    public static void main(String[] args) {
        Random random = new Random();

        ListaDeEnteros mList = new ListaDeEnterosEnlazada();
        for (int i=0; i < 10; i++) mList.agregarFinal(random.nextInt(100));
        System.out.println(mList);

        mList = UtilitariosLista.mergeSort(mList);
        System.out.println(mList);

        ListaDeEnteros mList2 = new ListaDeEnterosEnlazada();
        mList2.agregarFinal(18);
        mList2.agregarFinal(27);
        mList2.agregarFinal(36);

        mList2 = ((ListaDeEnterosEnlazada) mList2).sort();
        System.out.println(mList2);

        mList = ((ListaDeEnterosEnlazada) mList).combineSort(mList2);
        System.out.print(mList);
    }
}
