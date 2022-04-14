package tallerlenguajes2.tp3.peliculas.view;

import javax.swing.*;
import java.awt.*;

public class IconoFlechita implements Icon {

        private static final int ARRIBA = 0;
        private static final int ABAJO = 1;
        private final int SIZE = 12; // Recomendable que sea un número par

        private final Color COLOR_BORDE;
        private final Color COLOR_RELLENO;
        private final int DIRECCION;

        public IconoFlechita(Color color_borde, Color color_relleno, int direccion){
            COLOR_BORDE = color_borde;
            COLOR_RELLENO = color_relleno;
            DIRECCION = direccion;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            switch(DIRECCION) {
                case ARRIBA:
                    dibujarFlechaArriba(g, x, y);
                    break;
                case ABAJO:
                    dibujarFlechaAbajo(g, x, y);
                    break;
            }
        }

        private void dibujarFlechaArriba(Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g;

            Polygon flecha = new Polygon();
            flecha.addPoint(x + SIZE, y + SIZE);
            flecha.addPoint(x, y + SIZE);
            flecha.addPoint(x + SIZE/2, y);

            g2.setColor(COLOR_RELLENO);
            g2.fillPolygon(flecha);

            g2.setColor(COLOR_BORDE);
            g2.drawPolygon(flecha);

        }

        private void dibujarFlechaAbajo(Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g;

            Polygon flecha = new Polygon();
            flecha.addPoint(x, y);
            flecha.addPoint(x + SIZE, y);
            flecha.addPoint(x + SIZE/2, y + SIZE);

            g2.setColor(COLOR_RELLENO);
            g2.fillPolygon(flecha);

            g2.setColor(COLOR_BORDE);
            g2.drawPolygon(flecha);

        }

        @Override
        public int getIconWidth() {
            return SIZE;
        }

        @Override
        public int getIconHeight() {
            return SIZE;
        }

}

