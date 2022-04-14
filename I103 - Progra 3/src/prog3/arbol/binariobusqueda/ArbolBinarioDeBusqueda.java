package prog3.arbol.binariobusqueda;

import prog3.arbol.binario.NodoBinario;

public class ArbolBinarioDeBusqueda<T extends Comparable<T>> {

	private NodoBinario<T> raiz;

	public ArbolBinarioDeBusqueda(T dato) {
		this.raiz = new NodoBinario<T>(dato);
	}

	private ArbolBinarioDeBusqueda(NodoBinario<T> nodo) {
		this.raiz = nodo;
	}

	private NodoBinario<T> getRaiz() {
		return raiz;
	}

	public T getDatoRaiz() {
		if (this.getRaiz() != null) {
			return this.getRaiz().getDato();
		} else {
			return null;
		}
	}

	public ArbolBinarioDeBusqueda<T> getHijoIzquierdo() {
		return new ArbolBinarioDeBusqueda<T>(this.raiz.getHijoIzquierdo());
	}

	public ArbolBinarioDeBusqueda<T> getHijoDerecho() {
		return new ArbolBinarioDeBusqueda<T>(this.raiz.getHijoDerecho());
	}

	private NodoBinario<T> buscar(Comparable<T> x, NodoBinario<T> t) {
		if (t != null) {
			if (x.compareTo(t.getDato()) < 0) {
				t = this.buscar(x, t.getHijoIzquierdo());
			} else if (x.compareTo(t.getDato()) > 0) {
				t = this.buscar(x, t.getHijoDerecho());
			} else
				; // Se encontro el nodo, asi que es t
			return t;
		} else {
			return null;
		}
	}

	public boolean incluye(Comparable<T> dato) {
		return buscar(dato, this.raiz) != null;
	}

	public T buscar(T dato) {
		NodoBinario<T> result = this.buscar(dato, this.getRaiz());
		if (result != null) {
			return result.getDato();
		}
		return null;
	}

	public void eliminar(T dato) {
		this.raiz = this.eliminar(dato, raiz);
	}

	private NodoBinario<T> eliminar(T dato, NodoBinario<T> t) {
		if (t.getDato().compareTo(dato) > 0) {
			if (t.getHijoIzquierdo() != null)
				t.setHijoIzquierdo(this.eliminar(dato, t.getHijoIzquierdo()));
		} else if (t.getDato().compareTo(dato) < 0) {
			if (t.getHijoDerecho() != null)
				t.setHijoDerecho(this.eliminar(dato, t.getHijoDerecho()));
		} else if (t.getDato().compareTo(dato) == 0) {
			if (t.getHijoIzquierdo() != null && t.getHijoDerecho() != null) {
				NodoBinario<T> menor = this.buscarMayorDeLosMenores(t);
				t.setDato(menor.getDato());
				t.setHijoIzquierdo(this.eliminar(menor.getDato(), t.getHijoIzquierdo()));
			} else {
				// tiene un solo hijo o es hoja
				if (t.getHijoIzquierdo() != null)
					t = t.getHijoIzquierdo();
				else
					t = t.getHijoDerecho();
			}
		}
		return t;
	}

	private NodoBinario<T> buscarMayorDeLosMenores(NodoBinario<T> t) {
		if (t.getHijoIzquierdo()!=null){
			NodoBinario<T> maxDerecho = t.getHijoIzquierdo();
			while (maxDerecho.getHijoDerecho()!=null){
				maxDerecho=maxDerecho.getHijoDerecho();
			}
			return maxDerecho;
		}
		return t;
	}

	public boolean esVacio() {
		return (this.getRaiz() == null);
	}

	public boolean esHoja() {
		return this.getDatoRaiz() != null && this.getHijoIzquierdo().esVacio() && this.getHijoDerecho().esVacio();
	}

	public void imprimirInOrden() {
		if (this.esVacio())
			System.out.println("Arbol Vacio");
		else
			imprimirInOrden(this.raiz);
	}

	private void imprimirInOrden(NodoBinario<T> t) {
		if (t != null) {
			imprimirInOrden(t.getHijoIzquierdo());
			System.out.println(t.getDato());
			imprimirInOrden(t.getHijoDerecho());
		}
	}

	public void agregar(T dato) {
		if (raiz == null)
			raiz = new NodoBinario<T>(dato);
		else
			this.agregar(dato, raiz);
	}

	private void agregar(T dato, NodoBinario<T> r) {
		if (dato.compareTo(r.getDato()) < 0)
			if (r.getHijoIzquierdo() == null)
				r.setHijoIzquierdo(new NodoBinario<T>(dato));
			else
				this.agregar(dato, r.getHijoIzquierdo());
		else if (dato.compareTo(r.getDato()) > 0)
			if (r.getHijoDerecho() == null)
				r.setHijoDerecho(new NodoBinario<T>(dato));
			else
				this.agregar(dato, r.getHijoDerecho());
	}

}
