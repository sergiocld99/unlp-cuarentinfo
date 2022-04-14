package prog3.arbol.binario.util;

import prog3.arbol.binario.ArbolBinario;
import prog3.listaenteros.ListaDeEnteros;
import prog3.listaenteros.ListaDeEnterosEnlazada;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Recorrido<T> {

	public void imprimirPreOrder(ArbolBinario<T> aBinario) {
		if (aBinario == null) return;
		System.out.print(aBinario.getDatoRaiz() + " ");
		if (!aBinario.getHijoIzquierdo().esVacio()) imprimirPreOrder(aBinario.getHijoIzquierdo());
		if (!aBinario.getHijoDerecho().esVacio()) imprimirPreOrder(aBinario.getHijoDerecho());
	}

	public void imprimirInOrder(ArbolBinario<T> aBinario) {
		if (aBinario == null) return;
		if (!aBinario.getHijoIzquierdo().esVacio()) imprimirInOrder(aBinario.getHijoIzquierdo());
		System.out.print(aBinario.getDatoRaiz() + " ");
		if (!aBinario.getHijoDerecho().esVacio()) imprimirInOrder(aBinario.getHijoDerecho());
	}

	public void imprimirPostOrder(ArbolBinario<T> aBinario) {
		if (aBinario == null) return;
		if (!aBinario.getHijoIzquierdo().esVacio()) imprimirPostOrder(aBinario.getHijoIzquierdo());
		if (!aBinario.getHijoDerecho().esVacio()) imprimirPostOrder(aBinario.getHijoDerecho());
		System.out.print(aBinario.getDatoRaiz() + " ");
	}

	public static void main(String[] args){
		// TODO: All right!
		Recorrido<Integer> recorrido= new Recorrido<>();
		//recorrido.imprimirPreOrder(arbolBinarioB);
		//recorrido.imprimirInOrder(arbolBinarioB);
		//recorrido.imprimirPostOrder(arbolBinarioB);
	}
}
