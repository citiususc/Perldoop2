package perldoop.modelo.generacion;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona la reserva de palabras durante la generación de código
 *
 * @author César Pomar
 */
public final class GestorReservas {

    public final static String PREF = "pd_";
    private Map<String, Integer> reservas;

    /**
     * Construye el gestor de Reservas
     */
    public GestorReservas() {
        this.reservas = new HashMap<>(100);
    }

    /**
     * Obtiene el alias para una variable o función
     *
     * @param id Id
     * @param conflicto Alias por conflicto
     * @return Id alias
     */
    public String getAlias(String id, boolean conflicto) {
        if (!conflicto && !isReservada(id)) {
            return id;
        }
        int n = reservas.getOrDefault(id, 1);
        reservas.put(id, n + 1);
        return PREF + n + id;
    }

    /**
     * Obtiene una variable auxiliar
     *
     * @return Variable auxiliar
     */
    public String getAux() {
        return getAlias("", true);
    }

    /**
     * Obtiene una variable auxiliar
     *
     * @param id Nombre de la variable
     * @return Variable auxiliar
     */
    public String getAux(String id) {
        return getAlias(id, true);
    }

    /**
     * Comprueba si el identificador esta reservado por java
     *
     * @param id Identificador
     * @return Palabra reservada java
     */
    public static boolean isReservadaJava(String id) {
        switch (id) {
            //Java
            case "abstract":
            case "continue":
            case "for":
            case "new":
            case "switch":
            case "assert":
            case "default":
            case "goto":
            case "package":
            case "synchronized":
            case "boolean":
            case "do":
            case "if":
            case "private":
            case "this":
            case "break":
            case "double":
            case "implements":
            case "protected":
            case "throw":
            case "byte":
            case "else":
            case "import":
            case "public":
            case "throws":
            case "case":
            case "enum":
            case "instanceof":
            case "return":
            case "transient":
            case "catch":
            case "extends":
            case "int":
            case "short":
            case "try":
            case "char":
            case "final":
            case "interface":
            case "static":
            case "void":
            case "class":
            case "finally":
            case "long":
            case "strictfp":
            case "volatile":
            case "const":
            case "float":
            case "native":
            case "super":
            case "while":
                return true;
            default:
                return false;
        }
    }

    /**
     * Busca si una palabra esta reservada
     *
     * @param id Identificador
     * @return Esta reservada
     */
    private static boolean isReservada(String id) {
        switch (id) {
            //Clases
            case "IOException":
            //Perldoop
            case "Box":
            case "Casting":
            case "Pd":
            case "Perl":
            case "PerlFile":
            case "PerlList":
            case "PerlMap":
            case "Ref":
            case "Regex":
            case "__":
            //Hadoop
            case "Text":
            case "Mapper":
            case "Reducer":
                return true;
            default:
                return id.startsWith(PREF) || isReservadaJava(id);
        }
    }

}
