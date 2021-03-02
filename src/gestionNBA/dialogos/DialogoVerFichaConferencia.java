package gestionNBA.dialogos;

import gestionNBA.base.Conferencia;
import gestionNBA.base.Equipo;
import gestionNBA.mvc.modelo.Modelo;

import javax.swing.*;
import java.awt.event.*;
import java.util.ResourceBundle;

public class DialogoVerFichaConferencia extends JDialog {
    private JPanel panelPrincipal;
    private JButton botCerrar;
    private JLabel lblFotoFichaConferencia;
    private JLabel lblNombreFichaConferencia;
    private JLabel lblAnnoFundacionFichaConferencia;
    private JLabel lblTitulosFichaConferencia;
    private JLabel lblJefeFichaConferencia;
    private JList<Equipo> listaEquiposFichaConferencia;
    private ResourceBundle resourceBundle;
    private Conferencia conferencia;
    private Modelo modelo;

    DefaultListModel<Equipo> equiposDlm;

    public DialogoVerFichaConferencia(Conferencia conferencia, Modelo modelo) {
        this.conferencia = conferencia;
        this.modelo = modelo;

        resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle");

        iniciarModelos();
        mostrarDatosFichaConferencia();
        actionListeners();
        initUI();
    }

    /**
     * Método que muestra en los campos los datos de la Conferencia seleccionada.
     */
    private void mostrarDatosFichaConferencia() {
        listarEquiposConferencias();
        lblNombreFichaConferencia.setText(conferencia.getNombreConferencia());
        lblAnnoFundacionFichaConferencia.setText(String.valueOf(conferencia.getAnnoFundacion()));
        lblTitulosFichaConferencia.setText(String.valueOf(conferencia.getTitulos()));
        lblJefeFichaConferencia.setText(conferencia.getJefeConferencia());
        lblFotoFichaConferencia.setIcon(conferencia.getFotoConferencia());
    }

    /**
     * Método que inicializa los diferentes modelos para listar.
     */
    private void iniciarModelos() {
        equiposDlm = new DefaultListModel<>();
        listaEquiposFichaConferencia.setModel(equiposDlm);
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
     * Método que cierra el cuadro de diálogo.
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Metodo que refresca el jlist de Equipos de la seccion de la Conferencia.
     */
    private void listarEquiposConferencias() {
        equiposDlm.clear();
        for(Equipo equipo : modelo.getEquipos()){
            if (equipo.getConferencia() != null && equipo.getConferencia().equals(conferencia)) {
                equiposDlm.addElement(equipo);
            }
        }
    }

    /**
     * Método que define las propiedades del cuadro de diálogo.
     */
    private void initUI() {
        setContentPane(panelPrincipal);
        setModal(true);
        setTitle(resourceBundle.getString("texto.tituloDialogoFichaConferencia"));
        setIconImage(new ImageIcon(getClass().getResource("/pelotaBaloncesto.png")).getImage());
        setSize(450,500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
