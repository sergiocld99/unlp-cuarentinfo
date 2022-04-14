package tallerlenguajes2.tp3.peliculas.io;

import tallerlenguajes2.tp3.peliculas.model.Datos;
import tallerlenguajes2.tp3.peliculas.model.Pelicula;
import tallerlenguajes2.tp3.peliculas.Aplicacion;
import tallerlenguajes2.tp3.peliculas.view.Colores;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que se encarga de la lectura y procesamiento de los archivos movies.csv y ratings.csv,
 * los cuales deben encontrarse ubicados en la raíz del proyecto, o bien, el mismo directorio que el JAR
 * @author Calderón Sergio y Ercoli Juan Martín
 * @version 1
 */
public class Lector {
    private static final String MOVIES_PATH = "movies.csv";
    private static final String RATINGS_PATH = "ratings.csv";
    private static final int PORCE_ONE = 10, PORCE_TWO = 90, UPDATE_RATE = 250;
    private final JProgressBar progressBar;

    /** @param progressBar una barra de progreso que es utilizada para mostrar el progreso de la carga */
    public Lector(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    /**
     * Lee el archivo movies.csv, del cual obtiene los nombres de las películas y sus IDs
     * @param datos una instancia de dicha clase en el cual se carga la información
     * @param archivo un objeto archivo del cual se extraen los datos
     * @return si la operación pudo concretarse
     */
    private boolean leerPeliculas(Datos datos, File archivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            // Saltear encabezado
            br.readLine();

            // Leer todas las lineas
            while ((linea = br.readLine()) != null) {
                // PRECONDICION: Todas las filas son una pelicula diferente
                // PRECONDICION: El nombre de la pelicula esta en la segunda fila (que existe)
                datos.agregarPelicula(filtrarTitulo(linea), filtrarID(linea));

                // actualizar progreso (suponemos 10000 elementos)
                int progreso = datos.getCantPeliculas() * PORCE_ONE / 10000;
                progressBar.setValue(progreso);
            }

            progressBar.setValue(PORCE_ONE);
            return true;
        } catch (FileNotFoundException e) {
            this.mostrarErrorArchivoNoEncontrado(archivo.getAbsolutePath());
            e.printStackTrace();
            // pedirle al usuario que busque el archivo correcto
            File archivoIntento = examinarArchivo();
            if (archivoIntento != null) {
                leerPeliculas(datos, archivoIntento);
                return true;
            }
        } catch (IOException e) {
            this.mostrarErrorLectura(archivo.getAbsolutePath());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Lee el archivo ratings.csv, del cual obtiene los votos de los usuarios. Estos son agregados
     * en un objeto Pelicula correspondiente, la lista de Datos no debe estar vacia.
     * @param datos la instancia de Datos utilizada previamente en leerPeliculas
     * @param archivo un objeto archivo del cual se extraeran los datos
     * @return si la operacion pudo concretarse
     */
    private boolean leerRatings(Datos datos, File archivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            // PRECONDICION: La columna de userId esta ordenada de menor a mayor
            String ultimoId = "";
            String linea;

            // Saltear encabezado
            br.readLine();

            int votosLeidos = 0;

            // Leer todas las lineas
            while ((linea = br.readLine()) != null){
                // PRECONDICION: Cada fila es un voto diferente
                // Valores separados por coma
                String[] params = linea.split(",");
                float rating = getRating(params);

                // En caso de encontrar alguna inconsistencia
                if (rating == -1) throw new Exception("Formato invalido");

                // PRECONDICION: La primer columna es el userId
                String actualId = params[0];
                if (!actualId.equals(ultimoId)){
                    datos.incrementarUsuarios();
                    datos.reiniciarIterador();
                    ultimoId = actualId;
                }

                // Agregar voto
                Pelicula pelicula = datos.getPelicula(params[1]);
                pelicula.incrementarUsuarios();
                pelicula.addVoto(rating);

                if (++votosLeidos % UPDATE_RATE == 0){
                    // Actualizar progreso (suponemos 101000 votos)
                    int progreso = (votosLeidos) * (PORCE_TWO - PORCE_ONE) / 101000;
                    progressBar.setValue(progreso + PORCE_ONE);
                }
            }

            progressBar.setValue(PORCE_TWO);
            return true;

        } catch (FileNotFoundException e) {
            this.mostrarErrorArchivoNoEncontrado(archivo.getAbsolutePath());
            e.printStackTrace();
            // pedirle al usuario que busque el archivo correcto
            File archivoIntento = examinarArchivo();
            if (archivoIntento != null){
                leerRatings(datos, archivoIntento);
                return true;
            }
        } catch (IOException e) {
            this.mostrarErrorLectura(archivo.getAbsolutePath());
            e.printStackTrace();
        } catch (Exception e){
            this.mostrarErrorGeneral(archivo.getAbsolutePath(), e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private String filtrarTitulo(String line) {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) return matcher.group(1);
        else return line.split(",")[1];
    }

    private String filtrarID(String line){
        return line.split(",")[0];
    }

    // PRECONDICION: La tercera columna existe y es el rating (float)
    private float getRating(String[] params) {
        try {
            return Float.parseFloat(params[2]);
        } catch (NumberFormatException e){
            System.err.println("Se encontrÃ³ el rating " + params[2] + ", que no es un float");
            return -1;
        }
    }

    /**
     * Permite obtener la informacion organizada a partir de la lectura de los archivos .csv
     * @return una instancia de Datos, la cual siempre se devuelve inicializada, incluso si ocurre un error
     */
    public Datos getDatos() {
        long startTime = System.currentTimeMillis();
        Datos datos = new Datos();

        // Paso 0: Se prepara la barra de progreso
        progressBar.setForeground(Colores.VERDE.color().brighter());
        progressBar.setValue(0);

        // Paso 1 (0 al 40%)
        if (!leerPeliculas(datos, new File(MOVIES_PATH))) return datos;

        // Paso 2 (40 al 90%)
        if (!leerRatings(datos, new File(RATINGS_PATH))) return datos;

        // Paso 3 (90% al 100%)
        Collections.sort(datos.getPeliculas());

        // Terminar
        System.out.println("Tiempo empleado: " + (System.currentTimeMillis()-startTime) + " ms");
        progressBar.setValue(100);
        return datos;
    }

    private void mostrarErrorArchivoNoEncontrado(String filepath){
        JOptionPane.showMessageDialog(null, "No se pudo encontrar el archivo " +
                    filepath, Aplicacion.NOMBRE, JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarErrorLectura(String filepath){
        JOptionPane.showMessageDialog(null, "Ocurrio un problema al leer el archivo " +
                filepath, Aplicacion.NOMBRE, JOptionPane.WARNING_MESSAGE);

        barraError();
    }

    private void mostrarErrorGeneral(String filepath, String info){
        JOptionPane.showMessageDialog(null, "Ocurrio un error en ejecucion (" +
                        info + ") mientras se " +
                " procesaba el archivo " + filepath, Aplicacion.NOMBRE, JOptionPane.WARNING_MESSAGE);

        barraError();
    }

    private File examinarArchivo(){
        File archivo = null;

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setSelectedFile(new File(""));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        if (chooser.showOpenDialog(null) == JFileChooser.OPEN_DIALOG) {
            archivo = chooser.getSelectedFile();
            // muestra error si es invÃ¡lido
            if ((archivo == null) || (archivo.getName().equals(""))) {
                JOptionPane.showMessageDialog(null,
                        "Nombre de archivo invalido",
                        Aplicacion.NOMBRE,
                        JOptionPane.ERROR_MESSAGE);

                barraError();
            }
        } else barraError();

        return archivo;
    }

    private void barraError(){
        progressBar.setForeground(Color.red);
        progressBar.setValue(100);
    }
}
