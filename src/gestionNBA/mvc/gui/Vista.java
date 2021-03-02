package gestionNBA.mvc.gui;

import com.github.lgooddatepicker.components.DatePicker;
import gestionNBA.base.Conferencia;
import gestionNBA.base.Equipo;
import gestionNBA.base.Jugador;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Vista {
    JFrame frame;
    JPanel panelGeneral;
    JLabel lblNombre;
    JTextField txtNombreJugador;
    JTextField txtApellidosJugador;
    JSpinner spnPuntosJugador;
    JComboBox cmboxEquipos;
    JButton botSeleccionarFotoJugador;
    JLabel lblFotoJugador;
    JLabel lblFotoEquipo;
    JButton botCrearJugador;
    DatePicker datePickerFechaJugador;
    JButton botGraficoJugadores;
    JButton botConfiguracion;
    JButton botEliminarJugador;
    JButton botAnnadirJugadores;
    JButton botEliminarEquipo;
    JButton botAnnadirEquipo;
    JButton botEliminarConferencia;
    JList<Jugador> listaJugadores;
    JList<Equipo> listaEquipos;
    JList<Conferencia> listaConferencias;
    JButton botGuardarDatos;
    JButton botCargarDatos;
    JButton botUsuarios;
    JTextField txtNombreEquipo;
    JTextField txtNombreEstadio;
    JButton botCrearEquipo;
    JButton botSeleccionarFotoEquipo;
    JTextField txtNombreConferencia;
    JSpinner spnTitulosConferencia;
    JButton botCrearConferencia;
    JRadioButton radioBotConferenciaEste;
    JRadioButton radioBotConferenciaOeste;
    JTextField txtAnnoFundaconEquipo;
    JTextField txtAnnoFundacionConferencia;
    JButton botModificarJugador;
    JButton botModificarEquipo;
    JButton botModificarConferencia;
    JTextField txtPresupuestoEquipo;
    JLabel lblFotoConferenciaEste;
    JLabel lblFotoConferenciaOeste;
    JTextField txtJefeConferencia;
    JComboBox cmboxConferencias;
    JButton botGraficoEquipos;
    JButton botInformeJugadores;
    JButton botInformeEquipos;
    JButton botDocumentacion;


    DefaultListModel<Jugador> jugadoresDlm;
    DefaultListModel<Equipo> equiposDlm;
    DefaultListModel<Conferencia> conferenciasDlm;

    DefaultComboBoxModel<Equipo> equiposDcbm;
    DefaultComboBoxModel<Conferencia> conferenciasDcbm;

    public Vista() {
        initUI();
        iniciarModelos();
        controlPorTeclado();
    }

    /**
     * Método que define las propiedades del cuadro de la aplicación.
     */
    private void initUI() {
        frame = new JFrame("NBA");
        frame.setContentPane(panelGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(getClass().getResource("/pelotaBaloncesto.png")).getImage());
        frame.setVisible(true);
        frame.setSize(890,550);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Método que inicializa los diferentes modelos para listar.
     */
    private void iniciarModelos() {
        jugadoresDlm = new DefaultListModel<>();
        equiposDlm = new DefaultListModel<>();
        conferenciasDlm = new DefaultListModel<>();
        equiposDcbm = new DefaultComboBoxModel<>();
        conferenciasDcbm = new DefaultComboBoxModel<>();

        listaJugadores.setModel(jugadoresDlm);
        listaEquipos.setModel(equiposDlm);
        listaConferencias.setModel(conferenciasDlm);

        cmboxConferencias.setModel(conferenciasDcbm);
    }

    /**
     * Método que asocia componentes con su acceso Mnemónico.
     */
    private void controlPorTeclado() {
        botCrearJugador.getRootPane().setDefaultButton(botCrearJugador);

        botCrearJugador.setMnemonic(KeyEvent.VK_J);
        botSeleccionarFotoJugador.setMnemonic(KeyEvent.VK_U);

        botCrearEquipo.setMnemonic(KeyEvent.VK_E);
        botSeleccionarFotoEquipo.setMnemonic(KeyEvent.VK_B);

        botCrearConferencia.setMnemonic(KeyEvent.VK_C);

        botGuardarDatos.setMnemonic(KeyEvent.VK_G);
        botCargarDatos.setMnemonic(KeyEvent.VK_A);
        botDocumentacion.setMnemonic(KeyEvent.VK_D);
        botConfiguracion.setMnemonic(KeyEvent.VK_P);

        botGraficoJugadores.setMnemonic(KeyEvent.VK_1);
        botInformeJugadores.setMnemonic(KeyEvent.VK_2);
        botModificarJugador.setMnemonic(KeyEvent.VK_3);
        botEliminarJugador.setMnemonic(KeyEvent.VK_4);

        botGraficoEquipos.setMnemonic(KeyEvent.VK_6);
        botInformeEquipos.setMnemonic(KeyEvent.VK_7);
        botAnnadirJugadores.setMnemonic(KeyEvent.VK_8);
        botModificarEquipo.setMnemonic(KeyEvent.VK_9);
        botEliminarEquipo.setMnemonic(KeyEvent.VK_K);

        botAnnadirEquipo.setMnemonic(KeyEvent.VK_I);
        botModificarConferencia.setMnemonic(KeyEvent.VK_F);
        botEliminarConferencia.setMnemonic(KeyEvent.VK_V);
    }
}
