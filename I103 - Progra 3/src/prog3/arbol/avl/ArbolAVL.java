package prog3.arbol.avl;



public class ArbolAVL<T extends Comparable<T>> {

	private NodoAVL <T> raiz;

	
	public ArbolAVL(T dato) {
		this.raiz = new NodoAVL<T>(dato);
	}

	private ArbolAVL(NodoAVL<T> nodo) {
		this.raiz = nodo;
	}

	private NodoAVL<T> getRaiz() {
		return raiz;
	}

	public T getDatoRaiz() {
		if (this.getRaiz() != null) {
			return this.getRaiz().getDato();
		} else {
			return null;
		}
	}

	public ArbolAVL<T> getHijoIzquierdo() {
		return new ArbolAVL<T>(this.raiz.getHijoIzquierdo());
	}

	public ArbolAVL<T> getHijoDerecho() {
		return new ArbolAVL<T>(this.raiz.getHijoDerecho());
	}

	private NodoAVL<T> buscar(T x, NodoAVL<T> t) {
		if (t != null) {
			if (x.compareTo(t.getDato()) < 0) {
				t = this.buscar(x, t.getHijoIzquierdo());
			} else if (x.compareTo(t.getDato()) > 0) {
				t = this.buscar(x, t.getHijoDerecho());
			} 
			// Se encontro el nodo, asi que es t
			return t;
		} else {
			return null;
		}
	}

	public boolean incluye(T x) {
		return buscar(x, this.raiz) != null;
	}

	public T buscar(T dato) {
		NodoAVL<T> nodo = buscar(dato, this.raiz);
		if (nodo != null) {
			return nodo.getDato();
		} else {
			return null;
		}
	}


	public void agregar(T dato) {
		this.raiz=this.agregarDato(dato, this.getRaiz());

	}

	private NodoAVL<T> agregarDato(T x, NodoAVL<T> t) {
		 if( t == null )
             t = new NodoAVL<T>( x);
         else if( x.compareTo( t.getDato() ) < 0 )
         {
             t.setHijoIzquierdo(agregarDato( x, t.getHijoIzquierdo()));
             if( altura( t.getHijoIzquierdo() ) - altura( t.getHijoDerecho()) == 2 )
                 if( x.compareTo( t.getHijoIzquierdo().getDato()) < 0 )
                     t = rotacionSimpleIzq( t );
                 else
                     t = rotacionDobleIzq(t );
         }
         else if( x.compareTo( t.getDato() ) > 0 )
         {
             t.setHijoDerecho(agregarDato( x, t.getHijoDerecho()));
             if( altura( t.getHijoDerecho()) - altura( t.getHijoIzquierdo()) == 2 )
                 if( x.compareTo( t.getHijoDerecho().getDato()) > 0 )
                     t = rotacionSimpleDer( t );
                 else
                     t = rotacionDobleDer( t );
         }
         else
             ;  // Duplicate; do nothing
         t.altura = max( altura( t.getHijoIzquierdo() ), altura( t.getHijoDerecho()) ) + 1;
         return t;
		
	}

	public void eliminar(T dato) {
		// Falta implementar. 
	}

	public boolean esVacio() {
		return (this.getDatoRaiz() == null);
	}
	
	private NodoAVL<T> rotacionSimpleIzq(NodoAVL<T> nodo){
		 NodoAVL<T> k1 = nodo.getHijoIzquierdo();
         nodo.setHijoIzquierdo(k1.getHijoDerecho());
         k1.setHijoDerecho(nodo);
         nodo.altura = max( altura( nodo.getHijoIzquierdo()), altura( nodo.getHijoDerecho()) ) + 1;
         k1.altura = max( altura( k1.getHijoIzquierdo()), nodo.altura) + 1;
         return k1;
	}
	
	private NodoAVL<T> rotacionSimpleDer(NodoAVL<T> nodo){
		NodoAVL<T> k2 = nodo.getHijoDerecho();
        nodo.setHijoDerecho(k2.getHijoIzquierdo());
        k2.setHijoIzquierdo(nodo);
        nodo.altura = max( altura( nodo.getHijoIzquierdo()), altura( nodo.getHijoDerecho()) ) + 1;
        k2.altura = max( altura( k2.getHijoDerecho()), nodo.altura) + 1;
        return k2;
	}
	
	private NodoAVL<T>  rotacionDobleIzq(NodoAVL<T> nodo){
		nodo.setHijoIzquierdo(rotacionSimpleDer(nodo.getHijoIzquierdo()));
        return rotacionSimpleIzq( nodo);
	}
	
	private NodoAVL<T>  rotacionDobleDer(NodoAVL<T> nodo){
		nodo.setHijoDerecho(rotacionSimpleIzq( nodo.getHijoDerecho() ));
        return rotacionSimpleDer( nodo );
	}

    private int altura(NodoAVL<T>   t )
    {
        return t == null ? -1 : t.altura;
    }
	private int max( int param1, int param2 )
    {
        return param1 > param2 ? param1 : param2;
    }

	
	public void printTree( )
    {
        if( this.esVacio())
            System.out.println( "Arbol Vacio" );
        else
            printTree(this.raiz);
    }
	
	 private void printTree( NodoAVL<T> t )
     {
         if( t != null )
         {
             printTree( t.getHijoIzquierdo() );
             System.out.println( t.getDato());
             printTree( t.getHijoDerecho());
         }
     }
	 
	 public boolean esHoja() {
			return this.getDatoRaiz() != null && this.getHijoIzquierdo().esVacio() && this.getHijoDerecho().esVacio();
	}
}