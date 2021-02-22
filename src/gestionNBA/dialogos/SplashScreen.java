package gestionNBA.dialogos;

import javax.swing.*;
import java.awt.*;


public class SplashScreen extends JDialog implements Runnable{

    private static final long serialVersionUID = 1L;
    private JProgressBar barraProgreso;

    public SplashScreen() {
        initUI();
    }

    /**
     * Método que define las propiedades del cuadro de diálogo.
     */
    private void initUI() {
        setBounds(100, 100, 637, 566);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JLabel lblImagen = new JLabel();
        lblImagen.setIcon(new ImageIcon(SplashScreen.class.getResource("/nba.jpg")));
        contentPane.add(lblImagen, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(2, 1, 0, 0));
        barraProgreso = new JProgressBar();
        barraProgreso.setStringPainted(true);
        //barraProgreso.setForeground(Color.);
        panelInferior.add(barraProgreso);

        JLabel lblNombre = new JLabel("© Sergio González Gómez");
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        panelInferior.add(lblNombre);

        //Anado el panel inferior al principal
        contentPane.add(panelInferior, BorderLayout.SOUTH);

        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setSize(550, 600);
        setVisible(true);
    }

    /**
     * Método que define la función del hilo de la barra de progreso.
     */
    @Override
    public void run() {
        try {
            for(int i = 0; i < 100; i++) {
                Thread.sleep(30);
                barraProgreso.setValue(i);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        dispose();
    }

    /**
     * Método que inicializa y ejecuta el hilo de la barra de progreso.
     */
    public void iniciarHilo() {
        Thread hilo = new Thread(new SplashScreen());
        hilo.start();

        try {
            hilo.join();
            setVisible(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
