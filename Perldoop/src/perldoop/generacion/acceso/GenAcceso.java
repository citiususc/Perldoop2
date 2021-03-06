package perldoop.generacion.acceso;

import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.variable.VarExistente;
import perldoop.modelo.generacion.Declaracion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase generadora de acceso
 *
 * @author César Pomar
 */
public class GenAcceso {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenAcceso(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Accede a una o varias posiciones dentro de una expresion de tipo colección
     *
     * @param s Simbolo del Acceso
     * @param expresion Codigo de la expresión
     * @param coleccion Coleccion de indices para el acceso
     * @param comentarioRef Comentario a escribir entre la expresión y su acceso
     */
    private void accesoColeccion(Acceso s, StringBuilder expresion, Coleccion coleccion, String comentarioRef) {
        StringBuilder codigo = new StringBuilder(100);
        boolean escritura = false;//Acceso para escribir
        boolean noRef = false;//No generar referencia
        Simbolo uso = Buscar.getUsoCol((Expresion) s.getPadre());
        if (uso instanceof Igual) {
            noRef = true;
            escritura = Buscar.isHijo(s, ((Igual) uso).getIzquierda());
        } else {
            uso = Buscar.getUso((Expresion) s.getPadre());
            //Si hay otro acceso anidado o una desreferenciacion, obviamos crear la referencia
            if (uso instanceof Acceso || (Buscar.isCamino(uso, ColLlave.class, ExpColeccion.class)
                    && Buscar.getUso((Expresion) Buscar.getPadre(uso, 1)) instanceof AccesoDesRef)
                    || (uso instanceof Lista && uso.getPadre() instanceof Coleccion)) {
                noRef = true;

            }
        }
        if (isDelete(s)) {//Codigo para funcion delete
            codigo.append(comentarioRef);
            codigo.append(expresion).append(",");
            Tipo t = s.getExpresion().getTipo();
            Character c = Buscar.getContexto(s);
            t = t.isRef() ? t.getSubtipo(1) : t;
            if (c == '$' && coleccion.getTipo().isColeccion()) {
                if (t.isMap()) {
                    codigo.append(coleccion).append(",true");
                } else {
                    codigo.append("Pd.last(").append(coleccion).append(")");
                }
            } else {
                codigo.append(coleccion);
            }
        } else if (!coleccion.getTipo().isColeccion()) {
            //Encampsular en Referencia solo si es necesario
            if (s.getTipo().isRef() && !noRef) {
                codigo.append("new ").append(Tipos.declaracion(s.getTipo())).append("(");
            }
            codigo.append(expresion).append(comentarioRef);
            genAcceso(s, coleccion, escritura, codigo);
            if (s.getTipo().isRef() && !noRef) {
                codigo.append(")");
            }
        } else {
            codigo.append("Pd.").append(comentarioRef);
            genMultiAcceso(s, expresion, coleccion, escritura, codigo);
        }
        s.setCodigoGenerado(codigo);
    }

    /**
     * Genera un multiple acceso a una coleccion
     *
     * @param s Simbolo acceso
     * @param expresion Expresion
     * @param index Posicion del acceso
     * @param escritura Acceso como escritura
     * @param codigo Codigo para generar el acceso
     */
    private void genMultiAcceso(Acceso s, StringBuilder expresion, Simbolo index, boolean escritura, StringBuilder codigo) {
        char contexto = Buscar.getContexto(s);
        codigo.append(contexto == '$' ? 's' : contexto == '@' ? 'a' : 'h').append("Access(");
        codigo.append(expresion).append(',').append(index);
        if (contexto == 'h') {
            codigo.append(", f->Casting.box(");
            //Si en el acceso hay otra coleccion
            if (s.getExpresion().getTipo().getSubtipo(1).isColeccion()) {
                codigo.append("new Ref(f))");
            } else {
                codigo.append("f)");
            }
        }
        codigo.append(escritura ? ',' : ')');
    }

    /**
     * Comprueba si el acceso es con fines de borrado
     *
     * @param s Simbolo acceso
     * @return Acceso para borrado
     */
    private boolean isDelete(Acceso s) {
        Simbolo uso = Buscar.getUso((Expresion) s.getPadre());
        if (uso instanceof ExpFuncion) {
            Funcion f = ((ExpFuncion) uso).getFuncion();
            return f.getPaquetes().isVacio() && f.getIdentificador().getValor().equals("delete");
        }
        return false;
    }

    /**
     * Genera el acceso a una coleccion
     *
     * @param s Simbolo acceso
     * @param index Posicion del acceso
     * @param escritura Acceso como escritura
     * @param codigo Codigo para generar el acceso
     */
    private static void genAcceso(Acceso s, Simbolo index, boolean escritura, StringBuilder codigo) {
        //Ignorar referencia en acceso anidado
        Tipo t = s.getExpresion().getTipo();
        if (t.isRef()) {
            t = t.getSubtipo(1);
        }
        if (t.isArray()) {
            codigo.append("[").append(index).append("]");
        } else if (t.isList()) {
            if (escritura) {
                codigo.append(".set(").append(index).append(",");
            } else {
                codigo.append(".get(").append(index).append(")");
            }
        } else if (escritura) {
            codigo.append(".put(").append(index).append(",");
        } else {
            codigo.append(".get(").append(index).append(")");
        }
    }

    /**
     * Replica un acceso para lectura y escritura
     *
     * @param exp Expresion acceso
     * @param lectura Simbolo para la lectura
     * @param escritura Simbolo para la escritura
     * @param tabla Tabla generador
     */
    public static void getReplica(ExpAcceso exp, Simbolo lectura, Simbolo escritura, TablaGenerador tabla) {
        Acceso acceso = exp.getAcceso();
        if (lectura == null) {//Si no se necesita lectura obviamos esta comporbacion
            lectura = new SimboloAux(null, new StringBuilder());
            escritura.getCodigoGenerado().append(acceso.getExpresion());
        } else if (!Buscar.isRepetible(acceso.getExpresion())) {//Comprobar que la expresion es repetible
            Tipo te = acceso.getExpresion().getTipo().getSubtipo(1);//Quitar el ref de acceso anidado
            //Declarar variable aux
            String aux = tabla.getGestorReservas().getAux();
            tabla.getDeclaraciones().add(new Declaracion(exp, te, aux));
            //Subtituir codigo original
            lectura.setCodigoGenerado(new StringBuilder(aux));
            escritura.setCodigoGenerado(new StringBuilder(100).append("(").append(aux).append("=").append(acceso.getExpresion()).append(")"));
        } else {
            lectura.setCodigoGenerado(new StringBuilder(100).append(acceso.getExpresion()));
            escritura.setCodigoGenerado(new StringBuilder(100).append(acceso.getExpresion()));
        }
        //Acceso a referencia
        if (acceso instanceof AccesoDesRef) {
            String get = genDesRef(acceso);
            if (!get.isEmpty()) {
                escritura.getCodigoGenerado().insert(0, ((AccesoDesRef) acceso).getContexto().getComentario());
                escritura.getCodigoGenerado().append(Tipos.declaracion(exp.getTipo()).insert(0, " = new ").append("("));
                lectura.getCodigoGenerado().append(get);
                return;
            }
            Simbolo valor = Buscar.getExpresion(acceso.getExpresion()).getValor();
            if (valor instanceof ColLlave) {
                valor = ((ColLlave) valor).getLista().getExpresiones().get(0);
            }
            if (valor instanceof ExpAcceso) {
                lectura.getCodigoGenerado().setLength(0);
                escritura.getCodigoGenerado().setLength(0);
                getReplica((ExpAcceso) valor, lectura, escritura, tabla);
            } else {
                escritura.setCodigoGenerado(exp.getCodigoGenerado());
                lectura.setCodigoGenerado(exp.getCodigoGenerado());
            }
            return;
        }else if (acceso instanceof AccesoColRef){
            String get = genDesRef(acceso);
            lectura.getCodigoGenerado().append(get);
            escritura.getCodigoGenerado().append(get);
        }
        //Acceso a coleccion
        Simbolo colL;
        Simbolo colE;
        if (acceso instanceof AccesoCol) {
            colE = colL = ((AccesoCol) acceso).getColeccion();
        } else {
            escritura.getCodigoGenerado().append(((AccesoColRef) acceso).getFlecha().getComentario());
            colE = colL = ((AccesoColRef) acceso).getColeccion();
        }
        if (!Buscar.isRepetible(colL)) {
            //Declarar variable aux
            String aux = tabla.getGestorReservas().getAux();
            tabla.getDeclaraciones().add(new Declaracion(exp, colL.getTipo(), aux));
            //Subtituir codigo original
            colL = new SimboloAux(colL.getTipo(), new StringBuilder(aux));
            colE = new SimboloAux(colE.getTipo(), new StringBuilder(100).append(aux).append("=").append(colE));
        }
        genAcceso(acceso, colL, false, lectura.getCodigoGenerado());
        genAcceso(acceso, colE, true, escritura.getCodigoGenerado());
    }

    /**
     * Genera el get para acceder a una referencia siempre que este sea necesario
     *
     * @param a Acceso
     * @return Acceso a referencia
     */
    private static String genDesRef(Acceso a) {
        Expresion exp = Buscar.getExpresion(a.getExpresion());
        //Coger elemento entre llaves
        if (exp.getValor() instanceof ColLlave) {
            exp = ((ColLlave) exp.getValor()).getLista().getExpresiones().get(0);
        }
        //Acceso para escirtura
        if (a instanceof AccesoDesRef) {
            Simbolo uso = Buscar.getUsoCol((Expresion) a.getPadre());
            if (uso instanceof Igual && Buscar.isHijo(exp, ((Igual) uso).getIzquierda()) && exp.getValor() instanceof VarExistente) {
                return Tipos.declaracion(exp.getTipo()).insert(0, " = new ").append("(").toString();
            }
        }
        //Acceso para lectura
        if (!(exp.getValor() instanceof AccesoCol) && !(exp.getValor() instanceof AccesoColRef)
                && !(exp.getValor() instanceof ColCorchete) && !(exp.getValor() instanceof ColLlave)) {
            return ".get()";
        }
        return "";
    }

    /**
     * Accede a una referencia
     *
     * @param s Simbolo del Acceso a referencia
     * @param comentarioSimbolo Comentario del simbolo que indica la desreferenciación
     */
    private void AccesoReferencia(Acceso s, String comentarioSimbolo) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(comentarioSimbolo);
        if (s.getExpresion().getTipo().isBox()) {
            Tipo ref = new Tipo(s.getTipo()).add(0, Tipo.REF);
            codigo.append("((").append(Tipos.declaracion(ref)).append(")").append(s.getExpresion()).append(").get()");
        } else {
            codigo.append(s.getExpresion());
            codigo.append(genDesRef(s));
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(AccesoCol s) {
        accesoColeccion(s, s.getExpresion().getCodigoGenerado(), s.getColeccion(), "");
    }

    public void visitar(AccesoColRef s) {
        Expresion exp = s.getExpresion();
        accesoColeccion(s, new StringBuilder(100).append(exp).append(genDesRef(s)), s.getColeccion(), s.getFlecha().getComentario());
    }

    public void visitar(AccesoDesRef s) {
        AccesoReferencia(s, s.getContexto().getComentario());
    }

    public void visitar(AccesoRef s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("new ").append(Tipos.declaracion(s.getTipo())).append(s.getBarra().getComentario());
        codigo.append("(").append(s.getExpresion().getCodigoGenerado()).append(")");
        s.setCodigoGenerado(codigo);
    }

}
