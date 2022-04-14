package prog3.arbol.general;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;
import prog3.util.ColaGenerica;

public class ArbolGeneral<T> {

	private NodoGeneral<T> raiz;

	public ArbolGeneral() {

		this.raiz = null;
	}

	public ArbolGeneral(T dato) {

		this.raiz = new NodoGeneral<>(dato);

	}

	public ArbolGeneral(T dato, ListaGenerica<ArbolGeneral<T>> lista) {

		this(dato);
		ListaGenerica<NodoGeneral<T>> hijos = new ListaGenericaEnlazada<>();

		lista.comenzar();
		while (!lista.fin()) {
			ArbolGeneral<T> arbolTemp = lista.proximo();
			hijos.agregarFinal(arbolTemp.getRaiz());

		}

		raiz.setListaHijos(hijos);
	}

	private ArbolGeneral(NodoGeneral<T> nodo) {

		this.raiz = nodo;
	}

	private NodoGeneral<T> getRaiz() {

		return this.raiz;
	}

	public T getDatoRaiz() {

		return this.raiz.getDato();
	}

	public ListaGenerica<ArbolGeneral<T>> getHijos() {

		ListaGenerica<ArbolGeneral<T>> lista = new ListaGenericaEnlazada<ArbolGeneral<T>>();
		ListaGenerica<NodoGeneral<T>> hijos = this.getRaiz().getHijos();
		lista.comenzar();
		hijos.comenzar();

		while (!hijos.fin()) {
			lista.agregarFinal(new ArbolGeneral<>(hijos.proximo()));

		}

		return lista;
	}

	public void agregarHijo(ArbolGeneral<T> unHijo) {

		NodoGeneral<T> hijo = unHijo.getRaiz();
		this.raiz.getHijos().agregarFinal(hijo);
	}

	public void eliminarHijo(ArbolGeneral<T> unHijo) {

		NodoGeneral<T> hijo = unHijo.getRaiz();
		boolean ok = false;

		ListaGenerica<NodoGeneral<T>> listaHijos = this.getRaiz().getHijos();
		listaHijos.comenzar();

		while (!listaHijos.fin() && !ok) {

			NodoGeneral<T> hijoTemp = listaHijos.proximo();
			if (hijoTemp.getDato().equals(hijo.getDato())) {
				ok = listaHijos.eliminar(hijoTemp);

			}
		}

	}

	public boolean esHoja(){
		return this.getHijos().tamanio() == 0;
	}

	public boolean esVacio(){
		return this.getRaiz() == null;
	}

	// TODO: RECURSIVE ALL-IN-ONE
	public Integer altura() {
		if (this.esVacio()) return -1;
		if (this.esHoja()) return 0;
		int max = 0, altSub;

		ListaGenerica<ArbolGeneral<T>> hijos = this.getHijos();
		hijos.comenzar();

		while (!hijos.fin()){
			altSub = hijos.proximo().altura();
			if (altSub > max) max = altSub;
		}

		return ++max;		// ++ para incluir al nodo mismo
	}

	// TODO: RECURSIVE ALL-IN-ONE
	public boolean include(T dato) {
		if (!this.esVacio()){
			if (this.getDatoRaiz().equals(dato)) return true;
			if (!this.esHoja()) {
				ListaGenerica<ArbolGeneral<T>> hijos = this.getHijos();
				hijos.comenzar();

				while (!hijos.fin()){
					if (hijos.proximo().include(dato)) return true;
				}
			}
		}

		return false;
	}

	// TODO ALRIGHT! RECURSIVE ALL-IN-ONE
	public Integer nivel(T dato) {
		int subResult;

		if (!this.esVacio()) {
			if (this.getDatoRaiz().equals(dato)) return 0;
			if (!this.esHoja()) {
				ListaGenerica<ArbolGeneral<T>> hijos = this.getHijos();
				hijos.comenzar();

				while (!hijos.fin()) {
					subResult = hijos.proximo().nivel(dato);
					if (subResult >= 0) return ++subResult;		// ++ includes actual node
				}
			}
		}

		return -1;
	}

	// TODO: PER LEVEL - ALL IN ONE
	public Integer ancho() {
		if (this.esVacio()) return -1;
		int max = 0, actWidth = 0;

		ColaGenerica<ArbolGeneral<T>> cola = new ColaGenerica<>();
		cola.encolar(this);
		cola.encolar(null);

		ArbolGeneral<T> ag;

		while (!cola.esVacia()){
			ag = cola.desencolar();

			if (ag != null){
				ListaGenerica<ArbolGeneral<T>> hijos = ag.getHijos();
				hijos.comenzar();

				while (!hijos.fin()){
					cola.encolar(hijos.proximo());
					actWidth++;
				}
			} else if (actWidth > 0) {
				if (actWidth > max) max = actWidth;
				cola.encolar(null);
				actWidth = 0;
			}
		}

		return max;
	}

}
