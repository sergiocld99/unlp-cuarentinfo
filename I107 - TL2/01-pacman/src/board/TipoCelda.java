package board;

/**
 * Enumerativo con los objetos no desplazables del tablero. 
 * Se restringen a los siguientes: LIBRE (transitable sin punto), PARED (obstáculo), 
 * CASA (exclusivo para fantasmas), TUNEL, PUNTO y FRUTA (también transitables).
 * 
 * @author Calderón Sergio, Ercoli Juan Martín
 * @version 1
 */
public enum TipoCelda {
	LIBRE, PARED, CASA, TUNEL, PUNTO, FRUTA
}
