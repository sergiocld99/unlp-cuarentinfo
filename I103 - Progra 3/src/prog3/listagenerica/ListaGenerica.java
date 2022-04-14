package prog3.listagenerica;

/**
 * ListaDeEnteros es una clase abstracta que define los métodos que deberán
 * implementar todas aquellas clases que quieran representar una coleccion de
 * números enteros, extendiendo esta clase
 * */
public abstract class ListaGenerica<T> {

	/** permite posicionarse al principio de la lista */
	public abstract void comenzar();

	/** permite avanzar al proximo elemento de lista */
	public abstract T proximo();

	/**
	 * devuelve true si nos encontramos en el último elemento de la lista, false
	 * en caso contrario
	 */
	public abstract boolean fin();

	/**
	 * devuelve el elemento que se encuentra en la posicion pos. Hay que
	 * recordar que la lista empieza en la posicion 0.
	 * 
	 * @param pos
	 *            posicion del elemento que se va a recuperar
	 */
	public abstract T elemento(int pos);

	/**
	 * agrega un elemento en la posicion pos indicada. Si hubiera un elemento en
	 * dicha posicion, el mismo se reubicará a continuación del elemento que
	 * vamos a agregar. Si pudo eliminarlo devuelve true, false en caso contrario.
	 * 
	 * @param elem
	 *            elemento a agregar
	 * @param pos
	 *            posicion donde deberá agregarse el elemento
	 * */
	public abstract boolean agregarEn(T elem, int pos);

	/**
	 * agrega un elemento al principio de la lista. Si pudo agregarlo devuelve true, false en caso contrario.
	 * 
	 * @param elem
	 *            elemento a agregar
	 * */
	public abstract boolean agregarInicio(T elem);

	/**
	 * agrega un elemento al final de la lista. Si pudo agregarlo devuelve true, false en caso contrario.
	 * 
	 * @param elem
	 *            elemento a agregar
	 * */
	public abstract boolean agregarFinal(T elem);

	/**
	 * elimina la primer ocurrencia del elemento elem indicado. Si pudo eliminarlo devuelve true, false en caso contrario.
	 * 
	 * @param elem
	 *            elemento a eliminar
	 * */
	public abstract boolean eliminar(T elem);

	/**
	 * elimina el elemento ubicado en la posicion pos. Si pudo eliminarlo devuelve true, false en caso contrario.
	 * 
	 * @param pos
	 *            posicion del elemento a eliminar
	 * */
	public abstract boolean eliminarEn(int pos);

	/**
	 * devuleve true si la lista contiene al elemento elem, false en caso
	 * contrario
	 * 
	 * @param elem
	 *            elemento a buscar en la lista
	 * */
	public abstract boolean incluye(T elem);

	/**
	 * devuelve true si la lista no contiene elemntos, false en caso contrario
	 * */
	public abstract boolean esVacia();

	/**
	 * devuelve un numero que representa la cantidad de elementos que contiene
	 * la lista
	 * */
	public abstract int tamanio();

}
