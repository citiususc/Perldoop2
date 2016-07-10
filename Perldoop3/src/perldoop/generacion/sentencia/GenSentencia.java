package perldoop.generacion.sentencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.asignacion.Asignacion;
import perldoop.modelo.arbol.constante.*;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.generacion.*;

/**
 * Clase generadora de sentencia
 *
 * @author César Pomar
 */
public class GenSentencia {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenSentencia(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(StcLista s) {
        List<CodigoJava> codigo = tabla.getBloqueActual().getCodigo();
        for (Expresion exp : s.getLista().getExpresiones()) {
            boolean sentencia = false;
            //Calculamos las sentencias
            if (exp instanceof ExpVariable) {
                Variable var = ((ExpVariable) exp).getVariable();
                if (var instanceof VarMy) {
                    sentencia = true;
                } else {
                    continue;//Las lecturas de variables no sirven para nada
                }
            } else if (exp instanceof ExpConstante) {
                Constante cst = ((ExpConstante) exp).getConstante();
                if (cst instanceof CadenaComando) {
                    sentencia = true;
                } else {
                    continue;//Las constantes no sirven para nada
                }
            } else if (exp instanceof ExpAsignacion) {
                sentencia = true;
            } else if (exp instanceof ExpFuncion) {
                sentencia = true;
            } else if (exp instanceof ExpFuncion5) {
                sentencia = true;
            } else if (exp instanceof ExpRegulares) {
                Regulares reg = ((ExpRegulares) exp).getRegulares();
                if (reg instanceof RegularSubs || reg instanceof RegularTrans) {
                    sentencia = true;
                }
            }
            //Analizamos todo en subarbol en busca de algo que merezca ser ejecutado
            if (tabla.getOpciones().isSentenciasEstrictas() && !sentencia) {
                List<Simbolo> hijos = new ArrayList<>(100);
                hijos.add(exp);
                while (!hijos.isEmpty()) {
                    Simbolo hijo = hijos.remove(hijos.size() - 1);
                    hijos.addAll(Arrays.asList(hijo.getHijos()));
                    if(hijo instanceof Funcion || hijo instanceof Asignacion){
                        sentencia = true;
                        break;
                    }
                }
            }
            //Si es sentencia la escribimos, si no usamos la funcion auxiliar para evaluarla
            if (sentencia) {
                codigo.add(new SentenciaJava(exp.getCodigoGenerado().toString() + ";"));
            } else {
                StringBuilder c = new StringBuilder(exp.getCodigoGenerado().length() + 30);
                codigo.add(new SentenciaJava(
                        c.append("Perl.eval(").append(exp.getCodigoGenerado()).append(");").toString()
                ));
            }
        }
    }

    public void visitar(StcBloque s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(StcFlujo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(StcPaquete s) {
        tabla.getClase().setNombre(s.getId().toString());
        for (Terminal p : s.getPaquetes().getIdentificadores()) {
            tabla.getClase().getPaquetes().add(p.toString());
        }
    }

    public void visitar(StcComentario s) {
        tabla.getBloqueActual().getCodigo().add(new SentenciaJava(s.getComentario().toString()));
    }

    public void visitar(StcDeclaracion s) {
        //No genera codigo
    }

}
