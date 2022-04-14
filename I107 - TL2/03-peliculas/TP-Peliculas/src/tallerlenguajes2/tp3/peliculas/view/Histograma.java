package tallerlenguajes2.tp3.peliculas.view;

import tallerlenguajes2.tp3.peliculas.Aplicacion;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Histograma extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final Font FUENTE = new Font("Default", Font.BOLD, 12);
    private final List<Barra> barras = new ArrayList<>();
    private final JPanel panelBarra;
    private final JPanel panelEtiqueta;
    private final JLabel lblTotalVotos;

    public Histograma() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Colores.OSCURO.color().brighter());
        setLayout(new BorderLayout());

        int gapEntreBarra = 5;
        panelBarra = new JPanel(new GridLayout(1, 0, gapEntreBarra, 0));
        Border externo = new MatteBorder(3, 3, 3, 3, Colores.OSCURO.color().brighter());
        Border interno = new EmptyBorder(10, 10, 0, 10);
        Border compuesto = new CompoundBorder(externo, interno);
        panelBarra.setBorder(compuesto);

        panelEtiqueta = new JPanel(new GridLayout(1, 0, gapEntreBarra, 0));
        panelEtiqueta.setBackground(Colores.OSCURO.color().brighter());
        panelEtiqueta.setBorder(new EmptyBorder(5, 10, 0, 10));

        lblTotalVotos = new JLabel();
        lblTotalVotos.setFont(Aplicacion.FUENTE);
        lblTotalVotos.setForeground(Colores.BLANCO.color());

        add(lblTotalVotos, BorderLayout.NORTH);
        add(panelBarra, BorderLayout.CENTER);
        add(panelEtiqueta, BorderLayout.PAGE_END);
    }

    public void agregarColumna(String etiqueta, int valor, Color color) {
        Barra barra = new Barra(etiqueta, valor, color);
        barras.add(barra);
    }

    public void formalizarHistograma() {
        panelBarra.removeAll();
        panelEtiqueta.removeAll();

        int suma = 0;

        int max = 0;
        for (Barra barra : barras)
            max = Math.max(max, barra.getValor());


        for (Barra barra : barras) {
            JLabel etiqueta = new JLabel(Integer.toString(barra.getValor()));
            etiqueta.setFont(Aplicacion.FUENTE);

            etiqueta.setHorizontalTextPosition(JLabel.CENTER);
            etiqueta.setHorizontalAlignment(JLabel.CENTER);

            etiqueta.setVerticalTextPosition(JLabel.TOP);
            etiqueta.setVerticalAlignment(JLabel.BOTTOM);

            int alturaBarra;
            int alturaHistograma = 200;
            int anchoBarra = 100;

            suma += barra.getValor();

            if(max <= 0)
                alturaBarra = 0;
            else
                alturaBarra = (barra.getValor() * alturaHistograma) / max ;

            Icon icon = new ColorIcon(barra.getColor(), anchoBarra, alturaBarra);
            etiqueta.setIcon(icon);
            etiqueta.updateUI();

            panelBarra.add(etiqueta);

            JLabel etiquetaBarra = new JLabel(barra.getEtiqueta());
            etiquetaBarra.setFont(FUENTE);
            etiquetaBarra.setForeground(Colores.DORADO.color().brighter());
            etiquetaBarra.setHorizontalAlignment(JLabel.CENTER);
            panelEtiqueta.add(etiquetaBarra);
        }

        lblTotalVotos.setText("Total de votos = " + suma);
    }

    public void cambiarValorBarra(int pos, int valor){
        barras.get(pos-1).setValor(valor);
    }

    private static class Barra {

        private final String etiqueta;
        private int valor;
        private final Color color;

        public Barra(String etiqueta, int valor, Color color) {
            this.etiqueta = etiqueta;
            this.valor = valor;
            this.color = color;
        }

        public String getEtiqueta() {
            return etiqueta;
        }

        public int getValor() {
            return valor;
        }

        public void setValor(int valor) {
            this.valor = valor;
        }

        public Color getColor() {
            return color;
        }

    }

    private static class ColorIcon implements Icon {

        private final Color color;
        private final int ancho;
        private final int alto;

        public ColorIcon(Color color, int ancho, int alto) {
            this.color = color;
            this.ancho = ancho;
            this.alto = alto;
        }

        public int getAncho() {
            return ancho;
        }

        public int getAlto() {
            return alto;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            dibujarParalelepipedo(g, x, y);
        }

        private void dibujarParalelepipedo(Graphics g, int x, int y){
            // Graphics a 2D
            Graphics2D g2 = (Graphics2D) g;

            // Medida de desborde
            int gap = 7;

            // Grosor de linea
            int thick = 2;

            // Dibuja el rectangulo oscuro
            g2.setColor(color.darker());
            g2.fillRect(x, y, ancho, alto);

            // Porción para darle perspectiva, une los vertices de los rectangulos principales
            Polygon unidorPunta = new Polygon();
            unidorPunta.addPoint(x, y);
            unidorPunta.addPoint(x - gap, y + gap);
            unidorPunta.addPoint(x, y + gap);
            g2.fillPolygon(unidorPunta);

            // Dibuja el rectangulo claro
            g2.setColor(color);
            g2.fillRect(x - gap, y + gap, ancho, alto);

            // Borde de la cara superior
            Polygon borde1 = new Polygon();
            borde1.addPoint(x, y);
            borde1.addPoint(x - gap, y + gap);
            borde1.addPoint(x - gap + ancho, y + gap);
            borde1.addPoint(x + ancho, y);

            // Sirve para ocultar la punta del extremo inferior derecho, para darle perspectiva
            Polygon ocultadorPunta = new Polygon();
            ocultadorPunta.addPoint(x-gap+ancho, y + alto);
            ocultadorPunta.addPoint(x+ancho, y+alto - gap);
            ocultadorPunta.addPoint(x+ancho, y+alto);

            // Borde para la izquierda del rectangulo claro
            Polygon borde2 = new Polygon();
            borde2.addPoint(x - gap , y + gap);
            borde2.addPoint(x - gap, y + alto + gap);

            // Borde para la cara derecha
            Polygon borde3 = new Polygon();
            borde3.addPoint(x + ancho - gap , y + gap );
            borde3.addPoint(x + ancho, y);
            borde3.addPoint(x + ancho, y + alto - gap);
            borde3.addPoint(x + ancho - gap, y + alto);

            // Relleno para la cara derecha
            Polygon rellenoCaraDerecha = new Polygon();
            rellenoCaraDerecha.addPoint(x + ancho - gap + thick, y + gap);
            rellenoCaraDerecha.addPoint(x + ancho - thick, y + thick);
            rellenoCaraDerecha.addPoint(x + ancho - thick, y + alto - gap - thick);
            rellenoCaraDerecha.addPoint(x + ancho - gap + thick, y + alto - thick);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(thick));

            // Dibujo el borde de la cara superior
            g2.drawPolygon(borde1);

            // Dibujo el borde para la izquierda del rectangulo claro
            g2.drawPolygon(borde2);

            if(alto > 0) {
                // Dibujo el borde para la cara derecha
                g2.drawPolygon(borde3);

                g2.setColor(color.darker().darker());

                // Dibujo el relleno para la cara derecha
                g2.fillPolygon(rellenoCaraDerecha);
            }

            if(alto > 5) {
                // Recomiendo que el ocultador de punta tenga el mismo color que el background
                g2.setColor(Colores.BLANCO.color());

                // Dibujo el ocultador de punta
                g2.fillPolygon(ocultadorPunta);
            }
        }

        @Override
        public int getIconWidth() {
            return getAncho();
        }

        @Override
        public int getIconHeight() {
            return getAlto();
        }
    }

}
