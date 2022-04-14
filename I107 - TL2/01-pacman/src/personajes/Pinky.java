/**
 * 
 */
package personajes;

import board.Tablero;

/**
 * Representa al fantasma llamado Pinky, el cual trata de emboscar a PacMan 
 * ubicando su objetivo 4 posiciones por delante
 * @author Calderón Sergio, Ercoli Juan Martín
 * @version 1
 */
public class Pinky extends Fantasma {
	private static final int CELDAS_DELANTE = 4;
	
	/**
	 * Constructor por defecto, sin parámetros
	 */
	public Pinky() {
		super();
	}

	/**
	 * Constructor que permite referenciar el tablero y la entidad a perseguir
	 * @param tablero la instancia del tablero utilizada por la partida y el resto de personajes
	 * @param pacman La instancia del PacMan que maneja el agente/usuario
	 */
	public Pinky(Tablero tablero, Pacman pacman) {
		super(tablero, pacman, 7);
	}
	
	@Override
	public void resetearPosicion() {
		super.setEstaEnCasa(true);
		super.setModo(ModoJuego.PERSECUCION);
		super.setAngulo(Direccion.UP);
		int[] posInicio = super.getHelper().getPosCasaSegunIndice(0);
		super.getPosicion().cambiar(posInicio[0], posInicio[1]);
	}

	@Override
	public void notificarPosPacman() {
		int[] vector = super.getPacman().getPosicion().getVector();
		super.getHelper().getPosConDelta(vector, super.getPacman().getAngulo(), CELDAS_DELANTE);
		super.cambiarObjetivo(vector[0], vector[1]);
	}
}
