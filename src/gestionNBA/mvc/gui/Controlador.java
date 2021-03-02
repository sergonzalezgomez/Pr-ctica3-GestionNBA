package gestionNBA.mvc.gui;

import gestionNBA.base.Conferencia;
import gestionNBA.base.Equipo;
import gestionNBA.base.Jugador;
import gestionNBA.dialogos.*;
import gestionNBA.mvc.modelo.Modelo;
import gestionNBA.util.Util;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

public class Controlador implements ActionListener, FocusListener, ListSelectionListener, MouseListener {
    private Vista vista;
    private Modelo modelo;
    private ResourceBundle resourceBundle;

    public Controlador(Vista vista, Modelo modelo){
        this.vista = vista;
        this.modelo = modelo;

        anadirActionListeners(this);
        anadirFocusListeners(this);
        anadirListSelectionListeners(this);

        resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle");
    }

    /**
     * Método que asocia componentes con el listener ListSelectionListener.
     *
     * @param listener listener recibido por el método.
     */
    private void anadirListSelectionListeners(ListSelectionListener listener) {
        vista.listaJugadores.addListSelectionListener(listener);
        vista.listaEquipos.addListSelectionListener(listener);
        vista.listaConferencias.addListSelectionListener(listener);

        vista.listaJugadores.addMouseListener((MouseListener) listener);
        vista.listaEquipos.addMouseListener((MouseListener) listener);
        vista.listaConferencias.addMouseListener((MouseListener) listener);
    }

    /**
     * Método que asocia componentes con el listener FocusListener.
     *
     * @param listener listener recibido por el método.
     */
    private void anadirFocusListeners(FocusListener listener) {
        vista.txtNombreJugador.addFocusListener(listener);
        vista.txtApellidosJugador.addFocusListener(listener);
        vista.datePickerFechaJugador.addFocusListener(listener);
        vista.spnPuntosJugador.addFocusListener(listener);

        vista.txtNombreEquipo.addFocusListener(listener);
        vista.txtAnnoFundaconEquipo.addFocusListener(listener);
        vista.txtNombreEstadio.addFocusListener(listener);
        vista.txtPresupuestoEquipo.addFocusListener(listener);

        vista.txtNombreConferencia.addFocusListener(listener);
        vista.txtAnnoFundacionConferencia.addFocusListener(listener);
        vista.spnTitulosConferencia.addFocusListener(listener);
        vista.txtJefeConferencia.addFocusListener(listener);
    }

    /**
     * Método que asocia componentes con el listener ActionListener.
     *
     * @param listener listener recibido por el método.
     */
    private void anadirActionListeners(ActionListener listener) {
        vista.botGuardarDatos.addActionListener(listener);
        vista.botCargarDatos.addActionListener(listener);
        vista.botDocumentacion.addActionListener(listener);
        vista.botConfiguracion.addActionListener(listener);

        vista.botSeleccionarFotoJugador.addActionListener(listener);
        vista.botCrearJugador.addActionListener(listener);
        vista.botGraficoJugadores.addActionListener(listener);
        vista.botInformeJugadores.addActionListener(listener);
        vista.botEliminarJugador.addActionListener(listener);
        vista.botModificarJugador.addActionListener(listener);

        vista.botSeleccionarFotoEquipo.addActionListener(listener);
        vista.botCrearEquipo.addActionListener(listener);
        vista.botGraficoEquipos.addActionListener(listener);
        vista.botInformeEquipos.addActionListener(listener);
        vista.botAnnadirJugadores.addActionListener(listener);
        vista.botEliminarEquipo.addActionListener(listener);
        vista.botModificarEquipo.addActionListener(listener);

        vista.radioBotConferenciaEste.addActionListener(listener);
        vista.radioBotConferenciaOeste.addActionListener(listener);
        vista.botCrearConferencia.addActionListener(listener);
        vista.botAnnadirEquipo.addActionListener(listener);
        vista.botEliminarConferencia.addActionListener(listener);
        vista.botModificarConferencia.addActionListener(listener);
    }

