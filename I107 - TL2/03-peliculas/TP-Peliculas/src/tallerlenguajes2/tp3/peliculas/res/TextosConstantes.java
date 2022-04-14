package tallerlenguajes2.tp3.peliculas.res;

/**
 * Clase que almacena String extensos utilizados en la interfaz gráfica
 * @author Ercoli Juan Martín y Calderón Sergio
 * @version 1
 */
public class TextosConstantes {

    /** Explicación de la app, se muestra en la opción "¿Cómo funciona?" */
    public static final String MSG_HOW =
                             "<html>"
            +                "El proyecto consiste en <b>4 paneles</b> que tienen asignada una función determinada:"
            +                "<br><br>   &emsp;\u2022<b> <u>Panel de procesar datos:</u></b>"
            +                "<br>         &emsp;&emsp;&emsp;\u25E6 Contiene un boton que al presionarlo, carga los datos de las películas <br>"
            +                "             &emsp;&emsp;&emsp;       almacenados en dos archivos .csv (contenidos en la raíz) al programa."
            +                "<br>         &emsp;&emsp;&emsp;\u25E6 Contiene una barra de progreso para visualizar el progreso de la carga "
            +                "<br><br>     &emsp;\u2022<b> <u>Panel de contadores:</u></b>"
            +                "<br>         &emsp;&emsp;&emsp;\u25E6 Contiene un contador para la cantidad de usuarios procesados."
            +                "<br>         &emsp;&emsp;&emsp;\u25E6 Contiene un contador para la cantidad de películas procesadas."
            +                "<br>         &emsp;&emsp;&emsp;\u25E6 Contiene un contador para la cantidad de votos procesados."
            +                "<br><br>     &emsp;\u2022<b> <u>Panel de tabla y selección:</u></b>"
            +                "<br>         &emsp;&emsp;&emsp;\u25E6 Contiene un selector que permite elegir la cantidad de películas a mostrar"
            +                "<br>         &emsp;&emsp;&emsp;\u25E6 Contiene una tabla que muestra las películas (y su información) ordenadas en <br>"
            +                "             &emsp;&emsp;&emsp;       orden descendente por la cantidad de votos que tienen."
            +                "<br><br>     &emsp;\u2022<b> <u>Panel de histograma:</u></b>"
            +                "<br>         &emsp;&emsp;&emsp;\u25E6 Contiene un histograma que está relacionado a la tabla"
            +                "<br>          &emsp;&emsp;&emsp;\u25E6 Contiene un botón que al presionarlo muestra la cantidad total de votos  <br>"
            +                "             &emsp;&emsp;&emsp;       ordenados por la cantidad de estrellitas (rating) que poseen."
            +                "<br>          &emsp;&emsp;&emsp;\u25E6 Contiene un contador cantidad de votos de las películas seleccionadas <br>"
            +                "<br>         &emsp;&emsp;&emsp;\u25E6 Al principio el histograma mostrará la cantidad total de votos ordenados por la <br>"
            +                "             &emsp;&emsp;&emsp;       cantidad de estrellitas (rating) que poseen."
            +                "<br>          &emsp;&emsp;&emsp;\u25E6 Al seleccionar una película en la tabla, el histograma mostrará los votos de la <br>"
            +                "             &emsp;&emsp;&emsp;       película ordenados por la cantidad de estrellitas (rating) que poseen."
            +                "<br>         &emsp;&emsp;&emsp;\u25E6 Al seleccionar varias películas de la tabla, el histograma mostrara la suma de <br>"
            +                "             &emsp;&emsp;&emsp;       votos de las películas ordenados por el rating que poseen "
            +                "</html>";

    /** Mensaje a mostrar cuando el usuario cliquea en "Acerca de" */
    public static final String MSG_ABOUT =
                             "<html>"
            +                "Proyecto realizado por: "
            +                "<br>     &emsp;\u2022" + " <b>Sergio Leandro Calderón.</b>"
            +                "<br>     &emsp;\u2022" + " <b>Juan Martín Ercoli.</b>"
            +                "<br> <u>Para la materia Taller de Lenguajes II.</u> <br>"
            +                "<br>La idea de este proyecto consiste en tomar<br>"
            +                "un conjunto de datos referentes a películas<br>"
            +                "provisto por GroupLens, con alrededor de:<br>"
            +                "     &emsp;\u2022 100,000 votos.<br>"
            +                "     &emsp;\u2022 9,000 películas.<br>"
            +                "     &emsp;\u2022 600 usuarios.<br>"
            +                "<br>El objetivo es generar una interfaz gráfica que<br>"
            +                "permita ver de forma amigable toda la información."
            +                "</html>";
}
