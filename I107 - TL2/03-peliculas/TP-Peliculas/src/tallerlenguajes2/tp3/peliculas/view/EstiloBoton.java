package tallerlenguajes2.tp3.peliculas.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class EstiloBoton extends BasicButtonUI {

    private final Color COLOR_PRINCIPAL;
    private final Color COLOR_SECUNDARIO;

    public EstiloBoton(Color color_principal, Color color_secundario){
        super();
        COLOR_PRINCIPAL = color_principal;
        COLOR_SECUNDARIO = color_secundario;
    }

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 10, 15));
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 3 : 0);
        super.paint(g, c);
    }

    private void paintBackground (Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.COLOR_SECUNDARIO);
        g2.fill3DRect(0, yOffset, size.width, size.height - yOffset, true);
        g2.setColor(this.COLOR_PRINCIPAL);
        g2.fill3DRect(0, yOffset, size.width, size.height + yOffset - 5, true);
    }
}