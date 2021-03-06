package perldoop.generacion;

import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.*;
import perldoop.modelo.arbol.abrirbloque.*;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.aritmetica.*;
import perldoop.modelo.arbol.asignacion.*;
import perldoop.modelo.arbol.binario.*;
import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.arbol.cadena.*;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.comparacion.*;
import perldoop.modelo.arbol.cuerpo.*;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.flujo.*;
import perldoop.modelo.arbol.fuente.*;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.funciondef.*;
import perldoop.modelo.arbol.funcionsub.*;
import perldoop.modelo.arbol.handle.*;
import perldoop.modelo.arbol.lectura.*;
import perldoop.modelo.arbol.lista.*;
import perldoop.modelo.arbol.logico.*;
import perldoop.modelo.arbol.modificador.*;
import perldoop.modelo.arbol.modulos.*;
import perldoop.modelo.arbol.numero.*;
import perldoop.modelo.arbol.paquete.*;
import perldoop.modelo.arbol.rango.Rango;
import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.arbol.std.*;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.generacion.ClaseJava;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.TablaSimbolos;

/**
 * Generador de codigo java
 *
 * @author César Pomar
 */
public class Generador implements Visitante {

    private TablaGenerador tabla;
    private FachadaGeneradores fachada;

    /**
     * Contruye el generador
     *
     * @param tablaSimbolos Tabla de símbolos
     * @param opciones opciones
     * @param fichero Fichero Perl
     */
    public Generador(TablaSimbolos tablaSimbolos, Opciones opciones, String fichero) {
        tabla = new TablaGenerador(tablaSimbolos, opciones, fichero);
        fachada = new FachadaGeneradores(tabla);
    }

    /**
     * Obtiene la tabla de símbolos
     *
     * @return Tabla de símbolos
     */
    public TablaSimbolos getTablaSimbolos() {
        return tabla.getTablaSimbolos();
    }

    /**
     * Establece la tabla de símbolos
     *
     * @param tablaSimbolos Tabla de símbolos
     */
    public void setTablaSimbolos(TablaSimbolos tablaSimbolos) {
        tabla.setTablaSimbolos(tablaSimbolos);
    }

    /**
     * Obtiene las opciones
     *
     * @return Opciones
     */
    public Opciones getOpciones() {
        return tabla.getOpciones();
    }

    /**
     * Establece las opciones
     *
     * @param opciones Opciones
     */
    public void setOpciones(Opciones opciones) {
        tabla.setOpciones(opciones);
    }

    /**
     * Obtiene el fichero Perl
     *
     * @return Fichero Perl
     */
    public String getFichero() {
        return tabla.getFichero();
    }

    /**
     * Establece el fichero Perl
     *
     * @param fichero Fichero Perl
     */
    public void setFichero(String fichero) {
        tabla.setFichero(fichero);
    }

    /**
     * Obtiene la clase
     *
     * @return Clase
     */
    public ClaseJava getClase() {
        return tabla.getClase();
    }

    @Override
    public void visitar(Raiz s) {
        fachada.getGenRaiz().visitar(s);
    }

    @Override
    public void visitar(Fuente s) {
        fachada.getGenFuente().visitar(s);
    }

    @Override
    public void visitar(FuncionDef s) {
        fachada.getGenFuncionDef().visitar(s);
    }

    @Override
    public void visitar(FuncionSub s) {
        fachada.getGenFuncionSub().visitar(s);
    }

    @Override
    public void visitar(Cuerpo s) {
        fachada.getGenCuerpo().visitar(s);
    }

    @Override
    public void visitar(StcLista s) {
        fachada.getGenSentencia().visitar(s);
    }

    @Override
    public void visitar(StcBloque s) {
        fachada.getGenSentencia().visitar(s);
    }

    @Override
    public void visitar(StcFlujo s) {
        fachada.getGenSentencia().visitar(s);
    }

    @Override
    public void visitar(StcModulos s) {
        fachada.getGenSentencia().visitar(s);
    }

    @Override
    public void visitar(StcComentario s) {
        fachada.getGenSentencia().visitar(s);
    }

    @Override
    public void visitar(StcTipado s) {
        fachada.getGenSentencia().visitar(s);
    }

    @Override
    public void visitar(StcImport s) {
        fachada.getGenSentencia().visitar(s);
    }

    @Override
    public void visitar(StcLineaJava s) {
        fachada.getGenSentencia().visitar(s);
    }

