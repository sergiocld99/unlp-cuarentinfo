/**
 * 
 */
package personajes;

import board.Tablero;

/**
 * Representa el personaje manejado por el usuario. Además de los atributos heredados
 * de entidad (posición, ángulo, etc), tiene una cantidad de vidas y un booleano que 
 * indica si está en movimiento (esto último es para los gráficos).
 * @author Calderón Sergio, Ercoli Juan Martín
 * @version 1
 */
public class Pacman extends Entidad {
	private int vidas;
	private boolean enMovimiento;
	
	/**
	 * Constructor por defecto, sin parámetros
	 */
	public Pacman() {
		super();
	}

	/**
	 * Constructor que permite referenciar el tablero del cual se puede consultar las celdas para avanzar
	 * @param tablero la instancia del tablero utilizada por la partida y el resto de personajes
	 */
	public Pacman(Tablero tablero) {
		super(tablero, false);
	}
	
	

	/**
	 * Constructor que permite referenciar el tablero y asignar una cantidad de vidas
	 * @param tablero la instancia del tablero utilizada por la partida y 
	 * el resto de personajes
	 * @param vidas la cantidad de vidas al inicio de la partida
	 */
	public Pacman(Tablero tablero, int vidas) {
		this(tablero);
		this.vidas = vidas;
	}

	@Override
	public void resetearPosicion() {
		super.getPosicion().cambiar(16, 20);
		super.setAngulo(Direccion.LEFT);
		super.setVelocidadEnMs(900);
		this.setEnMovimiento(true);
	}

	/**
	 * Permite obtener la cantidad de vidas actuales de PacMan (entero positivo)
	 * @return el valor del atributo vidas
	 */
	public int getVidas() {
		return vidas;
	}

	/**
	 * Permite establecer la cantidad de vidas actuales de PacMan (entero positivo)
	 * @param vidas el valor del atributo vidas a asignar
	 */
	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	/**
	 * Permite conocer si el PacMan se encuentra desplazándose (true) o está quieto (false)
	 * @return el valor del atributo enMovimiento
	 */
	public boolean isEnMovimiento() {
		return enMovimiento;
	}

	/**
	 * Permite cambiar el valor del atributo que indica si está desplazándose actualmente
	 * @param enMovimiento el valor del atributo enMovimiento a asignar
	 */
	public void setEnMovimiento(boolean enMovimiento) {
		this.enMovimiento = enMovimiento;
	}
	
	/**
	 * Decrementa en uno la cantidad de vidas actuales de PacMan
	 * @return "true" si se acabaron las vidas (la partida debe terminar)
	 */
	public boolean restarVida() {
		this.animacionPerder();
		return (--this.vidas) == 0;
	}

	/**
	 * Realiza un giro del personaje durante unos segundos indicando que perdió una vida
	 */
	private void animacionPerder() {
		this.setEnMovimiento(false);
		for (int i=0; i<8; i++) {
			super.girarHorario();
			// delay
		}
	}
}
