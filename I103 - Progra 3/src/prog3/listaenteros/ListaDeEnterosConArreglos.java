package prog3.listaenteros;

/**
 * La clase ListaDeEnterosConArreglos es una ListaDeEnteros con capacidad fija y
 * limitada.
 * */
public class ListaDeEnterosConArreglos extends ListaDeEnteros {
	/*
	 * tamanio logico que representa la cantidad real de elementos que estan
	 * almacenados en la lista
	 */
	private int tamanio=0;
	/*
	 * conjunto de datos, en este caso, al estar incializado en 10, las
	 * posiciones validas van de 0 a 9
	 */
	private Integer[] datos = new Integer[10];
	/*
	 * posicion actual en el arreglo de datos. Util cuando se quiere recorrer el
	 * arreglo de datos
	 */
	private int actual = 0;

	@Override
	public void comenzar() {
		actual = 0;
	}

	@Override
	public Integer proximo() {
		return datos[actual++];
	}

	@Override
	public boolean fin() {
		return actual == tamanio;
	}

	@Override
	public Integer elemento(int pos) {
		return datos[pos];
	}

	@Override
	public boolean agregarEn(Integer elem, int pos) {
		if (pos < 0 || pos > datos.length || pos > tamanio
				|| tamanio == datos.length)
			return false;
		tamanio++;
		for (int i = tamanio - 1; i > pos; i--)
			datos[i] = datos[i - 1];
		datos[pos] = elem;
		return true;
	}

	@Override
	public boolean agregarInicio(Integer elem) {
		return this.agregarEn(elem, 0);
	}

	@Override
	public boolean agregarFinal(Integer elem) {
		datos[tamanio++] = elem;
		return true;
	}

	@Override
	public boolean eliminar(Integer elem) {
		boolean encontre = false;
		int i = 0;
		while (i < tamanio && !encontre) {
			if (datos[i].equals(elem)) {
				eliminarEn(i);
				encontre = true;
			}
			i++;
		}
		return encontre;
	}

	@Override
	public boolean eliminarEn(int pos) {
		if (pos < 0 || pos > tamanio-1)
			return false;
		
		for (int i = pos; i < tamanio-2; i++)
			datos[i] = datos[i+1];
		datos[tamanio-1]=null;
		tamanio--;
		return true;
	}

	@Override
	public boolean incluye(Integer elem) {
		boolean encontre = false;
		int i = 0;
		while (i < tamanio && !encontre) {
			if (datos[i].equals(elem)) {
				encontre = true;
			}
			i++;
		}
		return encontre;
	}

	@Override
	public int tamanio() {
		return tamanio;
	}

	@Override
	public boolean esVacia() {
		return (tamanio == 0);
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < this.tamanio; i++) {
			str = str + datos[i] + " -> ";
		}
		if (str.length() > 1)
			str = str.substring(0, str.length() - 4);

		return str;
	}

}
