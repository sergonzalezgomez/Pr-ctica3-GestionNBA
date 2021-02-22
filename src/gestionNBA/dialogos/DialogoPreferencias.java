package gestionNBA.dialogos;

import gestionNBA.util.Util;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class DialogoPreferencias extends JDialog {
    private JPanel panelPrincipal;
    private JButton botAceptar;
    private JButton botCerrar;
    private JRadioButton radioBotSpain;
    private JRadioButton radioBotUk;
    private ResourceBundle resourceBundle;

    public DialogoPreferencias() {
        resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle");

        cargarConfiguracion();
        actionListeners();
        initUI();
    }

    /**
     * Método que alberga los ActionListeners de la clase.
     */
    private void actionListeners() {
        botAceptar.addActionListener(new ActionListener() {
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

        // call onCancel() on ENTER
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    /**
     * Método que llama a guardarConfiguracion() y pregunta mediante cuadro de diálogo
     * si deseas cerrar la aplicación para que se guarden los cambios.
     */
    private void onOK() {
        guardarConfiguracion();
        if(Util.mostrarDialogoSiNo(resourceBundle.getString("texto.dialogoSiNoCerrarApp")) == JOptionPane.YES_OPTION){
            System.exit(2);
        }
        dispose();
    }

    /**
     * Método que guarda la configuración de idiomas de la aplicación en el fichero preferencias.conf.
     * Si no existe este fichero (preferencias.conf), creará uno nuevo en la carpeta data
     */
    private void guardarConfiguracion() {
        Properties propiedades = new Properties();
        String idioma;
        String pais;
        if(radioBotSpain.isSelected()){
            idioma = "es";
            pais = "ES";
        } else {
            idioma = "en";
            pais = "UK";
        }
        propiedades.setProperty("idioma", idioma);
        propiedades.setProperty("pais", pais);
        Util.crearSiNoExisteDirectorioDatos();
        try {
            propiedades.store(new FileWriter("data/preferencias.conf"), "Fichero de preferencias");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que carga la última configuración guardada en el fichero preferencias.conf
     */
    private void cargarConfiguracion() {
        Properties properties = new Properties();
        try {

            properties.load(new FileReader("data/preferencias.conf"));

            String pais = properties.getProperty("pais");

            if(pais.equals("ES")){
                radioBotSpain.setSelected(true);
            }else {
                radioBotUk.setSelected(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        setTitle(resourceBundle.getString("texto.tituloDialogoPreferencias"));
        setIconImage(new ImageIcon(getClass().getResource("/pelotaBaloncesto.png")).getImage());
        setSize(200,200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
