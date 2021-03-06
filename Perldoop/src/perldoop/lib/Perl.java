package perldoop.lib;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

/**
 * Implementación de las funciones nativa Perl
 *
 * @author César Pomar
 */
public final class Perl {

    /**
     * Imprime por pantalla
     *
     * @param args Argumentos
     * @return Retorna 1 si tiene exito
     */
    public static int print(Object... args) {
        return print(PerlFile.STDOUT, args);
    }

    /**
     * Imprime en un fichero
     *
     * @param f Fichero
     * @param args Argumentos
     * @return Retorna 1 si tiene exito
     */
    public static int print(PerlFile f, Object... args) {
        if (f == null) {
            return 0;
        }
        return f.print(args);
    }

    /**
     * Imprime por pantalla
     *
     * @param format Formato
     * @param args Argumentos
     * @return Retorna 1 si tiene exito
     */
    public static int printf(String format, Object... args) {
        return printf(PerlFile.STDOUT, format, args);
    }

    /**
     * Imprime en un fichero
     *
     * @param f Fichero
     * @param format Formato
     * @param args Argumentos
     * @return Retorna 1 si tiene exito
     */
    public static int printf(PerlFile f, String format, Object... args) {
        if (f == null) {
            return 0;
        }
        return f.printf(format, args);
    }

    /**
     * Procesa la cadena str y elimina y retorna su ultimo caracter
     *
     * @param str Cadena a procesar
     * @param rn Retorno de la funcion, ultimo caracter
     * @return Actualizacion de variable
     */
    public static String chop(String str, Ref<String>... rn) {
        int len = str.length();
        if (len == 0) {//Cadena vacia
            if (rn.length > 0) {
                rn[0].set("");
            }
            return str;
        }
        if (rn.length > 0) {//Ultimo caracter
            rn[0].set("" + str.charAt(len - 1));
        }
        return str.substring(0, len - 1);
    }

    /**
     * Procesa la cadena str y elimina y retorna el numero de caracteres eliminados
     *
     * @param str Cadena a procesar
     * @param rn Retorno de la funcion, numero de elementos eliminados
     * @return Actualizacion de variable
     */
    public static String chomp(String str, Ref<Integer>... rn) {
        String chomp = str.trim();
        if (rn.length > 0) {
            rn[0].set(str.length() - chomp.length());
        }
        return chomp;
    }

    /**
     * Ordena un array cuyos elementos son comparables
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param comp Comparador personalizado
     * @return Array ordenado
     */
    public static <T extends Comparable> T[] sort(T[] array, Comparator<T>... comp) {
        T[] copia = Arrays.copyOf(array, array.length);
        if (comp.length > 0) {
            Arrays.sort(copia, comp[0]);
        } else if (array.length > 1) {
            if (array[0] instanceof Number) {//Number no es comparable
                Arrays.sort((Number[]) copia, (Number n1, Number n2) -> Casting.toString(n1).compareTo(Casting.toString(n2)));
            } else {
                Arrays.sort(copia);
            }
        }
        return copia;
    }

    /**
     * Ordena una lista cuyos elementos son comparables
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param comp Comparador personalizado
     * @return Array ordenado
     */
    public static <T extends Comparable> PerlList<T> sort(PerlList<T> list, Comparator<T>... comp) {
        PerlList copia = new PerlList(list);
        if (comp.length > 0) {
            copia.sort(comp[0]);
        } else if (!list.isEmpty()) {
            if (list.get(0) instanceof Number) {//Number no es comparable
                ((PerlList<Number>) copia).sort((Number n1, Number n2) -> Casting.toString(n1).compareTo(Casting.toString(n2)));
            } else {
                copia.sort(null);
            }
        }
        return copia;
    }

