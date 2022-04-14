package tallerlenguajes2.tp3.peliculas;

import tallerlenguajes2.tp3.peliculas.view.Colores;
import tallerlenguajes2.tp3.peliculas.view.IconoFlechita;
import tallerlenguajes2.tp3.peliculas.view.PanelPrincipal;
import javax.swing.*;
import java.awt.*;

/**
 * Clase que permite ejecutar el programa
 * @author Calderón Sergio y Ercoli Juan Martín
 * @version 1
 */
public class Aplicacion {

    /** Nombre de la aplicación, para mostrarlo como título */
    public static final String NOMBRE = "Tablero de control de películas";

    /** Fuente para los textos de la aplicación */
    public static final Font FUENTE = new Font("Century Gothic", Font.BOLD, 14);


    /**
     * Setea el estilo de la aplicación y muestra el panel principal
     * @param args Argumentos tipo String. En este caso son ignorados.
     */
    public static void main(String[] args) {
        // Para agregar un look nimbus
        setearEstiloNimbus();

        PanelPrincipal panelPrincipal = new PanelPrincipal();
        panelPrincipal.setVisible(true);
    }


    /**
     * Establece colores, fuentes e íconos para ciertos componentes
     */
    private static void setearEstiloNimbus(){
        UIManager.put("control", Colores.BLANCO.color().brighter());

        // Setear íconos de las flechas para el sort de la tabla
        UIManager.put("Table.ascendingSortIcon",
                new IconoFlechita(Colores.OSCURO.color(), Colores.ROSA_PASTEL.color(), 0));
        UIManager.put("Table.descendingSortIcon",
                new IconoFlechita(Colores.OSCURO.color(), Colores.ROSA_PASTEL.color(), 1));

        // Para agregar una fuente a los diálogos
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", FUENTE);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals("Nimbus")) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
