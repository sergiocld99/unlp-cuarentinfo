package prog3.arbol.binario;

import parciales.Par;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;
import prog3.util.ColaGenerica;

public class ArbolBinario<T> {

	private NodoBinario<T> raiz;
	
	public ArbolBinario(){		
		this.raiz = null;
	}
	
	public ArbolBinario(T dato){		
		this.raiz = new NodoBinario<T>(dato);
	}	
	
	private ArbolBinario(NodoBinario<T> nodo){		
		this.raiz = nodo;
	}
	
	private NodoBinario<T> getRaiz(){		
		return raiz;
	}
	
	public T getDatoRaiz(){
		if (this.getRaiz() != null){
			return this.getRaiz().getDato();
		}else{
			return null;
		}
	}
	
	public ArbolBinario<T> getHijoIzquierdo(){		
		return new ArbolBinario<T>(this.raiz.getHijoIzquierdo());
	}
	
	public ArbolBinario<T> getHijoDerecho(){		
		return new ArbolBinario<T>(this.raiz.getHijoDerecho());
	}	
	
	public void agregarHijoIzquierdo(ArbolBinario<T> hijo){		
		this.raiz.setHijoIzquierdo(hijo.getRaiz());
	}

	public void agregarHijoDerecho(ArbolBinario<T> hijo){		
		this.raiz.setHijoDerecho(hijo.getRaiz());
	}	
	
	public void eliminarHijoIzquierdo(){		
		this.raiz.setHijoIzquierdo(new NodoBinario<T>(null));
	}
	
	public void eliminarHijoDerecho(){		
		this.raiz.setHijoDerecho(new NodoBinario<T>(null));
	}	
	
	public boolean esVacio(){
		return this.raiz==null;
	}
	
	public boolean esHoja(){
		return this.getDatoRaiz()!=null && this.getHijoIzquierdo().esVacio() && this.getHijoDerecho().esVacio();
	}

	// TODO: FROM PRACTICE EXPLICATION, ALRIGHT!
	public ListaGenerica<T> frontera() {
		ListaGenerica<T> ret = new ListaGenericaEnlazada<>();

		if (!this.esVacio()){
			if (this.esHoja()) ret.agregarFinal(this.getDatoRaiz());
			else {
				ListaGenerica<T> left = this.getHijoIzquierdo().frontera();
				ListaGenerica<T> right = this.getHijoDerecho().frontera();

				left.comenzar();
				right.comenzar();

				while (!left.fin()) ret.agregarFinal(left.proximo());
				while (!right.fin()) ret.agregarFinal(right.proximo());
			}
		}

		return ret;
	}

	// TODO: FROM PDF 4.2 - AB
	public boolean lleno() {
		ColaGenerica<ArbolBinario<T>> cola = new ColaGenerica<>();
		ArbolBinario<T> ab;
		int cantN, level;

		cola.encolar(this);
		cola.encolar(null);
		cantN = level = 0;

		while(!cola.esVacia()){
			ab = cola.desencolar();
			if (ab != null) {
				if (!ab.getHijoIzquierdo().esVacio()){
					cola.encolar(ab.getHijoIzquierdo());
					cantN++;
				}
				if (!ab.getHijoDerecho().esVacio()){
					cola.encolar(ab.getHijoDerecho());
					cantN++;
				}
			} else if (!cola.esVacia()){
					if (cantN == Math.pow(2, ++level)){
						cola.encolar(null);
						cantN = 0;
					} else return false;
				}
		}

		return true;
	}

	// TODO: I THINK IT'S ALRIGHT!
	public boolean completo(){
		ColaGenerica<ArbolBinario<T>> cola = new ColaGenerica<>();
		ArbolBinario<T> ab;
		boolean onlyLeaves = false;

		cola.encolar(this);

		while (!cola.esVacia()){
			ab = cola.desencolar();
			if (ab.esHoja()) {
				onlyLeaves = true;
			} else {
				if (onlyLeaves || ab.getHijoIzquierdo().esVacio()) return false;
				cola.encolar(ab.getHijoIzquierdo());
				if (ab.getHijoDerecho().esVacio()) onlyLeaves = true;
				else cola.encolar(ab.getHijoDerecho());
			}
		}

		return true;
	}

	// todo ejercicio verano 2011
	public ArbolBinario<T> podar(){
		if (this.esVacio()) return this;
		else return this.copiar(this, 0, ultimoNivelCompleto(this));
	}

	private ArbolBinario<T> copiar(ArbolBinario<T> unArbol, int nivelAct, int nivelMax){
		ArbolBinario<T> nuevo = new ArbolBinario<>(unArbol.getDatoRaiz());
		if (nivelAct < nivelMax) {
			nuevo.agregarHijoIzquierdo(copiar(unArbol.getHijoIzquierdo(), nivelAct + 1, nivelMax));
			nuevo.agregarHijoDerecho(copiar(unArbol.getHijoDerecho(), nivelAct+1, nivelMax));
		}
		return nuevo;
	}

	private int ultimoNivelCompleto(ArbolBinario<T> unArbol){
		ColaGenerica<ArbolBinario<T>> cola = new ColaGenerica<>();
		ArbolBinario<T> aux;

		boolean elProximoNivelEsCompleto = true;
		int nivel = 0;

		cola.encolar(unArbol);
		cola.encolar(null);

		while (elProximoNivelEsCompleto){
			aux = cola.desencolar();
			if (aux != null){
				if (this.tieneAmbosHijos(aux)){
					cola.encolar(aux.getHijoIzquierdo());
					cola.encolar(aux.getHijoDerecho());
				} else elProximoNivelEsCompleto = false;
			} else {
				cola.encolar(null);
				nivel++;
			}
		}

		return nivel;
	}

	private boolean tieneAmbosHijos(ArbolBinario<T> nodo){
		return !nodo.getHijoIzquierdo().esVacio() && !nodo.getHijoDerecho().esVacio();
	}

	// todo ejercicio verano 2011 en preorden
	public ArbolBinario<Par> anotarMaximos(ArbolBinario<Integer> arbolDeEnteros){
		Par datoRaiz = new Par();
		datoRaiz.setDatoIzq(hallarMaximo(arbolDeEnteros.getHijoIzquierdo()));
		datoRaiz.setDatoDer(hallarMaximo(arbolDeEnteros.getHijoDerecho()));
		ArbolBinario<Par> retorno = new ArbolBinario<>(datoRaiz);

		if (!arbolDeEnteros.getHijoIzquierdo().esVacio())
			retorno.agregarHijoIzquierdo(anotarMaximos(arbolDeEnteros.getHijoIzquierdo()));
		if (!arbolDeEnteros.getHijoDerecho().esVacio())
			retorno.agregarHijoDerecho(anotarMaximos(arbolDeEnteros.getHijoDerecho()));

		return retorno;
	}

	private int hallarMaximo(ArbolBinario<Integer> unArbol){
		if (unArbol == null || unArbol.esVacio()) return -1;
		if (unArbol.esHoja()) return unArbol.getDatoRaiz();

		int maxIzq = hallarMaximo(unArbol.getHijoIzquierdo());
		int maxDer = hallarMaximo(unArbol.getHijoDerecho());
		return Math.max(maxIzq, maxDer);
	}
}