    /**
     * Separa una cadena usando un separador expresado en forma de expresion regular como cadena
     *
     * @param regex Separador
     * @param str Cadena
     * @param limit Limite
     * @return Array de cadenas
     */
    public static String[] split(String regex, String str, Integer... limit) {
        int lt = -1;
        if (limit.length > 0) {
            lt = limit[0];
        }
        if (regex.equals(" ")) {
            regex = "\\s";
        }
        return Arrays.stream(str.split("(" + regex + ")+", lt)).filter(s->!s.isEmpty()).toArray(String[]::new);
    }

    /**
     * Separa una cadena usando un separador expresado en forma de expresion regular
     *
     * @param regex Separador
     * @param str Cadena
     * @param limit Limite
     * @return Array de cadenas
     */
    public static String[] split2(String regex, String str, Integer... limit) {
        int lt = -1;
        if (limit.length > 0) {
            lt = limit[0];
        }
        return Arrays.stream(str.split("(" + regex + ")+", lt)).filter(s->!s.isEmpty()).toArray(String[]::new);
    }

    /**
     * Une una lista de elementos usando un separador
     *
     * @param sep Separador
     * @param list Lista
     * @return Resultado
     */
    public static String join(String sep, List list) {
        StringJoiner joiner = new StringJoiner(sep);
        for (Object e : list) {
            joiner.add(e.toString());
        }
        return joiner.toString();
    }

    /**
     * Añade un valor al final de la lista
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param value Valor
     * @return Numero de elementos añadidos, es decir 1
     */
    public static <T> Integer push(PerlList<T> list, T value) {
        list.add(value);
        return 1;
    }

    /**
     * Añade un valor al final del array
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param value Valor
     * @param rn Retorno de la funcion, numero de elementos añadidos
     * @return Actualizacion de variable
     */
    public static <T> T[] push(T[] array, T value, Ref<Integer>... rn) {
        if (rn.length > 0) {
            rn[0].set(1);
        }
        T[] copia = Arrays.copyOf(array, array.length + 1);
        copia[array.length] = value;
        return copia;
    }

    /**
     * Añade una lista de valores valores al final de la lista
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param values Lista de valores
     * @return Numero de elementos añadidos
     */
    public static <T> Integer push(PerlList<T> list, List<T> values) {
        list.addAll(values);
        return values.size();
    }

    /**
     * Añade una lista de valores valores al final del array
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param values Lista de valores
     * @param rn Retorno de la funcion, numero de elementos añadidos
     * @return Actualizacion de variable
     */
    public static <T> T[] push(T[] array, List<T> values, Ref<Integer>... rn) {
        if (rn.length > 0) {
            rn[0].set(values.size());
        }
        T[] copia = Arrays.copyOf(array, array.length + values.size());
        for (int i = 0; i < values.size(); i++) {
            copia[array.length + i] = values.get(i);
        }
        return copia;
    }

    /**
     * Elimina el ultimo elemento de la lista y lo retorna
     *
     * @param <T> Tipo del elemento
     * @param list Lista
     * @return Ultimo elemento
     */
    public static <T> T pop(PerlList<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.remove(list.size() - 1);
    }

    /**
     * Elimina el ultimo elemento de la lista y lo retorna
     *
     * @param <T> Tipo del elemento
     * @param array Array
     * @param rn Retorno de la funcion, ultimo elemento
     * @return Actualizacion de variable
     */
    public static <T> T[] pop(T[] array, Ref<T>... rn) {
        if (rn.length > 0) {
            rn[0].set(array[array.length - 1]);
        }
        if (array.length == 0) {
            return array;
        }
        return Arrays.copyOf(array, array.length - 1);
    }

    /**
     * Añade un valor al principio de la lista
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param value Valor
     * @return Numero de elementos añadidos, es decir 1
     */
    public static <T> Integer unshift(PerlList<T> list, T value) {
        list.add(0, value);
        return 1;
    }

