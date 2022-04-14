package personajes;
/**
 * 
 */

/**
 * Representa una ubicación en el plano dada por un par de valores (x,y)
 * @author Calderón Sergio, Ercoli Juan Martín
 * @version 1
 */
public class Posicion {
	private int x, y;

	/**
	 * Constructor que permite inicializar variables
	 * @param x el valor de la abscisa (eje X)
	 * @param y el valor de la ordenada (eje Y)
	 */
	public Posicion(int x, int y) {
		this.cambiar(x, y);
	}

	/**
	 * Constructor por defecto, sin parámetros
	 */
	public Posicion() {
		
	}

	/**
	 * Permite obtener la abscisa
	 * @return el valor del atributo x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Permite establecer la abscisa
	 * @param x el valor del atributo x a asignar
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Permite obtener la ordenada
	 * @return el valor del atributo y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Permite establecer la ordenada
	 * @param y el valor del atributo y a asignar
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	
	/**
	 * Método que permite actualizar la coordenada
	 * @param x el nuevo valor de la abscisa (eje X)
	 * @param y el nuevo valor de la ordenada (eje Y)
	 */
	public void cambiar(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Método para obtener la coordenada como vector en R2
	 * @return un arreglo con el valor de x e y
	 */
	public int[] getVector() {
		return new int[] {this.getX(), this.getY()};
	}
	
}
