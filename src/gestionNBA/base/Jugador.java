package gestionNBA.base;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jugador implements Comparable<Jugador>, Serializable{

    private String nombreJugador;
    private String apellidosJugador;
    private LocalDate fechaNacimiento;
    private int puntosAnotados;
    private Icon fotoJugador;
    private List<Equipo>equipos;

    public Jugador(String nombre, String apellidos, LocalDate fechaNacimiento, int puntosAnotados, Icon fotoJugador) {
        super();
        this.nombreJugador = nombre;
        this.apellidosJugador = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.puntosAnotados = puntosAnotados;
        equipos = new ArrayList<Equipo>();
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

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public void annadirEquiposAJugador(Equipo equipo) { equipos.add(equipo); }

    public void eliminarEquipo(Equipo equipo){
        equipos.remove(equipo);
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
        if (equipos.isEmpty()) {
            cadena = nombreJugador + " " + apellidosJugador + " - Agente Libre";
        }
        else if (equipos.size() == 1) {
            cadena = nombreJugador + " " + apellidosJugador + " - " + equipos.size() + " equipo";
        } else {
            cadena = nombreJugador + " " + apellidosJugador + " - " + equipos.size() + " equipos";
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
