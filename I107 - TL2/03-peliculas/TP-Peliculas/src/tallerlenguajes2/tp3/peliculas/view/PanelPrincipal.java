package tallerlenguajes2.tp3.peliculas.view;

import tallerlenguajes2.tp3.peliculas.Aplicacion;
import tallerlenguajes2.tp3.peliculas.io.Lector;
import tallerlenguajes2.tp3.peliculas.model.Datos;
import tallerlenguajes2.tp3.peliculas.model.Pelicula;
import tallerlenguajes2.tp3.peliculas.res.TextosConstantes;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que representa la interfaz gráfica con los componentes solicitados para este trabajo
 * @author Ercoli Juan Martín y Calderón Sergio
 * @version 1
 */
public class PanelPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String PROCESS_BTN_NORMAL = "Procesar datos";
    private static final String PROCESS_BTN_RETRY = "Volver a procesar";

    private static final int CANT_BARRAS_HISTOGRAMA = 5;

    private Thread thread;
    private Datos datos;

    // Componentes
    private JPanel pContenedorPrincipal;
    private JButton btnProcesarDatos;
    private JProgressBar pgbProcesarDatos;
    private JLabel lblCantUsuarios;
    private JLabel lblCantPeliculas;
    private JLabel lblCantVotos;
    private JTable tabla;
    private PeliculasTableModel model;
    private JComboBox<String> selector;
    private JButton btnTotalHistograma;
    private Histograma histograma;
    private JLabel lblHistograma;

    /**
     * Constructor por defecto, inicializa y carga los componentes de la UI
     */
    public PanelPrincipal() {
        this.datos = new Datos();
        this.setFrame();
        this.setMainPanel();
        this.setProcessPanel();
        this.setContadores();
        this.setTablePanel();
        this.setHistograma();

        this.histograma.revalidate();
        thread = new Thread(new ProcesamientoDatos());
    }

    private void setFrame(){
        getContentPane().setBackground(Colores.ROSA_PASTEL.color());
        setTitle(Aplicacion.NOMBRE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Size
        Dimension sizePantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int anchoPantalla = sizePantalla.width;
        int alturaPantalla = sizePantalla.height;
        setMinimumSize(new Dimension(anchoPantalla*6/10 , (int) (alturaPantalla*9.5 /10)));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tabla.clearSelection();
            }
        });

        setAppIcon();
        setMenuBar();
    }

    private void setMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem jmiExit = new JMenuItem("Salir");
        jmiExit.addActionListener(e -> System.exit(0));
        fileMenu.add(jmiExit);
        
        JMenu helpMenu = new JMenu("Ayuda");
        JMenuItem jmiHow = new JMenuItem("Cómo funciona?");

        jmiHow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JOptionPane.showMessageDialog(PanelPrincipal.this,
                        TextosConstantes.MSG_HOW,
                        "¿Cómo funciona?",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenuItem jmiAbout = new JMenuItem("Acerca de");

        jmiAbout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JOptionPane.showMessageDialog(PanelPrincipal.this,
                        TextosConstantes.MSG_ABOUT,
                        "Acerca de",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        helpMenu.add(jmiHow);
        helpMenu.add(jmiAbout);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        super.setJMenuBar(menuBar);
    }

    private void setAppIcon(){
        try {
            InputStream inputStream = getClass().
                    getResourceAsStream("/tallerlenguajes2/tp3/peliculas/res/iconoPelicula.png");

            BufferedImage bufferedImage = ImageIO.read(inputStream);
            super.setIconImage(bufferedImage);
        } catch (IOException e){
            System.err.println("Ocurrió un error durante la lectura del ícono");
            e.printStackTrace();
        } catch (IllegalArgumentException | NullPointerException e){
            System.err.println("El ícono no se encuentra en el directorio especificado");
            e.printStackTrace();
        }
    }

    private void setMainPanel(){
        // Vista Principal
        GridBagLayout layoutPrincipal = new GridBagLayout();
        getContentPane().setLayout(layoutPrincipal);

        // Panel Principal
        GridBagLayout layoutPContenedorPrincipal = new GridBagLayout();
        pContenedorPrincipal = new JPanel();
        pContenedorPrincipal.setLayout(layoutPContenedorPrincipal);
        pContenedorPrincipal.setBackground(Colores.CELESTE_PASTEL.color());

        pContenedorPrincipal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tabla.clearSelection();
            }
        });

        // Scroll Pane
        JScrollPane spPrincipal = new JScrollPane();
        spPrincipal.setViewportView(pContenedorPrincipal);
        spPrincipal.setBorder(new LineBorder(Colores.OSCURO.color(),5,true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10,5,10,5);
        getContentPane().add(spPrincipal, gbc);
    }

    private void setProcessPanel(){
        GridBagLayout layoutPContenedorProcesarDatos = new GridBagLayout();
        JPanel pContenedorProcesarDatos = new JPanel();
        pContenedorProcesarDatos.setLayout(layoutPContenedorProcesarDatos);
        pContenedorProcesarDatos.setBackground(Colores.ROSA_PASTEL.color());

        this.setProcessButton(pContenedorProcesarDatos);
        this.setProgressBar(pContenedorProcesarDatos);

        // Borde para el panel Procesar
        pContenedorProcesarDatos.setBorder(new MatteBorder(5,5,5,5, Colores.OSCURO.color()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 30, 10 ,30);
        pContenedorPrincipal.add(pContenedorProcesarDatos, gbc);
    }

    private void setProcessButton(JPanel pContenedorProcesarDatos){
        btnProcesarDatos = new JButton();
        btnProcesarDatos.setUI(new EstiloBoton(Colores.OSCURO.color().brighter(), Colores.CELESTE_PASTEL.color()));
        btnProcesarDatos.setFont(Aplicacion.FUENTE);
        btnProcesarDatos.setForeground(Colores.BLANCO.color());
        btnProcesarDatos.setText(PROCESS_BTN_NORMAL);
        btnProcesarDatos.addActionListener(e -> this.procesarDatos());
        btnProcesarDatos.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if(btnProcesarDatos.isEnabled())
                    btnProcesarDatos.setUI(new EstiloBoton(Colores.OSCURO.color().brighter().brighter(),
                        Colores.CELESTE_PASTEL.color().brighter()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnProcesarDatos.setUI(new EstiloBoton(Colores.OSCURO.color().brighter(),
                        Colores.CELESTE_PASTEL.color()));
            }

        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 25, 10 ,30);

        pContenedorProcesarDatos.add(btnProcesarDatos, gbc);
    }

    private void setProgressBar(JPanel pContenedorProcesarDatos){
        pgbProcesarDatos = new JProgressBar();
        pgbProcesarDatos.setUI(new BasicProgressBarUI(){

            @Override
            protected Color getSelectionBackground() {
                return Colores.OSCURO.color();
            }

            @Override
            protected Color getSelectionForeground() {
                return Colores.OSCURO.color();
            }

        });

        pgbProcesarDatos.setStringPainted(true);
        pgbProcesarDatos.setBorder(new MatteBorder(5,5,5,5, Colores.OSCURO.color()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 15, 10 ,25);

        pContenedorProcesarDatos.add(pgbProcesarDatos, gbc);
    }

    private void setContadores(){
        GridBagLayout layoutPContenedorContadores = new GridBagLayout();
        // Contadores
        JPanel pContenedorContadores = new JPanel();
        pContenedorContadores.setBorder(BorderFactory.
                createMatteBorder(5, 5, 5, 5, Colores.OSCURO.color()));
        pContenedorContadores.setLayout(layoutPContenedorContadores);
        pContenedorContadores.setBackground(Colores.ROSA_PASTEL.color());

        this.setContadorUsuarios(pContenedorContadores);
        this.setContadorPeliculas(pContenedorContadores);
        this.setContadorVotos(pContenedorContadores);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 30, 5, 30);
        pContenedorPrincipal.add(pContenedorContadores, gbc);
    }

    private void setContadorUsuarios(JPanel pContenedorContadores){
        GridBagLayout layoutPContadorUsuarios = new GridBagLayout();
        JPanel pContadorUsuarios = new JPanel();
        pContadorUsuarios.setBorder(BorderFactory.
                createMatteBorder(2, 1, 2, 5, Colores.OSCURO.color().brighter()));
        pContadorUsuarios.setLayout(layoutPContadorUsuarios);

        // Label: Palabra "Usuarios"
        JLabel lblUsuarios = new JLabel();
        lblUsuarios.setFont(Aplicacion.FUENTE);
        lblUsuarios.setForeground(Colores.BLANCO.color());
        lblUsuarios.setText("Usuarios");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        pContadorUsuarios.add(lblUsuarios, gbc);

        // Label: Número a mostrar
        lblCantUsuarios = new JLabel();
        lblCantUsuarios.setFont(Aplicacion.FUENTE);
        lblCantUsuarios.setForeground(Colores.BLANCO.color());
        lblCantUsuarios.setText("????");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        pContadorUsuarios.add(lblCantUsuarios, gbc);

        // Background para el contador de usuarios
        pContadorUsuarios.setBackground(Colores.CELESTE_PASTEL.color());

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 30, 10, 30);
        pContenedorContadores.add(pContadorUsuarios, gbc);
    }

    private void setContadorPeliculas(JPanel pContenedorContadores){
        GridBagLayout layoutPContadorPeliculas = new GridBagLayout();
        JPanel pContadorPeliculas = new JPanel();
        pContadorPeliculas.setBorder(BorderFactory.
                createMatteBorder(2, 1, 2, 5, Colores.OSCURO.color().brighter()));
        pContadorPeliculas.setLayout(layoutPContadorPeliculas);

        // Label: Palabra "Películas"
        JLabel lblPeliculas = new JLabel();
        lblPeliculas.setFont(Aplicacion.FUENTE);
        lblPeliculas.setForeground(Colores.BLANCO.color());
        lblPeliculas.setText("Películas");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        pContadorPeliculas.add(lblPeliculas, gbc);

        // Label: Número a mostrar
        lblCantPeliculas = new JLabel();
        lblCantPeliculas.setFont(Aplicacion.FUENTE);
        lblCantPeliculas.setForeground(Colores.BLANCO.color());
        lblCantPeliculas.setText("????");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        pContadorPeliculas.add(lblCantPeliculas, gbc);

        // Background para el contador de peliculas
        pContadorPeliculas.setBackground(Colores.CELESTE_PASTEL.color());

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 30, 10, 30);
        pContenedorContadores.add(pContadorPeliculas, gbc);
    }

    private void setContadorVotos(JPanel pContenedorContadores){
        JPanel pContadorVotos = new JPanel();
        pContadorVotos.setBorder(javax.swing.BorderFactory.
                createMatteBorder(2, 1, 2, 5, Colores.OSCURO.color().brighter()));
        GridBagLayout layoutPContadorVotos = new GridBagLayout();
        pContadorVotos.setLayout(layoutPContadorVotos);

        // Label: Palabras "Cantidad de Votos"
        JLabel lblVotos = new JLabel();
        lblVotos.setFont(Aplicacion.FUENTE);
        lblVotos.setForeground(Colores.BLANCO.color());
        lblVotos.setText("Cantidad de votos");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        pContadorVotos.add(lblVotos, gbc);

        // Label: Número a mostrar
        lblCantVotos = new JLabel();
        lblCantVotos.setFont(Aplicacion.FUENTE);
        lblCantVotos.setForeground(Colores.BLANCO.color());
        lblCantVotos.setText("????");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        pContadorVotos.add(lblCantVotos, gbc);

        // Background para el contador de votos
        pContadorVotos.setBackground(Colores.CELESTE_PASTEL.color());

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 30, 10, 30);
        pContenedorContadores.add(pContadorVotos, gbc);
    }

    private void setTablePanel(){
        GridBagLayout layoutPContenedorTabla = new GridBagLayout();
        JPanel pContenedorTabla = new JPanel();
        pContenedorTabla.setLayout(layoutPContenedorTabla);
        pContenedorTabla.setBackground(Colores.ROSA_PASTEL.color());

        this.setSelector(pContenedorTabla);
        this.setTable(pContenedorTabla);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 30, 10, 30);
        pContenedorPrincipal.add(pContenedorTabla, gbc);
    }

    private void setSelector(JPanel pContenedorTabla){
        GridBagLayout layoutPSelector = new GridBagLayout();
        JPanel pSelector = new JPanel();
        pSelector.setLayout(layoutPSelector);
        pSelector.setBackground(Colores.ROSA_PASTEL.color());

        // Label: Palabras "Cantidad de resultados a mostrar"
        JLabel lblSelector = new JLabel();
        lblSelector.setFont(Aplicacion.FUENTE);
        lblSelector.setForeground(Colores.BLANCO.color());
        lblSelector.setText("Cantidad de resultados a mostrar:  ");
        lblSelector.setBackground(Colores.ROSA_PASTEL.color());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 30);

        pSelector.add(lblSelector, gbc);

        // ComboBox: Opciones limitadas (filtros)
        selector = new JComboBox<>();
        selector.setFont(Aplicacion.FUENTE);
        selector.setModel(new DefaultComboBoxModel<>(new String[] {
                "5", "10", "20", "100", "1000", "TODOS"}));
        selector.addActionListener(e -> actualizarTabla());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        pSelector.add(selector, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER; //NW
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 5, 10, 30);
        pContenedorTabla.add(pSelector, gbc);
    }

    private void setTable(JPanel pContenedorTabla) {
        model = new PeliculasTableModel(datos.getPeliculas());

        tabla = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component returnComp = super.prepareRenderer(renderer, row, column);
                if (!returnComp.getBackground().equals(getSelectionBackground())) {
                    Color bg = (row % 2 == 0 ? Colores.GRIS_SUAVE.color() : Colores.BLANCO.color());
                    returnComp.setBackground(bg);
                }
                return returnComp;
            }
        };

        tabla.setModel(model);
        tabla.setFont(Aplicacion.FUENTE);
        tabla.getTableHeader().setReorderingAllowed(true);
        tabla.setFocusable(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setRowHeight(25);
        tabla.setSelectionBackground(Colores.CELESTE_PASTEL.color());
        tabla.setShowVerticalLines(false);

        TableRowSorter<PeliculasTableModel> sorter = new TableRowSorter<>(model);
        tabla.setRowSorter(sorter);

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mostrarHistogramaIndividual(tabla.getSelectedRows());
            }
        });

        // Scroll Pane (para permitir deslizamiento)
        JScrollPane spTabla = new JScrollPane();
        spTabla.setPreferredSize(new Dimension(450,160));
        spTabla.setViewportView(tabla);
        spTabla.setBackground(Colores.OSCURO.color());

        // Borde para el ScrollPane de la tabla
        spTabla.setBorder(new MatteBorder(5,0,0,0, Colores.OSCURO.color()));

        // Borde para el panel contenedor tabla
        pContenedorTabla.setBorder(new MatteBorder(5,5,5,5, Colores.OSCURO.color()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        pContenedorTabla.add(spTabla, gbc);

        this.alinearTextosTabla();
    }

    private void setHistograma(){
        GridBagLayout layoutPContenedorHistograma = new GridBagLayout();
        //Histograma
        JPanel pContenedorHistograma = new JPanel();
        pContenedorHistograma.setLayout(layoutPContenedorHistograma);
        pContenedorHistograma.setBackground(Colores.ROSA_PASTEL.color());

        // Label: Palabra "Histograma"
        lblHistograma = new JLabel();
        lblHistograma.setFont(Aplicacion.FUENTE);
        lblHistograma.setText("Histograma");
        lblHistograma.setForeground(Colores.BLANCO.color());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        pContenedorHistograma.add(lblHistograma, gbc);

        // Boton: Mostrar Histograma completo
        setTotalHistogramButton(pContenedorHistograma);

        // Componente principal del Histograma
        final String estrellita = "\u2606";
        histograma = new Histograma();

        Color[] basicColors = new Color[]{
                Colores.CELESTE_PASTEL.color(), Colores.ROSA_PASTEL.color(), Colores.VERDE.color(),
                Colores.DORADO.color(), Colores.GRIS_SUAVE.color()
        };

        for (int i=0; i<CANT_BARRAS_HISTOGRAMA; i++){
            StringBuilder sb = new StringBuilder(estrellita);
            for (int j=0; j<i; j++) sb.append(estrellita);
            histograma.agregarColumna(sb.toString(), 0, basicColors[i]);
        }

        histograma.formalizarHistograma();

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        pContenedorHistograma.add(histograma, gbc);

        // Borde para el contenedor del histograma
        pContenedorHistograma.setBorder(
                new MatteBorder(5,5,5,5, Colores.OSCURO.color()));

        // Ubicar el contenedor del histograma
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 30, 5, 30);
        pContenedorPrincipal.add(pContenedorHistograma, gbc);
    }

    private void setTotalHistogramButton(JPanel pContenedorHistograma) {
        btnTotalHistograma = new JButton("Mostrar votos totales");
        btnTotalHistograma.setFont(Aplicacion.FUENTE);
        btnTotalHistograma.setForeground(Colores.BLANCO.color());
        btnTotalHistograma.setUI(new EstiloBoton(
                Colores.OSCURO.color().brighter(), Colores.CELESTE_PASTEL.color()));
        btnTotalHistograma.addActionListener(e -> tabla.clearSelection());
        btnTotalHistograma.addActionListener(e -> mostrarHistogramaGeneral());
        btnTotalHistograma.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if(btnTotalHistograma.isEnabled())
                    btnTotalHistograma.setUI(new EstiloBoton(Colores.OSCURO.color().brighter().brighter(),
                            Colores.CELESTE_PASTEL.color().brighter()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnTotalHistograma.setUI(new EstiloBoton(Colores.OSCURO.color().brighter(),
                        Colores.CELESTE_PASTEL.color()));
            }

        });

        btnTotalHistograma.setEnabled(false);

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 50, 10, 50);
        pContenedorHistograma.add(btnTotalHistograma, gbc);
    }

    private void procesarDatos(){
        if (thread.getState() == Thread.State.NEW){
            thread.start();
        } else if (thread.getState() == Thread.State.TERMINATED){
            thread = new Thread(new ProcesamientoDatos());
            thread.start();
        }
    }

    private void actualizarTabla(){
        if (selector.getSelectedIndex() == selector.getItemCount()-1){
            // Está seleccionada la ultima opción, que es TODOS
            model.setCantidadAMostrar(datos.getPeliculas().size());
        } else {
            // Está seleccionada una opción numérica (filtro)
            String selectedOption = (String) selector.getSelectedItem();
            if (selectedOption != null) try {
                int cantInteres = Integer.parseInt(selectedOption);
                model.setCantidadAMostrar(cantInteres);
            } catch (NumberFormatException e){
                System.err.println("El filtro seleccionado no es un número \n\n" + e.getMessage());
            }
        }

        model.fireTableDataChanged();
    }

    private void mostrarHistogramaGeneral() {
        int[] valores = datos.getVotosTotales();

        for (int i=1; i<=5; i++){
            histograma.cambiarValorBarra(i, valores[i]);
        }

        lblHistograma.setText("Histograma General");
        histograma.formalizarHistograma();
        histograma.revalidate();
    }

    private void mostrarHistogramaIndividual(int[] filasSeleccionadas){
        int[] votos = new int[CANT_BARRAS_HISTOGRAMA+1];
        Pelicula peliculaSeleccionada = null;

        for (int numeroFila: filasSeleccionadas) {
            peliculaSeleccionada = model.getPeliculas().get(tabla.convertRowIndexToModel(numeroFila));

            for(int i = 1; i <=CANT_BARRAS_HISTOGRAMA; i++) {
                votos[i] += peliculaSeleccionada.getCantVotos(i);
            }

        }
        for (int i = 1; i <=CANT_BARRAS_HISTOGRAMA; i++) {
            histograma.cambiarValorBarra(i, votos[i]);
        }

        if (filasSeleccionadas.length > 1)
            lblHistograma.setText("Histograma Combinado para las " +
                    filasSeleccionadas.length + " películas seleccionadas");
        else {
            if (peliculaSeleccionada != null)
                lblHistograma.setText("Histograma para " + peliculaSeleccionada.getTitulo());
        }

        histograma.formalizarHistograma();
        histograma.revalidate();
    }

    private void gestionarHeaderTabla(){
        JTableHeader tableHeader = tabla.getTableHeader();

        tableHeader.setDefaultRenderer(new HeaderTabla(tabla));
        tableHeader.setFont(Aplicacion.FUENTE);
        tableHeader.setPreferredSize(new Dimension(30,30));
    }

    private void alinearTextosTabla(){
        TableColumnModel columnModel = tabla.getColumnModel();
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        gestionarHeaderTabla();

        tabla.getColumnModel().getColumn(0).setPreferredWidth(300);

        for (int i=0; i<columnModel.getColumnCount(); i++){
            TableColumn columna = columnModel.getColumn(i);
            columna.setCellRenderer(cellRenderer);
            columna.setMinWidth(150);
        }
    }

    private class ProcesamientoDatos implements Runnable {

        @Override
        public void run() {
            Lector lector = new Lector(pgbProcesarDatos);
            btnProcesarDatos.setEnabled(false);
            datos = lector.getDatos();

            // actualizar UI con los datos totales
            lblCantUsuarios.setText(String.valueOf(datos.getCantUsuarios()));
            lblCantPeliculas.setText(String.valueOf(datos.getCantPeliculas()));
            lblCantVotos.setText(String.valueOf(datos.getCantVotos()));

            // Habilitar botones
            btnTotalHistograma.setEnabled(true);
            btnProcesarDatos.setEnabled(true);
            btnProcesarDatos.setText(PROCESS_BTN_RETRY);

            model.setPeliculas(datos.getPeliculas());
            model.fireTableDataChanged();
            mostrarHistogramaGeneral();
        }
    }
}