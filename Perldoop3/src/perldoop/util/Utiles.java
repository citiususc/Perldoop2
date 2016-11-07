package perldoop.util;

import java.util.Iterator;
import java.util.List;

/**
 * Utilidades varias para servir de ayuda a todos los modulos
 *
 * @author César Pomar
 */
public final class Utiles {

    /**
     * Normaliza un nombre para poder ser usado como identificador en java
     *
     * @param nombre Nombre
     * @return Identificador
     */
    public static String normalizar(String nombre) {
        nombre = nombre.replaceAll("_", "__");
        return nombre.replaceAll("(^[0-9]|[^a-zA-Z0-9])", "_");
    }

    /**
     * Concatena varios StringBuilder y los añade a destino
     *
     * @param lista Lista de StringBuilder
     * @param sep Separador
     * @param destino StringBuilder destino
     */
    public static void concat(List<StringBuilder> lista, String sep, StringBuilder destino) {
        Iterator<StringBuilder> it = lista.iterator();
        while (it.hasNext()) {
            destino.append(it.next());
            if (it.hasNext()) {
                destino.append(sep);
            }
        }
    }

}
