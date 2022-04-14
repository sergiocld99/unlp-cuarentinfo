package tallerlenguajes2.tp3.peliculas.view;

import tallerlenguajes2.tp3.peliculas.model.Pelicula;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PeliculasTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final String[] columnNames = {"Nombre de película", "Usuarios", "Rating"};
    private List<Pelicula> peliculas;

    private static final int COLUMN_TITLE   = 0;
    private static final int COLUMN_USERS   = 1;
    private static final int COLUMN_SCORE   = 2;
    private int cantidadAMostrar = 5;

    public PeliculasTableModel(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return Math.min(peliculas.size(), cantidadAMostrar);
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (peliculas.isEmpty()) return Object.class;
        else return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pelicula pelicula = peliculas.get(rowIndex);

        switch (columnIndex){
            case COLUMN_TITLE:
                return pelicula.getTitulo();
            case COLUMN_USERS:
                return pelicula.getCantUsuarios();
            case COLUMN_SCORE:
                return String.format("%.2f", pelicula.getRatingPromedio());
            default:
                throw new IllegalArgumentException("Índice de columna " + columnIndex + " inválido");
        }
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public void setCantidadAMostrar(int cantidadAMostrar) {
        this.cantidadAMostrar = cantidadAMostrar;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }
}
