package gestionNBA.dialogos;

import gestionNBA.base.Conferencia;
import gestionNBA.base.Equipo;
import gestionNBA.mvc.modelo.Modelo;
import gestionNBA.util.Util;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.ResourceBundle;

public class DialogoModificarConferencia extends JDialog {
    private JPanel panelPrincipal;
    private JButton botModificar;
    private JButton botCerrar;
    private JLabel lblFotoModificada;
    private JTextField txtNombreModificado;
    private JTextField txtAnnoFundacionModificado;
    private JButton botAnnadirImagenModificada;
    private JTextField txtTitulosModificado;
    private JTextField txtJefeModificiado;
    private JList listaEquiposDialogoEquipo;
    private ResourceBundle resourceBundle;
    private Conferencia conferencia;
    private Modelo modelo;

    DefaultListModel<Equipo> equiposDlm;

    public DialogoModificarConferencia(Conferencia conferencia, Modelo modelo) {
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
        txtNombreModificado.setText(conferencia.getNombreConferencia());
        txtAnnoFundacionModificado.setText(String.valueOf(conferencia.getAnnoFundacion()));
        txtTitulosModificado.setText(String.valueOf(conferencia.getTitulos()));
        txtJefeModificiado.setText(conferencia.getJefeConferencia());
        lblFotoModificada.setIcon(conferencia.getFotoConferencia());
    }

    /**
     * Método que refresca el jlist de Equipos de la sección de la Conferencia.
     */
    private void listarEquiposConferencias() {
        equiposDlm.clear();
        for(Equipo equipo : modelo.getEquipos()){
            if (equipo.getConferencia() != null && equipo.getConferencia().getNombreConferencia().equals(conferencia.getNombreConferencia())) {
                equiposDlm.addElement(equipo);
            }
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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        botAnnadirImagenModificada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarFotoConferencia();
            }
        });

        // call onCancel() on ENTER
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private boolean datosConferenciaCorrectos() {
        boolean correcto = true;
        if (txtNombreModificado.getText().isEmpty() || txtAnnoFundacionModificado.getText().isEmpty() || txtTitulosModificado.getText().isEmpty()
            || txtJefeModificiado.getText().isEmpty()) {
            correcto = false;
        }
        return correcto;
    }

    /**
     * Método que inicializa los diferentes modelos para listar.
     */
    private void iniciarModelos() {
        equiposDlm = new DefaultListModel<>();
        listaEquiposDialogoEquipo.setModel(equiposDlm);
    }

    /**
     * Método que muestra un dialogo de seleccion de fichero para seleccionar una imagen
     * y asignarsela a la Conferencia seleccionada.
     */
    private void seleccionarFotoConferencia() {
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
     * Método que modifica la Conferencia seleccionada a partir de los datos introducidos en sus campos.
     * Si los campos no se han introducido de forma correcta lanza un diálogo de error indicándolo.
     * Estos campos se comprueban mediante el método datosConferenciaCorrectos().
     */
    private void onOK() {
        if (!datosConferenciaCorrectos()) {
            Util.mostrarDialogoError(resourceBundle.getString("texto.dialogoErrorDatosIncorrectos"));
        } else {
            if (Util.mostrarDialogoSiNo(resourceBundle.getString("texto.dialogoSiNoCambios")) == JOptionPane.YES_OPTION) {
                conferencia.setNombreConferencia(txtNombreModificado.getText());
                conferencia.setAnnoFundacion(Integer.parseInt(txtAnnoFundacionModificado.getText()));
                conferencia.setTitulos(Integer.parseInt(txtTitulosModificado.getText()));
                conferencia.setJefeConferencia(txtJefeModificiado.getText());
                conferencia.setFotoConferencia(lblFotoModificada.getIcon());
                dispose();
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
        setTitle(resourceBundle.getString("texto.tituloDialogoModificarConferencia"));
        setIconImage(new ImageIcon(getClass().getResource("/pelotaBaloncesto.png")).getImage());
        setSize(500,700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
