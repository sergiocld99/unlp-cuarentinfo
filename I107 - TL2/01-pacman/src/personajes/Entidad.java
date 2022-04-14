/**
 * 
 */
package personajes;

import utils.NewPositionsHelper;
import java.awt.Image;
import board.Tablero;

/**
 * Representa un objeto que puede moverse por el tablero durante la partida, el
 * cual tiene una {@link Posicion}, una {@link Direccion} a donde se encuentra mirando
 * actualmente, una utilidad {@link NewPositionsHelper} para efectuar cálculos entre 
 * celdas y comprobar posiciones válidas para avanzar, un booleano que indica si es 
 * autómata (controlado por la CPU) o manejado por el usuario, y la velocidad.
 * @author Calderón Sergio, Ercoli Juan Martín
 * @version 1
 */
public abstract class Entidad {
	private Posicion posicion;
	private Direccion angulo;
	private NewPositionsHelper helper;
	private Image graficos;
	private boolean manejadoPorCPU;
	private int velocidadEnMs;
	
	/**
	 * Constructor por defecto, sin parámetros
	 */
	public Entidad() {
		this.helper = new NewPositionsHelper();
		this.posicion = new Posicion();
	}
	
	/**
	 * Constructor que permite referenciar el tablero y asignar si es entidad autómata
	 * @param tablero la instancia del tablero utilizada por la partida y el resto de personajes
	 * @param manejadoPorCPU booleano que debe ser true si la CPU se encarga de moverlo por el tablero
	 */
	public Entidad(Tablero tablero, boolean manejadoPorCPU) {
		this();
		this.helper.referenciarTablero(tablero);
		this.manejadoPorCPU = manejadoPorCPU;
	}

	/**
	 * Permite obtener la posición actual de la entidad
	 * @return el valor del atributo posicion
	 */
	public Posicion getPosicion() {
		return posicion;
	}

	/**
	 * Permite cambiar la posición de la entidad.
	 * Se recomienda usar getPosicion().cambiar(x,y) para evitar crear instancias
	 * @param posicion el valor del atributo posicion a asignar
	 */
	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	/**
	 * Permite obtener la dirección a la cual se encuentra mirando la entidad actualmente
	 * @return el valor del atributo angulo
	 */
	public Direccion getAngulo() {
		return angulo;
	}

	/**
	 * Permite cambiar la dirección (4 posibles) que está mirando la entidad
	 * @param angulo el valor del atributo angulo a asignar
	 */
	public void setAngulo(Direccion angulo) {
		this.angulo = angulo;
	}


	/**
	 * @return el valor del atributo graficos
	 */
	public Image getGraficos() {
		return graficos;
	}

	/**
	 * @param graficos el valor del atributo graficos a asignar
	 */
	public void setGraficos(Image graficos) {
		this.graficos = graficos;
	}

	/**
	 * Permite conocer si la entidad es autómata (true) o manejada por el usuario (false)
	 * @return el valor del atributo manejadoPorCPU
	 */
	public boolean isManejadoPorCPU() {
		return manejadoPorCPU;
	}

	/**
	 * Permite establecer si la entidad es autómata (true) o manejada por el usuario (false)
	 * @param manejadoPorCPU el valor del atributo manejadoPorCPU a asignar
	 */
	public void setManejadoPorCPU(boolean manejadoPorCPU) {
		this.manejadoPorCPU = manejadoPorCPU;
	}

	/**
	 * Permite obtener la velocidad del personaje
	 * @return el valor del atributo velocidadEnMs
	 */
	public int getVelocidadEnMs() {
		return velocidadEnMs;
	}

	/**
	 * Permite cambiar la velocidad del personaje
	 * @param velocidadEnMs el valor del atributo velocidadEnMs a asignar
	 */
	public void setVelocidadEnMs(int velocidadEnMs) {
		this.velocidadEnMs = velocidadEnMs;
	}

	/**
	 * Restaura la ubicación por defecto, importante para inicializar y útil 
	 * cuando se debe "recomenzar la partida", ej: cuando PacMan pierde una vida
	 */
	public abstract void resetearPosicion();
	
	/**
	 * Cambia el ángulo a la siguiente dirección en el sentido de las agujas del reloj
	 */
	public void girarHorario() {
		switch (this.getAngulo()) {
			case DOWN: {
				this.setAngulo(Direccion.LEFT);
				break;
			}
			case LEFT: {
				this.setAngulo(Direccion.UP);
				break;
			}
			case UP: {
				this.setAngulo(Direccion.RIGHT);
				break;
			}
			case RIGHT: {
				this.setAngulo(Direccion.DOWN);
				break;
			}
		}
	}
	
	/**
	 * Delega el cálculo de la siguiente posición en la dirección propuesta y efectúa
	 * el movimiento en caso de que sea posible
	 * @param direccion el ángulo inicial, es decir, antes de efectuar el movimiento
	 * @return si fue posible avanzar en dicha dirección en el caso de entidades 
	 * manejadas por el usuario. Siempre devolverá true si lo maneja la CPU ya que 
	 * lo gira automáticamente, entonces la Partida puede ignorar el resultado en dicho caso
	 */
	public boolean intentarMov(Direccion direccion) {
		int[] vector = this.getPosicion().getVector();
		helper.getPosInmediata(vector, direccion);
		
		if (this.isManejadoPorCPU()) {
			while (!helper.validarPosicion(vector)) {
				this.girarHorario();
				helper.getPosInmediata(vector, direccion);
			}
			this.efectuarMov(vector);
			return true;
		} else {
			if (helper.validarPosicion(vector)) {
				this.efectuarMov(vector);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Realiza la animación de pasar por un túnel y traslada la entidad a la posición de salida
	 * @param direccion En cuál de las 4 direcciones posibles está ingresando
	 */
	public void pasarPorTunel(Direccion direccion) {
		int[] vector = this.getPosicion().getVector();
		velocidadEnMs += 10;
		// efectuar animacion
		helper.getPosInversa(vector, direccion);
		this.efectuarMov(vector);
		velocidadEnMs -= 10;
	}
	
	/**
	 * Realiza la animación del personaje al moverse y actualiza su posición
	 * @param nuevaPos el vector (x,y) de la nueva celda válida a avanzar
	 */
	private void efectuarMov(int[] nuevaPos) {
		this.getPosicion().cambiar(nuevaPos[0], nuevaPos[1]);
		this.redibujar();
	}
	
	
	/**
	 * Actualiza el gráfico de la entidad
	 */
	private void redibujar() {
		// Implementación
	}
	
	/**
	 * 
	 * @return La instancia de utilidad para calcular y predecir posiciones
	 * (sólo está permitido su uso en clases heredadas, útil para los fantasmas)
	 */
	protected NewPositionsHelper getHelper() {
		return helper;
	}
}
