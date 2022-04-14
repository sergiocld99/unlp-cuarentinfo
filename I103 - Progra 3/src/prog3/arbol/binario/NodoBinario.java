package prog3.arbol.binario;

public class NodoBinario<T> {

	private T dato;
	private NodoBinario<T> hijoIzquierdo;
	private NodoBinario<T> hijoDerecho;
	
	public NodoBinario(T dato){
		this.dato = dato;
		this.hijoIzquierdo = null;
		this.hijoDerecho = null;
	}
	
	public T getDato(){		
		return this.dato; 
	}
	
	public NodoBinario<T> getHijoIzquierdo(){		
		return this.hijoIzquierdo;
	}
	
	public NodoBinario<T> getHijoDerecho(){		
		return this.hijoDerecho;
	}	
	
	public void setDato(T dato){		
		this.dato = dato;
	}
	
	public void setHijoIzquierdo(NodoBinario<T> hijoIzq){		
		this.hijoIzquierdo = hijoIzq;
	}
	
	public void setHijoDerecho(NodoBinario<T> hijoDer){		
		this.hijoDerecho = hijoDer;
	}	
}
