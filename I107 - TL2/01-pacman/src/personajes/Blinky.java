/**
 * 
 */
package personajes;

import board.Tablero;

/**
 * Representa al fantasma llamado Blinky, el cual se encarga de perseguir a PacMan en
 * todo momento si es posible
 * @author Calderón Sergio, Ercoli Juan Martín
 * @version 1
 */
public class Blinky extends Fantasma {

	/**
	 * Constructor por defecto, sin parámetros
	 */
	public Blinky() {
		super();
	}
	
	/**
	 * Constructor que permite referenciar el tablero y la entidad a perseguir
	 * @param tablero la instancia del tablero utilizada por la partida y el resto de personajes
	 * @param pacman La instancia del PacMan que maneja el agente/usuario
	 */
	public Blinky(Tablero tablero, Pacman pacman) {
		super(tablero, pacman, 1);
	}

	@Override
	public void resetearPosicion() {
		super.setEstaEnCasa(true);
		super.setModo(ModoJuego.PERSECUCION);
		super.setAngulo(Direccion.LEFT);
		int[] posInicio = super.getHelper().getPosCasaSegunIndice(1);
		super.getPosicion().cambiar(posInicio[0], posInicio[1]);
	}

	@Override
	public void notificarPosPacman() {
		Posicion posPacman = super.getPacman().getPosicion();
		super.cambiarObjetivo(posPacman.getX(), posPacman.getY());
	}

}
