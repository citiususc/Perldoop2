package perldoop.internacionalizacion;

/**
 * Clase para gestionar los mensajes de errores de la apliación
 *
 * @author César Pomar
 */
public class Errores extends Internacionalizacion {

    //Tipos
    public static final String ERROR = "ERROR";
    public static final String AVISO = "AVISO";
    //Lexicos
    public static final String CARACTER_INVALIDO = "CARACTER_INVALIDO";
    public static final String FALLOS_LEXICOS = "FALLOS_LEXICOS";
    public static final String ETIQUETA_IGNORADA = "ETIQUETA_IGNORADA";
    //Preprocesador
    public static final String ETIQUETA_NO_USADA = "ETIQUETA_NO_USADA";
    public static final String FALLOS_SINTACTICOS = "FALLOS_SINTACTICOS";
    //Sintacticos
    public static final String ERROR_SINTACTICO = "ERROR_SINTACTICO";
    public static final String FALLOS_SEMANTICO = "FALLOS_SEMANTICO";
    //Semanticos
    public static final String MODIFICAR_CONSTANTE = "MODIFICAR_CONSTANTE";
    public static final String MODIFICAR_DEFERENCIACION = "MODIFICAR_DEFERENCIACION";
    public static final String OUR_SIN_PAQUETE = "OUR_SIN_PAQUETE";
    public static final String DECLACION_CONSTANTE = "DECLACION_CONSTANTE";
    public static final String TIPO_FOREACH = "TIPO_FOREACH";
    public static final String ACCESO_DECLARACION = "ACCESO_DECLARACION";
    public static final String VARIABLE_SIN_TIPO = "VARIABLE_SIN_TIPO";
    public static final String VARIABLE_NO_EXISTE = "VARIABLE_NO_EXISTE";
    public static final String PAQUETE_NO_EXISTE = "PAQUETE_NO_EXISTE";
    public static final String TIPO_ESCALAR_OMITIDO = "TIPO_ESCALAR_OMITIDO";
    public static final String ETIQUETA_SIN_USO = "ETIQUETA_SIN_USO";
    public static final String TIPO_INCORRECTO = "TIPO_INCORRECTO";
    public static final String VARIABLE_ENMASCARADA = "VARIABLE_ENMASCARADA";
    public static final String ERROR_CASTING = "ERROR_CASTING";

    public Errores() {
        super("errores");
    }

}
