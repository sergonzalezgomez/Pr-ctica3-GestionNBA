package gestionNBA.util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Util {

    /**
     * Método que muestra un JOptionPane de tipo error con el mensaje que se le pasa por parámetro.
     *
     * @param mensaje String recibida por el método.
     */
    public static void mostrarDialogoError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Método que muestra un JOptionPane de tipo YES/NO option con el mensaje que se le pasa por parámetro.
     *
     * @param mensaje String recibida por el método.
     *
     * @return JOptionPande de tipo YES/NO option.
     */
    public static int mostrarDialogoSiNo(String mensaje) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("idiomaResourceBundle");
        return JOptionPane.showConfirmDialog(null, mensaje, resourceBundle.getString("texto.tituloMostrarDialogoSiNo"), JOptionPane.YES_NO_OPTION);
    }

    /**
     * Método que recibe y devuelve un ImageIcon y lo reescala con los valores de alto y ancho
     * que se le pasan por parámetro.
     *
     * @param icon ImageIcon recibida por el método.
     * @param alto int recibida por el método.
     * @param ancho int recibida por el método.
     *
     * @return iconoEscalado que es la imagen reescalada.
     */
    public static ImageIcon escalarImageIcon( ImageIcon icon, int alto, int ancho) {
        Image imagen = icon.getImage();
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
        return iconoEscalado;
    }

    /**
     * Método que carga el fichero de preferencias.conf y con ello define el idioma de la aplicacción.
     *
     * @return locale, con ello se define el idioma de la aplicacción.
     */
    public static Locale obtenerLocale() {
        Locale locale = null;

        Properties properties = new Properties();
        try {
            properties.load(new FileReader("data/preferencias.conf"));
            String pais = properties.getProperty("pais");
            String idioma = properties.getProperty("idioma");

            if(pais.equals("UK")){
                locale = new Locale("en", "UK");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(locale == null){
            locale = new Locale("es", "ES");
        }
        return locale;
    }

    /**
     * Método que crea el directorio data (donde se guarda el fichero preferencias.conf) si no existiese.
     */
    public static void crearSiNoExisteDirectorioDatos(){
        File directorio = new File("data");
        if(!directorio.exists()) {
            directorio.mkdir();
        }
    }
}
