package prog3.arbol.avl;

public class NodoAVL<T extends Comparable<T>> {

	private T dato;
	//acceso package para que el arbol avl pueda accederlo
	int altura;
	private NodoAVL<T> hijoIzquierdo;
	private NodoAVL<T> hijoDerecho;
	
	public NodoAVL(T dato){		
		this.dato = dato;
		this.hijoIzquierdo = null;
		this.hijoDerecho = null;
	}
	
	public T getDato(){		
		return this.dato; 
	}
	
	public NodoAVL<T> getHijoIzquierdo(){		
		return this.hijoIzquierdo;
	}
	
	public NodoAVL<T> getHijoDerecho(){		
		return this.hijoDerecho;
	}	
	
	public void setDato(T dato){		
		this.dato = dato;
	}
	
	public void setHijoIzquierdo(NodoAVL<T> hijoIzq){		
		this.hijoIzquierdo = hijoIzq;
	}
	
	public void setHijoDerecho(NodoAVL<T> hijoDer){		
		this.hijoDerecho = hijoDer;
	}	
	
}
