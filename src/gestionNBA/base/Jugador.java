package gestionNBA.base;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;

public class Jugador implements Comparable<Jugador>, Serializable{

    private String nombreJugador;
    private String apellidosJugador;
    private LocalDate fechaNacimiento;
    private int puntosAnotados;
    private Icon fotoJugador;
    private Equipo equipo;

    public Jugador(String nombre, String apellidos, LocalDate fechaNacimiento, int puntosAnotados, Equipo equipo, Icon fotoJugador) {
        super();
        this.nombreJugador = nombre;
        this.apellidosJugador = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.puntosAnotados = puntosAnotados;
        this.equipo = equipo;
        this.fotoJugador = fotoJugador;
    }

    public String getNombreJugador() { return nombreJugador; }

    public void setNombreJugador(String nombre) { this.nombreJugador = nombre; }

    public String getApellidosJugador() { return apellidosJugador; }

    public void setApellidosJugador(String apellidos) { this.apellidosJugador = apellidos; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public void setAnnoNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public int getPuntosAnotados() { return puntosAnotados; }

    public void setPuntosAnotados(int puntosAnotados) { this.puntosAnotados = puntosAnotados; }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        //Si antes ya tenia equipo, lo elimino de su lista
        if(this.equipo != null){
            this.equipo.getJugadores().remove(this);
        }
        this.equipo = equipo;
        //Tambien annado este jugador a la lista de jugadores de su equipo
        if(equipo != null){
            equipo.getJugadores().add(this);
            Collections.sort(equipo.getJugadores());
        }
    }

    public Icon getFotoJugador() {
        return fotoJugador;
    }

    public void setFotoJugador(Icon fotoJugador) {
        this.fotoJugador = fotoJugador;
    }

    @Override
    public String toString() {
        String cadena = "";
        if (getEquipo()==null) {
            cadena = nombreJugador + " " + apellidosJugador + " - Agente Libre";
        }
        else {
            cadena = nombreJugador + " " + apellidosJugador + " - " + equipo.getNombreEquipo();
        }
        return cadena;
    }

    @Override
    public int compareTo(Jugador jugador) {

        if(jugador == null){
            throw new NullPointerException("jugador es null");
        }
        if(jugador.getClass() != Jugador.class){
            throw new ClassCastException("el objeto no es de tipo Jugador");
        }

        if(nombreJugador.equals(jugador.nombreJugador)){
            return apellidosJugador.compareTo(jugador.apellidosJugador);
        }
        return nombreJugador.compareTo(jugador.nombreJugador);
    }
}
