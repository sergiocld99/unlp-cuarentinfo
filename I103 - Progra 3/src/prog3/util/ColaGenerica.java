package prog3.util;

import prog3.listagenerica.ListaGenericaEnlazada;

public class ColaGenerica<T> {
	private ListaGenericaEnlazada<T> datos = new ListaGenericaEnlazada<T>();

	public void encolar(T elemento) {
		this.datos.agregarFinal(elemento);
	}

	public T desencolar() {
		T elemento = this.datos.elemento(0);
		this.datos.eliminarEn(0);
		return elemento;
	}

	public T tope() {
		return this.datos.elemento(0);
	}

	public boolean esVacia() {
		return this.datos.esVacia();

	}

}
