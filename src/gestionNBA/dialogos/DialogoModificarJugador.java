package gestionNBA.dialogos;

import com.github.lgooddatepicker.components.DatePicker;
import gestionNBA.base.Equipo;
import gestionNBA.base.Jugador;
import gestionNBA.mvc.modelo.Modelo;
import gestionNBA.util.Util;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

public class DialogoModificarJugador extends JDialog {
    private JPanel panelPrincipal;
    private JButton botCerrar;
    private JLabel lblFotoModificada;
    private JButton botModificar;
    private JTextField txtNombreModificado;
    private JSpinner spnPuntosModificados;
    private JTextField txtApellidosModificados;
    private DatePicker datePickerFechaModificada;
    private JButton botAnnadirImagenModificada;
    private JList listaEquipos;
    private ResourceBundle resourceBundle;
    private Jugador jugador;
    private Modelo modelo;

    DefaultListModel<Equipo> equiposDlm;

    public DialogoModificarJugador(Jugador jugador, Modelo modelo) {
        this.jugador = jugador;
        this.modelo = modelo;

        resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle");

        iniciarModelos();
        mostrarDatosFichaJugador();
        actionListeners();
        initUI();
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
            public void actionPerformed(ActionEvent e) {
                seleccionarFotoJugador();
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
     * Método que inicializa los diferentes modelos para listar.
     */
    private void iniciarModelos() {
        equiposDlm = new DefaultListModel<>();
        listaEquipos.setModel(equiposDlm);
    }

    /**
     * Metodo que refresca el comboBox de Equipos de la sección del Jugador.
     */
    private void listarEquipos() {
        equiposDlm.clear();
        for(Equipo equipo : modelo.getEquipos()){
            equiposDlm.addElement(equipo);
        }
    }

    /**
     * Método que muestra en los campos los datos del Jugador seleccionado.
     */
    private void mostrarDatosFichaJugador() {
        listarEquipos();
        txtNombreModificado.setText(jugador.getNombreJugador());
        txtApellidosModificados.setText(jugador.getApellidosJugador());
        datePickerFechaModificada.setDate(jugador.getFechaNacimiento());
        spnPuntosModificados.setValue(jugador.getPuntosAnotados());
        lblFotoModificada.setIcon(jugador.getFotoJugador());

    }

    /**
     * Método que comprueba que los datos introducidos a través de los campos no estén vacios.
     *
     * @return true si todos los datos son correctos, false en caso contrario.
     */
    private boolean datosJugadorCorrectos() {
        boolean datosCorrectos = true;
        if (txtNombreModificado.getText().isEmpty() || txtApellidosModificados.getText().isEmpty() || datePickerFechaModificada.getText().isEmpty()) {
            datosCorrectos = false;
        }
        return datosCorrectos;
    }

    /**
     * Método que muestra un dialogo de selección de fichero para seleccionar una imagen
     * y asignarsela al Jugador seleccionado.
     */
    private void seleccionarFotoJugador() {
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
     * Método que modifica el Jugador seleccionado a partir de los datos introducidos en sus campos.
     * Si los campos no se han introducido de forma correcta lanza un diálogo de error indicándolo.
     * Estos campos se comprueban mediante el método datosJugadorCorrectos().
     */
    private void onOK() {
        if (datosJugadorCorrectos()) {
            if (Util.mostrarDialogoSiNo(resourceBundle.getString("texto.dialogoSiNoCambios")) == JOptionPane.YES_OPTION) {
                jugador.setNombreJugador(txtNombreModificado.getText());
                jugador.setApellidosJugador(txtApellidosModificados.getText());
                jugador.setAnnoNacimiento(datePickerFechaModificada.getDate());
                jugador.setPuntosAnotados(Integer.parseInt(String.valueOf(spnPuntosModificados.getValue())));
                jugador.setFotoJugador(lblFotoModificada.getIcon());
                annadirEquiposAJugador();
                dispose();
            }
        }
        else {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorDatosIncorrectos"));
        }
    }

    /**
     * Método que añade todos los Equipos seleccionados del jlist al Jugador seleccionado.
     */
    private void annadirEquiposAJugador() {
        List<Equipo> equipoLista = listaEquipos.getSelectedValuesList();
        for (Equipo equipo : equipoLista) {
            if (!jugador.getEquipos().contains(equipo)) {
                jugador.annadirEquiposAJugador(equipo);
                equipo.annadirJugadorAEquipos(jugador);
            }
        }
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
        setTitle(resourceBundle.getString("texto.tituloDialogoModificarJugador"));
        setIconImage(new ImageIcon(getClass().getResource("/pelotaBaloncesto.png")).getImage());
        setSize(450,500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
