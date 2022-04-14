package tallerlenguajes2.tp3.peliculas.view;

import java.awt.*;

/**
 * Enumerativo con los colores personalizados para la aplicacion
 */
public enum Colores {
    CELESTE_PASTEL(32, 136, 203),
    ROSA_PASTEL(232, 57, 95),
    BLANCO(255, 255, 255),
    OSCURO(41, 47, 54),
    VERDE(0, 130, 0),
    DORADO(255, 215, 0),
    GRIS_SUAVE(211, 211, 211);

    private final Color color;

    Colores(int red, int green, int blue){
        this.color = new Color(red, green, blue);
    }

    /**
     * Permite obtener el color asignado
     * @return la instancia de Color correspondiente
     */
    public Color color() {
        return color;
    }
}
