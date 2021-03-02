package gestionNBA.mvc.modelo;

import gestionNBA.base.Conferencia;
import gestionNBA.base.Equipo;
import gestionNBA.base.Jugador;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Modelo {
    private List<Jugador> jugadores;
    private List<Equipo> equipos;
    private List<Conferencia> conferencias;

    public Modelo() {
        jugadores = new LinkedList<>();
        equipos = new LinkedList<>();
        conferencias = new LinkedList<>();
    }

    /**
     * Método que devuelve la lista de Jugadores.
     *
     * @return la lista jugadores.
     */
    public List<Jugador> getJugadores() { return jugadores; }

    /**
     * Método que devuelve la lista de Equipos.
     *
     * @return la lista equipos.
     */
    public List<Equipo> getEquipos() { return equipos; }

    /**
     * Método que devuelve la cantidad de elementos dentro de la lista de Equipos.
     *
     * @return el tamaño de la lista equipos.
     */
    public int getJugadoresEquipo() { return equipos.size(); }

    /**
     * Método que devuelve la lista de Conferencias.
     *
     * @return la lista conferencias.
     */
    public List<Conferencia> getConferencias() { return conferencias; }

    /**
     * Método que añade el Jugador que se pasa por parámetro a la lista de Jugadores.
     *
     * @param jugador Jugador recibido por el método.
     */
    public void annadirJugador(Jugador jugador){
        jugadores.add(jugador);
        Collections.sort(jugadores);
    }

    /**
     * Método que añade el Equipo que se pasa por parámetro a la lista de Equipos.
     *
     * @param equipo Equipo recibido por el método.
     */
    public void annadirEquipo(Equipo equipo){
        equipos.add(equipo);
        Collections.sort(equipos);
    }

    /**
     * Método que añade la Conferencia que se pasa por parámetro a la lista de Conferencias.
     *
     * @param conferencia Conferencia recibida por el método.
     */
    public void annadirConferencia(Conferencia conferencia){
        conferencias.add(conferencia);
        Collections.sort(conferencias);
    }

    /**
     * Método que elimina el Jugador seleccionado de la lista de Jugadores.
     *
     * @param jugadorEliminado Jugador recibido por el método.
     */
    public void eliminarJugador(Jugador jugadorEliminado){
        jugadores.remove(jugadorEliminado);
    }

    /**
     * Método que elimina el Equipo seleccionado de la lista de Equipos.
     * Los Jugadores que pertenecían a este Equipo pasarán al estado de "agente libre".
     *
     * @param equipoEliminado Equipo recibido por el método.
     */
    public void eliminarEquipo(Equipo equipoEliminado){
        for(Jugador jugador : jugadores){
            if (jugador.getEquipos().contains(equipoEliminado)) {
                jugador.eliminarEquipo(equipoEliminado);
            }
        }
        equipos.remove(equipoEliminado);
    }

    /**
     * Método que elimina la Conferencia seleccionada de la lista de Conferencias.
     * Los Equipos que pertenecían a esta Conferencia pasarán al estado de "sin Conferencia".
     *
     * @param conferenciaEliminada Conferencia recibida por el método.
     */
    public void eliminarConferencia(Conferencia conferenciaEliminada) {
        for(Equipo equipo : getEquipos()){
            if (equipo.getConferencia() != null && equipo.getConferencia().getNombreConferencia().equals(conferenciaEliminada.getNombreConferencia())) {
                equipo.setConferencia(null);
            }
        }
        conferencias.remove(conferenciaEliminada);
    }

    /**
     * Método que comprueba si un Jugador con su nombre y apellidos ya existe en la lista de Jugadores.
     * El nombre y apellidos de este se pasan por parámetro.
     *
     * @param nombre String recibida por el método.
     * @param apellidos String recibida por el método
     *
     * @return true si el Jugador ya existe, false en caso contrario.
     */
    public boolean existeJugador(String nombre, String apellidos){
        for(Jugador jugador : jugadores){
            String cadena = jugador.getNombreJugador() + " " + jugador.getApellidosJugador();
            if(cadena.equalsIgnoreCase(nombre + " " + apellidos)){
                return true;
            }
        }
        return false;
    }

    /**
     * Método que comprueba si un Equipo con su nombre ya existe en la lista de Equipos.
     * El nombre de este se pasa por parámetro.
     *
     * @param nombre String recibida por el método.
     *
     * @return true si el Equipo ya existe, false en caso contrario.
     */
    public boolean existeEquipo(String nombre){
        for(Equipo equipo : equipos){
            if(equipo.getNombreEquipo().equalsIgnoreCase(nombre)){
                return true;
            }
        }
        return false;
    }

    /**
     * Método que comprueba si una Conferencia con su nombre ya existe en la lista de Conferencias.
     * El nombre de este se pasa por parámetro.
     *
     * @param nombre String recibida por el método.
     *
     * @return true si la Conferencia ya existe, false en caso contrario.
     */
    public boolean existeConferencia(String nombre){
        for(Conferencia conferencia : conferencias){
            if(conferencia.getNombreConferencia().equalsIgnoreCase(nombre)){
                return true;
            }
        }
        return false;
    }

    /**
     * Método que guarda los datos de las listas con los objetos creados.
     *
     * @param file File recibida por el método.
     *
     * @throws IOException Se lanza si no hay errores en la entrada/salida del programa.
     */
    public void guardarDatos(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream serializador = new ObjectOutputStream(fos);

        serializador.writeObject(jugadores);
        serializador.writeObject(equipos);
        serializador.writeObject(conferencias);

        serializador.close();
    }

    /**
     * Método que carga los datos de las listas con los objetos creados.
     *
     * @param file File recibida por el método.
     *
     * @throws IOException Se lanza si no hay errores en la entrada/salida del programa.
     * @throws ClassNotFoundException Se lanza si no se encuentra la clase.
     */
    public void cargarDatos(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream deserializador = new ObjectInputStream(fis);
        jugadores = (List<Jugador>) deserializador.readObject();
        equipos = (List<Equipo>) deserializador.readObject();
        conferencias = (List<Conferencia>) deserializador.readObject();

        deserializador.close();
    }
}
