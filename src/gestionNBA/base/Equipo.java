package gestionNBA.base;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipo implements Comparable<Equipo>, Serializable {

    private String nombreEquipo;
    private int annoFundacion;
    private String estadio;
    private float presupuesto;
    private Conferencia conferencia;
    private Icon fotoEquipo;
    private List<Jugador> jugadores;


    public Equipo(String nombre, int annoFundacion, String estadio, float presupuesto, Conferencia conferencia,Icon fotoEquipo) {
        super();
        this.nombreEquipo = nombre;
        this.annoFundacion = annoFundacion;
        this.estadio = estadio;
        this.presupuesto = presupuesto;
        this.conferencia = conferencia;
        jugadores = new ArrayList<>();
        this.fotoEquipo = fotoEquipo;
    }

    public String getNombreEquipo() { return nombreEquipo; }

    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }

    public int getAnnoFundacion() { return annoFundacion; }

    public void setAnnoFundacion(int annoFundacion) { this.annoFundacion = annoFundacion; }

    public String getEstadio() { return estadio; }

    public float getPresupuesto() { return presupuesto; }

    public void setConferencia(Conferencia conferencia) { this.conferencia = conferencia; }

    public Conferencia getConferencia() { return conferencia; }

    public void setEstadio(String estadio) { this.estadio = estadio; }

    public void setPresupuesto(float presupuesto) { this.presupuesto = presupuesto; }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void annadirJugadorAEquipos(Jugador jugador) { jugadores.add(jugador); }

    public int getNumJugadoresEquipo() { return getJugadores().size(); }

    public Icon getFotoEquipo() {
        return fotoEquipo;
    }

    public void setFotoEquipo(Icon fotoEquipo) {
        this.fotoEquipo = fotoEquipo;
    }

    public void eliminarJugador(Jugador jugador){
        jugadores.remove(jugador);
    }

    @Override
    public String toString() {
        String cadena = "";
        if (getConferencia()==null) {
            cadena = nombreEquipo + " - Sin Conferencia";
        }
        else {
            cadena = nombreEquipo + " - " + conferencia.getNombreConferencia();
        }
        return cadena;
    }

    @Override
    public int compareTo(Equipo equipo) {
        if(equipo == null){
            throw new NullPointerException("equipo es null");
        }
        if(equipo.getClass() != Equipo.class){
            throw new ClassCastException("el objeto no es de tipo Equipo");
        }

        if(nombreEquipo.equalsIgnoreCase(equipo.nombreEquipo)){
            return estadio.compareTo(equipo.estadio);
        }
        return nombreEquipo.compareTo(equipo.nombreEquipo);
    }
}
