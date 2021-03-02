package gestionNBA.dialogos;

import gestionNBA.base.Equipo;
import gestionNBA.base.Jugador;
import gestionNBA.mvc.modelo.Modelo;
import gestionNBA.util.Util;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.ResourceBundle;

public class DialogoVerFichaJugador extends JDialog {
    private JPanel panelPrincipal;
    private JButton botCerrar;
    private JLabel lblNombreFichaJugador;
    private JLabel lblApellidosFichaJugador;
    private JLabel lblFechaNacimientoFichaJugador;
    private JLabel lblPuntosFichaJugador;
    private JLabel lblEquipoFichaJugador;
    private JLabel lblFotoFichaJugador;
    private JList listaEquiposDialogoJugador;
    private JButton botEliminarEquipos;
    private ResourceBundle resourceBundle;
    private Jugador jugador;
    private Modelo modelo;

    DefaultListModel<Equipo> equiposDlm;

    public DialogoVerFichaJugador(Jugador jugador, Modelo modelo) {
        this.jugador = jugador;
        this.modelo = modelo;

        resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle");

        iniciarModelos();
        mostrarDatosFichaJugador();
        actionListeners();
        initUI();
    }

    /**
     * Método que inicializa los diferentes modelos para listar.
     */
    private void iniciarModelos() {
        equiposDlm = new DefaultListModel<>();
        listaEquiposDialogoJugador.setModel(equiposDlm);
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

        botEliminarEquipos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Util.mostrarDialogoSiNo(resourceBundle.getString("texto.mostrarDialogoSiNoJugadores")) == JOptionPane.YES_OPTION) {
                    eliminarEquiposJugador();
                    listarEquipos();
                }
            }
        });
    }

    /**
     * Método que elimina los Equipos seleccionados del Jugador actual.
     */
    private void eliminarEquiposJugador() {
        List<Equipo> equipoLista = listaEquiposDialogoJugador.getSelectedValuesList();
        for (Equipo equipo : equipoLista) {
            equipo.eliminarJugador(jugador);
            jugador.eliminarEquipo(equipo);
        }
        listarEquipos();
    }

    /**
     * Metodo que refresca el jlist de Equipos de la seccion del Jugador.
     */
    private void listarEquipos() {
        equiposDlm.clear();
        for(Equipo equipo : modelo.getEquipos()){
            if (jugador.getEquipos().contains(equipo)) {
                equiposDlm.addElement(equipo);
            }
        }
    }

    /**
     * Método que muestra en los campos los datos del Jugador seleccionado.
     */
    private void mostrarDatosFichaJugador() {
        listarEquipos();
        lblNombreFichaJugador.setText(jugador.getNombreJugador());
        lblApellidosFichaJugador.setText(jugador.getApellidosJugador());
        lblFechaNacimientoFichaJugador.setText(String.valueOf(jugador.getFechaNacimiento()));
        lblPuntosFichaJugador.setText(String.valueOf(jugador.getPuntosAnotados()));
        lblFotoFichaJugador.setIcon(jugador.getFotoJugador());
    }

    /**
     * Método que cierra el cuadro de diálogo.
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Método que define las propiedades del cuadro de diálogo.
     */
    private void initUI() {
        setContentPane(panelPrincipal);
        setModal(true);
        setTitle(resourceBundle.getString("texto.tituloDialogoFichaJugador"));
        setIconImage(new ImageIcon(getClass().getResource("/pelotaBaloncesto.png")).getImage());
        setSize(450,550);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
