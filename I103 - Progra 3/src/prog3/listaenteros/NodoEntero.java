package prog3.listaenteros;
/**
 * Esta clase representa un nodo de la lista de enteros enlazada.
 * */
public class NodoEntero {
	private Integer dato;
	private NodoEntero siguiente;
	
	public Integer getDato() {
		return dato;
	}
	public void setDato(Integer dato) {
		this.dato = dato;
	}
	public NodoEntero getSiguiente() {
		return siguiente;
	}
	public void setSiguiente(NodoEntero siguiente) {
		this.siguiente = siguiente;
	}

}