    /**
     * Añade un valor al principio del array
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param value Valor
     * @param rn Retorno de la funcion, numero de elementos añadidos
     * @return Actualizacion de variable
     */
    public static <T> T[] unshift(T[] array, T value, Ref<Integer>... rn) {
        if (rn.length > 0) {
            rn[0].set(1);
        }
        T[] copia = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length + 1);
        copia[0] = value;
        System.arraycopy(array, 0, copia, 1, array.length);
        return copia;
    }

    /**
     * Añade una lista de valores valores al principio de la lista
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param values Lista de valores
     * @return Numero de elementos añadidos
     */
    public static <T> Integer unshift(PerlList<T> list, List<T> values) {
        list.addAll(0, values);
        return values.size();
    }

    /**
     * Añade una lista de valores valores al principio del array
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param values Lista de valores
     * @param rn Retorno de la funcion, numero de elementos añadidos
     * @return Actualizacion de variable
     */
    public static <T> T[] unshift(T[] array, List<T> values, Ref<Integer>... rn) {
        if (rn.length > 0) {
            rn[0].set(values.size());
        }
        T[] copia = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length + values.size());
        values.toArray(copia);
        System.arraycopy(array, 0, copia, values.size(), array.length);
        return copia;
    }

    /**
     * Elimina el primer elemento de la lista y lo retorna
     *
     * @param <T> Tipo del elemento
     * @param list Lista
     * @return Primer elemento
     */
    public static <T> T shift(PerlList<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.remove(0);
    }

    /**
     * Elimina el primer elemento de la lista y lo retorna
     *
     * @param <T> Tipo del elemento
     * @param array Array
     * @param rn Retorno de la funcion, primer elemento
     * @return Actualizacion de variable
     */
    public static <T> T[] shift(T[] array, Ref<T>... rn) {
        if (rn.length > 0) {
            rn[0].set(array[0]);
        }
        if (array.length == 0) {
            return array;
        }
        return Arrays.copyOfRange(array, 1, array.length);
    }

    /**
     * Borra varias posiciones en un array
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param indexs Posiciones
     * @return Valores eliminados
     */
    public static <T> T[] delete(T[] array, Number[] indexs) {
        T[] res = (T[]) Array.newInstance(array.getClass().getComponentType(), indexs.length);
        for (int i = 0; i < indexs.length; i++) {
            res[i] = array[indexs[i].intValue()];
            array[indexs[i].intValue()] = null;
        }
        return res;
    }

    /**
     * Borra una posicion en un array
     *
     * @param <T> Tipo de los elementos
     * @param array Array
     * @param index Posicion
     * @return Valor eliminado
     */
    public static <T> T delete(T[] array, Integer index) {
        T res = array[index];
        array[index] = null;
        return res;
    }

    /**
     * Borra varias posiciones en una lista
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param indexs Posiciones
     * @return Valores eliminados
     */
    public static <T> PerlList<T> delete(PerlList<T> list, Number[] indexs) {
        PerlList<T> res = new PerlList<>(indexs.length);
        for (Number n : indexs) {
            res.add(list.get(n.intValue()));
            list.set(n.intValue(), null);
        }
        return res;
    }

    /**
     * Borra una posicion en una lista
     *
     * @param <T> Tipo de los elementos
     * @param list Lista
     * @param index Posicion
     * @return Valor eliminado
     */
    public static <T> T delete(PerlList<T> list, Integer index) {
        T res = list.get(index);
        list.set(index, null);
        return res;
    }

    /**
     * Borra varias posiciones en un hash
     *
     * @param <T> Tipo de los elementos
     * @param map Mapa
     * @param keys Claves
     * @return Valores eliminados
     */
    public static <T> PerlList<T> delete(PerlMap<T> map, String[] keys) {
        PerlList<T> res = new PerlList<>(keys.length);
        for (String k : keys) {
            res.add(map.remove(k));
        }
        return res;
    }

    /**
     * Borra una posicion en un hash
     *
     * @param <T> Tipo de los elementos
     * @param map Mapa
     * @param key Clave
     * @return Valor eliminado
     */
    public static <T> T delete(PerlMap<T> map, String key) {
        return map.remove(key);
    }

    /**
     * Borra una posicion multiclave en un hash
     *
     * @param <T> Tipo de los elementos
     * @param map Mapa
     * @param keys Claves
     * @param multiKey Clave multiple
     * @return Valor eliminado
     */
    public static <T> T delete(PerlMap<T> map, String[] keys, boolean multiKey) {
        return map.remove(Arrays.asList(keys));
    }

    /**
     * Obtiene las claves de un mapa
     *
     * @param map Mapa
     * @return Lista de claves
     */
    public static PerlList<String> keys(PerlMap map) {
        return new PerlList<>(map.keySet());
    }

    /**
     * Obitne los valores de un mapa
     *
     * @param <T> Tipo de los valores
     * @param map mapa
     * @return Lista de valores
     */
    public static <T> PerlList<T> values(PerlMap<T> map) {
        return new PerlList<>(map.values());
    }

    /**
     * Abre un fichero para lectura
     *
     * @param file Descriptor del fichero
     * @param path Ruta del fichero
     * @return 1 si tuvo existo, 0 en otro caso
     */
    public static Integer open(PerlFile file, String path) {
        return file.open(path, "<");
    }

    /**
     * Abre un fichero
     *
     * @param file Descriptor del fichero
     * @param mode Modo de apertura
     * @param path Ruta del fichero
     * @return 1 si tuvo existo, 0 en otro caso
     */
    public static Integer open(PerlFile file, String mode, String path) {
        return file.open(path, mode);
    }

    /**
     * Cierra un fichero
     *
     * @param file Descriptor del fichero
     * @return 1 si tuvo existo, 0 en otro caso
     */
    public static Integer close(PerlFile file) {
        if (file != null) {
            return file.close();
        }
        return 0;
    }

    /**
     * Aborta la ejecucion y muestra el status de salida
     *
     * @param status Status
     * @return Sin retorno
     */
    public static Boolean exit(Integer status) {
        System.exit(status);
        return null;
    }

    /**
     * Muestra un mensaje de error por pantalla
     *
     * @param args Mensaje
     * @return True si tuvo existo, False en otro caso
     */
    public static Boolean warn(Object... args) {
        return PerlFile.STDERR.println(args) == 1;
    }

    /**
     * Aborta la ejecucuin y muestra un mensaje por pantalla
     *
     * @param args Mensaje
     * @return Sin retorno
     */
    public static Boolean die(Object... args) {
        PerlFile.STDERR.println(args);
        System.exit(-1);
        return null;
    }

    /**
     * Comprueba si la expresion esta definida
     *
     * @param exp Expresion
     * @return Expresion no es nula
     */
    public static Boolean defined(Object exp) {
        return exp != null;
    }

    /**
     * Comprueba si la expresion esta definida(Accesos que generan referencia)
     *
     * @param f Referencia
     * @return Expresion no es nula
     */
    public static Boolean defined(Ref f) {
        return Casting.toBoolean(f);
    }

    /**
     * Convierte la cadena a minusculas
     *
     * @param str Cadena
     * @return Cadena en minusculas
     */
    public static String lc(String str) {
        return str.toLowerCase();
    }

    /**
     * Convierte la primera letra de la cadena a minusculas
     *
     * @param str Cadena
     * @return Cadena con la primera letra en minusculas
     */
    public static String lcfirst(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * Convierte la cadena a mayusculas
     *
     * @param str Cadena
     * @return Cadena en mayusculas
     */
    public static String uc(String str) {
        return str.toUpperCase();
    }

    /**
     * Convierte la primera letra de la cadena a mayusculas
     *
     * @param str Cadena
     * @return Cadena con la primera letra en mayusculas
     */
    public static String ucfirst(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Logaritmo natural
     *
     * @param n Numero
     * @return Logaritmo de n
     */
    public static Double log(Number n) {
        return Math.log(n.doubleValue());
    }

    /**
     * Cambia la codificación de un fichero
     *
     * @param file Fichero
     * @param encode Codificación
     * @return
     */
    public static Integer binmode(PerlFile file, String encode) {
        return file.setEnconde(encode);
    }

}
