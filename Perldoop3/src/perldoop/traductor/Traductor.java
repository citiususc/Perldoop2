package perldoop.traductor;

import java.util.List;
import perldoop.generacion.Generador;
import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.semantica.Semantica;
import perldoop.excepciones.ExcepcionSemantica;

/**
 * Clase que verifica la semanca del arbol de simbolos.
 *
 * @author César Pomar
 */
public final class Traductor implements Acciones {

    private List<Simbolo> simbolos;
    private int index;
    private Opciones opciones;
    private Semantica semantica;
    private Generador generador;
    private int errores;
    private boolean generar;

    /**
     * Construye el traductor
     *
     * @param simbolos Simbolos
     * @param semantica Semantica
     * @param generador Generador
     * @param opciones Opciones
     */
    public Traductor(List<Simbolo> simbolos, Semantica semantica, Generador generador, Opciones opciones) {
        errores = 0;
        index = 0;
        this.simbolos = simbolos;
        this.semantica = semantica;
        this.generador = generador;
        this.opciones = opciones;
        this.semantica.setAcciones(this);
    }

    /**
     * Obtiene las opciones
     *
     * @return Opciones
     */
    public Opciones getOpciones() {
        return opciones;
    }

    /**
     * Establece las opciones
     *
     * @param opciones Opciones
     */
    public void setOpciones(Opciones opciones) {
        this.opciones = opciones;
    }

    /**
     * Obtiene el número de errores, si no hay errores el analisis se ha realizado correctamente.
     *
     * @return Número de errores
     */
    public int getErrores() {
        return errores;
    }

    /**
     * Inicia la verificacion y generacion del codigo.
     *
     * @return Número de errores
     */
    public int traducir() {
        boolean error = false;
        for (; index < simbolos.size(); index++) {
            if (!error) {
                try {
                    analizar(simbolos.get(index));
                } catch (ExcepcionSemantica ex) {
                    error = true;
                    errores++;
                }
            } else if (simbolos.get(index) instanceof Sentencia) {
                error = false;
            }
        }
        return errores;
    }

    @Override
    public void analizar(Simbolo s) throws ExcepcionSemantica {
        generar = true;
        s.aceptar(semantica);
        if (generar && errores == 0) {
            s.aceptar(generador);
        }
    }

    @Override
    public void reAnalizarDespuesDe(Simbolo s) {
        List<Simbolo> l = simbolos.subList(index, simbolos.size());
        int posicion = simbolos.indexOf(s);
        int distancia = 1;
        Simbolo padre = simbolos.get(index).getPadre();
        while (padre != null && l.indexOf(padre) + index < posicion) {
            distancia++;
            padre = padre.getPadre();
        }
        for (int i = index; i < posicion - distancia + 1; i++) {
            simbolos.set(i, simbolos.set(i + distancia, simbolos.get(i)));
        }
        index--;
    }

    @Override
    public void saltarGenerador() {
        generar = false;
    }

}
