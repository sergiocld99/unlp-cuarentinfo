package tallerlenguajes2.tp3.peliculas.model;

import java.util.*;

/**
 * Clase que almacena toda la información necesaria para mostrarla en el tablero de control de películas,
 * tales como una lista con cada película (engloba la información individual) y la cantidad de usuarios
 * @author Calderón Sergio y Ercoli Juan Martín
 * @version 1
 */
public class Datos {
    private final List<Pelicula> peliculas;
    private Iterator<Pelicula> iterator;

    private int cantUsuarios;

    /**
     * Constructor por defecto, inicializa una lista de películas para su posterior carga
     */
    public Datos() {
        this.peliculas = new ArrayList<>();
        this.cantUsuarios = 0;
    }

    /**
     * Permite obtener la cantidad de usuarios que votaron según el archivo ratings.csv (sin repetir)
     * @return el valor del atributo cantUsuarios
     */
    public int getCantUsuarios() {
        return cantUsuarios;
    }

    /**
     * Permite obtener la lista con todas las instancias de Película cargadas por el Lector
     * @return la referencia al atributo peliculas
     */
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    /**
     * Permite obtener la cantidad de películas encontradas en el archivo movies.csv
     * @return el tamaÃ±o de la lista de películas
     */
    public int getCantPeliculas() {
        return getPeliculas().size();
    }

    /**
     * Realiza un cálculo a partir de todas las instancias de películas en la lista ya cargada,
     * sumando sus cantidades de votos individuales. Este resultado difiere de la cantidad
     * de usuarios ya que los mismos pueden votar por más de una película
     * @return la suma de votos total
     */
    public int getCantVotos() {
        int sum = 0;

        for (Pelicula p : getPeliculas()){
            sum += p.getCantVotos();
        }

        return sum;
    }

    /**
     * Incrementa el valor del atributo cantUsuarios en 1
     */
    public void incrementarUsuarios(){
        this.cantUsuarios++;
    }

    /**
     * Crea y agrega una instancia de Película a la lista
     * @param titulo el título de la película
     * @param movieId el ID de la película
     */
    public void agregarPelicula(String titulo, String movieId){
        this.getPeliculas().add(new Pelicula(titulo, movieId));
    }

    /**
     * Permite regresar el puntero de lista al primer elemento. Esto debe realizarse
     * cuando en el archivo ratings.csv se lee un voto de un nuevo usuario, ya que
     * se tiene como precondición que los votos de un mismo usuario están ordenados por movieId
     */
    public void reiniciarIterador(){
        iterator = getPeliculas().iterator();
    }

    /**
     * Permite obtener la película previamente cargada a partir de su ID. La búsqueda se realiza
     * a partir de la última posición, por lo que es importante reiniciar el iterador si se
     * desea buscar a la misma en toda la lista en lugar de sólo una parte
     * @param movieId el ID de la película
     * @return la instancia de Película correspondiente, null si no pudo ser encontrada
     */
    public Pelicula getPelicula(String movieId){
        while (iterator.hasNext()){
            Pelicula p = iterator.next();
            if (p.getMovieId().equals(movieId)) {
                return p;
            }
        }

        System.err.println("Se solicitó obtener la película " + movieId + ", la cual no está cargada");
        return null;
    }

    /**
     * Recorre la lista de películas ya cargada, contando los votos y agrupándolos según su rating.
     * Este resumen es útil para mostrarlo de forma gráfica, por ejemplo, en el histograma general.
     * @return un arreglo de longitud 6, el cual las posiciones 1-5 almacenan las cantidades de
     * votos encontrados con dicho rating
     */
    public int[] getVotosTotales(){
        int[] result = new int[6];

        for (Pelicula p : getPeliculas()){
            for (int i=1; i<=5; i++){
                result[i] += p.getCantVotos(i);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "Se tienen " + getCantUsuarios() + " usuarios, " +
                getCantPeliculas() + " películas, " +
                getCantVotos() + " votos";
    }
}