    @Override
    public void visitar(ExpLectura s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpStd s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(StcError s) {
        //Nunca se ejecutara
    }

    @Override
    public void visitar(ExpNumero s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpCadena s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpVariable s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpAsignacion s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpBinario s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpAritmetica s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpLogico s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpComparacion s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpColeccion s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpAcceso s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpFuncion s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpFuncion5 s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpRegulares s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpRango s) {
        fachada.getGenExpresion().visitar(s);
    }

    @Override
    public void visitar(Rango s) {
        fachada.getGenRango().visitar(s);
    }

    @Override
    public void visitar(Lista s) {
        fachada.getGenLista().visitar(s);
    }

    @Override
    public void visitar(ModNada s) {
        fachada.getGenModificador().visitar(s);
    }

    @Override
    public void visitar(ModIf s) {
        fachada.getGenModificador().visitar(s);
    }

    @Override
    public void visitar(ModUnless s) {
        fachada.getGenModificador().visitar(s);
    }

    @Override
    public void visitar(ModWhile s) {
        fachada.getGenModificador().visitar(s);
    }

    @Override
    public void visitar(ModUntil s) {
        fachada.getGenModificador().visitar(s);
    }

    @Override
    public void visitar(ModFor s) {
        fachada.getGenModificador().visitar(s);
    }

    @Override
    public void visitar(ModuloPackage s) {
        fachada.getGenModulo().visitar(s);
    }

    @Override
    public void visitar(ModuloUse s) {
        fachada.getGenModulo().visitar(s);
    }

    @Override
    public void visitar(ModuloDo s) {
        fachada.getGenModulo().visitar(s);
    }

    @Override
    public void visitar(Next s) {
        fachada.getGenFlujo().visitar(s);
    }

    @Override
    public void visitar(Last s) {
        fachada.getGenFlujo().visitar(s);
    }

    @Override
    public void visitar(Return s) {
        fachada.getGenFlujo().visitar(s);
    }

    @Override
    public void visitar(Igual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(MasIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(MenosIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(MultiIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(DivIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(ModIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(PowIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(AndIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(OrIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(XorIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(DespDIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(DespIIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(LOrIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(DLOrIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(LAndIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(XIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(ConcatIgual s) {
        fachada.getGenAsignacion().visitar(s);
    }

    @Override
    public void visitar(Entero s) {
        fachada.getGenNumero().visitar(s);
    }

    @Override
    public void visitar(Decimal s) {
        fachada.getGenNumero().visitar(s);
    }

    @Override
    public void visitar(CadenaSimple s) {
        fachada.getGenCadena().visitar(s);
    }

    @Override
    public void visitar(CadenaDoble s) {
        fachada.getGenCadena().visitar(s);
    }

    @Override
    public void visitar(CadenaComando s) {
        fachada.getGenCadena().visitar(s);
    }

    @Override
    public void visitar(CadenaQ s) {
        fachada.getGenCadena().visitar(s);
    }

    @Override
    public void visitar(CadenaQW s) {
        fachada.getGenCadena().visitar(s);
    }

    @Override
    public void visitar(CadenaQQ s) {
        fachada.getGenCadena().visitar(s);
    }

    @Override
    public void visitar(CadenaQX s) {
        fachada.getGenCadena().visitar(s);
    }

    @Override
    public void visitar(CadenaQR s) {
        fachada.getGenCadena().visitar(s);
    }

    @Override
    public void visitar(CadenaTexto s) {
        fachada.getGenCadenaTexto().visitar(s);
    }

    @Override
    public void visitar(VarExistente s) {
        fachada.getGenVariable().visitar(s);
    }

    @Override
    public void visitar(VarPaquete s) {
        fachada.getGenVariable().visitar(s);
    }

    @Override
    public void visitar(VarSigil s) {
        fachada.getGenVariable().visitar(s);
    }

    @Override
    public void visitar(VarPaqueteSigil s) {
        fachada.getGenVariable().visitar(s);
    }

    @Override
    public void visitar(VarMy s) {
        fachada.getGenVariable().visitar(s);
    }

    @Override
    public void visitar(VarOur s) {
        fachada.getGenVariable().visitar(s);
    }

    @Override
    public void visitar(Paquetes s) {
        fachada.getGenPaquetes().visitar(s);
    }

    @Override
    public void visitar(ColParentesis s) {
        fachada.getGenColeccion().visitar(s);
    }

    @Override
    public void visitar(ColDecMy s) {
        fachada.getGenColeccion().visitar(s);
    }

    @Override
    public void visitar(ColDecOur s) {
        fachada.getGenColeccion().visitar(s);
    }

    @Override
    public void visitar(ColCorchete s) {
        fachada.getGenColeccion().visitar(s);
    }

    @Override
    public void visitar(ColLlave s) {
        fachada.getGenColeccion().visitar(s);
    }

    @Override
    public void visitar(AccesoCol s) {
        fachada.getGenAcceso().visitar(s);
    }

    @Override
    public void visitar(AccesoColRef s) {
        fachada.getGenAcceso().visitar(s);
    }

    @Override
    public void visitar(AccesoDesRef s) {
        fachada.getGenAcceso().visitar(s);
    }

    @Override
    public void visitar(AccesoRef s) {
        fachada.getGenAcceso().visitar(s);
    }

    @Override
    public void visitar(FuncionBasica s) {
        fachada.getGenFuncion().visitar(s);
    }

    @Override
    public void visitar(FuncionHandle s) {
        fachada.getGenFuncion().visitar(s);
    }

    @Override
    public void visitar(FuncionBloque s) {
        fachada.getGenFuncion().visitar(s);
    }

    @Override
    public void visitar(HandleErr s) {
        fachada.getGenHandle().visitar(s);
    }

    @Override
    public void visitar(HandleOut s) {
        fachada.getGenHandle().visitar(s);
    }

    @Override
    public void visitar(HandleFile s) {
        fachada.getGenHandle().visitar(s);
    }

    @Override
    public void visitar(StdIn s) {
        fachada.getGenStd().visitar(s);
    }

    @Override
    public void visitar(StdOut s) {
        fachada.getGenStd().visitar(s);
    }

    @Override
    public void visitar(StdErr s) {
        fachada.getGenStd().visitar(s);
    }

    @Override
    public void visitar(LecturaIn s) {
        fachada.getGenLectura().visitar(s);
    }

    @Override
    public void visitar(LecturaFile s) {
        fachada.getGenLectura().visitar(s);
    }

    @Override
    public void visitar(RegularNoMatch s) {
        fachada.getGenRegulares().visitar(s);
    }

    @Override
    public void visitar(RegularMatch s) {
        fachada.getGenRegulares().visitar(s);
    }

    @Override
    public void visitar(RegularSubs s) {
        fachada.getGenRegulares().visitar(s);
    }

    @Override
    public void visitar(RegularTrans s) {
        fachada.getGenRegulares().visitar(s);
    }

    @Override
    public void visitar(BinOr s) {
        fachada.getGenBinario().visitar(s);

    }

    @Override
    public void visitar(BinAnd s) {
        fachada.getGenBinario().visitar(s);
    }

    @Override
    public void visitar(BinNot s) {
        fachada.getGenBinario().visitar(s);
    }

    @Override
    public void visitar(BinXor s) {
        fachada.getGenBinario().visitar(s);
    }

    @Override
    public void visitar(BinDespI s) {
        fachada.getGenBinario().visitar(s);
    }

    @Override
    public void visitar(BinDespD s) {
        fachada.getGenBinario().visitar(s);
    }

    @Override
    public void visitar(LogOr s) {
        fachada.getGenLogico().visitar(s);
    }

    @Override
    public void visitar(DLogOr s) {
        fachada.getGenLogico().visitar(s);
    }

    @Override
    public void visitar(LogAnd s) {
        fachada.getGenLogico().visitar(s);
    }

    @Override
    public void visitar(LogNot s) {
        fachada.getGenLogico().visitar(s);
    }

    @Override
    public void visitar(LogOrBajo s) {
        fachada.getGenLogico().visitar(s);
    }

    @Override
    public void visitar(LogAndBajo s) {
        fachada.getGenLogico().visitar(s);
    }

    @Override
    public void visitar(LogNotBajo s) {
        fachada.getGenLogico().visitar(s);
    }

    @Override
    public void visitar(LogXorBajo s) {
        fachada.getGenLogico().visitar(s);
    }

    @Override
    public void visitar(LogTernario s) {
        fachada.getGenLogico().visitar(s);
    }

    @Override
    public void visitar(CompNumEq s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumNe s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumLt s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumLe s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumGt s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumGe s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumCmp s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrEq s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrNe s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrLt s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrLe s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrGt s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrGe s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrCmp s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(CompSmart s) {
        fachada.getGenComparacion().visitar(s);
    }

    @Override
    public void visitar(AritSuma s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritResta s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritMulti s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritDiv s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPow s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritX s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritConcat s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritMod s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPositivo s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritNegativo s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPreIncremento s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPreDecremento s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPostIncremento s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPostDecremento s) {
        fachada.getGenAritmetica().visitar(s);
    }

    @Override
    public void visitar(AbrirBloque s) {
        fachada.getGenAbrirBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueWhile s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueUntil s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueDoWhile s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueDoUntil s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueFor s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueForeachVar s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueForeach s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueIf s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueUnless s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueSimple s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(SubBloqueElse s) {
        fachada.getGenBloque().visitar(s);

    }

    @Override
    public void visitar(SubBloqueElsif s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(SubBloqueVacio s) {
        fachada.getGenBloque().visitar(s);
    }

    @Override
    public void visitar(Terminal s) {
        fachada.getGenTerminal().visitar(s);
    }

}
