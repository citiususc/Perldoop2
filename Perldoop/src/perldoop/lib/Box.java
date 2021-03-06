package perldoop.lib;

/**
 * Interfaz para contenedor de cualquier tipo de dato
 *
 * @author César Pomar
 */
public interface Box extends Comparable<Box> {

    /**
     * Obtiene la representanción booleana
     *
     * @return Entero despues de la conversión
     */
    Boolean booleanValue();

    /**
     * Obtiene la representanción entera
     *
     * @return Entero despues de la conversión
     */
    Integer intValue();

    /**
     * Obtiene la representación entero largo
     *
     * @return Entero largo despues de la conversión
     */
    Long longValue();

    /**
     * Obtiene la representación flotante
     *
     * @return Flotante despues de la conversión
     */
    Float floatValue();

    /**
     * Obtiene la representación flotante de doble precisión
     *
     * @return Flotante de doble precisión despues de la conversión
     */
    Double doubleValue();

    /**
     * Obtiene la representación abstracta de un numero
     *
     * @return Representación abstracta de un numero
     */
    Number numberValue();

    /**
     * Obtiene la representación cadena
     *
     * @return Cadena despues de la conversión
     */
    String stringValue();

    /**
     * Obtiene la representación referencia
     *
     * @return Referencia despues de la conversión
     */
    Ref refValue();

    /**
     * Obtiene la representación fichero
     *
     * @return Fichero despues de la conversión
     */
    PerlFile fileValue();

    @Override
    public default int compareTo(Box o) {
        return stringValue().compareTo(o.stringValue());
    }

}