    /**
     * Método que responde a los ActionEvent producidos sobre los componentes asociados.
     * Dependiendo de la propiedad actionCommand del evento se reconoce que componente se ha pulsado.
     *
     * @param actionEvent Evento recibido por el método.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String comando = actionEvent.getActionCommand();
        try {
            switch (comando) {
                case "GuardarDatos": {
                    guardarDatos();
                    break;
                }
                case "CargarDatos": {
                    cargarDatos();
                    break;
                }
                case "AbrirDocumentacion": {
                    abrirDocumentacion();
                    break;
                }
                case "Preferencias": {
                    DialogoPreferencias dialogoPreferencias = new DialogoPreferencias();
                    break;
                }
                case "NuevoJugador": {
                    nuevoJugador();
                    break;
                }
                case "SeleccionarFotoJugador": {
                    seleccionarFotoJugador();
                    break;
                }
                case "MostrarGraficoPuntosJugadores": {
                    mostrarGraficoJugadores();
                    break;
                }
                case "MostrarInformeJugadores": {
                    mostrarInformeJugadores();
                    break;
                }
                case "ModificarJugador": {
                    modificarJugador();
                    break;
                }
                case "EliminarJugador": {
                    eliminarJugador();
                    break;
                }
                case "NuevoEquipo": {
                    nuevoEquipo();
                    break;
                }
                case "SeleccionarFotoEquipo": {
                    seleccionarFotoEquipo();
                    break;
                }
                case "MostrarGraficoJugadoresEnEquipo": {
                    mostrarGraficoEquipos();
                    break;
                }
                case "MostrarInformeEquipos": {
                    mostrarInformeEquipos();
                    break;
                }
                case "AñadirJugadoresEquipo": {
                    annadirJugadoresEquipo();
                    break;
                }
                case "ModificarEquipo": {
                    modificarEquipo();
                    break;
                }
                case "EliminarEquipo": {
                    eliminarEquipo();
                    break;
                }
                case "NuevaConferencia": {
                    nuevaConferencia();
                    break;
                }
                case "AñadirEquiposConferencia": {
                    anndirEquiposConferencia();
                    break;
                }
                case "ModificarConferencia": {
                    modificarConferencia();
                    break;
                }
                case "EliminarConferencia": {
                    eliminarConferencia();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que muestra un dialogo de seleccion de fichero para guardar los datos.
     *
     * @throws IOException Se lanza si no hay errores en la entrada/salida del programa.
     * @throws ClassNotFoundException Se lanza si no se encuentra la clase.
     */
    private void guardarDatos() throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        int opt = fileChooser.showSaveDialog(vista.frame);
        if(opt == JFileChooser.APPROVE_OPTION){
            modelo.guardarDatos(fileChooser.getSelectedFile());
        }
    }

    /**
     * Método que muestra un dialogo de seleccion de fichero para cargar los datos.
     *
     * @throws IOException Se lanza si no hay errores en la entrada/salida del programa.
     * @throws ClassNotFoundException Se lanza si no se encuentra la clase.
     */
    private void cargarDatos() throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        int opt = fileChooser.showOpenDialog(vista.frame);
        if(opt == JFileChooser.APPROVE_OPTION){
            modelo.cargarDatos(fileChooser.getSelectedFile());
        }
        listar();
    }

    /**
     * Método que muestra un dialogo de seleccion de fichero para cargar los datos.
     *
     * @throws IOException Se lanza si no hay errores en la entrada/salida del programa.
     * @throws URISyntaxException Se lanza si hay algún error en la sintaxis de la uri que pasamos.
     */
    private void abrirDocumentacion() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://github.com/sergonzalezgomez/Pr-ctica3-GestionNBA/wiki/Manual-de-Usuario"));
    }

    /**
     * Método que llamar a los demás métodos existentes de listar.
     */
    private void listar() {
        listarEquipoComboBox();
        listarEquipo();
        listarJugadores();
        listarConferenciasComboBox();
        listarConferencias();
    }

    /**
     * Método que crea un nuevo Jugador a partir de los datos introducidos en sus campos.
     * Si los campos no se han introducido correctamente invoca el método mostrarDialogoError() de la clase Util.
     * Estos campos se comprueban mediante el método datosJugadorCorrectos().
     */
    private void nuevoJugador() {
        if (!datosJugadorCorrectos()) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorDatosIncorrectos"));
        }
        else {
            if(modelo.existeJugador(vista.txtNombreJugador.getText(), vista.txtApellidosJugador.getText())){
                Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorJugadorYaCreado"));
            }
            else {
                Jugador jugador = new Jugador(vista.txtNombreJugador.getText(), vista.txtApellidosJugador.getText(),
                        vista.datePickerFechaJugador.getDate(), (Integer) vista.spnPuntosJugador.getValue(), vista.lblFotoJugador.getIcon());

                if (jugador.getFotoJugador() == null) {
                    ImageIcon icono = new ImageIcon(getClass().getResource("/jugadorSinFoto.png"));
                    ImageIcon iconoEscalado = Util.escalarImageIcon(icono, 100, 100);
                    jugador.setFotoJugador(iconoEscalado);
                }

                modelo.annadirJugador(jugador);
                borrarCamposJugador();
                listarJugadores();
            }
        }
    }

    /**
     * Método que muestra un dialogo de seleccion de fichero para seleccionar una imagen
     * y asignarsela al Jugador seleccionado.
     */
    private void seleccionarFotoJugador() {
        JFileChooser selector = new JFileChooser();
        int opt = selector.showOpenDialog(null);
        if(opt == JFileChooser.APPROVE_OPTION){
            File file = selector.getSelectedFile();
            ImageIcon icono = new ImageIcon(file.getPath());
            ImageIcon iconoEscalado = Util.escalarImageIcon(icono, 100, 100);

            vista.lblFotoJugador.setIcon(iconoEscalado);
        }
    }

    /**
     * Método que comprueba que los datos introducidos a través de los campos no estén vacios.
     *
     * @return true si todos los datos son correctos, false en caso contrario.
     */
    private boolean datosJugadorCorrectos() {
        boolean datosCorrectos = true;
        if (vista.txtNombreJugador.getText().isEmpty() || vista.txtApellidosJugador.getText().isEmpty() || vista.datePickerFechaJugador.getText().isEmpty()) {
            datosCorrectos = false;
        }
        return datosCorrectos;
    }

    /**
     * Método que refresca el JList de Jugadores.
     */
    private void listarJugadores() {
        vista.jugadoresDlm.clear();
        for(Jugador jugador : modelo.getJugadores()){
            vista.jugadoresDlm.addElement(jugador);
        }
    }

    /**
     * Método que vacia todos los campos del panel de creación de Jugadores.
     */
    private void borrarCamposJugador() {
        vista.txtNombreJugador.setText("");
        vista.txtApellidosJugador.setText("");
        vista.datePickerFechaJugador.setText("");
        vista.spnPuntosJugador.setValue(0);
        vista.lblFotoJugador.setIcon(null);
    }

    /**
     * Método que llama a la clase DialogoVerFichaJugador pasándole como parámetro el Jugador seleccionado.
     * Si no ha seleccionado ningún Jugador invoca el método mostrarDialogoError() de la clase Util.
     */
    private void verFichaJugador() {
        Jugador fichaJugador = vista.listaJugadores.getSelectedValue();
        if (fichaJugador == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorFichaJugador"));
        }
        else {
            DialogoVerFichaJugador dialogo = new DialogoVerFichaJugador(fichaJugador, modelo);
        }
    }

    /**
     * Método que muestra una gráfica de tipo BarChart con los Jugadores y sus puntos anotados.
     */
    private void mostrarGraficoJugadores() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Jugador jugador : modelo.getJugadores()){
            String cadenaNombreApellidos = jugador.getNombreJugador() + " " + jugador.getApellidosJugador();
            dataset.setValue(jugador.getPuntosAnotados(), "Puntos NBA", cadenaNombreApellidos);
        }

        JFreeChart grafica = ChartFactory.createBarChart("Puntos por Jugador", "Jugador", "Puntos totales NBA", dataset, PlotOrientation.VERTICAL, true, true, true);

        ChartFrame ventana = new ChartFrame("Gráfica Puntos", grafica);
        ventana.setSize(700,700);
        ventana.setVisible(true);
        ventana.setIconImage(new ImageIcon(getClass().getResource("/pelotaBaloncesto.png")).getImage());
        ventana.setLocationRelativeTo(null);
    }

    /**
     * Método que muestra un informe de la colección Jugadores.
     */
    private void mostrarInformeJugadores() {
        try {
            //JasperCompileManager.compileReportToFile("informes/informeJugadores.jrxml");

            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informeJugadores.jasper"));

            JRBeanCollectionDataSource coleccion = new JRBeanCollectionDataSource(modelo.getJugadores());

            JasperPrint print = JasperFillManager.fillReport(report, null, coleccion);

            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que llama a la clase DialogoModificarJugador pasándole como parámetro el Jugador seleccionado.
     * Si no ha seleccionado ningún Jugador invoca el método mostrarDialogoError() de la clase Util.
     */
    private void modificarJugador() {
        Jugador modificarJugador = vista.listaJugadores.getSelectedValue();
        if (modificarJugador == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorModificarJugador"));
        }
        else {
            DialogoModificarJugador dialogo = new DialogoModificarJugador(modificarJugador, modelo);
            listarJugadores();
        }
    }

    /**
     * Método que elimina el Jugador seleccionado invocando el método eliminarJugador() de la clase Modelo.
     * Si no ha seleccionado ningún Jugador invoca el método mostrarDialogoError() de la clase Util.
     */
    private void eliminarJugador() {
        Jugador jugador = vista.listaJugadores.getSelectedValue();
        if (jugador == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorEliminarJugador"));
        }
        else {
            if (Util.mostrarDialogoSiNo(resourceBundle.getString("texto.mostrarDialogoSiNoEliminarJugador")) == JOptionPane.YES_OPTION) {
                modelo.eliminarJugador(jugador);
                listarJugadores();
            }
        }
    }

    /**
     * Método que crea un nuevo Equipo a partir de los datos introducidos en sus campos.
     * Si los campos no se han introducido correctamente invoca el método mostrarDialogoError() de la clase Util.
     * Estos campos se comprueban mediante el método datosEquipoCorrectos().
     */
    private void nuevoEquipo() {
        if (!datosEquipoCorrectos()) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorDatosIncorrectos"));
        }
        else {
            if(modelo.existeEquipo(vista.txtNombreEquipo.getText())) {
                Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorEquipoYaCreado"));
            }
            else {
                Equipo equipo = new Equipo(vista.txtNombreEquipo.getText(), Integer.parseInt(vista.txtAnnoFundaconEquipo.getText()),
                        vista.txtNombreEstadio.getText(), Float.parseFloat(vista.txtPresupuestoEquipo.getText()),
                        (Conferencia) vista.cmboxConferencias.getSelectedItem(), vista.lblFotoEquipo.getIcon());

                modelo.annadirEquipo(equipo);
                borrarCamposEquipo();
                listar();
            }
        }
    }

    /**
     * Método que muestra un dialogo de selección de fichero para seleccionar una imagen
     * y asignarsela al Equipo seleccionado.
     */
    private void seleccionarFotoEquipo() {
        JFileChooser selector = new JFileChooser();
        int opt = selector.showOpenDialog(null);
        if(opt == JFileChooser.APPROVE_OPTION){
            File file = selector.getSelectedFile();
            ImageIcon icono = new ImageIcon(file.getPath());
            ImageIcon iconoEscalado = Util.escalarImageIcon(icono, 90, 90);
            vista.lblFotoEquipo.setIcon(iconoEscalado);
        }
    }

    /**
     * Método que comprueba que los datos introducidos a través de los campos no estén vacios.
     *
     * @return true si todos los datos son correctos, false en caso contrario.
     */
    private boolean datosEquipoCorrectos() {
        boolean datosCorrectos = true;
        if (vista.txtNombreEquipo.getText().isEmpty() || vista.txtAnnoFundaconEquipo.getText().isEmpty() ||
                vista.txtNombreEstadio.getText().isEmpty() || vista.txtPresupuestoEquipo.getText().isEmpty()) {
            datosCorrectos = false;
        }
        return datosCorrectos;
    }

    /**
     * Metodo que refresca el comboBox de Equipos de la sección del Jugador.
     */
    private void listarEquipoComboBox() {
        vista.equiposDcbm.removeAllElements();
        vista.equiposDcbm.addElement(null);
        for(Equipo equipo : modelo.getEquipos()){
            vista.equiposDcbm.addElement(equipo);
        }
    }

    /**
     * Método que refresca el JList de Equipos.
     */
    private void listarEquipo() {
        vista.equiposDlm.clear();
        for(Equipo equipo : modelo.getEquipos()){
            vista.equiposDlm.addElement(equipo);
        }
    }

    /**
     * Método que vacia todos los campos del panel de creación de Equipos.
     */
    private void borrarCamposEquipo() {
        vista.txtNombreEquipo.setText("");
        vista.txtAnnoFundaconEquipo.setText("");
        vista.txtNombreEstadio.setText("");
        vista.txtPresupuestoEquipo.setText("");
        vista.lblFotoEquipo.setIcon(null);

    }

    /**
     * Método que llama a la clase DialogoVerFichaEquipo pasándole como parámetro el Equipo seleccionado.
     * Si no ha seleccionado ningún Equipo invoca el método mostrarDialogoError() de la clase Util.
     */
    private void verFichaEquipo() {
        Equipo fichaEquipo = vista.listaEquipos.getSelectedValue();
        if (fichaEquipo == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorFichaEquipo"));
        }
        else {
            DialogoVerFichaEquipo dialogo = new DialogoVerFichaEquipo(fichaEquipo, modelo);
            listar();
        }
    }

    /**
     * Método que muestra una gráfica de tipo PieChart con los Equipos y los Jugadores que tiene cada uno.
     */
    private void mostrarGraficoEquipos() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for(Equipo equipo : modelo.getEquipos()) {
            dataset.setValue(equipo.getNombreEquipo() + " - " + equipo.getNumJugadoresEquipo(), equipo.getNumJugadoresEquipo());
        }

        JFreeChart grafica = ChartFactory.createPieChart("Jugadores por Equipo", dataset, true, true, true);
        ChartFrame ventana = new ChartFrame("Número de Jugadores por Equipo", grafica);

        ventana.setSize(700,700);
        ventana.setVisible(true);
        ventana.setIconImage(new ImageIcon(getClass().getResource("/pelotaBaloncesto.png")).getImage());
        ventana.setLocationRelativeTo(null);

    }

    /**
     * Método que muestra un informe de la colección Equipos.
     */
    private void mostrarInformeEquipos() {
        try {
            //JasperCompileManager.compileReportToFile("informes/informeEquipos.jrxml");

            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informeEquipos.jasper"));

            JRBeanCollectionDataSource coleccion = new JRBeanCollectionDataSource(modelo.getEquipos());

            JasperPrint print = JasperFillManager.fillReport(report, null, coleccion);

            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que añade el Jugador seleccionado al Equipo seleccionado.
     */
    private void annadirJugadoresEquipo() {
        Equipo equipo = vista.listaEquipos.getSelectedValue();
        Jugador jugador = vista.listaJugadores.getSelectedValue();
        if (jugador == null || equipo == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorTraspasarJugadorAEquipo"));
        }
        else {
            if (Util.mostrarDialogoSiNo(resourceBundle.getString("texto.mostrarDialogoSiNoTraspasarJugador")) == JOptionPane.YES_OPTION) {
                if (!jugador.getEquipos().contains(equipo)) {
                    jugador.annadirEquiposAJugador(equipo);
                    equipo.annadirJugadorAEquipos(jugador);
                }
                listar();
            }
        }
    }

    /**
     * Método que llama a la clase DialogoModificarEquipo pasándole como parámetro el Equipo seleccionado.
     * Si no ha seleccionado ningún Equipo invoca el método mostrarDialogoError() de la clase Util.
     */
    private void modificarEquipo() {
        Equipo modificarEquipo = vista.listaEquipos.getSelectedValue();
        if (modificarEquipo == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorModificarEquipo"));
        }
        else {
            DialogoModificarEquipo dialogo = new DialogoModificarEquipo(modificarEquipo, modelo);
            listar();
        }
    }

    /**
     * Método que elimina el Equipo seleccionado invocando el método eliminarEquipo() de la clase Modelo.
     * Si no ha seleccionado ningún Equipo invoca el método mostrarDialogoError() de la clase Util.
     */
    private void eliminarEquipo() {
        Equipo equipo = vista.listaEquipos.getSelectedValue();
        if (equipo == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorEliminarEquipo"));
        }
        else {
            if (Util.mostrarDialogoSiNo(resourceBundle.getString("texto.mostrarDialogoSiNoEliminarEquipo")) == JOptionPane.YES_OPTION) {
                modelo.eliminarEquipo(equipo);
                listar();
            }
        }

    }

    /**
     * Método que crea una nueva Conferencia a partir de los datos introducidos en sus campos.
     * Si los campos no se han introducido correctamente invoca el método mostrarDialogoError() de la clase Util.
     * Estos campos se comprueban mediante el método datosConferenciaCorrectos().
     */
    private void nuevaConferencia() {
        if (!datosConferenciaCorrectos()) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorDatosIncorrectos"));
        }
        else {
            if(modelo.existeConferencia(vista.txtNombreConferencia.getText())){
                Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorConferenciaYaCreada"));
            }
            else {
                Icon fotoConferencia;
                if (vista.radioBotConferenciaOeste.isSelected()) {
                    fotoConferencia = (vista.lblFotoConferenciaOeste.getIcon());
                } else {
                    fotoConferencia = (vista.lblFotoConferenciaEste.getIcon());
                }
                Conferencia conferencia = new Conferencia(vista.txtNombreConferencia.getText(),
                        Integer.parseInt(vista.txtAnnoFundacionConferencia.getText()),
                        (Integer) vista.spnTitulosConferencia.getValue(), vista.txtJefeConferencia.getText(), fotoConferencia);

                modelo.annadirConferencia(conferencia);
                borrarCamposConferencia();
                listar();
            }
        }
    }

    /**
     * Método que comprueba que los datos introducidos a través de los campos no estén vacios.
     *
     * @return true si todos los datos son correctos, false en caso contrario.
     */
    private boolean datosConferenciaCorrectos() {
        Boolean datosCorrectos = true;
        if (vista.txtNombreConferencia.getText().isEmpty() || vista.txtAnnoFundacionConferencia.getText().isEmpty() ||
            vista.txtJefeConferencia.getText().isEmpty()) {
            datosCorrectos = false;
        }
        return datosCorrectos;
    }

    /**
     * Método que refresca el JList de Conferencias.
     */
    private void listarConferencias() {
        vista.conferenciasDlm.clear();
        for(Conferencia conferencia : modelo.getConferencias()){
            vista.conferenciasDlm.addElement(conferencia);
        }
    }

    /**
     * Método que vacia todos los campos del panel de creación de Conferencias.
     */
    private void borrarCamposConferencia() {
        vista.txtNombreConferencia.setText("");
        vista.txtAnnoFundacionConferencia.setText("");
        vista.spnTitulosConferencia.setValue(0);
        vista.txtJefeConferencia.setText("");
        vista.radioBotConferenciaEste.setSelected(false);
        vista.radioBotConferenciaOeste.setSelected(false);
    }

    /**
     * Metodo que refresca el comboBox de Conferencias de la sección del Equipo.
     */
    private void listarConferenciasComboBox() {
        vista.conferenciasDcbm.removeAllElements();
        vista.conferenciasDcbm.addElement(null);
        for(Conferencia conferencia : modelo.getConferencias()){
            vista.conferenciasDcbm.addElement(conferencia);
        }
    }

    /**
     * Método que llama a la clase DialogoVerFichaConferencia pasándole como parámetro la Conferencia seleccionada.
     * Si no ha seleccionado ninguna Conferencia invoca el método mostrarDialogoError() de la clase Util.
     */
    private void verFichaConferencia() {
        Conferencia fichaConferencia = vista.listaConferencias.getSelectedValue();
        if (fichaConferencia == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorFichaConferencia"));
        }
        else {
            DialogoVerFichaConferencia dialogo = new DialogoVerFichaConferencia(fichaConferencia, modelo);
        }
    }

    /**
     * Método que añade el Equipo seleccionado a la Conferencia seleccionada.
     */
    private void anndirEquiposConferencia() {
        Equipo equipo = vista.listaEquipos.getSelectedValue();
        Conferencia conferencia = vista.listaConferencias.getSelectedValue();
        if (conferencia == null || equipo == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorAnnadirEquipoAConferencia"));
        }
        else {
            if (Util.mostrarDialogoSiNo(resourceBundle.getString("texto.mostrarDialogoSiNoAnnadirEquipo")) == JOptionPane.YES_OPTION) {
                equipo.setConferencia(conferencia);
                listar();
            }
        }
    }

    /**
     * Método que llama a la clase DialogoModificarConferencia pasándole como parámetro la Conferencia seleccionada.
     * Si no ha seleccionado ninguna Conferencia invoca el método mostrarDialogoError() de la clase Util.
     */
    private void modificarConferencia() {
        Conferencia conferenciaModificada = vista.listaConferencias.getSelectedValue();
        if (conferenciaModificada == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorModificarConferencia"));
        }
        else {
            DialogoModificarConferencia dialogo = new DialogoModificarConferencia(conferenciaModificada, modelo);
            listar();
        }

    }

    /**
     * Método que elimina la Conferencia seleccionada invocando el método eliminarConferencia() de la clase Modelo.
     * Si no ha seleccionado ningún Equipo invoca el método mostrarDialogoError() de la clase Util.
     */
    private void eliminarConferencia() {
        Conferencia conferencia = vista.listaConferencias.getSelectedValue();
        if (conferencia == null) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorEliminarConferencia"));
        }
        else {
            if (Util.mostrarDialogoSiNo(resourceBundle.getString("texto.mostrarDialogoSiNoEliminarConferencia")) == JOptionPane.YES_OPTION) {
                modelo.eliminarConferencia(conferencia);
                listar();
            }
        }
    }

    /**
     * Método que responde a los ActionEvent producidos sobre los componentes asociados.
     * Cuando se seleccione un elemento del JList y se pulse 2 veces sobre él, se mostrará la ficha de este.
     *
     * @param event Evento recibido por el método.
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        Jugador jugador = vista.listaJugadores.getSelectedValue();
        Equipo equipo = vista.listaEquipos.getSelectedValue();
        Conferencia conferencia = vista.listaConferencias.getSelectedValue();
        if (jugador != null) {
            if (event.getClickCount() == 2) {
                verFichaJugador();
            }
        }
        if (equipo != null) {
            if (event.getClickCount() == 2) {
                verFichaEquipo();
            }
        }
        if (conferencia != null) {
            if (event.getClickCount() == 2) {
                verFichaConferencia();
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
