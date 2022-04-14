package prog3.arbol.binario.util;

import prog3.arbol.binario.ArbolBinario;
import prog3.listaenteros.ListaDeEnteros;
import prog3.listaenteros.ListaDeEnterosEnlazada;
import prog3.util.ColaGenerica;

public class Utiles {

    public int sumaMaximaVertical(ArbolBinario<Integer> ab){
        int sum = 0;

        if (ab != null && !ab.esVacio()){
            int left = sumaMaximaVertical(ab.getHijoIzquierdo());
            int right = sumaMaximaVertical(ab.getHijoDerecho());
            sum = ab.getDatoRaiz() + Math.max(left, right);
        }

        return sum;
    }

    public int sumaMaximaHorizontal(ArbolBinario<Integer> ab){
        ColaGenerica<ArbolBinario<Integer>> cola = new ColaGenerica<>();
        ArbolBinario<Integer> aux;
        int levelSum, maxSum;

        cola.encolar(ab);
        cola.encolar(null);
        levelSum = maxSum = 0;

        while (!cola.esVacia()){
            aux = cola.desencolar();

            if (aux != null){
                levelSum += aux.getDatoRaiz();
                if (!aux.getHijoIzquierdo().esVacio()) cola.encolar(aux.getHijoIzquierdo());
                if (!aux.getHijoDerecho().esVacio()) cola.encolar(aux.getHijoDerecho());
            } else {
                if (levelSum > maxSum) maxSum = levelSum;
                if (!cola.esVacia()) cola.encolar(null);
                levelSum = 0;
            }
        }

        return maxSum;
    }

    // todo funcionando, recursion es lo mas eficiente e intuitivo :)
    public ListaDeEnteros trayectoriaPesada(ArbolBinario<Integer> ab){
        ListaDeEnteros retorno = new ListaDeEnterosEnlazada();
        this.trayectoriaPesada(ab, 0, 0, retorno);
        return retorno;
    }

    private void trayectoriaPesada(ArbolBinario<Integer> ab, int sumaParcial, int nivel, ListaDeEnteros l){
        if (ab == null || ab.esVacio()) return;
        int sumaActual = sumaParcial + ab.getDatoRaiz() * nivel;

        if (ab.esHoja()) l.agregarFinal(sumaActual);
        else {
            this.trayectoriaPesada(ab.getHijoIzquierdo(), sumaActual, nivel+1, l);
            this.trayectoriaPesada(ab.getHijoDerecho(), sumaActual, nivel+1, l);
        }
    }
}
