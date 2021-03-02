package gestionNBA.dialogos;

import gestionNBA.base.Conferencia;
import gestionNBA.base.Equipo;
import gestionNBA.base.Jugador;
import gestionNBA.mvc.modelo.Modelo;
import gestionNBA.util.Util;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

public class DialogoModificarEquipo extends JDialog {
    private JPanel panelPrincipal;
    private JButton botModificar;
    private JButton botCerrar;
    private JLabel lblFotoModificada;
    private JTextField txtNombreModificado;
    private JTextField txtAnnoFundacionModificado;
    private JButton botAnnadirImagenModificada;
    private JTextField txtEstadioModificado;
    private JTextField txtPresupuestoModificiado;
    private JList<Jugador> listaJugadoresDialogoEquipo;
    private JComboBox cmboxConferenciasFichaEquipo;
    private ResourceBundle resourceBundle;
    private Equipo equipo;
    private Modelo modelo;

    DefaultListModel<Jugador> jugadoresDlm;
    DefaultComboBoxModel<Conferencia> conferenciasDcm;

    public DialogoModificarEquipo(Equipo equipo, Modelo modelo) {
        this.equipo = equipo;
        this.modelo = modelo;

        resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle");

        iniciarModelos();
        mostrarDatosFichaEquipo();
        actionListeners();
        initUI();
    }

    /**
     * Método que inicializa los diferentes modelos para listar.
     */
    private void iniciarModelos() {
        jugadoresDlm = new DefaultListModel<>();
        listaJugadoresDialogoEquipo.setModel(jugadoresDlm);
        conferenciasDcm = new DefaultComboBoxModel<>();
        cmboxConferenciasFichaEquipo.setModel(conferenciasDcm);
    }

    /**
     * Método que refresca el jlist de Jugadores de la sección del Equipo.
     */
    private void listarJugadores() {
        jugadoresDlm.clear();
        for(Jugador jugador : modelo.getJugadores()){
            jugadoresDlm.addElement(jugador);

        }
    }

    /**
     * Método que refresca el comboBox de Conferencias de la sección del Equipo.
     */
    private void listarConferenciasComboBox() {
        conferenciasDcm.removeAllElements();
        conferenciasDcm.addElement(null);
        for(Conferencia conferencia : modelo.getConferencias()){
            conferenciasDcm.addElement(conferencia);
        }
    }

    /**
     * Método que alberga los ActionListeners de la clase.
     */
    private void actionListeners() {
        botModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        botCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        botAnnadirImagenModificada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarFotoEquipo();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ENTER
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onOK(); }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Método que muestra en los campos los datos del Equipo seleccionado.
     */
    private void mostrarDatosFichaEquipo() {
        listarJugadores();
        listarConferenciasComboBox();
        txtNombreModificado.setText(equipo.getNombreEquipo());
        txtAnnoFundacionModificado.setText(String.valueOf(equipo.getAnnoFundacion()));
        txtEstadioModificado.setText(equipo.getEstadio());
        txtPresupuestoModificiado.setText(String.valueOf(equipo.getPresupuesto()));
        cmboxConferenciasFichaEquipo.setSelectedItem(equipo.getConferencia());
        lblFotoModificada.setIcon(equipo.getFotoEquipo());
    }

    /**
     * Método que modifica el Equipo seleccionado a partir de los datos introducidos en sus campos.
     * Si los campos no se han introducido de forma correcta lanza un diálogo de error indicándolo.
     * Estos campos se comprueban mediante el método datosEquipoCorrectos().
     */
    private void onOK() {
        if (datosEquipoCorrectos()) {
            if (Util.mostrarDialogoSiNo(resourceBundle.getString("texto.dialogoSiNoCambios")) == JOptionPane.YES_OPTION) {
                equipo.setNombreEquipo(txtNombreModificado.getText());
                equipo.setAnnoFundacion(Integer.parseInt(txtAnnoFundacionModificado.getText()));
                equipo.setEstadio(txtEstadioModificado.getText());
                equipo.setPresupuesto(Float.parseFloat(txtPresupuestoModificiado.getText()));
                equipo.setConferencia((Conferencia) cmboxConferenciasFichaEquipo.getSelectedItem());
                equipo.setFotoEquipo(lblFotoModificada.getIcon());
                annadirJugadoresAEquipo();
                dispose();
            }
        }
        else {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorDatosIncorrectos"));
        }

    }

    /**
     * Método que añade todos los Jugadores seleccionados del jlist al Equipo seleccionado.
     */
    private void annadirJugadoresAEquipo() {
        List<Jugador> jugadorLista = listaJugadoresDialogoEquipo.getSelectedValuesList();
        for (Jugador jugador : jugadorLista) {
            if (!jugador.getEquipos().contains(equipo)) {
                jugador.annadirEquiposAJugador(equipo);
                equipo.annadirJugadorAEquipos(jugador);
            }
        }
    }

    /**
     * Método que comprueba que los datos introducidos a través de los campos no estén vacios.
     *
     * @return true si todos los datos son correctos, false en caso contrario.
     */
    private boolean datosEquipoCorrectos() {
        boolean datosCorrectos = true;
        if (txtNombreModificado.getText().isEmpty() || txtAnnoFundacionModificado.getText().isEmpty() || txtEstadioModificado.getText().isEmpty()
            || txtPresupuestoModificiado.getText().isEmpty()) {
            datosCorrectos = false;
        }
        return  datosCorrectos;
    }

    /**
     * Método que muestra un dialogo de seleccion de fichero para seleccionar una imagen
     * y asignarsela al Equipo seleccionado.
     */
    private void seleccionarFotoEquipo() {
        JFileChooser selector = new JFileChooser();
        int opt = selector.showOpenDialog(null);
        if(opt == JFileChooser.APPROVE_OPTION){
            File file = selector.getSelectedFile();
            ImageIcon icono = new ImageIcon(file.getPath());
            ImageIcon iconoEscalado = Util.escalarImageIcon(icono, 100, 100);
            lblFotoModificada.setIcon(iconoEscalado);
        }
    }

    /**
     * Método que cierra el cuadro de diálogo.
     */
    private void onCancel() { dispose(); }

    /**
     * Método que define las propiedades del cuadro de diálogo.
     */
    private void initUI() {
        setContentPane(panelPrincipal);
        setModal(true);
        setTitle(resourceBundle.getString("texto.tituloDialogoModificarEquipo"));
        setIconImage(new ImageIcon(getClass().getResource("/pelotaBaloncesto.png")).getImage());
        setSize(500,700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
