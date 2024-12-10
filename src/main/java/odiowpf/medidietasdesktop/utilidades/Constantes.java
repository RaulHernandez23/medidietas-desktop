package odiowpf.medidietasdesktop.utilidades;

import java.text.SimpleDateFormat;

public class Constantes {

    // KEYS para los HashMaps
    public static final String KEY_ERROR = "error";
    public static final String KEY_MENSAJE = "mensaje";
    public static final String KEY_OBJETO = "objeto";
    public static final String KEY_IMAGEN = "imagen";

    // Constantes para la conexión con la API
    public static final String PUERTO_REST = "8081";
    public static final String PUERTO_GRPC = "50052";
    public static final String HOST = "localhost";
    public static final String URL_REST = "http://" + HOST + ":" + PUERTO_REST + "/api/medidietas/";
    public static final String URL_GRPC = HOST + ":" + PUERTO_GRPC;

    // Formatos
    public static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("yyyy-MM-dd");

    // Mensajes
    public static final String ALERTA_CONFIRMACION_TITULO = "Confirmación";
    public static final String ALERTA_CONFIRMACION_CONTENIDO = "¿Estas seguro de cancelar la operación?";
    public static final String ALERTA_ERROR_TITULO = "Error";
    public static final String ALERTA_MODIFICACION_COMIDA_TITULO = "Modificación exitosa";
    public static final String ALERTA_MODIFICACION_COMIDA_CONTENIDO = "La comida se modificó correctamente.";

    //Titulos
    public static final String TITULO_REGISTRAR_COMIDA = "Registrar Comida";
    public static final String TITULO_MODIFICAR_COMIDA = "Modificar comida";
}
