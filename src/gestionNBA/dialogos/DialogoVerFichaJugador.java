package gestionNBA.dialogos;

import gestionNBA.base.Jugador;

import javax.swing.*;
import java.awt.event.*;
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
    private JLabel lblTxtNombreEquipo;
    private ResourceBundle resourceBundle;
    private Jugador jugador;

    public DialogoVerFichaJugador(Jugador jugador) {
        this.jugador = jugador;
        mostrarDatosFichaJugador();

        resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle");

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
     * Método que muestra en los campos los datos del Jugador seleccionado.
     */
    private void mostrarDatosFichaJugador() {
        lblNombreFichaJugador.setText(jugador.getNombreJugador());
        lblApellidosFichaJugador.setText(jugador.getApellidosJugador());
        lblFechaNacimientoFichaJugador.setText(String.valueOf(jugador.getFechaNacimiento()));
        lblPuntosFichaJugador.setText(String.valueOf(jugador.getPuntosAnotados()));
        if (jugador.getEquipo() == null) {
            lblEquipoFichaJugador.setText("Agente Libre");
        }
        else {
            lblEquipoFichaJugador.setIcon(jugador.getEquipo().getFotoEquipo());
            lblTxtNombreEquipo.setText(jugador.getEquipo().getNombreEquipo());
        }
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
