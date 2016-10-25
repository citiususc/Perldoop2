package perldoop.semantica.util;

import java.util.List;
import perldoop.error.GestorErrores;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.expresion.ExpFuncion5;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;
import perldoop.util.ParserEtiquetas;

/**
 * Clase para la gestion semantica de tipos
 *
 * @author César Pomar
 */
public final class Tipos {

    /**
     * Compara si dos tipos son iguales
     *
     * @param t1 Tipo 1
     * @param t2 Tipo 1
     * @return Tipo 1 igual a tipo 2
     */
    private static boolean igual(Tipo t1, Tipo t2) {
        List<Byte> tl1 = t1.getTipo();
        List<Byte> tl2 = t2.getTipo();
        if (tl1.size() != tl2.size()) {
            return false;
        } else {
            for (int i = 0; i < tl1.size(); i++) {
                if (!tl1.get(i).equals(tl2.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Compara si el tipo 1 es igual o puede convertirse al tipo 2
     *
     * @param t1 Tipo 1
     * @param t2 Tipo 2
     * @return Tipo 1 compatible con tipo 2
     */
    private static boolean compatible(Tipo t1, Tipo t2) {
        List<Byte> tl1 = t1.getTipo();
        List<Byte> tl2 = t2.getTipo();
        if (tl2.size() == 1) {
            return true;
        }
        if (t1.isRef() && t2.isBox() || t1.isBox() && t2.isRef()) {
            return true;
        }
        return tl2.size() == tl1.size() && ((!t1.isRef() && !t2.isRef()) || igual(t1, t2));
    }

    /**
     * Comprueba el casting de un simbolo a otro para verificar la conversión
     *
     * @param s Simbolo con tipo de origen
     * @param t Tipo de destino
     * @param ge Sistema para lanzar el error si porcede
     */
    public static void casting(Simbolo s, Tipo t, GestorErrores ge) {
        casting(s, s.getTipo(), t, ge);
    }

    /**
     * Comprueba el casting de un simbolo a otro para verificar la conversión, en este caso el tipo de simbolo se
     * especifica a parte por si hiciera falta modificarlo
     *
     * @param s Simbolo origen
     * @param ts Tipo del simbolo origen
     * @param t Tipo de destino
     * @param ge Sistema para lanzar el error si porcede
     */
    public static void casting(Simbolo s, Tipo ts, Tipo t, GestorErrores ge) {
        if (!compatible(ts, t)) {
            ge.error(Errores.ERROR_CASTING, Buscar.tokenInicio(s), ParserEtiquetas.parseTipo(s.getTipo()), ParserEtiquetas.parseTipo(t));
            throw new ExcepcionSemantica(Errores.ERROR_CASTING);
        }
    }

    /**
     * Conversion del tipo a un numero
     *
     * @param s Simbolo
     * @return Tipo numerico
     */
    public static Tipo toNumber(Simbolo s) {
        Tipo t = s.getTipo();
        /////////TODO MEJORAR
        if (s.getTipo().isArrayOrList() && (s instanceof ExpFuncion || s instanceof ExpFuncion5
                || (s instanceof ExpColeccion && ((ExpColeccion) s).getColeccion() instanceof ColParentesis))) {
            t = t.getSubtipo(1);
        }
        //////////////////////   
        switch (t.getTipo().get(0)) {
            case Tipo.BOOLEAN:
                return new Tipo(Tipo.INTEGER);
            case Tipo.INTEGER:
            case Tipo.LONG:
            case Tipo.FLOAT:
            case Tipo.DOUBLE:
                return new Tipo(t);
            case Tipo.NUMBER:
            case Tipo.BOX:
            case Tipo.STRING:
                return new Tipo(Tipo.DOUBLE);
            case Tipo.FILE:
                return new Tipo(Tipo.INTEGER);
            case Tipo.ARRAY:
            case Tipo.LIST:
            case Tipo.MAP:
                return new Tipo(Tipo.INTEGER);
            case Tipo.REF:
                return new Tipo(Tipo.INTEGER);
        }
        return new Tipo(Tipo.DOUBLE);
    }

}
