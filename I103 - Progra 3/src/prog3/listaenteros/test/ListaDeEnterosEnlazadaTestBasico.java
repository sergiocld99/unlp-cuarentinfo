package prog3.listaenteros.test;

import prog3.listaenteros.ListaDeEnterosEnlazada;

public class ListaDeEnterosEnlazadaTestBasico {
    public static void main(String[] args) {
        ListaDeEnterosEnlazada mList = new ListaDeEnterosEnlazada();
        mList.agregarInicio(18);
        mList.agregarFinal(1999);
        mList.agregarEn(8,1);

        //System.out.println(mList);
        mList.comenzar();
        recursive(mList);
    }

    public static void recursive(ListaDeEnterosEnlazada mList){
        if (!mList.fin()){
            int num = mList.proximo();  // this method returns ACTUAL element
            recursive(mList);
            System.out.print(num + " ");
        }
    }
}
