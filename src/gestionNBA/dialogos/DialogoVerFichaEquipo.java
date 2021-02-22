package gestionNBA.dialogos;

import gestionNBA.base.Equipo;
import gestionNBA.base.Jugador;
import gestionNBA.mvc.modelo.Modelo;
import gestionNBA.util.Util;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.ResourceBundle;

public class DialogoVerFichaEquipo extends JDialog {
    private JPanel panelPrincipal;
    private JButton botCerrar;
    private JLabel lblFotoFichaEquipo;
    private JLabel lblNombreFichaEquipo;
    private JLabel lblAnnoFundacionFichaEquipo;
    private JLabel lblEstadioFichaEquipo;
    private JLabel lblPresupuestoFichaEquipo;
    private JList<Jugador> listaJugadoresFichaEquipo;
    private JButton botEliminarJugadores;
    private JLabel lblConferenciaEquipo;
    private ResourceBundle resourceBundle;
    private Equipo equipo;
    private Modelo modelo;

    DefaultListModel<Jugador> jugadoresDlm;

    public DialogoVerFichaEquipo(Equipo equipo, Modelo modelo) {
        this.equipo = equipo;
        this.modelo = modelo;

        resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle");

        iniciarModelos();
        mostrarDatosFichaEquipo();
        actionListeners();
        initUI();
    }

    /**
     * Método que alberga los ActionListeners de la clase.
     */
    private void actionListeners() {
        botCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        botEliminarJugadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Util.mostrarDialogoSiNo(resourceBundle.getString("texto.mostrarDialogoSiNoJugadores")) == JOptionPane.YES_OPTION) {
                    eliminarJugadoresEquipo();
                    listarJugadores();
                }
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
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Método que muestra en los campos los datos del Equipo seleccionado.
     */
    private void mostrarDatosFichaEquipo() {
        listarJugadores();
        lblNombreFichaEquipo.setText(equipo.getNombreEquipo());
        lblAnnoFundacionFichaEquipo.setText(String.valueOf(equipo.getAnnoFundacion()));
        lblEstadioFichaEquipo.setText(equipo.getEstadio());
        lblPresupuestoFichaEquipo.setText(String.valueOf(equipo.getPresupuesto()) + "M €");
        if (equipo.getConferencia() == null) {
            lblConferenciaEquipo.setText("Sin Conferencia");
        } else {
            lblConferenciaEquipo.setText(equipo.getConferencia().getNombreConferencia());
        }
        lblFotoFichaEquipo.setIcon(equipo.getFotoEquipo());
    }

    /**
     * Método que inicializa los diferentes modelos para listar.
     */
    private void iniciarModelos() {
        jugadoresDlm = new DefaultListModel<>();
        listaJugadoresFichaEquipo.setModel(jugadoresDlm);
    }

    /**
     * Método que elimina los Jugadores seleccionados del Equipo actual.
     */
    private void eliminarJugadoresEquipo() {
        List<Jugador> jugadorLista = listaJugadoresFichaEquipo.getSelectedValuesList();
        for (Jugador jugador : jugadorLista) {
            jugador.setEquipo(null);
        }
    }

    /**
     * Metodo que refresca el jlist de Jugadores de la seccion del Equipo.
     */
    private void listarJugadores() {
        jugadoresDlm.clear();
        for(Jugador jugador : modelo.getJugadores()){
            if (jugador.getEquipo() != null) {
                String nombreEquipo = jugador.getEquipo().getNombreEquipo();
                if (equipo.getNombreEquipo().equals(nombreEquipo)) {
                    jugadoresDlm.addElement(jugador);
                }
            }
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
        setTitle(resourceBundle.getString("texto.tituloDialogoFichaEquipo"));
        setIconImage(new ImageIcon(getClass().getResource("/pelotaBaloncesto.png")).getImage());
        setSize(450,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
