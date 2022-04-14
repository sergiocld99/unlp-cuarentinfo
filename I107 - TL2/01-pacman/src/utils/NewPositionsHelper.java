package utils;

import board.Tablero;
import personajes.Direccion;

/**
 * Utilidad para calcular y verificar cambios de posición válidos
 * @author Calderón Sergio, Ercoli Juan Martín
 * @version 1
 */
public class NewPositionsHelper {
	private Tablero tabReferencia;

	/**
	 * Constructor por defecto, sin parámetros
	 */
	public NewPositionsHelper() {
		
	}
	
	/**
	 * Asigna el tablero con el cual se efectuarán los cálculos
	 * @param unTablero El tablero de la partida actual, 
	 * debe ser la misma instancia para todos los personajes
	 */
	public void referenciarTablero(Tablero unTablero) {
		this.tabReferencia = unTablero;
	}
	
	/**
	 * Calcula y devuelve vía parámetro la siguiente posición válida en la dirección solicitada
	 * @param posActual Los valores (x,y) actuales antes de efectuar el avance
	 * @param direccion En cuál de las 4 direcciones definidas se está moviendo
	 */
	public void getPosInmediata(int[] posActual, Direccion direccion) {
		// Implementación
	}
	
	/**
	 * Calcula y devuelve vía parámetro una futura posición válida en la dirección solicitada
	 * @param posActual Los valores (x,y) actuales antes de efectuar el avance
	 * @param direccion En cuál de las 4 direcciones definidas se está moviendo
	 * @param delta El número de celdas por delante que se solicita calcular
	 */
	public void getPosConDelta(int[] posActual, Direccion direccion, int delta) {
		// Implementación
	}
	
	/**
	 * Calcula y devuelve vía parámetro la posición luego de atravesar un túnel
	 * @param posicion Los valores (x,y) correspondientes al túnel de ingreso
	 * @param direccion En cuál de las 4 direcciones definidas ingresó al túnel
	 */
	public void getPosInversa(int[] posicion, Direccion direccion) {
		// Implementación
	}
	
	/**
	 * Calcula la n-ésima celda válida que forma parte del espacio de la casa
	 * @param n El número índice (comenzado en 0) para elegir una de las celdas
	 * @return Un vector (x,y) correspondiente a la casa
	 */
	public int[] getPosCasaSegunIndice(int n) {
		int[] allCoords = this.tabReferencia.getEspacioCasa();
		return new int[] {allCoords[n*2], allCoords[n*2+1]};
	}
	
	/**
	 * Realiza una resta entre el número de puntos iniciales y restantes, útil para Inky
	 * @return El número total de puntos/bolitas comidas del tablero
	 */
	public int getPuntosComidos() {
		return this.tabReferencia.getCantPuntosInicio() - this.tabReferencia.getCantPuntosRestantes();
	}
	
	/**
	 * Realiza un cociente entre el número de puntos comidos y los iniciales, útil para Clyde
	 * @return El porcentaje (entre 0 y 100) de puntos/bolitas comidas del tablero
	 */
	public int getPorcentajeComido() {
		return this.getPuntosComidos() * 100 / this.tabReferencia.getCantPuntosInicio();
	}
	
	/**
	 * Genera coordenadas aleatorias hasta encontrar una posición transitable del tablero,
	 *  útil para Clyde y para los fantasmas cuando entran en modo asustado
	 * @return Un vector de valores (x,y) que es válido para una entidad
	 */
	public int[] getRandomPosValida() {
		int[] resultado = new int[2];
		do {
			resultado[0] = (int) (Math.random() * Tablero.MAX_SIZE);
			resultado[1] = (int) (Math.random() * Tablero.MAX_SIZE);
		} while (!this.validarPosicion(resultado));
		
		return resultado;
	}
	
	/**
	 * Verifica mediante el tablero referenciado si la posición es válida
	 * @param vector el par de valores (x,y) a consultar
	 * @return si la celda calculada es transitable
	 */
	public boolean validarPosicion(int[] vector) {
		return this.tabReferencia.esTransitable(vector[0], vector[1]);
	}
}
