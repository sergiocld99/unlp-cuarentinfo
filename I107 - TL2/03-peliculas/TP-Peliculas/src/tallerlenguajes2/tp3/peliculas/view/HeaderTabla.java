package tallerlenguajes2.tp3.peliculas.view;

import tallerlenguajes2.tp3.peliculas.Aplicacion;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class HeaderTabla extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private final DefaultTableCellRenderer defaultRenderer;

    public HeaderTabla(JTable tabla) {
        defaultRenderer = (DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JPanel panel = new JPanel(new BorderLayout());

        defaultRenderer.setHorizontalAlignment(JLabel.CENTER);

        c.setForeground(Colores.CELESTE_PASTEL.color().darker());
        c.setFont(Aplicacion.FUENTE);
        c.setPreferredSize(new Dimension(30,30));

        panel.setBorder(new MatteBorder(2,2,2,2, Colores.OSCURO.color().brighter()));
        panel.add(c);
        return panel;
    }
}