package gestionNBA;

import gestionNBA.dialogos.SplashScreen;
import gestionNBA.mvc.gui.Controlador;
import gestionNBA.mvc.gui.Vista;
import gestionNBA.mvc.modelo.Modelo;
import gestionNBA.util.Util;

import java.util.Locale;


public class Principal {

    public static void main(String[] args) {
        Locale locale = Util.obtenerLocale();
        Locale.setDefault(locale);

        SplashScreen splashScreen = new SplashScreen();
        splashScreen.iniciarHilo();

        Vista vista = new Vista();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(vista, modelo);
    }
}
